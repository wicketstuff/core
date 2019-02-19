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
import org.wicketstuff.jqplot.lib.data.LineSeriesData;
import org.wicketstuff.jqplot.lib.data.item.LineSeriesItem;
import org.wicketstuff.jqplot.lib.elements.Title;
import org.wicketstuff.jqplot.lib.metadata.JqPlotPlugin;

/**
 * Simple implementation of Bar Chart. This class can/should be extended.
 *
 * @author inaiat
 */
@JqPlotPlugin(values = {JqPlotResources.CategoryAxisRenderer,
    JqPlotResources.BarRenderer,
    JqPlotResources.PointLabels,
    JqPlotResources.CanvasTextRenderer,
    JqPlotResources.CanvasAxisLabelRenderer,
    JqPlotResources.CanvasAxisTickRenderer,
    JqPlotResources.CanvasOverlay,
    JqPlotResources.DateAxisRenderer })
public class BarSeriesChart<I extends Number, V extends Number> extends
		AbstractChart<LineSeriesData<I, V>, String> {

	private static final long serialVersionUID = 3650210485517566138L;

	private final ChartConfiguration<String> chartConfig;

	private LineSeriesData<I, V> barData = new LineSeriesData<>();

    /**
     * Construtor
     */
    public BarSeriesChart() {
        this(null, null, null);
    }

    /**
     * Construtor
     * @param title Title for bar chart
     */
    public BarSeriesChart(String title) {
        this(title, null, null);
    }

    /**
     * Construtor
     *
     * @param title Title
     * @param labelX label for axis x
     * @param labelY label for axys y
     */
    public BarSeriesChart(String title, String labelX, String labelY)
    {
      this.chartConfig = new ChartConfiguration<>();

      chartConfig.setTitle(new Title(title))
        .setLabelX(labelX)
        .setLabelY(labelY)
        .seriesDefaultsInstance()
        .pointLabelsInstance();

      chartConfig
        .seriesDefaultsInstance()
        .setRenderer(JqPlotResources.BarRenderer)
        .setFill(true);
     }

	public void addValue(Collection<LineSeriesItem<I, V>> value) {
		barData.addValue(value);
	}

	public void addValues(@SuppressWarnings("unchecked") Collection<LineSeriesItem<I, V>>... values) {
		barData.addValues(values);
	}

	@Override
	public LineSeriesData<I, V> getChartData() {
		return barData;
	}

	@Override
	public ChartConfiguration<String> getChartConfiguration() {
		return this.chartConfig;
	}
}
