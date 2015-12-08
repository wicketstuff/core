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

import org.apache.wicket.model.IModel;
import org.wicketstuff.dashboard.Dashboard;
import org.wicketstuff.dashboard.Widget;
import org.wicketstuff.dashboard.web.util.CascadingLoadableDetachableModel;

/**
 * @author Decebal Suiu
 */
public class WidgetModel extends CascadingLoadableDetachableModel<Widget, Dashboard> {

	private static final long serialVersionUID = 1L;

	private String widgetId;
		
	public WidgetModel(IModel<Dashboard> dashboardModel, String widgetId) {
		super(dashboardModel);
		
		this.widgetId = widgetId;
	}
	
	public String getWidgetId() {
		return widgetId;
	}

	public void setWidgetId(String widgetId) {
		this.widgetId = widgetId;
	}

	@Override
	public Widget load(Dashboard parentObject) {
		return parentObject.getWidget(widgetId);
	}
	
}
