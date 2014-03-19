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
package org.wicketstuff.console;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;
import org.wicketstuff.console.clojure.ClojureScriptEnginePanel;
import org.wicketstuff.console.clojure.ClojureScriptEngineWindow;
import org.wicketstuff.console.clojure.ClojureScriptEngineWithTemplatesPanel;
import org.wicketstuff.console.clojure.ClojureScriptEngineWithTemplatesWindow;
import org.wicketstuff.console.groovy.GroovyScriptEnginePanel;
import org.wicketstuff.console.groovy.GroovyScriptEngineWindow;
import org.wicketstuff.console.groovy.GroovyScriptEngineWithTemplatesPanel;
import org.wicketstuff.console.groovy.GroovyScriptEngineWithTemplatesWindow;
import org.wicketstuff.console.jython.JythonScriptEnginePanel;
import org.wicketstuff.console.jython.JythonScriptEngineWindow;
import org.wicketstuff.console.jython.JythonScriptEngineWithTemplatesPanel;
import org.wicketstuff.console.jython.JythonScriptEngineWithTemplatesWindow;
import org.wicketstuff.console.scala.ScalaScriptEnginePanel;
import org.wicketstuff.console.scala.ScalaScriptEngineWindow;
import org.wicketstuff.console.scala.ScalaScriptEngineWithTemplatesPanel;
import org.wicketstuff.console.scala.ScalaScriptEngineWithTemplatesWindow;
import org.wicketstuff.console.templates.PackagedScriptTemplates;

public class ComponentsRenderTest
{

	private WicketTester tester;

	@Before
	public void setUp()
	{
		tester = new WicketTester();
	}

	@Test
	public void test_rendersSuccessfully_Groovy()
	{
		tester.startComponent(new GroovyScriptEnginePanel("foo"));
		tester.startComponent(new GroovyScriptEngineWithTemplatesPanel("foo",
			new PackagedScriptTemplates()));
		tester.startComponent(new GroovyScriptEngineWindow("foo"));
		tester.startComponent(new GroovyScriptEngineWithTemplatesWindow("foo", null,
			new PackagedScriptTemplates()));

	}

	@Test
	public void test_rendersSuccessfully_Clojure()
	{

		tester.startComponent(new ClojureScriptEnginePanel("foo"));
		tester.startComponent(new ClojureScriptEngineWindow("foo"));
		tester.startComponent(new ClojureScriptEngineWithTemplatesPanel("foo",
			new PackagedScriptTemplates()));
		tester.startComponent(new ClojureScriptEngineWithTemplatesWindow("foo", null,
			new PackagedScriptTemplates()));
	}

	@Test
	public void test_rendersSuccessfully_Scala()
	{

		tester.startComponent(new ScalaScriptEnginePanel("foo"));
		tester.startComponent(new ScalaScriptEngineWindow("foo"));
		tester.startComponent(new ScalaScriptEngineWithTemplatesPanel("foo",
			new PackagedScriptTemplates()));
		tester.startComponent(new ScalaScriptEngineWithTemplatesWindow("foo", null,
			new PackagedScriptTemplates()));
	}

	@Test
	public void test_rendersSuccessfully_Jython()
	{

		tester.startComponent(new JythonScriptEnginePanel("foo"));
		tester.startComponent(new JythonScriptEngineWindow("foo"));
		tester.startComponent(new JythonScriptEngineWithTemplatesPanel("foo",
			new PackagedScriptTemplates()));
		tester.startComponent(new JythonScriptEngineWithTemplatesWindow("foo", null,
			new PackagedScriptTemplates()));
	}

}
