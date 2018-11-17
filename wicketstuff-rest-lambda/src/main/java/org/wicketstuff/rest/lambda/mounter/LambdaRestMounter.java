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
package org.wicketstuff.rest.lambda.mounter;

import java.util.function.Consumer;
import java.util.function.Function;

import org.apache.wicket.core.request.mapper.ResourceMapper;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;
import org.wicketstuff.rest.lambda.request.mapper.RestResourceMapper;
import org.wicketstuff.rest.lambda.resource.SimpleLambdaResource;
import org.wicketstuff.rest.lambda.resource.TextOutputLambdaResource;
import org.wicketstuff.restutils.http.HttpMethod;
import org.wicketstuff.restutils.wicket.AttributesWrapper;

/**
 * REST resource mounter. The resources are built using the provided functions/consumers.
 * 
 * @see SimpleLambdaResource
 * @see TextOutputLambdaResource
 * 
 * @author andrea
 *
 */
public class LambdaRestMounter
{
	
	/** The application. */
	private final WebApplication application;
	
	/**
	 * Constructor for the mounter.
	 * 
	 * @param application
	 * 			The web application we are using.
	 */
	public LambdaRestMounter(WebApplication application) 
	{
		this.application = application;
	}

	/**
	 * Mount a {@link TextOutputLambdaResource} to the given path and for 
	 * HTTP method POST.
	 *
	 * @param path the mounting path
	 * @param respondFunction the respond function
	 * @param outputFunction the output function
	 */
	public void post(final String path, final Function<AttributesWrapper, Object> respondFunction,
			final Function<Object, String> outputFunction) 
	{		
		mountRestResource(HttpMethod.POST, path, respondFunction, outputFunction);
	}
	
	/**
	 * Mount a {@link SimpleLambdaResource} to the given path and for 
	 * HTTP method POST.
	 *
	 * @param path the mounting path
	 * @param respondConsumer the respond consumer
	 */
	public void post(final String path, final Consumer<AttributesWrapper> respondConsumer) 
	{		
		mountRestResource(HttpMethod.POST, path, respondConsumer);
	}
	
	/**
	 * Mount a {@link TextOutputLambdaResource} to the given path and for 
	 * HTTP method GET.
	 *
	 * @param path the mounting path
	 * @param respondFunction the respond function
	 * @param outputFunction the output function
	 */
	public void get(final String path, final Function<AttributesWrapper, Object> respondFunction,
			final Function<Object, String> outputFunction) 
	{		
		mountRestResource(HttpMethod.GET, path, respondFunction, outputFunction);
	}
	
	/**
	 * Mount a {@link SimpleLambdaResource} to the given path and for 
	 * HTTP method GET.
	 *
	 * @param path the mounting path
	 * @param respondConsumer the respond consumer
	 */
	public void get(final String path, final Consumer<AttributesWrapper> respondConsumer) 
	{		
		mountRestResource(HttpMethod.GET, path, respondConsumer);
	}
	
	/**
	 * Mount a {@link SimpleLambdaResource} to the given path and for 
	 * HTTP method PUT.
	 *
	 * @param path the mounting path
	 * @param respondConsumer the respond consumer
	 */
	public void put(final String path, final Consumer<AttributesWrapper> respondConsumer)
	{
		mountRestResource(HttpMethod.PUT, path, respondConsumer);
	}
	
	/**
	 * Mount a {@link TextOutputLambdaResource} to the given path and for 
	 * HTTP method PUT.
	 *
	 * @param path the mounting path
	 * @param respondFunction the respond function
	 * @param outputFunction the output function
	 */
	public void put(final String path, final Function<AttributesWrapper, Object> respondFunction,
			final Function<Object, String> outputFunction) 
	{		
		mountRestResource(HttpMethod.PUT, path, respondFunction, outputFunction);
	}
	
	/**
	 * Mount a {@link SimpleLambdaResource} to the given path and for 
	 * HTTP method DELETE.
	 *
	 * @param path the mounting path
	 * @param respondConsumer the respond consumer
	 */
	public void delete(final String path, final Consumer<AttributesWrapper> respondConsumer)
	{
		mountRestResource(HttpMethod.DELETE, path, respondConsumer);
	}
	
	/**
	 * Mount a {@link TextOutputLambdaResource} to the given path and for 
	 * HTTP method DELETE.
	 *
	 * @param path the mounting path
	 * @param respondFunction the respond function
	 * @param outputFunction the output function
	 */
	public void delete(final String path, final Function<AttributesWrapper, Object> respondFunction,
			final Function<Object, String> outputFunction) 
	{		
		mountRestResource(HttpMethod.DELETE, path, respondFunction, outputFunction);
	}
	
	/**
	 * Mount a {@link SimpleLambdaResource} to the given path and for 
	 * HTTP method OPTIONS.
	 *
	 * @param path the mounting path
	 * @param respondConsumer the respond consumer
	 */
	public void options(final String path, final Consumer<AttributesWrapper> respondConsumer)
	{
		mountRestResource(HttpMethod.OPTIONS, path, respondConsumer);
	}
	
