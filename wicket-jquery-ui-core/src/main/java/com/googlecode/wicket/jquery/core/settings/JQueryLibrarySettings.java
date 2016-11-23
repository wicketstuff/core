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
package com.googlecode.wicket.jquery.core.settings;

import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.settings.JavaScriptLibrarySettings;

/**
 * Provides library settings for jQuery UI resource references<br>
 * <br>
 * Usage:
 *
 * <pre>
 * <code>
 * public class MyApplication extends WebApplication
 * {
 *     public void init()
 *     {
 *         super.init();
 *
 *         JQueryLibrarySettings settings = new JQueryLibrarySettings();
 *         settings.setJQueryGlobalizeReference(new JavaScriptResourceReference(...));
 *
 *         this.setJavaScriptLibrarySettings(settings);
 *     }
 * }
 * </code>
 * </pre>
 *
 * @author Sebastien Briquet - sebfz1
 */
public class JQueryLibrarySettings extends JavaScriptLibrarySettings
{
	private ResourceReference globalizeReference = null; // null by default, meaning the user has to set it explicitly

	/**
	 * Constructor
	 */
	public JQueryLibrarySettings()
	{
	}

	/**
	 * Gets the JQuery globalize javascript resource reference
	 *
	 * @return the {@link ResourceReference}
	 */
	public ResourceReference getJQueryGlobalizeReference()
	{
		return this.globalizeReference;
	}

	/**
	 * Sets the JQuery globalize library resource reference
	 *
	 * @param reference the {@link ResourceReference}
	 */
	public void setJQueryGlobalizeReference(ResourceReference reference)
	{
		this.globalizeReference = reference;
	}
}
