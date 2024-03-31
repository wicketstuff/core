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

import java.io.Serializable;

import org.wicketstuff.jqplot.lib.JqPlotResources;

// TODO: Auto-generated Javadoc
/**
 * The default jqPlot marker renderer, rendering the points on the line.
 * 
 * @author inaiat
 */
public class MarkerOptions implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 3501054776797812489L;
    
    /** The show. */
    private Boolean show;
    
    /** The style. */
    private String style;
    
    /** The line width. */
    private Integer lineWidth;
    
    /** The size. */
    private Float size;
    
    /** The color. */
    private String color;
    
    /** The shadow. */
    private Boolean shadow;
    
    /** The shadow angle. */
    private Float shadowAngle;
    
    /** The shadow offset. */
    private Integer shadowOffset;
    
    /** The shadow depth. */
    private Integer shadowDepth;
    
    /** The shadow alpha. */
    private String shadowAlpha;
    
    /** The shadow renderer. */
    private JqPlotResources shadowRenderer;
    
    /** The shape renderer. */
    private JqPlotResources shapeRenderer;
    
    /**
     * Instantiates a new marker options.
     */
    public MarkerOptions() {
	}   

    
    /**
     * Style.
     *
     * @param style the style
     * @return the marker options
     */
    public MarkerOptions style(String style) {
    	this.style = style;
    	return this;
    }
    
    /**
     * Size.
     *
     * @param size the size
     * @return the marker options
     */
    public MarkerOptions size(Float size) {
    	this.size = size;
    	return this;
    }
  
    /**
     * Line width.
     *
     * @param lineWidth the line width
     * @return the marker options
     */
    public MarkerOptions lineWidth(Integer lineWidth) {
    	this.lineWidth = lineWidth;
    	return this;
    }
    
    /**
     * Show.
     *
     * @param show the show
     * @return the marker options
     */
    public MarkerOptions show(Boolean show) {
    	this.show = show;
    	return this;
    }    

    /**
     * Gets the style.
     *
     * @return style
     */
    public String getStyle() {
        return style;
    }

    /**
     * Sets the style.
     *
     * @param style the new style
     */
    public void setStyle(String style) {
        this.style = style;
    }

	/**
	 * Gets the show.
	 *
	 * @return the show
	 */
	public Boolean getShow() {
		return show;
	}

	/**
	 * Sets the show.
	 *
	 * @param show the new show
	 */
	public void setShow(Boolean show) {
		this.show = show;
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
	 */
	public void setLineWidth(Integer lineWidth) {
		this.lineWidth = lineWidth;
	}

	/**
	 * Gets the size.
	 *
	 * @return the size
	 */
	public Float getSize() {
		return size;
	}

	/**
	 * Sets the size.
	 *
	 * @param size the new size
	 */
	public void setSize(Float size) {
		this.size = size;
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
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * Gets the shadow.
	 *
	 * @return the shadow
	 */
	public Boolean getShadow() {
		return shadow;
	}

	/**
	 * Sets the shadow.
	 *
	 * @param shadow the new shadow
	 */
	public void setShadow(Boolean shadow) {
		this.shadow = shadow;
	}

	/**
	 * Gets the shadow angle.
	 *
	 * @return the shadow angle
	 */
	public Float getShadowAngle() {
		return shadowAngle;
	}

	/**
	 * Sets the shadow angle.
	 *
	 * @param shadowAngle the new shadow angle
	 */
	public void setShadowAngle(Float shadowAngle) {
		this.shadowAngle = shadowAngle;
	}

	/**
	 * Gets the shadow offset.
	 *
	 * @return the shadow offset
	 */
	public Integer getShadowOffset() {
		return shadowOffset;
	}

	/**
	 * Sets the shadow offset.
	 *
	 * @param shadowOffset the new shadow offset
	 */
	public void setShadowOffset(Integer shadowOffset) {
		this.shadowOffset = shadowOffset;
	}

	/**
	 * Gets the shadow depth.
	 *
	 * @return the shadow depth
	 */
	public Integer getShadowDepth() {
		return shadowDepth;
	}

	/**
	 * Sets the shadow depth.
	 *
	 * @param shadowDepth the new shadow depth
	 */
	public void setShadowDepth(Integer shadowDepth) {
		this.shadowDepth = shadowDepth;
	}

	/**
	 * Gets the shadow alpha.
	 *
	 * @return the shadow alpha
	 */
	public String getShadowAlpha() {
		return shadowAlpha;
	}

	/**
	 * Sets the shadow alpha.
	 *
	 * @param shadowAlpha the new shadow alpha
	 */
	public void setShadowAlpha(String shadowAlpha) {
		this.shadowAlpha = shadowAlpha;
	}

	/**
	 * Gets the shadow renderer.
	 *
	 * @return the shadow renderer
	 */
	public JqPlotResources getShadowRenderer() {
		return shadowRenderer;
	}

	/**
	 * Sets the shadow renderer.
	 *
	 * @param shadowRenderer the new shadow renderer
	 */
	public void setShadowRenderer(JqPlotResources shadowRenderer) {
		this.shadowRenderer = shadowRenderer;
	}

	/**
	 * Gets the shape renderer.
	 *
	 * @return the shape renderer
	 */
	public JqPlotResources getShapeRenderer() {
		return shapeRenderer;
	}

	/**
	 * Sets the shape renderer.
	 *
	 * @param shapeRenderer the new shape renderer
	 */
	public void setShapeRenderer(JqPlotResources shapeRenderer) {
		this.shapeRenderer = shapeRenderer;
	}    
    
}
