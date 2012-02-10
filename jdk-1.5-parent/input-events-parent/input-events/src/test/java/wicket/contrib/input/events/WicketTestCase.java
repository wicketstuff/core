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
package wicket.contrib.input.events;

import junit.framework.TestCase;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.AbstractAjaxBehavior;
import org.apache.wicket.util.tester.WicketTester;

/**
 * Base class for tests which require comparing wicket response with a file.
 * <p>
 * To create/replace the expected result file with the new content, define the system property like
 * -Dwicket.replace.expected.results=true
 * 
 */
public abstract class WicketTestCase extends TestCase
{
	/** */
	public WicketTester tester;

	/**
	 * Constructor
	 */
	public WicketTestCase()
	{
	}

	/**
	 * Create the test.
	 * 
	 * @param name
	 *            The test name
	 */
	public WicketTestCase(String name)
	{
		super(name);
	}

	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception
	{
		tester = new WicketTester();
	}

	/**
	 * @see junit.framework.TestCase#tearDown()
	 */
	@Override
	protected void tearDown() throws Exception
	{
		tester.destroy();
	}

	/**
	 * Use <code>-Dwicket.replace.expected.results=true</code> to automatically replace the expected
	 * output file.
	 * 
	 * @param pageClass
	 * @param filename
	 * @throws Exception
	 */
	protected void executeTest(final Class<? extends Page> pageClass, final String filename)
		throws Exception
	{
		System.out.println("=== " + pageClass.getName() + " ===");
		tester.startPage(pageClass);
		tester.assertRenderedPage(pageClass);
		tester.dumpPage();
		tester.assertResultPage(this.getClass(), filename);
	}

	/**
	 * 
	 * @param clazz
	 * @param component
	 * @param filename
	 * @throws Exception
	 */
	protected void executedListener(final Class<?> clazz, final Component component,
		final String filename) throws Exception
	{
		assertNotNull(component);

		System.out.println("=== " + clazz.getName() + " : " + component.getPageRelativePath() +
			" ===");

		tester.executeListener(component);
		tester.assertResultPage(clazz, filename);
	}

	/**
	 * 
	 * @param clazz
	 * @param behavior
	 * @param filename
	 * @throws Exception
	 */
	protected void executedBehavior(final Class<?> clazz, final AbstractAjaxBehavior behavior,
		final String filename) throws Exception
	{
		assertNotNull(behavior);

		System.out.println("=== " + clazz.getName() + " : " + behavior.toString() + " ===");

		tester.executeBehavior(behavior);
		tester.assertResultPage(clazz, filename);
	}
}
