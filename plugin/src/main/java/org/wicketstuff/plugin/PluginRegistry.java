package org.wicketstuff.plugin;

/**
 * @since 1.4
 */
public interface PluginRegistry
{
	public void registerPlugin(Object plugin);

	<T> T lookupPlugin(Class<T> pluginType);
}
