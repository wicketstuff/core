/*
 * Copyright 2017 Dieter Tremel.
 * http://www.tremel-computer.de
 * All rights, if not explicitly granted, reserved.
 */
package org.wicketstuff.gchart.gchart.options.builder;

import org.wicketstuff.gchart.gchart.options.ChartOptions;
import org.wicketstuff.gchart.gchart.options.OptionModifier;
import org.apache.wicket.model.IModel;
import org.wicketstuff.gchart.Chart;

/**
 *
 * @author Dieter Tremel
 */
public interface IOptionBuilder {

    /**
     * Helper function to build resource base key for a chart and it's id
     * according to a convention. This should be used as left part for all keys
     * for i18n of charts. Takes the chart id from the chart field.
     *
     * @return Base key with trailing dot, eg. {@code "Chart.myChart."} if param
     * is {@code "myChart"}.
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
     * Add a title of the chart from i18n. The key of the ResourceModel is
     * constructed by convention.
     *
     * @return IOptionbuilder for fluent api.
     * @see OptionBuilder#getChartBaseResourceKey(java.lang.String) 
     * @see IOptionBuilder#getChartBaseResourceKey() 
     */
    public IOptionBuilder title();

    /**
     * Add a title of the chart direct from String. Convenient function to wrap
     * String in a model.
     *
     * @param title Text of title.
     * @return IOptionbuilder for fluent api.
     */
    public IOptionBuilder title(String title);

    /**
     * Give the model for the title of the chart.
     *
     * @param titleModel Model for title.
     * @return IOptionbuilder for fluent api.
     */
    public IOptionBuilder title(IModel<String> titleModel);

    /**
     * Add a subTitle of the chart from i18n. The key of the ResourceModel is
     * constructed by convention.
     *
     * @return IOptionbuilder for fluent api.
     * @see OptionBuilder#getChartBaseResourceKey(java.lang.String)
     * @see IOptionBuilder#getChartBaseResourceKey()
     */
    public IOptionBuilder subTitle();

    /**
     * Add a subTitle of the chart direct from String. Convenient function to wrap
     * String in a model.
     *
     * @param subTitle Text of subTitle.
     * @return IOptionbuilder for fluent api.
     */
    public IOptionBuilder subTitle(String subTitle);

    /**
     * Give the model for the subTitle of the chart.
     *
     * @param subTitleModel Model for subTitle.
     * @return IOptionbuilder for fluent api.
     */
    public IOptionBuilder subTitle(IModel<String> subTitleModel);

    /**
     * Add a axis with a title from i18n. The key of the ResourceModel is
     * constructed by convention by
     * {@link IOptionBuilder#getChartBaseResourceKey()} + {@code "axis.name.title"}.
     *
     * @param name Uniq name for the axis.
     * @return IOptionbuilder for fluent api.
     * @see OptionBuilder#getChartBaseResourceKey(java.lang.String)
     * @see IOptionBuilder#getChartBaseResourceKey()
     */
    public IOptionBuilder axis(String name);

    /**
     * Add a axis title direct from String. Convenient function to wrap String
     * in a model.
     *
     * @param name Uniq name for the axis.
     * @param title Text of title.
     * @return IOptionbuilder for fluent api.
     */
    public IOptionBuilder axis(String name, String title);

    /**
     * Add a axis, give the model for the title of the axis.
     *
     * @param name Uniq name for the axis.
     * @param titleModel Model for title.
     * @return IOptionbuilder for fluent api.
     */
    public IOptionBuilder axis(String name, IModel<String> titleModel);

    /**
     * Add a modifier to configure options for the axis.
     *
     * @param name Name to refer to an axis.
     * @param axisModifier Modifier to handle axis options.
     * @return IOptionbuilder for fluent api.
     */
    public IOptionBuilder modifyAxis(String name, OptionModifier axisModifier);

    /**
     * Map a serie to an axis by the axisname.
     *
     * @param axisName Name of a previous defined axis.
     * @return IOptionbuilder for fluent api.
     */
    public IOptionBuilder mapSeries(String axisName);

    /**
     * Map a serie to an axis by the axisname. Aply a modifier to the options of
     * this series.
     *
     * @param axisName Name of a previous defined axis.
     * @param seriesModifier Modifierer for the opts of this series.
     * @return IOptionbuilder for fluent api.
     */
    public IOptionBuilder mapSeries(String axisName, OptionModifier seriesModifier);

    /**
     * Return the ChartOptions defined by previous fluent calls.
     *
     * @return ChartOptions as defined.
     */
    public ChartOptions build();

}
