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
 * Represents an exception during script engine execution.
 * <p>
 * This does not represent an exception thrown by the script itself, which would rather be included
 * in the {@link IScriptExecutionResult}.
 * 
 * @author cretzel
 * 
 */
public class ScriptEngineException extends RuntimeException
{

	private static final long serialVersionUID = 1L;

	public ScriptEngineException()
	{
		super();
	}

	public ScriptEngineException(final String message, final Throwable cause)
	{
		super(message, cause);
	}

	public ScriptEngineException(final String message)
	{
		super(message);
	}

	public ScriptEngineException(final Throwable cause)
	{
		super(cause);
	}

}
