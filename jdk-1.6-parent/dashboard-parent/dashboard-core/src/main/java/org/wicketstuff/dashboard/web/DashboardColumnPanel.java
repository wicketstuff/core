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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.template.PackageTextTemplate;
import org.wicketstuff.dashboard.Dashboard;
import org.wicketstuff.dashboard.DashboardUtils;
import org.wicketstuff.dashboard.Widget;
import org.wicketstuff.dashboard.WidgetLocation;

/**
 * @author Decebal Suiu
 */
class DashboardColumnPanel extends GenericPanel<Dashboard> {
	
	private static final long serialVersionUID = 1L;	
		
	private StopSortableAjaxBehavior stopSortableAjaxBehavior;
	
	public DashboardColumnPanel(String id, final IModel<Dashboard> dashboardModel, int columnIndex) {
		super(id, dashboardModel);
		
	   	WebMarkupContainer columnContainer = new WebMarkupContainer("columnContainer");
	   	columnContainer.setOutputMarkupId(true);
	   	columnContainer.setMarkupId("column-" + columnIndex);

		final List<Widget> widgets = getModelObject().getWidgets(columnIndex);
		ListView<Widget> listView = new ListView<Widget>("widgetList", widgets) {
			 
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<Widget> item) {
				final Widget widget = item.getModelObject();
				// TODO problems if I add WidgetLoadingPanel
//				if (widget.isCollapsed()) {
					IModel<Widget> widgetModel = new WidgetModel(dashboardModel, widget.getId());
					WidgetPanel widgetPanel = createWidgetPanel("widget", widgetModel);					
					item.add(widgetPanel);					
//				} else {
//					item.add(new WidgetLoadingPanel("widget", new WidgetModel(widget.getId())));
//				}
				
				item.setOutputMarkupId(true);
				item.setMarkupId("widget-" + widget.getId());
			}

		};	
				
		columnContainer.add(listView);
		add(columnContainer);
		
		if (columnIndex == 0) {
			add(new AttributeModifier("style", "margin-left: 0px"));
		}
	}

	public WebMarkupContainer getColumnContainer() {
		return (WebMarkupContainer) get("columnContainer");
	}
	
	public Dashboard getDashboard() {
		return getModelObject();
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		
		addSortableBehavior(getColumnContainer());
	}
	
	private WidgetPanel createWidgetPanel(String id, IModel<Widget> model) {
		return new WidgetPanel(id, model);
	}

	private void addSortableBehavior(Component component) {
		stopSortableAjaxBehavior = new StopSortableAjaxBehavior() {

			private static final long serialVersionUID = 1L;

			@Override
			public void saveLayout(Map<String, WidgetLocation> widgetLocations, AjaxRequestTarget target) {
				Dashboard dashboard = getDashboard();
				DashboardUtils.updateWidgetLocations(dashboard, widgetLocations);
				DashboardContext dashboardContext = findParent(DashboardPanel.class).getDashboardContext();
				dashboardContext.getDashboardPersiter().save(dashboard);
			}
			
		};

		/*
		sortableBehavior.setConnectWith(".column");
		sortableBehavior.setHandle(".dragbox-header");
		sortableBehavior.setCursor("move");
		sortableBehavior.setForcePlaceholderSize(true);
		sortableBehavior.setPlaceholder("placeholder");
		sortableBehavior.setOpacity(0.4f);
		*/
		
		component.add(stopSortableAjaxBehavior);
	}

	@Override
	public void renderHead(IHeaderResponse response) {
        super.renderHead(response);

        CharSequence script = stopSortableAjaxBehavior.getSuccessScript();

        Map<String, String> vars = new HashMap<String, String>();
        vars.put("component", get("columnContainer").getMarkupId());
        vars.put("stopBehavior", script.toString());

        PackageTextTemplate template = new PackageTextTemplate(DashboardColumnPanel.class, "res/sort-behavior.template.js");
        template.interpolate(vars);

        response.renderOnDomReadyJavaScript(template.getString());
    }
    
	/*
	private class WidgetLoadingPanel extends AjaxLazyLoadPanel {

		private static final long serialVersionUID = 1L;

		public WidgetLoadingPanel(String id, IModel<Widget> model) {
			super(id, model);			
		}

		@Override
        public Component getLazyLoadComponent(String id) {
			return createWidgetPanel(id, (WidgetModel) this.getDefaultModel());
        }
        
		@Override
        public Component getLoadingComponent(final String markupId) {	
			Widget widget = (Widget) this.getDefaultModelObject();
			int height = 330;
						
			return new Label(markupId, 
				 "<div class=\"dragbox\" style=\"width:99%;height:" + height + "px;margin-right: .9%;\">" +
				 "<h3 style=\"text-align:center;padding-top:" +  (height/2-10) + "px;\">Widget '" + widget.getTitle() +
				 "' is loading ..." +
				 "</h3></div>").
			   setEscapeModelStrings(false);
		}
		
	}
	*/
	
}
