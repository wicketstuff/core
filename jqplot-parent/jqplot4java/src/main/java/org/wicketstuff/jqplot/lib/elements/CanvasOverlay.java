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

import java.util.ArrayList;
import java.util.Collection;

/**
 * CanvasOverlay object.  
 * 
 * CanvasOverlay properties can be set or overriden by the options passed in from the user.
 *
 * @author inaiat
 */
public class CanvasOverlay implements Element {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 7228237274262615669L;
    
    /** The name. */
    private String name;
    
    /** The show. */
    private Boolean show;

    /** The line width. */
    private Integer lineWidth;

    /** The line cap. */
    private String lineCap;

    /** The color. */
    private String color;

    /** The shadow. */
    private Boolean shadow;

    /** The showTooltip. */
    private Boolean showTooltip;

    /** The showTooltipPrecision. */
    private Float showTooltipPrecision;

    /** The tooltipLocation. */
    private String tooltipLocation;

    /** The fadeTooltip. */
    private Boolean fadeTooltip;

    /** The tooltipFadeSpeed. */
    private String tooltipFadeSpeed;

    /** The tooltipOffset. */
    private Integer tooltipOffset;

    /** The tooltipFormatString. */
    private String tooltipFormatString;

    /** The xOffset. */
    private String xOffset;
    
    /** The objects */
    private Collection<LineObject> objects;

    /**
     * Instantiates a new canvas overlay.
     */
    public CanvasOverlay() {
    }

