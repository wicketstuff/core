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

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertSame;
import static junit.framework.Assert.assertTrue;

import org.junit.Test;

public class DefaultScriptExecutionResultTest
{

	@Test
	public void test_success() throws Exception
	{

		// Given
		final String script = "";
		final Throwable exception = null;
		final String output = "output";
		final Object returnValue = null;

		// When
		final DefaultScriptExecutionResult result = new DefaultScriptExecutionResult(script,
			exception, output, returnValue);

		// Then
		assertTrue(result.isSuccess());

	}

	@Test
	public void test_simple_fail() throws Exception
	{

		// Given
		final String script = "";
		final Throwable exception = new IllegalArgumentException("foo");
		final String output = "output";
		final Object returnValue = null;

		// When
		final DefaultScriptExecutionResult result = new DefaultScriptExecutionResult(script,
			exception, output, returnValue);

		// Then
		assertFalse(result.isSuccess());
		assertSame(exception, result.getException());

	}

}
