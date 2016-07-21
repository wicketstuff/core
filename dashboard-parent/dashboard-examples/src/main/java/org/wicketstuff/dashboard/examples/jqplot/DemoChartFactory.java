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
package org.wicketstuff.dashboard.examples.jqplot;

import java.util.Arrays;
import java.util.Map;

import org.wicketstuff.dashboard.widgets.jqplot.ChartFactory;
import org.wicketstuff.dashboard.widgets.jqplot.JqPlotWidget;

import br.com.digilabs.jqplot.Chart;
import br.com.digilabs.jqplot.chart.AreaChart;
import br.com.digilabs.jqplot.chart.BarChart;
import br.com.digilabs.jqplot.chart.LineChart;
import br.com.digilabs.jqplot.chart.PieChart;
import br.com.digilabs.jqplot.elements.Serie;

/**
 * @author Decebal Suiu
 */
public class DemoChartFactory implements ChartFactory {

	@Override
	public Chart<?> createChart(JqPlotWidget widget) {
		return getChart(widget.getSettings());
	}

	private Chart<?> getChart(Map<String, String> settings) {
		Chart<?> chart = null;
		
		String chartType = settings.get("chartType");
		if (JqPlotWidget.BAR_TYPE.equals(chartType)) {
			chart = barChart();
		} else if (JqPlotWidget.LINE_TYPE.equals(chartType)) {
			chart = lineChart();
		} else if (JqPlotWidget.PIE_TYPE.equals(chartType)) {
			chart = pieChart();
		} else if (JqPlotWidget.AREA_TYPE.equals(chartType)) {
			chart = areaChart();			
		}
		
		return chart;
	}

    public static Chart<?> lineChart() {
        LineChart<Integer> lineChart;
        lineChart = new LineChart<Integer>("Line Chart");
        lineChart.addValues(1, 2, 3, 4, 5);
        
        return lineChart;
    }

    public static Chart<?> areaChart() {
        AreaChart<Integer> areaChart;
        areaChart = new AreaChart<Integer>("Area Char");
        areaChart.addValue(Arrays.<Integer>asList(11, 9, 5, 12, 14));
        areaChart.addValue(Arrays.<Integer>asList(4, 8, 5, 3, 6));
        areaChart.addValue(Arrays.<Integer>asList(12, 6, 13, 11, 2));
        
        return areaChart;
    }

    @SuppressWarnings("deprecation")
	public static Chart<?> barChart() {
        BarChart<Integer> barChart;
        barChart = new BarChart<Integer>("Bar Chart");

        barChart.setPadMin(1.05f);
        barChart.setStackSeries(true);
        barChart.setCaptureRightClick(true);
        barChart.setHighlightMouseDown(true);

        barChart.setBarMargin(30);
        barChart.setTicks("A", "B", "C", "D");
        barChart.addValue(Arrays.<Integer>asList(200, 600, 700, 1000));
        barChart.addValue(Arrays.<Integer>asList(200, 600, 700, 1000));
        barChart.addValue(Arrays.<Integer>asList(200, 600, 700, 1000));

        // Texto das Legendas.
        barChart.addSeries(new Serie("A"), new Serie("B"), new Serie("C"));
        
        return barChart;
    }

    public static Chart<?> pieChart() {
        PieChart<Number> pizzaChart = new PieChart<Number>("Pizza Chart");
        pizzaChart.addValue("Drops", 10f);
        pizzaChart.addValue("Chocolate", 20f);
        pizzaChart.addValue("Jujuba", 5f);
        
        return pizzaChart;
    }

}
