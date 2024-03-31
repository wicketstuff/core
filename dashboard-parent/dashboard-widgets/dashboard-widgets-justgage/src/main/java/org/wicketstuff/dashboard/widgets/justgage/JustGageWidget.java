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
package org.wicketstuff.dashboard.widgets.justgage;

import org.apache.wicket.model.Model;
import org.wicketstuff.dashboard.AbstractWidget;
import org.wicketstuff.dashboard.Widget;
import org.wicketstuff.dashboard.web.WidgetView;

/**
 * @author Decebal Suiu
 */
public class JustGageWidget extends AbstractWidget {

	private static final long serialVersionUID = 1L;
		
	private static JustGageFactory justGageFactory;
	
	public JustGageWidget() {
		super();
		
		title = "JustGage";
	}

	public static JustGageFactory getChartFactory() {
		return justGageFactory;
	}

	public static void setJustGageFactory(JustGageFactory justGageFactory) {
		JustGageWidget.justGageFactory = justGageFactory;
	}

	public JustGage getJustGage() {
		if (justGageFactory == null) {
			throw new RuntimeException("JustGageFactory cannot be null. Use JustGageWidget.setJustGageFactory(...)");
		}
		
		return justGageFactory.createJustGage(this);
	}

	public WidgetView createView(String viewId) {
		return new JustGageWidgetView(viewId, new Model<Widget>(this));
	}

}
