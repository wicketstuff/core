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
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.Application;
import org.apache.wicket.Session;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.authroles.authorization.strategies.role.IRoleCheckingStrategy;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.WebRequest;
import org.apache.wicket.request.http.WebResponse;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.util.collections.MultiMap;
import org.apache.wicket.util.convert.IConverter;
import org.apache.wicket.util.lang.Args;
import org.wicketstuff.rest.annotations.AuthorizeInvocation;
import org.wicketstuff.rest.annotations.MethodMapping;
import org.wicketstuff.rest.annotations.parameters.CookieParam;
import org.wicketstuff.rest.annotations.parameters.HeaderParam;
import org.wicketstuff.rest.annotations.parameters.MatrixParam;
import org.wicketstuff.rest.annotations.parameters.PathParam;
import org.wicketstuff.rest.annotations.parameters.RequestBody;
import org.wicketstuff.rest.annotations.parameters.RequestParam;
import org.wicketstuff.rest.contenthandling.IWebSerialDeserial;
import org.wicketstuff.rest.contenthandling.mimetypes.DefaultMimeTypeResolver;
import org.wicketstuff.rest.contenthandling.mimetypes.IMimeTypeResolver;
import org.wicketstuff.rest.contenthandling.mimetypes.RestMimeTypes;
import org.wicketstuff.rest.resource.urlsegments.AbstractURLSegment;
import org.wicketstuff.rest.utils.http.HttpMethod;
import org.wicketstuff.rest.utils.http.HttpUtils;
import org.wicketstuff.rest.utils.reflection.MethodParameter;
import org.wicketstuff.rest.utils.reflection.ReflectionUtils;

/**
 * Base class to build a resource that serves REST requests.
 * 
 * @author andrea del bene
 * 
 */
public abstract class AbstractRestResource<T extends IWebSerialDeserial> implements IResource {
	/** Multimap that stores every mapped method of the class */
	private final MultiMap<String, MethodMappingInfo> mappedMethods = new MultiMap<String, MethodMappingInfo>();

	/**
	 * The implementation of {@link IWebSerialDeserial} that is used to
	 * serialize/desiarilze objects to/from string (for example to/from JSON)
	 */
	private final T objSerialDeserial;

	/** Role-checking strategy. */
	private final IRoleCheckingStrategy roleCheckingStrategy;

	/**
	 * Constructor with no role-checker (i.e we don't use annotation
	 * {@link AuthorizeInvocation}).
	 * 
	 * @param serialDeserial
	 *            General class that is used to serialize/desiarilze objects to
	 *            string.
	 */
	public AbstractRestResource(T serialDeserial) {
		this(serialDeserial, null);
	}

	/**
	 * Main constructor that takes in input the object serializer/deserializer
	 * and the role-checking strategy to use.
	 * 
	 * @param serialDeserial
	 *            General class that is used to serialize/desiarilze objects to
	 *            string
	 * @param roleCheckingStrategy
	 *            the role-checking strategy.
	 */
	public AbstractRestResource(T serialDeserial, IRoleCheckingStrategy roleCheckingStrategy) {
		Args.notNull(serialDeserial, "serialDeserial");

		this.objSerialDeserial = serialDeserial;
		this.roleCheckingStrategy = roleCheckingStrategy;

		configureObjSerialDeserial(serialDeserial);
		loadAnnotatedMethods();
	}

