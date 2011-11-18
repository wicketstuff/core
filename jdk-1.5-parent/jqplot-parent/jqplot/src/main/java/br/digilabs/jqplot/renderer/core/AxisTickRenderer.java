/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.digilabs.jqplot.renderer.core;

import br.digilabs.jqplot.JqPlotResources;

/**
 *
 * @author inaiat
 */
public class AxisTickRenderer {

    private String mark;
    private Boolean showMark;
    private Boolean showGridline;
    private Boolean isMinorTick;
    private Integer size;
    private Integer markSize;
    private Boolean show;
    private JqPlotResources formatter;
    private String prefix;
    private String formatString;
    private String fontFamily;
    private String fontSize;
    private String textColor;
    private Boolean escapeHTML;

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
     * @return the size
     */
    public Integer getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(Integer size) {
        this.size = size;
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
     * @return the escapeHTML
     */
    public Boolean getEscapeHTML() {
        return escapeHTML;
    }

    /**
     * @param escapeHTML the escapeHTML to set
     */
    public void setEscapeHTML(Boolean escapeHTML) {
        this.escapeHTML = escapeHTML;
    }
}
