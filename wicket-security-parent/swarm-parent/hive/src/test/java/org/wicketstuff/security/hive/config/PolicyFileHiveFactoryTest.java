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
package org.wicketstuff.security.hive.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.TestCase;

import org.wicketstuff.security.actions.Actions;
import org.wicketstuff.security.hive.Hive;
import org.wicketstuff.security.hive.authorization.EverybodyPrincipal;
import org.wicketstuff.security.hive.authorization.FaultyPermission;
import org.wicketstuff.security.hive.authorization.SimplePrincipal;
import org.wicketstuff.security.hive.authorization.TestPermission;

/**
 * @author marrink
 */
public class PolicyFileHiveFactoryTest extends TestCase
{
	private static final String KEY = "POLICY_TEST";

	/**
	 * @param name
	 */
	public PolicyFileHiveFactoryTest(String name)
	{
		super(name);
	}

	@Override
	protected void setUp()
	{
		new TestActionFactory(KEY);
	}

	@Override
	protected void tearDown()
	{
		Actions.unregisterActionFactory(KEY);
	}

	/**
	 * Test method for
	 * {@link org.wicketstuff.security.hive.config.PolicyFileHiveFactory#addPolicyFile(java.net.URL)}
	 * .
	 */
	public void testAddPolicyFile()
	{
		PolicyFileHiveFactory factory = new PolicyFileHiveFactory(Actions.getActionFactory(KEY));
		factory.addPolicyFile(getClass().getResource("test-policy.hive"));
		try
		{
			factory.addPolicyFile(new URL("http", "localhost", "foobar"));
		}
		catch (MalformedURLException e)
		{
			fail(e.getMessage());
		}
		factory.addPolicyFile(null);
	}

	/**
	 * Test method for
	 * {@link org.wicketstuff.security.hive.config.PolicyFileHiveFactory#createHive()}. using url's
	 */
	public void testCreateHive()
	{
		PolicyFileHiveFactory factory = new PolicyFileHiveFactory(Actions.getActionFactory(KEY));
		factory.addPolicyFile(getClass().getResource("test-policy.hive"));
		doCreateHive(factory);
	}

	/**
	 * Test method for
	 * {@link org.wicketstuff.security.hive.config.PolicyFileHiveFactory#createHive()}. using
	 * streams
	 */
	public void testCreateHive2()
	{
		PolicyFileHiveFactory factory = new PolicyFileHiveFactory(Actions.getActionFactory(KEY));
		InputStream stream;
		try
		{
			stream = getClass().getResource("test-policy.hive").openStream();
			factory.addStream(stream);
			doCreateHive(factory);
		}
		catch (IOException e)
		{
			fail(e.toString());
		}

		finally
		{
			// can not test if stream is actually closed other then trying to
			// read from it
		}
	}

	/**
	 * Test method for
	 * {@link org.wicketstuff.security.hive.config.PolicyFileHiveFactory#createHive()}. using
	 * readers
	 */
	public void testCreateHive3()
	{
		PolicyFileHiveFactory factory = new PolicyFileHiveFactory(Actions.getActionFactory(KEY));
		InputStream stream;
		try
		{
			stream = getClass().getResource("test-policy.hive").openStream();
			factory.addReader(new InputStreamReader(stream));
			doCreateHive(factory);
		}
		catch (IOException e)
		{
			fail(e.toString());
		}

		finally
		{
			// can not test if stream is actually closed other then trying to
			// read from it
		}
	}

