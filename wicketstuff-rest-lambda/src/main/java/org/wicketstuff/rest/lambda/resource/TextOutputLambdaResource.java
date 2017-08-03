/**
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.wicketstuff.rest.lambda.resource;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import org.apache.wicket.request.http.WebResponse;
import org.apache.wicket.request.resource.IResource;
import org.wicketstuff.rest.utils.wicket.AttributesWrapper;

/**
 * {@link IResource} that uses a {@link java.util.function.Function} to serve
 * the request. The result of this function (a generic Object) is then written 
 * as string to the web response. A second function is provided to transform 
 * the result a textual representation.
 * 
 * @author andrea
 *
 */
public class TextOutputLambdaResource implements IResource 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2761454069675861246L;

	final private Function<AttributesWrapper, Object> respondFunction; 

	final private Function<Object, String> outputTextFunction; 
	
	/**
	 * Build a new resource using the two provided functions.
	 * 
	 * @param respondFunction
	 * 				the function used to respond requests
	 * @param outputTextFunction
	 * 				the function used to convert the respond result to text.
	 */
	public TextOutputLambdaResource(Function<AttributesWrapper, Object> respondFunction,
			Function<Object, String> outputTextFunction) 
	{
		this.respondFunction = respondFunction;
		this.outputTextFunction = outputTextFunction;
	}

	@Override
	public void respond(Attributes attributes) 
	{
		AttributesWrapper attributesWrapper = new AttributesWrapper(attributes);
		Optional<?> respondResult = Optional.of(respondFunction.apply(attributesWrapper));
		WebResponse webResponse = attributesWrapper.getWebResponse();
		Consumer<Object> writeToResponse = (result) -> 
			webResponse.write(outputTextFunction.apply(result));
		
		respondResult.ifPresent(writeToResponse);
	}
}