	/***
	 * Handles a REST request invoking one of the methods annotated with
	 * {@link MethodMapping}. If the annotated method returns a value, this
	 * latter is automatically serialized to a given string format (like JSON,
	 * XML, etc...) and written to the web response.<br/>
	 * If no method is found to serve the current request, a 400 HTTP code is
	 * returned to the client. Similarly, a 401 HTTP code is return if the user
	 * doesn't own one of the roles required to execute an annotated method (See
	 * {@link AuthorizeInvocation}).
	 */
	@Override
	public final void respond(Attributes attributes) {
		PageParameters pageParameters = attributes.getParameters();
		WebResponse response = (WebResponse) attributes.getResponse();
		HttpMethod httpMethod = HttpUtils.getHttpMethod((WebRequest) RequestCycle.get()
				.getRequest());
		int indexedParamCount = pageParameters.getIndexedCount();

		// mapped method are stored concatenating the number of the segments of
		// their URL and their HTTP method (see annotation MethodMapping)
		List<MethodMappingInfo> mappedMethodsCandidates = mappedMethods.get(indexedParamCount + "_"
				+ httpMethod.getMethod());

		MethodMappingInfo mappedMethod = selectMostSuitedMethod(mappedMethodsCandidates,
				pageParameters);

		if (mappedMethod != null) {
			if (!hasAny(mappedMethod.getRoles())) {
				response.sendError(401, "User is not allowed to invoke method on server.");
				return;
			}

			onBeforeMethodInvoked(mappedMethod, attributes);
			Object result = invokeMappedMethod(mappedMethod, attributes);
			onAfterMethodInvoked(mappedMethod, attributes, result);

			// if the invoked method returns a value, it is written to response
			if (result != null) {
				serializeObjectToResponse(response, result, mappedMethod.getMimeOutputFormat());
			}
		} else {
			response.sendError(400, "No suitable method found for URL '" + extractUrlFromRequest()
					+ "' and HTTP method " + httpMethod);
		}
	}

	/**
	 * Invoked just before a mapped method is invoked to serve the current
	 * request.
	 * 
	 * @param mappedMethod
	 *            the mapped method.
	 * @param attributes
	 *            the current Attributes object.
	 */
	protected void onBeforeMethodInvoked(MethodMappingInfo mappedMethod, Attributes attributes) {
	}

	/**
	 * Invoked just after a mapped method has been invoked to serve the current
	 * request.
	 * 
	 * @param mappedMethod
	 *            the mapped method.
	 * @param attributes
	 *            the current Attributes object.
	 * @param result
	 *            the value returned by the invoked method.
	 */
	protected void onAfterMethodInvoked(MethodMappingInfo mappedMethod, Attributes attributes,
			Object result) {
	}

	/**
	 * Method invoked to serialize the result of the invoked method and write
	 * this value to the response.
	 * 
	 * @param response
	 *            The current response object.
	 * @param result
	 *            The object to write to response.
	 * @param restMimeFormats
	 */
	private void serializeObjectToResponse(WebResponse response, Object result, String mimeType) {
		try {
			response.setContentType(mimeType);
			objSerialDeserial.objectToResponse(result, response, mimeType);
		} catch (Exception e) {
			throw new RuntimeException("Error writing object to response.", e);
		}
	}

	/**
	 * Method invoked to select the most suited method to serve the current
	 * request.
	 * 
	 * @param mappedMethods
	 *            List of {@link MethodMappingInfo} containing the informations
	 *            of mapped methods.
	 * @param pageParameters
	 *            The PageParameters of the current request.
	 * @return The "best" method found to serve the request.
	 */
	private MethodMappingInfo selectMostSuitedMethod(List<MethodMappingInfo> mappedMethods,
			PageParameters pageParameters) {
		int highestScore = 0;
		MultiMap<Integer, MethodMappingInfo> mappedMethodByScore = new MultiMap<Integer, MethodMappingInfo>();

		// no method mapped
		if (mappedMethods == null || mappedMethods.size() == 0)
			return null;

		/**
		 * To select the "best" method, a score is assigned to every mapped
		 * method. To calculate the score method calculateScore is executed for
		 * every segment.
		 */
		for (MethodMappingInfo mappedMethod : mappedMethods) {
			List<AbstractURLSegment> segments = mappedMethod.getSegments();
			int score = 0;

			for (AbstractURLSegment segment : segments) {
				int i = segments.indexOf(segment);
				String currentActualSegment = AbstractURLSegment.getActualSegment(pageParameters
						.get(i).toString());

				int partialScore = segment.calculateScore(currentActualSegment);

				if (partialScore == 0) {
					score = -1;
					break;
				}

				score += partialScore;
			}

			if (score >= highestScore) {
				highestScore = score;
				mappedMethodByScore.addValue(score, mappedMethod);
			}
		}
		// if we have more than one method with the highest score, throw
		// ambiguous exception.
		if (mappedMethodByScore.get(highestScore) != null
				&& mappedMethodByScore.get(highestScore).size() > 1)
			throwAmbiguousMethodsException(mappedMethodByScore.get(highestScore));

		return mappedMethodByScore.getFirstValue(highestScore);
	}

