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
package org.apache.wicket.security;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.security.actions.ActionFactory;
import org.apache.wicket.security.hive.BasicHive;
import org.apache.wicket.security.hive.Hive;
import org.apache.wicket.security.hive.HiveMind;
import org.apache.wicket.security.hive.SimpleCachingHive;
import org.apache.wicket.security.hive.authorization.Principal;
import org.apache.wicket.security.hive.authorization.SimplePrincipal;
import org.apache.wicket.security.hive.authorization.permissions.ComponentPermission;
import org.apache.wicket.security.hive.config.HiveFactory;
import org.apache.wicket.security.pages.MockLoginPage;
import org.apache.wicket.security.pages.SpeedPage;
import org.apache.wicket.security.swarm.SwarmWebApplication;
import org.apache.wicket.security.swarm.actions.SwarmAction;
import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author marrink
 */
public class SpeedTest extends TestCase
{
	private static final Logger log = LoggerFactory.getLogger(SpeedTest.class);

	/**
	 * Nr. of rows and columns in our repeater.
	 */
	public static final int ROWS = 50;

	public static final int COLS = 50;

	private boolean useCache = false;

	private Map<Key, Result> results = new HashMap<Key, Result>();

	/**
	 * number of denied permissions. 1=0% denied, 2 =50% denied, 3 =66% denied, etc
	 */
	private int denialFactor = 1;

	/**
	 * The swarm application used for the test.
	 */
	protected WebApplication application;

	/**
	 * Handle to the mock environment.
	 */
	protected WicketTester mock;

	private void mySetUp()
	{
		mock = new WicketTester(application = new SwarmWebApplication()
		{

			@Override
			protected Object getHiveKey()
			{
				// if we were using servlet-api 2.5 we could get the contextpath
				// from the servletcontext
				return "test";
			}

			@Override
			protected void setUpHive()
			{
				HiveFactory factory = new DummyFactory(useCache, denialFactor, getActionFactory());
				HiveMind.registerHive(getHiveKey(), factory);
			}

			@Override
			public Class< ? extends Page> getHomePage()
			{
				return SpeedPage.class;
			}

			public Class< ? extends Page> getLoginPage()
			{
				return MockLoginPage.class;
			}
		}, "src/test/java/" + getClass().getPackage().getName().replace('.', '/'));
	}

	private void myTearDown()
	{
		mock.setupRequestAndResponse();
		mock.getWicketSession().invalidate();
		mock.processRequestCycle();
		mock.destroy();
		mock = null;
		application = null;
		HiveMind.unregisterHive("test");
	}

	/**
	 * Performance test with caching enabled, 50% denial rate.
	 */
	public void cachedpartialDenied()
	{
		denialFactor = 2;
		useCache = true;
		doTestRun();
	}

	/**
	 * Performance test, no caching, 50% denial rate.
	 */
	public void noCachePartialDenied()
	{
		useCache = false;
		denialFactor = 2;
		doTestRun();
	}

	/**
	 * Same performance test but now with caching enabled.
	 */
	public void cachedAllAllowed()
	{
		useCache = true;
		denialFactor = 1;
		doTestRun();
	}

	/**
	 * Test performance diff between secure page and "unsecure" page with lots of
	 * components.
	 */
	public void noCacheAllAllowed()
	{
		useCache = false;
		denialFactor = 1;
		doTestRun();
	}

