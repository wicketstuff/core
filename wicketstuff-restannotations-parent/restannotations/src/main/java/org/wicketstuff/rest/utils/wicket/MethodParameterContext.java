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
package org.wicketstuff.rest.utils.wicket;

import java.util.Map;

import org.wicketstuff.rest.contenthandling.IWebSerialDeserial;

/**
 * Context execution for a method parameter. It contains the current Attribute wrapper, the value for method parameters and the 
 * object serial/deserial to use.
 * 
 * @author andrea del bene
 * @see IWebSerialDeserial
 */
public class MethodParameterContext
{
	private final AttributesWrapper attributesWrapper;
	private final Map<String, String> pathParameters;
	private final IWebSerialDeserial serialDeserial;

	public MethodParameterContext(AttributesWrapper attributesWrapper,
		Map<String, String> pathParameters, IWebSerialDeserial serialDeserial)
	{
		this.attributesWrapper = attributesWrapper;
		this.pathParameters = pathParameters;
		this.serialDeserial = serialDeserial;
	}

	public AttributesWrapper getAttributesWrapper()
	{
		return attributesWrapper;
	}

	public Map<String, String> getPathParameters()
	{
		return pathParameters;
	}

	public IWebSerialDeserial getSerialDeserial()
	{
		return serialDeserial;
	}
}
