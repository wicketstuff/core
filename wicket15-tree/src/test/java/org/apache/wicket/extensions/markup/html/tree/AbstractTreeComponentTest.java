/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.wicket.extensions.markup.html.tree;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.tree.TreeModel;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.list.AbstractItem;
import org.apache.wicket.mock.MockApplication;
import org.apache.wicket.util.tester.WicketTester;
import org.apache.wicket.util.visit.IVisit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Exercises expanding, collapsing and selecting nodes of a tree component through its Ajax links,
 * the way a user does in the browser. Subclasses provide the tree component and the paths to its
 * links.
 */
public abstract class AbstractTreeComponentTest
{
	private WicketTester tester;

	private FamilyTree family;

	private AbstractTree tree;

	/**
	 * @return a new tree component with id {@code tree} for the given model
	 */
	protected abstract AbstractTree newTree(String id, TreeModel model);

	/**
	 * @return the path of the junction (expand/collapse) link, relative to a tree item
	 */
	protected abstract String junctionLinkPath();

	/**
	 * @return the path of the link that selects a node, relative to a tree item, or {@code null}
	 *         when the tree has no such link
	 */
	protected abstract String selectLinkPath();

	/**
	 * @return the CSS class a tree item gets when its node is selected
	 */
	protected abstract String selectedCssClass();

	@BeforeEach
	public void setUp()
	{
		tester = new WicketTester(new MockApplication()
		{
			@Override
			protected void init()
			{
				super.init();
				// MockApplication disables the CSP; test with the strict CSP Wicket applications
				// get by default
				getCspSettings().blocking().strict();
			}
		});
		family = new FamilyTree();
		tree = newTree("tree", family.model);
		tester.startPage(new TreeTestPage(tree));
		tester.assertRenderedPage(TreeTestPage.class);
	}

	@AfterEach
	public void tearDown()
	{
		tester.destroy();
	}

	@Test
	public void rendersOnlyTheCollapsedRoot()
	{
		String document = tester.getLastResponseAsString();

		assertThat(document, containsString("Opa Puk"));
		assertThat(document, not(containsString("Pietje Puk")));
		assertNotNull(item(family.opa));
		assertNull(item(family.pietje));
		assertFalse(tree.getTreeState().isNodeExpanded(family.opa));
	}

	@Test
	public void expandsNestedNodesUsingAjax()
	{
		clickJunction(family.opa);

		assertTrue(tree.getTreeState().isNodeExpanded(family.opa));
		String response = tester.getLastResponseAsString();
		assertThat(response, containsString("<ajax-response>"));
		assertThat(response, containsString("Pietje Puk"));
		assertThat(response, containsString("Janneke Puk"));
		assertThat(response, not(containsString("Pino Puk")));
		assertNull(item(family.pino));

		clickJunction(family.janneke);

		assertTrue(tree.getTreeState().isNodeExpanded(family.janneke));
		assertThat(tester.getLastResponseAsString(), containsString("Pino Puk"));
		assertNotNull(item(family.pino));
	}

	@Test
	public void collapseRemovesTheChildrenUsingAjax()
	{
		clickJunction(family.opa);
		clickJunction(family.janneke);
		List<String> descendantIds = Arrays.asList(shortItemId(family.pietje),
			shortItemId(family.janneke), shortItemId(family.pino));

		clickJunction(family.opa);

		assertFalse(tree.getTreeState().isNodeExpanded(family.opa));
		assertThat(removedItemIds(tester.getLastResponseAsString()),
			containsInAnyOrder(descendantIds.toArray()));
		assertNull(item(family.pietje));
		assertNull(item(family.janneke));
		assertNull(item(family.pino));

		tester.startPage(tester.getLastRenderedPage());
		assertThat(tester.getLastResponseAsString(), not(containsString("Pietje Puk")));
	}

	@Test
	public void selectsAndDeselectsANodeUsingAjax()
	{
		assumeTrue(selectLinkPath() != null, "this tree has no link that selects a node");
		clickJunction(family.opa);

		clickSelect(family.janneke);

		assertTrue(tree.getTreeState().isNodeSelected(family.janneke));
		assertThat(tree.getTreeState().getSelectedNodes(), hasItem(family.janneke));
		assertThat(cssClassesOf(item(family.janneke), tester.getLastResponseAsString()),
			hasItem(selectedCssClass()));

		clickSelect(family.janneke);

		assertFalse(tree.getTreeState().isNodeSelected(family.janneke));
		assertThat(cssClassesOf(item(family.janneke), tester.getLastResponseAsString()),
			not(hasItem(selectedCssClass())));
	}

