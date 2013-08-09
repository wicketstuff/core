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

import javax.servlet.ServletResponse;

import org.apache.wicket.request.http.WebRequest;
import org.apache.wicket.request.http.WebResponse;
import org.wicketstuff.rest.contenthandling.IObjectSerialDeserial;
import org.wicketstuff.rest.contenthandling.RestMimeTypes;
import org.wicketstuff.rest.utils.http.HttpUtils;

// TODO: Auto-generated Javadoc
/**
 * Abstract object serializer/deserializer that works with textual formats.
 * 
 * @author andrea del bene
 * 
 */
public abstract class TextualObjectSerialDeserial implements IObjectSerialDeserial {
	
	/** the supported charset. */
	private final String charset;
	
	/** the supported MIME type. */
	private final String mimeType;

	/**
	 * Instantiates a new textual object serial deserial.
	 *
	 * @param charset the charset
	 * @param mimeType the mime type
	 */
	public TextualObjectSerialDeserial(String charset, String mimeType) {
		this.charset = charset;
		this.mimeType = mimeType;
	}

	/* (non-Javadoc)
	 * @see org.wicketstuff.rest.contenthandling.IObjectSerialDeserial#objectToResponse(java.lang.Object, org.apache.wicket.request.http.WebResponse, java.lang.String)
	 */
	@Override
	public void objectToResponse(Object targetObject, WebResponse response, String mimeType)
			throws Exception {
		setCharsetResponse(response);
		
		String strOutput;
		
		if(RestMimeTypes.TEXT_PLAIN.equals(mimeType))
			strOutput = targetObject == null ? "" : targetObject.toString();
		else
			strOutput = objectToString(targetObject, mimeType);
		
		response.write(strOutput);
	}

	/**
	 * Sets the charset response.
	 *
	 * @param response the new charset response
	 */
	private void setCharsetResponse(WebResponse response) {
		if (response.getContainerResponse() instanceof ServletResponse) {
			ServletResponse sResponse = (ServletResponse) response.getContainerResponse();
			sResponse.setCharacterEncoding(charset);
		}
	}

	/* (non-Javadoc)
	 * @see org.wicketstuff.rest.contenthandling.IObjectSerialDeserial#requestToObject(org.apache.wicket.request.http.WebRequest, java.lang.Class, java.lang.String)
	 */
	@Override
	public <T> T requestToObject(WebRequest request, Class<T> targetClass, String mimeType)
			throws Exception {
		return stringToObject(HttpUtils.readStringFromRequest(request), targetClass, mimeType);
	}

	/* (non-Javadoc)
	 * @see org.wicketstuff.rest.contenthandling.IObjectSerialDeserial#isMimeTypeSupported(java.lang.String)
	 */
	@Override
	final public boolean isMimeTypeSupported(String mimeType) {
		return RestMimeTypes.TEXT_PLAIN.equals(mimeType) || this.mimeType.equals(mimeType);
	}

	/**
	 * Returns a textual representation of the target object.
	 * 
	 * @param targetObject
	 *            the object to convert to string.
	 * @param mimeType
	 *            the target MIME type.
	 * @return the textual representation of the object.
	 */
	public abstract String objectToString(Object targetObject, String mimeType);

	/**
	 * Extract an object instance from a string value.
	 *
	 * @param <T> the generic type
	 * @param source the source string.
	 * @param targetClass the type of the returned object.
	 * @param mimeType the target MIME type.
	 * @return the extracted object.
	 */
	public abstract <T> T stringToObject(String source, Class<T> targetClass, String mimeType);
	
	/**
	 * Gets the supported charset.
	 *
	 * @return the supported charset
	 */
	public String getCharset() {
		return charset;
	}

	/**
	 * Gets the mime type.
	 *
	 * @return the mime type
	 */
	public String getMimeType() {
		return mimeType;
	}
}
