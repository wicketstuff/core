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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.WicketRuntimeException;
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

// TODO: Auto-generated Javadoc
/**
 * The class contains the informations of a method parameter, like its type or its index in the
 * array of method parameters.
 * 
 * @author andrea del bene
 */
public class MethodParameter<T>
{

	/** The parameter class. */
	private final Class<T> parameterClass;

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

	/**
	 * The annotation used to indicate how the value 
	 * of the parameter must be retrieved
	 * */
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
	public MethodParameter(Class<T> type, MethodMappingInfo ownerMethod, int paramIndex)
	{
		Args.notNull(type, "type");
		Args.notNull(ownerMethod, "ownerMethod");

		this.parameterClass = type;
		this.ownerMethod = ownerMethod;
		this.paramIndex = paramIndex;

		annotationParam = ReflectionUtils.getAnnotationParam(paramIndex, ownerMethod.getMethod());

		this.required = ReflectionUtils.getAnnotationField(annotationParam, "required", true);
		this.deaultValue = ReflectionUtils.getAnnotationField(annotationParam, "defaultValue", "");

		ValidatorKey validatorAnnotation = ReflectionUtils.findMethodParameterAnnotation(
			ownerMethod.getMethod(), paramIndex, ValidatorKey.class);

		this.valdatorKey = ReflectionUtils.getAnnotationField(validatorAnnotation, "value", "");

	}

	public Object parameterValue(MethodParameterContext context)
	{
		Object paramValue = null;
		
		if (annotationParam == null)
			paramValue = extractParameterFromUrl(context);
		else
			paramValue = extractParameterValue(context);

		// try to use the default value
		if (paramValue == null && !deaultValue.isEmpty())
			paramValue = AbstractRestResource.toObject(parameterClass, deaultValue);

		return paramValue;
	}
	
	/***
	 * Extract a parameter values from the REST URL.
	 * 
	 * @param methodParameter
	 *            the current method parameter.
	 * @param pathParamIterator
	 *            an iterator on the current values of path parameters.
	 * 
	 * @return the parameter value.
	 */
	private Object extractParameterFromUrl(MethodParameterContext context)
	{
		LinkedHashMap<String, String> parameters = context.getPathParameters();
		Iterator<String> paramIterator = parameters.values().iterator();
		List<MethodParameter> methodParameters = ownerMethod.getMethodParameters();
		
		for (int i = 0; i < paramIndex; i++)
		{
			MethodParameter parameter = methodParameters.get(i);
			
			if(parameter.getAnnotationParam() == null)
				paramIterator.next();
		}
		
		return AbstractRestResource.toObject(parameterClass, paramIterator.next());
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
	 *            the annotation for the current parameter that indicates how to retrieve the value
	 *            for the current parameter.
	 * @param pageParameters
	 *            PageParameters for the current request.
	 * @return the extracted value.
	 */
	private Object extractParameterValue(MethodParameterContext context)
	{
		Object paramValue = null;

		String mimeInputFormat = ownerMethod.getMimeInputFormat();
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
	 * @param pageParameters
	 *            PageParameters for the current request.
	 * @param matrixParam
	 *            the {@link MatrixParam} annotation used for the current parameter.
	 * @param argClass
	 *            the type of the current method parameter.
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
	 * @param headerParam
	 *            the {@link HeaderParam} annotation used for the current method parameter.
	 * @param argClass
	 *            the type of the current method parameter.
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
	 * @param pageParameters
	 *            the PageParameters of the current request.
	 * @param requestParam
	 *            the {@link RequestParam} annotation used for the current method parameter.
	 * @param argClass
	 *            the type of the current method parameter.
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
	 * @param annotation
	 *            the {@link CookieParam} annotation used for the current method parameter.
	 * @param argClass
	 *            the type of the current method parameter.
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
	 * @param mimeInputFormat
	 *            the type we want to extract from request body.
	 * @return the extracted object.
	 */
	private Object deserializeObjectFromRequest(String mimeInputFormat,
		IWebSerialDeserial serialDeserial)
	{
		WebRequest servletRequest = AbstractRestResource.getCurrentWebRequest();
		try
		{
			return serialDeserial.requestToObject(servletRequest, parameterClass, mimeInputFormat);
		}
		catch (Exception e)
		{
			throw new WicketRuntimeException("Error occurred during object extraction from request.", e);
		}
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

	public String getValdatorKey()
	{
		return valdatorKey;
	}

	public Annotation getAnnotationParam()
	{
		return annotationParam;
	}

}
