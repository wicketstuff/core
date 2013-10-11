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
import org.apache.wicket.settings.def.JavaScriptLibrarySettings;

import com.googlecode.wicket.jquery.ui.plugins.sfmenu.resource.SuperfishStyleSheetResourceReference;
import com.googlecode.wicket.jquery.ui.plugins.sfmenu.resource.SuperfishVerticalStyleSheetResourceReference;

/**
 * Default implementation of {@link ISuperfishLibrarySettings}.<br/>
 *
 * @author Ludger Kluitmann - JavaLuigi
 *
 */
public class SuperfishLibrarySettings extends JavaScriptLibrarySettings implements ISuperfishLibrarySettings
{
	private static SuperfishLibrarySettings instance = null;

	/**
	 * INTERNAL USE<br/>
	 * Gets the {@link SuperfishLibrarySettings} instance
	 * @return the {@link SuperfishLibrarySettings} instance
	 */
	public static synchronized ISuperfishLibrarySettings get()
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


	@Override
	public ResourceReference getSuperfishStyleSheetReference()
	{
		return SuperfishStyleSheetResourceReference.get();
	}


	@Override
	public ResourceReference getSuperfishVerticalStyleSheetReference()
	{
		return SuperfishVerticalStyleSheetResourceReference.get();
	}
}
