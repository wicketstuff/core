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

import jakarta.servlet.ServletResponse;

import java.io.IOException;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.request.http.WebRequest;
import org.apache.wicket.request.http.WebResponse;
import org.wicketstuff.rest.contenthandling.IObjectSerialDeserial;
import org.wicketstuff.rest.contenthandling.IWebSerialDeserial;
import org.wicketstuff.rest.contenthandling.mimetypes.RestMimeTypes;
import org.wicketstuff.restutils.http.HttpUtils;

/**
 * Web serializer/deserailizer that works with a textual format.
 *
 * @author andrea del bene
 *
 */
public class TextualWebSerialDeserial implements IWebSerialDeserial
{

	/** the supported charset. */
	private final String charset;

	/** the supported MIME type. */
	private final String mimeType;

	/** the object serializer/deserializer **/
	private final IObjectSerialDeserial<String> objectSerialDeserial;

	public TextualWebSerialDeserial(String charset, String mimeType,
		IObjectSerialDeserial<String> objectSerialDeserial)
	{
		this.charset = charset;
		this.mimeType = mimeType;
		this.objectSerialDeserial = objectSerialDeserial;
	}

	@Override
	public void objectToResponse(Object targetObject, WebResponse response, String mimeType)
		throws WicketRuntimeException
	{
		setCharsetResponse(response);

		String strOutput;

		if (RestMimeTypes.TEXT_PLAIN.equals(mimeType))
		{
			strOutput = targetObject == null ? "" : targetObject.toString();
		}
		else
		{
			strOutput = objectSerialDeserial.serializeObject(targetObject, mimeType);
		}

		response.write(strOutput);
	}

	@Override
	public <T> T requestToObject(WebRequest request, Class<T> argClass, String mimeType)
		throws WicketRuntimeException
	{
		try
		{
			return objectSerialDeserial.deserializeObject(HttpUtils.readStringFromRequest(request),
				argClass, mimeType);
		}
		catch (IOException e)
		{
			throw new WicketRuntimeException("An error occurred during request reading.", e);
		}
	}

	@Override
	final public boolean isMimeTypeSupported(String mimeType)
	{
		return RestMimeTypes.TEXT_PLAIN.equals(mimeType) || this.mimeType.equals(mimeType);
	}

	/**
	 * Sets the charset for the current response.
	 *
	 * @param response
	 *            the current response
	 */
	private void setCharsetResponse(WebResponse response)
	{
		if (response.getContainerResponse() instanceof ServletResponse)
		{
			ServletResponse sResponse = (ServletResponse)response.getContainerResponse();
			sResponse.setCharacterEncoding(charset);
		}
	}

	public String getCharset()
	{
		return charset;
	}

	public String getMimeType()
	{
		return mimeType;
	}

	public IObjectSerialDeserial<String> getObjectSerialDeserial()
	{
		return objectSerialDeserial;
	}

}
