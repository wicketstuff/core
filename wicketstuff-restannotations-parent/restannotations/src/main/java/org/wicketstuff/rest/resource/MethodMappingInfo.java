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
package org.wicketstuff.rest.resource;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.util.collections.MultiMap;
import org.wicketstuff.rest.annotations.AuthorizeInvocation;
import org.wicketstuff.rest.annotations.MethodMapping;
import org.wicketstuff.rest.contenthandling.mimetypes.IMimeTypeResolver;
import org.wicketstuff.rest.resource.urlsegments.AbstractURLSegment;
import org.wicketstuff.rest.utils.collection.CollectionUtils;
import org.wicketstuff.rest.utils.http.HttpMethod;
import org.wicketstuff.rest.utils.reflection.MethodParameter;

/**
 * This class contains the informations of a resource mapped method (i.e. a method annotated with
 * {@link MethodMapping}). These informations are used at runtime to select the most suited method
 * to serve the current request.
 * 
 * @author andrea del bene
 * 
 */
public class MethodMappingInfo implements IMimeTypeResolver
{
	/** The HTTP method used to invoke this mapped method. */
	private final HttpMethod httpMethod;
	/** Segments that compose the URL we mapped the method on. */
	private final List<AbstractURLSegment> segments;
	/** Optional roles we used to annotate the method (see. {@link AuthorizeInvocation}). */
	private final Roles roles;
	/** The resource method we have mapped. */
	private final Method method;
	/** The MIME type to use in input. */
	private final String inputFormat;
	/** The MIME type to use in output. */
	private final String outputFormat;
	/** Method parameters list */
	private final List<MethodParameter<?>> methodParameters;
	/** Method parameters list */
	private final Map<Class<? extends Annotation>, List<MethodParameter<?>>> annotatedMethodParameters;
	
	/**
	 * Class constructor.
	 * 
	 * @param methodMapped
	 *            the method mapped
	 * @param method
	 *            the resource's method mapped.
	 */
	public MethodMappingInfo(MethodMapping methodMapped, Method method)
	{
		this.httpMethod = methodMapped.httpMethod();
		this.method = method;
		this.segments = Collections.unmodifiableList(loadSegments(methodMapped.value()));
		this.roles = loadRoles();

		this.inputFormat = methodMapped.consumes();
		this.outputFormat = methodMapped.produces();
		this.methodParameters = loadMethodParameters(method);
		this.annotatedMethodParameters = loadAnnotatedMethodParameters();
	}

	private List<MethodParameter<?>> loadMethodParameters(Method method)
	{
		Class<?>[] paramsTypes = method.getParameterTypes();
		List<MethodParameter<?>> methodParameters = new ArrayList<>();

		for (int i = 0; i < paramsTypes.length; i++)
		{
			methodParameters.add(new MethodParameter<>(paramsTypes[i], this, i));
		}

		return Collections.unmodifiableList(methodParameters);
	}

	private Map<Class<? extends Annotation>, List<MethodParameter<?>>> loadAnnotatedMethodParameters()
	{
		MultiMap<Class<? extends Annotation>, MethodParameter<?>> result = new MultiMap<>();
		
		for (MethodParameter<?> methodParameter : methodParameters)
		{
			Annotation annotationParam = methodParameter.getAnnotationParam();
			
			if(annotationParam != null)
			{
				result.addValue(annotationParam.getClass(), methodParameter);
			}
		}
		
		return CollectionUtils.makeListMapImmutable(result);
	}

	/**
	 * Loads the segment that compose the URL used to map the method. Segments are instances of
	 * class {@link AbstractURLSegment}.
	 * 
	 * @param urlPath
	 *            the URL path of the method.
	 * @return a list containing the segments that compose the URL in input
	 */
	private List<AbstractURLSegment> loadSegments(String urlPath)
	{
		String[] segArray = urlPath.split("/");
		ArrayList<AbstractURLSegment> segments = new ArrayList<AbstractURLSegment>();

		for (int i = 0; i < segArray.length; i++)
		{
			String segment = segArray[i];
			AbstractURLSegment segmentValue;

			if (segment.isEmpty())
				continue;

			segmentValue = AbstractURLSegment.newSegment(segment);
			segments.add(segmentValue);
		}

		return segments;
	}

	/**
	 * Load the optional roles used to annotate the method with.
	 * 
	 * @return the authorization roles for the method. {@link AuthorizeInvocation}
	 */
	private Roles loadRoles()
	{
		AuthorizeInvocation authorizeInvocation = method.getAnnotation(AuthorizeInvocation.class);
		Roles roles = new Roles();

		if (authorizeInvocation != null)
		{
			roles = new Roles(authorizeInvocation.value());
		}
		return roles;
	}

	// getters and setters

	/**
	 * Gets the segments of the mapped URL.
	 * 
	 * @return the segments
	 */
	public List<AbstractURLSegment> getSegments()
	{
		return segments;
	}

	/**
	 * Gets the segments count.
	 * 
	 * @return the segments count
	 */
	public int getSegmentsCount()
	{
		return segments.size();
	}

	/**
	 * Gets the HTTP method.
	 * 
	 * @return the HTTP method
	 */
	public HttpMethod getHttpMethod()
	{
		return httpMethod;
	}

	/**
	 * Gets the relative class method.
	 * 
	 * @return the class method
	 */
	public Method getMethod()
	{
		return method;
	}

	/**
	 * Gets the optional authorization roles for this method.
	 * 
	 * @return the roles
	 */
	public Roles getRoles()
	{
		return roles;
	}

	/**
	 * Gets the mime input format.
	 * 
	 * @return the mime input format
	 */
	public String getInputFormat()
	{
		return inputFormat;
	}

	/**
	 * Gets the mime output format.
	 * 
	 * @return the mime output format
	 */
	public String getOutputFormat()
	{
		return outputFormat;
	}

	/**
	 * Gets the method parameters.
	 *
	 * @return the method parameters
	 */
	public List<MethodParameter<?>> getMethodParameters()
	{
		return methodParameters;
	}

	/**
	 * Gets the method parameters stored by annotation.
	 *
	 * @return the method parameters
	 */
	public Map<Class<? extends Annotation>, List<MethodParameter<?>>> getAnnotatedMethodParameters()
	{
		return annotatedMethodParameters;
	}
}