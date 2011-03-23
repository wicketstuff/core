package org.wicketstuff.console.engine;

final class DefaultScriptExecutionResult implements IScriptExecutionResult {

	private final String script;
	private final Throwable exception;
	private final String output;
	private final Object returnValue;

	public DefaultScriptExecutionResult(String script, Throwable exception,
			String output, Object returnValue) {
		this.script = script;
		this.exception = exception;
		this.output = output;
		this.returnValue = returnValue;
	}

	public String getScript() {
		return script;
	}

	public boolean isSuccess() {
		return exception == null;
	}

	public Throwable getException() {
		return exception;
	}

	public String getOutput() {
		return output;
	}

	public Object getReturnValue() {
		return returnValue;
	}

}