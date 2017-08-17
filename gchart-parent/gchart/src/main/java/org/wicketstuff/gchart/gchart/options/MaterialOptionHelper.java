/* 
 * Copyright 2017 Dieter Tremel.
 *
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

/**
 * Implementation of {@link OptionHelper} for Material Charts.
 *
 * @author Dieter Tremel
 */
public class MaterialOptionHelper extends AbstractOptionHelper {

    public MaterialOptionHelper(Chart chart) {
        super(chart);
    }

    @Override
    public ChartOptions addTitle(ChartOptions parent, String title) {
        ChartOptions chartOpt = new ChartOptions();
        chartOpt.put("title", title);
        parent.put("chart", chartOpt);
        return parent;
    }

    @Override
    public ChartOptions addTitle(ChartOptions parent, IModel<String> titleModel) {
        ChartOptions chartOpt = new ChartOptions();
        chartOpt.put("title", titleModel);
        parent.put("chart", chartOpt);
        return parent;
    }

    @Override
    public ChartOptions addSubTitle(ChartOptions parent, String subTitle) {
        ChartOptions chartOpt = new ChartOptions();
        chartOpt.put("subtitle", subTitle);
        parent.put("chart", chartOpt);
        return parent;
    }

    @Override
    public ChartOptions addSubTitle(ChartOptions parent, IModel<String> subTitleModel) {
        ChartOptions chartOpt = new ChartOptions();
        chartOpt.put("subtitle", subTitleModel);
        parent.put("chart", chartOpt);
        return parent;
    }

    @Override
    public ChartOptions addDualAxisOptions(ChartOptions parent,
            String[] axisNames, String[] seriesMapping, String[] axisTitles,
            OptionModifier[] axisModifiers, OptionModifier[] seriesModifiers) {
        ChartOptions axesOpt = new ChartOptions();
        ChartOptions yOpt = new ChartOptions();
        int axNo = 0;
        for (String axisName : axisNames) {
            ChartOptions lblOpt = new ChartOptions();
            lblOpt.put("label", axisTitles[axNo++]);
            yOpt.put(axisName, lblOpt);
            if (axisModifiers != null && axisModifiers[axNo] != null) {
                axisModifiers[axNo].modify(yOpt);
            }
        }
        axesOpt.put("y", yOpt);
        parent.put("axes", axesOpt);

        ChartOptions seriesOpt = new ChartOptions();
        int i = 0;
        for (String seriesMap : seriesMapping) {
            ChartOptions mapOpt = new ChartOptions();
            mapOpt.put("axis", seriesMap);
            if (seriesModifiers != null && seriesModifiers[i] != null) {
                seriesModifiers[i].modify(seriesOpt);
            }
            seriesOpt.put(String.valueOf(i++), mapOpt);
        }
        parent.put("series", seriesOpt);
        return parent;
    }
}
