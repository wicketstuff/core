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
package org.wicketstuff.dashboard.examples;

import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.dashboard.Dashboard;
import org.wicketstuff.dashboard.Widget;

/**
 * @author Decebal Suiu
 */
public class WidgetPage extends WebPage {

	private static final long serialVersionUID = 1L;

	public WidgetPage(PageParameters parameters) {
		super(parameters);

		String widgetId = parameters.get("id").toString();
		Dashboard dashboard = WicketApplication.get().getDashboard();
		Widget widget = dashboard.getWidget(widgetId);
		add(widget.createView("widget"));
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		response.render(CssHeaderItem.forUrl("css/style.css"));
	}
}
