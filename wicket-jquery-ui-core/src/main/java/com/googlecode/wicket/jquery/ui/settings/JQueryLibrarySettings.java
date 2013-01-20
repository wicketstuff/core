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
package com.googlecode.wicket.jquery.ui.settings;

import org.apache.wicket.request.resource.ResourceReference;

import com.googlecode.wicket.jquery.ui.resource.JQueryResourceReference;
import com.googlecode.wicket.jquery.ui.resource.JQueryUIResourceReference;

/**
 * Provides an utility class for jQuery library settings (core js &#38; ui resource references).<br/>
 *
 * <code><pre>
 * public class MyApplication extends WebApplication
 * {
 *     public void init()
 *     {
 *         super.init();
 *
 *         JQueryLibrarySettings.setJQueryReference(new JavaScriptResourceReference(MyApplication.class, "jquery-1.8.2.min.js"));
 *         JQueryLibrarySettings.setJQueryUIReference(new JavaScriptResourceReference(MyApplication.class, "jquery-ui-1.9.0.min.js"));
 *     }
 * }
 * <pre></code>
 *
 * <b>Note:</b> The reference given as parameter can be null.
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public class JQueryLibrarySettings
{
	private static ResourceReference jQueryReference = JQueryResourceReference.get();
	private static ResourceReference jQueryUIReference = JQueryUIResourceReference.get();
	private static ResourceReference jQueryGlobalizeReference = null; //should be explicitly set by user


	// jQuery Core //

	/**
	 * Gets the JQuery backing library resource reference
	 *
	 * @return the {@link ResourceReference}
	 */
	public static ResourceReference getJQueryReference()
	{
		return JQueryLibrarySettings.jQueryReference;
	}

	/**
	 * Sets the JQuery backing library resource reference
	 *
	 * @param reference the {@link ResourceReference}, or null
	 */
	public static void setJQueryReference(ResourceReference reference)
	{
		JQueryLibrarySettings.jQueryReference = reference;
	}


	// jQuery UI //

	/**
	 * Gets the JQuery UI backing library resource reference
	 *
	 * @return the {@link ResourceReference}
	 */
	public static ResourceReference getJQueryUIReference()
	{
		return JQueryLibrarySettings.jQueryUIReference;
	}

	/**
	 * Sets the JQuery UI backing library resource reference
	 *
	 * @param reference the {@link ResourceReference}, or null
	 */
	public static void setJQueryUIReference(ResourceReference reference)
	{
		JQueryLibrarySettings.jQueryUIReference = reference;
	}

	// jQuery UI //
	/**
	 * Gets the JQuery UI backing library resource reference
	 *
	 * @return the {@link ResourceReference}
	 */
	public static ResourceReference getJQueryGlobalizeReference()
	{
		return JQueryLibrarySettings.jQueryGlobalizeReference;
	}

	/**
	 * Sets the JQuery UI backing library resource reference
	 *
	 * @param reference the {@link ResourceReference}, or null
	 */
	public static void setJQueryGlobalizeReference(ResourceReference reference)
	{
		JQueryLibrarySettings.jQueryGlobalizeReference = reference;
	}

}
