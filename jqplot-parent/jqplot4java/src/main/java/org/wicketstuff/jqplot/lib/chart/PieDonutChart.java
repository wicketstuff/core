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

import java.util.Collection;

import org.wicketstuff.jqplot.lib.ChartConfiguration;
import org.wicketstuff.jqplot.lib.JqPlotResources;
import org.wicketstuff.jqplot.lib.data.PieDonutData;
import org.wicketstuff.jqplot.lib.data.item.LabeledItem;
import org.wicketstuff.jqplot.lib.elements.Title;
import org.wicketstuff.jqplot.lib.metadata.JqPlotPlugin;

/**
 * Simple implementation of Pie Donut Chart. This class can/should be extended.
 * 
 * @author inaiat
 */
@JqPlotPlugin(values = {JqPlotResources.DonutRenderer})
public class PieDonutChart<T extends Number> extends AbstractChart<PieDonutData<T>,String> {

	private static final long serialVersionUID = -4671992800819368331L;

	PieDonutData<T> data = new PieDonutData<T>();
	
	private final ChartConfiguration<String> chartConfig;

    /**
     * Construtor
     */
    public PieDonutChart() {
        this(null);
    }

    /**
     * 
     * @param title The title
     */
    public PieDonutChart(String title) {
    	this.chartConfig = new ChartConfiguration<String>(); 
    	chartConfig
    		.setTitle(new Title(title))
    		.seriesDefaultsInstance()
    		.setRenderer(JqPlotResources.DonutRenderer)
    		.rendererOptionsInstance()
    		.setSliceMargin(4)
    		.setShowDataLabels(true)
    		.setDataLabels("value");
    }

    /**
     * 
     * @param value Collection of {@link LabeledItem}
     */
    public void addValue(Collection<LabeledItem<T>> value) {
        data.addValue(value);
    }

    /**
     * 
     * @return PieDonutData
     */
    public PieDonutData<T> getChartData() {
        return data;
    }

	@Override
	public ChartConfiguration<String> getChartConfiguration() {
		return chartConfig;
	}
}
