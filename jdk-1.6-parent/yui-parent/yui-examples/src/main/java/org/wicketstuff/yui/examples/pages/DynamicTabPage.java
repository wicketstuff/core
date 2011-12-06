/*
 * $Id: SliderPage.java 4820 2006-03-08 08:21:01Z eelco12 $ $Revision: 4820 $
 * $Date: 2006-03-08 16:21:01 +0800 (Wed, 08 Mar 2006) $
 * 
 * ==============================================================================
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.wicketstuff.yui.examples.pages;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.wicketstuff.yui.examples.WicketExamplePage;
import org.wicketstuff.yui.markup.html.tabs.dynamictabbedpanel.DynamicAjaxTabbedPanel;

/**
 * Dynamic Tab page using Dyanmic Tabs exteded from TabbedPanel
 * 
 * @author Josh
 */
@SuppressWarnings("serial")
public class DynamicTabPage extends WicketExamplePage
{
	private List<ITab> tabs = new ArrayList<ITab>();

	/**
	 * Construct.
	 */
	public DynamicTabPage()
	{
		add(new DynamicAjaxTabbedPanel("dyntab", tabs)
		{

			@Override
			protected ITab newTab(String label)
			{
				return new AbstractTab(new Model<String>(label))
				{

					@Override
					public Panel getPanel(String panelId)
					{
						return new DynamicPanel(panelId);
					}
				};
			}
		});
	}

	private class DynamicPanel extends DynamicAjaxTabbedPanel
	{

		public DynamicPanel(String id)
		{
			super(id, new ArrayList<ITab>());
		}

		@Override
		protected ITab newTab(String label)
		{
			return new AbstractTab(new Model<String>(label))
			{

				@Override
				public Panel getPanel(String panelId)
				{
					return new EmptyPanel(panelId);
				}
			};
		}
	}
}
