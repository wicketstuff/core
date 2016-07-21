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
package org.wicketstuff.rest.utils.reflection;

import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.wicket.request.http.WebRequest;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.lang.Args;
import org.wicketstuff.rest.annotations.parameters.CookieParam;
import org.wicketstuff.rest.annotations.parameters.HeaderParam;
import org.wicketstuff.rest.annotations.parameters.MatrixParam;
import org.wicketstuff.rest.annotations.parameters.PathParam;
import org.wicketstuff.rest.annotations.parameters.RequestBody;
import org.wicketstuff.rest.annotations.parameters.RequestParam;
import org.wicketstuff.rest.annotations.parameters.ValidatorKey;
import org.wicketstuff.rest.contenthandling.IWebSerialDeserial;
import org.wicketstuff.rest.resource.AbstractRestResource;
import org.wicketstuff.rest.resource.MethodMappingInfo;
import org.wicketstuff.rest.resource.urlsegments.AbstractURLSegment;
import org.wicketstuff.rest.utils.wicket.MethodParameterContext;

/**
 * The class contains the informations of a method parameter, like its type or its index in the
 * array of method parameters.
 *
 * @param <T> the generic type
 * @author andrea del bene
 */
public class MethodParameter<T>
{

	/** The parameter class. */
	private final Class<? extends T> parameterClass;

	/** The owner method. */
	private final MethodMappingInfo ownerMethod;

	/** The param index. */
	private final int paramIndex;

	/** Indicates if the parameter is required or not. */
	private final boolean required;

	/** Default value of the method parameter. */
	private final String deaultValue;

	/** Validator key for the parameter. */
	private final String valdatorKey;

	/** The annotation used to indicate how the value of the parameter must be retrieved. */
	private final Annotation annotationParam;

	/**
	 * Instantiates a new method parameter.
	 *
	 * @param type
	 *            the type of the parameter.
	 * @param ownerMethod
	 *            the owner method for the parameter.
	 * @param paramIndex
	 *            the index of the parameter in the array of method's parameters.
	 */
	public MethodParameter(Class<? extends T> type, MethodMappingInfo ownerMethod, int paramIndex)
	{
		Args.notNull(type, "type");
		Args.notNull(ownerMethod, "ownerMethod");

		this.parameterClass = type;
		this.ownerMethod = ownerMethod;
		this.paramIndex = paramIndex;

		this.annotationParam = ReflectionUtils.getAnnotationParam(paramIndex, ownerMethod.getMethod());

		this.required = ReflectionUtils.getAnnotationField(annotationParam, "required", true);
		this.deaultValue = ReflectionUtils.getAnnotationField(annotationParam, "defaultValue", "");

		ValidatorKey validatorAnnotation = ReflectionUtils.findMethodParameterAnnotation(
			ownerMethod.getMethod(), paramIndex, ValidatorKey.class);

		this.valdatorKey = ReflectionUtils.getAnnotationField(validatorAnnotation, "value", "");

	}

	/**
	 * Extract parameter value from the current web request or other web entities (cookies, request header, etc...).
	 *
	 * @param context the context
	 * @return the object
	 */
	public Object extractParameterValue(MethodParameterContext context)
	{
		Object paramValue = null;

		if (annotationParam == null)
			paramValue = extractParameterFromUrl(context);
		else
			paramValue = extractParameterFromAnnotation(context);

		// try to use the default value
		if (paramValue == null && !deaultValue.isEmpty())
			paramValue = AbstractRestResource.toObject(parameterClass, deaultValue);

		return paramValue;
	}

	/**
	 * *
	 * Extract a parameter values from the REST URL.
	 *
	 * @param
	 * 		context the current context.
	 * @return the parameter value.
	 */
	private Object extractParameterFromUrl(MethodParameterContext context)
	{
		Map<String, String> parameters = context.getPathParameters();
		Iterator<String> paramIterator = parameters.values().iterator();
		List<MethodParameter<?>> methodParameters = ownerMethod.getMethodParameters();

		for (int i = 0; i < paramIndex; i++)
		{
			MethodParameter<?> parameter = methodParameters.get(i);

			if(parameter.getAnnotationParam() == null)
				paramIterator.next();
		}

		if(paramIterator.hasNext())
		{
			return AbstractRestResource.toObject(parameterClass, paramIterator.next());
		}

		return null;
	}

