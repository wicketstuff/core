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

import java.util.List;
import java.util.Map;

import org.wicketstuff.dashboard.web.DashboardEvent;

/**
 * @author Decebal Suiu
 */
public class DashboardUtils {
	
	@SuppressWarnings("unchecked")
	public static void updateWidgetLocations(Dashboard dashboard, DashboardEvent dashboardEvent) {
		DashboardEvent.EventType eventType = dashboardEvent.getType();
		if (DashboardEvent.EventType.WIDGET_ADDED == eventType) {
			List<Widget> widgets = dashboard.getWidgets(0);
	        for (Widget widget : widgets) {
	        	widget.getLocation().incrementRow();
	        }
		} else if (DashboardEvent.EventType.WIDGET_REMOVED == eventType) {
			Widget widgetRemoved = (Widget) dashboardEvent.getDetail();
			WidgetLocation widgetRemovedLocation = widgetRemoved.getLocation();
			List<Widget> widgets = dashboard.getWidgets(widgetRemovedLocation.getColumn());
	        for (Widget widget : widgets) {
	        	WidgetLocation widgetLocation = widget.getLocation();
	        	if (widgetLocation.getRow() > widgetRemovedLocation.getRow()) {
	        		widget.getLocation().decrementRow();
	        	}
	        }			
		} else if (DashboardEvent.EventType.WIDGETS_SORTED == eventType) {
			Map<String, WidgetLocation> widgetLocations = (Map<String, WidgetLocation>) dashboardEvent.getDetail();
			List<Widget> widgets = dashboard.getWidgets();
	        for (Widget widget : widgets) {
	            String id = widget.getId();
	            WidgetLocation location = widgetLocations.get(id);
	            if (!location.equals(widget.getLocation())) {
	                widget.setLocation(location);
	            }                         
	        }
		}
	}

}
