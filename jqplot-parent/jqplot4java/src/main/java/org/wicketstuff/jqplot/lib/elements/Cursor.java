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


/**
 * Plugin class representing the cursor as displayed on the plot.
 * 
 * @author inaiat
 *
 */
public class Cursor implements Element {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2573578555244857373L;
	
	/** The style. */
	private String style;
	
	/** The show. */
	private Boolean show = true;
	
	/** The show tooltip. */
	private Boolean showTooltip;
	
	/** The follow mouse. */
	private Boolean followMouse;
	
	/** The tooltip location. */
	private Location tooltipLocation;
	
	/** The tooltip offset. */
	private Integer tooltipOffset;
	
	/** The show tooltip grid position. */
	private Boolean showTooltipGridPosition;
	
	/** The show tooltip unit position. */
	private Boolean showTooltipUnitPosition;
	
	/** The show tooltip data position. */
	private Boolean showTooltipDataPosition;
	
	/** The tooltip format string. */
	private String tooltipFormatString;
	
	/** The use axes formatters. */
	private Boolean useAxesFormatters;
	
	/** The zoom. */
	private Boolean zoom;
	
	/** The loose zoom. */
	private Boolean looseZoom;
	
	/** The click reset. */
	private Boolean clickReset;
	
	/** The dbl click reset. */
	private Boolean dblClickReset;
	
	/** The show vertical line. */
	private Boolean showVerticalLine;
	
	/** The show horizontal line. */
	private Boolean showHorizontalLine;
	
	/** 'none', 'x' or 'y'. */
	private String constrainZoomTo; 
	
	/** The intersection threshold. */
	private Integer intersectionThreshold;
	
	/** The show cursor legend. */
	private Boolean showCursorLegend;
	
	/** The cursor legend format string. */
	private String cursorLegendFormatString;
	
	/** The constrain outside zoom. */
	private Boolean constrainOutsideZoom;
	
	/** The show tooltip outside zoom. */
	private Boolean showTooltipOutsideZoom;
	
	/** 
	 * Show position for the specified axes.  
	 * This is an array like [['xaxis', 'yaxis'], ['xaxis', 'y2axis']] 
	 * Default is to compute automatically for all visible axes.
	 */
	private String tooltipAxisGroups;

	/**
	 * Gets the style.
	 *
	 * @return the style
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
	 * Gets the show tooltip.
	 *
	 * @return the show tooltip
	 */
	public Boolean getShowTooltip() {
		return showTooltip;
	}

	/**
	 * Sets the show tooltip.
	 *
	 * @param showTooltip the new show tooltip
	 */
	public void setShowTooltip(Boolean showTooltip) {
		this.showTooltip = showTooltip;
	}

	/**
	 * Gets the follow mouse.
	 *
	 * @return the follow mouse
	 */
	public Boolean getFollowMouse() {
		return followMouse;
	}

	/**
	 * Sets the follow mouse.
	 *
	 * @param followMouse the new follow mouse
	 */
	public void setFollowMouse(Boolean followMouse) {
		this.followMouse = followMouse;
	}

	/**
	 * Gets the tooltip location.
	 *
	 * @return the tooltip location
	 */
	public Location getTooltipLocation() {
		return tooltipLocation;
	}

	/**
	 * Sets the tooltip location.
	 *
	 * @param tooltipLocation the new tooltip location
	 */
	public void setTooltipLocation(Location tooltipLocation) {
		this.tooltipLocation = tooltipLocation;
	}

	/**
	 * Gets the tooltip offset.
	 *
	 * @return the tooltip offset
	 */
	public Integer getTooltipOffset() {
		return tooltipOffset;
	}

	/**
	 * Sets the tooltip offset.
	 *
	 * @param tooltipOffset the new tooltip offset
	 */
	public void setTooltipOffset(Integer tooltipOffset) {
		this.tooltipOffset = tooltipOffset;
	}

