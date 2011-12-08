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

import org.apache.wicket.ResourceReference;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.resources.JavascriptResourceReference;

import br.com.digilabs.jqplot.Chart;
import br.com.digilabs.jqplot.JqPlotUtils;


/**
 * 
 * @author inaiat
 */
public class JqPlotBehavior extends AbstractBehavior
{

	private static final long serialVersionUID = -8088313975214875631L;

	private static final ResourceReference JQPLOT_JS = new JavascriptResourceReference(
			JqPlotBehavior.class, "jquery.jqplot.min.js");
	private static final ResourceReference JQPLOT_CSS = new ResourceReference(JqPlotBehavior.class,
			"jquery.jqplot.min.css");
	private final List<String> resources;

	private Chart<?> chart;
	private String divId;

	public JqPlotBehavior(Chart<?> chart, String divId)
	{
		this.chart = chart;
		this.divId = divId;
		this.resources = JqPlotUtils.retriveJavaScriptResources(chart);
	}

	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);
		response.renderJavascriptReference(JQPLOT_JS);
		response.renderCSSReference(JQPLOT_CSS);
		for (String resource : resources)
		{
			response.renderJavascriptReference(new ResourceReference(JqPlotBehavior.class, resource));
		}
		String json = JqPlotUtils.createJquery(chart, divId);
		response.renderJavascript(json, null);

	}


}
