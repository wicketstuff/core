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
 * Legend object.  
 * 
 * Legend properties can be set or overriden by the options passed in from the user.
 *
 * @author inaiat
 */
public class Legend implements Element {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 7228235274262615669L;
    
    /** The options. */
    private String options;
    
    /** The placement. */
    private String placement;
    
    /** The location. */
    private Location location;
    
    /** The border. */
    private String border;
    
    /** The background. */
    private String background;
    
    /** The font family. */
    private String fontFamily;
    
    /** The margin top. */
    private String marginTop;
    
    /** The margin right. */
    private String marginRight;
    
    /** The margin bottom. */
    private String marginBottom;
    
    /** The margin left. */
    private String marginLeft;
    
    /** The renderer. */
    private JqPlotResources renderer;
    
    /** The xoffset. */
    private Double xoffset;
    
    /** The yoffset. */
    private Double yoffset;
    
    /** The show. */
    private Boolean show;
    
    /** The show lables. */
    private Boolean showLables;
    
    /** The show swatches. */
    private Boolean showSwatches;
    
    /** The pre draw. */
    private Boolean preDraw;
    
    /** The escape html. */
    private Boolean escapeHtml;
    
    /** The lables. */
    private String[] lables;

    /** The Render Options */
    private LegendRenderer rendererOptions;

    /**
     * Instantiates a new legend.
     */
    public Legend() {
    }

    /**
     * Instantiates a new legend.
     *
     * @param show the show
     * @param location the location
     */
    public Legend(boolean show, Location location) {
        this.show = show;
        this.location = location;
    }

    /**
     * Instantiates a new legend.
     *
     * @param options the options
     */
    public Legend(String options) {
        this.options = options;
    }

    /**
     * Gets the background.
     *
     * @return the background
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
     * Gets the border.
     *
     * @return the border
     */
    public String getBorder() {
        return border;
    }

    /**
     * Sets the border.
     *
     * @param border the new border
     */
    public void setBorder(String border) {
        this.border = border;
    }

    /**
     * Gets the escape html.
     *
     * @return the escape html
     */
    public Boolean getEscapeHtml() {
        return escapeHtml;
    }

    /**
     * Sets the escape html.
     *
     * @param escapeHtml the new escape html
     */
    public void setEscapeHtml(Boolean escapeHtml) {
        this.escapeHtml = escapeHtml;
    }

    /**
     * Gets the font family.
     *
     * @return the font family
     */
    public String getFontFamily() {
        return fontFamily;
    }

    /**
     * Sets the font family.
     *
     * @param fontFamily the new font family
     */
    public void setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
    }

    /**
     * Gets the lables.
     *
     * @return the lables
     */
    public String[] getLables() {
        return lables;
    }

    /**
     * Sets the lables.
     *
     * @param lables the new lables
     */
    public void setLables(String[] lables) {
        this.lables = lables;
    }

    /**
     * Gets the location.
     *
     * @return the location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Sets the location.
     *
     * @param location the new location
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Gets the margin bottom.
     *
     * @return the margin bottom
     */
    public String getMarginBottom() {
        return marginBottom;
    }

    /**
     * Sets the margin bottom.
     *
     * @param marginBottom the new margin bottom
     */
    public void setMarginBottom(String marginBottom) {
        this.marginBottom = marginBottom;
    }

    /**
     * Gets the margin left.
     *
     * @return the margin left
     */
    public String getMarginLeft() {
        return marginLeft;
    }

    /**
     * Sets the margin left.
     *
     * @param marginLeft the new margin left
     */
    public void setMarginLeft(String marginLeft) {
        this.marginLeft = marginLeft;
    }

    /**
     * Gets the margin right.
     *
     * @return the margin right
     */
    public String getMarginRight() {
        return marginRight;
    }

    /**
     * Sets the margin right.
     *
     * @param marginRight the new margin right
     */
    public void setMarginRight(String marginRight) {
        this.marginRight = marginRight;
    }

    /**
     * Gets the margin top.
     *
     * @return the margin top
     */
    public String getMarginTop() {
        return marginTop;
    }

    /**
     * Sets the margin top.
     *
     * @param marginTop the new margin top
     */
    public void setMarginTop(String marginTop) {
        this.marginTop = marginTop;
    }

    /**
     * Gets the placement.
     *
     * @return the placement
     */
    public String getPlacement() {
        return placement;
    }

    /**
     * Sets the placement.
     *
     * @param placement the new placement
     */
    public void setPlacement(String placement) {
        this.placement = placement;
    }

    /**
     * Gets the pre draw.
     *
     * @return the pre draw
     */
    public Boolean getPreDraw() {
        return preDraw;
    }

    /**
     * Sets the pre draw.
     *
     * @param preDraw the new pre draw
     */
    public void setPreDraw(Boolean preDraw) {
        this.preDraw = preDraw;
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
     * @param renderer the new renderer
     */
    public void setRenderer(JqPlotResources renderer) {
        this.renderer = renderer;
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
     * Gets the show lables.
     *
     * @return the show lables
     */
    public Boolean getShowLables() {
        return showLables;
    }

    /**
     * Sets the show lables.
     *
     * @param showLables the new show lables
     */
    public void setShowLables(Boolean showLables) {
        this.showLables = showLables;
    }

    /**
     * Gets the show swatches.
     *
     * @return the show swatches
     */
    public Boolean getShowSwatches() {
        return showSwatches;
    }

    /**
     * Sets the show swatches.
     *
     * @param showSwatches the new show swatches
     */
    public void setShowSwatches(Boolean showSwatches) {
        this.showSwatches = showSwatches;
    }

    /**
     * Gets the xoffset.
     *
     * @return the xoffset
     */
    public Double getXoffset() {
        return xoffset;
    }

    /**
     * Sets the xoffset.
     *
     * @param xoffset the new xoffset
     */
    public void setXoffset(Double xoffset) {
        this.xoffset = xoffset;
    }

    /**
     * Gets the yoffset.
     *
     * @return the yoffset
     */
    public Double getYoffset() {
        return yoffset;
    }

    /**
     * Sets the yoffset.
     *
     * @param yoffset the new yoffset
     */
    public void setYoffset(Double yoffset) {
        this.yoffset = yoffset;
    }

    /**
     * Gets the options.
     *
     * @return the options
     */
    public String getOptions() {
        return options;
    }

    /**
     * Sets the options.
     *
     * @param options the new options
     */
    public void setOptions(String options) {
        this.options = options;
    }

    /**
     *  Gets the {@link RendererOptions}
	 * @return LegendRenderer
     */
	public LegendRenderer getRendererOptions()
	{
		return rendererOptions;
	}

    /**
     *  Sets the {@link RendererOptions}
	 *  @param rendererOptions set the LegendRenderer
     */
	public void setRendererOptions(LegendRenderer rendererOptions)
	{
		this.rendererOptions = rendererOptions;
	}
}
