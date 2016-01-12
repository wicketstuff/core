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

import java.util.Map;

/**
 * An {@link IScriptEngine} executes a script in a certain language and returns the results of the
 * execution as an {@link IScriptExecutionResult}.
 * 
 * @author cretzel
 */
public interface IScriptEngine
{

	/**
	 * Executes a script.
	 * 
	 * @param script
	 *            a script in the language supported by this engine
	 * @return result of the script execution
	 */
	IScriptExecutionResult execute(String script);

	/**
	 * Executes a script.
	 * 
	 * @param script
	 *            a script in the language supported by this engine
	 * @param bindings
	 *            variable bindings
	 * @return result of the script execution
	 */
	IScriptExecutionResult execute(String script, Map<String, Object> bindings);

}
