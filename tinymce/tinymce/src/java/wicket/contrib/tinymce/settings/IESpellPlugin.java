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
 * @author Iulian Costan (iulian.costan@gmail.com)
 */
public class IESpellPlugin extends Plugin
{
	private static final long serialVersionUID = 1L;

	private PluginButton iespellButton;

	/**
	 * Construct iespell plugin
	 */
	public IESpellPlugin()
	{
		super("iespell");

		iespellButton = new PluginButton("iespell", this);
	}

	/**
	 * @return PLuginButton the iespell button
	 */
	public PluginButton getIespellButton()
	{
		return iespellButton;
	}
}
