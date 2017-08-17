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
import java.util.HashMap;
import org.apache.wicket.model.IModel;

/**
 * Implementation of {@link OptionHelper} for Classic Charts.
 *
 * @author Dieter Tremel
 */
public class ClassicOptionHelper extends AbstractOptionHelper {

    public ClassicOptionHelper(Chart chart) {
        super(chart);
    }

    @Override
    public ChartOptions addTitle(ChartOptions parent, IModel<String> titleModel) {
        parent.put("title", titleModel);
        return parent;
    }
    @Override
    public ChartOptions addTitle(ChartOptions parent, String title) {
        parent.put("title", title);
        return parent;
    }
    @Override
    public ChartOptions addSubTitle(ChartOptions parent, IModel<String> titleModel) {
// no subtitle in classic chart       parent.put("subtitle", titleModel);
        return parent;
    }
    @Override
    public ChartOptions addSubTitle(ChartOptions parent, String subTitle) {
// no subtitle in classic chart       parent.put("subtitle", subTitle);
        return parent;
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

    @Override
    public ChartOptions addDualAxisOptions(ChartOptions parent,
            String[] axisNames, String[] seriesMapping, String[] axisTitles,
            OptionModifier[] axisModifiers, OptionModifier[] seriesModifiers) {
        HashMap<String, Integer> axisNoMap = new HashMap<>();
        ChartOptions vAxesOpts = new ChartOptions();
        int axNo = 0;
        for (String axisName : axisNames) {
            axisNoMap.put(axisName, axNo);
            ChartOptions axisOpts = new ChartOptions();
            axisOpts.put("title", axisTitles[axNo]);
            if(axisModifiers!= null && axisModifiers[axNo]!= null){
                axisModifiers[axNo].modify(axisOpts);
            }
            vAxesOpts.put(String.valueOf(axNo++), axisOpts);
        }
        parent.put("vAxes", vAxesOpts);

        ChartOptions seriesOpt = new ChartOptions();
        int serNo = 0;
        for (String seriesMap : seriesMapping) {
            ChartOptions mapOpt = new ChartOptions();
            mapOpt.put("targetAxisIndex", axisNoMap.get(seriesMap));
            if(seriesModifiers!= null && seriesModifiers[serNo]!= null){
                seriesModifiers[serNo].modify(mapOpt);
            }
            seriesOpt.put(String.valueOf(serNo++), mapOpt);
        }
        parent.put("series", seriesOpt);
        return parent;
    }
}
