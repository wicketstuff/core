package org.wicketstuff.console.engine;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.StringReader;
import java.util.Collections;
import java.util.Map;

import clojure.lang.Compiler;
import clojure.lang.RT;

public class ClojureEngine implements IScriptEngine {

	// TODO maybe this should be global for all engines
	public static Map<String, Object> _bindings;

	public static Map<String, Object> getBindings() {
		return _bindings;
	}

	public static Object getBinding(String key) {
		return _bindings.get(key);
	}

	public synchronized IScriptExecutionResult execute(final String script) {
		return execute(script, null);
	}

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
