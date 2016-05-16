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

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
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
import org.apache.wicket.util.convert.ConversionException;
import org.apache.wicket.util.convert.IConverter;
import org.apache.wicket.util.lang.Args;
import org.apache.wicket.util.string.Strings;
import org.apache.wicket.validation.IErrorMessageSource;
import org.apache.wicket.validation.IValidationError;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.Validatable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.rest.annotations.AuthorizeInvocation;
import org.wicketstuff.rest.annotations.MethodMapping;
import org.wicketstuff.rest.contenthandling.IWebSerialDeserial;
import org.wicketstuff.rest.resource.urlsegments.AbstractURLSegment;
import org.wicketstuff.rest.resource.urlsegments.visitor.ScoreMethodAndExtractPathVars;
import org.wicketstuff.rest.utils.collection.CollectionUtils;
import org.wicketstuff.rest.utils.http.HttpMethod;
import org.wicketstuff.rest.utils.http.HttpUtils;
import org.wicketstuff.rest.utils.reflection.MethodParameter;
import org.wicketstuff.rest.utils.reflection.ReflectionUtils;
import org.wicketstuff.rest.utils.wicket.AttributesWrapper;
import org.wicketstuff.rest.utils.wicket.MethodParameterContext;
import org.wicketstuff.rest.utils.wicket.bundle.DefaultBundleResolver;

/**
 * Base class to build a resource that serves REST requests.
 *
 * @author andrea del bene
 *
 */
public abstract class AbstractRestResource<T extends IWebSerialDeserial> implements IResource
{
	private static final long serialVersionUID = 1L;

	public static final String NO_SUITABLE_METHOD_FOUND = "No suitable method found.";

	public static final String USER_IS_NOT_ALLOWED = "User is not allowed to use this resource.";

	private static final Logger log = LoggerFactory.getLogger(AbstractRestResource.class);

	/**
	 * HashMap that stores every mapped method of the class. Mapped method are stored concatenating
	 * the number of the segments of their URL and their HTTP method (see annotation MethodMapping)
	 */
	private final Map<String, List<MethodMappingInfo>> mappedMethods;

	/**
	 * Another HashMap that stores every mapped method of the class.
	 * The key of the map is {@link Method}.
	 */
	private final Map<Method, MethodMappingInfo> mappedMethodsInfo;

	/**
	 * HashMap that stores the validators registered by the resource.
	 */
	private final Map<String, IValidator<?>> declaredValidators = new HashMap<>();

	/**
	 * The implementation of {@link IWebSerialDeserial} that is used to serialize/desiarilze objects
	 * to/from string (for example to/from JSON)
	 */
	private final T webSerialDeserial;

	/** Role-checking strategy. */
	private final IRoleCheckingStrategy roleCheckingStrategy;

	/** Bundle resolver */
	private final IErrorMessageSource bundleResolver;

	/**
	 * Constructor with no role-checker (i.e we don't use annotation {@link AuthorizeInvocation}).
	 *
	 * @param serialDeserial
	 *            General class that is used to serialize/desiarilze objects to string.
	 */
	public AbstractRestResource(T serialDeserial)
	{
		this(serialDeserial, null);
	}

	/**
	 * Main constructor that takes in input the object serializer/deserializer and the role-checking
	 * strategy to use.
	 *
	 * @param serialDeserial
	 *            General class that is used to serialize/desiarilze objects to string
	 * @param roleCheckingStrategy
	 *            the role-checking strategy.
	 */
	public AbstractRestResource(T serialDeserial, IRoleCheckingStrategy roleCheckingStrategy)
	{
		Args.notNull(serialDeserial, "serialDeserial");

		onInitialize(serialDeserial);

		this.webSerialDeserial = serialDeserial;
		this.roleCheckingStrategy = roleCheckingStrategy;
		this.mappedMethods = loadAnnotatedMethods();
		this.mappedMethodsInfo = loadAnnotatedMethodsInfo();
		this.bundleResolver = new DefaultBundleResolver(loadBoundleClasses());
	}

