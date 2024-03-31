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
import org.wicketstuff.jqplot.lib.data.MeterData;
import org.wicketstuff.jqplot.lib.elements.Title;
import org.wicketstuff.jqplot.lib.metadata.JqPlotPlugin;

/**
 * Simple implementation of Meter Gauge Chart. This class can/should be extended.
 * 
 * @author inaiat
 */
@JqPlotPlugin(values = {JqPlotResources.MeterGaugeRenderer})
public class MeterGaugeChart extends AbstractChart<MeterData,String> {

    private static final long serialVersionUID = -8122703368130701972L;
    
	private final ChartConfiguration<String> chartConfig;
    
    private MeterData data = new MeterData(0F);

    /**
     * Construtor
     */
    public MeterGaugeChart() {
        this(null);

    }

    /**
     * Construtor
     * @param title The title
     */
    public MeterGaugeChart(String title) {
        this.chartConfig = new ChartConfiguration<String>();
        chartConfig
        .setTitle(new Title(title))
        .seriesDefaultsInstance()
        .setRenderer(JqPlotResources.MeterGaugeRenderer)
        .rendererOptionsInstance();
    }

    /**
     *
     * @param value value
     */
    public void setValue(Float value) {
        data.setValue(value);
    }

    /**
     * 
     * @param value value
     */
    public void setValue(Integer value) {
        data.setValue(value.floatValue());
    }

    public MeterData getChartData() {
        return data;
    }

	@Override
	public ChartConfiguration<String> getChartConfiguration() {
		return chartConfig;
	}
}
