/*
 * Copyright 2017 Dieter Tremel.
 * http://www.tremel-computer.de
 * All rights, if not explicitly granted, reserved.
 */
package org.wicketstuff.gchart.gchart.options.builder;

import org.wicketstuff.gchart.Chart;
import org.wicketstuff.gchart.gchart.options.OptionModifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Dieter Tremel
 */
public abstract class OptionBuilder implements IOptionBuilder {

    private static final Logger log = LoggerFactory.getLogger(OptionBuilder.class);
    protected Chart chart;

    protected IModel<String> titleModel;
    protected IModel<String> subTitleModel;
    protected Map<String, IModel<String>> axisMap = new LinkedHashMap<>();
    protected Map<String, OptionModifier> axisModifierMap = new HashMap<>();
    List<SeriesDeclaration> series = new ArrayList<>();

    protected OptionBuilder(Chart chart) {
        this.chart = chart;
    }

    public static ClassicOptionBuilder classic(Chart chart) {
        return new ClassicOptionBuilder(chart);
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
    public IOptionBuilder title() {
        this.titleModel = getChartLabelResourceModel("title");
        return this;
    }

    @Override
    public IOptionBuilder title(String title) {
        this.titleModel = Model.of(title);
        return this;
    }

    @Override
    public IOptionBuilder title(IModel<String> titleModel) {
        this.titleModel = titleModel;
        return this;
    }

    @Override
    public IOptionBuilder subTitle() {
        this.subTitleModel = getChartLabelResourceModel("subTitle");
        return this;
    }

    @Override
    public IOptionBuilder subTitle(String subTitle) {
        this.subTitleModel = Model.of(subTitle);
        return this;
    }

    @Override
    public IOptionBuilder subTitle(IModel<String> subTitleModel) {
        this.subTitleModel = subTitleModel;
        return this;
    }

    @Override
    public IOptionBuilder axis(String name) {
        axisMap.put(name, getChartLabelResourceModel("axis." + name + ".title"));
        return this;
    }

    @Override
    public IOptionBuilder axis(String name, String title) {
        axisMap.put(name, Model.of(title));
        return this;
    }

    @Override
    public IOptionBuilder axis(String name, IModel<String> titleModel) {
        axisMap.put(name, titleModel);
        return this;
    }

    @Override
    public IOptionBuilder modifyAxis(String name, OptionModifier axisModifier) {
        if (!axisMap.containsKey(name)) {
            log.warn("Adding a modfier for an axis that is not yet defined.");
        }
        axisModifierMap.put(name, axisModifier);
        return this;
    }

    @Override
    public IOptionBuilder mapSeries(String axisName) {
        series.add(new SeriesDeclaration(axisName));
        return this;
    }

    @Override
    public IOptionBuilder mapSeries(String axisName, OptionModifier seriesModifier) {
        series.add(new SeriesDeclaration(axisName, seriesModifier));
        return this;
    }

    /**
     * Class for a record of sieries axis mapping and associated OptionModifier.
     */
    protected class SeriesDeclaration {

        private final String axisName;
        private final OptionModifier modifier;

        /**
         * Declares a series without modifier.
         *
         * @param axisName Name of axis to map to.
         */
        public SeriesDeclaration(String axisName) {
            this.axisName = axisName;
            modifier = null;
        }

        /**
         * Declares a series with modifier.
         *
         * @param axisName Name of axis to map to.
         * @param modifier Modifier to apply to the sieries' options.
         */
        public SeriesDeclaration(String axisName, OptionModifier modifier) {
            this.axisName = axisName;
            this.modifier = modifier;
        }

        public String getAxisName() {
            return axisName;
        }

        public OptionModifier getModifier() {
            return modifier;
        }

        /**
         * Calc if there is a modifier defined.
         *
         * @return True if an modifiere exists for this mapping.
         */
        public boolean hasModifier() {
            return modifier != null;
        }
    }
}
