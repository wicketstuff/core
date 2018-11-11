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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.wicket.model.IModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.wicketstuff.gchart.Chart;

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

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getChartBaseResourceKey method, of class AbstractOptionHelper.
     */
    @Test
    public void testGetChartBaseResourceKey() {
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
