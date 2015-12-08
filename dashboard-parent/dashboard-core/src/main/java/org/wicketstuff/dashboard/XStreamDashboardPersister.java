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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Collections;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * Default {@link DashboardPersister}
 * Persists a dashboard in specified file.
 * If you need to have different logic for storing and loading of {@link Dashboard}s: implement your own {@link DashboardPersister}
 * @author Decebal Suiu
 */
public class XStreamDashboardPersister implements DashboardPersister {
	
	private File file;
	private XStream xstream;
	
	public XStreamDashboardPersister(File file) {
		this.file = file;
		
        xstream = new XStream(new DomDriver("UTF-8"));
        xstream.setMode(XStream.NO_REFERENCES);
        xstream.alias("dashboard", DefaultDashboard.class);
//        xstream.alias("textWidget", TextWidget.class);
//        xstream.alias("chartWidget", ChartWidget.class);
	}
	
	@Override
	public Dashboard load() {
		if (!file.exists() || !file.isFile()) {
			return null;
		}
		
		try {
			return (Dashboard) xstream.fromXML(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void save(Dashboard dashboard) {
		// sort widgets
		Collections.sort(dashboard.getWidgets(), new WidgetComparator());
		
		try {
			xstream.toXML(dashboard, new FileOutputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
