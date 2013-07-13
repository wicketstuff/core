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
package com.googlecode.wicket.jquery.ui.plugins.wysiwyg.settings;

import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.settings.def.JavaScriptLibrarySettings;

import com.googlecode.wicket.jquery.ui.plugins.wysiwyg.resource.BootstrapCombinedNoIconsStyleSheetResourceReference;
import com.googlecode.wicket.jquery.ui.plugins.wysiwyg.resource.BootstrapJavaScriptResourceReference;
import com.googlecode.wicket.jquery.ui.plugins.wysiwyg.resource.BootstrapResponsiveStyleSheetResourceReference;
import com.googlecode.wicket.jquery.ui.plugins.wysiwyg.resource.BootstrapWysiwygJavaScriptResourceReference;
import com.googlecode.wicket.jquery.ui.plugins.wysiwyg.resource.EditorStyleSheetResourceReference;
import com.googlecode.wicket.jquery.ui.plugins.wysiwyg.resource.JQueryHotKeysJavaScriptResourceReference;
import com.googlecode.wicket.jquery.ui.plugins.wysiwyg.resource.PrettifyJavaScriptResourceReference;

/**
 * Default implementation of {@link IWysiwygLibrarySettings}.<br/>
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public class WysiwygLibrarySettings extends JavaScriptLibrarySettings implements IWysiwygLibrarySettings
{
	private static WysiwygLibrarySettings instance = null;

	/**
	 * INTERNAL USE<br/>
	 * Gets the {@link WysiwygLibrarySettings} instance
	 * @return the {@link WysiwygLibrarySettings} instance
	 */
	public static synchronized IWysiwygLibrarySettings get()
	{
		if (WysiwygLibrarySettings.instance == null)
		{
			WysiwygLibrarySettings.instance = new WysiwygLibrarySettings();
		}

		return WysiwygLibrarySettings.instance;
	}


	/**
	 * Singleton class
	 */
	private WysiwygLibrarySettings()
	{
	}

	// StyleSheets //
	@Override
	public CssResourceReference getBootstrapCombinedNoIconsStyleSheetReference()
	{
		return BootstrapCombinedNoIconsStyleSheetResourceReference.get();
	}

	@Override
	public CssResourceReference getBootstrapResponsiveStyleSheetReference()
	{
		return BootstrapResponsiveStyleSheetResourceReference.get();
	}

	@Override
	public CssResourceReference getEditorStyleSheetReference()
	{
		return EditorStyleSheetResourceReference.get();
	}


	// JavaScripts //
	@Override
	public JavaScriptResourceReference getBootstrapJavaScriptReference()
	{
		return BootstrapJavaScriptResourceReference.get();
	}

	@Override
	public JavaScriptResourceReference getBootstrapWysiwygJavaScriptReference()
	{
		return BootstrapWysiwygJavaScriptResourceReference.get();
	}

	@Override
	public JavaScriptResourceReference getJQueryHotKeysJavaScriptReference()
	{
		return JQueryHotKeysJavaScriptResourceReference.get();
	}

	@Override
	public JavaScriptResourceReference getPrettifyJavaScriptReference()
	{
		return PrettifyJavaScriptResourceReference.get();
	}
}
