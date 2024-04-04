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
 * A 'tick' object showing the value of a tick/gridline on the plot.
 * 
 * @author inaiat
 */
public class TickOptions implements Element {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8139093369426118021L;

	/** The mark. */
	private String mark;

	/** The show mark. */
	private Boolean showMark;

	/** The show gridline. */
	private Boolean showGridline;

	/** The is minor tick. */
	private Boolean isMinorTick;

	/** The angle. */
	private Integer angle;

	/** The mark size. */
	private Integer markSize;

	/** The show. */
	private Boolean show;

	/** The show label. */
	private Boolean showLabel;

	/** The label position. */
	private String labelPosition;

	/** The label. */
	private String label;

	/** The format string. */
	private String formatString;

	/** The prefix. */
	private String prefix;

	/** The font family. */
	private String fontFamily;

	/** The font size. */
	private String fontSize;

	/** The font weight. */
	private String fontWeight;

	/** The font stretch. */
	private Double fontStretch;

	/** The text color. */
	private String textColor;

	/** The escape html. */
	private Boolean escapeHTML;

	/**
	 * Gets the format string.
	 * 
	 * @return formatString
	 */
	public String getFormatString() {
		return formatString;
	}

	/**
	 * Sets the format string.
	 * 
	 * @param formatString
	 *            the new format string
	 * @return TickOptions
	 */
	public TickOptions setFormatString(String formatString) {
		this.formatString = formatString;
		return this;
	}

	/**
	 * Sets the angle.
	 * 
	 * @param angle
	 *            the angle to set
	 * @return TickOptions
	 */
	public TickOptions setAngle(Integer angle) {
		this.angle = angle;
		return this;
	}

	/**
	 * Gets the angle.
	 * 
	 * @return the angle
	 */
	public Integer getAngle() {
		return angle;
	}

	/**
	 * Gets the label position.
	 * 
	 * @return the labelPosition
	 */
	public String getLabelPosition() {
		return labelPosition;
	}

	/**
	 * Sets the label position.
	 * 
	 * @param labelPosition
	 *            to set
	 * @return TickOptions
	 */
	public TickOptions setLabelPosition(String labelPosition) {
		this.labelPosition = labelPosition;
		return this;
	}

	/**
	 * Gets the mark.
	 * 
	 * @return the mark
	 */
	public String getMark() {
		return mark;
	}

	/**
	 * Sets the mark.
	 * 
	 * @param mark
	 *            the new mark
	 * @return TickOptions
	 */
	public TickOptions setMark(String mark) {
		this.mark = mark;
		return this;
	}

	/**
	 * Gets the show mark.
	 * 
	 * @return the show mark
	 */
	public Boolean getShowMark() {
		return showMark;
	}

	/**
	 * Sets the show mark.
	 * 
	 * @param showMark
	 *            the new show mark
	 * @return TickOptions
	 */
	public TickOptions setShowMark(Boolean showMark) {
		this.showMark = showMark;
		return this;
	}

	/**
	 * Gets the show gridline.
	 * 
	 * @return the show gridline
	 */
	public Boolean getShowGridline() {
		return showGridline;
	}

	/**
	 * Sets the show gridline.
	 * 
	 * @param showGridline
	 *            the new show gridline
	 * @return TickOptions
	 */
	public TickOptions setShowGridline(Boolean showGridline) {
		this.showGridline = showGridline;
		return this;
	}

	/**
	 * Gets the checks if is minor tick.
	 * 
	 * @return the checks if is minor tick
	 */
	public Boolean getIsMinorTick() {
		return isMinorTick;
	}

	/**
	 * Sets the checks if is minor tick.
	 * 
	 * @param isMinorTick
	 *            the new checks if is minor tick
	 * @return TickOptions
	 */
	public TickOptions setIsMinorTick(Boolean isMinorTick) {
		this.isMinorTick = isMinorTick;
		return this;
	}

	/**
	 * Gets the mark size.
	 * 
	 * @return the mark size
	 */
	public Integer getMarkSize() {
		return markSize;
	}

	/**
	 * Sets the mark size.
	 * 
	 * @param markSize
	 *            the new mark size
	 * @return TickOptions
	 */
	public TickOptions setMarkSize(Integer markSize) {
		this.markSize = markSize;
		return this;
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
	 * @param show
	 *            the new show
	 * @return TickOptions
	 */
	public TickOptions setShow(Boolean show) {
		this.show = show;
		return this;
	}

	/**
	 * Gets the show label.
	 * 
	 * @return the show label
	 */
	public Boolean getShowLabel() {
		return showLabel;
	}

	/**
	 * Sets the show label.
	 * 
	 * @param showLabel
	 *            the new show label
	 * @return TickOptions
	 */
	public TickOptions setShowLabel(Boolean showLabel) {
		this.showLabel = showLabel;
		return this;
	}

	/**
	 * Gets the label.
	 * 
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Sets the label.
	 * 
	 * @param label
	 *            the new label
	 * @return TickOptions
	 */
	public TickOptions setLabel(String label) {
		this.label = label;
		return this;
	}

	/**
	 * Gets the prefix.
	 * 
	 * @return the prefix
	 */
	public String getPrefix() {
		return prefix;
	}

	/**
	 * Sets the prefix.
	 * 
	 * @param prefix
	 *            the new prefix
	 * @return TickOptions
	 */
	public TickOptions setPrefix(String prefix) {
		this.prefix = prefix;
		return this;
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
	 * @param fontFamily
	 *            the new font family
	 * @return TickOptions
	 */
	public TickOptions setFontFamily(String fontFamily) {
		this.fontFamily = fontFamily;
		return this;
	}

	/**
	 * Gets the font size.
	 * 
	 * @return the font size
	 */
	public String getFontSize() {
		return fontSize;
	}

	/**
	 * Sets the font size.
	 * 
	 * @param fontSize
	 *            the new font size
	 * @return TickOptions
	 */
	public TickOptions setFontSize(String fontSize) {
		this.fontSize = fontSize;
		return this;
	}

	/**
	 * Gets the font weight.
	 * 
	 * @return the font weight
	 */
	public String getFontWeight() {
		return fontWeight;
	}

	/**
	 * Sets the font weight.
	 * 
	 * @param fontWeight
	 *            the new font weight
	 * @return TickOptions
	 */
	public TickOptions setFontWeight(String fontWeight) {
		this.fontWeight = fontWeight;
		return this;
	}

	/**
	 * Gets the font stretch.
	 * 
	 * @return the font stretch
	 */
	public Double getFontStretch() {
		return fontStretch;
	}

	/**
	 * Sets the font stretch.
	 * 
	 * @param fontStretch
	 *            the new font stretch
	 * @return TickOptions
	 */
	public TickOptions setFontStretch(Double fontStretch) {
		this.fontStretch = fontStretch;
		return this;
	}

	/**
	 * Gets the text color.
	 * 
	 * @return the text color
	 */
	public String getTextColor() {
		return textColor;
	}

	/**
	 * Sets the text color.
	 * 
	 * @param textColor
	 *            the new text color
	 * @return TickOptions
	 */
	public TickOptions setTextColor(String textColor) {
		this.textColor = textColor;
		return this;
	}

	/**
	 * Gets the escape html.
	 * 
	 * @return the escape html
	 */
	public Boolean getEscapeHTML() {
		return escapeHTML;
	}

	/**
	 * Sets the escape html.
	 * 
	 * @param escapeHTML
	 *            the new escape html
	 * @return TickOptions
	 */
	public TickOptions setEscapeHTML(Boolean escapeHTML) {
		this.escapeHTML = escapeHTML;
		return this;
	}
}
