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

import org.wicketstuff.jqplot.lib.axis.Axis;

// TODO: Auto-generated Javadoc
/**
 * Object representing the grid on which the plot is drawn.  The grid in this context is the area bounded by the axes, 
 * the area which will contain the series.  Note, the series are drawn on their own canvas.  
 * The Grid object cannot be instantiated directly, but is created by the Plot oject.  
 * Grid properties can be set or overriden by the options passed in from the user. 
 * 
 * @param <T> Type of {@link Axis}
 * 
 * @author inaiat
 */
public class Grid<T extends Serializable> implements Element {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 5478580499167992682L;
    
    /** The draw grid lines. */
    private Boolean drawGridLines;
    
    /** The grid line color. */
    private String gridLineColor;
    
    /** The grid line width. */
    private Double gridLineWidth;    
    
    /** The background. */
    private String background;
    
    /** The border color. */
    private String borderColor;
    
    /** The border width. */
    private Double borderWidth;
    
    /** The draw border. */
    private Boolean drawBorder;
    
    /** The shadow. */
    private Boolean shadow;
    
    /** The shadow angle. */
    private Double shadowAngle;
    
    /** The shadow offset. */
    private Double shadowOffset;
    
    /** The shadow width. */
    private Double shadowWidth;
    
    /** The shadow depth. */
    private Double shadowDepth;
    
    /** The shadow color. */
    private String shadowColor;
    
    /** The shadow alpha. */
    private String shadowAlpha;
    
    /** The left. */
    private Float left;
    
    /** The top. */
    private Float top;
    
    /** The right. */
    private Float right;
    
    /** The bottom. */
    private Float bottom;
    
    /** The width. */
    private Float width;
    
    /** The height. */
    private Float height;
    
    /** The axis. */
    private Axis<T>[] axis;
    
    /** The renderer options. */
    private String[] rendererOptions;

    /**
     * Gets the axis.
     *
     * @return array de Axis
     */
    public Axis<T>[] getAxis() {
        return axis;
    }

    /**
     * Sets the axis.
     *
     * @param axis the new axis
     */
    public void setAxis(Axis<T>[] axis) {
        this.axis = axis;
    }

    /**
     * Gets the background.
     *
     * @return background
     */
    public String getBackground() {
        return background;
    }

    /**
     * Sets the background.
     *
     * @param background the new background
     */
    public void setBackground(String background) {
        this.background = background;
    }

    /**
     * Gets the border color.
     *
     * @return borderColor
     */
    public String getBorderColor() {
        return borderColor;
    }

    /**
     * Sets the border color.
     *
     * @param borderColor the new border color
     */
    public void setBorderColor(String borderColor) {
        this.borderColor = borderColor;
    }

    /**
     * Gets the border width.
     *
     * @return borderWidth
     */
    public Double getBorderWidth() {
        return borderWidth;
    }

    /**
     * Sets the border width.
     *
     * @param borderWidth the new border width
     */
    public void setBorderWidth(Double borderWidth) {
        this.borderWidth = borderWidth;
    }

    /**
     * Gets the bottom.
     *
     * @return bottom
     */
    public Float getBottom() {
        return bottom;
    }

    /**
     * Sets the bottom.
     *
     * @param bottom the new bottom
     */
    public void setBottom(Float bottom) {
        this.bottom = bottom;
    }

    /**
     * Gets the draw border.
     *
     * @return drawBorder true ou false
     */
    public Boolean getDrawBorder() {
        return drawBorder;
    }

    /**
     * Sets the draw border.
     *
     * @param drawBorder the new draw border
     */
    public void setDrawBorder(Boolean drawBorder) {
        this.drawBorder = drawBorder;
    }

    /**
     * Gets the draw grid lines.
     *
     * @return drawGridLines true ou false
     */
    public Boolean getDrawGridLines() {
        return drawGridLines;
    }

    /**
     * Sets the draw grid lines.
     *
     * @param drawGridLines the new draw grid lines
     */
    public void setDrawGridLines(Boolean drawGridLines) {
        this.drawGridLines = drawGridLines;
    }

    /**
     * Gets the grid line color.
     *
     * @return gridLineColor
     */
    public String getGridLineColor() {
        return gridLineColor;
    }

