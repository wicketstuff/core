/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.security.swarm.actions;

import java.util.List;

import junit.framework.TestCase;

import org.apache.wicket.Component;
import org.apache.wicket.authorization.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.security.actions.Access;
import org.wicketstuff.security.actions.ActionFactory;
import org.wicketstuff.security.actions.Actions;
import org.wicketstuff.security.actions.AllActions;
import org.wicketstuff.security.actions.Enable;
import org.wicketstuff.security.actions.Inherit;
import org.wicketstuff.security.actions.RegistrationException;
import org.wicketstuff.security.actions.Render;
import org.wicketstuff.security.actions.WaspAction;

/**
 * Tests for the {@link SwarmActionFactory}.
 * 
 * @author marrink
 */
public class SwarmActionFactoryTest extends TestCase
{
	private static final Logger log = LoggerFactory.getLogger(SwarmActionFactoryTest.class);

	private static final String KEY = "TEST_FACTORY";

	private SwarmActionFactory factory;

	/**
	 * 
	 * Construct.
	 * 
	 * @param name
	 */
	public SwarmActionFactoryTest(String name)
	{
		super(name);
	}

	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp()
	{
		factory = new SwarmActionFactory(KEY);
	}

	/**
	 * @see junit.framework.TestCase#tearDown()
	 */
	@Override
	protected void tearDown()
	{
		factory = null;
		Actions.unregisterActionFactory(KEY);
	}

	/**
	 * Test for {@link AllActions}.
	 */
	public void testAllActions()
	{
		WaspAction action = factory.getAction(AllActions.class);
		assertNotNull(action);
		List<WaspAction> actions = factory.getRegisteredActions();
		assertFalse(actions.isEmpty());
		for (int i = 0; i < actions.size(); i++)
		{
			assertTrue(action.implies(actions.get(i)));
		}
		try
		{
			factory.register(AllActions.class, "all");
			fail("Should not be able to register the 'all' action");
		}
		catch (RegistrationException e)
		{
			log.debug(e.getMessage());
		}
		WaspAction action2 = factory.getAction("all"); // caches the name all
		assertEquals(action, action2);
		assertNotSame(action, action2);
		// not same since AllActions.class will always deliver a new instance
		assertSame(action2, factory.getAction("all")); // cache lookup
	}

	/**
	 * @see SwarmActionFactory#getAction(Action)
	 */
	public void testGetActionAction()
	{
		WaspAction action = factory.getAction(Component.RENDER);
		assertNotNull(action);
		assertEquals(factory.getAction(Render.class), action);
		assertEquals(factory.getAction("render"), action);

		Action wicketAction = new Action("inherit, render");
		action = factory.getAction(wicketAction);
		assertNotNull(action);
		assertEquals(factory.getAction(wicketAction.getName()), action);

		assertNull(factory.getAction((Action)null));
		assertNull(factory.getAction(new Action("foo")));
	}

	/**
	 * 
	 * @see SwarmActionFactory#getAction(String)
	 */
	public void testGetActionString()
	{
		WaspAction action = factory.getAction(Action.RENDER);
		assertNotNull(action);
		assertEquals(factory.getAction(Render.class), action);
		assertEquals(factory.getAction(Access.class), factory.getAction((String)null));
		assertEquals(factory.getAction(Access.class), factory.getAction(""));
		try
		{
			factory.getAction("foo");
			fail("foo is not registered action");
		}
		catch (IllegalArgumentException e)
		{
			log.debug(e.getMessage());
		}
	}

