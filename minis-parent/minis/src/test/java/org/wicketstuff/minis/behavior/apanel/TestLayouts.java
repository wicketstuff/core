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

import org.apache.wicket.Component;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.util.resource.StringBufferResourceStream;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("serial")
public class TestLayouts
{
	private static class LayoutTestPage extends TestPage
	{
		protected Label label1;
		protected Label label2;
		protected Label label3;
		protected Label label4;

		public LayoutTestPage(final ILayout layout)
		{
			super();
			final APanel aPanel = new APanel("aPanel", layout);

			label1 = new Label("label1", "some text");
			label2 = new Label("label2", "some text");
			label3 = new Label("label3", "some text");
			label4 = new Label("label4", "some text");
			aPanel.add(label1);
			aPanel.add(label2);
			aPanel.add(label3);
			aPanel.add(label4);
			add(aPanel);

			addConstraints();
		}

		protected void addConstraints()
		{
		}
	}

	private WicketTester tester;

	@Before
	public void setup()
	{
		tester = new WicketTester();
	}

	private void testComponents()
	{
		tester.assertComponent("aPanel", APanel.class);
		tester.assertLabel("aPanel:label1", "some text");
		tester.assertLabel("aPanel:label2", "some text");
		tester.assertLabel("aPanel:label3", "some text");
		tester.assertLabel("aPanel:label4", "some text");
	}

	@Test
	public void testFlowLayout() throws Exception
	{
		tester.startPage(new LayoutTestPage(new FlowLayout()
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onAfterTag(final Component component, final StringBuilder stringBuilder)
			{
				stringBuilder.append("]");
			}

			@Override
			public void onBeforeTag(final Component component, final StringBuilder stringBuilder)
			{
				stringBuilder.append("[");
			}
		}));

		testComponents();

		testGeneratedMarkup("<wicket:panel>" + "[<span wicket:id=\"label1\"></span>]"
			+ "[<span wicket:id=\"label2\"></span>]" + "[<span wicket:id=\"label3\"></span>]"
			+ "[<span wicket:id=\"label4\"></span>]" + "</wicket:panel>");
	}

	private void testGeneratedMarkup(final String expected)
	{
		final APanel aPanel = (APanel)tester.getComponentFromLastRenderedPage("aPanel");
		final StringBufferResourceStream resourceStream = (StringBufferResourceStream)aPanel.getMarkupResourceStream(
			aPanel, APanel.class);

		Assert.assertEquals(expected, resourceStream.asString());
	}

	/**
	 * Tests that components are places like this:
	 * <table border=1>
	 * <tr>
	 * <td>label1</td>
	 * <td>label2</td>
	 * </tr>
	 * <tr>
	 * <td>label3</td>
	 * <td>label4</td>
	 * </tr>
	 * <tr>
	 * <td>---</td>
	 * <td>---</td>
	 * </tr>
	 * </table>
	 */
	@Test
	public void testGridLayout()
	{
		// layout size is for 6 components to test empty cells processing
		tester.startPage(new LayoutTestPage(new GridLayout(2, 3))
		{
			@Override
			protected void addConstraints()
			{
				label1.add(new GridLayoutConstraint(0, 0));
				label2.add(new GridLayoutConstraint(1, 0));
				label3.add(new GridLayoutConstraint(0, 1));
				label4.add(new GridLayoutConstraint(1, 1));
			}
		});

		testComponents();

		testGeneratedMarkup("<wicket:panel>" + "<table>" + "<tr>"
			+ "<td><span wicket:id=\"label1\"></span></td>"
			+ "<td><span wicket:id=\"label2\"></span></td>" + "</tr><tr>"
			+ "<td><span wicket:id=\"label3\"></span></td>"
			+ "<td><span wicket:id=\"label4\"></span></td>" + "</tr><tr>" + "<td></td><td></td>"
			+ "</tr>" + "</table>" + "</wicket:panel>");
	}

	@Test(expected = WicketRuntimeException.class)
	public void testGridLayoutIntersectingConstraintsException()
	{
		tester.startPage(new LayoutTestPage(new GridLayout(3, 3))
		{
			@Override
			protected void addConstraints()
			{
				label1.add(new GridLayoutConstraint(0, 0).setColSpan(2).setRowSpan(2));
				label2.add(new GridLayoutConstraint(1, 1));
			}
		});

		final APanel aPanel = (APanel)tester.getComponentFromLastRenderedPage("aPanel");
		final StringBufferResourceStream resourceStream = (StringBufferResourceStream)aPanel.getMarkupResourceStream(
			aPanel, APanel.class);
		System.out.println(resourceStream.asString());
	}

	@Test(expected = WicketRuntimeException.class)
	public void testGridLayoutOutOfCellsException()
	{
		tester.startPage(new LayoutTestPage(new GridLayout(2, 1)));
	}

	/**
	 * Tests that components are places like this:
	 * <table border=1>
	 * <tr>
	 * <td>label3</td>
	 * <td>label2</td>
	 * </tr>
	 * <tr>
	 * <td>label1</td>
	 * <td>label4</td>
	 * </tr>
	 * </table>
	 */
	@Test
	public void testGridLayoutWithAutoConstraints()
	{
		tester.startPage(new LayoutTestPage(new GridLayout(2, 2))
		{
			@Override
			protected void addConstraints()
			{
// label1.add(new GridLayoutConstraint(0, 1)); // auto added
				label2.add(new GridLayoutConstraint(1, 0));
				label3.add(new GridLayoutConstraint(0, 0));
// label3.add(new GridLayoutConstraint(1, 1)); // auto added
			}
		});

		testComponents();

		testGeneratedMarkup("<wicket:panel>" + "<table>" + "<tr>"
			+ "<td><span wicket:id=\"label3\"></span></td>"
			+ "<td><span wicket:id=\"label2\"></span></td>" + "</tr><tr>"
			+ "<td><span wicket:id=\"label1\"></span></td>"
			+ "<td><span wicket:id=\"label4\"></span></td>" + "</tr>" + "</table>"
			+ "</wicket:panel>");
	}

	/**
	 * Tests that components are places like this:
	 * <table border=1>
	 * <tr>
	 * <td colspan=2>label1</td>
	 * </tr>
	 * <tr>
	 * <td rowspan=2>label2</td>
	 * <td>label3</td>
	 * </tr>
	 * <tr>
	 * <td>label4</td>
	 * </tr>
	 * </table>
	 */
	@Test
	public void testGridLayoutWithSpan()
	{
		tester.startPage(new LayoutTestPage(new GridLayout(2, 3))
		{
			@Override
			protected void addConstraints()
			{
				label1.add(new GridLayoutConstraint(0, 0).setColSpan(2));
				label2.add(new GridLayoutConstraint(0, 1).setRowSpan(2));
				label3.add(new GridLayoutConstraint(1, 1));
// label4.add(new GridLayoutConstraint(1, 2)); // auto added
			}
		});

		testComponents();

		testGeneratedMarkup("<wicket:panel>" + "<table>" + "<tr>"
			+ "<td colspan=\"2\"><span wicket:id=\"label1\"></span></td>" + "</tr><tr>"
			+ "<td rowspan=\"2\"><span wicket:id=\"label2\"></span></td>"
			+ "<td><span wicket:id=\"label3\"></span></td>" + "</tr><tr>"
			+ "<td><span wicket:id=\"label4\"></span></td>" + "</tr>" + "</table>"
			+ "</wicket:panel>");
	}
}