	private void doCreateHive(PolicyFileHiveFactory factory)
	{
		// setup aliases
		factory.setAlias("perm", "org.wicketstuff.security.hive.authorization.TestPermission");
		factory.setAlias("nine", "9");
		factory.setAlias("test", "test");
		factory.setAlias("auth", "org.wicketstuff.security.hive.authorization");
		// based on policy content we can expect the following
		// principals/permissions
		Hive hive = factory.createHive();
		assertTrue(hive.containsPrincipal(new EverybodyPrincipal()));
		assertTrue(hive.containsPrincipal(new SimplePrincipal("test1")));
		assertTrue(hive.containsPrincipal(new SimplePrincipal("test2")));
		assertTrue(hive.containsPrincipal(new SimplePrincipal("test6")));
		assertTrue(hive.containsPermission(new TestPermission("A", "inherit, render")));
		assertTrue(hive.containsPermission(new TestPermission("A", "enable")));
		assertTrue(hive.containsPermission(new TestPermission("1.A", "inherit, render")));
		assertTrue(hive.containsPermission(new TestPermission("1.A", "enable")));
		assertFalse(hive.containsPermission(new TestPermission("2.A", "inherit, render")));
		assertFalse(hive.containsPermission(new TestPermission("2.A", "enable")));
		assertTrue(hive.containsPermission(new TestPermission("2.B", "inherit, render, enable")));
		assertTrue(hive.containsPermission(new TestPermission("2.B", "enable")));
		assertTrue(hive.containsPermission(new TestPermission("2.C", "render, enable")));
		assertTrue(hive.containsPermission(new TestPermission("2.C.1", "enable")));
		assertTrue(hive.containsPermission(new TestPermission("7.A", "inherit, render")));
		assertTrue(hive.containsPermission(new TestPermission("7.A", "enable")));
		assertTrue(hive.containsPermission(new TestPermission("7.B", "inherit, render, enable")));
		assertTrue(hive.containsPermission(new TestPermission("7.B", "enable")));
		assertTrue(hive.containsPermission(new TestPermission("7.C", "render, enable")));
		assertTrue(hive.containsPermission(new TestPermission("7.C.1", "enable")));
		assertFalse(hive.containsPermission(new TestPermission("6.A", "inherit, render")));
		assertFalse(hive.containsPermission(new TestPermission("6.A", "enable")));
		assertFalse(hive.containsPermission(new TestPermission("6.B", "inherit, render, enable")));
		assertFalse(hive.containsPermission(new TestPermission("6.B", "enable")));
		assertFalse(hive.containsPermission(new TestPermission("6.C", "render, enable")));
		assertFalse(hive.containsPermission(new TestPermission("6.C.1", "enable")));
		assertTrue(hive.containsPrincipal(new SimplePrincipal("test8")));
		assertTrue(hive.containsPermission(new TestPermission("8.A")));
		assertTrue(hive.containsPermission(new TestPermission("8.B")));
		assertFalse(hive.containsPermission(new FaultyPermission()));
		assertTrue(hive.containsPrincipal(new SimplePrincipal("test9")));
		assertTrue(hive.containsPermission(new TestPermission("9.A")));
		assertTrue(hive.containsPermission(new TestPermission("9.B", "test")));
		assertTrue(hive.containsPrincipal(new SimplePrincipal("test9B")));
		assertTrue(hive.containsPermission(new TestPermission(
			"test.ContainerPage2:test.ContainerPage2$SecureMarkupContainer", "inherit, render")));
		assertTrue(hive.containsPermission(new TestPermission(
			"test2.ContainerPage2:test.ContainerPage2$SecureMarkupContainer", "inherit, render")));
		assertTrue(hive.containsPrincipal(new SimplePrincipal("test10")));
		assertTrue(hive.containsPermission(new TestPermission("10.B")));
	}

	/**
	 * Test if the regex used in the factory is OK.
	 */
	public void testRegExPrincipalPattern()
	{
		Pattern principalPattern = null;
		try
		{
			Field field = PolicyFileHiveFactory.class.getDeclaredField("principalPattern");
			field.setAccessible(true);
			principalPattern = (Pattern)field.get(null);
		}
		catch (IllegalArgumentException e)
		{
			fail(e.getMessage());
		}
		catch (SecurityException e)
		{
			fail(e.getMessage());
		}
		catch (IllegalAccessException e)
		{
			fail(e.getMessage());
		}
		catch (NoSuchFieldException e)
		{
			fail(e.getMessage());
		}
		assertNotNull(principalPattern);
		assertFalse(principalPattern.matcher("").matches());
		assertTrue(principalPattern.matcher("grant").matches());
		assertTrue(principalPattern.matcher(
			"grant principal org.apache.wicket.TestPrincipal \"render\"").matches());
		assertFalse(principalPattern.matcher("grant foo").matches());
		assertFalse(principalPattern.matcher(
			"grant principal org.apache.wicket.TestPrincipal \"render").matches());
		assertFalse(principalPattern.matcher(
			"grant principal \"org.apache.wicket.TestPrincipal\" \"render\"").matches());
		assertTrue(principalPattern.matcher(
			"grant principal org.apache. wicket.TestPrincipal \"render\"").matches());
		assertFalse(principalPattern.matcher(
			"grant principal org.apache.wicket\".TestPrincipal \"render\"").matches());
		assertFalse(principalPattern.matcher(
			"grant principal org.apache.wicket.TestPrincipal \"render\" \"enable\"").matches());
		assertTrue(principalPattern.matcher(
			"grant principal org.apache.wicket.TestPrincipal \"some 'wicket' actions\"").matches());
		assertFalse(principalPattern.matcher("grant principal \"org.apache.wicket.TestPrincipal\"")
			.matches());
		assertFalse(principalPattern.matcher(
			"grant principal org.apache.wicket.TestPrincipal render").matches());
	}