    /**
     * Sets the grid line color.
     *
     * @param gridLineColor the new grid line color
     */
    public void setGridLineColer(String gridLineColor) {
        this.gridLineColor = gridLineColor;
    }

    /**
     * Gets the grid line width.
     *
     * @return gridLineWidth
     */
    public Double getGridLineWidth() {
        return gridLineWidth;
    }

    /**
     * Sets the grid line width.
     *
     * @param gridLineWidth the new grid line width
     */
    public void setGridLineWidth(Double gridLineWidth) {
        this.gridLineWidth = gridLineWidth;
    }

    /**
     * Gets the height.
     *
     * @return height
     */
    public Float getHeight() {
        return height;
    }

    /**
     * Sets the height.
     *
     * @param height the new height
     */
    public void setHeight(Float height) {
        this.height = height;
    }

    /**
     * Gets the left.
     *
     * @return left
     */
    public Float getLeft() {
        return left;
    }

    /**
     * Sets the left.
     *
     * @param left the new left
     */
    public void setLeft(Float left) {
        this.left = left;
    }

    /**
     * Gets the renderer options.
     *
     * @return rendererOptions
     */
    public String[] getRendererOptions() {
        return rendererOptions;
    }

    /**
     * Sets the renderer options.
     *
     * @param rendererOptions the new renderer options
     */
    public void setRendererOptions(String[] rendererOptions) {
        this.rendererOptions = rendererOptions;
    }

    /**
     * Gets the right.
     *
     * @return right
     */
    public Float getRight() {
        return right;
    }

    /**
     * Sets the right.
     *
     * @param right the new right
     */
    public void setRight(Float right) {
        this.right = right;
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
     */
    public void setShadow(Boolean shadow) {
        this.shadow = shadow;
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
     */
    public void setShadowAlpha(String shadowAlpha) {
        this.shadowAlpha = shadowAlpha;
    }

    /**
     * Gets the shadow angle.
     *
     * @return shadowAngle
     */
    public Double getShadowAngle() {
        return shadowAngle;
    }

    /**
     * Sets the shadow angle.
     *
     * @param shadowAngle the new shadow angle
     */
    public void setShadowAngle(Double shadowAngle) {
        this.shadowAngle = shadowAngle;
    }

    /**
     * Gets the shadow color.
     *
     * @return shadowColor
     */
    public String getShadowColor() {
        return shadowColor;
    }

    /**
     * Sets the shadow color.
     *
     * @param shadowColor the new shadow color
     */
    public void setShadowColor(String shadowColor) {
        this.shadowColor = shadowColor;
    }

    /**
     * Gets the shadow depth.
     *
     * @return shadowDepth
     */
    public Double getShadowDepth() {
        return shadowDepth;
    }

    /**
     * Sets the shadow depth.
     *
     * @param shadowDepth the new shadow depth
     */
    public void setShadowDepth(Double shadowDepth) {
        this.shadowDepth = shadowDepth;
    }

    /**
     * Gets the shadow offset.
     *
     * @return shadowOffSet
     */
    public Double getShadowOffset() {
        return shadowOffset;
    }

    /**
     * Sets the shadow offset.
     *
     * @param shadowOffset the new shadow offset
     */
    public void setShadowOffset(Double shadowOffset) {
        this.shadowOffset = shadowOffset;
    }

    /**
     * Gets the shadow width.
     *
     * @return shadowWidth
     */
    public Double getShadowWidth() {
        return shadowWidth;
    }

    /**
     * Sets the shadow width.
     *
     * @param shadowWidth the new shadow width
     */
    public void setShadowWidth(Double shadowWidth) {
        this.shadowWidth = shadowWidth;
    }

    /**
     * Gets the top.
     *
     * @return top
     */
    public Float getTop() {
        return top;
    }

    /**
     * Sets the top.
     *
     * @param top the new top
     */
    public void setTop(Float top) {
        this.top = top;
    }

    /**
     * Gets the width.
     *
     * @return width
     */
    public Float getWidth() {
        return width;
    }

    /**
     * Sets the width.
     *
     * @param width the new width
     */
    public void setWidth(Float width) {
        this.width = width;
    }
}
