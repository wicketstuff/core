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
package org.wicketstuff.rest.contenthandling.serialdeserial;

import java.io.IOException;

import javax.servlet.ServletResponse;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.request.http.WebRequest;
import org.apache.wicket.request.http.WebResponse;
import org.wicketstuff.rest.contenthandling.IObjectSerialDeserial;
import org.wicketstuff.rest.contenthandling.IWebSerialDeserial;
import org.wicketstuff.rest.contenthandling.RestMimeTypes;
import org.wicketstuff.rest.utils.http.HttpUtils;

/**
 * Abstract object serializer/deserializer that works with textual formats.
 * 
 * @author andrea del bene
 * 
 * @deprecated
 * Use TextualWebSerialDeserial and IObjectSerialDeserial<String> instead
 */
@Deprecated
public abstract class TextualObjectSerialDeserial implements IWebSerialDeserial,
	IObjectSerialDeserial<String>
{

	/** the supported charset. */
	private final String charset;

	/** the supported MIME type. */
	private final String mimeType;

	/**
	 * Instantiates a new textual object serial deserial.
	 * 
	 * @param charset
	 *            the charset
	 * @param mimeType
	 *            the mime type
	 */
	public TextualObjectSerialDeserial(String charset, String mimeType)
	{
		this.charset = charset;
		this.mimeType = mimeType;
	}

	@Override
	public void objectToResponse(Object targetObject, WebResponse response, String mimeType)
	{
		setCharsetResponse(response);

		String strOutput;

		if (RestMimeTypes.TEXT_PLAIN.equals(mimeType))
			strOutput = targetObject == null ? "" : targetObject.toString();
		else
			strOutput = serializeObject(targetObject, mimeType);

		response.write(strOutput);
	}

	/**
	 * Sets the charset response.
	 * 
	 * @param response
	 *            the new charset response
	 */
	private void setCharsetResponse(WebResponse response)
	{
		if (response.getContainerResponse() instanceof ServletResponse)
		{
			ServletResponse sResponse = (ServletResponse)response.getContainerResponse();
			sResponse.setCharacterEncoding(charset);
		}
	}

	@Override
	public <T> T requestToObject(WebRequest request, Class<T> targetClass, String mimeType)
	{
		try
		{
			return deserializeObject(HttpUtils.readStringFromRequest(request), targetClass, mimeType);
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

	@Override
	public IObjectSerialDeserial<String> getIObjectSerialDeserial(String mimeType) 
	{
		if(!isMimeTypeSupported(mimeType))
			return null;
		
		return this;
	}

	/**
	 * Gets the supported charset.
	 * 
	 * @return the supported charset
	 */
	public String getCharset()
	{
		return charset;
	}

	/**
	 * Gets the mime type.
	 * 
	 * @return the mime type
	 */
	public String getMimeType()
	{
		return mimeType;
	}

}