	/**
	 * Test if the regex used in the factory is OK.
	 */
	public void testRegExPermissionPattern()
	{
		Pattern permissionPattern = null;
		try
		{
			Field field = PolicyFileHiveFactory.class.getDeclaredField("permissionPattern");
			field.setAccessible(true);
			permissionPattern = (Pattern)field.get(null);
			assertTrue(permissionPattern.matcher(
				"permission org.apache.wicket.TestPrincipal \"test\", \"render\";").matches());
			assertTrue(permissionPattern.matcher(
				"permission org.apache.wicket.TestPrincipal \"test\";").matches());
			assertTrue(permissionPattern.matcher(
				"permission org.apache.wicket.TestPrincipal \"test 123\", \"render\";").matches());
			assertTrue(permissionPattern.matcher(
				"permission org.apache.wicket.TestPrincipal \"test\", \"render 123\";").matches());
			assertTrue(permissionPattern.matcher(
				"permission org.apache.wicket.TestPrincipal \"test\", \"render 'wicket'\";")
				.matches());
			assertTrue(permissionPattern.matcher(
				"permission org.apache. wicket.TestPrincipal \"test\", \"render 'wicket'\";")
				.matches());
			assertTrue(permissionPattern.matcher(
				"permission org.apache.wicket.TestPrincipal \"test 'wicket'\", \"render\";")
				.matches());
			assertFalse(permissionPattern.matcher("permission org.apache.wicket.TestPrincipal;")
				.matches());
			assertFalse(permissionPattern.matcher("permission ").matches());
			assertFalse(permissionPattern.matcher(
				" org.apache.wicket.TestPrincipal \"test\", \"render\";").matches());
			assertFalse(permissionPattern.matcher(
				"permission org.apache.wicket.TestPrincipal \"test\" \"test\";").matches());
			assertFalse(permissionPattern.matcher(
				"permission org.apache.wicket.TestPrincipal \"test\", ;").matches());
			assertFalse(permissionPattern.matcher(
				"permission org.apache.wicket.TestPrincipal \"test\", \"render\"").matches());
			assertFalse(permissionPattern.matcher(
				"permission ${ComponentPermission} ${ml}, \"inherit, render\";").matches());
			assertTrue(permissionPattern.matcher(
				"permission ${ComponentPermission} \"${ml}\", \"inherit, render\";").matches());
			assertTrue(permissionPattern.matcher(
				"permission ${ComponentPermission} ${ml} \"${ml}\", \"inherit, render\";")
				.matches());
			assertFalse(permissionPattern.matcher(
				"permission ${ComponentPermission} ${ml}, \"whatever\", \"inherit, render\";")
				.matches());
			// technically spaces and some other characters are not allowed in
			// classnames either but they don't cause any problems yet "," did
		}
		catch (IllegalArgumentException e)
		{
			fail(e.getMessage());
		}
		catch (SecurityException e)
		{
			fail(e.getMessage());
		}
		catch (IllegalAccessException e)
		{
			fail(e.getMessage());
		}
		catch (NoSuchFieldException e)
		{
			fail(e.getMessage());
		}
		assertNotNull(permissionPattern);
	}