	/**
	 * Build a list of classes to use to search for a valid bundle. This list is
	 * made of the classes of the validators registered with abstractResource
	 * and of the class of the abstractResource.
	 *
	 * @param abstractResource
	 *            the abstract REST resource that is using the validator
	 * @return the list of the classes to use.
	 */
	private List<Class<?>> loadBoundleClasses()
	{
		Collection<IValidator<?>> validators = declaredValidators.values();
		List<Class<?>> validatorsClasses = ReflectionUtils.getElementsClasses(validators);

		validatorsClasses.add(this.getClass());

		return validatorsClasses;
	}

	/***
	 * Handles a REST request invoking one of the methods annotated with {@link MethodMapping}. If
	 * the annotated method returns a value, this latter is automatically serialized to a given
	 * string format (like JSON, XML, etc...) and written to the web response.<br/>
	 * If no method is found to serve the current request, a 400 HTTP code is returned to the
	 * client. Similarly, a 401 HTTP code is return if the user doesn't own one of the roles
	 * required to execute an annotated method (See {@link AuthorizeInvocation}).
	 *
	 * @param attributes
	 *            the Attribute object of the current request
	 */
	@Override
	public final void respond(Attributes attributes)
	{
		AttributesWrapper attributesWrapper = new AttributesWrapper(attributes);
		WebResponse response = attributesWrapper.getWebResponse();
		HttpMethod httpMethod = attributesWrapper.getHttpMethod();

		// select the best "candidate" method to serve the request
		ScoreMethodAndExtractPathVars mappedMethod = selectMostSuitedMethod(attributesWrapper);

		if (mappedMethod != null)
		{
			handleMethodExecution(attributesWrapper, mappedMethod);
		}
		else
		{
			noSuitableMethodFound(response, httpMethod);
		}
	}

	/**
	 * Handle the different steps (authorization, validation, etc...) involved in method execution.
	 *
	 * @param attributesWrapper
	 *            wrapper for the current Attributes
	 * @param mappedMethod
	 *            the mapped method to execute
	 */
	private void handleMethodExecution(AttributesWrapper attributesWrapper,
		ScoreMethodAndExtractPathVars mappedMethod)
	{
		WebResponse response = attributesWrapper.getWebResponse();
		MethodMappingInfo methodInfo = mappedMethod.getMethodInfo();
		Attributes attributes = attributesWrapper.getOriginalAttributes();
		String outputFormat = methodInfo.getOutputFormat();

		// 1-check if user is authorized to invoke the method
		if (!isUserAuthorized(methodInfo.getRoles()))
		{
			unauthorizedMethodAccess(response, methodInfo);
			return;
		}

		// 2-extract method parameters
		List<?> parametersValues = null;

		try
		{
			parametersValues = extractMethodParameters(mappedMethod, attributesWrapper);
		}
		catch (RuntimeException e)
		{
			setResponseStatusCode(400);
			return;
		}

		// 3-validate method parameters
		List<IValidationError> validationErrors = validateMethodParameters(methodInfo,
			parametersValues);

		if (validationErrors.size() > 0)
		{
			IValidationError error = validationErrors.get(0);
			Serializable message = error.getErrorMessage(bundleResolver);

			webSerialDeserial.objectToResponse(message, response, outputFormat);
			response.setStatus(400);

			return;
		}

		// 4-invoke method triggering the before-after hooks
		onBeforeMethodInvoked(methodInfo, attributes);
		Object result = invokeMappedMethod(methodInfo.getMethod(), parametersValues, response);
		onAfterMethodInvoked(methodInfo, attributes, result);

		// 5-set response content type
		response.setContentType(outputFormat);

		// 6-if the invoked method returns a value, it is written to response
		if (result != null)
		{
			objectToResponse(result, response, outputFormat);
		}

	}


	/**
	 * Check if user is allowed to run a method annotated with {@link AuthorizeInvocation}
	 *
	 * @param roles
	 *            the user roles
	 * @return true if user is allowed, else otherwise
	 */
	private boolean isUserAuthorized(Roles roles)
	{
		if (roles.isEmpty())
		{
			return true;
		}
		else
		{
			return roleCheckingStrategy.hasAnyRole(roles);
		}
	}

