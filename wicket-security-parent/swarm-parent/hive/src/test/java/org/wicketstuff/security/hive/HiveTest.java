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
package org.wicketstuff.security.hive;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;

import org.wicketstuff.security.hive.authorization.EverybodyPrincipal;
import org.wicketstuff.security.hive.authorization.Permission;
import org.wicketstuff.security.hive.authorization.Principal;
import org.wicketstuff.security.hive.authorization.SimplePrincipal;
import org.wicketstuff.security.hive.authorization.TestPermission;

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
	 * @see BasicHive#addPrincipal(org.wicketstuff.security.hive.authorization.Principal,
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
		List<Permission> permissions = new ArrayList<Permission>();
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
	 * @see BasicHive#addPermission(org.wicketstuff.security.hive.authorization.Principal,
	 *      org.wicketstuff.security.hive.authorization.Permission)
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
	 * @see BasicHive#hasPermission(org.wicketstuff.security.hive.authentication.Subject,
	 *      org.wicketstuff.security.hive.authorization.Permission)
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

	/**
	 * @see BasicHive#getPrincipals(org.wicketstuff.security.hive.authorization.Permission)
	 */
	public void testGetPrincipals()
	{
		BasicHive hive = new BasicHive();
		hive.addPermission(new EverybodyPrincipal(), new TestPermission("foobar"));
		hive.addPermission(new SimplePrincipal("foo.bar"), new TestPermission("foobar"));
		hive.addPermission(new EverybodyPrincipal(), new TestPermission("test", "read, write"));
		assertEquals(Collections.EMPTY_SET, hive.getPrincipals(new TestPermission("foo")));
		Set<Principal> principals = hive.getPrincipals(new TestPermission("foobar"));
		assertEquals(2, principals.size());
		assertTrue(principals.contains(new EverybodyPrincipal()));
		assertTrue(principals.contains(new SimplePrincipal("foo.bar")));
		principals = hive.getPrincipals(new TestPermission("test", "read, write"));
		assertEquals(1, principals.size());
		assertTrue(principals.contains(new EverybodyPrincipal()));

	}

	/**
	 * @see BasicHive#getPermissions(org.wicketstuff.security.hive.authorization.Principal)
	 */
	public void testGetPermissions()
	{
		BasicHive hive = new BasicHive();
		hive.addPermission(new EverybodyPrincipal(), new TestPermission("foobar"));
		hive.addPermission(new SimplePrincipal("foo.bar"), new TestPermission("foobar"));
		hive.addPermission(new EverybodyPrincipal(), new TestPermission("test", "read, write"));
		assertEquals(Collections.EMPTY_SET, hive.getPermissions(new SimplePrincipal("foobar")));
		Set<Permission> permissions = hive.getPermissions(new EverybodyPrincipal());
		assertEquals(2, permissions.size());
		assertTrue(permissions.contains(new TestPermission("foobar")));
		assertTrue(permissions.contains(new TestPermission("test", "read, write")));
		permissions = hive.getPermissions(new SimplePrincipal("foo.bar"));
		assertEquals(1, permissions.size());
		assertTrue(permissions.contains(new TestPermission("foobar")));

	}
}
