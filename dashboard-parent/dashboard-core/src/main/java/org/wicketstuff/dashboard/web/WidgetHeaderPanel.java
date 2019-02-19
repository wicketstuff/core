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

import static org.wicketstuff.dashboard.DashboardContextInitializer.getDashboardContext;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.wicketstuff.dashboard.Dashboard;
import org.wicketstuff.dashboard.Widget;

/**
 * @author Decebal Suiu
 */
public class WidgetHeaderPanel extends GenericPanel<Widget> {
	private static final long serialVersionUID = 1L;

	public WidgetHeaderPanel(String id, IModel<Widget> model) {
		super(id, model);

		setMarkupId("header-" + getModelObject().getId());

		final WebMarkupContainer toggle = new WebMarkupContainer("toggle");

		toggle.setOutputMarkupId(true).add(AttributeModifier.replace("class", getCssClass()));
		toggle.add(new AjaxEventBehavior("click") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onEvent(AjaxRequestTarget target) {
				Widget widget = getWidget();

				// change widget's collapsed property
				widget.setCollapsed(!widget.isCollapsed());

				// save the new state of widget/dashboard
				Dashboard dashboard = findParent(DashboardPanel.class).getDashboard();
				getDashboardContext().getDashboardPersister().save(dashboard);

				// change toggle's image
				target.add(toggle.add(AttributeModifier.replace("class", getCssClass())));

				// hide/show the widget's view
				WidgetView widgetView = findParent(WidgetPanel.class).getWidgetView();
				target.add(widgetView);
			}
		});
		toggle.add(new AttributeModifier("title", new IModel<String>() {
			private static final long serialVersionUID = 1L;

			@Override
			public String getObject() {
				return getWidget().isCollapsed() ? getString("expand") : getString("collapse");
			}
		}));
		add(toggle);

		add(new Label("title", new PropertyModel<Widget>(model, "title")));

		WidgetActionsPanel actionsPanel = new WidgetActionsPanel("actions", model);
		add(actionsPanel);
	}

	private String getCssClass() {
		return "dragbox-toggle " + (getWidget().isCollapsed() ? "collapsed" : "expanded");
	}

	public Widget getWidget() {
		return getModelObject();
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);

		StringBuilder statement = new StringBuilder("$('#").append(getMarkupId()).append("').on('mouseover', function(ev) {");
		statement.append(" $(this).find('.dragbox-actions').show();").
				append("}).on('mouseout', function(ev) {").
				append(" $(this).find('.dragbox-actions').hide();").
				append("});");

		response.render(OnDomReadyHeaderItem.forScript(statement.toString()));
	}
}
