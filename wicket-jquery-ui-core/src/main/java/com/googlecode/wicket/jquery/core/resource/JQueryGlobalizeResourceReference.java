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
package com.googlecode.wicket.jquery.core.resource;

import org.apache.wicket.request.resource.JavaScriptResourceReference;

import com.googlecode.wicket.jquery.core.settings.JQueryLibrarySettings;

/**
 * The resource reference for the jQuery Globalize javascript library.<br/>
 * This reference is not added by default. See {@link JQueryLibrarySettings}.
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public class JQueryGlobalizeResourceReference extends JavaScriptResourceReference
{
	private static final long serialVersionUID = 1L;

	private static final JQueryGlobalizeResourceReference INSTANCE = new JQueryGlobalizeResourceReference();

	/**
	 * Normally you should not use this method, but use
	 * {@link JQueryLibrarySettings#getJQueryGlobalizeReference()} to prevent version conflicts.
	 *
	 * @return the single instance of the resource reference
	 */
	public static JQueryGlobalizeResourceReference get()
	{
		return INSTANCE;
	}

	/**
	 * Private constructor
	 */
	private JQueryGlobalizeResourceReference()
	{
		super(JQueryGlobalizeResourceReference.class, "globalize.js");
	}
}
