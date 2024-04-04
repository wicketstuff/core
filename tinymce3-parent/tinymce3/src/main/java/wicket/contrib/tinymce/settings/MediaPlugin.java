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
 * This plugin adds "insert media" functions to TinyMCE.
 * 
 * Plugin options
 * 
 * [media_use_script] True/false option that gives you the ability to have a JavaScript embed method
 * instead of using object/embed tags. Defaults to: false [media_wmp6_compatible] True/false option
 * that enables you to force Windows media player 6 compatiblity by returning that clsid, but some
 * features and options for WMP may not work if you use this option. You can find a reference on
 * these options at w3schools. Defaults to: false [media_skip_plugin_css] Skips the loading of the
 * default plugin CSS file, this can be useful if your content CSS already defined the media
 * specific CSS information, Defaults to: false. [media_external_list_url] URL to a JS file
 * containing files to be listed in the media dropdown list similar to the one found in the advimg
 * dialog. The name of the array variable in the JS file should be 'tinyMCEMediaList'. [media_types]
 * Name/Value list of format mappings to file extensions. Defaults to:
 * flash=swf;shockwave=dcr;qt=mov
 * ,qt,mpg,mp3,mp4,mpeg;shockwave=dcr;wmp=avi,wmv,wm,asf,asx,wmx,wvx;rmp=rm,ra,ram.
 * 
 * @author Daniel Walmsley
 */
public class MediaPlugin extends Plugin
{
	private static final long serialVersionUID = 1L;

	private PluginButton addMediaButton;

	/**
	 * Construct.
	 */
	public MediaPlugin()
	{
		super("media");
		addMediaButton = new PluginButton("media", this);
	}

	/**
	 * @return the paste button
	 */
	public PluginButton getMediaButton()
	{
		return addMediaButton;
	}
}
