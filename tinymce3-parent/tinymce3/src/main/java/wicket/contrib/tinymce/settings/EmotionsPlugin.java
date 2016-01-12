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
package wicket.contrib.tinymce.settings;

/**
 * The emotions plugin is able to insert smiley images into the TinyMCE editable area.
 * 
 * @author Iulian-Corneliu COSTAN
 */
public class EmotionsPlugin extends Plugin
{
	private static final long serialVersionUID = 1L;
	private PluginButton emotionsButton;

	/**
	 * Construct.
	 */
	public EmotionsPlugin()
	{
		super("emotions");
		emotionsButton = new PluginButton("emotions", this);
	}

	/**
	 * @return
	 */
	public PluginButton getEmotionsButton()
	{
		return emotionsButton;
	}
}
