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
package com.googlecode.wicket.jquery.ui.kendo;

import org.apache.wicket.Application;
import org.apache.wicket.settings.IJavaScriptLibrarySettings;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.kendo.resource.KendoUIJavaScriptResourceReference;
import com.googlecode.wicket.jquery.ui.kendo.settings.IKendoUILibrarySettings;

/**
 * Provides the base class for Kendo UI behavior implementations
 *
 * @author Sebastien Briquet - sebfz1
 */
public class KendoAbstractBehavior extends JQueryBehavior
{
	private static final long serialVersionUID = 1L;

	/**
	 * Gets the {@link ICalendarLibrarySettings}
	 *
	 * @return null if Application's {@link IJavaScriptLibrarySettings} is not an instance of {@link ICalendarLibrarySettings}
	 */
	private static IKendoUILibrarySettings getLibrarySettings()
	{
		if (Application.exists() && (Application.get().getJavaScriptLibrarySettings() instanceof IKendoUILibrarySettings))
		{
			return (IKendoUILibrarySettings) Application.get().getJavaScriptLibrarySettings();
		}

		return null;
	}


	/**
	 * Constructor
	 * @param selector the html selector (ie: "#myId")
	 * @param method the kendo-ui method
	 */
	public KendoAbstractBehavior(String selector, String method)
	{
		this(selector, method, new Options());
	}

	/**
	 * Constructor
	 * @param selector the html selector (ie: "#myId")
	 * @param method the kendo-ui method
	 * @param options the {@link Options}
	 */
	public KendoAbstractBehavior(String selector, String method, Options options)
	{
		super(selector, method, options);

		this.initReferences();
	}

	/**
	 * Initializes CSS & JavaScript resource references
	 */
	private void initReferences()
	{
		IKendoUILibrarySettings settings = getLibrarySettings();

		// kendo.common.min.css //
		if (settings != null && settings.getKendoUICommonStyleSheetReference() != null)
		{
			this.add(settings.getKendoUICommonStyleSheetReference());
		}

		// kendo.<theme>.min.css //
		if (settings != null && settings.getKendoUIThemeStyleSheetReference() != null)
		{
			this.add(settings.getKendoUIThemeStyleSheetReference());
		}

		// kendo.web.min.js //
		if (settings != null && settings.getKendoUIJavaScriptReference() != null)
		{
			this.add(settings.getKendoUIJavaScriptReference());
		}
		else
		{
			this.add(KendoUIJavaScriptResourceReference.get());
		}
	}
}
