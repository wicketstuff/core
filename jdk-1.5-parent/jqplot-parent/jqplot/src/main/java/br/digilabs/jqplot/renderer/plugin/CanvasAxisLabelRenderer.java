/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.digilabs.jqplot.renderer.plugin;

import br.digilabs.jqplot.JqPlotResources;
import br.digilabs.jqplot.chart.elements.Renderer;

/**
 *
 * @author inaiat
 */
public class CanvasAxisLabelRenderer implements Renderer {

	private static final long serialVersionUID = -5172222371889152689L;

	private Float angle;
    private Boolean show;
    private Boolean showLabel;
    private String label;
    private String fontFamily;
    private String fontSize;
    private String fontWeight;
    private String fontStretch;
    private String textColor;
    private Boolean enableFontSupport;
    private String pt2px;

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
     * @return the showLabel
     */
    public Boolean getShowLabel() {
        return showLabel;
    }

    /**
     * @param showLabel the showLabel to set
     */
    public void setShowLabel(Boolean showLabel) {
        this.showLabel = showLabel;
    }

    /**
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @param label the label to set
     */
    public void setLabel(String label) {
        this.label = label;
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
    public String getFontStretch() {
        return fontStretch;
    }

    /**
     * @param fontStretch the fontStretch to set
     */
    public void setFontStretch(String fontStretch) {
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
    public String getPt2px() {
        return pt2px;
    }

    /**
     * @param pt2px the pt2px to set
     */
    public void setPt2px(String pt2px) {
        this.pt2px = pt2px;
    }

    public JqPlotResources resource() {
        return JqPlotResources.CanvasAxisLabelRenderer;
    }
}
