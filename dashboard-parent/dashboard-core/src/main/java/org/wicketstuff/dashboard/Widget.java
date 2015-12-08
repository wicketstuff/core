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
import java.util.Map;

import org.apache.wicket.markup.html.panel.Panel;
import org.wicketstuff.dashboard.web.WidgetView;

/**
 * @author Decebal Suiu
 */
public interface Widget extends Serializable {

	public String getId();
	
	public void setId(String id);
	
	public String getTitle();
	
	public void setTitle(String title);
	
	public WidgetLocation getLocation();
	
	public void setLocation(WidgetLocation location);
	
	public WidgetView createView(String viewId);
	
	public boolean isCollapsed();
	
	public void setCollapsed(boolean collapsed);
	
	public void init();
	
	public boolean hasSettings();
	
	public Map<String, String> getSettings();
	
	public void setSettings(Map<String, String> settings);
	
	public Panel createSettingsPanel(String settingsPanelId);
	
}
