/*
 * Copyright 2011 inaiat.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.jqplot.behavior;

import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.ResourceReference;

import br.com.digilabs.jqplot.JqPlotUtil;
import br.com.digilabs.jqplot.chart.Chart;


/**
 * 
 * @author inaiat
 */
public class JqPlotBehavior extends Behavior
{

	private static final long serialVersionUID = -8088313975214875631L;

	private static final ResourceReference JQPLOT_JS = new JavaScriptResourceReference(
			JqPlotBehavior.class, "jquery.jqplot.min.js");
	private static final ResourceReference JQPLOT_CSS = new  CssResourceReference(JqPlotBehavior.class,
			"jquery.jqplot.min.css");
	private final List<String> resources;

	private Chart<?> chart;
	private String divId;

	public JqPlotBehavior(Chart<?> chart, String divId)
	{
		this.chart = chart;
		this.divId = divId;
		this.resources = JqPlotUtil.retriveJavaScriptResources(chart);
	}

	@Override
	public void renderHead(Component component, IHeaderResponse response)
	{
		super.renderHead(component, response);
		response.renderJavaScriptReference(JQPLOT_JS);
		response.renderCSSReference(JQPLOT_CSS);
		for (String resource : resources)
		{
			response.renderJavaScriptReference(new JavaScriptResourceReference(JqPlotBehavior.class, resource));
		}
		String json = JqPlotUtil.createJquery(chart, divId);
		response.renderJavaScript(json, null);

	}


}
