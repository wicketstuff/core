/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.wicket.security.examples.tabs.pages;

import org.apache.wicket.PageParameters;
import org.apache.wicket.model.IModel;
import org.apache.wicket.security.examples.pages.MySecurePage;

/**
 * Custom secure page, required, because the way all these examples are set up, to return
 * the context for logging off.
 * 
 * @author marrink
 */
public class SecurePage extends MySecurePage
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Construct.
	 */
	public SecurePage()
	{
	}

	/**
	 * Construct.
	 * 
	 * @param parameters
	 */
	public SecurePage(PageParameters parameters)
	{
		super(parameters);
	}

	/**
	 * Construct.
	 * 
	 * @param model
	 */
	public SecurePage(IModel< ? > model)
	{
		super(model);
	}

}