	@Test
	public void rendersWithoutInlineStylesOrEventHandlers()
	{
		assertCspCompliant(tester.getLastResponseAsString());

		clickJunction(family.opa);
		assertCspCompliant(tester.getLastResponseAsString());

		clickJunction(family.janneke);
		assertCspCompliant(tester.getLastResponseAsString());

		if (selectLinkPath() != null)
		{
			clickSelect(family.janneke);
			assertCspCompliant(tester.getLastResponseAsString());
		}

		clickJunction(family.opa);
		assertCspCompliant(tester.getLastResponseAsString());
	}

	@Test
	public void rendersHiddenRootOfRootlessTreeWithoutInlineStyle()
	{
		AbstractTree rootless = newTree("tree", new FamilyTree().model);
		rootless.setRootLess(true);
		rootless.getTreeState().expandNode(rootless.getModelObject().getRoot());
		tester.startPage(new TreeTestPage(rootless));

		String document = tester.getLastResponseAsString();
		assertThat(document, not(containsString("Opa Puk")));
		assertThat(document, containsString("Pietje Puk"));
		assertTrue(
			Pattern.compile("<\\w+ hidden id=\"" + Pattern.quote(rootless.getMarkupId()) + "_\\d+\">")
				.matcher(document)
				.find(), "no hidden placeholder for the root in " + document);
		assertCspCompliant(document);
	}

	/**
	 * Asserts that the markup has no inline style attributes and no inline event handlers, which
	 * Wicket's Content Security Policy blocks, and that every generated style class has its rule
	 * in a {@code <style>} element with the CSP nonce in the same response.
	 */
	private static void assertCspCompliant(String markup)
	{
		Matcher inlineStyle = Pattern.compile("<[^>]*\\sstyle=\"[^>]*>").matcher(markup);
		assertFalse(inlineStyle.find(), () -> "inline style attribute: " + inlineStyle.group());

		Matcher eventHandler = Pattern.compile("<[^>]*\\son[a-z]+=\"[^>]*>").matcher(markup);
		assertFalse(eventHandler.find(), () -> "inline event handler: " + eventHandler.group());

		Matcher styleClass = Pattern.compile("wicket15-tree-(?:icon|column)-[0-9a-f]{16}")
			.matcher(markup);
		while (styleClass.find())
		{
			String cssClass = styleClass.group();
			Matcher styleTag = Pattern.compile("<style[^>]*\\sid=\"" + cssClass + "\"[^>]*>")
				.matcher(markup);
			assertTrue(styleTag.find(), "no <style> element for " + cssClass + " in " + markup);
			assertThat(styleTag.group(), containsString(" nonce=\""));
			assertThat(markup, containsString("." + cssClass + "." + cssClass + "{"));
		}
	}

	private void clickJunction(FamilyTreeNode node)
	{
		clickLink(node, junctionLinkPath());
	}

	private void clickSelect(FamilyTreeNode node)
	{
		clickLink(node, selectLinkPath());
	}

	private void clickLink(FamilyTreeNode node, String relativePath)
	{
		Component item = item(node);
		assertNotNull(item, "no tree item rendered for " + node);
		tester.clickLink(item.getPageRelativePath() + ":" + relativePath, true);
	}

	/**
	 * @return the rendered tree item for the node, or {@code null} when the node is not visible
	 */
	private Component item(FamilyTreeNode node)
	{
		return tree.visitChildren(AbstractItem.class, (AbstractItem item, IVisit<Component> visit) -> {
			if (item.getDefaultModelObject() == node)
			{
				visit.stop(item);
			}
		});
	}

	private String shortItemId(FamilyTreeNode node)
	{
		Component item = item(node);
		assertNotNull(item, "no tree item rendered for " + node);
		return item.getId();
	}

	private List<String> removedItemIds(String ajaxResponse)
	{
		Matcher matcher = Pattern.compile(
			"Wicket\\.Tree\\.removeNodes\\(\"" + Pattern.quote(tree.getMarkupId()) + "_\",\\[([^\\]]*)\\]\\)")
			.matcher(ajaxResponse);
		assertTrue(matcher.find(), "no Wicket.Tree.removeNodes call in " + ajaxResponse);
		List<String> ids = new ArrayList<>();
		for (String id : matcher.group(1).split(","))
		{
			ids.add(id.replace("\"", "").trim());
		}
		return ids;
	}

	private static List<String> cssClassesOf(Component component, String markup)
	{
		// skip the <component id="..."> elements of the Ajax response envelope
		Matcher tag = Pattern.compile("<(?!component\\b)\\w[^>]*\\sid=\"" +
			Pattern.quote(component.getMarkupId()) + "\"[^>]*>").matcher(markup);
		assertTrue(tag.find(), "no tag with id " + component.getMarkupId() + " in " + markup);
		Matcher klass = Pattern.compile("\\sclass=\"([^\"]*)\"").matcher(tag.group());
		return klass.find() ? Arrays.asList(klass.group(1).split("\\s+")) : List.of();
	}
}
