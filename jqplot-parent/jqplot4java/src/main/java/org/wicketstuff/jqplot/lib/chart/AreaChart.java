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

import java.util.List;

import org.wicketstuff.jqplot.lib.ChartConfiguration;
import org.wicketstuff.jqplot.lib.JqPlotResources;
import org.wicketstuff.jqplot.lib.data.AreaFillData;
import org.wicketstuff.jqplot.lib.elements.Title;

/**
 * Simple implementation of Area Chart. This class can/should be extended.
 *
 * @param <T> the generic type
 * @author inaiat
 */
public class AreaChart<T extends Number> extends AbstractChart<AreaFillData<T>,String> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6050878600406620553L;
	
	private final ChartConfiguration<String> chartConfig;

	/** The data. */
	private AreaFillData<T> data = new AreaFillData<T>();
    
    /**
     * Instantiates a new area chart.
     */
    public AreaChart() {
        this(null);
    }

    /**
     * Instantiates a new area chart.
     *
     * @param title the title
     */
    public AreaChart(String title) {
    	chartConfig= new ChartConfiguration<String>();
    	chartConfig
        	.setTitle(new Title(title))
        	.setStackSeries(true)
        	.setShowMarker(false)
        	.seriesDefaultsInstance()
        	.setFill(true);
    	chartConfig
        	.axesInstance()
        	.xAxisInstance()
        	.setRenderer(JqPlotResources.CategoryAxisRenderer);
    }

    /* (non-Javadoc)
     * @see org.wicketstuff.jqplot.lib.Chart#getChartData()
     */
    public AreaFillData<T> getChartData() {
        return data;
    }

    /**
     * Adds the value.
     *
     * @param value the value
     */
    public void addValue(List<T> value) {
        data.addValue(value);
    }

    /**
     * Adds the values.
     *
     * @param value the value
     */
    public void addValues(List<T>... value) {
        data.addValues(value);
    }

	@Override
	public ChartConfiguration<String> getChartConfiguration() {
		return chartConfig;
	}
}
