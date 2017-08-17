/*
 * Copyright 2017 Dieter Tremel.
 * http://www.tremel-computer.de
 * All rights, if not explicitly granted, reserved.
 */
package org.wicketstuff.gchart.gchart;

import org.wicketstuff.gchart.Chart;
import org.wicketstuff.gchart.ChartType;
import org.wicketstuff.gchart.ChartLibLoaderBehavior;
import java.util.List;
import java.util.Locale;
import org.apache.wicket.Component;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.Model;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author Dieter Tremel
 */
public class ChartLibLoaderBehaviorTest {
        private final Chart chart1;
        private final Chart chart2;
        private final WebPage page;

    public ChartLibLoaderBehaviorTest() {
        page = mock(WebPage.class);
        when(page.getLocale()).thenReturn(Locale.getDefault());
        chart1 = mock(Chart.class);
        when(chart1.getTypeModel()).thenReturn(Model.of(ChartType.AREA));
        chart2 = mock(Chart.class);
        when(chart2.getTypeModel()).thenReturn(Model.of(ChartType.CALENDAR));
    }
    
    @Before
    public void setUp() {
    }

    /**
     * Test of addChart method, of class ChartLibLoaderBehavior.
     */
    @Test
    public void testAddChart() {
        System.out.println("addChart");
        ChartLibLoaderBehavior loader = new ChartLibLoaderBehavior();
        assertTrue(loader.addChart(chart1));
        assertTrue(loader.addChart(chart2));
    }

    /**
     * Test of removeChart method, of class ChartLibLoaderBehavior.
     */
    @Test
    public void testRemoveChart() {
        System.out.println("removeChart");
        ChartLibLoaderBehavior loader = new ChartLibLoaderBehavior();
        assertTrue(loader.addChart(chart1));
//        assertTrue(loader.addChart(chart2));
        assertTrue(loader.removeChart(chart1));
        assertFalse(loader.removeChart(chart2));
    }

    /**
     * Test of getLocale method, of class ChartLibLoaderBehavior.
     */
    @Test
    public void testGetLocale() {
        System.out.println("getLocale");
        ChartLibLoaderBehavior instance = new ChartLibLoaderBehavior();
        instance.bind(page);
        Locale expResult = Locale.getDefault();
        Locale result = instance.getLocale();
        assertEquals(expResult, result);
    }

    /**
     * Test of setLocale method, of class ChartLibLoaderBehavior.
     */
    @Test
    public void testSetLocale() {
        System.out.println("setLocale");
        Locale locale = Locale.ITALIAN;
        ChartLibLoaderBehavior instance = new ChartLibLoaderBehavior();
        instance.setLocale(locale);
        assertEquals(locale, instance.getLocale());
    }

    /**
     * Test of getCharts method, of class ChartLibLoaderBehavior.
     */
    @Test
    public void testGetCharts() {
        System.out.println("getCharts");
        ChartLibLoaderBehavior loader = new ChartLibLoaderBehavior();
        assertTrue(loader.addChart(chart1));
        assertTrue(loader.addChart(chart2));
        List<Chart> result = loader.getCharts();
        assertEquals(2, result.size());
    }

    /**
     * Test of toJavaScript method, of class ChartLibLoaderBehavior.
     */
    @Test
    public void testToJavaScript() {
        System.out.println("toJavaScript");
        ChartLibLoaderBehavior loader = new ChartLibLoaderBehavior();
        loader.bind(page);
        assertTrue(loader.addChart(chart1));
        assertTrue(loader.addChart(chart2));
        String expResult = "google.charts.load('current', {\"packages\":[\"calendar\",\"corechart\"],\"language\":\"de\"});\n";
        String result = loader.toJavaScript();
        System.out.println(expResult);
        System.out.println(result);
        assertEquals(expResult, result);
    }
}
