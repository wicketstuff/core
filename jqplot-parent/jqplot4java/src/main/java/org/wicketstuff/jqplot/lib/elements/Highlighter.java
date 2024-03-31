package org.wicketstuff.jqplot.lib.elements;

import org.wicketstuff.jqplot.lib.JqPlotResources;
import org.wicketstuff.jqplot.lib.metadata.JqPlotPlugin;

@JqPlotPlugin(values = {JqPlotResources.Highlighter})
public class Highlighter implements Element {

    private static final long serialVersionUID = 6872308828768070373L;

    /** true to show the highlight **/
    private Boolean show;

    /** true to show a tooltip with the point values. */
    private Boolean showTooltip;

    /** sprintf format string for the tooltip. */
    private String tooltipFormatString;

    /** true to show the marker */
    private Boolean showMarker;

    /** Pixels to add to the lineWidth of the highlight. */
    private Double lineWidthAdjust;

    /** Pixels to add to the overall size of the highlight. */
    private Double sizeAdjust;

    /** Where to position tooltip, ‘n’, ‘ne’, ‘e’, ‘se’, ‘s’, ‘sw’, ‘w’, ‘nw’ */
    private Location tooltipLocation;

    /** true = fade in/out tooltip, false = show/hide tooltip */
    private Boolean fadeTooltip;

    /** ‘slow’, ‘def’, ‘fast’, or number of milliseconds. */
    private String tooltipFadeSpeed;

    /** Pixel offset of tooltip from the highlight. */
    private Double tooltipOffset;

    /**
     * Which axes to display in tooltip, ‘x’, ‘y’ or ‘both’, ‘xy’ or ‘yx’ ‘both’ and ‘xy’ are equivalent,
     * ‘yx’ reverses order of labels.
     */
    private TooltipAxes tooltipAxes;

    /**
     * Use the x and y axes formatters to format the text in the tooltip. Must be 'false' for
     * the 'tooltipFormatString' to take effect
     */
	private Boolean useAxesFormatters;

    /**
	 * alternative to tooltipFormatString will format the whole tooltip text, populating
	 * with x, y values as indicated by tooltipAxes option.
	 */
	private String formatString;

    /**
     * Number of y values to expect in the data point array.
     * Typically this is 1. Certain plots, like OHLC, will have more y values in each data point array
     */
	private Integer yvalues;

    /** true to bring the series of the highlighted point to the front of other series **/
    private Boolean bringSeriesToFront;

    public Boolean getShow() {
        return show;
    }

    public void setShow(Boolean show) {
        this.show = show;
    }

    public Boolean getShowTooltip() {
        return showTooltip;
    }

    public void setShowTooltip(Boolean showTooltip) {
        this.showTooltip = showTooltip;
    }

    public String getTooltipFormatString() {
        return tooltipFormatString;
    }

    public void setTooltipFormatString(String tooltipFormatString) {
        this.tooltipFormatString = tooltipFormatString;
    }

    public Boolean getShowMarker() {
        return showMarker;
    }

    public void setShowMarker(Boolean showMarker) {
        this.showMarker = showMarker;
    }

    public Double getLineWidthAdjust() {
        return lineWidthAdjust;
    }

    public void setLineWidthAdjust(Double lineWidthAdjust) {
        this.lineWidthAdjust = lineWidthAdjust;
    }

    public Double getSizeAdjust() {
        return sizeAdjust;
    }

    public void setSizeAdjust(Double sizeAdjust) {
        this.sizeAdjust = sizeAdjust;
    }

    public Location getTooltipLocation() {
        return tooltipLocation;
    }

    public void setTooltipLocation(Location tooltipLocation) {
        this.tooltipLocation = tooltipLocation;
    }

    public Boolean getFadeTooltip() {
        return fadeTooltip;
    }

    public void setFadeTooltip(Boolean fadeTooltip) {
        this.fadeTooltip = fadeTooltip;
    }

    public String getTooltipFadeSpeed() {
        return tooltipFadeSpeed;
    }

    public void setTooltipFadeSpeed(String tooltipFadeSpeed) {
        this.tooltipFadeSpeed = tooltipFadeSpeed;
    }

    public Double getTooltipOffset() {
        return tooltipOffset;
    }

    public void setTooltipOffset(Double tooltipOffset) {
        this.tooltipOffset = tooltipOffset;
    }

    public TooltipAxes getTooltipAxes() {
        return tooltipAxes;
    }

    public void setTooltipAxes(TooltipAxes tooltipAxes) {
        this.tooltipAxes = tooltipAxes;
    }

    public Boolean getUseAxesFormatters() {
        return useAxesFormatters;
    }

    public void setUseAxesFormatters(Boolean useAxesFormatters) {
        this.useAxesFormatters = useAxesFormatters;
    }

    public String getFormatString() {
        return formatString;
    }

    public void setFormatString(String formatString) {
        this.formatString = formatString;
    }

    public Integer getYvalues() {
        return yvalues;
    }

    public void setYvalues(Integer yvalues) {
        this.yvalues = yvalues;
    }

    public Boolean getBringSeriesToFront() {
        return bringSeriesToFront;
    }

    public void setBringSeriesToFront(Boolean bringSeriesToFront) {
        this.bringSeriesToFront = bringSeriesToFront;
    }
}
