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

import org.wicketstuff.jqplot.lib.JqPlotResources;

/**
 * The Class Serie.
 *
 * @author inaiat
 */
public class Serie implements Element {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 7138260563176853708L;
    
    /** The label. */
    private String label;
    
    /** The renderer. */
    private JqPlotResources renderer;
    
    /** The renderer options. */
    private RendererOptions rendererOptions;
    
    /** The fill. */
    private Boolean fill;
    
    /** The line width. */
    private Integer lineWidth;
    
    /** The marker options. */
    private MarkerOptions markerOptions;
    
    /** The show marker. */
    private Boolean showMarker;
    
    /** The show alpha. */
    private Boolean showAlpha;
    
    /** The shadow. */
    private Boolean shadow;
    
    /** The shadow alpha. */
    private String shadowAlpha;

    /** The shadow depth. */
    private Integer shadowDepth;
    
    /** The show line. */
    private Boolean showLine;
    
    /** The color. */
    private String color;

    /**
     * Instantiates a new serie.
     */
    public Serie() {
    }  

    /**
     * Instantiates a new serie.
     *
     * @param label the label
     */
    public Serie(String label) {
    	this.label = label;
    }

    /**
     * Line width.
     *
     * @param lineWidth the line width
     * @return the serie
     */
    public Serie lineWidth(Integer lineWidth) {
    	this.lineWidth = lineWidth;
    	return this;
    }
    
    /**
     * Marker options.
     *
     * @param markerOptions the marker options
     * @return the serie
     */
    public Serie markerOptions(MarkerOptions markerOptions) {
    	this.markerOptions = markerOptions;
    	return this;
    }
    
    /**
     * Show line.
     *
     * @param showLine the show line
     * @return the serie
     */
    public Serie showLine(Boolean showLine) {
    	this.setShowLine(showLine);
    	return this;
    }
    
    /**
     * Renderer options.
     *
     * @param rendererOptions the renderer options
     * @return the serie
     */
    public Serie rendererOptions(RendererOptions rendererOptions) {
    	this.rendererOptions = rendererOptions;
    	return this;
    }
    
    /**
     * Renderer.
     *
     * @param renderer the renderer
     * @return the serie
     */
    public Serie renderer(JqPlotResources renderer) {
    	this.renderer = renderer;
    	return this;
    }

    /**
     * Gets the shadow alpha.
     *
     * @return shadowAlpha
     */
    public String getShadowAlpha() {
        return shadowAlpha;
    }

    /**
     * Sets the shadow alpha.
     *
     * @param shadowAlpha the new shadow alpha
	 * @return Serie
     */
    public Serie setShadowAlpha(String shadowAlpha) {
        this.shadowAlpha = shadowAlpha;
        return this;
    }

    /**
     * Gets the shadow depth.
     *
     * @return shadowDepth
     */
    public Integer getShadowDepth() {
        return shadowDepth;
    }

    /**
     * Sets the shadow depth.
     *
     * @param shadowDepth the new shadow depth
	 * @return Serie
     */
    public Serie setShadowDepth(Integer shadowDepth) {
        this.shadowDepth = shadowDepth;
        return this;
    }

    /**
     * Gets the show alpha.
     *
     * @return showAlpha true ou false
     */
    public Boolean getShowAlpha() {
        return showAlpha;
    }

    /**
     * Sets the show alpha.
     *
     * @param showAlpha the new show alpha
	 * @return Serie
     */
    public Serie setShowAlpha(Boolean showAlpha) {
        this.showAlpha = showAlpha;
        return this;
    }

    /**
     * Gets the shadow.
     *
     * @return shadow
     */
    public Boolean getShadow() {
        return shadow;
    }

    /**
     * Sets the shadow.
     *
     * @param shadow the new shadow
	 * @return Serie
     */
    public Serie setShadow(Boolean shadow) {
        this.shadow = shadow;
        return this;
    }

    /**
     * Gets the show marker.
     *
     * @return the show marker
     */
    public Boolean getShowMarker() {
        return showMarker;
    }

    /**
     * Sets the show marker.
     *
     * @param showMarker the new show marker
	 * @return Serie
	 */
    public Serie setShowMarker(Boolean showMarker) {
        this.showMarker = showMarker;
        return this;
    }
    
    /**
     * Gets the marker options.
     *
     * @return the marker options
     */
    public MarkerOptions getMarkerOptions() {
        return markerOptions;
    }

    /**
     * Sets the marker options.
     *
     * @param markerOptions the new marker options
	 * @return Serie
     */
    public Serie setMarkerOptions(MarkerOptions markerOptions) {
        this.markerOptions = markerOptions;
        return this;
    }

    /**
     * Gets the line width.
     *
     * @return the line width
     */
    public Integer getLineWidth() {
        return lineWidth;
    }

    /**
     * Sets the line width.
     *
     * @param lineWidth the new line width
	 * @return Serie
     */
    public Serie setLineWidth(Integer lineWidth) {
        this.lineWidth = lineWidth;
        return this;
    }

    /**
     * Gets the renderer options.
     *
     * @return the renderer options
     */
    public RendererOptions getRendererOptions() {
        return rendererOptions;
    }

    /**
     * Sets the renderer options.
     *
     * @param rendererOptions the new renderer options
	 * @return Serie
     */
    public Serie setRendererOptions(RendererOptions rendererOptions) {
        this.rendererOptions = rendererOptions;
        return this;
    }

    
    /**
     * Gets the label.
     *
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets the label.
     *
     * @param label the label to set
	 * @return Serie
     */
    public Serie setLabel(String label) {
        this.label = label;
        return this;
    }

    /**
     * Gets the renderer.
     *
     * @return the renderer
     */
    public JqPlotResources getRenderer() {
        return renderer;
    }

    /**
     * Sets the renderer.
     *
     * @param renderer the renderer to set
	 * @return Serie
     */
    public Serie setRenderer(JqPlotResources renderer) {
        this.renderer = renderer;
        return this;
    }

    /**
     * Gets the fill.
     *
     * @return the fill
     */
    public Boolean getFill() {
        return fill;
    }

    /**
     * Sets the fill.
     *
     * @param fill the fill to set
	 * @return Serie
	 */
    public Serie setFill(Boolean fill) {
        this.fill = fill;
        return this;
    }


    /**
     * Gets the color.
     *
     * @return the color
     */
    public String getColor() {
        return color;
    }

    /**
     * Sets the color.
     *
     * @param color the new color
	 * @return Serie
     */
    public Serie setColor(String color) {
        this.color = color;
        return this;
    }
	
	/**
	 * Gets the show line.
	 *
	 * @return the show line
	 */
	public Boolean getShowLine() {
		return showLine;
	}

	/**
	 * Sets the show line.
	 *
	 * @param showLine the new show line
	 * @return Serie
	 */
	public Serie setShowLine(Boolean showLine) {
		this.showLine = showLine;
		return this;
	}

	/**
	 * Get renderer options instance
	 * @return RendererOptions
	 */
	public RendererOptions rendererOptionsInstance() {
		if (rendererOptions==null) {
			this.rendererOptions = new RendererOptions();
		}
		return rendererOptions;
	}
	
	public MarkerOptions markerOptionsInstance() {
		if (markerOptions==null) {
			markerOptions = new MarkerOptions();
		}
		return markerOptions;
	}
}
