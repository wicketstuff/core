package org.wicketstuff.rest.utils.wicket;

import java.util.LinkedHashMap;

import org.wicketstuff.rest.contenthandling.IWebSerialDeserial;

public class MethodParameterContext
{
	private final AttributesWrapper attributesWrapper;
	private final LinkedHashMap<String, String> pathParameters;
	private final IWebSerialDeserial serialDeserial;

	public MethodParameterContext(AttributesWrapper attributesWrapper,
		LinkedHashMap<String, String> pathParameters, IWebSerialDeserial serialDeserial)
	{
		this.attributesWrapper = attributesWrapper;
		this.pathParameters = pathParameters;
		this.serialDeserial = serialDeserial;
	}

	public AttributesWrapper getAttributesWrapper()
	{
		return attributesWrapper;
	}

	public LinkedHashMap<String, String> getPathParameters()
	{
		return pathParameters;
	}

	public IWebSerialDeserial getSerialDeserial()
	{
		return serialDeserial;
	}
}
