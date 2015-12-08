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

/**
 * Default {@link WidgetRegistry} implementation.
 * Override this class if you need to tire registry with some underling storage
 * 
 * @author Decebal Suiu
 */
public class DefaultWidgetRegistry implements WidgetRegistry {

	private List<WidgetDescriptor> widgetDescriptors;
	
	public DefaultWidgetRegistry() {
		widgetDescriptors = new ArrayList<WidgetDescriptor>();
	}
	
	@Override
	public List<WidgetDescriptor> getWidgetDescriptors() {
		return widgetDescriptors;
	}

	public void setWidgetDescriptors(List<WidgetDescriptor> widgetDescriptors) {
		this.widgetDescriptors = widgetDescriptors;
	}

	@Override
	public WidgetRegistry registerWidget(WidgetDescriptor widgetDescriptor) {
		if (widgetDescriptor != null) {
			widgetDescriptors.add(widgetDescriptor);
		}
		
		return this;
	}

	@Override
	public WidgetDescriptor getWidgetDescriptorByClassName(String widgetClassName) {
		for (WidgetDescriptor widgetDescriptor : widgetDescriptors) {
			if (widgetDescriptor.getWidgetClassName().equals(widgetClassName)) {
				return widgetDescriptor;
			}
		}
		
		return null;
	}
	
	@Override	
	public WidgetDescriptor getWidgetDescriptorByTypeName(String widgetTypeName) {
		for (WidgetDescriptor widgetDescriptor : widgetDescriptors) {
			if (widgetDescriptor.getTypeName().equals(widgetTypeName)) {
				return widgetDescriptor;
			}
		}
		
		return null;		
	}

}
