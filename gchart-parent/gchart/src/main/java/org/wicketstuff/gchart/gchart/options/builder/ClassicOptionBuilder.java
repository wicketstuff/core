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
package org.wicketstuff.gchart.gchart.options.builder;

import org.wicketstuff.gchart.Chart;
import org.wicketstuff.gchart.gchart.options.ChartOptions;
import java.util.HashMap;
import java.util.Map;
import org.apache.wicket.model.IModel;

/**
 *
 * @author Dieter Tremel
 */
public class ClassicOptionBuilder extends OptionBuilder {

    public ClassicOptionBuilder(Chart chart) {
        super(chart);
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
    public ChartOptions build() {
        ChartOptions options = new ChartOptions();

        if (titleModel != null) {
            options.put("title", titleModel);
        }
        // no subtitle in classic chart
//        if (subTitleModel != null) {
//            options.put("subTitle", subTitleModel);
//        }

        HashMap<String, Integer> axisNoMap = new HashMap<>();
        ChartOptions vAxes = new ChartOptions();
        int axNo = 0;
        for (Map.Entry<String, IModel<String>> entry : axisMap.entrySet()) {
            axisNoMap.put(entry.getKey(), axNo);
            final ChartOptions axisOpt = new ChartOptions();
            axisOpt.put("title", entry.getValue());
            if (axisModifierMap.containsKey(entry.getKey())) {
                axisModifierMap.get(entry.getKey()).modify(axisOpt);
            }
            vAxes.put(String.valueOf(axNo++), axisOpt);
        }
        if (!vAxes.isEmpty()) {
            options.put("vAxes", vAxes);
        }

        int serNo = 0;
        ChartOptions seriesOpts = new ChartOptions();
        for (SeriesDeclaration ser : series) {
            ChartOptions mapOpt = new ChartOptions();
            mapOpt.put("targetAxisIndex", axisNoMap.get(ser.getAxisName()));
            if (ser.hasModifier()) {
                ser.getModifier().modify(mapOpt);
            }
            seriesOpts.put(String.valueOf(serNo++), mapOpt);
        }
        if (!seriesOpts.isEmpty()) {
            options.put("series", seriesOpts);
        }

        return options;
    }

}
