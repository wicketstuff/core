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

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.wicketstuff.gchart.Chart;

/**
 * Utility methods to generate more complex constructs in {@link ChartOptions}.
 * Also two implementations ({@link ClassicOptionHelper} and
 * {@link MaterialOptionHelper}) can be used for convenient building of
 * different option structure just by changing the implementation of this
 * interface.
 *
 * Also used to generate ResourceModels according to a convention for the keys.
 * Implementations should call {@link ResourceModel#wrapOnAssignment(org.apache.wicket.Component)
 * } with the {@link Chart} component as wrapper.
 *
 * @author Dieter Tremel
 */
public interface OptionHelper {

    /**
     * Generate the base resource key for the chart. By convention this is build
     * from the id of the Chart Component. Eg. if Chart id is {@code "myChart"}
     * the result should be {@code "chart.myChart."}.
     *
     * @return Base resource key of chart in form
     * {@code "chart.<id of chart>."}. Note the trailing dot.
     */
    public String getChartBaseResourceKey();

    /**
     * Generate a wrapped ResourceModel for the {@link Chart} component.
     *
     * @param subKey Subkey as suffix of {@link #getChartBaseResourceKey() }.
     * @return ResourceModel for the complete key. There is no default defined,
     * so missing properties can be easyly detected.
     */
    public IModel<String> getChartLabelResourceModel(String subKey);

    /**
     * Add a title of the chart to the root of ChartOptions. Use ResourceModel
     * for the label text. The title handling is different for Material and
     * Classic charts, so this convenient methods are written.
     *
     * @param parent Root ChartOptions to add title to.
     * @return Parent for fluent calling.
     */
    public ChartOptions addTitle(ChartOptions parent);

    /**
     * Add a title of the chart to the root of ChartOptions.
     *
     * @param parent Root ChartOptions to add title to.
     * @param title String to use for the title.
     * @return Parent for fluent calling.
     */
    public ChartOptions addTitle(ChartOptions parent, String title);

    /**
     * Add a title of the chart to the root of ChartOptions.
     *
     * @param parent Root ChartOptions to add title to.
     * @param titleModel Model to use for the title.
     * @return Parent for fluent calling.
     */
    public ChartOptions addTitle(ChartOptions parent, IModel<String> titleModel);
    /**
     * Add a subTitle of the chart to the root of ChartOptions. Use ResourceModel
     * for the label text. The subTitle handling is different for Material and
     * Classic charts, so this convenient methods are written.
     *
     * @param parent Root ChartOptions to add subTitle to.
     * @return Parent for fluent calling.
     */
    public ChartOptions addSubTitle(ChartOptions parent);

    /**
     * Add a subTitle of the chart to the root of ChartOptions.
     *
     * @param parent Root ChartOptions to add subTitle to.
     * @param subTitle String to use for the subTitle.
     * @return Parent for fluent calling.
     */
    public ChartOptions addSubTitle(ChartOptions parent, String subTitle);

    /**
     * Add a subTitle of the chart to the root of ChartOptions.
     *
     * @param parent Root ChartOptions to add subTitle to.
     * @param subTitleModel Model to use for the subTitle.
     * @return Parent for fluent calling.
     */
    public ChartOptions addSubTitle(ChartOptions parent, IModel<String> subTitleModel);

    /**
     * Add a definition of dual axis to the root of ChartOptions. Use
     * ResourceModels with key convention for labeling axis.
     *
     * @param parent Root ChartOptions to add title to.
     * @param axisNames Names of the axis.
     * @param seriesMapping Map each serie in order to an axis by the axis name.
     * @return Parent for fluent calling.
     */
    public ChartOptions addDualAxisOptions(ChartOptions parent, String[] axisNames, String[] seriesMapping);

    /**
     * Add a definition of dual axis to the root of ChartOptions. Use
     * ResourceModels with key convention for labeling axis.
     *
     * @param parent Root ChartOptions to add title to.
     * @param axisNames Names of the axis.
     * @param seriesMapping Map each serie in order to an axis by the axis name.
     * @param axisModifiers Modifiers for the axis definitions. Can be used to
     * add options to each axis. Set to {@code null} if no modification is
     * necessary at all. Set position in array to {@code null} if to
     * modification is needed for the particular axis.
     * @param seriesModifiers Modifiers for the series definitions. Can be used
     * to add options to each series. Set to {@code null} if no modification is
     * necessary at all. Set position in array to {@code null} if to
     * modification is needed for the particular series.
     * @return Parent for fluent calling.
     */
    public ChartOptions addDualAxisOptions(ChartOptions parent,
            String[] axisNames, String[] seriesMapping,
            OptionModifier[] axisModifiers, OptionModifier[] seriesModifiers);

    /**
     * Add a definition of dual axis to the root of ChartOptions. Use explicit
     * axis titles for labeling axis.
     *
     * @param parent Root ChartOptions to add title to.
     * @param axisNames Names of the axis.
     * @param seriesMapping Map each serie in order to an axis by the axis name.
     * @param axisTitles Display titles (labels) used for the titles axis. Must
     * have same order and count as {@code axisNames}.
     * @return Parent for fluent calling.
     */
    public ChartOptions addDualAxisOptions(ChartOptions parent, String[] axisNames, String[] seriesMapping, String[] axisTitles);

    /**
     * Add a definition of dual axis to the root of ChartOptions. Use explicit
     * axis titles for labeling axis.
     *
     * @param parent Root ChartOptions to add title to.
     * @param axisNames Names of the axis.
     * @param seriesMapping Map each serie in order to an axis by the axis name.
     * @param axisTitles Display titles (labels) used for the titles axis. Must
     * have same order and count as {@code axisNames}.
     * @param axisModifiers Modifiers for the axis definitions. Can be used to
     * add options to each axis. Set to {@code null} if no modification is
     * necessary at all. Set position in array to {@code null} if to
     * modification is needed for the particular axis.
     * @param seriesModifiers Modifiers for the series definitions. Can be used
     * to add options to each series. Set to {@code null} if no modification is
     * necessary at all. Set position in array to {@code null} if to
     * modification is needed for the particular series. Set to {@code null} if
     * no modification needed.
     * @return Parent for fluent calling.
     */
    public ChartOptions addDualAxisOptions(ChartOptions parent,
            String[] axisNames, String[] seriesMapping, String[] axisTitles,
            OptionModifier[] axisModifiers, OptionModifier[] seriesModifiers);
}
