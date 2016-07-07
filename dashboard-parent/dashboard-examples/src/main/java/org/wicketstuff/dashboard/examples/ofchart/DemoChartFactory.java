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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import ro.nextreports.jofc2.model.Chart;
import ro.nextreports.jofc2.model.axis.XAxis;
import ro.nextreports.jofc2.model.axis.YAxis;
import ro.nextreports.jofc2.model.elements.BarChart;
import ro.nextreports.jofc2.model.elements.LineChart;
import ro.nextreports.jofc2.model.elements.PieChart;
import ro.nextreports.jofc2.model.elements.ScatterChart;

/**
 * @author Decebal Suiu
 */
public class DemoChartFactory {

	public static Chart createDemoBarChart() {
		// a) json hard-coded
		/*
		String json = "{\"elements\":[{\"type\":\"bar\",\"values\":[1,2,3,4,5,6,7,8,9]}],\"title\":{\"text\":\"Tue Oct 14 2008\"}}";
		return new OpenFlashChart(id, 500, 300, new Model<String>(json));
		*/
		// b) use http://code.google.com/p/jofc2/
//		BarChart barChart = new BarChart();
		BarChart barChart = new BarChart(BarChart.Style.GLASS);
		barChart.setColour("#F78000");
		barChart.useAnimation(true);
		barChart.addValues(1, 2, 3, 4, 5, 6, 7 , 8, 9, 8, 7, 6, 5, 4, 3);
		barChart.setText("Tue Oct 14 2008");
		
		Chart chart = new Chart("Beers and bugs");
		/*
		Legend legend = new Legend();
		legend.setBg_colour("#333333");
		chart.setLegend(legend);
		*/
		chart.setBackgroundColour("#FFFFFF");
		chart.addElements(barChart);		
		
		return chart;
	}

	public static Chart createDemoDoubleBarChart() {
		BarChart barChart = new BarChart(BarChart.Style.GLASS);
		barChart.setColour("#9AC836");
		barChart.setTooltip("Beers:<br>Value:#val#");
		barChart.addValues(1, 5, 8, 3, 4, 0, 2, 7);
		barChart.setText("Beers consumed");
		barChart.setAlpha(0.1f);

		BarChart barChart2 = new BarChart(BarChart.Style.GLASS);
		barChart2.setColour("#E30071");
		barChart2.setTooltip("#val#<br>bugs fixed");
		barChart2.setText("bugs fixed");
		barChart2.setFontSize(15);
		barChart.setAlpha(0.9f);
		barChart2.addValues(2, 7, 1, 5, 8, 3, 0, 5, 2, 1);

		Chart chart = new Chart("Beers and bugs");
		chart.addElements(barChart, barChart2);
		chart.setBackgroundColour("#FFFFFF");

		return chart;
	}
	
	public static Chart createDemoLineChart() {
		LineChart lineChart = new LineChart();
		lineChart.setWidth(4);
		lineChart.setColour("#DFC329");
		lineChart.setDotSize(5);
		
		List<Number> values = new ArrayList<Number>();
		Random random = new Random();
		for (int i = 0; i < 15; i++) {
		      values.add(1 + random.nextInt(5));
		}
		lineChart.addValues(values);

		Chart chart = new Chart("My life with my wife");
		YAxis yAxis = new YAxis();
		yAxis.setRange(0, 6, 1);		
		chart.setYAxis(yAxis);
		chart.addElements(lineChart);
		chart.setBackgroundColour("#FFFFFF");
		
		return chart;
	}
	
	public static Chart createDemoDotedLineChart() {
		// TODO
//		LineChart lineChart = new LineChart(LineChart.Style.DOT);
		LineChart lineChart = new LineChart();
		lineChart.setWidth(2);
		lineChart.setDotSize(4);
		lineChart.setHaloSize(0);

		List<Number> values = new ArrayList<Number>();
		for (int i = 0; i < 8; i += 0.2) {
		      double value = Math.sin(i) + 1.5;
		      if (value > 1.75) {
		    	  lineChart.addDots(new LineChart.Dot(value, "#D02020"));
		      } else {
		    	  // TODO give me an out of memory
//		    	  values.add(value);
		      }
	    }
		lineChart.addValues(values);

		Chart chart = new Chart(new Date().toString());
		YAxis yAxis = new YAxis();
		yAxis.setRange(0, 3, 1);
		chart.setYAxis(yAxis);
		chart.addElements(lineChart);
		chart.setBackgroundColour("#FFFFFF");
	    
		return chart;
	}
	
	public static Chart createDemoPieChart() {
		PieChart pieChart = new PieChart();
		pieChart.setAnimate(true);
		pieChart.setStartAngle(35);
		pieChart.setBorder(2);
//		pieChart.setAlpha(0.6f);		
		pieChart.addValues(2, 3, 3.5);
		pieChart.addSlice(6.5f, "Romania (6.5)");
		pieChart.setColours("#F78000", "#9AC836", "#D54192", "#E3001B");
		pieChart.setTooltip("#val# of #total#<br>#percent# of 100%");

		Chart chart = new Chart("Downloads");
		chart.addElements(pieChart);
		chart.setBackgroundColour("#FFFFFF");
		
		return chart;
	}

	public static Chart createDemoScatterChart() {
		ScatterChart scatterChart = new ScatterChart(ScatterChart.Style.LINE);
		scatterChart.setColour("#FFD600");		
		scatterChart.setAlpha(0.6f);
		scatterChart.useAnimation(true);
		
		for (int i = 0; i < 5; i++) {
			scatterChart.addPoint(i, i + 1);
		}
   
		Chart chart = new Chart(new Date().toString());
	    YAxis yAxis = new YAxis();
	    yAxis.setRange(0, 7, 1);
	    chart.setYAxis(yAxis);
	    XAxis xAxis = new XAxis();
	    xAxis.setRange(0, 6, 1);
	    chart.setXAxis(xAxis);
	    chart.addElements(scatterChart);
		chart.setBackgroundColour("#FFFFFF");
	
	    return chart;
	}

}