	/**
	 * Throw an exception if two o more methods have the same "score" for the
	 * current request. See method selectMostSuitedMethod.
	 * 
	 * @param list
	 *            the list of ambiguous methods.
	 */
	private void throwAmbiguousMethodsException(List<MethodMappingInfo> list) {
		WebRequest request = (WebRequest) RequestCycle.get().getRequest();
		String methodsNames = "";

		for (MethodMappingInfo urlMappingInfo : list) {
			if (!methodsNames.isEmpty())
				methodsNames += ", ";

			methodsNames += urlMappingInfo.getMethod().getName();
		}

		throw new WicketRuntimeException("Ambiguous methods mapped for the current request: URL '"
				+ request.getClientUrl() + "', HTTP method " + HttpUtils.getHttpMethod(request)
				+ ". " + "Mapped methods: " + methodsNames);
	}

	/**
	 * Method called to initialize and configure the object
	 * serializer/deserializer.
	 * 
	 * @param objSerialDeserial
	 *            the object serializer/deserializer
	 */
	protected void configureObjSerialDeserial(T objSerialDeserial) {
	};

	/***
	 * Internal method to load class methods annotated with
	 * {@link MethodMapping}
	 */
	private void loadAnnotatedMethods() {
		Method[] methods = getClass().getDeclaredMethods();
		boolean isUsingAuthAnnot = false;

		for (int i = 0; i < methods.length; i++) {
			Method method = methods[i];
			MethodMapping methodMapped = method.getAnnotation(MethodMapping.class);
			AuthorizeInvocation authorizeInvocation = method
					.getAnnotation(AuthorizeInvocation.class);

			isUsingAuthAnnot = isUsingAuthAnnot || authorizeInvocation != null;

			if (methodMapped != null) {
				HttpMethod httpMethod = methodMapped.httpMethod();
				MethodMappingInfo urlMappingInfo = new MethodMappingInfo(method,
						getMimeTypeResolver(method));

				if (!isMimeTypesSupported(urlMappingInfo.getMimeInputFormat())
						|| !isMimeTypesSupported(urlMappingInfo.getMimeOutputFormat()))
					throw new WicketRuntimeException(
							"Mapped methods use a MIME type not supported by obj serializer/deserializer!");

				mappedMethods.addValue(
						urlMappingInfo.getSegmentsCount() + "_" + httpMethod.getMethod(),
						urlMappingInfo);
			}
		}
		// if AuthorizeInvocation has been found but no role-checker has been
		// configured, throw an exception
		if (isUsingAuthAnnot && roleCheckingStrategy == null)
			throw new WicketRuntimeException(
					"Annotation AuthorizeInvocation is used but no role-checking strategy has been set for the controller!");
	}

	/**
	 * Checks if the given MIME type is supported by the current obj
	 * serial/deserial.
	 * 
	 * @param mimeType
	 *            the MIME type we want to check.
	 * @return true if the MIME type is supported, false otherwise.
	 */
	private boolean isMimeTypesSupported(String mimeType) {
		if (RestMimeTypes.TEXT_PLAIN.equals(mimeType))
			return true;

		return objSerialDeserial.isMimeTypeSupported(mimeType);
	}

	/***
	 * Invokes one of the resource methods annotated with {@link MethodMapping}.
	 * 
	 * @param mappedMethod
	 *            mapping info of the method.
	 * @param attributes
	 *            Attributes object for the current request.
	 * @return the value returned by the invoked method
	 */
	private Object invokeMappedMethod(MethodMappingInfo mappedMethod, Attributes attributes) {

		Method method = mappedMethod.getMethod();
		List parametersValues = new ArrayList();

		// Attributes objects
		PageParameters pageParameters = attributes.getParameters();
		WebResponse response = (WebResponse) attributes.getResponse();
		HttpMethod httpMethod = HttpUtils.getHttpMethod((WebRequest) RequestCycle.get()
				.getRequest());

		LinkedHashMap<String, String> pathParameters = mappedMethod
				.populatePathParameters(pageParameters);
		Iterator<String> pathParamsIterator = pathParameters.values().iterator();
		Class<?>[] parameterTypes = method.getParameterTypes();

		for (int i = 0; i < parameterTypes.length; i++) {
			Object paramValue = null;
			MethodParameter methodParameter = new MethodParameter(parameterTypes[i], mappedMethod,
					i);
			Annotation annotation = ReflectionUtils.getAnnotationParam(i, method);
			// retrieve parameter value
			if (annotation != null)
				paramValue = extractParameterValue(methodParameter, pathParameters, annotation,
						pageParameters);
			else
				paramValue = extractParameterFromUrl(methodParameter, pathParamsIterator);
			// try to use the default value
			if (paramValue == null && !methodParameter.getDeaultValue().isEmpty())
				paramValue = toObject(methodParameter.getParameterClass(),
						methodParameter.getDeaultValue());

			if (paramValue == null && methodParameter.isRequired()) {
				response.sendError(400, "No suitable method found for URL '"
						+ extractUrlFromRequest() + "' and HTTP method " + httpMethod);
				return null;
			}

			parametersValues.add(paramValue);
		}

		try {
			return method.invoke(this, parametersValues.toArray());
		} catch (Exception e) {
			response.sendError(500, "General server error.");
			throw new RuntimeException("Error invoking method '" + method.getName() + "'", e);
		}
	}

