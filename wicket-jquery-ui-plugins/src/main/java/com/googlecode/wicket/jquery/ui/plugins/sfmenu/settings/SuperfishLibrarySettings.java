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
package com.googlecode.wicket.jquery.ui.plugins.sfmenu.settings;

import org.apache.wicket.request.resource.ResourceReference;

import com.googlecode.wicket.jquery.ui.plugins.sfmenu.resource.SuperfishStyleSheetResourceReference;
import com.googlecode.wicket.jquery.ui.plugins.sfmenu.resource.SuperfishVerticalStyleSheetResourceReference;

/**
 * Provides library settings for superfish css resource references<br/>
 * <br/>
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
 *         SuperfishLibrarySettings settings = SuperfishLibrarySettings.get();
 *         settings.setStyleSheetReference(new CssResourceReference(...));
 *         settings.setVerticalStyleSheetReference(new CssResourceReference(...));
 *     }
 * }
 * </code>
 * </pre>
 *
 * @author Ludger Kluitmann - JavaLuigi
 * @since 6.12.0
 */
public class SuperfishLibrarySettings
{
	private static SuperfishLibrarySettings instance = null;

	private ResourceReference stylesheetReference = SuperfishStyleSheetResourceReference.get();
	private ResourceReference stylesheetVerticalReference = SuperfishVerticalStyleSheetResourceReference.get();

	/**
	 * Gets the {@link SuperfishLibrarySettings} instance
	 *
	 * @return the {@link SuperfishLibrarySettings} instance
	 */
	public static synchronized SuperfishLibrarySettings get()
	{
		if (SuperfishLibrarySettings.instance == null)
		{
			SuperfishLibrarySettings.instance = new SuperfishLibrarySettings();
		}

		return SuperfishLibrarySettings.instance;
	}

	/**
	 * Singleton class
	 */
	private SuperfishLibrarySettings()
	{
	}

	/**
	 * Gets the superfish stylesheet resource reference
	 *
	 * @return the {@link ResourceReference}
	 */
	public ResourceReference getStyleSheetReference()
	{
		return this.stylesheetReference;
	}

	/**
	 * Sets the superfish stylesheet resource reference
	 *
	 * @param reference the {@link ResourceReference}
	 */
	public void setStylesheetReference(ResourceReference reference)
	{
		this.stylesheetReference = reference;
	}

	/**
	 * Gets the superfish vertical stylesheet resource reference
	 *
	 * @return the {@link ResourceReference}
	 */
	public ResourceReference getVerticalStyleSheetReference()
	{
		return this.stylesheetVerticalReference;
	}

	/**
	 * Sets the superfish stylesheet resource reference
	 *
	 * @param reference the {@link ResourceReference}
	 */
	public void setVerticalStyleSheetReference(ResourceReference reference)
	{
		this.stylesheetVerticalReference = reference;
	}
}
