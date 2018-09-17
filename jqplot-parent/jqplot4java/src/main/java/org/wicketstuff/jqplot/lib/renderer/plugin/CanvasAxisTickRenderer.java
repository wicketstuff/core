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
 * Renderer to draw axis ticks with a canvas element to support advanced featrues such as rotated text.  
 * This renderer uses a separate rendering engine to draw the text on the canvas.  Two modes of rendering the text 
 * are available.  
 * If the browser has native font support for canvas fonts (currently Mozila 3.5 and Safari 4), you can enable 
 * text rendering with the canvas fillText method.  
 * You do so by setting the 'enableFontSupport' option to true. Browsers lacking native font support will have 
 * the text drawn on the canvas using the Hershey font metrics.  Even if the 'enableFontSupport' option is true 
 * non-supporting browsers will still render with the Hershey font.
 * 
 * @see <a href="http://www.jqplot.com/docs/files/plugins/jqplot-canvasAxisTickRenderer-js.html">http://www.jqplot.com/docs/files/plugins/jqplot-canvasAxisTickRenderer-js.html</a>
 * 
 * @author inaiat
 */
@Deprecated
public class CanvasAxisTickRenderer implements Renderer {

    private static final long serialVersionUID = -2722557983084932478L;

    private String mark;
    private Boolean showMark;
    private Boolean showGridline;
    private Boolean isMinorTick;
    private Float angle;
    private Integer markSize;
    private Boolean show;
    private String labelPosition;
    private JqPlotResources formatter;
    private String formatString;
    private String prefix;
    private String fontFamily;
    private String fontSize;
    private String fontWeight;
    private Float fontStretch;
    private String textColor;
    private Boolean enableFontSupport;
    private Float pt2px;

    /**
     * @return the mark
     */
    public String getMark() {
        return mark;
    }

    /**
     * @param mark the mark to set
     */
    public void setMark(String mark) {
        this.mark = mark;
    }

    /**
     * @return the showMark
     */
    public Boolean getShowMark() {
        return showMark;
    }

    /**
     * @param showMark the showMark to set
     */
    public void setShowMark(Boolean showMark) {
        this.showMark = showMark;
    }

    /**
     * @return the showGridline
     */
    public Boolean getShowGridline() {
        return showGridline;
    }

    /**
     * @param showGridline the showGridline to set
     */
    public void setShowGridline(Boolean showGridline) {
        this.showGridline = showGridline;
    }

    /**
     * @return the isMinorTick
     */
    public Boolean getIsMinorTick() {
        return isMinorTick;
    }

    /**
     * @param isMinorTick the isMinorTick to set
     */
    public void setIsMinorTick(Boolean isMinorTick) {
        this.isMinorTick = isMinorTick;
    }

    /**
     * @return the angle
     */
    public Float getAngle() {
        return angle;
    }

    /**
     * @param angle the angle to set
     */
    public void setAngle(Float angle) {
        this.angle = angle;
    }

    /**
     * @return the markSize
     */
    public Integer getMarkSize() {
        return markSize;
    }

    /**
     * @param markSize the markSize to set
     */
    public void setMarkSize(Integer markSize) {
        this.markSize = markSize;
    }

    /**
     * @return the show
     */
    public Boolean getShow() {
        return show;
    }

    /**
     * @param show the show to set
     */
    public void setShow(Boolean show) {
        this.show = show;
    }

    /**
     * @return the labelPosition
     */
    public String getLabelPosition() {
        return labelPosition;
    }

    /**
     * @param labelPosition the labelPosition to set
     */
    public void setLabelPosition(String labelPosition) {
        this.labelPosition = labelPosition;
    }

    /**
     * @return the formatter
     */
    public JqPlotResources getFormatter() {
        return formatter;
    }

    /**
     * @param formatter the formatter to set
     */
    public void setFormatter(JqPlotResources formatter) {
        this.formatter = formatter;
    }

    /**
     * @return the formatString
     */
    public String getFormatString() {
        return formatString;
    }

    /**
     * @param formatString the formatString to set
     */
    public void setFormatString(String formatString) {
        this.formatString = formatString;
    }

    /**
     * @return the prefix
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * @param prefix the prefix to set
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    /**
     * @return the fontFamily
     */
    public String getFontFamily() {
        return fontFamily;
    }

    /**
     * @param fontFamily the fontFamily to set
     */
    public void setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
    }

    /**
     * @return the fontSize
     */
    public String getFontSize() {
        return fontSize;
    }

    /**
     * @param fontSize the fontSize to set
     */
    public void setFontSize(String fontSize) {
        this.fontSize = fontSize;
    }

    /**
     * @return the fontWeight
     */
    public String getFontWeight() {
        return fontWeight;
    }

    /**
     * @param fontWeight the fontWeight to set
     */
    public void setFontWeight(String fontWeight) {
        this.fontWeight = fontWeight;
    }

    /**
     * @return the fontStretch
     */
    public Float getFontStretch() {
        return fontStretch;
    }

    /**
     * @param fontStretch the fontStretch to set
     */
    public void setFontStretch(Float fontStretch) {
        this.fontStretch = fontStretch;
    }

    /**
     * @return the textColor
     */
    public String getTextColor() {
        return textColor;
    }

    /**
     * @param textColor the textColor to set
     */
    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    /**
     * @return the enableFontSupport
     */
    public Boolean getEnableFontSupport() {
        return enableFontSupport;
    }

    /**
     * @param enableFontSupport the enableFontSupport to set
     */
    public void setEnableFontSupport(Boolean enableFontSupport) {
        this.enableFontSupport = enableFontSupport;
    }

    /**
     * @return the pt2px
     */
    public Float getPt2px() {
        return pt2px;
    }

    /**
     * @param pt2px the pt2px to set
     */
    public void setPt2px(Float pt2px) {
        this.pt2px = pt2px;
    }

    public JqPlotResources resource() {
        return JqPlotResources.CanvasAxisTickRenderer;
    }   

}
