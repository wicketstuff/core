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
 * Plugin renderer to draw a bubble chart. A Bubble chart has data points displayed as colored circles 
 * with an optional text label inside.
 * 
 * Note that all bubble colors will be the same unless the 'varyBubbleColors' option is set to true.  
 * Colors can be specified in the data array or in the seriesColors array option on the series.  
 * If no colors are defined, the default jqPlot series of 16 colors are used. Colors are automatically 
 * cycled around again if there are more bubbles than colors.  Bubbles are autoscaled by default to fit 
 * within the chart area while maintaining relative sizes.  If the 'autoscaleBubbles' option is set to false, 
 * the r(adius) values in the data array a treated as literal pixel values for the radii of the bubbles.
 *
 * Properties are passed into the bubble renderer in the rendererOptions object of the series options like:
 * 
 * @author inaiat
 */
@Deprecated
public class BubbleRenderer implements Renderer {

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
        return JqPlotResources.BubbleRenderer;
    }
}
