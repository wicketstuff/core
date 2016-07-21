/*
 *  Copyright (C) 2005  Iulian-Corneliu Costan
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package wicket.contrib.tinymce4.settings;

/**
 * Base class for tinymce button. There ae two types of buttons:
 * <ul>
 * <li>default button - these buttons are defined in TinyMCESettings class.</li>
 * <li>plugin button - these buttons are defined by individual plugins.</li>
 * </ul>
 * 
 * @author Iulian-Corneliu COSTAN
 */
public class Button extends wicket.contrib.tinymce4.settings.Enum
{
	private static final long serialVersionUID = 1L;

	// default tinymce buttons
	public static final Button bold = new Button("bold");
	public static final Button italic = new Button("italic");
	public static final Button underline = new Button("underline");
	public static final Button strikethrough = new Button("strikethrough");
	public static final Button justifyleft = new Button("justifyleft");
	public static final Button justifycenter = new Button("justifycenter");
	public static final Button justifyright = new Button("justifyright");
	public static final Button justifyfull = new Button("justifyfull");
	public static final Button styleselect = new Button("styleselect");
	public static final Button formatselect = new Button("formatselect");
	public static final Button bullist = new Button("bullist");
	public static final Button numlist = new Button("numlist");
	public static final Button outdent = new Button("outdent");
	public static final Button indent = new Button("indent");
	public static final Button undo = new Button("undo");
	public static final Button redo = new Button("redo");
	public static final Button link = new Button("link");
	public static final Button unlink = new Button("unlink");
	public static final Button anchor = new Button("anchor");
	public static final Button image = new Button("image");
	public static final Button cleanup = new Button("cleanup");
	public static final Button help = new Button("help");
	public static final Button code = new Button("code");
	public static final Button hr = new Button("hr");
	public static final Button removeformat = new Button("removeformat");
	public static final Button visualaid = new Button("visualaid");
	public static final Button sub = new Button("sub");
	public static final Button sup = new Button("sup");
	public static final Button charmap = new Button("charmap");
	public static final Button separator = new Button("separator");

	// others buttons added by plugins
	public static final Button newdocument = new Button("newdocument");
	public static final Button cut = new Button("cut");
	public static final Button copy = new Button("copy");
	public static final Button fontselect = new Button("fontselect");
	public static final Button fontsizeselect = new Button("fontsizeselect");
	public static final Button forecolor = new Button("forecolor");
	public static final Button backcolor = new Button("backcolor");


	protected Button(String name)
	{
		super(name);
	}
}
