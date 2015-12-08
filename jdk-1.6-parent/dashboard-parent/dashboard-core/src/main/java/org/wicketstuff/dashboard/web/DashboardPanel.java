/*
 * Copyright 2012 Decebal Suiu
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this work except in compliance with
 * the License. You may obtain a copy of the License in the LICENSE file, or at:
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.wicketstuff.dashboard.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.list.Loop;
import org.apache.wicket.markup.html.list.LoopItem;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.wicketstuff.dashboard.Dashboard;

/**
 * @author Decebal Suiu
 */
public class DashboardPanel extends GenericPanel<Dashboard> implements DashboardContextAware {
	
	private static final long serialVersionUID = 1L;

	private transient DashboardContext dashboardContext;
	
	private List<DashboardColumnPanel> columnPanels;
	
	public DashboardPanel(String id, IModel<Dashboard> model) {
		super(id, model);			

		addColumnsPanel();
		
		add(new DashboardResourcesBehavior());
	}
		
	public Dashboard getDashboard() {
		return getModelObject();
	}
	
	@Override
	public void setDashboardContext(DashboardContext dashboardContext) {
		this.dashboardContext = dashboardContext;
	}

	/**
	 * Used by children.
	 */
	public DashboardContext getDashboardContext() {
		return dashboardContext;
	}

	private void addColumnsPanel() {
		final int columnCount = getDashboard().getColumnCount();
		Loop columnsView = new Loop("columns", columnCount) {
			
			private static final long serialVersionUID = 1L;

			@Override
			protected void onBeforeRender() {
				if (!hasBeenRendered()) {
					columnPanels = new ArrayList<DashboardColumnPanel>();
				}
				
				super.onBeforeRender();
			}

			@Override
			protected void populateItem(LoopItem item) {
			    float columnPanelWidth = 100 / columnCount;
		    	DashboardColumnPanel columnPanel = new DashboardColumnPanel("column", getModel(), item.getIndex());
		    	columnPanel.setRenderBodyOnly(true);
		    	columnPanel.getColumnContainer().add(AttributeModifier.replace("style", "width: " + columnPanelWidth + "%;"));		    	
		    	item.add(columnPanel);
		    	
		    	columnPanels.add(columnPanel);
			}
			
		};
		add(columnsView);
	}

}
