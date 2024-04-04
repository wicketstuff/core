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
package org.wicketstuff.jqplot.lib.elements;

/**
 * The Class ElemContainer.
 *
 * @author inaiat
 */
public class ElemContainer implements Element {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -3849421146501469906L;
    
    /** The plot width. */
    private Float plotWidth;
    
    /** The plot height. */
    private Float plotHeight;

    /**
     * Instantiates a new elem container.
     *
     * @param plotWidth the plot width
     * @param plotHeight the plot height
     */
    public ElemContainer(Float plotWidth, Float plotHeight) {
        this.plotWidth = plotWidth;
        this.plotHeight = plotHeight;
    }

    /**
     * Gets the plot height.
     *
     * @return plotHeight
     */
    public Float getPlotHeight() {
        return plotHeight;
    }

    /**
     * Sets the plot height.
     *
     * @param plotHeight the new plot height
     */
    public void setPlotHeight(Float plotHeight) {
        this.plotHeight = plotHeight;
    }

    /**
     * Gets the plot width.
     *
     * @return plotWidth
     */
    public Float getPlotWidth() {
        return plotWidth;
    }

    /**
     * Sets the plot width.
     *
     * @param plotWidth the new plot width
     */
    public void setPlotWidth(Float plotWidth) {
        this.plotWidth = plotWidth;
    }

    /**
     * Creates the element.
     */
    public static void createElement() {
    }
}
