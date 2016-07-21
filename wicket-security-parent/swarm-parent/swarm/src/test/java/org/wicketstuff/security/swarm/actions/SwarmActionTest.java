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

import junit.framework.TestCase;

import org.wicketstuff.security.actions.Access;
import org.wicketstuff.security.actions.ActionFactory;
import org.wicketstuff.security.actions.Actions;
import org.wicketstuff.security.actions.Enable;
import org.wicketstuff.security.actions.Inherit;
import org.wicketstuff.security.actions.Render;
import org.wicketstuff.security.actions.WaspAction;

/**
 * Tests WaspAction class.
 * 
 * @author marrink
 */
public class SwarmActionTest extends TestCase
{
	private static final String KEY = "ACTION_TEST";

	/**
	 * Constructor for WaspActionTest.
	 * 
	 * @param arg0
	 */
	public SwarmActionTest(String arg0)
	{
		super(arg0);
	}

	/**
	 * Test method for {@link SwarmAction#hashCode()}
	 */
	public void testHashCode()
	{
		WaspAction action = new SwarmAction(5, "test", KEY);
		// persistent
		assertEquals(action.hashCode(), action.hashCode());
		WaspAction action2 = new SwarmAction(5, "test", KEY);
		// equal
		assertEquals(action.hashCode(), action2.hashCode());
		WaspAction action3 = new TestAction(5, "does not matter", KEY);
		assertEquals(action2, action3);
		assertTrue(action3.hashCode() == action2.hashCode());

	}

	/**
	 * Test method for {@link SwarmAction#implies(int)}
	 */
	public void testImpliesInt()
	{
		SwarmAction action = new SwarmAction(5, "test", KEY);
		assertTrue(action.implies(5));
		assertTrue(action.implies(1));
		assertFalse(action.implies(6));

	}

	/**
	 * Test method for {@link SwarmAction#implies(WaspAction)}
	 */
	public void testImpliesWaspAction()
	{
		ActionFactory factory = Actions.getActionFactory(KEY);
		WaspAction action = factory.getAction(Render.class);
		WaspAction action2 = action.add(factory.getAction(Inherit.class));
		assertTrue(action2.implies(action));
		assertFalse(action.implies(action2));
	}

	/**
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp()
	{
		new SwarmActionFactory(KEY);
	}

	/**
	 * @see junit.framework.TestCase#tearDown()
	 */
	@Override
	protected void tearDown()
	{
		Actions.unregisterActionFactory(KEY);
	}

	/**
	 * Test method for {@link SwarmAction#equals(Object)}
	 */
	public void testEqualsObject()
	{
		WaspAction action = new SwarmAction(5, "test", KEY);
		// null
		assertFalse(action.equals(null));
		// reflexive
		assertEquals(action, action);
		// symmetric
		WaspAction action2 = new SwarmAction(5, "test", KEY);
		assertEquals(action, action2);
		assertEquals(action2, action);
		WaspAction action3 = new SwarmAction(6, "test2", KEY);
		assertFalse(action.equals(action3));
		assertFalse(action3.equals(action));
		// transitive
		WaspAction action4 = new TestAction(5, "test", KEY);
		assertEquals(action, action4);
		assertEquals(action4, action2);
		// action2 already equals action
		// consistent
		// action is inmutable so it is consistent
	}

	/**
	 * Test method for {@link SwarmAction#add(int)}
	 */
	public void testAddInt()
	{
		ActionFactory factory = Actions.getActionFactory(KEY);
		SwarmAction action = (SwarmAction)factory.getAction(Render.class);
		assertEquals(2, action.actions());
		SwarmAction action2 = (SwarmAction)action.add(((SwarmAction)factory.getAction(Access.class)).actions());
		assertEquals(2, action.actions()); // check inmutability
		assertEquals(2, action2.actions());
		assertNotSame(action, action2);
		SwarmAction action3 = (SwarmAction)action.add(((SwarmAction)factory.getAction(Enable.class)).actions());
		assertEquals(2, action.actions());
		assertEquals(6, action3.actions());

	}

	/**
	 * Test method for {@link SwarmAction#add(WaspAction)}
	 */
	public void testAddWaspAction()
	{
		ActionFactory factory = Actions.getActionFactory(KEY);
		SwarmAction action = (SwarmAction)factory.getAction(Render.class);
		assertEquals(2, action.actions());
		SwarmAction action2 = action.add(factory.getAction(Access.class));
		assertEquals(2, action.actions()); // check inmutability
		assertEquals(2, action2.actions());
		assertNotSame(action, action2);
		SwarmAction action3 = action.add(factory.getAction(Enable.class));
		assertEquals(2, action.actions());
		assertEquals(6, action3.actions());

	}

	/**
	 * Test various constructor scenarios.
	 */
	public void testConstructor()
	{
		try
		{
			new SwarmAction(6, null, KEY);
			fail("description should be required");
		}
		catch (IllegalArgumentException e)
		{
			// noop
		}
		try
		{
			new SwarmAction(-10, "foobar", KEY);
			fail("negative numbers should not be allowed");
		}
		catch (IllegalArgumentException e)
		{
			// noop
		}
	}

	private static class TestAction extends SwarmAction
	{
		/**
		 * Construct.
		 * 
		 * @param action
		 * @param name
		 * @param key
		 */
		protected TestAction(int action, String name, Object key)
		{
			super(action, name, key);
		}

	}
}
