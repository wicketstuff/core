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

public class ClojureEngineTest
{

	private IScriptEngine engine;

	@Before
	public void setup()
	{
		engine = new ClojureEngine();
	}

	@Test
	public void test_empty_script() throws Exception
	{
		// Given
		final String script = "";

		// When
		final IScriptExecutionResult result = engine.execute(script);

		// Then
		assertTrue(result.isSuccess());
		assertNull(result.getException());
		assertEquals("", result.getOutput());
		assertNull(result.getReturnValue());
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
		assertEquals("", result.getOutput());
		assertNull(result.getReturnValue());
	}

	@Test
	public void test_simple_returnValue() throws Exception
	{
		// Given
		final String script = "5";

		// When
		final IScriptExecutionResult result = engine.execute(script);

		// Then
		assertTrue(result.isSuccess());
		assertNull(result.getException());
		assertEquals("", result.getOutput());
		assertEquals(5L, result.getReturnValue());
	}

	@Test
	public void test_simple_output_sysout() throws Exception
	{
		// Given
		final String script = "(.print System/out 5)";

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
	public void test_simple_output_rtout() throws Exception
	{
		// Given
		final String script = "(print 6)";

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
	public void test_simple_defn() throws Exception
	{
		// Given
		final String script = "(defn fak [n]" + "(if (< n 1) 1 (* n (fak (dec n)))))"
			+ "(print (fak 5))";

		// When
		final IScriptExecutionResult result = engine.execute(script);

		// Then
		assertNull(result.getReturnValue());
		final String output = result.getOutput();
		assertEquals("120", output);
	}

	@Test
	public void test_simple_binding() throws Exception
	{
		// Given
		final String script = "user/x";
		final Map<String, Object> bindings = new HashMap<String, Object>();
		bindings.put("x", 5);

		// When
		final IScriptExecutionResult result = engine.execute(script, bindings);

		// Then
		assertNull(result.getException());
		assertEquals(5, result.getReturnValue());

	}

}
