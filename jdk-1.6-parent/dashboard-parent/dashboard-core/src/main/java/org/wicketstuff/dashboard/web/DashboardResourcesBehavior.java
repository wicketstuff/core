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
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.IHeaderResponse;

/**
 * @author Decebal Suiu
 */
public class DashboardResourcesBehavior extends Behavior {

	private static final long serialVersionUID = 1L;

	@Override
	public void renderHead(Component component, IHeaderResponse response) {
		super.renderHead(component, response);
		
		DashboardSettings settings = DashboardSettings.get();
		
		if (settings.isIncludeJQuery()) {
			response.renderJavaScriptReference(settings.getJQueryReference());
		}

		if (settings.isIncludeJQueryUI()) {
			response.renderJavaScriptReference(settings.getJQueryUIReference());
		}

		if (settings.isIncludeJQueryJson()) {
			response.renderJavaScriptReference(settings.getJQueryJsonReference());
		}

		if (settings.isIncludeJavaScript()) {
			response.renderJavaScriptReference(settings.getJavaScriptReference());
		}
		
		if (settings.isIncludeCss()) {
		    response.renderCSSReference(settings.getCssReference());
		}
	}

}
