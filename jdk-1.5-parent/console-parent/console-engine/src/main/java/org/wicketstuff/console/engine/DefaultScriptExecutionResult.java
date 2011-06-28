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

/**
 * Default implementation of {@link IScriptExecutionResult}.
 * <p>
 * {@link #isSuccess()} returns {@code true}, if there isn't any exception.
 * 
 * @author cretzel
 */
final class DefaultScriptExecutionResult implements IScriptExecutionResult
{

	private final String script;
	private final Throwable exception;
	private final String output;
	private final Object returnValue;

	public DefaultScriptExecutionResult(final String script, final Throwable exception,
		final String output, final Object returnValue)
	{
		this.script = script;
		this.exception = exception;
		this.output = output;
		this.returnValue = returnValue;
	}

	public String getScript()
	{
		return script;
	}

	public boolean isSuccess()
	{
		return exception == null;
	}

	public Throwable getException()
	{
		return exception;
	}

	public String getOutput()
	{
		return output;
	}

	public Object getReturnValue()
	{
		return returnValue;
	}

}