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

import java.lang.reflect.Constructor;
import java.util.UUID;

/**
 * Default {@link WidgetFactory} which creates a widget by specified widget class name
 * @author Decebal Suiu
 */
public class DefaultWidgetFactory implements WidgetFactory {

	@SuppressWarnings("unchecked")
	public Widget createWidget(WidgetDescriptor widgetDescriptor) {
		String widgetClassName = widgetDescriptor.getWidgetClassName();
		try {
			Class<Widget> widgetClass = (Class<Widget>) Class.forName(widgetClassName);
			Constructor<Widget> constructor = widgetClass.getConstructor();
			Widget widget = constructor.newInstance();
			String widgetId = createWidgetId(widgetDescriptor, widget);
			widget.setId(widgetId);
			widget.init();
			
			return widget;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	protected String createWidgetId(WidgetDescriptor widgetDescriptor, Widget widget) {
		return UUID.randomUUID().toString();
	}
	
}
