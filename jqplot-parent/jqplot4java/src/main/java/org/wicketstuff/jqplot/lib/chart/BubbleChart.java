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
import org.wicketstuff.jqplot.lib.data.BubbleData;
import org.wicketstuff.jqplot.lib.data.item.BubbleItem;
import org.wicketstuff.jqplot.lib.elements.Title;
import org.wicketstuff.jqplot.lib.metadata.JqPlotPlugin;

/**
 * 
 * Simple implementation of Bubble Chart. This class can/should be extended.
 * 
 * @author inaiat
 */
@JqPlotPlugin(values = { JqPlotResources.BubbleRenderer })
public class BubbleChart extends AbstractChart<BubbleData<BubbleItem>, String> {

	private static final long serialVersionUID = -8122703368130701972L;

	private final ChartConfiguration<String> chartConfig;

	private BubbleData<BubbleItem> bubbleData = new BubbleData<BubbleItem>();

	/**
	 * Construtor
	 */
	public BubbleChart() {
		this(null);
	}

	/**
	 * Construtor
	 * 
	 * @param title The title
	 */
	public BubbleChart(String title) {
		this.chartConfig = new ChartConfiguration<String>();
		chartConfig
			.setTitle(new Title(title))
			.seriesDefaultsInstance()
			.setRenderer((JqPlotResources.BubbleRenderer));
	}

	/**
	 * 
	 * @return chartData
	 */
	public BubbleData<BubbleItem> getChartData() {
		return bubbleData;
	}

	/**
	 * Add a value
	 * @param item Add a {@link BubbleItem}
	 */
	public void addValue(BubbleItem item) {
		bubbleData.addValue(item);
	}

	/**
	 * Add a value
	 * @param x x
	 * @param y y
	 * @param radius radius
	 * @param label label
	 */
	public void addValue(Float x, Float y, Float radius, String label) {
		bubbleData.addValue(new BubbleItem(x, y, radius, label));
	}

	/**
	 * Add a value
	 * @param x x
	 * @param y y
	 * @param radius radius
	 * @param label label
	 */
	public void addValue(Integer x, Integer y, Integer radius, String label) {
		bubbleData.addValue(new BubbleItem(x, y, radius, label));
	}

	@Override
	public ChartConfiguration<String> getChartConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}
}
