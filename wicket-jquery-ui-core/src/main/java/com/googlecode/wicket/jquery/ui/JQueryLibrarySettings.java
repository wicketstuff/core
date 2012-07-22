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
package com.googlecode.wicket.jquery.ui;

import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.ResourceReference;

/**
 * Provides a static class for jQuery library settings (core js &#38; ui resource references).
 * 
 * @author Sebastien Briquet - sebfz1
 *
 */
public class JQueryLibrarySettings
{
	public static ResourceReference CORE_JS = new JavaScriptResourceReference(JQueryLibrarySettings.class, "jquery-1.7.2.min.js"); 
	public static ResourceReference CORE_UI = new JavaScriptResourceReference(JQueryLibrarySettings.class, "jquery-ui-1.8.21.min.js");

	/**
	 * Sets the JQuery backing library resource reference
	 * 
	 * @param reference the {@link ResourceReference}
	 */
	public static void setJQueryReference(ResourceReference reference)
	{
		JQueryLibrarySettings.CORE_JS = reference;
	}

	/**
	 * Sets the JQuery UI backing library resource reference
	 * 
	 * @param reference the {@link ResourceReference}
	 */
	public static void setJQueryUIReference(ResourceReference reference)
	{
		JQueryLibrarySettings.CORE_UI = reference;
	}
}
