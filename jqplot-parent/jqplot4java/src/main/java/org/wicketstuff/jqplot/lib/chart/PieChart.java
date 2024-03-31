/*
 *  Copyright 2011 Inaiat H. Moraes.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
 */
package org.wicketstuff.jqplot.lib.chart;

import org.wicketstuff.jqplot.lib.ChartConfiguration;
import org.wicketstuff.jqplot.lib.JqPlotResources;
import org.wicketstuff.jqplot.lib.data.PieData;
import org.wicketstuff.jqplot.lib.data.item.LabeledItem;
import org.wicketstuff.jqplot.lib.elements.Legend;
import org.wicketstuff.jqplot.lib.elements.Location;
import org.wicketstuff.jqplot.lib.elements.Title;
import org.wicketstuff.jqplot.lib.metadata.JqPlotPlugin;

/**
 * Simple implementation of Pie Chart. This class can/should be extended.
 *
 * @author inaiat
 */
@JqPlotPlugin(values = {JqPlotResources.PieRenderer})
public class PieChart<T extends Number> extends AbstractChart<PieData<T>,String> {

	private static final long serialVersionUID = -9078771567964143651L;

	private final ChartConfiguration<String> chartConfig;

	private PieData<T> data = new PieData<>();

    /**
     * Construtor
     */
    public PieChart() {
        this(null);
    }

    /**
     * Construtor
     * @param title The title
     */
    public PieChart(String title) {
    	this.chartConfig = new ChartConfiguration<>();
    	chartConfig
    		.setTitle(new Title(title))
    		.setLegend(new Legend(true, Location.e))
    		.seriesDefaultsInstance()
    		.setRenderer(JqPlotResources.PieRenderer)
    		.rendererOptionsInstance()
    		.setShowLables(true);
    }

    /**
     *
     * @param label label
     * @param value value
     */
    public void addValue(String label, T value) {
        data.addValue(new LabeledItem<>(label, value));
    }

    /**
     *
     * @param value value
     */
    public void addValue(LabeledItem<T> value) {
        data.addValue(value);
    }

    /**
     *
     * @param values values
     */
    public void addValues(@SuppressWarnings("unchecked") LabeledItem<T>... values) {
        data.addValues(values);
    }

    /**
     *
     * @return chartData
     */
    @Override
	public PieData<T> getChartData() {
        return data;
    }

	@Override
	public ChartConfiguration<String> getChartConfiguration() {
		return chartConfig;
	}

}