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
package org.wicketstuff.dashboard;

import java.io.Serializable;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxCallDecorator;
import org.apache.wicket.ajax.calldecorator.AjaxCallDecorator;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.wicketstuff.dashboard.web.DashboardContext;
import org.wicketstuff.dashboard.web.DashboardPanel;
import org.wicketstuff.dashboard.web.WidgetPanel;
import org.wicketstuff.dashboard.web.WidgetView;
import org.wicketstuff.dashboard.web.util.ConfirmAjaxDecoratorDelegate;

/**
 * @author Decebal Suiu
 */
public abstract class WidgetAction implements Serializable {

	private static final long serialVersionUID = 1L;

	protected Widget widget;	
	protected String label;
	protected String image;
	protected String tooltip;

	public WidgetAction(Widget widget) {
		this.widget = widget;
	}
	
	public abstract AbstractLink getLink(String id);

 
	public String getLabel() {
		return label;
	}

	public String getImage() {
		return image;
	}

	public String getTooltip() {
		return tooltip;
	}

	public static class Refresh extends WidgetAction {

		private static final long serialVersionUID = 1L;

		public Refresh(Widget widget) {
			super(widget);
			
			image = "images/refresh.gif";
			tooltip = "Refresh";
		}

		@Override
		public AbstractLink getLink(String id) {
			return new AjaxLink<Void>(id) {

				private static final long serialVersionUID = 1L;

				@Override
				public void onClick(AjaxRequestTarget target) {				
					WidgetView widgetView = findParent(WidgetPanel.class).getWidgetView();
					target.add(widgetView);
				}
				
			};			
		}
		
	}
	
	public static class Delete extends WidgetAction {

		private static final long serialVersionUID = 1L;

		public Delete(Widget widget) {
			super(widget);
			
			image = "images/delete.gif";
			tooltip = "Delete";
		}

		@Override
		public AbstractLink getLink(String id) {
			return new AjaxLink<Void>(id) {

				private static final long serialVersionUID = 1L;

				@Override
				public void onClick(AjaxRequestTarget target) {
					DashboardPanel dashboardPanel = findParent(DashboardPanel.class);
					Dashboard dashboard = dashboardPanel.getDashboard();
					dashboard.deleteWidget(widget.getId());
					DashboardContext dashboardContext = dashboardPanel.getDashboardContext();
					dashboardContext.getDashboardPersiter().save(dashboard);
					// the widget is removed from ui with javascript (with a IAjaxCallDecorator) -> see getAjaxCallDecorator()
				}
				
				@Override
				protected IAjaxCallDecorator getAjaxCallDecorator() {
					AjaxCallDecorator ajaxDecorator = new AjaxCallDecorator() {
						
						private static final long serialVersionUID = 1L;
						
						@Override
						public CharSequence decorateOnSuccessScript(Component c, CharSequence script) {
							return "$('#widget-" + widget.getId() + "').remove();";
						}
						
					};
					
					return new ConfirmAjaxDecoratorDelegate(ajaxDecorator, "Delete widget " + widget.getTitle() + "?");
				}
				
			};
		}
		
	}

	public static class Settings extends WidgetAction {

		private static final long serialVersionUID = 1L;

		public Settings(Widget widget) {
			super(widget);
			
			image = "images/edit.png";
			tooltip = "Settings";
		}

		@Override
		public AbstractLink getLink(String id) {
			return new AjaxLink<Void>(id) {

				private static final long serialVersionUID = 1L;

				@Override
				public void onClick(AjaxRequestTarget target) {
					if (widget.hasSettings()) {
						WidgetPanel widgetPanel = findParent(WidgetPanel.class);
						Panel settingsPanel = widgetPanel.getSettingsPanel();
						settingsPanel.setVisible(true);
						target.add(settingsPanel);
					}
				}
				
			};
		}
		
	}

}
