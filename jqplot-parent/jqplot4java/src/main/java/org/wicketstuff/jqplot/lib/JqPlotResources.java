/*
 *  Copyright 2011 Inaiat.
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
package org.wicketstuff.jqplot.lib;

// TODO: Auto-generated Javadoc
/**
 * 
 * Resources of JqPlot aka Plugins.
 * 
 * @author inaiat
 */
public enum JqPlotResources {

    /** The Highlighter. */
    Highlighter("$.jqplot.Highlighter", "plugins/jqplot.highlighter.min.js"),
    
    /** The Cursor. */
    Cursor("$.jqplot.Cursor","plugins/jqplot.cursor.min.js"),
    
    /** The Bar renderer. */
    BarRenderer("$.jqplot.BarRenderer", "plugins/jqplot.barRenderer.min.js"),
    
    /** The Bubble renderer. */
    BubbleRenderer("$.jqplot.BubbleRenderer", "plugins/jqplot.bubbleRenderer.min.js"),
    
    /** The Pie renderer. */
    PieRenderer("$.jqplot.PieRenderer", "plugins/jqplot.pieRenderer.min.js"),
    
    /** The Donut renderer. */
    DonutRenderer("$.jqplot.DonutRenderer", "plugins/jqplot.donutRenderer.min.js"),
    
    /** The Canvas axis label renderer. */
    CanvasAxisLabelRenderer("$.jqplot.CanvasAxisLabelRenderer", "plugins/jqplot.canvasAxisLabelRenderer.min.js"),
    
    /** The Category axis renderer. */
    CategoryAxisRenderer("$.jqplot.CategoryAxisRenderer", "plugins/jqplot.categoryAxisRenderer.min.js"),
    
    /** The Canvas axis tick renderer. */
    CanvasAxisTickRenderer("$.jqplot.CanvasAxisTickRenderer", "plugins/jqplot.canvasAxisTickRenderer.min.js"),    
    
    /** The Canvas text renderer. */
    CanvasTextRenderer("$.jqplot.CanvasTextRenderer","plugins/jqplot.canvasTextRenderer.min.js"),
    
    /** The CanvasOverlay renderer. */
    CanvasOverlay("$.jqplot.CanvasOverlay", "plugins/jqplot.canvasOverlay.min.js"),

    /** The Date axis renderer. */
    DateAxisRenderer("$.jqplot.DateAxisRenderer","plugins/jqplot.dateAxisRenderer.min.js"),
    
    /** The Meter gauge renderer. */
    MeterGaugeRenderer("$.jqplot.MeterGaugeRenderer","plugins/jqplot.meterGaugeRenderer.min.js"),
    
    /** The Point labels. */
    PointLabels("$.jqplot.PointLabels","plugins/jqplot.pointLabels.min.js"),
    
    /** The Shadow renderer. */
    ShadowRenderer("$.jqplot.ShadowRenderer", "jquery.jqplot.min.js"),

    // Core Renderer 
    /** The Axis tick renderer. */
    AxisTickRenderer("$.jqplot.AxisTickRenderer"),
    
    EnhancedLegendRenderer("$.jqplot.EnhancedLegendRenderer","plugins/jqplot.enhancedLegendRenderer.min.js"),
    
    /** A class of a formatter for the tick text. */
    DefaultTickFormatter("$.jqplot.DefaultTickFormatter");

    
    /** The class name. */
    private String className;
    
    /** The resource. */
    private String resource;

    /**
     * Instantiates a new jq plot resources.
     *
     * @param className the class name
     */
    private JqPlotResources(String className) {
        this(className, null);
    }

    /**
     * Instantiates a new jq plot resources.
     *
     * @param className the class name
     * @param resource the resource
     */
    private JqPlotResources(String className, String resource) {
        this.className = className;
        this.resource = resource;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return className;
    }

    /**
     * Gets the class name.
     *
     * @return the class name
     */
    public String getClassName() {
        return className;
    }

    /**
     * Gets the resource.
     *
     * @return the resource
     */
    public String getResource() {
        return resource;
    }
}

