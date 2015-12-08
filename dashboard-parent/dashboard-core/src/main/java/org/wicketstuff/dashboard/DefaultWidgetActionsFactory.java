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

import java.util.ArrayList;
import java.util.List;

import org.wicketstuff.dashboard.web.DefaultWidgetActions;

/**
 * Default {@link WidgetActionsFactory} which provide common actions like:
 * <ul>
 * <li>Refresh</li>
 * <li>View settings</li>
 * <li>Delete an widget from a dashboard</li>
 * </ul>
 * @author Decebal Suiu
 */
public class DefaultWidgetActionsFactory implements WidgetActionsFactory {

	@Override
	public List<WidgetAction> createWidgetActions(Widget widget) {
		List<WidgetAction> widgetActions = new ArrayList<WidgetAction>();
		widgetActions.add(new DefaultWidgetActions.Refresh(widget));
		if (widget.hasSettings()) {
			widgetActions.add(new DefaultWidgetActions.Settings(widget));
		}
		widgetActions.add(new DefaultWidgetActions.Delete(widget));

		return widgetActions;
	}

}
