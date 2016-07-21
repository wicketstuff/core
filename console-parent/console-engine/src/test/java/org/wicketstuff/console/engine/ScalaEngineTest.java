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

public class ScalaEngineTest
{
	private static final String NEWLINE = System.getProperty("line.separator");

	public static ScalaEngine engine = new ScalaEngine();


	@Before
	public void setup()
	{
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
	public void test_println() throws Exception
	{
		// Given
		final String script = "println(\"foo\")\nprintln(\"bar\")";

		// When
		final IScriptExecutionResult result = engine.execute(script);

		// Then
		assertTrue(result.isSuccess());
		assertNull(result.getException());
		assertNull(result.getReturnValue());
		final String output = result.getOutput();
		assertEquals("foo" + NEWLINE + "bar" + NEWLINE, output);
	}

	@Test
	public void test_print() throws Exception
	{
		// Given
		final String script = "print(\"foo\")";

		// When
		final IScriptExecutionResult result = engine.execute(script);

		// Then
		final String output = result.getOutput();
		assertEquals("foo", output);
	}

	@Test
	public void test_simple_output_sysout() throws Exception
	{
		// Given
		final String script = "System.out.print(\"5\")";

		// When
		final IScriptExecutionResult result = engine.execute(script);

		// Then
		assertTrue(result.isSuccess());
		assertNull(result.getException());
		assertNull(result.getReturnValue());
		final String output = result.getOutput();
		assertEquals("5", output);
	}

	@Test
	public void test_simple_output_sysout_2() throws Exception
	{
		// Given
		final String script = "System.out.print(\"6\")";

		// When
		final IScriptExecutionResult result = engine.execute(script);

		// Then
		assertTrue(result.isSuccess());
		assertNull(result.getException());
		assertNull(result.getReturnValue());
		final String output = result.getOutput();
		assertEquals("6", output);
	}

	@Test
	public void test_simple_script() throws Exception
	{

		// Given
		final String script = "val list = 1 :: 2 :: 3 :: List()\n"
			+ "val doubled = list.map(i => i*2)\n" + "System.out.print(doubled)";

		// When
		final IScriptExecutionResult result = engine.execute(script);

		// Then
		assertTrue(result.isSuccess());
		assertNull(result.getException());
		assertNull(result.getReturnValue());
		final String output = result.getOutput();
		assertEquals("List(2, 4, 6)", output.substring(output.length() - 13));

	}

	@Test
	public void test_simple_script_2() throws Exception
	{
		// Given
		final String script = "print(\"list\")";

		// When
		final IScriptExecutionResult result = engine.execute(script);

		// Then
		assertTrue(result.isSuccess());
		assertNull(result.getException());
		assertNull(result.getReturnValue());
		final String output = result.getOutput();
		assertEquals("list", output);

	}

	@Test
	public void test_returnValue() throws Exception
	{
		// Given
		final String script = "val $result = 42; print($result);";

		// When
		final IScriptExecutionResult result = engine.execute(script);

		// Then
		assertTrue(result.isSuccess());
		assertNull(result.getException());
		assertEquals("42", result.getOutput());

	}

	@Test
	public void test_simple_def() throws Exception
	{
		// Given
		final String script = "def fak(n: Int): Int = {\n" + "if (n>1) n * fak(n-1) else 1\n}\n"
			+ "System.out.print(fak(5))";

		// When
		final IScriptExecutionResult result = engine.execute(script);

		// Then
		assertNull(result.getReturnValue());
		final String output = result.getOutput();
		assertEquals("120", output);
	}

	@Test
	public void test_simple_class() throws Exception
	{
		// Given
		final String script = "class Foo {\n" + "val x:String = \"Foo\"\n" + "}\n" + "\n"
			+ "val f = new Foo\n" + "System.out.print(f.x)\n";

		// When
		final IScriptExecutionResult result = engine.execute(script);

		// Then
		assertNull(result.getReturnValue());
		final String output = result.getOutput();
		assertEquals("Foo", output);
	}

	@Test
	public void test_simple_binding() throws Exception
	{
		// Given
		final String script = "System.out.print(x)";
		final Map<String, Object> bindings = new HashMap<String, Object>();
		bindings.put("x", new Integer(5));

		// When
		final IScriptExecutionResult result = engine.execute(script, bindings);

		// Then
		assertEquals("5", result.getOutput());

	}

	@Test
	public void test_binding_types() throws Exception
	{
		// Given
		final Map<String, Object> bindings = new HashMap<String, Object>();
		bindings.put("x", new Integer(5));
		final String script = "println(x.getClass().getName())\nprint(x.intValue())\n";

		// When
		final IScriptExecutionResult result = engine.execute(script, bindings);

		// Then
		assertEquals("java.lang.Integer" + NEWLINE + "5", result.getOutput());

	}

	@Test
	public void test_execute_exception_01() throws Exception
	{
		// Given
		final String script = "xxx";

		// When
		final IScriptExecutionResult result = engine.execute(script);

		// Then
		final String output = result.getOutput();
		assertFalse(result.isSuccess());
		final Throwable exception = result.getException();
		assertNotNull(exception);
		assertTrue(exception instanceof Exception);
		assertTrue(output.contains("error"));
		assertNull(result.getReturnValue());
	}

	@Test
	public void test_execute_exception_02_incomplete() throws Exception
	{
		// Given
		final String script = "for (i <- 1 to 10";

		// When
		final IScriptExecutionResult result = engine.execute(script);

		// Then
		assertFalse(result.isSuccess());
		final Throwable exception = result.getException();
		assertNotNull(exception);
		assertTrue(exception instanceof Exception);
		assertNull(result.getReturnValue());
	}


}
