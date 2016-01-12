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
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.StringReader;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import clojure.lang.Associative;
import clojure.lang.Compiler;
import clojure.lang.Namespace;
import clojure.lang.PersistentHashMap;
import clojure.lang.RT;
import clojure.lang.Symbol;
import clojure.lang.Var;

/**
 * Executes Clojure code.
 * <p>
 * stdout and stderr are captured. Bindings are pushed into user namespace, so to access a binding
 * named &quot;foo&quot; use &quot;user/foo&quot;. *
 * 
 * @author cretzel
 */
public class ClojureEngine implements IScriptEngine
{

	/**
	 * {@inheritDoc}
	 */
	public synchronized IScriptExecutionResult execute(final String script)
	{
		return execute(script, null);
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
		final OutputStreamWriter newRtOut = new OutputStreamWriter(newOut);
		boolean threadBindingPushed = false;

		try
		{
			System.setOut(newOut);
			System.setErr(newOut);

			Associative mappings = PersistentHashMap.EMPTY;
			mappings = mappings.assoc(RT.CURRENT_NS, RT.CURRENT_NS.get());
			mappings = mappings.assoc(RT.OUT, newRtOut);
			mappings = mappings.assoc(RT.ERR, newRtOut);
			mappings = applyBindings(bindings, mappings);
			Var.pushThreadBindings(mappings);
			threadBindingPushed = true;

			returnValue = Compiler.load(new StringReader(script));
		}
		catch (final Exception e)
		{
			exception = e;
		}
		finally
		{

			try
			{
				if (threadBindingPushed)
				{
					Var.popThreadBindings();
				}
				newRtOut.flush();
				newRtOut.close();
				System.setOut(oldOut);
				System.setErr(oldErr);

				output = bout.toString();
			}
			catch (final Exception e1)
			{
				ScriptEngineException see;
				if (exception == null)
				{
					see = new ScriptEngineException(e1);
				}
				else
				{
					see = new ScriptEngineException("Failed to handle original exception: " +
						exception, e1);
				}

				throw see;
			}

		}

		return new DefaultScriptExecutionResult(script, exception, output, returnValue);
	}

	private Associative applyBindings(final Map<String, Object> bindings, Associative mappings)
	{
		if (bindings != null)
		{
			final Set<Entry<String, Object>> entrySet = bindings.entrySet();
			for (final Entry<String, Object> entry : entrySet)
			{
				final Symbol symbol = Symbol.intern(entry.getKey());
				final Namespace userNs = Namespace.findOrCreate(Symbol.create("user".intern()));
				final Var var = Var.intern(userNs, symbol);
				var.setDynamic(true);
				mappings = mappings.assoc(var, entry.getValue());
			}
		}
		return mappings;
	}

}
