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

/**
 * Central registry for {@link WidgetDescriptor}s
 * @author Decebal Suiu
 */
public interface WidgetRegistry {

	/**
	 * Register new type of widgets described by {@link WidgetDescriptor}
	 * @param widgetDescriptor descriptor to be registered
	 * @return this {@link WidgetRegistry}
	 */
	public WidgetRegistry registerWidget(WidgetDescriptor widgetDescriptor);
	
	/**
	 * Returns a list of registered widgets
	 * @return list of registered widget descriptors
	 */
	public List<WidgetDescriptor> getWidgetDescriptors();
	
	/**
	 * Lookup {@link WidgetDescriptor} by widget class
	 * @param widgetClassName widget class name
	 * @return registered {@link WidgetDescriptor} with specified widget class or null if {@link WidgetDescriptor} was not found
	 */
	public WidgetDescriptor getWidgetDescriptorByClassName(String widgetClassName);
	
	/**
	 * Lookup {@link WidgetDescriptor} by widget type
	 * @param widgetTypeName widget type
	 * @return registered {@link WidgetDescriptor} with specified widget type or null if {@link WidgetDescriptor} was not found
	 */
	public WidgetDescriptor getWidgetDescriptorByTypeName(String widgetTypeName);
	
}
