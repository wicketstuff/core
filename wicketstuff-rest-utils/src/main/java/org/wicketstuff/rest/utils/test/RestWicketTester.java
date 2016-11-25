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
package org.wicketstuff.rest.utils.test;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.tester.WicketTester;
import org.wicketstuff.rest.utils.http.HttpMethod;

import javax.servlet.ServletContext;


public class RestWicketTester extends WicketTester 
{
	
	public RestWicketTester() 
	{
		super();
	}

	public RestWicketTester(Class<? extends Page> homePage) 
	{
		super(homePage);
	}

	public RestWicketTester(WebApplication application, boolean init) 
	{
		super(application, init);
	}

	public RestWicketTester(WebApplication application, ServletContext servletCtx, boolean init) 
	{
		super(application, servletCtx, init);
	}

	public RestWicketTester(WebApplication application, ServletContext servletCtx)
	{
		super(application, servletCtx);
	}

	public RestWicketTester(WebApplication application, String path) 
	{
		super(application, path);
	}

	public RestWicketTester(WebApplication application) 
	{
		super(application);
	}
	
	public void executeUrl(HttpMethod httpMethod, String url) 
	{
		getRequest().setMethod(httpMethod.getMethod());
		executeUrl(url);
	}
	
}
