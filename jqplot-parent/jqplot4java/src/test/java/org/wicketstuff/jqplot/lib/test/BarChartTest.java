/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wicketstuff.jqplot.lib.test;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.wicketstuff.jqplot.lib.JqPlotUtils;
import org.wicketstuff.jqplot.lib.chart.BarChart;

import com.github.openjson.JSONException;

/**
 *
 * @author inaiat
 */
public class BarChartTest {

    @Test
    public void testLabeledLine() throws JSONException {
        BarChart<Integer> chart = new BarChart<>();
        chart.addValue(Arrays.<Integer>asList(200, 600, 700, 1000));
        chart.addValue(Arrays.<Integer>asList(200, 600, 700, 1000));
        chart.addValue(Arrays.<Integer>asList(200, 600, 700, 1000));
        System.out.println(JqPlotUtils.createJquery(chart, "chart"));

    }
}
