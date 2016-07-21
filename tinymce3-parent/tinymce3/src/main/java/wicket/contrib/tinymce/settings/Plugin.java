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
 * Base class for all TinyMCE plugins.
 * <p>
 * Note: Only basic functionality is implemented, more work is needed.
 * 
 * @author Iulian-Corneliu Costan (iulian.costan@gmail.com)
 * @author Frank Bille Jensen (fbille@avaleo.net)
 */
public abstract class Plugin extends wicket.contrib.tinymce.settings.Enum
{
	private static final long serialVersionUID = 1L;

	private String pluginPath;

	protected Plugin(String name)
	{
		this(name, null);
	}

	protected Plugin(String name, String pluginPath)
	{
		super(name);
		this.pluginPath = pluginPath;
	}

	/**
	 * @return the path to custom plugin
	 */
	public String getPluginPath()
	{
		return pluginPath;
	}

	/**
	 * Override this in specific plugins if the plugin needs to be able to add something to the
	 * javascript. Use this to define the javascript callback functions if the plugin requires this
	 * (i.e. "paste_callback".
	 * <p>
	 * NOTE: This should NOT be used to configure settings for the plugin. The output is added AFTER
	 * the tinyMCE.init().
	 * 
	 * @param buffer
	 *            The output buffer which the plugin should append to, if they have some additional
	 *            javascript.
	 */
	protected void definePluginExtensions(StringBuffer buffer)
	{
		// do nothing;
	}

	/**
	 * Define configuration settings for this plugin.
	 * <p>
	 * I.e. the "paste" plugin can be configured with different settings such as: "paste_callback"
	 * etc.
	 * 
	 * @param buffer
	 *            buffer to append to
	 * @param settingKey
	 *            The setting to set. I.e. "paste_callback"
	 * @param value
	 *            The value to set on the setting. I.e. "myCallbackFunction"
	 */
	protected void definePluginSettings(StringBuffer buffer)
	{
		// do nothting
	}

	protected void define(StringBuffer buffer, String key, String value)
	{
		if (value != null)
		{
			buffer.append(",\n\t")
				.append(key)
				.append(" : ")
				.append("\"")
				.append(value)
				.append("\"");
		}
	}

	public void setPluginPath(String pluginPath)
	{
		this.pluginPath = pluginPath;
	}

}
