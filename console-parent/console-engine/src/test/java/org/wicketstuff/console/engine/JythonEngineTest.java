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
package org.wicketstuff.console.engine;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class JythonEngineTest
{

	private static final String NEWLINE = System.getProperty("line.separator");

	private IScriptEngine engine;

	@Before
	public void setup()
	{
		engine = new JythonEngine();

		// First run somehow fails sometimes
		engine.execute("");
	}


	@Test
	public void test_simple_output_sysout() throws Exception
	{
		// Given
		final String script = "import java.lang.System\n" + "java.lang.System.out.print(\"4\") ";

		// When
		final IScriptExecutionResult result = engine.execute(script);

		// Then
		final String output = result.getOutput();
		final Throwable exception = result.getException();
		assertTrue(result.isSuccess());
		assertNull(exception);
		assertNull(result.getReturnValue());
		assertEquals("4", output);
	}

	@Test
	public void test_simple_print() throws Exception
	{
		// Given
		final String script = "import sys\n" + "x = 2 + 2\n" + "print x";

		// When
		final IScriptExecutionResult result = engine.execute(script);

		// Then
		final String output = result.getOutput();
		final Throwable exception = result.getException();
		assertTrue(result.isSuccess());
		assertNull(exception);
		assertNull(result.getReturnValue());
		assertEquals("4" + NEWLINE, output);
	}


	@Test
	public void test_empty_script() throws Exception
	{
		// Given
		final String script = "";

		// When
		final IScriptExecutionResult result = engine.execute(script);

		// Then
		final String output = result.getOutput();
		assertTrue(result.isSuccess());
		assertNull(result.getException());
		assertEquals("", output);
		assertNull(result.getReturnValue());
	}

	@Test
	public void test_simple_returnValue() throws Exception
	{
		// Given
		final String script = "result = 5";

		// When
		final IScriptExecutionResult result = engine.execute(script);

		// Then
		assertTrue(result.isSuccess());
		assertNull(result.getException());
		assertEquals("", result.getOutput());
		assertEquals("5", result.getReturnValue().toString());
	}

	@Test
	public void test_simple_loop() throws Exception
	{
		// Given
		final String script = "x = 0\n" + "for i in range(1,10):\n" + "  x = x + i\n"
			+ "result = x";

		// When
		final IScriptExecutionResult result = engine.execute(script);

		// Then
		assertTrue(result.isSuccess());
		assertNull(result.getException());
		assertEquals("", result.getOutput());
		assertEquals("45", result.getReturnValue().toString());
	}

	@Test
	public void test_simple_def() throws Exception
	{
		// Given
		final String script = "def fak(n):\n" + "    if n <= 1:\n" + "        return n\n"
			+ "    else:\n" + "        return n * fak(n-1)\n" + "\n" + "result = fak(5)";

		// When
		final IScriptExecutionResult result = engine.execute(script);

		// Then
		assertTrue(result.isSuccess());
		assertNull(result.getException());
		assertEquals("", result.getOutput());
		assertEquals("120", result.getReturnValue().toString());
	}

	@Test
	public void test_simple_class() throws Exception
	{
		// Given
		final String script = "class Foo (object):\n" + "    def __init__(self):\n"
			+ "        self.bar = 7\n" + "\n" + "    def foobar(self):\n"
			+ "        return self.bar\n" + "\n" + "f = Foo()\n" + "result = f.foobar()";

		// When
		final IScriptExecutionResult result = engine.execute(script);

		// Then
		assertTrue(result.isSuccess());
		assertNull(result.getException());
		assertEquals("", result.getOutput());
		assertEquals("7", result.getReturnValue().toString());
	}

	@Test
	public void test_simple_binding() throws Exception
	{
		// Given
		final String script = "result = x";
		final Map<String, Object> bindings = new HashMap<String, Object>();
		bindings.put("x", 5);

		// When
		final IScriptExecutionResult result = engine.execute(script, bindings);

		// Then
		assertEquals("5", result.getReturnValue().toString());

	}

	@Test
	public void test_execute_exception_01() throws Exception
	{
		// Given
		final String script = "xxx";

		// When
		final IScriptExecutionResult result = engine.execute(script);

		// Then
		assertFalse(result.isSuccess());
		final Throwable exception = result.getException();
		assertNotNull(exception);
		assertTrue(exception instanceof Exception);
		assertEquals("Traceback", result.getOutput().substring(0, "Traceback".length()));
		assertNull(result.getReturnValue());
	}

}
