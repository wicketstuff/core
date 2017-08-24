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
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;

/**
 * Abstract superclass for alle implementations of {@link OptionHelper}. Used
 * for {@link  MaterialOptionHelper} and {@link ClassicOptionHelper}.
 *
 * @author Dieter Tremel
 */
public abstract class AbstractOptionHelper implements OptionHelper {

    protected Chart chart;

    public AbstractOptionHelper(Chart chart) {
        assert chart != null;
        assert chart.getId() != null && !chart.getId().isEmpty();
        this.chart = chart;
    }

    /**
     * Helper function to build resource base key for a chart and it's id
     * according to a convention. This should be used as left part for all keys
     * for i18n of charts.
     *
     * @param chartId Wicket id of Chart.
     * @return Base key with trailing dot, eg. {@code "Chart.myChart."} if param
     * is {@code "myChart"}.
     */
    public static String getChartBaseResourceKey(String chartId) {
        return "chart." + chartId + ".";
    }

    @Override
    public String getChartBaseResourceKey() {
        return getChartBaseResourceKey(chart.getId());
    }

    @Override
    public IModel<String> getChartLabelResourceModel(String subKey) {
        ResourceModel resourceModel = new ResourceModel(getChartBaseResourceKey() + subKey);
        return resourceModel.wrapOnAssignment(chart);
    }

    @Override
    public ChartOptions addTitle(ChartOptions parent) {
        return addTitle(parent, getChartLabelResourceModel("title"));
    }

    @Override
    public ChartOptions addSubTitle(ChartOptions parent) {
        return addTitle(parent, getChartLabelResourceModel("subtitle"));
    }

    @Override
    public ChartOptions addDualAxisOptions(ChartOptions parent, String[] axisNames, String[] seriesMapping) {
        String[] axisTitles = new String[axisNames.length];
        int axNo = 0;
        for (String axisName : axisNames) {
            axisTitles[axNo++] = getChartLabelResourceModel("axis." + axisName + ".label").getObject();
        }
        return addDualAxisOptions(parent, axisNames, seriesMapping, axisTitles, null, null);
    }

    @Override
    public ChartOptions addDualAxisOptions(ChartOptions parent,
            String[] axisNames, String[] seriesMapping,
            OptionModifier[] axisModifiers, OptionModifier[] seriesModifiers) {
        String[] axisTitles = new String[axisNames.length];
        int axNo = 0;
        for (String axisName : axisNames) {
            axisTitles[axNo++] = getChartLabelResourceModel("axis." + axisName + ".label").getObject();
        }
        return addDualAxisOptions(parent, axisNames, seriesMapping, axisTitles, axisModifiers, seriesModifiers);
    }

    @Override
    public ChartOptions addDualAxisOptions(ChartOptions parent, String[] axisNames, String[] seriesMapping, String[] axisTitles) {
        return addDualAxisOptions(parent, axisNames, seriesMapping, axisTitles, null, null);
    }
}