	/**
	 * Utility method to extract the client URL from the current request.
	 * 
	 * @return the URL for the current request.
	 */
	static public Url extractUrlFromRequest() {
		return RequestCycle.get().getRequest().getClientUrl();
	}

	/**
	 * Extract the value for an annotated-method parameter (see package
	 * {@link org.wicketstuff.rest.annotations.parameters}).
	 * 
	 * @param methodParameter
	 *            the current method parameter.
	 * @param pathParameters
	 *            the values of path parameters for the current request.
	 * @param annotation
	 *            the annotation for the current parameter that indicates how to
	 *            retrieve the value for the current parameter.
	 * @param pageParameters
	 *            PageParameters for the current request.
	 * @return the extracted value.
	 */
	private Object extractParameterValue(MethodParameter methodParameter,
			LinkedHashMap<String, String> pathParameters, Annotation annotation,
			PageParameters pageParameters) {
		Object paramValue = null;
		Class<?> argClass = methodParameter.getParameterClass();
		String mimeInputFormat = methodParameter.getOwnerMethod().getMimeInputFormat();

		if (annotation instanceof RequestBody)
			paramValue = deserializeObjectFromRequest(argClass, mimeInputFormat);
		else if (annotation instanceof PathParam)
			paramValue = toObject(argClass, pathParameters.get(((PathParam) annotation).value()));
		else if (annotation instanceof RequestParam)
			paramValue = extractParameterFromQuery(pageParameters, (RequestParam) annotation,
					argClass);
		else if (annotation instanceof HeaderParam)
			paramValue = extractParameterFromHeader((HeaderParam) annotation, argClass);
		else if (annotation instanceof CookieParam)
			paramValue = extractParameterFromCookies((CookieParam) annotation, argClass);
		else if (annotation instanceof MatrixParam)
			paramValue = extractParameterFromMatrixParams(pageParameters, (MatrixParam) annotation,
					argClass);

		return paramValue;
	}

	/**
	 * Extract method parameter value from matrix parameters.
	 * 
	 * @param pageParameters
	 *            PageParameters for the current request.
	 * @param matrixParam
	 *            the {@link MatrixParam} annotation used for the current
	 *            parameter.
	 * @param argClass
	 *            the type of the current method parameter.
	 * @return the value obtained from query parameters and converted to
	 *         argClass.
	 */
	private Object extractParameterFromMatrixParams(PageParameters pageParameters,
			MatrixParam matrixParam, Class<?> argClass) {
		int segmentIndex = matrixParam.segmentIndex();
		String variableName = matrixParam.parameterName();
		String rawsSegment = pageParameters.get(segmentIndex).toString();
		Map<String, String> matrixParameters = AbstractURLSegment
				.getSegmentMatrixParameters(rawsSegment);

		if (matrixParameters.get(variableName) == null)
			return null;

		return toObject(argClass, matrixParameters.get(variableName));
	}

	/**
	 * Extract method parameter value from request header.
	 * 
	 * @param headerParam
	 *            the {@link HeaderParam} annotation used for the current method
	 *            parameter.
	 * @param argClass
	 *            the type of the current method parameter.
	 * @return the extracted value converted to argClass.
	 */
	private Object extractParameterFromHeader(HeaderParam headerParam, Class<?> argClass) {
		String value = headerParam.value();
		WebRequest webRequest = (WebRequest) RequestCycle.get().getRequest();

		return toObject(argClass, webRequest.getHeader(value));
	}

