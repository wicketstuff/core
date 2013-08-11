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
package com.googlecode.wicket.jquery.ui.plugins.fixedheadertable;

import org.apache.wicket.Application;
import org.apache.wicket.settings.IJavaScriptLibrarySettings;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.plugins.fixedheadertable.settings.FixedHeaderTableLibrarySettings;
import com.googlecode.wicket.jquery.ui.plugins.fixedheadertable.settings.IFixedHeaderTableLibrarySettings;

public class FixedHeaderTableBehavior extends JQueryBehavior
{
	private static final long serialVersionUID = 1L;
	private static final String METHOD = "fixedHeaderTable";

	/**
	 * Gets the {@link IFixedHeaderTableLibrarySettings}
	 *
	 * @return Default internal {@link IFixedHeaderTableLibrarySettings} if {@link Application}'s {@link IJavaScriptLibrarySettings} is not an instance of {@link IFixedHeaderTableLibrarySettings}
	 */
	private static IFixedHeaderTableLibrarySettings getLibrarySettings()
	{
		if (Application.exists() && (Application.get().getJavaScriptLibrarySettings() instanceof IFixedHeaderTableLibrarySettings))
		{
			return (IFixedHeaderTableLibrarySettings) Application.get().getJavaScriptLibrarySettings();
		}

		return FixedHeaderTableLibrarySettings.get();
	}


	public FixedHeaderTableBehavior(String selector)
	{
		this(selector, new Options());
	}

	public FixedHeaderTableBehavior(String selector, Options options)
	{
		super(selector, METHOD, options);

		this.initReferences();
	}

	private void initReferences()
	{
		IFixedHeaderTableLibrarySettings settings = getLibrarySettings();

		if (settings.getFixedHeaderTableStyleSheetReference() != null)
		{
			this.add(settings.getFixedHeaderTableStyleSheetReference());
		}

		if (settings.getFixedHeaderTableJavaScriptReference() != null)
		{
			this.add(settings.getFixedHeaderTableJavaScriptReference());
		}
	}
}
