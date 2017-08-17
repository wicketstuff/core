/*
 * Copyright 2017 Dieter Tremel.
 * http://www.tremel-computer.de
 * All rights, if not explicitly granted, reserved.
 */
package org.wicketstuff.gchart.gchart.options;

import org.wicketstuff.gchart.gchart.options.ChartOptions;
import org.wicketstuff.gchart.gchart.options.OptionModifier;
import org.wicketstuff.gchart.gchart.options.AbstractOptionHelper;
import org.wicketstuff.gchart.Chart;
import org.apache.wicket.model.IModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author Dieter Tremel
 */
public class AbstractOptionHelperTest {
    private Chart chart;
    
    public AbstractOptionHelperTest() {
        chart = mock(Chart.class);
        when(chart.getId()).thenReturn("myChart");
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getChartBaseResourceKey method, of class AbstractOptionHelper.
     */
    @Test
    public void testGetChartBaseResourceKey() {
        System.out.println("getChartBaseResourceKey");
        AbstractOptionHelper instance = new AbstractOptionHelperImpl(chart);
        String expResult = "chart.myChart.";
        String result = instance.getChartBaseResourceKey();
        assertEquals(expResult, result);
    }

    /**
     * Test of getChartLabelResourceModel method, of class AbstractOptionHelper.
     */
//    @Test
//    public void testGetChartResourceModel() {
//        System.out.println("getChartLabelResourceModel");
//        String subKey = "title";
//        AbstractOptionHelper instance = new AbstractOptionHelperImpl(chart);
//        ResourceModel expResult = "chart.myChart.title";
//        ResourceModel result = instance.getChartLabelResourceModel(subKey);
//        assertEquals(expResult, result);
//    }

    public class AbstractOptionHelperImpl extends AbstractOptionHelper {

        public AbstractOptionHelperImpl(Chart chart) {
            super(chart);
        }


        @Override
        public ChartOptions addTitle(ChartOptions parent) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public ChartOptions addTitle(ChartOptions parent, String title) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public ChartOptions addDualAxisOptions(ChartOptions parent, String[] axisNames, String[] seriesMapping) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public ChartOptions addDualAxisOptions(ChartOptions parent, String[] axisNames, String[] seriesMapping, String[] axisTitles) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public ChartOptions addDualAxisOptions(ChartOptions parent, String[] axisNames, String[] seriesMapping, String[] axisTitles, OptionModifier[] axisModifiers, OptionModifier[] seriesModifiers) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public ChartOptions addTitle(ChartOptions parent, IModel<String> titleModel) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public ChartOptions addSubTitle(ChartOptions parent, String subTitle) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public ChartOptions addSubTitle(ChartOptions parent, IModel<String> subTitleModel) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    }
    
}
