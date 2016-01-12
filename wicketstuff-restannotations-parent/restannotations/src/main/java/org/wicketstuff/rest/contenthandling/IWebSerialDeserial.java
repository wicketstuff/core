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
package org.wicketstuff.rest.contenthandling;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.request.http.WebRequest;
import org.apache.wicket.request.http.WebResponse;

/**
 * General interface to serialize/deserialize an object to/from request/response.
 *
 * @author andrea del bene
 *
 */
public interface IWebSerialDeserial
{
	/**
	 * Write the object in input to the response converting it to a given MIME type.
	 *
	 * @param targetObject
	 *            the object instance to serialize to string.
	 * @param response
	 *            the response object.
	 * @param mimeType
	 *            the MIME type of the response.
	 * @throws Exception
	 */
	public void objectToResponse(Object targetObject, WebResponse response, String mimeType)
		throws WicketRuntimeException;

	/**
	 * Extract an instance of argClass form the request.
	 *
	 * @param request
	 *            the request object.
	 * @param argClass
	 *            the type of the object we want to extract.
	 * @param mimeType
	 *            the MIME type of the request.
	 *
	 * @return the object extracted from the request.
	 */
	public <T> T requestToObject(WebRequest request, Class<T> argClass, String mimeType)
		throws WicketRuntimeException;

	/**
	 * Check if a given MIME type is handled.
	 *
	 * @param mimeType
	 *            the MIME type we want to check.
	 * @return true if the MIME type is supported, false otherwise.
	 */
	public boolean isMimeTypeSupported(String mimeType);

	/**
	 * Returns the {@link IObjectSerialDeserial} for the specific MIME type
	 *
	 * @param mimeType
	 * @return
	 *     the appropriate IObjectSerialDeserial.
	 */
	public IObjectSerialDeserial<?> getIObjectSerialDeserial(String mimeType);
}
