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
package org.wicketstuff.console.examples;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.mock.MockApplication;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.wicketstuff.console.examples.clojure.ClojureEngineTestPage;
import org.wicketstuff.console.examples.clojure.ClojureEngineWindowTestPage;
import org.wicketstuff.console.examples.clojure.ClojureEngineWithTemplatesTestPage;
import org.wicketstuff.console.examples.clojure.ClojureEngineWithTemplatesWindowTestPage;
import org.wicketstuff.console.examples.groovy.GroovyEngineTestPage;
import org.wicketstuff.console.examples.groovy.GroovyEngineWindowTestPage;
import org.wicketstuff.console.examples.groovy.GroovyEngineWithTemplatesTestPage;
import org.wicketstuff.console.examples.groovy.GroovyEngineWithTemplatesWindowTestPage;
import org.wicketstuff.console.examples.jython.JythonEngineTestPage;
import org.wicketstuff.console.examples.jython.JythonEngineWindowTestPage;
import org.wicketstuff.console.examples.jython.JythonEngineWithTemplatesTestPage;
import org.wicketstuff.console.examples.jython.JythonEngineWithTemplatesWindowTestPage;
import org.wicketstuff.console.examples.scala.ScalaEngineTestPage;
import org.wicketstuff.console.examples.scala.ScalaEngineWindowTestPage;
import org.wicketstuff.console.examples.scala.ScalaEngineWithTemplatesTestPage;
import org.wicketstuff.console.examples.scala.ScalaEngineWithTemplatesWindowTestPage;
import org.wicketstuff.console.templates.PackagedScriptTemplates;

@ContextConfiguration(locations = { "classpath:root-context.xml" })
public class TestPagesTest extends AbstractJUnit4SpringContextTests
{

	private WicketTester tester;
	private Class<? extends WebPage> clazz;

	@Before
	public void setUp()
	{
		tester = new WicketTester(new MockApplication());
	}

	@Test
	public void test_rendersSuccessfully_ScalaEngineTestPage()
	{
		clazz = ScalaEngineTestPage.class;
		tester.startPage(clazz);
		tester.assertRenderedPage(clazz);
	}

	@Test
	public void test_rendersSuccessfully_ScalaEngineWithTemplatesTestPage()
	{
		clazz = ScalaEngineWithTemplatesTestPage.class;
		tester.startPage(clazz);
		tester.assertRenderedPage(clazz);
	}

	@Test
	public void test_rendersSuccessfully_ScalaEngineWindowTestPage()
	{
		clazz = ScalaEngineWindowTestPage.class;
		tester.startPage(clazz);
		tester.assertRenderedPage(clazz);

		tester.clickLink("link");
		tester.assertComponentOnAjaxResponse("window");

	}

	@Test
	public void test_rendersSuccessfully_ScalaEngineWithTemplatesWindowTestPage()
	{
		clazz = ScalaEngineWithTemplatesWindowTestPage.class;
		tester.startPage(clazz);
		tester.assertRenderedPage(clazz);

		tester.clickLink("link");
		tester.assertComponentOnAjaxResponse("window");
	}

	@Test
	public void test_rendersSuccessfully_ClojureEngineTestPage()
	{
		clazz = ClojureEngineTestPage.class;
		tester.startPage(clazz);
		tester.assertRenderedPage(clazz);
	}

	@Test
	public void test_rendersSuccessfully_ClojureEngineWindowTestPage()
	{
		clazz = ClojureEngineWindowTestPage.class;
		tester.startPage(clazz);
		tester.assertRenderedPage(clazz);

		tester.clickLink("link");
		tester.assertComponentOnAjaxResponse("window");
	}


	@Test
	public void test_rendersSuccessfully_ClojureEngineWithTemplatesTestPage()
	{
		clazz = ClojureEngineWithTemplatesTestPage.class;
		tester.startPage(clazz);
		tester.assertRenderedPage(clazz);
	}

	@Test
	public void test_rendersSuccessfully_ClojureEngineWithTemplatesWindowTestPage()
	{
		clazz = ClojureEngineWithTemplatesWindowTestPage.class;
		final ClojureEngineWithTemplatesWindowTestPage page = (ClojureEngineWithTemplatesWindowTestPage)tester.startPage(clazz);
		tester.assertRenderedPage(clazz);

		tester.clickLink(page.getOpenLink());
		tester.assertComponentOnAjaxResponse("window");
	}

	@Test
	public void test_rendersSuccessfully_GroovyEngineTestPage()
	{
		clazz = GroovyEngineTestPage.class;
		tester.startPage(clazz);
		tester.assertRenderedPage(clazz);
	}

	@Test
	public void test_rendersSuccessfully_GroovyEngineWindowTestPage()
	{
		clazz = GroovyEngineWindowTestPage.class;
		tester.startPage(clazz);
		tester.assertRenderedPage(clazz);

		tester.clickLink("link");
		tester.assertComponentOnAjaxResponse("window");
	}

	@Test
	public void test_rendersSuccessfully_GroovyEngineWithTemplatesTestPage()
	{
		final GroovyEngineWithTemplatesTestPage page = new GroovyEngineWithTemplatesTestPage(
			new PackagedScriptTemplates());
		tester.startPage(page);
		tester.assertRenderedPage(GroovyEngineWithTemplatesTestPage.class);
	}

	@Test
	public void test_rendersSuccessfully_GroovyEngineWithTemplatesWindowTestPage()
	{
		clazz = GroovyEngineWithTemplatesWindowTestPage.class;
		final GroovyEngineWithTemplatesWindowTestPage page = (GroovyEngineWithTemplatesWindowTestPage)tester.startPage(clazz);
		tester.assertRenderedPage(clazz);

		tester.clickLink(page.getOpenLink());
		tester.assertComponentOnAjaxResponse("window");
	}


	@Test
	public void test_rendersSuccessfully_JythonEngineTestPage()
	{
		clazz = JythonEngineTestPage.class;
		tester.startPage(clazz);
		tester.assertRenderedPage(clazz);
	}

	@Test
	public void test_rendersSuccessfully_JythonEngineWindowTestPage()
	{
		clazz = JythonEngineWindowTestPage.class;
		tester.startPage(clazz);
		tester.assertRenderedPage(clazz);

		tester.clickLink("link");
		tester.assertComponentOnAjaxResponse("window");
	}

	@Test
	public void test_rendersSuccessfully_JythonEngineWithTemplatesTestPage()
	{
		clazz = JythonEngineWithTemplatesTestPage.class;
		tester.startPage(clazz);
		tester.assertRenderedPage(clazz);
	}

	@Test
	public void test_rendersSuccessfully_JythonEngineWithTemplatesWindowTestPage()
	{
		clazz = JythonEngineWithTemplatesWindowTestPage.class;
		final JythonEngineWithTemplatesWindowTestPage page = (JythonEngineWithTemplatesWindowTestPage)tester.startPage(clazz);
		tester.assertRenderedPage(clazz);

		tester.clickLink(page.getOpenLink());
		tester.assertComponentOnAjaxResponse("window");
	}

}
