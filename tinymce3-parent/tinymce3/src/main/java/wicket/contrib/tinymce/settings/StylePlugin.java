/*
 * Copyright (C) 2005 Iulian-Corneliu Costan
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package wicket.contrib.tinymce.settings;

/**
 * This plugin adds "style" functions to TinyMCE.
 * 
 * [style_font] Semicolon separated list of select box option name/values. Default: Arial,
 * Helvetica, sans-serif=Arial, Helvetica, sans-serif;Times New Roman, Times, serif=Times New Roman,
 * Times, serif;Courier New, Courier, mono=Courier New, Courier, mono;Times New Roman, Times,
 * serif=Times New Roman, Times, serif;Georgia, Times New Roman, Times, serif=Georgia, Times New
 * Roman, Times, serif;Verdana, Arial, Helvetica, sans-serif=Verdana, Arial, Helvetica,
 * sans-serif;Geneva, Arial, Helvetica, sans-serif=Geneva, Arial, Helvetica, sans-serif
 * [style_font_size] Semicolon separated list of select box option name/values. In format
 * key1=value1;key2=value2;.. [style_font_size_measurement] Semicolon separated list of select box
 * option name/values. [style_text_case] Semicolon separated list of select box option name/values.
 * [style_font_weight] Semicolon separated list of select box option name/values. [style_font_style]
 * Semicolon separated list of select box option name/values. [style_font_variant] Semicolon
 * separated list of select box option name/values. [style_font_line_height] Semicolon separated
 * list of select box option name/values. [style_font_line_height_measurement] Semicolon separated
 * list of select box option name/values. [style_background_attachment] Semicolon separated list of
 * select box option name/values. [style_background_repeat] Semicolon separated list of select box
 * option name/values. [style_background_hpos_measurement] Semicolon separated list of select box
 * option name/values. [style_background_vpos_measurement] Semicolon separated list of select box
 * option name/values. [style_background_hpos] Semicolon separated list of select box option
 * name/values. [style_background_vpos] Semicolon separated list of select box option name/values.
 * [style_wordspacing] Semicolon separated list of select box option name/values.
 * [style_wordspacing_measurement] Semicolon separated list of select box option name/values.
 * [style_letterspacing] Semicolon separated list of select box option name/values.
 * [style_letterspacing_measurement] Semicolon separated list of select box option name/values.
 * [style_vertical_alignment] Semicolon separated list of select box option name/values.
 * [style_text_align] Semicolon separated list of select box option name/values. [style_whitespace]
 * Semicolon separated list of select box option name/values. [style_display] Semicolon separated
 * list of select box option name/values. [style_text_indent_measurement] Semicolon separated list
 * of select box option name/values. [style_box_width_measurement] Semicolon separated list of
 * select box option name/values. [style_box_height_measurement] Semicolon separated list of select
 * box option name/values. [style_float] Semicolon separated list of select box option name/values.
 * [style_clear] Semicolon separated list of select box option name/values.
 * [style_padding_left_measurement] Semicolon separated list of select box option name/values.
 * [style_padding_top_measurement] Semicolon separated list of select box option name/values.
 * [style_padding_bottom_measurement] Semicolon separated list of select box option name/values.
 * [style_padding_right_measurement] Semicolon separated list of select box option name/values.
 * [style_margin_left_measurement] Semicolon separated list of select box option name/values.
 * [style_margin_top_measurement] Semicolon separated list of select box option name/values.
 * [style_margin_bottom_measurement] Semicolon separated list of select box option name/values.
 * [style_margin_right_measurement] Semicolon separated list of select box option name/values.
 * [style_border_style_top] Semicolon separated list of select box option name/values.
 * [style_border_style_right] Semicolon separated list of select box option name/values.
 * [style_border_style_bottom] Semicolon separated list of select box option name/values.
 * [style_border_style_left] Semicolon separated list of select box option name/values.
 * [style_border_width_top] Semicolon separated list of select box option name/values.
 * [style_border_width_right] Semicolon separated list of select box option name/values.
 * [style_border_width_bottom] Semicolon separated list of select box option name/values.
 * [style_border_width_left] Semicolon separated list of select box option name/values.
 * [style_border_width_top_measurement] Semicolon separated list of select box option name/values.
 * [style_border_width_right_measurement] Semicolon separated list of select box option name/values.
 * [style_border_width_bottom_measurement] Semicolon separated list of select box option
 * name/values. [style_border_width_left_measurement] Semicolon separated list of select box option
 * name/values. [style_list_type] Semicolon separated list of select box option name/values.
 * [style_list_position] Semicolon separated list of select box option name/values.
 * [style_positioning_type] Semicolon separated list of select box option name/values.
 * [style_positioning_visibility] Semicolon separated list of select box option name/values.
 * [style_positioning_width_measurement] Semicolon separated list of select box option name/values.
 * [style_positioning_height_measurement] Semicolon separated list of select box option name/values.
 * [style_positioning_overflow] Semicolon separated list of select box option name/values.
 * [style_positioning_placement_top_measurement] Semicolon separated list of select box option
 * name/values. [style_positioning_placement_right_measurement] Semicolon separated list of select
 * box option name/values. [style_positioning_placement_bottom_measurement] Semicolon separated list
 * of select box option name/values. [style_positioning_placement_left_measurement] Semicolon
 * separated list of select box option name/values. [style_positioning_clip_top_measurement]
 * Semicolon separated list of select box option name/values.
 * [style_positioning_clip_right_measurement] Semicolon separated list of select box option
 * name/values. [style_positioning_clip_bottom_measurement] Semicolon separated list of select box
 * option name/values. [style_positioning_clip_left_measurement] Semicolon separated list of select
 * box option name/values.
 * 
 * @author Daniel Walmsley
 */
public class StylePlugin extends Plugin
{
	private static final long serialVersionUID = 1L;

	private PluginButton styleButton;

	/**
	 * Construct.
	 */
	public StylePlugin()
	{
		super("style");
		styleButton = new PluginButton("styleprops", this);
	}

	/**
	 * @return the paste button
	 */
	public PluginButton getStyleButton()
	{
		return styleButton;
	}
}
