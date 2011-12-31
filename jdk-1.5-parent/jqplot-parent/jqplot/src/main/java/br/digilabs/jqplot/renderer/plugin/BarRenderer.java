/*;
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.digilabs.jqplot.renderer.plugin;

import java.util.Collection;

import br.digilabs.jqplot.JqPlotResources;
import br.digilabs.jqplot.chart.elements.Renderer;

/**
 *
 * @author inaiat
 */
public class BarRenderer implements Renderer {
    
	private static final long serialVersionUID = 2044639222061941989L;

	private Integer barPadding;
    private Integer barMargin;
    private String barDirection;
    private String barWidth;
    private Integer shadowOffset;
    private Float shadowAlpha;
    private Boolean waterfall;
    private Integer groups;
    private Boolean varyBarColor;
    private Boolean highlightMouseOver;
    private Boolean highlightMouseDown;
    private Collection<String> highlightColors;

    /**
     * @return the barPadding
     */
    public Integer getBarPadding() {
        return barPadding;
    }

    /**
     * @param barPadding the barPadding to set
     */
    public void setBarPadding(Integer barPadding) {
        this.barPadding = barPadding;
    }

    /**
     * @return the barMargin
     */
    public Integer getBarMargin() {
        return barMargin;
    }

    /**
     * @param barMargin the barMargin to set
     */
    public void setBarMargin(Integer barMargin) {
        this.barMargin = barMargin;
    }

    /**
     * @return the barDirection
     */
    public String getBarDirection() {
        return barDirection;
    }

    /**
     * @param barDirection the barDirection to set
     */
    public void setBarDirection(String barDirection) {
        this.barDirection = barDirection;
    }

    /**
     * @return the barWidth
     */
    public String getBarWidth() {
        return barWidth;
    }

    /**
     * @param barWidth the barWidth to set
     */
    public void setBarWidth(String barWidth) {
        this.barWidth = barWidth;
    }

    /**
     * @return the shadowOffset
     */
    public Integer getShadowOffset() {
        return shadowOffset;
    }

    /**
     * @param shadowOffset the shadowOffset to set
     */
    public void setShadowOffset(Integer shadowOffset) {
        this.shadowOffset = shadowOffset;
    }

    /**
     * @return the shadowAlpha
     */
    public Float getShadowAlpha() {
        return shadowAlpha;
    }

    /**
     * @param shadowAlpha the shadowAlpha to set
     */
    public void setShadowAlpha(Float shadowAlpha) {
        this.shadowAlpha = shadowAlpha;
    }

    /**
     * @return the waterfall
     */
    public boolean isWaterfall() {
        return waterfall;
    }

    /**
     * @param waterfall the waterfall to set
     */
    public void setWaterfall(boolean waterfall) {
        this.waterfall = waterfall;
    }

    /**
     * @return the groups
     */
    public Integer getGroups() {
        return groups;
    }

    /**
     * @param groups the groups to set
     */
    public void setGroups(Integer groups) {
        this.groups = groups;
    }

    /**
     * @return the varyBarColor
     */
    public boolean isVaryBarColor() {
        return varyBarColor;
    }

    /**
     * @param varyBarColor the varyBarColor to set
     */
    public void setVaryBarColor(boolean varyBarColor) {
        this.varyBarColor = varyBarColor;
    }

    /**
     * @return the highlightMouseOver
     */
    public boolean isHighlightMouseOver() {
        return highlightMouseOver;
    }

    /**
     * @param highlightMouseOver the highlightMouseOver to set
     */
    public void setHighlightMouseOver(boolean highlightMouseOver) {
        this.highlightMouseOver = highlightMouseOver;
    }

    /**
     * @return the highlightMouseDown
     */
    public boolean isHighlightMouseDown() {
        return highlightMouseDown;
    }

    /**
     * @param highlightMouseDown the highlightMouseDown to set
     */
    public void setHighlightMouseDown(boolean highlightMouseDown) {
        this.highlightMouseDown = highlightMouseDown;
    }

    /**
     * @return the highlightColors
     */
    public Collection<String> getHighlightColors() {
        return highlightColors;
    }

    /**
     * @param highlightColors the highlightColors to set
     */
    public void setHighlightColors(Collection<String> highlightColors) {
        this.highlightColors = highlightColors;
    }

    public JqPlotResources resource() {
        return JqPlotResources.BarRenderer;
    }
    
    
}
