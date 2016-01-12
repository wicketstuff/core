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
import java.util.List;

/**
 * Dashboards stores collection of {@link Widget}s and allow to manipulatet them
 * @author Decebal Suiu
 */
public interface Dashboard extends Serializable {

	public String getId();
	
	public String getTitle();
	
	public void setTitle(String title);
	
	public int getColumnCount();   

	public void setColumnCount(int columnCount);
	
	public List<Widget> getWidgets();
	
	public List<Widget> getWidgets(int column);
	
	public Widget getWidget(String widgetId);		
	
	public void addWidget(Widget widget);
	
	public void deleteWidget(String widgetId);

}
