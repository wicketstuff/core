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
package org.wicketstuff.dashboard.web;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.wicketstuff.dashboard.Widget;

/**
 * Root for all wicket {@link Component}s which are being used for rendering of widget's content
 * @author Decebal Suiu
 */
public class WidgetView extends GenericPanel<Widget> {

	private static final long serialVersionUID = 1L;	
	
	public WidgetView(String id, IModel<Widget> model) {
		super(id, model);

		setOutputMarkupPlaceholderTag(true);		
	}
	
	public Widget getWidget() {
		return getModelObject();
	}

	@Override
	protected void onConfigure() {
		super.onConfigure();
		
		setVisible(!getWidget().isCollapsed());
	}	
	
}