	/**
	 * Handle unauthorized user access. Default: set response Status to 401. Override this method to implement
	 * customized handling
	 * @param response Response-Object
	 * @param methodInfo The mapped method
	 */
	protected void unauthorizedMethodAccess(WebResponse response, MethodMappingInfo methodInfo)
	{
		response.write(USER_IS_NOT_ALLOWED);
		response.setStatus(401);
	}

	/**
	 * This method can be used to write a standard error message to the current response object when
	 * no mapped method has been found for the current request.
	 *
	 * @param response
	 *            the current response object
	 * @param httpMethod
	 *            the HTTP method of the current request
	 */
	public void noSuitableMethodFound(WebResponse response, HttpMethod httpMethod)
	{
		response.setStatus(400);
		response.write(NO_SUITABLE_METHOD_FOUND + " URL '" + extractUrlFromRequest() +
			"' and HTTP method " + httpMethod);
	}

	/**
	 * Validate parameter values of the mapped method we want to execute.
	 *
	 * @param mappedMethod
	 *            the target mapped methos
	 * @param parametersValues
	 *            the parameter values
	 * @return the list of validation errors, it is empty if validation succeeds
	 */
	private List<IValidationError> validateMethodParameters(MethodMappingInfo mappedMethod,
		List<?> parametersValues)
	{
		List<MethodParameter<?>> methodParameters = mappedMethod.getMethodParameters();
		List<IValidationError> errors = new ArrayList<IValidationError>();

		for (MethodParameter<?> methodParameter : methodParameters)
		{
			String validatorKey = methodParameter.getValdatorKey();

			if (!Strings.isEmpty(validatorKey))
			{
				int i = methodParameters.indexOf(methodParameter);
				Object parameterValue = parametersValues.get(i);

				validateMethodParameter(errors, validatorKey,
					parameterValue);
			}
		}

		return errors;
	}

	/**
	 * Validate a single parameter value of the mapped method we want to execute.
	 *
	 * @param errors
	 * 	the list of validation errors
	 * @param validatorKey
	 * 	the key for the current validator
	 * @param parameterValue
	 * 	the value for the current parameter
	 */
	private <E> void validateMethodParameter(List<IValidationError> errors,
		String validatorKey, E parameterValue)
	{
		IValidator<E> validator = getValidator(validatorKey, parameterValue);
		Validatable<E> validatable = new Validatable<>(parameterValue);

		if (validator != null)
		{
			validator.validate(validatable);
			errors.addAll(validatable.getErrors());
		}
		else
		{
			log.debug("No validator found for key '" + validatorKey + "'");
		}
	}

	/**
	 * Invoked just before a mapped method is invoked to serve the current request.
	 *
	 * @param mappedMethod
	 *            the mapped method.
	 * @param attributes
	 *            the current Attributes object.
	 */
	protected void onBeforeMethodInvoked(MethodMappingInfo mappedMethod, Attributes attributes)
	{
	}

	/**
	 * Invoked just after a mapped method has been invoked to serve the current request.
	 *
	 * @param mappedMethod
	 *            the mapped method.
	 * @param attributes
	 *            the current Attributes object.
	 * @param result
	 *            the value returned by the invoked method.
	 */
	protected void onAfterMethodInvoked(MethodMappingInfo mappedMethod, Attributes attributes,
		Object result)
	{
	}

	/**
	 * Method invoked to serialize the result of the invoked method and write this value to the
	 * response.
	 *
	 * @param response
	 *            The current response object.
	 * @param result
	 *            The object to write to response.
	 * @param restMimeFormats
	 * 			  The MIME type to use to serialize data
	 */
	public void objectToResponse(Object result, WebResponse response, String mimeType)
	{
		try
		{
			webSerialDeserial.objectToResponse(result, response, mimeType);
		}
		catch (Exception e)
		{
			throw new RuntimeException("Error writing object to response.", e);
		}
	}

