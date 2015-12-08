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
 * @author Decebal Suiu
 */
public class DefaultWidgetFactory implements WidgetFactory {

	@SuppressWarnings("unchecked")
	public Widget createWidget(WidgetDescriptor widgetDescriptor) {
		String widgetClassName = widgetDescriptor.getWidgetClassName();
		try {
			Class<Widget> widgetClass = (Class<Widget>) Class.forName(widgetClassName);
			Class<?>[] types = new Class[] { String.class };
			Object[] arguments = new String[] { UUID.randomUUID().toString() };
//			Constructor<Widget> constructor = widgetClass.getConstructor();
			Constructor<Widget> constructor = widgetClass.getConstructor(types);
//			Widget widget = constructor.newInstance();
			Widget widget = constructor.newInstance(arguments);
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

}
