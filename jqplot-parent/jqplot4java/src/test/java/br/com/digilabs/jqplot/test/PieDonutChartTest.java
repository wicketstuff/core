/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.digilabs.jqplot.test;


import java.util.ArrayList;
import java.util.List;

import org.wicketstuff.jqplot.lib.JqPlotUtils;
import org.wicketstuff.jqplot.lib.chart.PieDonutChart;
import org.wicketstuff.jqplot.lib.data.item.LabeledItem;

import junit.framework.TestCase;

/**
 *
 * @author inaiat
 */
public class PieDonutChartTest extends TestCase {

    public PieDonutChartTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testLabeledLine() {
    	PieDonutChart<Integer> chart = new PieDonutChart<Integer>();
    	
    	List<LabeledItem<Integer>> list = new ArrayList<LabeledItem<Integer>>();
    	list.add(new LabeledItem<Integer>("a",1));
    	list.add(new LabeledItem<Integer>("b",2));    	

    	List<LabeledItem<Integer>> list2 = new ArrayList<LabeledItem<Integer>>();
    	list2.add(new LabeledItem<Integer>("a",1));
    	list2.add(new LabeledItem<Integer>("b",2));    	
    	
    	
    	chart.addValue(list);
    	chart.addValue(list2);    	
    	
        System.out.println(JqPlotUtils.createJquery(chart, "chart"));        
    }
}
