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
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;

import org.apache.wicket.security.actions.Access;
import org.apache.wicket.security.actions.ActionFactory;
import org.apache.wicket.security.actions.Render;
import org.apache.wicket.security.actions.WaspAction;
import org.apache.wicket.security.hive.authentication.Subject;
import org.apache.wicket.security.hive.authorization.Permission;
import org.apache.wicket.security.hive.authorization.Principal;
import org.apache.wicket.security.hive.authorization.permissions.ActionPermission;
import org.apache.wicket.security.hive.config.TestActionFactory;
import org.apache.wicket.security.util.ManyToManyMap;

/**
 * test to see if we van intercept the principals and permissions stored and get
 * a list of principals containing a permission.
 * 
 * @author marrink
 */
public class HiveExtensionTest extends TestCase
{
	private ActionFactory factory;

	/**
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception
	{
		factory = new TestActionFactory("Foo");
	}

	/**
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception
	{
		factory.destroy();
	}

	public void testAddPrincipal()
	{
		Principal principal = new ScanManPrincipal("test2");
		ScanManSimpleCachingHive hive = new ScanManSimpleCachingHive();
		List list = new ArrayList(2);
		list.add(new MyPermission("p1", factory.getAction(Access.class)));
		list.add(new MyPermission("p2", factory.getAction(Render.class)));
		hive.addPrincipal(principal, list);
		hive.lock();
		Set set = hive.getPrincipals(new MyPermission("p1", factory.getAction(Access.class)));
		assertTrue(set.contains(principal));
		assertEquals(1, set.size());
	}

	public void testAddPermission()
	{
		Principal principal = new ScanManPrincipal("test1");
		ScanManSimpleCachingHive hive = new ScanManSimpleCachingHive();
		hive.addPermission(principal, new MyPermission("p1", factory.getAction(Access.class)));
		hive.addPermission(principal, new MyPermission("p2", factory.getAction(Render.class)));
		hive.lock();
		Set set = hive.getPrincipals(new MyPermission("p1", factory.getAction(Access.class)));
		assertTrue(set.contains(principal));
		assertEquals(1, set.size());
	}

	public void testEquals()
	{
		Principal p1 = new ScanManPrincipal("test");
		assertEquals(p1, p1);
		Principal p2 = new ScanManPrincipal("test");
		assertEquals(p1, p2);
	}

	/**
	 * Special hive used in this test.
	 * 
	 * @author warren
	 */
	private static final class ScanManSimpleCachingHive extends SimpleCachingHive
	{
		/**
		 * 
		 * Construct.
		 */
		public ScanManSimpleCachingHive()
		{
			super();
		}

		private ManyToManyMap hivePrincipalsAndPermissions = new ManyToManyMap();

		/**
		 * 
		 * @see org.apache.wicket.security.hive.BasicHive#addPrincipal(org.apache.wicket.security.hive.authorization.Principal,
		 *      java.util.Collection)
		 */
		public void addPrincipal(Principal principal, Collection permissions)
		{
			super.addPrincipal(principal, permissions);
			Iterator iterator = permissions.iterator();
			Permission permission = null;
			while (iterator.hasNext())
			{
				permission = (Permission)iterator.next();
				// Does not work
				hivePrincipalsAndPermissions.add(permission, principal);
				// Does work
				// hivePrincipalsAndPermissions.add(permission.getName(),
				// principal);
			}
		}

		/**
		 * 
		 * @see org.apache.wicket.security.hive.BasicHive#addPermission(org.apache.wicket.security.hive.authorization.Principal,
		 *      org.apache.wicket.security.hive.authorization.Permission)
		 */
		public void addPermission(Principal principal, Permission permission)
		{
			super.addPermission(principal, permission);
			// Does not work
			hivePrincipalsAndPermissions.add(permission, principal);
			// Does work
			// hivePrincipalsAndPermissions.add(permission.getName(),
			// principal);
		}

		/**
		 * Returns all the principals that contain the permission.
		 * 
		 * @param p
		 * @return set of principals or null
		 */
		public Set getPrincipals(Permission p)
		{
			// Does not work
			return hivePrincipalsAndPermissions.get(p);
			// Does work
			// return hivePrincipalsAndPermissions.get(p.getName());
		}
	}
	/**
	 * Simple principal used for this test.
	 * 
	 * @author warren
	 */
	private static final class ScanManPrincipal implements Principal
	{
		private static final long serialVersionUID = 1L;
		private String name;

		/**
		 * 
		 * Construct.
		 * 
		 * @param name
		 */
		public ScanManPrincipal(String name)
		{
			super();
			this.name = name;
			if (name == null)
				throw new IllegalArgumentException("Name must be specified");
		}

		/**
		 * @see org.apache.wicket.security.hive.authorization.Principal#getName()
		 */
		public String getName()
		{
			return name;
		}

		/**
		 * @see org.apache.wicket.security.hive.authorization.Principal#implies(Subject)
		 */
		public boolean implies(Subject subject)
		{
			// no inheritance structure in these principals.
			return false;
		}

		/**
		 * 
		 * @see java.lang.Object#toString()
		 */
		public String toString()
		{
			return getClass().getName() + ": " + getName();
		}

		/**
		 * generated hash based on class and name.
		 * 
		 * @see java.lang.Object#hashCode()
		 */
		public int hashCode()
		{
			final int PRIME = 31;
			int result = 1;
			result = PRIME * result + ((name == null) ? 0 : name.hashCode());
			result = PRIME * result + getClass().hashCode();
			return result;
		}

		/**
		 * 
		 * 
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		public boolean equals(Object obj)
		{
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final ScanManPrincipal other = (ScanManPrincipal)obj;
			if (name == null)
			{
				if (other.name != null)
					return false;
			}
			else if (!name.equals(other.name))
				return false;
			return true;
		}
	}
	/**
	 * Dummy permission used in this test.
	 * 
	 * @author marrink
	 */
	private static final class MyPermission extends ActionPermission
	{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Construct.
		 * 
		 * @param name
		 * @param actions
		 */
		public MyPermission(String name, WaspAction actions)
		{
			super(name, actions);
		}

	}
}