	/**
	 * Method invoked to select the most suited method to serve the current request.
	 *
	 * @param attributesWrapper
	 * 			the current attribute wrapper
	 * @return The "best" method found to serve the request.
	 */
	private ScoreMethodAndExtractPathVars selectMostSuitedMethod(AttributesWrapper attributesWrapper)
	{
		PageParameters pageParameters = attributesWrapper.getPageParameters();
		List<MethodMappingInfo> mappedMethodsCandidates = mappedMethods.get(pageParameters.getIndexedCount() +
			"_" + attributesWrapper.getHttpMethod());

		MultiMap<Integer, ScoreMethodAndExtractPathVars> scoredMethods = new MultiMap<>();

		// no method mapped
		if (mappedMethodsCandidates == null || mappedMethodsCandidates.size() == 0)
			return null;

		/**
		 * To select the "best" method, a score is assigned to every mapped method. To calculate the
		 * score method calculateScore is executed for every segment.
		 */
		int highestScore = 0;

		for (MethodMappingInfo mappedMethod : mappedMethodsCandidates)
		{
			ScoreMethodAndExtractPathVars scoredMethod =
				new ScoreMethodAndExtractPathVars(mappedMethod, pageParameters);

			for (AbstractURLSegment segment : mappedMethod.getSegments())
			{
				segment.accept(scoredMethod);

				if(!scoredMethod.isSegmentValid())
				{
					break;
				}
			}

			int methodScore = scoredMethod.getScore();
			
			if (methodScore >= highestScore)
			{
				highestScore = methodScore;
				scoredMethods.addValue(highestScore, scoredMethod);
			}
		}

		List<ScoreMethodAndExtractPathVars> methodsWithScore 
			= scoredMethods.get(highestScore);
		
		if(methodsWithScore != null && methodsWithScore.size() > 1)
		{
			// if we have more than one method with the highest score, throw
			// ambiguous exception.
			throwAmbiguousMethodsException(scoredMethods.getFirstValue(highestScore));
		}
		
		return scoredMethods.getFirstValue(highestScore);
	}

	/**
	 * Throw an exception if two o more methods have the same "score" for the current request. See
	 * method selectMostSuitedMethod.
	 *
	 * @param list
	 *            the list of ambiguous methods.
	 */
	private void throwAmbiguousMethodsException(ScoreMethodAndExtractPathVars... methods)
	{
		WebRequest request = getCurrentWebRequest();
		String methodsNames = "";

		for (ScoreMethodAndExtractPathVars method : methods)
		{
			if (!methodsNames.isEmpty())
				methodsNames += ", ";

			MethodMappingInfo urlMappingInfo = method.getMethodInfo();
			methodsNames += urlMappingInfo.getMethod().getName();
		}

		throw new WicketRuntimeException("Ambiguous methods mapped for the current request: URL '" +
			request.getClientUrl() + "', HTTP method " + HttpUtils.getHttpMethod(request) + ". " +
			"Mapped methods: " + methodsNames);
	}

	/**
	 * Method called to initialize and configure the resource.
	 *
	 * @param objSerialDeserial
	 *            the object serializer/deserializer
	 */
	protected void onInitialize(T objSerialDeserial)
	{
	}

	/***
	 * Internal method to load class methods annotated with {@link MethodMapping}
	 *
	 * @return
	 * 		a map object contained annotated method. Mapped method are stored concatenating
	 * 		the number of the segments of their URL and their HTTP method (see annotation MethodMapping)
	 */
	private Map<String, List<MethodMappingInfo>> loadAnnotatedMethods()
	{
		Method[] methods = getClass().getDeclaredMethods();
		MultiMap<String, MethodMappingInfo> mappedMethods = new MultiMap<String, MethodMappingInfo>();
		boolean isUsingAuthAnnot = false;

		for (int i = 0; i < methods.length; i++)
		{
			Method method = methods[i];
			MethodMapping methodMapped = method.getAnnotation(MethodMapping.class);
			AuthorizeInvocation authorizeInvocation = method.getAnnotation(AuthorizeInvocation.class);

			isUsingAuthAnnot = isUsingAuthAnnot || authorizeInvocation != null;

			if (methodMapped != null)
			{
				HttpMethod httpMethod = methodMapped.httpMethod();
				MethodMappingInfo methodMappingInfo = new MethodMappingInfo(methodMapped, method);

				if (!webSerialDeserial.isMimeTypeSupported(methodMappingInfo.getInputFormat()) ||
					!webSerialDeserial.isMimeTypeSupported(methodMappingInfo.getOutputFormat()))
					throw new WicketRuntimeException(
						"Mapped methods use a MIME type not supported by obj serializer/deserializer!");

				mappedMethods.addValue(
					methodMappingInfo.getSegmentsCount() + "_" + httpMethod.getMethod(),
					methodMappingInfo);
			}
		}
		// if AuthorizeInvocation has been found but no role-checker has been
		// configured, throw an exception
		if (isUsingAuthAnnot && roleCheckingStrategy == null)
			throw new WicketRuntimeException(
				"Annotation AuthorizeInvocation is used but no role-checking strategy has been set for the controller!");

		return CollectionUtils.makeListMapImmutable(mappedMethods);
	}