    /**
     * Instantiates a new canvas overlay.
     *
     * @param name the name
     */
    public CanvasOverlay(String name) {
        this.name = name;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
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
     * @return lineWidth
     */
    public Integer getLineWidth() {
        return lineWidth;
    }

    /**
     * Sets the line width.
     *
     * @param lineWidth the new line width
	 * @return CanvasOverlay
     */
    public CanvasOverlay setLineWidth(Integer lineWidth) {
        this.lineWidth = lineWidth;
        return this;
    }

    /**
     * Gets the line cap.
     *
     * @return lineCap
     */
    public String getLineCap() {
        return lineCap;
    }

    /**
     * Sets the line cap.
     *
     * @param lineCap the new line cap
	 * @return CanvasOverlay
	 */
    public CanvasOverlay setLineCap(String lineCap) {
        this.lineCap = lineCap;
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
	 * @return CanvasOverlay
	 */
    public CanvasOverlay setColor(String color) {
        this.color = color;
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
	 * @return CanvasOverlay
	 */
    public CanvasOverlay setShadow(Boolean shadow) {
        this.shadow = shadow;
        return this;
    }

    /**
     * Gets the showTooltip.
     *
     * @return the showTooltip
     */
    public Boolean getShowTooltip() {
        return showTooltip;
    }

    /**
     * Sets the showTooltip.
     *
     * @param showTooltip the new showTooltip
     */
    public void setShowTooltip(Boolean showTooltip) {
        this.showTooltip = showTooltip;
    }

    /**
     * Gets the showTooltipPrecision.
     *
     * @return the showTooltipPrecision
     */
    public Float getShowTooltipPrecision() {
        return showTooltipPrecision;
    }

    /**
     * Sets the showTooltipPrecision.
     *
     * @param showTooltipPrecision the new showTooltipPrecision
     */
    public void setShowTooltipPrecision(Float showTooltipPrecision) {
        this.showTooltipPrecision = showTooltipPrecision;
    }

    /**
     * Gets the tooltipLocation.
     *
     * @return the tooltipLocation
     */
    public String getTooltipLocation() {
        return tooltipLocation;
    }

    /**
     * Sets the tooltipLocation.
     *
     * @param tooltipLocation the new tooltipLocation
     */
    public void setTooltipLocation(String tooltipLocation) {
        this.tooltipLocation = tooltipLocation;
    }

    /**
     * Gets the fadeTooltip.
     *
     * @return the fadeTooltip
     */
    public Boolean getFadeTooltip() {
        return fadeTooltip;
    }

    /**
     * Sets the fadeTooltip.
     *
     * @param fadeTooltip the new fadeTooltip
     */
    public void setFadeTooltip(Boolean fadeTooltip) {
        this.fadeTooltip = fadeTooltip;
    }

    /**
     * Gets the tooltipFadeSpeed.
     *
     * @return the tooltipFadeSpeed
     */
    public String getTooltipFadeSpeed() {
        return tooltipFadeSpeed;
    }

    /**
     * Sets the tooltipFadeSpeed.
     *
     * @param tooltipFadeSpeed the new tooltipFadeSpeed
     */
    public void setTooltipFadeSpeed(String tooltipFadeSpeed) {
        this.tooltipFadeSpeed = tooltipFadeSpeed;
    }

    /**
     * Gets the tooltipOffset.
     *
     * @return the tooltipOffset
     */
    public Integer getTooltipOffset() {
        return tooltipOffset;
    }

    /**
     * Sets the tooltipOffset.
     *
     * @param tooltipOffset the new tooltipOffset
     */
    public void setTooltipOffset(Integer tooltipOffset) {
        this.tooltipOffset = tooltipOffset;
    }

    /**
     * Gets the tooltipFormatString.
     *
     * @return the tooltipFormatString
     */
    public String getTooltipFormatString() {
        return tooltipFormatString;
    }

    /**
     * Sets the tooltipFormatString.
     *
     * @param tooltipFormatString the new tooltipFormatString
     */
    public void setTooltipFormatString(String tooltipFormatString) {
        this.tooltipFormatString = tooltipFormatString;
    }

    /**
     * Gets the xOffset.
     *
     * @return the xOffset
     */
    public String getXOffset() {
        return xOffset;
    }

    /**
     * Sets the xOffset.
     *
     * @param xOffset the new xOffset
	 * @return CanvasOverlay
	 */
    public CanvasOverlay setXOffset(String xOffset) {
        this.xOffset = xOffset;
        return this;
    }

	/**
	 * Instantiates the objects.
	 * 
	 * @return the objects
	 */
	public Collection<LineObject> objectsInstance() {
		if (objects == null) {
			this.objects = new ArrayList<LineObject>();
		}
		return this.objects;
	}

	/**
	 * Gets the objects.
	 * 
	 * @return the objects
	 */
	public Collection<LineObject> getObjects() {
		return objects;
	}

	/**
	 * Sets the objects.
	 * 
	 * @param objects
	 *            the objects to set
	 * @return CanvasOverlay
	 */
	public CanvasOverlay setObjects(Collection<LineObject> objects) {
		this.objects = objects;
		return this;
	}

	/**
	 * Get a {@link DashedHorizontalLine} instance
	 * @return DashedHorizontalLine
	 */
	public DashedHorizontalLine dashedHorizontalLineInstance() {
           LineObject lineObject = new LineObject();
           DashedHorizontalLine dashedHorizontalLine = lineObject.dashedHorizontalLineInstance();
		objectsInstance().add(lineObject);
		return dashedHorizontalLine;
	}

	/**
	 * Get a {@link DashedVerticalLine} instance
	 * @return DashedVerticalLine
	 */
	public DashedVerticalLine dashedVerticalLineInstance() {
           LineObject lineObject = new LineObject();
           DashedVerticalLine dashedVerticalLine = lineObject.dashedVerticalLineInstance();
		objectsInstance().add(lineObject);
		return dashedVerticalLine;
	}

	/**
	 * Get a {@link HorizontalLine} instance
	 * @return HorizontalLine
	 */
	public HorizontalLine horizontalLineInstance() {
           LineObject lineObject = new LineObject();
           HorizontalLine horizontalLine = lineObject.horizontalLineInstance();
		objectsInstance().add(lineObject);
		return horizontalLine;
	}

	/**
	 * Get a {@link VerticalLine} instance
	 * @return VerticalLine
	 */
	public VerticalLine verticalLineInstance() {
           LineObject lineObject = new LineObject();
           VerticalLine verticalLine = lineObject.verticalLineInstance();
		objectsInstance().add(lineObject);
		return verticalLine;
	}
}
