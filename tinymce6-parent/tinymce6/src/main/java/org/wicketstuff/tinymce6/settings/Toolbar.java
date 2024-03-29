/*
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

package org.wicketstuff.tinymce6.settings;

import java.io.Serializable;

/**
 * Toolbar for Tinymce 6
 */
public class Toolbar implements Serializable
{
	private static final long serialVersionUID = 1L;

	/**
	 * Separator for Button Groups
	 */
	public static final String SEPARATOR = "|";

	private final String id;
	private final StringBuilder builder = new StringBuilder();

	public Toolbar(String id, String value)
	{
		this(id);
		builder.append(value);
	}

	public Toolbar(String id)
	{
		this.id = id;
	}

	public String getId()
	{
		return id;
	}

	/**
	 * Adds a button.
	 *
	 * @param buttonName
	 *            Name of the button
	 */
	public void appendButton(String buttonName)
	{
		builder.append(buttonName).append(' ');
	}

	/**
	 * Adds a button.
	 *
	 * @param button
	 *            button
	 */
	public void appendButton(Button button)
	{
		builder.append(button.name()).append(' ');
	}

	/**
	 * Appends a {@link #SEPARATOR}
	 */
	public void appendSeparator()
	{
		appendButton(SEPARATOR);
	}

	@Override
	public String toString()
	{
		return builder.toString();
	}
}