	private Map<Method, MethodMappingInfo> loadAnnotatedMethodsInfo()
	{
		Map<Method, MethodMappingInfo> methodsInfo = new HashMap<Method, MethodMappingInfo>();

		for (List<MethodMappingInfo> methodInfoList : mappedMethods.values())
		{
			for (MethodMappingInfo methodMappedInfo : methodInfoList)
			{
				methodsInfo.put(methodMappedInfo.getMethod(), methodMappedInfo);
			}
		}

		return Collections.unmodifiableMap(methodsInfo);
	}

	/***
	 * Invokes one of the resource methods annotated with {@link MethodMapping}.
	 *
	 * @param mappedMethod
	 *            mapping info of the method.
	 * @param attributesWrapper
	 *            Attributes wrapper for the current request.
	 * @return the value returned by the invoked method
	 */
	private List<?> extractMethodParameters(ScoreMethodAndExtractPathVars mappedMethod,
		AttributesWrapper attributesWrapper)
	{
		List<Object> parametersValues = new ArrayList<>();

		Map<String, String> pathParameters = mappedMethod.getPathVariables();
		MethodParameterContext parameterContext = new MethodParameterContext(attributesWrapper,
			pathParameters, webSerialDeserial);

		for (MethodParameter<?> methodParameter : mappedMethod.getMethodInfo().getMethodParameters())
		{
			Object paramValue = methodParameter.extractParameterValue(parameterContext);

			// if parameter is null and is required, abort extraction.
			if (paramValue == null && methodParameter.isRequired())
			{
				String exMsg = String.format("No valid value could be found the given method parameter: method %s, parameter index %i",
					mappedMethod.getMethodInfo().getMethod().getName(), methodParameter.getParamIndex());
				log.debug(exMsg);
				throw new WicketRuntimeException(exMsg);
			}

			parametersValues.add(paramValue);
		}

		return parametersValues;
	}

	/**
	 * Execute a method implemented in the current resource class
	 *
	 * @param method
	 *            the method that must be executed.
	 * @param parametersValues
	 *            method parameters
	 * @param response
	 *            the current WebResponse object.
	 * @return the value (if any) returned by the method.
	 */
	private Object invokeMappedMethod(Method method, List<?> parametersValues, WebResponse response)
	{
		try
		{
			return method.invoke(this, parametersValues.toArray());
		}
		catch (Exception exception)
		{
			handleException(response, exception);

			log.debug("Error invoking method '" + method.getName() + "'");
		}

		return null;
	}

	/**
	 * Handle Exception. Default: set response Status to 500. Override this method to implement
	 * customized error handling
	 * @param exception The Exception
	 * @param response Response-Object
	 */
	protected void handleException(WebResponse response, Exception exception)
	{
		response.setStatus(500);
		response.write("General server error.");
	}

	/**
	 * Utility method to extract the client URL from the current request.
	 *
	 * @return the URL for the current request.
	 */
	static public Url extractUrlFromRequest()
	{
		return RequestCycle.get().getRequest().getClientUrl();
	}

