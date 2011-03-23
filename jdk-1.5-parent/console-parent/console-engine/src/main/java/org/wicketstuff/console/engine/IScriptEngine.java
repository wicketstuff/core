package org.wicketstuff.console.engine;

import java.util.Map;

public interface IScriptEngine {

	IScriptExecutionResult execute(String script);

	IScriptExecutionResult execute(String script, Map<String, Object> bindings);

}