	/**
	 * Test if the regex used in the factory is OK.
	 */
	public void testRegExAliasPattern()
	{
		Pattern aliasPattern = null;
		try
		{
			Field field = PolicyFileHiveFactory.class.getDeclaredField("aliasPattern");
			field.setAccessible(true);
			aliasPattern = (Pattern)field.get(null);
			assertFalse(aliasPattern.matcher("no alias used whatsoever").find());
			Matcher m = aliasPattern.matcher("foo${bar}");
			assertTrue(m.find());
			assertEquals("${bar}", m.group());
			assertFalse(m.find());
			m = aliasPattern.matcher("${foo}bar");
			assertTrue(m.find());
			assertEquals("${foo}", m.group());
			assertFalse(m.find());
			m = aliasPattern.matcher("fo${o}bar");
			assertTrue(m.find());
			assertEquals("${o}", m.group());
			assertFalse(m.find());
			m = aliasPattern.matcher("${foo}${bar}");
			assertTrue(m.find());
			assertEquals("${foo}", m.group());
			assertTrue(m.find());
			assertEquals("${bar}", m.group());
			assertFalse(m.find());
			m = aliasPattern.matcher("${foo bar}foo${bar}");
			assertTrue(m.find());
			assertEquals("${foo bar}", m.group());
			assertTrue(m.find());
			assertEquals("${bar}", m.group());
			assertFalse(m.find());
			assertFalse(aliasPattern.matcher("${$foo}").find());
			assertFalse(aliasPattern.matcher("${}").find());
			assertFalse(aliasPattern.matcher("${foo\"bar\"}").find());
			// regex alone is not enough to detect nested alias
			// assertFalse(aliasPattern.matcher("${${foo}").find());
			// assertFalse(aliasPattern.matcher("${${foo}bar}").find());
		}
		catch (IllegalArgumentException e)
		{
			fail(e.getMessage());
		}
		catch (SecurityException e)
		{
			fail(e.getMessage());
		}
		catch (IllegalAccessException e)
		{
			fail(e.getMessage());
		}
		catch (NoSuchFieldException e)
		{
			fail(e.getMessage());
		}
		assertNotNull(aliasPattern);
	}

	/**
	 * test handling of nested aliases
	 */
	public void testResolveAliases()
	{
		try
		{
			Method method = PolicyFileHiveFactory.class.getDeclaredMethod("resolveAliases",
				new Class[] { String.class });
			method.setAccessible(true);
			PolicyFileHiveFactory factory = new PolicyFileHiveFactory(Actions.getActionFactory(KEY));
			factory.setAlias("foo", "foo");
			factory.setAlias("foobar", "foobar");
			String result = (String)method.invoke(factory, new Object[] { "${${foo}bar}" });
			fail("Unable to detect nested aliases: " + result);

		}
		catch (IllegalArgumentException e)
		{
			fail(e.getMessage());
		}
		catch (SecurityException e)
		{
			fail(e.getMessage());
		}
		catch (IllegalAccessException e)
		{
			fail(e.getMessage());
		}
		catch (NoSuchMethodException e)
		{
			fail(e.getMessage());
		}
		catch (InvocationTargetException e)
		{
			if (e.getCause() instanceof IllegalStateException)
			{
				assertTrue(e.getCause().getMessage().toLowerCase().indexOf("nesting") >= 0);
			}
			else
				fail(e.getMessage());
		}
	}

	/**
	 * test handling of nested aliases
	 */
	public void testResolveAliases2()
	{
		try
		{
			Method method = PolicyFileHiveFactory.class.getDeclaredMethod("resolveAliases",
				new Class[] { String.class });
			method.setAccessible(true);
			PolicyFileHiveFactory factory = new PolicyFileHiveFactory(Actions.getActionFactory(KEY));
			factory.setAlias("foo", "foo");
			factory.setAlias("foobar", "foobar");
			String result = (String)method.invoke(factory, new Object[] { "${${foo}" });
			fail("Unable to detect nested aliases: " + result);

		}
		catch (IllegalArgumentException e)
		{
			fail(e.getMessage());
		}
		catch (SecurityException e)
		{
			fail(e.getMessage());
		}
		catch (IllegalAccessException e)
		{
			fail(e.getMessage());
		}
		catch (NoSuchMethodException e)
		{
			fail(e.getMessage());
		}
		catch (InvocationTargetException e)
		{
			if (e.getCause() instanceof IllegalStateException)
			{
				assertTrue(e.getCause().getMessage().toLowerCase().indexOf("nesting") >= 0);
			}
			else
				fail(e.getMessage());
		}
	}

	/**
	 * Test handling null urls, readers and streams.
	 */
	public void testAddNull()
	{
		PolicyFileHiveFactory factory = new PolicyFileHiveFactory(Actions.getActionFactory(KEY));
		assertFalse(factory.addPolicyFile(null));
		assertFalse(factory.addReader(null));
		assertFalse(factory.addStream(null));

	}
}
