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
 * This plugin adds paste as plain text and paste from Word icons to TinyMCE.
 * <p>
 * Note: Only basic functionality is implemented, more work is needed.
 * 
 * @author Iulian-Corneliu COSTAN
 */
public class PastePlugin extends Plugin
{
	private static final long serialVersionUID = 1L;

	private PluginButton pasteButton;
	private PluginButton pasteWordButton;
	private PluginButton pasteTextButton;

	/**
	 * Construct.
	 */
	public PastePlugin()
	{
		super("paste");
		pasteButton = new PluginButton("paste", this);
		pasteWordButton = new PluginButton("pasteword", this);
		pasteTextButton = new PluginButton("pastetext", this);
	}

	/**
	 * @return the paste button
	 */
	public PluginButton getPasteButton()
	{
		return pasteButton;
	}

	/**
	 * @return the paste word button
	 */
	public PluginButton getPasteWordButton()
	{
		return pasteWordButton;
	}

	/**
	 * @return the paste text button
	 */
	public PluginButton getPasteTextButton()
	{
		return pasteTextButton;
	}
}
