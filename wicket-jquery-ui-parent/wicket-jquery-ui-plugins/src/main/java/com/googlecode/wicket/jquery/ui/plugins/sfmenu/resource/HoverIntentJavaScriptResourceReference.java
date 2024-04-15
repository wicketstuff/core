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
package com.googlecode.wicket.jquery.ui.plugins.sfmenu.resource;

import org.apache.wicket.resource.JQueryPluginResourceReference;

import com.googlecode.wicket.jquery.ui.plugins.sfmenu.settings.SuperfishLibrarySettings;

/**
 * Provides the 'hoverIntent' javascript resource reference for the superfish-menu library.
 *
 * @see SuperfishLibrarySettings
 * @author Sebastien Briquet - sebfz1
 * @since 6.12.0
 */
public class HoverIntentJavaScriptResourceReference extends JQueryPluginResourceReference
{
	private static final long serialVersionUID = 1L;
	private static final HoverIntentJavaScriptResourceReference INSTANCE = new HoverIntentJavaScriptResourceReference();

	/**
	 * Gets the instance of the resource reference
	 *
	 * @return the single instance of the resource reference
	 */
	public static HoverIntentJavaScriptResourceReference get()
	{
		return INSTANCE;
	}

	/**
	 * Private constructor
	 */
	private HoverIntentJavaScriptResourceReference()
	{
		super(HoverIntentJavaScriptResourceReference.class, "js/hoverIntent.js");
	}
}
