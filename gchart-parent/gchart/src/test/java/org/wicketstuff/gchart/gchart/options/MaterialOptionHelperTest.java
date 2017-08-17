/*
 * Copyright 2017 Dieter Tremel.
 * http://www.tremel-computer.de
 * All rights, if not explicitly granted, reserved.
 */
package org.wicketstuff.gchart.gchart.options;

import org.wicketstuff.gchart.gchart.options.ChartOptions;
import org.wicketstuff.gchart.Chart;
import org.wicketstuff.gchart.gchart.options.MaterialOptionHelper;
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
public class MaterialOptionHelperTest {

    private Chart chart;

    public MaterialOptionHelperTest() {
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
     * Test of addTitle method, of class MaterialOptionHelper.
     */
    @Test
    public void testAddTitleOption() {
        System.out.println("addTitleOption");
        ChartOptions parent = new ChartOptions();
        MaterialOptionHelper instance = new MaterialOptionHelper(chart);
        String expResult = "{\"chart\":{\"title\":\"Chart Title\"}}";
        ChartOptions result = instance.addTitle(parent, "Chart Title");
        System.out.println(result.toJSON());
        assertEquals(expResult, result.toJSON().toString());
    }

    /**
     * Test of addDualAxisOptions method, of class MaterialOptionHelper.
     */
    @Test
    public void testAddDualAxisOptions() {
        System.out.println("addDualAxisOptions");
        ChartOptions parent = new ChartOptions();
        MaterialOptionHelper instance = new MaterialOptionHelper(chart);
        String[] axisNames = {"Temp", "Humidity"};
        String[] seriesMapping = {"Temp", "Temp", "Humidity"};
        String[] axisTitles = {"Temperatur", "Feuchtigkeit"};
        String expResult = "{\"series\":{\"0\":{\"axis\":\"Temp\"},\"1\":{\"axis\":\"Temp\"},\"2\":{\"axis\":\"Humidity\"}},\"axes\":{\"y\":{\"Temp\":{\"label\":\"Temperatur\"},\"Humidity\":{\"label\":\"Feuchtigkeit\"}}}}";
        ChartOptions result = instance.addDualAxisOptions(parent, axisNames, seriesMapping, axisTitles);
        System.out.println(result.toJSON());
        assertEquals(expResult, result.toJSON().toString());
    }

}
