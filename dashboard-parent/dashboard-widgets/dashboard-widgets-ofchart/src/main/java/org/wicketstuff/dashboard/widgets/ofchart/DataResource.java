/*
 * Copyright 2013 Decebal Suiu
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
package org.wicketstuff.dashboard.widgets.ofchart;

import java.io.IOException;

import org.apache.wicket.request.resource.AbstractResource;
import org.wicketstuff.dashboard.Dashboard;

/**
 * @author Decebal Suiu
 */
public class DataResource extends AbstractResource {

	private static final long serialVersionUID = 1L;
	
	private Dashboard dashboard;

	public DataResource(Dashboard dashboard) {
		this.dashboard = dashboard;
	}
	
	@Override
	protected ResourceResponse newResourceResponse(Attributes attributes) {
		System.out.println("DataResource.newResourceResponse()");
		String widgetId = attributes.getParameters().get("widgetId").toString();
		System.out.println("widgetId = " + widgetId);
		
		System.out.println("dashboard = " + dashboard);
		final ChartWidget widget = (ChartWidget) dashboard.getWidget(widgetId);
		System.out.println("widget = " + widget);
		
		ResourceResponse response = new ResourceResponse(); 
		response.setContentType("application/json"); 
		response.setWriteCallback(new WriteCallback() {

			@Override
			public void writeData(Attributes attributes) throws IOException {
				attributes.getResponse().write(widget.getChartData());
			}
			
	    }); 
		
	    return response; 
	}
	
}
