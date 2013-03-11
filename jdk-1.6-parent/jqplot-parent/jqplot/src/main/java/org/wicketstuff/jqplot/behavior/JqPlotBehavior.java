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
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

import br.com.digilabs.jqplot.Chart;
import br.com.digilabs.jqplot.JqPlotUtils;

/**
 * 
 * @author inaiat
 */
public class JqPlotBehavior extends Behavior {

	private static final long serialVersionUID = -8088313975214875631L;

	private final List<String> resources;

	private Chart<?> chart;
	private String divId;

	public JqPlotBehavior(Chart<?> chart, String divId) {
		this.chart = chart;
		this.divId = divId;
		this.resources = JqPlotUtils.retriveJavaScriptResources(chart);
	}

	@Override
	public void renderHead(Component component, IHeaderResponse response) {
		super.renderHead(component, response);
		response.render(JavaScriptHeaderItem.forReference(JqPlotJavascriptResourceReference.get()));
		response.render(CssHeaderItem.forReference(JqPlotCssResourceReference.get()));
		for (String resource : resources) {
			response.render(JavaScriptHeaderItem.forReference(new JavaScriptResourceReference(JqPlotBehavior.class, resource)));
		}
		String json = JqPlotUtils.createJquery(chart, divId);
		response.render(JavaScriptHeaderItem.forScript(json, null));

	}

}