	/**
	 * Gets the show tooltip grid position.
	 *
	 * @return the show tooltip grid position
	 */
	public Boolean getShowTooltipGridPosition() {
		return showTooltipGridPosition;
	}

	/**
	 * Sets the show tooltip grid position.
	 *
	 * @param showTooltipGridPosition the new show tooltip grid position
	 */
	public void setShowTooltipGridPosition(Boolean showTooltipGridPosition) {
		this.showTooltipGridPosition = showTooltipGridPosition;
	}

	/**
	 * Gets the show tooltip unit position.
	 *
	 * @return the show tooltip unit position
	 */
	public Boolean getShowTooltipUnitPosition() {
		return showTooltipUnitPosition;
	}

	/**
	 * Sets the show tooltip unit position.
	 *
	 * @param showTooltipUnitPosition the new show tooltip unit position
	 */
	public void setShowTooltipUnitPosition(Boolean showTooltipUnitPosition) {
		this.showTooltipUnitPosition = showTooltipUnitPosition;
	}

	/**
	 * Gets the show tooltip data position.
	 *
	 * @return the show tooltip data position
	 */
	public Boolean getShowTooltipDataPosition() {
		return showTooltipDataPosition;
	}

	/**
	 * Sets the show tooltip data position.
	 *
	 * @param showTooltipDataPosition the new show tooltip data position
	 */
	public void setShowTooltipDataPosition(Boolean showTooltipDataPosition) {
		this.showTooltipDataPosition = showTooltipDataPosition;
	}

	/**
	 * Gets the tooltip format string.
	 *
	 * @return the tooltip format string
	 */
	public String getTooltipFormatString() {
		return tooltipFormatString;
	}

	/**
	 * Sets the tooltip format string.
	 *
	 * @param tooltipFormatString the new tooltip format string
	 */
	public void setTooltipFormatString(String tooltipFormatString) {
		this.tooltipFormatString = tooltipFormatString;
	}

	/**
	 * Gets the use axes formatters.
	 *
	 * @return the use axes formatters
	 */
	public Boolean getUseAxesFormatters() {
		return useAxesFormatters;
	}

	/**
	 * Sets the use axes formatters.
	 *
	 * @param useAxesFormatters the new use axes formatters
	 */
	public void setUseAxesFormatters(Boolean useAxesFormatters) {
		this.useAxesFormatters = useAxesFormatters;
	}

	/**
	 * Gets the zoom.
	 *
	 * @return the zoom
	 */
	public Boolean getZoom() {
		return zoom;
	}

	/**
	 * Sets the zoom.
	 *
	 * @param zoom the new zoom
	 */
	public void setZoom(Boolean zoom) {
		this.zoom = zoom;
	}

	/**
	 * Gets the loose zoom.
	 *
	 * @return the loose zoom
	 */
	public Boolean getLooseZoom() {
		return looseZoom;
	}

	/**
	 * Sets the loose zoom.
	 *
	 * @param looseZoom the new loose zoom
	 */
	public void setLooseZoom(Boolean looseZoom) {
		this.looseZoom = looseZoom;
	}

	/**
	 * Gets the click reset.
	 *
	 * @return the click reset
	 */
	public Boolean getClickReset() {
		return clickReset;
	}

	/**
	 * Sets the click reset.
	 *
	 * @param clickReset the new click reset
	 */
	public void setClickReset(Boolean clickReset) {
		this.clickReset = clickReset;
	}

	/**
	 * Gets the dbl click reset.
	 *
	 * @return the dbl click reset
	 */
	public Boolean getDblClickReset() {
		return dblClickReset;
	}

	/**
	 * Sets the dbl click reset.
	 *
	 * @param dblClickReset the new dbl click reset
	 */
	public void setDblClickReset(Boolean dblClickReset) {
		this.dblClickReset = dblClickReset;
	}

	/**
	 * Gets the show vertical line.
	 *
	 * @return the show vertical line
	 */
	public Boolean getShowVerticalLine() {
		return showVerticalLine;
	}

