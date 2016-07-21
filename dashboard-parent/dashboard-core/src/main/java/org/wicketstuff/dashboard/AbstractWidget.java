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

import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.markup.html.panel.Panel;

/**
 * Abstract stub implementation for {@link Widget} to simplify defining of actual widgets
 * @author Decebal Suiu
 */
public abstract class AbstractWidget implements Widget {
	
	private static final long serialVersionUID = 1L;
	
	protected String id;
	protected String title;
	protected boolean collapsed;
    protected Map<String, String> settings;
    protected WidgetLocation location;

    public AbstractWidget() {
		settings = new HashMap<String, String>();
		location = new WidgetLocation();
	}
    
    @Override
    public String getId() {
		return id;
	}

    @Override
	public void setId(String id) {
		this.id = id;
	}

    @Override
	public String getTitle() {
		return title;
	}

    @Override
	public void setTitle(String title) {
		this.title = title;
	}

    @Override
	public boolean isCollapsed() {
		return collapsed;
	}

    @Override
	public void setCollapsed(boolean collapsed) {
		this.collapsed = collapsed;
//		getInternalSettings().put(COLLAPSED, Boolean.toString(collapsed));
	}
	
	@Override
	public WidgetLocation getLocation() {
		return location;
	}

	@Override
	public void setLocation(WidgetLocation location) {
		this.location = location;
	}

	public void init() {
		// do nothing
	}
	
    @Override
	public boolean hasSettings() {
		return false;
	}

    @Override
	public Map<String, String> getSettings() {
		return settings;
	}

    @Override
	public void setSettings(Map<String, String> settings) {
		this.settings = settings;
	}

    @Override
	public Panel createSettingsPanel(String settingsPanelId) {
		return null;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) {
        	return true;
        }
        
        if (o == null || getClass() != o.getClass()) {
        	return false;
        }

        AbstractWidget widget = (AbstractWidget) o;
        
        if (!title.equals(widget.title)) {
        	return false;
        }
        
        if (!id.equals(widget.id)) {
        	return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        result = id.hashCode();
        result = 31 * result + title.hashCode();
        return result;
    }
    
}
