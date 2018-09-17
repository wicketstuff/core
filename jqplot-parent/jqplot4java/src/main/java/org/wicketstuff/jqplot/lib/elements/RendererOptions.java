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
import java.util.List;

/**
 * Renderer options.
 *
 * @author inaiat
 */
public class RendererOptions implements Element {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -3555383297912526665L;
    
    /** The show data labels. */
    private Boolean showDataLabels;
    
    /** The data labels. */
    private String dataLabels;
    
    /** The slice margin. */
    private Integer sliceMargin;
    
    /** The start angle. */
    private Integer startAngle;
    
    /** The bar direction. */
    private String barDirection;
    
    /** The highlight mouse down. */
    private Boolean highlightMouseDown;
    
    /** The bar margi	n. */
    private Integer barMargin;

    /** The bar width. */
    private Integer barWidth;
    
    /** The bubble gradients. */
    private Boolean bubbleGradients;
    
    /** The bubble alpha. */
    private Float bubbleAlpha;
    
    /** The highlight alpha. */
    private Float highlightAlpha;
    
    /** The show lables. */
    private Boolean showLables;
    
    /** The show tick labels. */
    private Boolean showTickLabels;
    
    /** The intervals. */
    private List<Integer> intervals;
    
    /** The interval colors. */
    private List<String> intervalColors;
    
    /** The min. */
    private Integer min;
    
    /** The max. */
    private Integer max;
    
    /** The label. */
    private String label;
    
    /** The label position. */
    private String labelPosition;
    
    /** The label height adjust. */
    private Integer labelHeightAdjust;
    
    /** The interval outer radius. */
    private Integer intervalOuterRadius;
    
    /** The line width. */
    private Integer lineWidth;
    
    /** The fill. */
    private Boolean fill;
    
    /** The fillToZero */
    private Boolean fillToZero;
    
    /** The smooth. */
    private Boolean smooth;
    
    /** The number rows. */
    private Integer numberRows;
    
    /** The margin top. */
    private String marginTop;
    
    /** The padding. */
    private Integer padding;
    
    /** The animation. */
    private Animation animation;
    
    /** The vary bar color. */
    private Boolean varyBarColor;

    /** The force tick at zero. */
    private Boolean forceTickAt0;

    /** The force tick at 100. */
    private Boolean forceTickAt100;
    
    /**
     * Smooth.
     *
     * @param smooth the smooth
     * @return the renderer options
     */
    public RendererOptions smooth(Boolean smooth) {
    	this.setSmooth(smooth);
    	return this;
    }
    
    /**
     * Vary bar color.
     *
     * @param varyBarColor the vary bar color
     * @return the renderer options
     */
    public RendererOptions varyBarColor(Boolean varyBarColor) {
    	this.setVaryBarColor(varyBarColor);
    	return this;
    }

    /**
     * Gets the padding.
     *
     * @return padding
     */
    public Integer getPadding() {
        return padding;
    }

    /**
     * Sets the padding.
     *
     * @param padding the new padding
	 * @return RendererOptions
	 */
    public RendererOptions setPadding(Integer padding) {
        this.padding = padding;
        return this;
    }

    /**
     * Gets the margin top.
     *
     * @return marginTop
     */
    public String getMarginTop() {
        return marginTop;
    }

    /**
     * Sets the margin top.
     *
     * @param marginTop the new margin top
	 * @return RendererOptions
	 */
    public RendererOptions setMarginTop(String marginTop) {
        this.marginTop = marginTop;
        return this;
    }

    /**
     * Gets the number rows.
     *
     * @return numberRows
     */
    public Integer getNumberRows() {
        return numberRows;
    }

    /**
     * Sets the number rows.
     *
     * @param numberRows the new number rows
	 * @return RendererOptions
	 */
    public RendererOptions setNumberRows(Integer numberRows) {
        this.numberRows = numberRows;
        return this;
    }

    /**
     * Gets the smooth.
     *
     * @return smooth true ou false
     */
    public Boolean getSmooth() {
        return smooth;
    }