	/**
	 * Sets the show vertical line.
	 *
	 * @param showVerticalLine the new show vertical line
	 */
	public void setShowVerticalLine(Boolean showVerticalLine) {
		this.showVerticalLine = showVerticalLine;
	}

	/**
	 * Gets the show horizontal line.
	 *
	 * @return the show horizontal line
	 */
	public Boolean getShowHorizontalLine() {
		return showHorizontalLine;
	}

	/**
	 * Sets the show horizontal line.
	 *
	 * @param showHorizontalLine the new show horizontal line
	 */
	public void setShowHorizontalLine(Boolean showHorizontalLine) {
		this.showHorizontalLine = showHorizontalLine;
	}

	/**
	 * Gets the constrain zoom to.
	 *
	 * @return the constrain zoom to
	 */
	public String getConstrainZoomTo() {
		return constrainZoomTo;
	}

	/**
	 * Sets the constrain zoom to.
	 *
	 * @param constrainZoomTo the new constrain zoom to
	 */
	public void setConstrainZoomTo(String constrainZoomTo) {
		this.constrainZoomTo = constrainZoomTo;
	}

	/**
	 * Gets the intersection threshold.
	 *
	 * @return the intersection threshold
	 */
	public Integer getIntersectionThreshold() {
		return intersectionThreshold;
	}

	/**
	 * Sets the intersection threshold.
	 *
	 * @param intersectionThreshold the new intersection threshold
	 */
	public void setIntersectionThreshold(Integer intersectionThreshold) {
		this.intersectionThreshold = intersectionThreshold;
	}

	/**
	 * Gets the show cursor legend.
	 *
	 * @return the show cursor legend
	 */
	public Boolean getShowCursorLegend() {
		return showCursorLegend;
	}

	/**
	 * Sets the show cursor legend.
	 *
	 * @param showCursorLegend the new show cursor legend
	 */
	public void setShowCursorLegend(Boolean showCursorLegend) {
		this.showCursorLegend = showCursorLegend;
	}

	/**
	 * Gets the cursor legend format string.
	 *
	 * @return the cursor legend format string
	 */
	public String getCursorLegendFormatString() {
		return cursorLegendFormatString;
	}

	/**
	 * Sets the cursor legend format string.
	 *
	 * @param cursorLegendFormatString the new cursor legend format string
	 */
	public void setCursorLegendFormatString(String cursorLegendFormatString) {
		this.cursorLegendFormatString = cursorLegendFormatString;
	}

	/**
	 * Gets the constrain outside zoom.
	 *
	 * @return the constrain outside zoom
	 */
	public Boolean getConstrainOutsideZoom() {
		return constrainOutsideZoom;
	}

	/**
	 * Sets the constrain outside zoom.
	 *
	 * @param constrainOutsideZoom the new constrain outside zoom
	 */
	public void setConstrainOutsideZoom(Boolean constrainOutsideZoom) {
		this.constrainOutsideZoom = constrainOutsideZoom;
	}

	/**
	 * Gets the show tooltip outside zoom.
	 *
	 * @return the show tooltip outside zoom
	 */
	public Boolean getShowTooltipOutsideZoom() {
		return showTooltipOutsideZoom;
	}

	/**
	 * Sets the show tooltip outside zoom.
	 *
	 * @param showTooltipOutsideZoom the new show tooltip outside zoom
	 */
	public void setShowTooltipOutsideZoom(Boolean showTooltipOutsideZoom) {
		this.showTooltipOutsideZoom = showTooltipOutsideZoom;
	}

	/**
	 * Gets the tooltip axis groups.
	 *
	 * @return the tooltip axis groups
	 */
	public String getTooltipAxisGroups() {
		return tooltipAxisGroups;
	}
	
	/**
	 * Sets the tooltip axis groups.
	 *
	 * @param tooltipAxisGroups the new tooltip axis groups
	 */
	public void setTooltipAxisGroups(String tooltipAxisGroups) {
		this.tooltipAxisGroups = tooltipAxisGroups;
	}

}
