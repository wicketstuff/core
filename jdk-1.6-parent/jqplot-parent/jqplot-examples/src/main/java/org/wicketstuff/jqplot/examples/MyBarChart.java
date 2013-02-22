package org.wicketstuff.jqplot.examples;

import br.com.digilabs.jqplot.JqPlotResources;
import br.com.digilabs.jqplot.chart.BarChart;
import br.com.digilabs.jqplot.metadata.JqPlotPlugin;

@JqPlotPlugin(values = {JqPlotResources.CanvasAxisTickRenderer})
public class MyBarChart extends BarChart<Integer> {

	private static final long serialVersionUID = -5011433271448732925L;

	public MyBarChart(String title) {
		super(title);
		getXAxis().setTickRenderer(JqPlotResources.CanvasAxisTickRenderer);
	}
	
}
