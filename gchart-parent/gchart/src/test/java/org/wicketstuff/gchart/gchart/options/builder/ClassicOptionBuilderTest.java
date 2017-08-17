/*
 * Copyright 2017 Dieter Tremel.
 * http://www.tremel-computer.de
 * All rights, if not explicitly granted, reserved.
 */
package org.wicketstuff.gchart.gchart.options.builder;

import org.wicketstuff.gchart.gchart.options.builder.OptionBuilder;
import org.wicketstuff.gchart.gchart.options.builder.ClassicOptionBuilder;
import org.wicketstuff.gchart.Chart;
import org.wicketstuff.gchart.gchart.options.ChartOptions;
import org.wicketstuff.gchart.gchart.options.OptionModifier;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Test of ClassicOptionBuilder.
 *
 * @author Dieter Tremel
 */
public class ClassicOptionBuilderTest {

    private Chart chart;

    public ClassicOptionBuilderTest() {
        chart = mock(Chart.class);
        when(chart.getId()).thenReturn("myChart");
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

//series: {
//    0: {targetAxisIndex: 0},
//    1: {targetAxisIndex: 1}
//},
//vAxes: {
//    // Adds titles to each axis.
//    0: {title: 'Temps (Celsius)'},
//    1: {title: 'Daylight'}
//}
    /**
     * Test of build method, of class ClassicOptionBuilder.
     */
    @Test
    public void testBuild() {
        System.out.println("build");
        ClassicOptionBuilder builder = OptionBuilder.classic(chart);

        builder.title("My first Chart");

        ChartOptions expResult = new ChartOptions();
        expResult.put("title", "My first Chart");

        ChartOptions result = builder.build();

        assertNotEquals(expResult, result);
        assertEquals(expResult.toJSON().toString(), result.toJSON().toString());

        // add axis and series
        builder.axis("TEMP", "Temps (Celsius)").axis("DAYLIGHT", "Daylight");
        builder.modifyAxis("TEMP", new OptionModifier() {
            @Override
            public void modify(ChartOptions options) {
            ChartOptions textStyle = new ChartOptions();
            textStyle.put("color", "red");
            options.put("textStyle", textStyle);
            }
        });

        builder.mapSeries("TEMP").mapSeries("DAYLIGHT", new OptionModifier() {
            @Override
            public void modify(ChartOptions options) {
                options.put("color", "yellow");
            }
        });
        result = builder.build();
        System.out.println(result.toJSON().toString());
        assertEquals("{\"vAxes\":{\"0\":{\"textStyle\":{\"color\":\"red\"},\"title\":\"Temps (Celsius)\"},\"1\":{\"title\":\"Daylight\"}},\"series\":{\"0\":{\"targetAxisIndex\":0},\"1\":{\"color\":\"yellow\",\"targetAxisIndex\":1}},\"title\":\"My first Chart\"}",
                result.toJSON().toString());

    }

}
