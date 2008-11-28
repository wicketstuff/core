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

import java.util.Locale;

/**
 * Adds a zoom drop list in MSIE5.5+.
 * 
 * @author Iulian-Corneliu Costan (iulian.costan@gmail.com)
 */
public class ZoomPlugin extends Plugin
{
	private static final long serialVersionUID = 1L;

	private PluginButton zoomButton;

	private String fonts;
	private String fontSizes;
	private String fontFamily;
	private String fontColor;
	private boolean xmlDeclaration;
	private Locale language;
	private String title;

	/**
	 * Construct.
	 */
	public ZoomPlugin()
	{
		super("zoom");
		zoomButton = new PluginButton("zoom", this);
	}

	/**
	 * @return the zoom button
	 */
	public PluginButton getZoomButton()
	{
		return zoomButton;
	}

	/**
	 * This option enables you to specify the default langcode for the output
	 * HTML.
	 * 
	 * @param fullpage_default_langcode
	 */
	public void setLanguage(Locale locale)
	{
		this.language = locale;
	}

	/**
	 * This option enables you to specify the default title for the output HTML.
	 * 
	 * @param fullpage_default_title
	 */
	public void setTitle(String fullpage_default_title)
	{
		this.title = fullpage_default_title;
	}

	/**
	 * This option enables you to specify if a XML declaration should be added
	 * or not true/false option.
	 * 
	 * @param xmlDeclaration
	 */
	public void setXmlDeclaration(boolean xmlDeclaration)
	{
		this.xmlDeclaration = xmlDeclaration;
	}

	/**
	 * This option enables you to specify the default font size for body
	 * element.
	 * 
	 * @param fontsize
	 */
	public void setFontSizes(String fontsize)
	{
		this.fontSizes = fontsize;
	}

	/**
	 * This option enables you to specify the default font family for body
	 * element.
	 * 
	 * @param fontfamily
	 */
	public void setFontFamily(String fontfamily)
	{
		this.fontFamily = fontfamily;
	}

	/**
	 * This option enables you to specify the default text color for body
	 * element.
	 * 
	 * @param fontcolor
	 */
	public void setFontColor(String fontcolor)
	{
		this.fontColor = fontcolor;
	}

	/**
	 * This option enables you specify what font family style values that can be
	 * added to the body element. The value format of this option is
	 * name=value;name=value. The default value of this option is:
	 * Arial=arial,helvetica,sans-serif;Courier New=courier
	 * new,courier,monospace;Georgia=georgia,times new
	 * roman,times,serif;Tahoma=tahoma,arial,helvetica,sans-serif;Times New
	 * Roman=times new
	 * roman,times,serif;Verdana=verdana,arial,helvetica,sans-serif;Impact=impact;WingDings=wingdings
	 * 
	 * @param fonts
	 */
	public void setFonts(String fonts)
	{
		this.fonts = fonts;
	}

	protected void definePluginSettings(StringBuffer buffer)
	{
		define(buffer, "fullpage_fonts", fonts);
		define(buffer, "fullpage_default_fontsizes", fontSizes);
		define(buffer, "fullpage_default_font_family", fontFamily);
		define(buffer, "fullpage_default_xml_pi", xmlDeclaration ? "true" : "false");
		define(buffer, "fullpage_default_langcode", language != null ? language.getLanguage() : null);
		define(buffer, "fullpage_default_title", title);
	}

}
