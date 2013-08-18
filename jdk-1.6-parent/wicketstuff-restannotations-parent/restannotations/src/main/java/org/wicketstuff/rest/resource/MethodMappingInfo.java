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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.rest.annotations.AuthorizeInvocation;
import org.wicketstuff.rest.annotations.MethodMapping;
import org.wicketstuff.rest.contenthandling.RestMimeTypes;
import org.wicketstuff.rest.resource.urlsegments.AbstractURLSegment;
import org.wicketstuff.rest.utils.http.HttpMethod;

// TODO: Auto-generated Javadoc
/**
 * This class contains the informations of a resource mapped method (i.e. a
 * method annotated with {@link MethodMapping}). These informations are used at
 * runtime to select the most suited method to serve the current request.
 * 
 * @author andrea del bene
 * 
 */
public class MethodMappingInfo {
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

	/**
	 * Class constructor.
	 *
	 * @param methodMapped the method mapped
	 * @param method the resource's method mapped.
	 */
	public MethodMappingInfo(MethodMapping methodMapped, Method method) {
		this.httpMethod = methodMapped.httpMethod();
		this.method = method;
		this.segments = Collections.unmodifiableList(loadSegments(methodMapped.value()));
		this.roles = loadRoles();

		this.inputFormat = methodMapped.consumes();
		this.outputFormat = methodMapped.produces();

	}

	/**
	 * Loads the segment that compose the URL used to map the method. Segments
	 * are instances of class {@link AbstractURLSegment}.
	 * 
	 * @param urlPath
	 *            the URL path of the method.
	 * @return a list containing the segments that compose the URL in input
	 */
	private List<AbstractURLSegment> loadSegments(String urlPath) {
		String[] segArray = urlPath.split("/");
		ArrayList<AbstractURLSegment> segments = new ArrayList<AbstractURLSegment>();

		for (int i = 0; i < segArray.length; i++) {
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
	 * @return the authorization roles for the method.
	 * {@link AuthorizeInvocation}
	 */
	private Roles loadRoles() {
		AuthorizeInvocation authorizeInvocation = method.getAnnotation(AuthorizeInvocation.class);
		Roles roles = new Roles();

		if (authorizeInvocation != null) {
			roles = new Roles(authorizeInvocation.value());
		}
		return roles;
	}

	/**
	 * This method is invoked to populate the path parameters found in the
	 * mapped URL with the values obtained from the current request.
	 * 
	 * @param pageParameters
	 *            the current PageParameters.
	 * @return a Map containing the path parameters with their relative value.
	 */
	public LinkedHashMap<String, String> populatePathParameters(PageParameters pageParameters) {
		LinkedHashMap<String, String> pathParameters = new LinkedHashMap<String, String>();
		int indexedCount = pageParameters.getIndexedCount();

		for (int i = 0; i < indexedCount; i++) {
			String segmentContent = AbstractURLSegment.getActualSegment(pageParameters.get(i)
					.toString());
			AbstractURLSegment segment = segments.get(i);

			segment.populatePathVariables(pathParameters, segmentContent);
		}

		return pathParameters;
	}

	// getters and setters

	/**
	 * Gets the segments of the mapped URL.
	 * 
	 * @return the segments
	 */
	public List<AbstractURLSegment> getSegments() {
		return segments;
	}

	/**
	 * Gets the segments count.
	 * 
	 * @return the segments count
	 */
	public int getSegmentsCount() {
		return segments.size();
	}

	/**
	 * Gets the HTTP method.
	 * 
	 * @return the HTTP method
	 */
	public HttpMethod getHttpMethod() {
		return httpMethod;
	}

	/**
	 * Gets the relative class method.
	 * 
	 * @return the class method
	 */
	public Method getMethod() {
		return method;
	}

	/**
	 * Gets the optional authorization roles for this method.
	 * 
	 * @return the roles
	 */
	public Roles getRoles() {
		return roles;
	}

	/**
	 * Gets the mime input format.
	 *
	 * @return the mime input format
	 */
	public String getMimeInputFormat() {
		return inputFormat;
	}

	/**
	 * Gets the mime output format.
	 *
	 * @return the mime output format
	 */
	public String getMimeOutputFormat() {
		return outputFormat;
	}
}