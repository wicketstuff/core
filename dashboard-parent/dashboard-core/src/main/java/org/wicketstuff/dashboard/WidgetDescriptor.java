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

import java.io.Serializable;

/**
 * Widget definition descriptor.
 * Define the nature of a widget.
 * 
 * @author Decebal Suiu
 */
public interface WidgetDescriptor extends Serializable {

	/**
	 * Returns a UUID string that represents the widget type name.
	 */
	public String getTypeName();
	
	/**
	 * Returns the widget name that user will see in the list of available widgets. 
	 */
	public String getName();
	
	public String getProvider();
	
	public String getDescription();
	
	/**
	 * Returns the classname of the widget.
	 */
	public String getWidgetClassName();
	
}
