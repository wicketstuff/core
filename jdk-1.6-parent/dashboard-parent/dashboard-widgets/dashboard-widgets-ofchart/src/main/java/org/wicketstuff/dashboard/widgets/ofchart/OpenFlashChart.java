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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.wicket.IResourceListener;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.handler.TextRequestHandler;
import org.apache.wicket.request.resource.PackageResourceReference;

/**
 * http://cwiki.apache.org/WICKET/open-flash-chart-and-wicket.html
 */
public class OpenFlashChart extends GenericPanel<String> implements IResourceListener {	

	private static final long serialVersionUID = 1L;
	
	private String width;
	private String height;
	private SWFObject swf;

	public OpenFlashChart(String id, String width, String height, IModel<String> jsonModel) {
		super(id, jsonModel);

		this.width = width;
		this.height = height;
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		
		response.renderJavaScriptReference(new PackageResourceReference(OpenFlashChart.class, "saveChartImage.js"));
	}
		
	public void onResourceRequested() {
		IRequestHandler requestHandler = new TextRequestHandler(getModelObject());
		requestHandler.respond(getRequestCycle());
	}
	
	@Override
	protected void onInitialize() {
		super.onInitialize();
		
		String swfURL = toAbsolutePath(urlFor(new PackageResourceReference(OpenFlashChart.class, "open-flash-chart.swf"), null).toString());
		
		// see http://ofc2dz.com/OFC2/downloads/ofc2Downloads.html
		// http://ofc2dz.com/OFC2/examples/MiscellaneousPatches.html (Passing the Char Parameter "ID" when saving images (23-Feb-2009))
		swfURL = swfURL.concat("?id=").concat(getMarkupId());
//		System.out.println("swfURL = " + swfURL);
		swf = new SWFObject(swfURL, width, height, "9.0.0");
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
		CharSequence dataPath = urlFor(IResourceListener.INTERFACE, null);
		try {
			dataPath = URLEncoder.encode(dataPath.toString(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Error encoding dataPath for Chart Json data file.", e);
		}
		
		return toAbsolutePath(dataPath.toString());
	}

	private String toAbsolutePath(String relativePath) {
		return getRequestCycle().getUrlRenderer().renderFullUrl(Url.parse(relativePath));
	}
	
}
