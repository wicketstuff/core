package org.wicketstuff.jqplot.examples;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.wicketstuff.jqplot.JqPlotChart;

import br.com.digilabs.jqplot.chart.LabeledLineChart;
import br.com.digilabs.jqplot.chart.LineChart;
import br.com.digilabs.jqplot.data.item.LabeledItem;

public class HomePage extends WebPage {

    private static final long serialVersionUID = 1L;

    public HomePage(final PageParameters parameters) {
    	LineChart<Integer> lineChart = new LineChart<Integer>();
		lineChart.addValue(1);
		lineChart.addValue(2);
		lineChart.addValue(3);
		lineChart.addValue(5);
		add(new JqPlotChart("chart1", lineChart));

		LabeledLineChart<Integer> chart2 = new LabeledLineChart<Integer>("Test", "Incliment Occurrance",
				"Incliment Factor", 15);
		chart2.addValue(new LabeledItem<Integer>("1/1/2008", 42));
		chart2.addValue(new LabeledItem<Integer>("2/14/2008", 56));
		chart2.addValue(new LabeledItem<Integer>("3/7/2008", 39));
		chart2.addValue(new LabeledItem<Integer>("4/22/2008", 81));
		add(new JqPlotChart("chart2", chart2));    }
}
