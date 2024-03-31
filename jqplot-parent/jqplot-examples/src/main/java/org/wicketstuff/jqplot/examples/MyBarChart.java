package org.wicketstuff.jqplot.examples;

import org.wicketstuff.jqplot.lib.JqPlotResources;
import org.wicketstuff.jqplot.lib.chart.BarChart;
import org.wicketstuff.jqplot.lib.metadata.JqPlotPlugin;

@JqPlotPlugin(values = {JqPlotResources.CanvasAxisTickRenderer})
public class MyBarChart extends BarChart<Integer> {

	private static final long serialVersionUID = -5011433271448732925L;

	public MyBarChart(String title) {
		super(title);
		getXAxis().setTickRenderer(JqPlotResources.CanvasAxisTickRenderer);
	}

}