	/**
	 * Internal method that tries to extract an instance of the given class from the request body.
	 *
	 * @param argClass
	 *            the type we want to extract from request body.
	 * @return the extracted object.
	 */
	public <E> E requestToObject(WebRequest request, Class<E> argClass, String mimeType)
	{
		try
		{
			return webSerialDeserial.requestToObject(request, argClass, mimeType);
		}
		catch (Exception e)
		{
			log.debug("Error deserializing object from request");
			return null;
		}
	}

	/**
	 * Utility method to retrieve the current web request.
	 *
	 * @return
	 * 		the current web request
	 */
	public static final WebRequest getCurrentWebRequest()
	{
		return (WebRequest)RequestCycle.get().getRequest();
	}

	/**
	 * Utility method to convert string values to the corresponding objects.
	 *
	 * @param clazz
	 *            the type of the object we want to obtain.
	 * @param value
	 *            the string value we want to convert.
	 * @return the object corresponding to the converted string value, or null if value parameter is
	 *         null
	 */
	public static Object toObject(Class<?> clazz, String value) throws RuntimeException
	{
		if (value == null)
		{
			return null;
		}

		// we use the standard Wicket conversion mechanism to obtain the
		// converted value.
		IConverter<?> converter = Application.get().getConverterLocator().getConverter(clazz);

		if (converter == null)
		{
			String exMsg = String.format("Could not find a suitable converter for value '%s' of type '%s'",
				value, clazz.getName());
			log.debug(exMsg);

			throw new WicketRuntimeException(exMsg);
		}

		try
		{
			return converter.convertToObject(value, Session.get().getLocale());
		}
		catch (ConversionException exception)
		{
			String exMsg = String.format("Value '%s' can not be converted to type '%s'",
				value, clazz.getName());

			log.debug(exMsg);

			throw new WicketRuntimeException(exMsg, exception);
		}
	}

	/**
	 * Utility method to retrieve the current web response.
	 *
	 * @return
	 * 		the current web response
	 */
	public static final WebResponse getCurrentWebResponse()
	{
		return (WebResponse)RequestCycle.get().getResponse();
	}

	/**
	 * Set the status code for the current response.
	 *
	 * @param statusCode
	 *            the status code we want to set on the current response.
	 */
	protected final void setResponseStatusCode(int statusCode)
	{
		try
		{
			getCurrentWebResponse().setStatus(statusCode);
		}
		catch (Exception e)
		{
			throw new IllegalStateException(
				"Could not find a suitable WebResponse object for the current ThreadContext.", e);
		}
	}

	/**
	 * Return mapped methods grouped by number of segments and HTTP method. So for example, to get
	 * all methods mapped on a path with three segments and with GET method, the key to use will be
	 * "3_GET" (underscore-separated)
	 *
	 * @return the immutable map containing mapped methods.
	 */
	protected Map<String, List<MethodMappingInfo>> getMappedMethods()
	{
		return mappedMethods;
	}

	/**
	 * Register a Wicket validator for the current resource.
	 *
	 * @param key
	 * 		the key to use to store the validator.
	 * @param validator
	 * 		the validator to register
	 */
	protected final void registerValidator(String key, IValidator<?> validator)
	{
		declaredValidators.put(key, validator);
	}

	/**
	 * Unregister a Wicket validator.
	 *
	 * @param key
	 * 		the key to use to remove the validator.
	 */
	protected final void unregisterValidator(String key)
	{
		declaredValidators.remove(key);
	}

	/**
	 * Retrieve a registered validator.
	 *
	 * @param key
	 * 		the key to use to retrieve the validator.
	 * @return the registered validator corresponding to the given key.
	 *         Null if no validator has been registered with the given key.
	 *
	 */
	@SuppressWarnings("unchecked")
	protected final <E> IValidator<E> getValidator(String key, E validatorType)
	{
		return (IValidator<E>) declaredValidators.get(key);
	}

	public Map<Method, MethodMappingInfo> getMappedMethodsInfo()
	{
		return mappedMethodsInfo;
	}

	public MethodMappingInfo getMethodInfo(Method method)
	{
		return mappedMethodsInfo.get(method);
	}
}
