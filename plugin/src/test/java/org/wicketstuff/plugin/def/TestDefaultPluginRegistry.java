package org.wicketstuff.plugin.def;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.Externalizable;
import java.io.Serializable;
import java.util.HashSet;

import org.apache.wicket.WicketRuntimeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @since 1.4
 */
public class TestDefaultPluginRegistry
{
// **********************************************************************************************************************
// Fields
// **********************************************************************************************************************

	private DefaultPluginRegistry registry;
	private PluginB registeredPlugin;

// **********************************************************************************************************************
// Other Methods
// **********************************************************************************************************************

	@BeforeEach
	protected void setUp() throws Exception
	{
		registry = new DefaultPluginRegistry();
		registeredPlugin = new PluginB();
		registry.registerPlugin(registeredPlugin);
	}

	@Test
	public void testOverrideStopClasses()
	{
		registry = new DefaultPluginRegistry();
		registry.setStopClasses(new HashSet<Class<?>>());
		registry.registerPlugin(registeredPlugin);
		assertSame(registry.lookupPlugin(Object.class), registeredPlugin);
		assertSame(registry.lookupPlugin(Serializable.class), registeredPlugin);
	}

	@Test
	public void testWithMultiplePluginsOfType()
	{
		registry.registerPlugin(new PluginB());
		try
		{
			registry.lookupPlugin(PluginB.class);
			fail("Registry containing two plugins of the same type should throw an exception.");
		}
		catch (WicketRuntimeException e)
		{
			// Do nothing, expected behavior!
		}
	}

	@Test
	public void testLookupPluginByClassHierarchy()
	{
		assertSame(registry.lookupPlugin(PluginB.class), registeredPlugin);
		assertSame(registry.lookupPlugin(PluginA.class), registeredPlugin);
		try
		{
			registry.lookupPlugin(Object.class);
			fail("Should not be able to lookup by a class registered as a stop class!");
		}
		catch (WicketRuntimeException e)
		{
			// Do nothing, expected behavior!
		}
	}

	@Test
	public void testLookupPluginByInterface()
	{
		assertSame(registry.lookupPlugin(IPlugin.class), registeredPlugin);
		try
		{
			registry.lookupPlugin(Serializable.class);
			fail("Should not be able to lookup by an interface registered as a stop class!");
		}
		catch (WicketRuntimeException e)
		{
			// Do nothing, expected behavior!
		}
	}

	@Test
	public void testDefaultRegisteredStopClasses()
	{
		assertTrue(registry.getStopClasses().contains(Object.class));
		assertTrue(registry.getStopClasses().contains(Serializable.class));
		assertTrue(registry.getStopClasses().contains(Externalizable.class));
		assertTrue(registry.getStopClasses().contains(Comparable.class));
	}

// **********************************************************************************************************************
// Inner Classes
// **********************************************************************************************************************

	public static interface IPlugin extends Serializable
	{
	}

	public static class PluginA
	{
	}

	public static class PluginB extends PluginA implements IPlugin
	{

		private static final long serialVersionUID = 1L;
	}
}
