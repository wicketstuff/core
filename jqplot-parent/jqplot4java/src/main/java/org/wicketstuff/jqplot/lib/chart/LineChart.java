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
import org.wicketstuff.jqplot.lib.data.LinedData;
import org.wicketstuff.jqplot.lib.elements.Title;
import org.wicketstuff.jqplot.lib.metadata.JqPlotPlugin;

/**
 * Simple implementation of Line Chart. This class can/should be extended.
 *
 * @author inaiat
 */
@JqPlotPlugin(values = { JqPlotResources.CanvasTextRenderer,
		JqPlotResources.CategoryAxisRenderer })
public class LineChart<T extends Number> extends
		AbstractChart<LinedData<T>, String> {

	private static final long serialVersionUID = -643105267124184518L;

	private final ChartConfiguration<String> chartConfig;

	private LinedData<T> linedData = new LinedData<>();

	/**
	 * Line chart construcotr
	 */
	public LineChart() {
		this(null, null, null);
	}

	/**
	 * Construtor
	 *
	 * @param title Title of chart
	 */
	public LineChart(String title) {
		this(title, null, null);
	}

	/**
	 * Construtor
	 *
	 * @param title title
	 * @param labelX labelX
	 * @param labelY labelY
 	 */
	public LineChart(String title, String labelX, String labelY) {
		this.chartConfig = new ChartConfiguration<>();
		chartConfig
				.setTitle(new Title(title))
				.setLabelX(labelX)
				.setLabelY(labelY)
				.axesDefaultsInstance()
				.setLabelRenderer(JqPlotResources.CanvasAxisLabelRenderer);
		chartConfig.axesInstance().xAxisInstance().setPad(0F);

	}

	/**
	 * Add a value
	 * @param value Add a value
	 */
	public void addValue(T value) {
		linedData.addValue(value);
	}

	/**
	 * Add a value
	 * @param values value Add values
	 */
	public void addValues(@SuppressWarnings("unchecked") T... values) {
		linedData.addValues(values);
	}

	/**
	 * Get Chart Data
	 * @return LinedData
	 */
	@Override
	public LinedData<T> getChartData() {
		return linedData;
	}

	@Override
	public ChartConfiguration<String> getChartConfiguration() {
		return chartConfig;
	}
}
