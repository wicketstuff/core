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
package org.wicketstuff.rest.contenthandling.webserialdeserial;

import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.request.http.WebRequest;
import org.apache.wicket.request.http.WebResponse;
import org.wicketstuff.rest.contenthandling.IObjectSerialDeserial;
import org.wicketstuff.rest.contenthandling.IWebSerialDeserial;

/**
 * Object serializer/deserializer that supports multiple formats.
 * 
 * @author andrea del bene
 * 
 */
public class MultiFormatSerialDeserial implements IWebSerialDeserial
{

	private final Map<String, IWebSerialDeserial> serialsDeserials = new HashMap<>();

	@Override
	public void objectToResponse(Object targetObject, WebResponse response, String mimeType)
	{

		IWebSerialDeserial serialDeserial = serialsDeserials.get(mimeType);

		if (serialDeserial != null)
			serialDeserial.objectToResponse(targetObject, response, mimeType);
	}

	@Override
	public <T> T requestToObject(WebRequest request, Class<T> targetClass, String mimeType)
	{
		IWebSerialDeserial serialDeserial = serialsDeserials.get(mimeType);

		if (serialDeserial != null)
			return serialDeserial.requestToObject(request, targetClass, mimeType);

		return null;
	}

	/**
	 * Register a new serial/deserial for the given MIME type.
	 * 
	 * @param serialDeserial
	 *            the serial/deserial to use with the given MIME type.
	 * @param mimeType
	 *            the MIME type we want to handle with the given serial/deserial.
	 */
	public void registerSerDeser(IWebSerialDeserial serialDeserial, String mimeType)
	{
		serialsDeserials.put(mimeType, serialDeserial);
	}

	@Override
	public boolean isMimeTypeSupported(String mimeType)
	{
		return serialsDeserials.get(mimeType) != null;
	}

	@Override
	public IObjectSerialDeserial<?> getIObjectSerialDeserial(String mimeType) 
	{
		IWebSerialDeserial webSerialDeserial = serialsDeserials.get(mimeType);
		
		if(webSerialDeserial == null)
		{
		    return null;
		}
		
		IObjectSerialDeserial<?> objectSerialDeserial = webSerialDeserial.getIObjectSerialDeserial(mimeType);
		
		return objectSerialDeserial;
	}
}
