package org.wicketstuff.console.engine;

public interface IScriptExecutionResult {

	String getScript();

	boolean isSuccess();

	String getOutput();

	Throwable getException();

	Object getReturnValue();

}
