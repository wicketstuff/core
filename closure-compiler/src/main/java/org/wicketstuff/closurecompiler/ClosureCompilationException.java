package org.wicketstuff.closurecompiler;

import java.util.Collections;
import java.util.List;

import org.apache.wicket.util.lang.Args;

import com.google.javascript.jscomp.JSError;

public class ClosureCompilationException extends Exception
{
	private static final long serialVersionUID = 1L;
	private final List<JSError> errors;

	public ClosureCompilationException(List<JSError> errors)
	{
		this.errors = Collections.unmodifiableList(Args.notNull(errors, "errors"));
	}

	public List<JSError> getErrors()
	{
		return errors;
	}

	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder();
		sb.append("ClosureCompilerCompilationException");
		sb.append("{errors=").append(errors);
		sb.append('}');
		return sb.toString();
	}
}
