/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wicketstuff.jqplot.lib.test;

import org.junit.jupiter.api.Test;
import org.wicketstuff.jqplot.lib.JqPlotUtils;
import org.wicketstuff.jqplot.lib.chart.LabeledLineChart;
import org.wicketstuff.jqplot.lib.chart.LineChart;
import org.wicketstuff.jqplot.lib.data.item.LabeledItem;

/**
 *
 * @author inaiat
 */
public class LineChartTest {
	@Test
	public void testAddValues() {

        Integer[] values = {1, 2, 3, 4, 5};
        LineChart<Integer> instance = new LineChart<>();
        instance.addValues(values);
        System.out.println(JqPlotUtils.createJquery(instance, "chart"));
    }

	@Test
    public void testBarChart() {
        LabeledLineChart<Integer> chart = new LabeledLineChart<>("Test","Incliment Occurrance","Incliment Factor",15);
        chart.addValue(new LabeledItem<>("1/1/2008", 42));
        chart.addValue(new LabeledItem<>("2/14/2008", 56));
        chart.addValue(new LabeledItem<>("3/7/2008", 39));
        chart.addValue(new LabeledItem<>("4/22/2008", 81));
        System.out.println(JqPlotUtils.createJquery(chart, "chart"));
        System.out.println(JqPlotUtils.retriveJavaScriptResources(chart));
    }
}
