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
import org.wicketstuff.console.engine.ClojureEngine;
import org.wicketstuff.console.engine.IScriptEngine;
import org.wicketstuff.console.engine.IScriptExecutionResult;

public class ClojureEngineTest {

	private ClojureEngine engine;

	@Before
	public void setup() {
		engine = new ClojureEngine();
	}

	@Test
	public void test_instanceof_engine() throws Exception {
		assertTrue(engine instanceof IScriptEngine);
	}

	@Test
	public void test_execute_emptyScript() throws Exception {
		// Given
		final String script = "(ns foo.baz)";

		// When
		final IScriptExecutionResult result = engine.execute(script);

		// Then
		assertTrue(result.isSuccess());
		assertNull(result.getException());
		assertEquals("", result.getOutput());
		assertNull(result.getReturnValue());
	}

	@Test
	public void test_execute_exception_01() throws Exception {
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
	public void test_empty_script() throws Exception {
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
	public void test_simple_returnValue() throws Exception {
		// Given
		final String script = "5";

		// When
		final IScriptExecutionResult result = engine.execute(script);

		// Then
		assertTrue(result.isSuccess());
		assertNull(result.getException());
		assertEquals("", result.getOutput());
		assertEquals(5, result.getReturnValue());
	}

	@Test
	public void test_simple_output_sysout() throws Exception {
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
	public void test_simple_output_rtout() throws Exception {
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
	public void test_simple_defn() throws Exception {
		// Given
		final String script = "(defn fak [n]"
				+ "(if (< n 1) 1 (* n (fak (dec n)))))" + "(print (fak 5))";

		// When
		final IScriptExecutionResult result = engine.execute(script);

		// Then
		assertNull(result.getReturnValue());
		final String output = result.getOutput();
		assertEquals("120", output);
	}

	@Test
	public void test_simple_binding() throws Exception {
		// Given
		final String script = "(org.wicketstuff.console.engine.ClojureEngine/getBinding \"x\")";
		Map<String, Object> bindings = new HashMap<String, Object>();
		bindings.put("x", 5);

		// When
		final IScriptExecutionResult result = engine.execute(script, bindings);

		// Then
		assertEquals(5, result.getReturnValue());

	}

}