	/**
	 * Extract method parameter's value from query string parameters.
	 * 
	 * @param pageParameters
	 *            the PageParameters of the current request.
	 * @param requestParam
	 *            the {@link RequestParam} annotation used for the current
	 *            method parameter.
	 * @param argClass
	 *            the type of the current method parameter.
	 * @return the extracted value converted to argClass.
	 */
	private Object extractParameterFromQuery(PageParameters pageParameters,
			RequestParam requestParam, Class<?> argClass) {

		String value = requestParam.value();

		if (pageParameters.get(value) == null)
			return null;

		return toObject(argClass, pageParameters.get(value).toString());
	}

	/**
	 * Extract method parameter's value from cookies.
	 * 
	 * @param annotation
	 *            the {@link CookieParam} annotation used for the current method
	 *            parameter.
	 * @param argClass
	 *            the type of the current method parameter.
	 * @return the extracted value converted to argClass.
	 */
	private Object extractParameterFromCookies(CookieParam cookieParam, Class<?> argClass) {
		String value = cookieParam.value();
		WebRequest webRequest = (WebRequest) RequestCycle.get().getRequest();

		if (webRequest.getCookie(value) == null)
			return null;

		return toObject(argClass, webRequest.getCookie(value).getValue());
	}

	/**
	 * Internal method that tries to extract an instance of the given class from
	 * the request body.
	 * 
	 * @param argClass
	 *            the type we want to extract from request body.
	 * @return the extracted object.
	 */
	private Object deserializeObjectFromRequest(Class<?> argClass, String mimeType) {
		WebRequest servletRequest = (WebRequest) RequestCycle.get().getRequest();
		try {
			return objSerialDeserial.requestToObject(servletRequest, argClass, mimeType);
		} catch (Exception e) {
			throw new RuntimeException("Error deserializing object from request", e);
		}
	}

	/***
	 * Extract a parameter values from the rest URL.
	 * 
	 * @param methodParameter
	 *            the current method parameter.
	 * @param pathParamIterator
	 *            an iterator on the current values of path parameters.
	 * 
	 * @return the parameter value.
	 */
	private Object extractParameterFromUrl(MethodParameter methodParameter,
			Iterator<String> pathParamIterator) {

		if (!pathParamIterator.hasNext())
			return null;

		return toObject(methodParameter.getParameterClass(), pathParamIterator.next());
	}

	/**
	 * Utility method to convert string values to the corresponding objects.
	 * 
	 * @param clazz
	 *            the type of the object we want to obtain.
	 * @param value
	 *            the string value we want to convert.
	 * @return the object corresponding to the converted string value, or null
	 *         if value parameter is null
	 */
	public static Object toObject(Class clazz, String value) throws IllegalArgumentException {
		if (value == null)
			return null;
		// we use the standard Wicket conversion mechanism to obtain the
		// converted value.
		try {
			IConverter converter = Application.get().getConverterLocator().getConverter(clazz);

			return converter.convertToObject(value, Session.get().getLocale());
		} catch (Exception e) {
			WebResponse response = (WebResponse) RequestCycle.get().getResponse();

			response.setStatus(400);
			response.write("Could not find a suitable constructor for value '" + value
					+ "' of type '" + clazz + "'");

			return null;
		}
	}

	/**
	 * Return an instance of {@link IMimeTypeResolver} that must be used as MIME
	 * type resolver.
	 * 
	 * @param method
	 *            the current mapped method.
	 * @return the MIME type resolver to use.
	 */
	protected IMimeTypeResolver getMimeTypeResolver(Method method) {
		MethodMapping methodMapped = method.getAnnotation(MethodMapping.class);

		return new DefaultMimeTypeResolver(methodMapped);
	}

	/**
	 * Utility method to check that the user owns one of the roles provided in
	 * input.
	 * 
	 * @param roles
	 *            the checked roles.
	 * @return true if the user owns one of roles in input, false otherwise.
	 */
	protected final boolean hasAny(Roles roles) {
		if (roles.isEmpty()) {
			return true;
		} else {
			return roleCheckingStrategy.hasAnyRole(roles);
		}
	}
}
