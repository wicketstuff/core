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
package com.googlecode.wicket.jquery.ui.plugins.fixedheadertable.settings;

import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.settings.def.JavaScriptLibrarySettings;

import com.googlecode.wicket.jquery.ui.plugins.emoticons.settings.IEmoticonsLibrarySettings;
import com.googlecode.wicket.jquery.ui.plugins.fixedheadertable.resource.FixedHeaderTableJavaScriptResourceReference;
import com.googlecode.wicket.jquery.ui.plugins.fixedheadertable.resource.FixedHeaderTableStyleSheetResourceReference;

/**
 * Default implementation of {@link IEmoticonsLibrarySettings}.<br/>
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public class FixedHeaderTableLibrarySettings extends JavaScriptLibrarySettings implements IFixedHeaderTableLibrarySettings
{
	private static IFixedHeaderTableLibrarySettings instance = null;

	/**
	 * INTERNAL USE<br/>
	 * Gets the {@link FixedHeaderTableLibrarySettings} instance
	 * @return the {@link FixedHeaderTableLibrarySettings} instance
	 */
	public static synchronized IFixedHeaderTableLibrarySettings get()
	{
		if (FixedHeaderTableLibrarySettings.instance == null)
		{
			FixedHeaderTableLibrarySettings.instance = new FixedHeaderTableLibrarySettings();
		}

		return FixedHeaderTableLibrarySettings.instance;
	}


	/**
	 * Constructor
	 */
	public FixedHeaderTableLibrarySettings()
	{
	}

	@Override
	public ResourceReference getFixedHeaderTableStyleSheetReference()
	{
		return FixedHeaderTableStyleSheetResourceReference.get();
	}

	@Override
	public ResourceReference getFixedHeaderTableJavaScriptReference()
	{
		return FixedHeaderTableJavaScriptResourceReference.get();
	}
}
