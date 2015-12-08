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
package org.wicketstuff.dashboard.widgets.jqplot;

import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.resource.JQueryPluginResourceReference;
import org.wicketstuff.jqplot.behavior.JqPlotBehavior;

import br.com.digilabs.jqplot.Chart;
import br.com.digilabs.jqplot.JqPlotUtils;

/**
 * @author Decebal Suiu
 */
public class ChartContainer extends WebMarkupContainer {

	private static final long serialVersionUID = 1L;

	public ChartContainer(String id, IModel<? extends Chart<?>> model) {
		super(id, model);

		setOutputMarkupId(true);
	}

	private String createJquery() {
		Chart<?> chart = getChart();
		
		StringBuilder builder = new StringBuilder();
		builder.append("$.jqplot('").append(getMarkupId()).append("', ");
		builder.append(chart.getChartData().toJsonString());
		builder.append(", ");
		builder.append(JqPlotUtils.jqPlotToJson(chart.getChartConfiguration()));
		builder.append(");\r\n");

		return builder.toString();
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		add(getJqPlotBehavior());
	}

	private Chart<?> getChart() {
		return (Chart<?>) getDefaultModelObject();
	}
	
	private Behavior getJqPlotBehavior() {
		return new Behavior() {

			private static final long serialVersionUID = 1L;

			@Override
			public void renderHead(Component component, IHeaderResponse response) {
				super.renderHead(component, response);
				
				response.render(JavaScriptHeaderItem.forReference(new JQueryPluginResourceReference(JqPlotBehavior.class, "jquery.jqplot.min.js")));
				response.render(CssHeaderItem.forReference(new CssResourceReference(JqPlotBehavior.class, "jquery.jqplot.min.css")));
				List<String> resources = JqPlotUtils.retriveJavaScriptResources(getChart());
				for (String resource : resources) {
					response.render(JavaScriptHeaderItem.forReference(new JavaScriptResourceReference(JqPlotBehavior.class, resource)));
				}
				String json = createJquery();
				response.render(OnDomReadyHeaderItem.forScript(json));
			}

		};
	}

}
