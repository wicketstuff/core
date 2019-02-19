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
import org.wicketstuff.jqplot.lib.data.LabeledData;
import org.wicketstuff.jqplot.lib.data.item.LabeledItem;
import org.wicketstuff.jqplot.lib.elements.Title;
import org.wicketstuff.jqplot.lib.metadata.JqPlotPlugin;

/**
 *
 * Simple implementation of Labeled Line Chart. This class can/should be extended.
 *
 * @author inaiat
 */
@JqPlotPlugin(values = {
    JqPlotResources.DateAxisRenderer,
    JqPlotResources.CanvasTextRenderer,
    JqPlotResources.CategoryAxisRenderer,
    JqPlotResources.DateAxisRenderer,
    JqPlotResources.CanvasAxisLabelRenderer,
    JqPlotResources.CanvasAxisTickRenderer
})
public class LabeledLineChart<T extends Number> extends AbstractChart<LabeledData<T>,String> {

	private static final long serialVersionUID = -6833884146696085085L;

	private final ChartConfiguration<String> chartConfig;

	private LabeledData<T> labeledData = new LabeledData<>();

    /**
     * Construtor
     */
    public LabeledLineChart() {
        this(null, null, null, 15);
    }

    /**
     * Construtor
     * @param title title
     * @param labelX labelX
     * @param labelY labelY
     */
    public LabeledLineChart(String title, String labelX, String labelY) {
        this(title, labelX, labelY, 15);
    }

    /**
     * Construtor
     *
	 * @param title title
	 * @param labelX labelX
	 * @param labelY labelY
     * @param tickAngle tickAngle
     */
    public LabeledLineChart(String title, String labelX, String labelY,
            Integer tickAngle) {
    	this.chartConfig = new ChartConfiguration<>();

    	chartConfig
    		.setTitle(new Title(title))
    		.setLabelX(labelX)
    		.setLabelY(labelY)
    		.axesInstance()
    		.xAxisInstance()
    		.setRenderer(JqPlotResources.DateAxisRenderer)
    		.setLabelRenderer(JqPlotResources.CanvasAxisLabelRenderer)
    		.tickOptionsInstance()
    		.setAngle(tickAngle);
    	chartConfig
    		.axesInstance()
    		.yAxisInstance()
    		.setLabelRenderer(JqPlotResources.CanvasAxisLabelRenderer);
    }

    /**
     * Add a value
     * @param value Add a {@link LabeledItem}
     */
    public void addValue(LabeledItem<T> value) {
        labeledData.addValue(value);
    }

	/**
	 * Add a value
	 * @param values value Add a {@link LabeledItem}
	 */
    public void addValues(@SuppressWarnings("unchecked") LabeledItem<T>... values) {
        labeledData.addValues(values);
    }

    /**
     *
     * @return LabeledData
     */
    @Override
	public LabeledData<T> getChartData() {
        return labeledData;
    }

	@Override
	public ChartConfiguration<String> getChartConfiguration() {
		return chartConfig;
	}
}
