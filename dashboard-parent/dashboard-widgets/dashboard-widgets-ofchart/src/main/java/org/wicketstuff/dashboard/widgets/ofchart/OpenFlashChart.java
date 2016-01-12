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
package org.wicketstuff.dashboard.widgets.ofchart;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.wicketstuff.dashboard.Widget;

/**
 * http://cwiki.apache.org/WICKET/open-flash-chart-and-wicket.html
 */
public class OpenFlashChart extends GenericPanel<Widget> {	

	private static final long serialVersionUID = 1L;
	
	private String width;
	private String height;
	private SWFObject swf;

	public OpenFlashChart(String id, String width, String height, IModel<Widget> model) {
		super(id, model);

		this.width = width;
		this.height = height;
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		
		response.render(JavaScriptHeaderItem.forReference(new PackageResourceReference(OpenFlashChart.class, "res/saveChartImage.js")));
	}
		
	@Override
	protected void onInitialize() {
		super.onInitialize();
		
		String swfUrl = toAbsolutePath(urlFor(new PackageResourceReference(OpenFlashChart.class, "res/open-flash-chart.swf"), null).toString());
		
		// see http://ofc2dz.com/OFC2/downloads/ofc2Downloads.html
		// http://ofc2dz.com/OFC2/examples/MiscellaneousPatches.html (Passing the Char Parameter "ID" when saving images (23-Feb-2009))
		swfUrl = swfUrl.concat("?id=").concat(getMarkupId());
//		System.out.println("swfUrl = " + swfUrl);
		swf = new SWFObject(swfUrl, width, height, "9.0.0");
		add(swf);
	}
	
	@Override
	protected void onBeforeRender() {
		String jsonUrl = getUrlForJson();
//		System.out.println("jsonUrl = " + jsonUrl);
		swf.addParameter("data-file", jsonUrl); 
		swf.addParameter("wmode", "transparent");
		 
		super.onBeforeRender();
	}

	private String getUrlForJson() {
		PageParameters parameters = new PageParameters();
		parameters.add("widgetId", getModelObject().getId());
		String jsonUrl = urlFor(new DataResourceReference(), parameters).toString();
		
		return toAbsolutePath(jsonUrl);
	}

	private String toAbsolutePath(String relativePath) {
		return getRequestCycle().getUrlRenderer().renderFullUrl(Url.parse(relativePath));
	}	
	
}