	/**
	 * Test if defaults are correctly registered.
	 * 
	 * @see SwarmActionFactory#getAction(int)
	 */
	public void testGetActionInt()
	{
		WaspAction action;
		try
		{
			action = factory.getAction(-1);// -1 does not exist
			fail("found action that should not be registered by the default factory: " + action);
		}
		catch (IllegalArgumentException e)
		{
			log.debug(e.getMessage());
		}
		String[] names = new String[] { "access", "access, inherit", "access, render",
				"access, inherit, render", "access", "access, inherit", "access, render, enable",
				"access, inherit, render, enable" };
		for (int i = 0; i < 8; i++)
		{
			action = factory.getAction(i);
			assertNotNull("action " + i + "was null", action);
			assertEquals("iteration " + i, names[i], action.getName());
		}
		try
		{
			action = factory.getAction(8);// 8 does not exist
			fail("found action that should not be registered by the default factory: " + action);
		}
		catch (IllegalArgumentException e)
		{
			log.debug(e.getMessage());
		}
	}

	/**
	 * Test if defaults are correctly registered.
	 * 
	 * @see SwarmActionFactory#getAction(Class)
	 */
	public void testGetActionClass()
	{
		assertNotNull(factory.getAction(Access.class));
		assertNotNull(factory.getAction(Inherit.class));
		assertNotNull(factory.getAction(Render.class));
		assertNotNull(factory.getAction(Enable.class));
		// test implies of defaults
		assertTrue(factory.getAction(Render.class).implies(factory.getAction(Access.class)));
		assertFalse(factory.getAction(Render.class).implies(factory.getAction(Inherit.class)));
		assertTrue(factory.getAction(Enable.class).implies(factory.getAction(Render.class)));
	}

	/**
	 * Test registering a String.
	 * 
	 * @see SwarmActionFactory#register(Class, String)
	 */
	public void testRegisterClassString()
	{
		assertEquals(4, factory.getNumberOfRegisteredClasses());
		try
		{
			factory.register(Hack.class, "hack");
			assertEquals(5, factory.getNumberOfRegisteredClasses());
			factory.register(Hack.class, "hacker");
			assertEquals(5, factory.getNumberOfRegisteredClasses());
			assertEquals("hack", factory.getAction(Hack.class).getName());
			register26Actions();
			factory.register(BugsBunny.class, "bugsbunny");
			assertEquals(32, factory.getNumberOfRegisteredClasses());
		}
		catch (RegistrationException e)
		{
			log.error(e.getMessage(), e);
			fail(e.getMessage());
		}
		try
		{
			factory.register(Illegal.class, "illegal");
			fail("to many actions");
		}
		catch (RegistrationException e)
		{
			log.debug(e.getMessage());
		}
	}

	private void register26Actions() throws RegistrationException
	{
		factory.register(Admin.class, "admin");
		factory.register(Root.class, "root");
		factory.register(Ace.class, "ace");
		factory.register(King.class, "king");
		factory.register(Queen.class, "queen");
		factory.register(Tiran.class, "tiran");
		factory.register(Emperor.class, "emperor");
		factory.register(Saint.class, "saint");
		factory.register(Pope.class, "pope");
		factory.register(Satanist.class, "satanist");
		factory.register(Angel.class, "angel");
		factory.register(Demon.class, "demon");
		factory.register(God.class, "god");
		factory.register(Devil.class, "devil");
		factory.register(Everything.class, "everything");
		factory.register(Nothing.class, "nothing");
		factory.register(Me.class, "me");
		factory.register(You.class, "you");
		factory.register(Him.class, "him");
		factory.register(Them.class, "them");
		factory.register(We.class, "we");
		factory.register(Cat.class, "cat");
		factory.register(Dog.class, "dog");
		factory.register(Mouse.class, "mouse");
		factory.register(MightyMouse.class, "mightymouse");
		factory.register(Superman.class, "superman");

	}

