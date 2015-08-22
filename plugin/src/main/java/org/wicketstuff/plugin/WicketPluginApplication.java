package org.wicketstuff.plugin;

import org.apache.wicket.Application;
import org.apache.wicket.protocol.http.WebApplication;
import org.wicketstuff.plugin.def.DefaultPluginRegistry;

/**
 * @author James Carman
 * @since 1.4
 */
public abstract class WicketPluginApplication extends WebApplication
{
// **********************************************************************************************************************
// Fields
// **********************************************************************************************************************

	private PluginRegistry pluginRegistry = new DefaultPluginRegistry();

// **********************************************************************************************************************
// Static Methods
// **********************************************************************************************************************

	public static WicketPluginApplication get()
	{
		return (WicketPluginApplication)Application.get();
	}

	/**
	 * A shortcut method to lookup a plugin.
	 * 
	 * @param pluginType
	 *            the plugin type
	 * @param <T>
	 *            the plugin type
	 * @return the plugin
	 */
	public static <T> T lookupPlugin(Class<T> pluginType)
	{
		return get().getPluginRegistry().lookupPlugin(pluginType);
	}

// **********************************************************************************************************************
// Constructors
// **********************************************************************************************************************

	public WicketPluginApplication()
	{
	}

// **********************************************************************************************************************
// Getter/Setter Methods
// **********************************************************************************************************************

	public PluginRegistry getPluginRegistry()
	{
		return pluginRegistry;
	}

	public void setPluginRegistry(PluginRegistry pluginRegistry)
	{
		this.pluginRegistry = pluginRegistry;
	}
}
