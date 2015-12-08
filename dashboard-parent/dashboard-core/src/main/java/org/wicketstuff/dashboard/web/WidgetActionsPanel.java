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

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.wicketstuff.dashboard.Widget;
import org.wicketstuff.dashboard.WidgetAction;

import java.util.List;

/**
 * @author Decebal Suiu
 */
public class WidgetActionsPanel extends GenericPanel<Widget> implements DashboardContextAware {

	private static final long serialVersionUID = 1L;

	private transient DashboardContext dashboardContext;

	public WidgetActionsPanel(String id, IModel<Widget> model) {
		super(id, model);

		IModel<List<WidgetAction>> actionsModel = new LoadableDetachableModel<List<WidgetAction>>() {

			private static final long serialVersionUID = 1L;

			@Override
			protected List<WidgetAction> load() {
				return dashboardContext.getWidgetActionsFactory().createWidgetActions(getWidget());
			}

		};
		ListView<WidgetAction> actionsView = new ListView<WidgetAction>("action", actionsModel) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<WidgetAction> item) {
				WidgetAction action = item.getModelObject();
				AbstractLink link = action.getLink("link");
				link.add(action.getImage("image"));
				link.add(AttributeModifier.replace("title", action.getTooltip()));
				item.add(link);
			}

		};
		add(actionsView);
	}

	@Override
	public void setDashboardContext(DashboardContext dashboardContext) {
		this.dashboardContext = dashboardContext;
	}

	private Widget getWidget() {
		return getModelObject();
	}

}
