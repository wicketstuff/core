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
package org.wicketstuff.jqplot.lib.renderer.plugin;

import org.wicketstuff.jqplot.lib.JqPlotResources;
import org.wicketstuff.jqplot.lib.renderer.Renderer;

/**
 * Plugin renderer to draw a meter gauge chart.
 *
 * Data consists of a single series with 1 data point to position the gauge needle. 
 * 
 * A meterGauge plot does not support events.
 * 
 * @see <a href="http://www.jqplot.com/docs/files/plugins/jqplot-meterGaugeRenderer-js.html">http://www.jqplot.com/docs/files/plugins/jqplot-meterGaugeRenderer-js.html</a>
 * 
 * @author inaiat
 */
@Deprecated
public class MeterGaugeRenderer implements Renderer {

    private static final long serialVersionUID = -6281926919874791228L;
    private Boolean sortMergedLabels;
    private Renderer tickRenderer;

    /**
     * @return the sortMergedLabels
     */
    public Boolean getSortMergedLabels() {
        return sortMergedLabels;
    }

    /**
     * @param sortMergedLabels the sortMergedLabels to set
     */
    public void setSortMergedLabels(Boolean sortMergedLabels) {
        this.sortMergedLabels = sortMergedLabels;
    }

    /**
     * @return the tickRenderer
     */
    public Renderer getTickRenderer() {
        return tickRenderer;
    }

    /**
     * @param tickRenderer the tickRenderer to set
     */
    public void setTickRenderer(Renderer tickRenderer) {
        this.tickRenderer = tickRenderer;
    }

    /**
     * 
     * @return JqPlotResources
     */
    public JqPlotResources resource() {
        return JqPlotResources.MeterGaugeRenderer;
    }
}
