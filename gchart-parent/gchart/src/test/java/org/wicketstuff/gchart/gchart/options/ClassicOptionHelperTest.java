/*
 * Copyright 2017 Dieter Tremel.
 * http://www.tremel-computer.de
 * All rights, if not explicitly granted, reserved.
 */
package org.wicketstuff.gchart.gchart.options;

import org.wicketstuff.gchart.gchart.options.ChartOptions;
import org.wicketstuff.gchart.Chart;
import org.wicketstuff.gchart.gchart.options.ClassicOptionHelper;
import org.wicketstuff.gchart.gchart.options.OptionModifier;
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
public class ClassicOptionHelperTest {

    private Chart chart;

    public ClassicOptionHelperTest() {
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
     * Test of addTitle method, of class ClassicOptionHelper.
     */
    @Test
    public void testAddTitleOption() {
        System.out.println("addTitleOption");
        ChartOptions parent = new ChartOptions();
        ClassicOptionHelper instance = new ClassicOptionHelper(chart);
        String expResult = "{\"title\":\"Chart Title\"}";
        ChartOptions result = instance.addTitle(parent, "Chart Title");
        System.out.println(result.toJSON());
        assertEquals(expResult, result.toJSON().toString());
    }

    /**
     * Test of addDualAxisOptions method, of class ClassicOptionHelper.
     */
    @Test
    public void testAddDualAxisOptions() {
        System.out.println("addDualAxisOptions");
        ChartOptions parent = new ChartOptions();
        ClassicOptionHelper instance = new ClassicOptionHelper(chart);
        String[] axisNames = {"Temp", "Humidity"};
        String[] seriesMapping = {"Temp", "Temp", "Humidity"};
        String[] axisTitles = {"Temperatur", "Feuchtigkeit"};
        String expResult = "{\"vAxes\":{\"0\":{\"title\":\"Temperatur\"},\"1\":{\"title\":\"Feuchtigkeit\"}},\"series\":{\"0\":{\"targetAxisIndex\":0},\"1\":{\"targetAxisIndex\":0},\"2\":{\"targetAxisIndex\":1}}}";
        ChartOptions result = instance.addDualAxisOptions(parent, axisNames, seriesMapping, axisTitles);
        System.out.println(result.toJSON());
        assertEquals(expResult, result.toJSON().toString());
    }

    /**
     * Test of addDualAxisOptions method, of class ClassicOptionHelper.
     */
    @Test
    public void testAddDualAxisOptionsModifier() {
        System.out.println("addDualAxisOptionsModifier");
        ChartOptions parent = new ChartOptions();
        ClassicOptionHelper instance = new ClassicOptionHelper(chart);
        String[] axisNames = {"Temp", "Humidity"};
        String[] seriesMapping = {"Temp", "Temp", "Humidity"};
        String[] axisTitles = {"Temperatur", "Feuchtigkeit"};
        OptionModifier temperatureAxisModifier = new OptionModifier() {
            @Override
            public void modify(ChartOptions options) {
                options.put("format", "percent");
            }
        };
        OptionModifier humiditySeriesModifier = new OptionModifier() {
            @Override
            public void modify(ChartOptions options) {
                options.put("lineWidth", 8);
            }
        };
        String expResult = "{\"vAxes\":{\"0\":{\"format\":\"percent\",\"title\":\"Temperatur\"},\"1\":{\"title\":\"Feuchtigkeit\"}},\"series\":{\"0\":{\"targetAxisIndex\":0},\"1\":{\"targetAxisIndex\":0,\"lineWidth\":8},\"2\":{\"targetAxisIndex\":1}}}";
        ChartOptions result = instance.addDualAxisOptions(parent,
                axisNames, seriesMapping, axisTitles,
                new OptionModifier[]{temperatureAxisModifier, null}, new OptionModifier[]{null, humiditySeriesModifier, null});
        System.out.println(result.toJSON());
        assertEquals(expResult, result.toJSON().toString());
    }

}
