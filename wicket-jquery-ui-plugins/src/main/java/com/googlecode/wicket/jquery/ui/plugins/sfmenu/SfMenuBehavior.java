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
package com.googlecode.wicket.jquery.ui.plugins.sfmenu;

import java.util.Map;

import org.apache.wicket.Application;
import org.apache.wicket.settings.IJavaScriptLibrarySettings;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.plugins.sfmenu.resource.HoverIntentJavaScriptResourceReference;
import com.googlecode.wicket.jquery.ui.plugins.sfmenu.resource.SuperfishJavaScriptResourceReference;
import com.googlecode.wicket.jquery.ui.plugins.sfmenu.settings.ISuperfishLibrarySettings;
import com.googlecode.wicket.jquery.ui.plugins.sfmenu.settings.SuperfishLibrarySettings;


/***
 *
 * @author Ludger Kluitmann - JavaLuigi
 *
 */
public abstract class SfMenuBehavior extends JQueryBehavior
{

	private static final long serialVersionUID = 1L;
	private static final String METHOD = "superfish";

	/**
	 * Gets the {@link ISuperfishLibrarySettings}
	 *
	 * @return Default internal {@link ISuperfishLibrarySettings} if {@link Application}'s {@link IJavaScriptLibrarySettings} is not an instance of {@link ISuperfishLibrarySettings}
	 */
	private static ISuperfishLibrarySettings getLibrarySettings()
	{
		if (Application.exists() && (Application.get().getJavaScriptLibrarySettings() instanceof ISuperfishLibrarySettings))
		{
			return (ISuperfishLibrarySettings) Application.get().getJavaScriptLibrarySettings();
		}

		return SuperfishLibrarySettings.get();
	}

	/***
	 * Construtor
	 *
	 * @param selector
	 */
	public SfMenuBehavior(String selector)
	{
		this(selector, new Options(), false);
	}

	/***
	 * Constructor
	 *
	 * @param selector
	 * @param options
	 */
	public SfMenuBehavior(String selector, Options options, Boolean verticalSfMenu)
	{
		super(selector, METHOD, options);

		ISuperfishLibrarySettings settings = getLibrarySettings();

		if(settings.getSuperfishStyleSheetReference() != null)
		{
			this.add(settings.getSuperfishStyleSheetReference());
		}
		if(verticalSfMenu && settings.getSuperfishVerticalStyleSheetReference() != null)
		{
			this.add(settings.getSuperfishVerticalStyleSheetReference());
		}

		this.add(HoverIntentJavaScriptResourceReference.get());
		this.add(SuperfishJavaScriptResourceReference.get());
	}

	// Properties //
	/**
	 * Gets the reference map of hash/menu-item.<br/>
	 *
	 * @return the non-null {@link Map}
	 */
	protected abstract Map<String, ISfMenuItem> getMenuItemMap();

}
