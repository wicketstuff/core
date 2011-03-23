package org.wicketstuff.console.engine;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertSame;
import static junit.framework.Assert.assertTrue;

import org.junit.Test;
import org.wicketstuff.console.engine.DefaultScriptExecutionResult;


public class DefaultScriptExecutionResultTest {

	
	@Test
	public void test_success() throws Exception {

		// Given
		String script = "";
		Throwable exception = null;
		String output = "output";
		Object returnValue = null;

		// When
		DefaultScriptExecutionResult result = new DefaultScriptExecutionResult(
				script, exception, output, returnValue);

		// Then
		assertTrue(result.isSuccess());

	}

	@Test
	public void test_simple_fail() throws Exception {

		// Given
		String script = "";
		Throwable exception = new IllegalArgumentException("foo");
		String output = "output";
		Object returnValue = null;

		// When
		DefaultScriptExecutionResult result = new DefaultScriptExecutionResult(
				script, exception, output, returnValue);

		// Then
		assertFalse(result.isSuccess());
		assertSame(exception, result.getException());

	}

}
