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
package org.wicketstuff.minis.behavior.apanel;

import java.util.Arrays;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.IMarkupResourceStreamProvider;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.util.resource.IResourceStream;
import org.apache.wicket.util.resource.StringBufferResourceStream;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings({ "rawtypes", "serial" })
public class TestRenderers
{
	private static class RenderersTestPage extends TestPage
	{
		private final APanel aPanel;

		public RenderersTestPage()
		{
			super();
			aPanel = new APanel("aPanel");
			add(aPanel);
		}

		public APanel getAPanel()
		{
			return aPanel;
		}
	}

	private WicketTester tester;

	private RenderersTestPage page;

	@Before
	public void setup()
	{
		tester = new WicketTester();
		page = new RenderersTestPage();
	}

	@Test
	public void testDefaultWebMarkupContainerWithMarkupRenderer()
	{
		class TestPanel extends Panel implements IMarkupResourceStreamProvider
		{
			private static final long serialVersionUID = 1L;

			public TestPanel(final String id)
			{
				super(id);
			}

			public IResourceStream getMarkupResourceStream(final MarkupContainer container,
				final Class containerClass)
			{
				return new StringBufferResourceStream().append("<wicket:panel></wicket:panel>");
			}
		}
		page.getAPanel().add(new TestPanel("panel"));
		tester.startPage(page);

		tester.assertComponent("aPanel:panel", TestPanel.class);
	}

	@Test
	public void testForm()
	{
		final Form form = new Form("form");
		form.add(new Label("label", "label on the form"));
		form.add(new Button("button"));
		page.getAPanel().add(form);
		tester.startPage(page);

		tester.assertComponent("aPanel:form", Form.class);
		tester.assertLabel("aPanel:form:label", "label on the form");
		tester.assertComponent("aPanel:form:button", Button.class);
	}

	@Test
	public void testLabel()
	{
		page.getAPanel().add(new Label("label", "some text"));
		tester.startPage(page);

		tester.assertLabel("aPanel:label", "some text");
	}

	@Test
	public void testLink()
	{
		final Link link = new Link("link")
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick()
			{
				// do nothing
			}
		};
		link.add(new Label("label", "some text"));
		page.getAPanel().add(link);
		tester.startPage(page);

		tester.assertComponent("aPanel:link", Link.class);
		tester.assertLabel("aPanel:link:label", "some text");
	}

	@Test
	public void testListView()
	{
		@SuppressWarnings("unchecked")
		final ListView listView = new ListView("listView", Arrays.asList("a1", "2", "3"))
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(final ListItem item)
			{
				final String s = (String)item.getModelObject();
				item.add(new Label("label", s));
			}
		};
		page.getAPanel().add(listView);
		tester.startPage(page);

		tester.assertComponent("aPanel:listView", ListView.class);
		tester.assertComponent("aPanel:listView:0", ListItem.class);
		tester.assertComponent("aPanel:listView:1", ListItem.class);
		tester.assertComponent("aPanel:listView:2", ListItem.class);
		tester.assertLabel("aPanel:listView:0:label", "a1");
		tester.assertLabel("aPanel:listView:1:label", "2");
		tester.assertLabel("aPanel:listView:2:label", "3");
	}

	@Test
	public void testRepeatingView()
	{
		final RepeatingView repeatingView = new RepeatingView("rw");
		repeatingView.add(new Label("1", "hello"));
		repeatingView.add(new Label("2", "goodbye"));
		repeatingView.add(new Label("3", "good morning"));
		page.getAPanel().add(repeatingView);
		tester.startPage(page);

		tester.assertLabel("aPanel:rw:1", "hello");
		tester.assertLabel("aPanel:rw:2", "goodbye");
		tester.assertLabel("aPanel:rw:3", "good morning");
	}

	@Test
	public void testRepeatingViewWithMarkupContainer()
	{
		final RepeatingView repeatingView = new RepeatingView("rw");
		for (int i = 0; i < 3; i++)
		{
			final BookmarkablePageLink<RenderersTestPage> link = new BookmarkablePageLink<RenderersTestPage>(
				String.valueOf(i), RenderersTestPage.class);
			link.add(new Label("label", "label" + i));
			repeatingView.add(link);
		}
		page.getAPanel().add(repeatingView);
		tester.startPage(page);

		tester.assertComponent("aPanel:rw:0", BookmarkablePageLink.class);
		tester.assertComponent("aPanel:rw:1", BookmarkablePageLink.class);
		tester.assertComponent("aPanel:rw:2", BookmarkablePageLink.class);
		tester.assertLabel("aPanel:rw:0:label", "label0");
		tester.assertLabel("aPanel:rw:1:label", "label1");
		tester.assertLabel("aPanel:rw:2:label", "label2");
	}
}