	/**
	 * Mount a {@link TextOutputLambdaResource} to the given path and for 
	 * HTTP method OPTIONS.
	 *
	 * @param path the mounting path
	 * @param respondFunction the respond function
	 * @param outputFunction the output function
	 */
	public void options(final String path, final Function<AttributesWrapper, Object> respondFunction,
			final Function<Object, String> outputFunction) 
	{		
		mountRestResource(HttpMethod.OPTIONS, path, respondFunction, outputFunction);
	}
	
	/**
	 * Mount a {@link SimpleLambdaResource} to the given path and for 
	 * HTTP method PATCH.
	 *
	 * @param path the mounting path
	 * @param respondConsumer the respond consumer
	 */
	public void patch(final String path, final Consumer<AttributesWrapper> respondConsumer)
	{
		mountRestResource(HttpMethod.PATCH, path, respondConsumer);
	}

	/**
	 * Mount a {@link TextOutputLambdaResource} to the given path and for 
	 * HTTP method PATCH.
	 *
	 * @param path the mounting path
	 * @param respondFunction the respond function
	 * @param outputFunction the output function
	 */
	public void patch(final String path, final Function<AttributesWrapper, Object> respondFunction,
			final Function<Object, String> outputFunction) 
	{		
		mountRestResource(HttpMethod.PATCH, path, respondFunction, outputFunction);
	}
	
	/**
	 * Mount a {@link SimpleLambdaResource} to the given path and for 
	 * HTTP method HEAD.
	 *
	 * @param path the mounting path
	 * @param respondConsumer the respond consumer
	 */
	public void head(final String path, final Consumer<AttributesWrapper> respondConsumer)
	{
		mountRestResource(HttpMethod.HEAD, path, respondConsumer);
	}

	/**
	 * Mount a {@link TextOutputLambdaResource} to the given path and for 
	 * HTTP method HEAD.
	 *
	 * @param path the mounting path
	 * @param respondFunction the respond function
	 * @param outputFunction the output function
	 */
	public void head(final String path, final Function<AttributesWrapper, Object> respondFunction,
			final Function<Object, String> outputFunction) 
	{		
		mountRestResource(HttpMethod.HEAD, path, respondFunction, outputFunction);
	}
	
	/**
	 * Mount a {@link SimpleLambdaResource} to the given path and for 
	 * HTTP method TRACE.
	 *
	 * @param path the mounting path
	 * @param respondConsumer the respond consumer
	 */
	public void trace(final String path, final Consumer<AttributesWrapper> respondConsumer)
	{
		mountRestResource(HttpMethod.TRACE, path, respondConsumer);
	}

	/**
	 * Mount a {@link TextOutputLambdaResource} to the given path and for 
	 * HTTP method TRACE.
	 *
	 * @param path the mounting path
	 * @param respondFunction the respond function
	 * @param outputFunction the output function
	 */
	public void trace(final String path, final Function<AttributesWrapper, Object> respondFunction,
			final Function<Object, String> outputFunction) 
	{		
		mountRestResource(HttpMethod.TRACE, path, respondFunction, outputFunction);
	}
	
	/**
	 * Mount a {@link TextOutputLambdaResource} to the given path and for the given http method
	 *
	 * @param httpMethod the http method
	 * @param path the mounting path
	 * @param respondFunction the respond function
	 * @param outputFunction the output function
	 * @return the resource mapper
	 */
	public ResourceMapper mountRestResource(final HttpMethod httpMethod, final String path, 
											final Function<AttributesWrapper, Object> respondFunction,
											final Function<Object, String> outputFunction)
	{
		TextOutputLambdaResource resource = new TextOutputLambdaResource(respondFunction, outputFunction);
		return mountRestResource(httpMethod, path, resource);
	}

	/**
	 * Mount a {@link SimpleLambdaResource} to the given path and for the given http method
	 *
	 * @param httpMethod the http method
	 * @param path the mounting path
	 * @param respondConsumer the respond consumer
	 * @return the resource mapper
	 */
	public ResourceMapper mountRestResource(final HttpMethod httpMethod, final String path, 
			final Consumer<AttributesWrapper> respondConsumer)
	{
		SimpleLambdaResource resource = new SimpleLambdaResource(respondConsumer);
		return mountRestResource(httpMethod, path, resource);
	}

	/**
	 * Mount rest resource.
	 *
	 * @param httpMethod the http method
	 * @param path the mounting path
	 * @param resource the resource
	 * @return the resource mapper
	 */
	protected ResourceMapper mountRestResource(final HttpMethod httpMethod, final String path,
			IResource resource) 
	{
		ResourceReference reference = ResourceReference.of(path + "_" + httpMethod.name(), () -> resource);
		
		if (reference.canBeRegistered())
		{
			application.getResourceReferenceRegistry().registerResourceReference(reference);
		}
		
		RestResourceMapper mapper = new RestResourceMapper(path, reference, httpMethod);
		application.mount(mapper);
		return mapper;
	}
}