	private void doTestRun()
	{
		mySetUp();
		mock.startPage(SpeedPage.class);
		mock.assertRenderedPage(MockLoginPage.class);
		FormTester form = mock.newFormTester("form");
		form.setValue("username", "speed");
		form.submit();
		mock.assertRenderedPage(SpeedPage.class);
		mock.assertVisible("secure");
		// weird no html????
		// TagTester tag = mock.getTagById("secure");
		// assertNotNull(tag);
		// assertEquals("not secure", tag.getValue());

		int warmup = 100;
		// warmup
		log.info("warmup");
		for (int i = 0; i < warmup; i++)
		{
			mock.startPage(mock.getLastRenderedPage());
			mock.assertRenderedPage(SpeedPage.class);
		}
		Key unsecured = new Key(denialFactor, useCache, false);
		int count = 100;
		log.info("measurement");
		long start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			mock.startPage(mock.getLastRenderedPage());
		long end = System.currentTimeMillis();
		results.put(unsecured, new Result(start, end, count, ROWS * COLS));
		log.info("security now enabled");
		// enable security checks
		((SpeedPage) mock.getLastRenderedPage()).setSecured(true);
		// warmup
		log.info("warmup");
		for (int i = 0; i < warmup; i++)
		{
			mock.startPage(mock.getLastRenderedPage());
			mock.assertRenderedPage(SpeedPage.class);
		}
		Key secured = new Key(denialFactor, useCache, true);
		log.info("measurement");
		long start2 = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			mock.startPage(mock.getLastRenderedPage());
		long end2 = System.currentTimeMillis();
		results.put(secured, new Result(start2, end2, count, ROWS * COLS));
		myTearDown();
	}

	/**
	 * Compare performance test results between caching and non caching.
	 */
	public void testPerformance()
	{
		cachedAllAllowed();
		noCacheAllAllowed();
		noCachePartialDenied();
		cachedpartialDenied();
		assertEquals(8, results.size());
		Result noCache1 = results.get(printResults(new Key(1, false, false)));
		Result noCache2 = results.get(printResults(new Key(1, false, true)));
		assertTrue("secure components are faster than normal components",
			(noCache1.end - noCache1.start) / noCache1.runs < (noCache2.end - noCache2.start)
				/ noCache2.runs);
		long diffNoCache =
			(((noCache2.end - noCache2.start) / noCache2.runs) - ((noCache1.end - noCache1.start) / noCache1.runs));
		Result cache1 = results.get(printResults(new Key(1, true, false)));
		Result cache2 = results.get(printResults(new Key(1, true, true)));
		assertTrue((cache1.end - cache1.start) / cache1.runs < (cache2.end - cache2.start)
			/ cache2.runs);
		long diffCache =
			(((cache2.end - cache2.start) / cache2.runs) - ((cache1.end - cache1.start) / cache1.runs));
		assertTrue("caching is actually bad for performance", diffCache < diffNoCache);

		// 50 % permissions denied
		noCache1 = results.get(printResults(new Key(2, false, false)));
		noCache2 = results.get(printResults(new Key(2, false, true)));
		assertTrue((noCache1.end - noCache1.start) / noCache1.runs < (noCache2.end - noCache2.start)
			/ noCache2.runs);
		diffNoCache =
			(((noCache2.end - noCache2.start) / noCache2.runs) - ((noCache1.end - noCache1.start) / noCache1.runs));
		cache1 = results.get(printResults(new Key(2, true, false)));
		cache2 = results.get(printResults(new Key(2, true, true)));
		assertTrue((cache1.end - cache1.start) / cache1.runs < (cache2.end - cache2.start)
			/ cache2.runs);
		diffCache =
			(((cache2.end - cache2.start) / cache2.runs) - ((cache1.end - cache1.start) / cache1.runs));
		assertTrue("caching is actually bad for performance", diffCache < diffNoCache);
	}

	private Key printResults(Key key)
	{
		Result result = results.get(key);
		log.info("Test results with cache enabled = " + key.caching);
		log.info("Testing " + result.components + " components");
		if (key.secured)
			log.info(result.components / key.denialRate + " component permissions granted");
		log.info((key.secured ? "secured" : "unsecured") + " page took "
			+ (result.end - result.start) + " ms total, "
			+ ((result.end - result.start) / result.runs) + " ms on average over " + result.runs
			+ " requests");
		if (key.secured)
		{
			Result result2 = results.get(new Key(key.denialRate, key.caching, false));
			long diff =
				(((result.end - result.start) / result.runs) - ((result2.end - result2.start) / result2.runs));
			log.info("each component security check took " + ((double) diff / result.components)
				+ " ms");
		}
		return key;

		// 2500 components, diff per request = 27 ms, time per component =
		// 0.0108 ms
		// no caching, no permission / principal inheritance, all components
		// allowed

		// 2500 components, diff per request = 917 ms, time per component =
		// 0.3668 ms
		// no caching, no permission / principal inheritance, 1250 components
		// allowed, 1250 components denied

		// caching should dramatically improve situations where no permission is
		// found

		// 2500 components, diff per request = 23 ms, time per component =
		// 0.0092 ms
		// with caching, no permission / principal inheritance, all components
		// allowed

		// 2500 components, diff per request = 18 ms, time per component =
		// 0.0072 ms
		// with caching, no permission / principal inheritance, 1250 components
		// allowed, 1250 components denied
	}

