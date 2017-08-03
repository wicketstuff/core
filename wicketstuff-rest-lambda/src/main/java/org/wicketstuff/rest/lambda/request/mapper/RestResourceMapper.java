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
package org.wicketstuff.rest.lambda.request.mapper;

import org.apache.wicket.core.request.mapper.ResourceMapper;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.resource.ResourceReference;
import org.wicketstuff.rest.utils.http.HttpMethod;
import org.wicketstuff.rest.utils.http.HttpUtils;

/**
 * 
 * Specialization of {@link ResourceMapper} to mount REST resources.
 * 
 * @author andrea
 *
 */
public class RestResourceMapper extends ResourceMapper 
{

	private final HttpMethod httpMethod;

	/**
	 * Constructor for the mapper
	 * 
	 * @param path
	 * 				mount path for the resource
	 * @param resourceReference
	 * 				resource reference that should be linked to the mount path
	 * @param httpMethod
	 * 				the HTTP method used by the resource
	 */
	public RestResourceMapper(String path, ResourceReference resourceReference, HttpMethod httpMethod) 
	{
		super(path, resourceReference);
		this.httpMethod = httpMethod;
	}

	@Override
	public int getCompatibilityScore(Request request)
	{
		HttpMethod requestHttpMethod = HttpUtils.getHttpMethod(request);
		
		if (requestHttpMethod != httpMethod) 
		{
			return Integer.MIN_VALUE;
		}
		
		return super.getCompatibilityScore(request);
	}

	@Override
	public IRequestHandler mapRequest(Request request) 
	{
		HttpMethod requestHttpMethod = HttpUtils.getHttpMethod(request);
		
		if (requestHttpMethod != httpMethod) 
		{
			return null;
		}
		
		return super.mapRequest(request);
	}
	
	public HttpMethod getHttpMethod() 
	{
		return httpMethod;
	}
}
