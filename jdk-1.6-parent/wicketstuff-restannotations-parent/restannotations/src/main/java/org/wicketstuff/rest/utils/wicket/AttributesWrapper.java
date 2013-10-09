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
package org.wicketstuff.rest.utils.wicket;

import org.apache.wicket.request.http.WebRequest;
import org.apache.wicket.request.http.WebResponse;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.IResource.Attributes;
import org.wicketstuff.rest.utils.http.HttpMethod;
import org.wicketstuff.rest.utils.http.HttpUtils;

/**
 * Utility class to extract and handle the information from class IResource.Attributes
 * 
 * @author andrea del bene
 * 
 */
public class AttributesWrapper
{
	private final WebResponse webResponse;

	private final WebRequest webRequest;

	private final PageParameters pageParameters;

	private final HttpMethod httpMethod;

	private final Attributes originalAttributes;

	public AttributesWrapper(Attributes attributes)
	{
		this.webRequest = (WebRequest)attributes.getRequest();
		this.webResponse = (WebResponse)attributes.getResponse();
		this.pageParameters = attributes.getParameters();
		this.httpMethod = HttpUtils.getHttpMethod(attributes.getRequest());
		this.originalAttributes = attributes;
	}

	public Attributes getOriginalAttributes()
	{
		return originalAttributes;
	}

	public WebResponse getWebResponse()
	{
		return webResponse;
	}

	public WebRequest getWebRequest()
	{
		return webRequest;
	}

	public PageParameters getPageParameters()
	{
		return pageParameters;
	}

	public HttpMethod getHttpMethod()
	{
		return httpMethod;
	}
}