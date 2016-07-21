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
package org.wicketstuff.dashboard.examples.ofchart;

import java.util.Map;

import org.wicketstuff.dashboard.widgets.ofchart.ChartDataFactory;
import org.wicketstuff.dashboard.widgets.ofchart.ChartWidget;

import ro.nextreports.jofc2.model.Chart;

/**
 * @author Decebal Suiu
 */
public class DemoChartDataFactory implements ChartDataFactory {

	@Override
	public String createChartData(ChartWidget widget) {
		return getChart(widget.getSettings()).toString();
	}

	private Chart getChart(Map<String, String> settings) {
		Chart chart = null;
		
		String chartType = settings.get("chartType");
		if (ChartWidget.BAR_TYPE.equals(chartType)) {
			chart = DemoChartFactory.createDemoBarChart();
		} else if (ChartWidget.DOUBLE_BAR_TYPE.equals(chartType)) {
			chart = DemoChartFactory.createDemoDoubleBarChart();
		} else if (ChartWidget.LINE_TYPE.equals(chartType)) {
			chart = DemoChartFactory.createDemoLineChart();
		} else if (ChartWidget.DOTED_LINE_TYPE.equals(chartType)) {
			chart = DemoChartFactory.createDemoDotedLineChart();			
		} else if (ChartWidget.PIE_TYPE.equals(chartType)) {
			chart = DemoChartFactory.createDemoPieChart();
		} else if (ChartWidget.SCATTER_TYPE.equals(chartType)) {
			chart = DemoChartFactory.createDemoScatterChart();			
		}
		
		return chart;
	}

}
