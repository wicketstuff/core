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
import java.util.Collections;
import java.util.Map;

import clojure.lang.Compiler;
import clojure.lang.RT;

/**
 * /** Executes Clojure code.
 * <p>
 * stdout and stderr are captured. Bindings are available to the Clojure code
 * via public static methods {@link #getBinding(String)} and
 * {@link #getBindings()}.
 * 
 * @author cretzel
 * 
 */
public class ClojureEngine implements IScriptEngine {

	private static Map<String, Object> _bindings;

	/** Returns all bindings. */
	public static Map<String, Object> getBindings() {
		return _bindings;
	}

	/** Returns value bound to key @{code key}. */
	public static Object getBinding(String key) {
		return _bindings.get(key);
	}

	/**
	 * {@inheritDoc}
	 */
	public synchronized IScriptExecutionResult execute(final String script) {
		return execute(script, null);
	}

	/**
	 * {@inheritDoc}
	 */
	public synchronized IScriptExecutionResult execute(final String script,
			final Map<String, Object> bindings) {

		/*
		 * put bindings into public static _bindings to be accessible by the
		 * script via ClojureEngine#getBinding(). Not especially nice, but
		 * should probably work for the most engines.
		 */
		if (bindings == null) {
			_bindings = Collections.emptyMap();
		} else {
			_bindings = Collections.synchronizedMap(bindings);
		}

		Throwable exception = null;
		String output = null;
		Object returnValue = null;

		final PrintStream oldOut = System.out;
		final PrintStream oldErr = System.err;
		final ByteArrayOutputStream bout = new ByteArrayOutputStream();
		final PrintStream newOut = new PrintStream(bout, false);
		final OutputStreamWriter newRtOut = new OutputStreamWriter(newOut);
		final Object oldRtOut = RT.OUT.get();

		try {
			RT.OUT.doReset(newRtOut);
			System.setOut(newOut);
			System.setErr(newOut);
			returnValue = Compiler.load(new StringReader(script));
		} catch (final Exception e) {
			exception = e;
		} finally {
			try {
				RT.OUT.doReset(oldRtOut);
				newRtOut.flush();
				newRtOut.close();
			} catch (final Exception e1) {
				exception = e1;
			}
			System.setOut(oldOut);
			System.setErr(oldErr);
			output = bout.toString();
		}

		return new DefaultScriptExecutionResult(script, exception, output,
				returnValue);
	}

}
