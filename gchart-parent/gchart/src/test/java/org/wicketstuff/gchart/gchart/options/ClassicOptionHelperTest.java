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
package org.wicketstuff.gchart.gchart.options;

import org.wicketstuff.gchart.Chart;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static net.javacrumbs.jsonunit.JsonAssert.*;

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
        ChartOptions parent = new ChartOptions();
        ClassicOptionHelper instance = new ClassicOptionHelper(chart);
        String expResult = "{\"title\":\"Chart Title\"}";
        ChartOptions result = instance.addTitle(parent, "Chart Title");
        assertJsonEquals(expResult, result.toJSON().toString());
    }

    /**
     * Test of addDualAxisOptions method, of class ClassicOptionHelper.
     */
    @Test
    public void testAddDualAxisOptions() {
        ChartOptions parent = new ChartOptions();
        ClassicOptionHelper instance = new ClassicOptionHelper(chart);
        String[] axisNames = {"Temp", "Humidity"};
        String[] seriesMapping = {"Temp", "Temp", "Humidity"};
        String[] axisTitles = {"Temperatur", "Feuchtigkeit"};
        String expResult = "{\"vAxes\":{\"0\":{\"title\":\"Temperatur\"},\"1\":{\"title\":\"Feuchtigkeit\"}},\"series\":{\"0\":{\"targetAxisIndex\":0},\"1\":{\"targetAxisIndex\":0},\"2\":{\"targetAxisIndex\":1}}}";
        ChartOptions result = instance.addDualAxisOptions(parent, axisNames, seriesMapping, axisTitles);
        assertJsonEquals(expResult, result.toJSON().toString());
    }

    /**
     * Test of addDualAxisOptions method, of class ClassicOptionHelper.
     */
    @Test
    public void testAddDualAxisOptionsModifier() {
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
        assertJsonEquals(expResult, result.toJSON().toString());
    }

}
