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
        ChartOptions parent = new ChartOptions();
        MaterialOptionHelper instance = new MaterialOptionHelper(chart);
        String expResult = "{\"chart\":{\"title\":\"Chart Title\"}}";
        ChartOptions result = instance.addTitle(parent, "Chart Title");
//        System.out.println(result.toJSON());
        assertEquals(expResult, result.toJSON().toString());
    }

    /**
     * Test of addDualAxisOptions method, of class MaterialOptionHelper.
     */
    @Test
    public void testAddDualAxisOptions() {
        ChartOptions parent = new ChartOptions();
        MaterialOptionHelper instance = new MaterialOptionHelper(chart);
        String[] axisNames = {"Temp", "Humidity"};
        String[] seriesMapping = {"Temp", "Temp", "Humidity"};
        String[] axisTitles = {"Temperatur", "Feuchtigkeit"};
        String expResult = "{\"series\":{\"0\":{\"axis\":\"Temp\"},\"1\":{\"axis\":\"Temp\"},\"2\":{\"axis\":\"Humidity\"}},\"axes\":{\"y\":{\"Temp\":{\"label\":\"Temperatur\"},\"Humidity\":{\"label\":\"Feuchtigkeit\"}}}}";
        ChartOptions result = instance.addDualAxisOptions(parent, axisNames, seriesMapping, axisTitles);
//        System.out.println(result.toJSON());
        assertEquals(expResult, result.toJSON().toString());
    }

}