	private static final class DummyFactory implements HiveFactory
	{

		private final boolean cache;

		private final int denialFactor;

		private ActionFactory actionFactory;

		/**
		 * Construct.
		 * 
		 * @param cache
		 *            use caching or not
		 * @param deny
		 *            factor for % of permission denied
		 * @param actionFactory
		 *            factory to create the actions
		 */
		public DummyFactory(boolean cache, int deny, ActionFactory actionFactory)
		{
			super();
			this.cache = cache;
			this.denialFactor = deny;
			this.actionFactory = actionFactory;
		}

		/**
		 * 
		 * @see org.apache.wicket.security.hive.config.HiveFactory#createHive()
		 */
		public Hive createHive()
		{
			BasicHive hive;
			if (cache)
				hive = new SimpleCachingHive();
			else
				hive = new BasicHive();
			Principal principal = new SimplePrincipal("speed");
			SwarmAction action = (SwarmAction) actionFactory.getAction("access, render");
			hive.addPermission(principal, new ComponentPermission(
				"org.apache.wicket.security.pages.SpeedPage", action));
			for (int i = 0; i < ROWS; i++)
			{
				for (int j = 0; j < COLS / denialFactor; j++)
				{
					// not granting a permission for each component will add
					// linear time to check, the more permissions the more time
					// will be added
					hive.addPermission(principal, new ComponentPermission(
						"org.apache.wicket.security.pages.SpeedPage:rows:" + i + ":cols:" + j
							+ ":label", action));
				}
			}
			return hive;
		}

	}

	/**
	 * Key to store results in a hashMap.
	 * 
	 * @author marrink
	 */
	private static final class Key
	{
		public final int denialRate;

		public final boolean caching;

		public final boolean secured;

		/**
		 * Construct.
		 * 
		 * @param denialRate
		 * @param caching
		 * @param secured
		 */
		public Key(int denialRate, boolean caching, boolean secured)
		{
			super();
			this.denialRate = denialRate;
			this.caching = caching;
			this.secured = secured;
		}

		/**
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode()
		{
			final int prime = 31;
			int result = 1;
			result = prime * result + (caching ? 1231 : 1237);
			result = prime * result + denialRate;
			result = prime * result + (secured ? 1231 : 1237);
			return result;
		}

		/**
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj)
		{
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final Key other = (Key) obj;
			if (caching != other.caching)
				return false;
			if (denialRate != other.denialRate)
				return false;
			if (secured != other.secured)
				return false;
			return true;
		}
	}

	/**
	 * Helper class to store results.
	 * 
	 * @author marrink
	 */
	private static final class Result
	{
		public final long start;

		public final long end;

		public final int runs;

		public final long components;

		/**
		 * Construct.
		 * 
		 * @param start
		 * @param end
		 * @param runs
		 * @param components
		 */
		public Result(long start, long end, int runs, long components)
		{
			super();
			this.start = start;
			this.end = end;
			this.runs = runs;
			this.components = components;
		}

		/**
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode()
		{
			final int prime = 31;
			int result = 1;
			result = prime * result + (int) (components ^ (components >>> 32));
			result = prime * result + (int) (end ^ (end >>> 32));
			result = prime * result + runs;
			result = prime * result + (int) (start ^ (start >>> 32));
			return result;
		}

		/**
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj)
		{
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final Result other = (Result) obj;
			if (components != other.components)
				return false;
			if (end != other.end)
				return false;
			if (runs != other.runs)
				return false;
			if (start != other.start)
				return false;
			return true;
		}

	}
}
