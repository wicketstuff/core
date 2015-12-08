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

/**
 * @author Decebal Suiu
 */
public class DashboardUtils {
	
	public static void updateWidgetLocations(Dashboard dashboard, Map<String, WidgetLocation> widgetLocations) {
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
