/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.gchart.gchart;

import static net.javacrumbs.jsonunit.JsonAssert.assertJsonEquals;
import static net.javacrumbs.jsonunit.core.Option.IGNORING_ARRAY_ORDER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Locale;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.Model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.wicketstuff.gchart.Chart;
import org.wicketstuff.gchart.ChartLibLoaderBehavior;
import org.wicketstuff.gchart.ChartType;

import net.javacrumbs.jsonunit.JsonAssert;
/**
 *
 * @author Dieter Tremel
 */
public class ChartLibLoaderBehaviorTest {
        private final Chart chart1;
        private final Chart chart2;
        private final WebPage page;

    private static String JSON_PREFIX = "google.charts.load('current', ";
    private static String JSON_SUFIX = ");\n";

    public ChartLibLoaderBehaviorTest() {
        page = mock(WebPage.class);
        when(page.getLocale()).thenReturn(Locale.getDefault());
        chart1 = mock(Chart.class);
        when(chart1.getTypeModel()).thenReturn(Model.of(ChartType.AREA));
        chart2 = mock(Chart.class);
        when(chart2.getTypeModel()).thenReturn(Model.of(ChartType.CALENDAR));
    }

    @BeforeEach
    public void setUp() {
    }

    /**
     * Test of addChart method, of class ChartLibLoaderBehavior.
     */
    @Test
    public void testAddChart() {
        ChartLibLoaderBehavior loader = new ChartLibLoaderBehavior();
        assertTrue(loader.addChart(chart1));
        assertTrue(loader.addChart(chart2));
    }

    /**
     * Test of removeChart method, of class ChartLibLoaderBehavior.
     */
    @Test
    public void testRemoveChart() {
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
        ChartLibLoaderBehavior loader = new ChartLibLoaderBehavior();
        loader.bind(page);
        assertTrue(loader.addChart(chart1));
        assertTrue(loader.addChart(chart2));

        Locale currentLocale = Locale.getDefault();
        String expResult = "{\"packages\":[\"corechart\",\"calendar\"],\"language\":\"" + currentLocale.getLanguage() +"\"}";
        String resultJs = loader.toJavaScript();

        assertTrue(resultJs.startsWith(JSON_PREFIX));
        assertTrue(resultJs.endsWith(JSON_SUFIX));

        String resJson = resultJs.substring(resultJs.indexOf('{'), resultJs.indexOf('}') + 1);

        assertJsonEquals(expResult, resJson, JsonAssert.when(IGNORING_ARRAY_ORDER));
    }
}