	/**
	 * Test registration of actions.
	 * 
	 * @see SwarmActionFactory#register(Class, SwarmAction)
	 */
	public void testRegisterClassSwarmAction()
	{
		try
		{
			factory.register(Hack.class, "hack");
			register26Actions();
			Bugsy bugsy = new Bugsy(factory.nextPowerOf2(), "bugs bunny", factory);
			factory.register(BugsBunny.class, bugsy);
			assertEquals(bugsy, factory.getAction(BugsBunny.class));
			assertTrue(factory.nextPowerOf2() == Integer.MAX_VALUE);
			// overflow happens here
			assertTrue(Integer.MAX_VALUE + "!=" + bugsy.actions(),
				Integer.MAX_VALUE == bugsy.actions());
			assertEquals(32, factory.getNumberOfRegisteredClasses());
		}
		catch (RegistrationException e)
		{
			log.debug(e.getMessage());
			fail(e.getMessage());
		}
	}

	// whole bunch of action interfaces
	private static interface Hack extends WaspAction
	{

	}

	private static interface Admin extends WaspAction
	{

	}

	private static interface Root extends WaspAction
	{

	}

	private static interface Ace extends WaspAction
	{

	}

	private static interface King extends WaspAction
	{

	}

	private static interface Queen extends WaspAction
	{

	}

	private static interface Tiran extends WaspAction
	{

	}

	private static interface Emperor extends WaspAction
	{

	}

	private static interface Saint extends WaspAction
	{

	}

	private static interface Pope extends WaspAction
	{

	}

	private static interface Satanist extends WaspAction
	{

	}

	private static interface Angel extends WaspAction
	{

	}

	private static interface Demon extends WaspAction
	{

	}

	private static interface God extends WaspAction
	{

	}

	private static interface Devil extends WaspAction
	{

	}

	private static interface Everything extends WaspAction
	{

	}

	private static interface Nothing extends WaspAction
	{

	}

	private static interface Me extends WaspAction
	{

	}

	private static interface You extends WaspAction
	{

	}

	private static interface Him extends WaspAction
	{

	}

	private static interface Them extends WaspAction
	{

	}

	private static interface We extends WaspAction
	{

	}

	private static interface Cat extends WaspAction
	{

	}

	private static interface Dog extends WaspAction
	{

	}

	private static interface Mouse extends WaspAction
	{

	}

	private static interface MightyMouse extends WaspAction
	{

	}

	private static interface Superman extends WaspAction
	{

	}

	private static interface BugsBunny extends WaspAction
	{

	}

	private static interface Illegal extends WaspAction
	{

	}

	private static class Bugsy extends SwarmAction implements BugsBunny
	{

		/**
		 * Construct.
		 * 
		 * @param actions
		 * @param name
		 * @param factory
		 */
		protected Bugsy(int actions, String name, ActionFactory factory)
		{
			super(getIt(actions, factory), name, KEY);
		}

		private static int getIt(int initial, ActionFactory factory)
		{
			return initial | getAction(factory, MightyMouse.class) |
				getAction(factory, Mouse.class) | getAction(factory, Dog.class) |
				getAction(factory, Cat.class) | getAction(factory, We.class) |
				getAction(factory, Them.class) | getAction(factory, Him.class) |
				getAction(factory, You.class) | getAction(factory, Me.class) |
				getAction(factory, Nothing.class) | getAction(factory, Everything.class) |
				getAction(factory, Devil.class) | getAction(factory, God.class) |
				getAction(factory, Demon.class) | getAction(factory, Angel.class) |
				getAction(factory, Satanist.class) | getAction(factory, Pope.class) |
				getAction(factory, Saint.class) | getAction(factory, Emperor.class) |
				getAction(factory, Tiran.class) | getAction(factory, Queen.class) |
				getAction(factory, King.class) | getAction(factory, Ace.class) |
				getAction(factory, Root.class) | getAction(factory, Admin.class) |
				getAction(factory, Hack.class) | getAction(factory, Enable.class) |
				getAction(factory, Superman.class) | getAction(factory, Inherit.class);
		}

		private static int getAction(ActionFactory factory, Class<? extends WaspAction> action)
		{
			return ((SwarmAction)factory.getAction(action)).actions();
		}
	}

}
