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

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.python.util.PythonInterpreter;

/**
 * Executes Jython/Python code.
 * 
 * @author cretzel
 */
public class JythonEngine implements IScriptEngine
{

	/**
	 * {@inheritDoc}
	 */
	public synchronized IScriptExecutionResult execute(final String script)
	{
		return execute(script, new HashMap<String, Object>());
	}

	/**
	 * {@inheritDoc}
	 */
	public synchronized IScriptExecutionResult execute(final String script,
		final Map<String, Object> bindings)
	{

		Throwable exception = null;
		String output = null;
		Object returnValue = null;

		final PrintStream oldOut = System.out;
		final PrintStream oldErr = System.err;
		final ByteArrayOutputStream bout = new ByteArrayOutputStream();
		final PrintStream newOut = new PrintStream(bout, false);

		try
		{
			System.setOut(newOut);
			System.setErr(newOut);

			final PythonInterpreter interpreter = new PythonInterpreter();
			interpreter.setOut(newOut);
			interpreter.setErr(newOut);

			for (final Entry<String, Object> binding : bindings.entrySet())
			{
				interpreter.set(binding.getKey(), binding.getValue());
			}

			interpreter.exec(script);
			returnValue = interpreter.get("result");
		}
		catch (final Exception e)
		{
			exception = e;
			e.printStackTrace();
		}
		finally
		{

			try
			{
				System.setOut(oldOut);
				System.setErr(oldErr);

				output = bout.toString();
			}
			catch (final Exception e1)
			{
				throw new ScriptEngineException(e1);
			}

		}

		return new DefaultScriptExecutionResult(script, exception, output, returnValue);
	}

}
