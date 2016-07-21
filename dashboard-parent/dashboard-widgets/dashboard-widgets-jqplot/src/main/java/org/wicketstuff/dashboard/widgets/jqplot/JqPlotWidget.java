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

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.wicketstuff.dashboard.AbstractWidget;
import org.wicketstuff.dashboard.Widget;
import org.wicketstuff.dashboard.web.WidgetView;

import br.com.digilabs.jqplot.Chart;

/**
 * @author Decebal Suiu
 */
public class JqPlotWidget extends AbstractWidget {

	private static final long serialVersionUID = 1L;
	
	public static final String BAR_TYPE = "Bar";
	public static final String LINE_TYPE = "Line";
	public static final String PIE_TYPE = "Pie";
	public static final String AREA_TYPE = "Area";

	public static final List<String> TYPES = Arrays.asList(new String[] {
			BAR_TYPE,
			LINE_TYPE,
			PIE_TYPE,
			AREA_TYPE
	});

	private static ChartFactory chartFactory;
	
	public JqPlotWidget() {
		super();
		
		title = "JqPlot";
	}
	
	public static ChartFactory getChartFactory() {
		return chartFactory;
	}

	public static void setChartFactory(ChartFactory chartFactory) {
		JqPlotWidget.chartFactory = chartFactory;
	}

	public WidgetView createView(String viewId) {
		return new JqPlotWidgetView(viewId, new Model<Widget>(this));
	}

	public Chart<?> getChart() {
		if (chartFactory == null) {
			throw new RuntimeException("ChartFactory cannot be null. Use JqPlotWidget.setChartFactory(...)");
		}
		
		return chartFactory.createChart(this);
	}

	@Override
	public void init() {
		if (!settings.containsKey("chartType")) {
			settings.put("chartType", JqPlotWidget.BAR_TYPE);
		}
	}

	@Override
	public boolean hasSettings() {
		return true;
	}

	@Override
	public Panel createSettingsPanel(String settingsPanelId) {
		return new JqPlotSettingsPanel(settingsPanelId, new Model<JqPlotWidget>(this));
	}

}
