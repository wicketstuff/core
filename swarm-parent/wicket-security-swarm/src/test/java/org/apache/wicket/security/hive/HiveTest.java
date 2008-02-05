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
package org.apache.wicket.security.hive;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.apache.wicket.security.hive.authorization.EverybodyPrincipal;
import org.apache.wicket.security.hive.authorization.TestPermission;

/**
 * Test hive operations.
 * 
 * @author marrink
 */
public class HiveTest extends TestCase
{
	/**
	 * 
	 * Construct.
	 * 
	 * @param name
	 */
	public HiveTest(String name)
	{
		super(name);
	}

	/**
	 * @see BasicHive#addPrincipal(org.apache.wicket.security.hive.authorization.Principal,
	 *      java.util.Collection)
	 */
	public void testAddPrincipal()
	{
		BasicHive hive = new BasicHive();
		try
		{
			hive.addPrincipal(new EverybodyPrincipal(), null);
			fail("should not be possible to add null permission.");
		}
		catch (IllegalArgumentException e)
		{
			assertNotNull(e);
		}
		assertFalse(hive.containsPrincipal(new EverybodyPrincipal()));
		List permissions = new ArrayList();
		permissions.add(new TestPermission("foo.bar"));
		permissions.add(new TestPermission("test"));
		hive.addPrincipal(new EverybodyPrincipal(), permissions);
		assertTrue(hive.containsPrincipal(new EverybodyPrincipal()));
		assertFalse(hive.isLocked());
		hive.lock();
		assertTrue(hive.isLocked());
		try
		{
			hive.addPrincipal(new EverybodyPrincipal(), permissions);
			fail("hive should be locked");
		}
		catch (IllegalStateException e)
		{
		}

	}

	/**
	 * @see BasicHive#addPermission(org.apache.wicket.security.hive.authorization.Principal,
	 *      org.apache.wicket.security.hive.authorization.Permission)
	 */
	public void testAddPermission()
	{
		BasicHive hive = new BasicHive();
		try
		{
			hive.addPermission(new EverybodyPrincipal(), null);
			fail("should not be possible to add null permission.");
		}
		catch (IllegalArgumentException e)
		{
			assertNotNull(e);
		}
		assertFalse(hive.containsPrincipal(new EverybodyPrincipal()));
		hive.addPermission(new EverybodyPrincipal(), new TestPermission("foobar"));
		assertTrue(hive.containsPrincipal(new EverybodyPrincipal()));
		assertFalse(hive.isLocked());
		hive.lock();
		assertTrue(hive.isLocked());
		try
		{
			hive.addPermission(new EverybodyPrincipal(), new TestPermission("foobar"));
			fail("hive should be locked");
		}
		catch (IllegalStateException e)
		{
		}
	}

	/**
	 * @see BasicHive#hasPermission(org.apache.wicket.security.hive.authentication.Subject,
	 *      org.apache.wicket.security.hive.authorization.Permission)
	 */
	public void testHasPermision()
	{
		BasicHive hive = new BasicHive();
		assertFalse(hive.containsPrincipal(new EverybodyPrincipal()));
		hive.addPermission(new EverybodyPrincipal(), new TestPermission("foobar"));
		assertTrue(hive.containsPrincipal(new EverybodyPrincipal()));
		assertTrue(hive.hasPermission(null, new TestPermission("foobar")));
		assertFalse(hive.hasPermission(null, new TestPermission("foo.bar")));

		hive.addPermission(new EverybodyPrincipal(), new TestPermission("test", "read, write"));
		assertTrue(hive.hasPermission(null, new TestPermission("test", "read")));
		assertTrue(hive.hasPermission(null, new TestPermission("test")));
	}
}