	/**
	 * Extract the value for an annotated-method parameter (see package.
	 *
	 * @param context
	 * 		the current context.
	 * @return the extracted value.
	 * {@link org.wicketstuff.rest.annotations.parameters}).
	 */
	private Object extractParameterFromAnnotation(MethodParameterContext context)
	{
		Object paramValue = null;

		String mimeInputFormat = ownerMethod.getInputFormat();
		PageParameters pageParameters = context.getAttributesWrapper().getPageParameters();

		if (annotationParam instanceof RequestBody)
		{
			paramValue = deserializeObjectFromRequest(mimeInputFormat, context.getSerialDeserial());
		}
		else if (annotationParam instanceof PathParam)
		{
			paramValue = AbstractRestResource.toObject(parameterClass,
				context.getPathParameters().get(((PathParam)annotationParam).value()));
		}
		else if (annotationParam instanceof RequestParam)
		{
			paramValue = extractParameterFromQuery(pageParameters, (RequestParam)annotationParam);
		}
		else if (annotationParam instanceof HeaderParam)
		{
			paramValue = extractParameterFromHeader((HeaderParam)annotationParam);
		}
		else if (annotationParam instanceof CookieParam)
		{
			paramValue = extractParameterFromCookies((CookieParam)annotationParam);
		}
		else if (annotationParam instanceof MatrixParam)
		{
			paramValue = extractParameterFromMatrixParams(pageParameters,
				(MatrixParam)annotationParam);
		}

		return paramValue;
	}

	/**
	 * Extract method parameter value from matrix parameters.
	 *
	 * @param pageParameters PageParameters for the current request.
	 * @param matrixParam the {@link MatrixParam} annotation used for the current parameter.
	 * @return the value obtained from query parameters and converted to argClass.
	 */
	private Object extractParameterFromMatrixParams(PageParameters pageParameters,
		MatrixParam matrixParam)
	{
		int segmentIndex = matrixParam.segmentIndex();
		String variableName = matrixParam.parameterName();
		String rawsSegment = pageParameters.get(segmentIndex).toString();
		Map<String, String> matrixParameters = AbstractURLSegment.getSegmentMatrixParameters(rawsSegment);

		if (matrixParameters.get(variableName) == null)
			return null;

		return AbstractRestResource.toObject(parameterClass, matrixParameters.get(variableName));
	}

	/**
	 * Extract method parameter value from request header.
	 *
	 * @param headerParam the {@link HeaderParam} annotation used for the current method parameter.
	 * @return the extracted value converted to argClass.
	 */
	private Object extractParameterFromHeader(HeaderParam headerParam)
	{
		String value = headerParam.value();
		WebRequest webRequest = AbstractRestResource.getCurrentWebRequest();

		return AbstractRestResource.toObject(parameterClass, webRequest.getHeader(value));
	}

	/**
	 * Extract method parameter's value from query string parameters.
	 *
	 * @param pageParameters the PageParameters of the current request.
	 * @param requestParam the {@link RequestParam} annotation used for the current method parameter.
	 * @return the extracted value converted to argClass.
	 */
	private Object extractParameterFromQuery(PageParameters pageParameters,
		RequestParam requestParam)
	{
		String value = requestParam.value();

		if (pageParameters.get(value) == null)
			return null;

		return AbstractRestResource.toObject(parameterClass, pageParameters.get(value).toString());
	}

	/**
	 * Extract method parameter's value from cookies.
	 *
	 * @param cookieParam the cookie param
	 * @return the extracted value converted to argClass.
	 */
	private Object extractParameterFromCookies(CookieParam cookieParam)
	{
		String value = cookieParam.value();
		WebRequest webRequest = AbstractRestResource.getCurrentWebRequest();

		if (webRequest.getCookie(value) == null)
			return null;

		return AbstractRestResource.toObject(parameterClass, webRequest.getCookie(value).getValue());
	}

	/**
	 * Internal method that tries to extract an instance of the given class from the request body.
	 *
	 * @param mimeInputFormat the type we want to extract from request body.
	 * @param serialDeserial the serial deserial
	 * @return the extracted object.
	 */
	private Object deserializeObjectFromRequest(String mimeInputFormat,
		IWebSerialDeserial serialDeserial)
	{
		WebRequest servletRequest = AbstractRestResource.getCurrentWebRequest();

		return serialDeserial.requestToObject(servletRequest, parameterClass, mimeInputFormat);
	}

	/**
	 * Gets the type of the method parameter.
	 *
	 * @return the parameter class
	 */
	public Class<?> getParameterClass()
	{
		return parameterClass;
	}

	/**
	 * Gets the owner method.
	 *
	 * @return the owner method
	 */
	public MethodMappingInfo getOwnerMethod()
	{
		return ownerMethod;
	}

	/**
	 * Gets the index of the parameter in the array of method's parameters.
	 *
	 * @return the parameter index
	 */
	public int getParamIndex()
	{
		return paramIndex;
	}

	/**
	 * Checks if the parameter required.
	 *
	 * @return true, if is required
	 */
	public boolean isRequired()
	{
		return required;
	}

	/**
	 * Gets the deault value for the parameter.
	 *
	 * @return the deault value
	 */
	public String getDeaultValue()
	{
		return deaultValue;
	}

	/**
	 * Gets the valdator key.
	 *
	 * @return the valdator key
	 */
	public String getValdatorKey()
	{
		return valdatorKey;
	}

	/**
	 * Gets the annotation for the parameter.
	 *
	 * @return the annotation for the parameter
	 */
	public Annotation getAnnotationParam()
	{
		return annotationParam;
	}
}
