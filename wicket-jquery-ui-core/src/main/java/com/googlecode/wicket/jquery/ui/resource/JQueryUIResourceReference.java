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
package com.googlecode.wicket.jquery.ui.resource;

import org.apache.wicket.request.resource.JavaScriptResourceReference;

import com.googlecode.wicket.jquery.ui.settings.JQueryLibrarySettings;


/**
 * The resource reference for the jQuery UI javascript library.<br/>
 * To add a jQuery UI resource reference to a component, do not use this reference, but use
 * {@link JQueryLibrarySettings#getJQueryUIReference()} to prevent version conflicts.
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public class JQueryUIResourceReference extends JavaScriptResourceReference
{
	private static final long serialVersionUID = 1L;

	private static final JQueryUIResourceReference INSTANCE = new JQueryUIResourceReference();

	/**
	 * Normally you should not use this method, but use
	 * {@link JQueryLibrarySettings#getJQueryUIReference()} to prevent version conflicts.
	 *
	 * @return the single instance of the resource reference
	 */
	public static JQueryUIResourceReference get()
	{
		return INSTANCE;
	}

	/**
	 * Private constructor
	 */
	private JQueryUIResourceReference()
	{
		super(JQueryUIResourceReference.class, "jquery-ui-1.9.2.min.js");
	}
}
