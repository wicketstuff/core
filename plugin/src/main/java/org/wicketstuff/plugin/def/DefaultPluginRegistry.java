package org.wicketstuff.plugin.def;

import java.io.Externalizable;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.wicket.WicketRuntimeException;
import org.wicketstuff.plugin.PluginRegistry;

/**
 * @version 1.4
 */
public class DefaultPluginRegistry implements PluginRegistry
{
// **********************************************************************************************************************
// Fields
// **********************************************************************************************************************

	public Map<Class<?>, List<Object>> pluginMap = new HashMap<Class<?>, List<Object>>();
	private Set<Class<?>> stopClasses = new HashSet<Class<?>>();

// **********************************************************************************************************************
// Constructors
// **********************************************************************************************************************

	public DefaultPluginRegistry()
	{
		stopClasses.add(Object.class);
		stopClasses.add(Serializable.class);
		stopClasses.add(Comparable.class);
		stopClasses.add(Externalizable.class);
	}

// **********************************************************************************************************************
// PluginRegistry Implementation
// **********************************************************************************************************************

	@SuppressWarnings("unchecked")
	public <T> T lookupPlugin(Class<T> pluginType)
	{
		List<T> plugins = (List<T>)pluginMap.get(pluginType);
		if (plugins == null)
		{
			throw new WicketRuntimeException("Plugin of type " + pluginType.getName() +
				" not found.");
		}
		if (plugins.size() == 1)
		{
			return plugins.get(0);
		}
		throw new WicketRuntimeException("There are " + plugins.size() +
			" plugins registered of type " + pluginType.getName() + ".");
	}

	public void registerPlugin(Object plugin)
	{
		final List<Class<?>> allClasses = getAllClasses(plugin.getClass());
		for (Class<?> c : allClasses)
		{
			List<Object> plugins = pluginMap.get(c);
			if (plugins == null)
			{
				plugins = new LinkedList<Object>();
				pluginMap.put(c, plugins);
			}
			plugins.add(plugin);
		}
	}

// **********************************************************************************************************************
// Getter/Setter Methods
// **********************************************************************************************************************

	public Set<Class<?>> getStopClasses()
	{
		return stopClasses;
	}

	public void setStopClasses(Set<Class<?>> stopClasses)
	{
		this.stopClasses = stopClasses;
	}

// **********************************************************************************************************************
// Other Methods
// **********************************************************************************************************************

	private void enqueue(Class<?> c, LinkedList<Class<?>> queue)
	{
		if (c != null && !stopClasses.contains(c))
		{
			queue.addLast(c);
		}
	}

	protected void enqueueAncestors(Class<?> c, LinkedList<Class<?>> queue)
	{
		for (Class<?> i : c.getInterfaces())
		{
			enqueue(i, queue);
		}
		enqueue(c.getSuperclass(), queue);
	}

	public List<Class<?>> getAllClasses(Class<?> cls)
	{
		final LinkedList<Class<?>> queue = new LinkedList<Class<?>>();
		final List<Class<?>> list = new LinkedList<Class<?>>();
		final Set<Class<?>> set = new HashSet<Class<?>>();
		queue.add(cls);
		while (!queue.isEmpty())
		{
			Class<?> c = queue.removeFirst();
			if (!set.contains(c))
			{
				list.add(c);
				set.add(c);
			}
			enqueueAncestors(c, queue);
		}
		return list;
	}
}