    /**
     * Sets the smooth.
     *
     * @param smooth the new smooth
	 * @return RendererOptions
	 */
    public RendererOptions setSmooth(Boolean smooth) {
        this.smooth = smooth;
        return this;
    }

    /**
     * Gets the fill.
     *
     * @return fill true ou false
     */
    public Boolean getFill() {
        return fill;
    }

    /**
     * Sets the fill.
     *
     * @param fill the new fill
	 * @return RendererOptions
	 */
    public RendererOptions setFill(Boolean fill) {
        this.fill = fill;
        return this;
    }
    
    /**
     * Gets the fillToZero.
     *
     * @return fillToZero true ou false
     */
    public Boolean getFillToZero() {
        return fillToZero;
    }

    /**
     * Sets the fillToZero.
     *
     * @param fillToZero the new fillToZero
     */
    public void setFillToZero(Boolean fillToZero) {
        this.fillToZero = fillToZero;
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
	 * @return RendererOptions
	 */
    public RendererOptions setLineWidth(Integer lineWidth) {
        this.lineWidth = lineWidth;
        return this;
    }

    /**
     * Gets the interval colors.
     *
     * @return Lista de cores intervalColors
     */
    public List<String> getIntervalColors() {
        if (intervalColors == null) {
            intervalColors = new ArrayList<String>();
        }
        return intervalColors;
    }

    /**
     * Sets the interval colors.
     *
     * @param intervalColors the new interval colors
	 * @return RendererOptions
	 */
    public RendererOptions setIntervalColors(List<String> intervalColors) {
        this.intervalColors = intervalColors;
        return this;
    }

    /**
     * Gets the intervals.
     *
     * @return lista  de intervalos
     */
    public List<Integer> getIntervals() {
        if (intervals == null) {
            intervals = new ArrayList<Integer>();
        }

        return intervals;
    }

    /**
     * Sets the intervals.
     *
     * @param intervals the new intervals
	 * @return RendererOptions
	 */
    public RendererOptions setIntervals(List<Integer> intervals) {
        this.intervals = intervals;
        return this;
    }

    /**
     * Gets the interval outer radius.
     *
     * @return intervalOuterRadius
     */
    public Integer getIntervalOuterRadius() {
        return intervalOuterRadius;
    }

    /**
     * Sets the interval outer radius.
     *
     * @param intervalOuterRadius the new interval outer radius
	 * @return RendererOptions
	 */
    public RendererOptions setIntervalOuterRadius(Integer intervalOuterRadius) {
        this.intervalOuterRadius = intervalOuterRadius;
        return this;
    }

    /**
     * Gets the label.
     *
     * @return label
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets the label.
     *
     * @param label the new label
	 * @return RendererOptions
	 */
    public RendererOptions setLabel(String label) {
        this.label = label;
        return this;
    }

    /**
     * Gets the label height adjust.
     *
     * @return labelHeightAdjust
     */
    public Integer getLabelHeightAdjust() {
        return labelHeightAdjust;
    }

    /**
     * Sets the label height adjust.
     *
     * @param labelHeightAdjust the new label height adjust
	 * @return RendererOptions
	 */
    public RendererOptions setLabelHeightAdjust(Integer labelHeightAdjust) {
        this.labelHeightAdjust = labelHeightAdjust;
        return this;
    }

    /**
     * Gets the label position.
     *
     * @return laelPosition
     */
    public String getLabelPosition() {
        return labelPosition;
    }

    /**
     * Sets the label position.
     *
     * @param labelPosition the new label position
	 * @return RendererOptions
	 */
    public RendererOptions setLabelPosition(String labelPosition) {
        this.labelPosition = labelPosition;
        return this;
    }

    /**
     * Gets the max.
     *
     * @return max
     */
    public Integer getMax() {
        return max;
    }

    /**
     * Sets the max.
     *
     * @param max the new max
	 * @return RendererOptions
	 */
    public RendererOptions setMax(Integer max) {
        this.max = max;
        return this;
    }

    /**
     * Gets the min.
     *
     * @return min
     */
    public Integer getMin() {
        return min;
    }

    /**
     * Sets the min.
     *
     * @param min the new min
	 * @return RendererOptions
	 */
    public RendererOptions setMin(Integer min) {
        this.min = min;
        return this;
    }

    /**
     * Gets the show tick labels.
     *
     * @return showTickLabels
     */
    public Boolean getShowTickLabels() {
        return showTickLabels;
    }

    /**
     * Sets the show tick labels.
     *
     * @param showTickLabels the new show tick labels
	 * @return RendererOptions
	 */
    public RendererOptions setShowTickLabels(Boolean showTickLabels) {
        this.showTickLabels = showTickLabels;
        return this;
    }

    /**
     * Gets the show lables.
     *
     * @return showLables flag true ou false
     */
    public Boolean getShowLables() {
        return showLables;
    }

    /**
     * Sets the show lables.
     *
     * @param showLables the new show lables
	 * @return RendererOptions
	 */
    public RendererOptions setShowLables(Boolean showLables) {
        this.showLables = showLables;
        return this;
    }

    /**
     * Gets the bubble alpha.
     *
     * @return bubbleAlpha
     */
    public Float getBubbleAlpha() {
        return bubbleAlpha;
    }

    /**
     * Sets the bubble alpha.
     *
     * @param bubbleAlpha the new bubble alpha
	 * @return RendererOptions
	 */
    public RendererOptions setBubbleAlpha(Float bubbleAlpha) {
        this.bubbleAlpha = bubbleAlpha;
        return this;
    }

    /**
     * Gets the highlight alpha.
     *
     * @return highLigthAlpha
     */
    public Float getHighlightAlpha() {
        return highlightAlpha;
    }

    /**
     * Sets the highlight alpha.
     *
     * @param highlightAlpha the new highlight alpha
	 * @return RendererOptions
	 */
    public RendererOptions setHighlightAlpha(Float highlightAlpha) {
        this.highlightAlpha = highlightAlpha;
        return this;
    }

    /**
     * Gets the bubble gradients.
     *
     * @return bubbleGradients
     */
    public Boolean getBubbleGradients() {
        return bubbleGradients;
    }

    /**
     * Sets the bubble gradients.
     *
     * @param bubbleGradients the new bubble gradients
	 * @return RendererOptions
	 */
    public RendererOptions setBubbleGradients(Boolean bubbleGradients) {
        this.bubbleGradients = bubbleGradients;
        return this;
    }

    /**
     * Gets the bar margin.
     *
     * @return barMargin
     */
    public Integer getBarMargin() {
        return barMargin;
    }

    /**
     * Sets the bar margin.
     *
     * @param barMargin the new bar margin
	 * @return RendererOptions
	 */
    public RendererOptions setBarMargin(Integer barMargin) {
        this.barMargin = barMargin;
        return this;
    }

    /**
     * Gets the bar width.
     *
     * @return barWidth
     */
    public Integer getBarWidth() {
        return barWidth;
    }

    /**
     * Sets the bar width.
     *
     * @param barWidth the new bar width
	 * @return RendererOptions
	 */
    public RendererOptions setBarWidth(Integer barWidth) {
        this.barWidth = barWidth;
        return this;
    }

    /**
     * Checks if is highlight mouse down.
     *
     * @return highLigthMouseDown
     */
    public Boolean isHighlightMouseDown() {
        return highlightMouseDown;
    }

    /**
     * Sets the highlight mouse down.
     *
     * @param highlightMouseDown the new highlight mouse down
	 * @return RendererOptions
	 */
    public RendererOptions setHighlightMouseDown(Boolean highlightMouseDown) {
        this.highlightMouseDown = highlightMouseDown;
        return this;
    }

    /**
     * Gets the bar direction.
     *
     * @return barDirection
	 * @return RendererOptions
	 */
    public String getBarDirection() {
        return barDirection;
    }

    /**
     * Sets the bar direction.
     *
     * @param barDirection the new bar direction
	 * @return RendererOptions
     */
    public RendererOptions setBarDirection(String barDirection) {
        this.barDirection = barDirection;
        return this;
    }

    /**
     * Checks if is fill zero.
     *
     * @return fillZero true ou false
     */
    public Boolean isFillZero() {
        return fillZero;
    }

    /**
     * Sets the fill zero.
     *
     * @param fillZero the new fill zero
	 * @return RendererOptions
     */
    public RendererOptions setFillZero(Boolean fillZero) {
        this.fillZero = fillZero;
        return this;
    }
    
    /** The fill zero. */
    private Boolean fillZero;

    /**
     * Gets the show data labels.
     *
     * @return the showDataLabels
     */
    public Boolean getShowDataLabels() {
        return showDataLabels;
    }

    /**
     * Sets the show data labels.
     *
     * @param showDataLabels the showDataLabels to set
	 * @return RendererOptions
     */
    public RendererOptions setShowDataLabels(Boolean showDataLabels) {
        this.showDataLabels = showDataLabels;
        return this;
    }

    /**
     * Gets the data labels.
     *
     * @return the dataLabels
     */
    public String getDataLabels() {
        return dataLabels;
    }

    /**
     * Sets the data labels.
     *
     * @param dataLabels the dataLabels to set
	 * @return RendererOptions
     */
    public RendererOptions setDataLabels(String dataLabels) {
        this.dataLabels = dataLabels;
        return this;
    }

    /**
     * Gets the slice margin.
     *
     * @return the sliceMargin
     */
    public Integer getSliceMargin() {
        return sliceMargin;
    }

    /**
     * Sets the slice margin.
     *
     * @param sliceMargin the sliceMargin to set
	 * @return RendererOptions
     */
    public RendererOptions setSliceMargin(Integer sliceMargin) {
        this.sliceMargin = sliceMargin;
        return this;
    }

    /**
     * Gets the start angle.
     *
     * @return the startAngle
     */
    public Integer getStartAngle() {
        return startAngle;
    }

    /**
     * Sets the start angle.
     *
     * @param startAngle the startAngle to set
	 * @return RendererOptions
     */
    public RendererOptions setStartAngle(Integer startAngle) {
        this.startAngle = startAngle;
        return this;
    }

    /**
     * Gets the animation.
     *
     * @return the animation
     */
    public Animation getAnimation() {
        return animation;
    }

    /**
     * Sets the animation.
     *
     * @param animation object for setting
     * different animation speed
	 * @return RendererOptions
     */
    public RendererOptions setAnimation(Animation animation) {
        this.animation = animation;
        return this;
    }

	/**
	 * Gets the vary bar color.
	 *
	 * @return the vary bar color
	 */
	public Boolean getVaryBarColor() {
		return varyBarColor;
	}

	/**
	 * Sets the vary bar color.
	 *
	 * @param varyBarColor the new vary bar color
	 * @return RendererOptions
	 */
	public RendererOptions setVaryBarColor(Boolean varyBarColor) {
		this.varyBarColor = varyBarColor;
		return this;
	}

	/**
	 * Gets the force tick at zero.
	 *
	 * @return the force tick at zero
	 */
	public Boolean getForceTickAt0() {
		return forceTickAt0;
	}

	/**
	 * Sets the force tick at zero.
	 *
	 * @param forceTickAt0 the new force tick at zero
	 * @return RendererOptions
	 */
	public RendererOptions setForceTickAt0(Boolean forceTickAt0) {
		this.forceTickAt0 = forceTickAt0;
		return this;
	}

	/**
	 * Gets the force tick at 100.
	 *
	 * @return the force tick at 100
	 */
	public Boolean getForceTickAt100() {
		return forceTickAt100;
	}

	/**
	 * Sets the force tick at 100.
	 *
	 * @param forceTickAt100 the new force tick at 100
	 * @return RendererOptions
	 */
	public RendererOptions setForceTickAt100(Boolean forceTickAt100) {
		this.forceTickAt100 = forceTickAt100;
		return this;
	}
}
