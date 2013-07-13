/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.googlecode.wicket.jquery.ui.plugins.wysiwyg;

import org.apache.wicket.Application;
import org.apache.wicket.markup.html.IPackageResourceGuard;
import org.apache.wicket.markup.html.SecurePackageResourceGuard;
import org.apache.wicket.markup.html.SecurePackageResourceGuard.SearchPattern;
import org.apache.wicket.settings.IJavaScriptLibrarySettings;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.plugins.wysiwyg.settings.IWysiwygLibrarySettings;
import com.googlecode.wicket.jquery.ui.plugins.wysiwyg.settings.WysiwygLibrarySettings;

public class WysiwygBehavior extends JQueryBehavior
{
	private static final long serialVersionUID = 1L;
	private static final String METHOD = "wysiwyg";

	/**
	 * Gets the {@link IWysiwygLibrarySettings}
	 *
	 * @return Default {@link IWysiwygLibrarySettings} if Application's {@link IJavaScriptLibrarySettings} is not an instance of {@link IWysiwygLibrarySettings}
	 */
	private static IWysiwygLibrarySettings getLibrarySettings()
	{
		if (Application.exists() && (Application.get().getJavaScriptLibrarySettings() instanceof IWysiwygLibrarySettings))
		{
			return (IWysiwygLibrarySettings) Application.get().getJavaScriptLibrarySettings();
		}

		return WysiwygLibrarySettings.get();
	}


	/**
	 * Constructor
	 * @param selector the html selector (ie: "#myId")
	 */
	public WysiwygBehavior(String selector)
	{
		this(selector, new Options());
	}

	/**
	 * Constructor
	 * @param selector the html selector (ie: "#myId")
	 * @param options the {@link Options}
	 */
	public WysiwygBehavior(String selector, Options options)
	{
		super(selector, METHOD, options);

		IPackageResourceGuard packageResourceGuard = Application.get().getResourceSettings().getPackageResourceGuard();

		if (packageResourceGuard instanceof SecurePackageResourceGuard)
		{
			SecurePackageResourceGuard guard = (SecurePackageResourceGuard) packageResourceGuard;
			if (!guard.getPattern().contains(new SearchPattern("+*.eot")))
			{
				guard.addPattern("+*.eot");
				guard.addPattern("+*.woff");
				guard.addPattern("+*.ttf");
			}
		}

		this.initReferences();
	}

	/**
	 * Initializes CSS & JavaScript resource references
	 */
	private void initReferences()
	{
		IWysiwygLibrarySettings settings = getLibrarySettings();

		// Bootstrap combined CSS
		if (settings.getBootstrapCombinedNoIconsStyleSheetReference() != null)
		{
			this.add(settings.getBootstrapCombinedNoIconsStyleSheetReference());
		}

		// Bootstrap Responsive CSS
		if (settings.getBootstrapResponsiveStyleSheetReference() != null)
		{
			this.add(settings.getBootstrapResponsiveStyleSheetReference());
		}

		// Editor CSS
		if (settings.getEditorStyleSheetReference() != null)
		{
			this.add(settings.getEditorStyleSheetReference());
		}

		// Bootstrap Wysiwyg
		if (settings.getBootstrapWysiwygJavaScriptReference() != null)
		{
			this.add(settings.getBootstrapWysiwygJavaScriptReference());
		}

		// Bootstrap
		if (settings.getBootstrapJavaScriptReference() != null)
		{
			this.add(settings.getBootstrapJavaScriptReference());
		}

		// JQuery Hot Keys
		if (settings.getJQueryHotKeysJavaScriptReference() != null)
		{
			this.add(settings.getJQueryHotKeysJavaScriptReference());
		}

		// Prettify
		if (settings.getPrettifyJavaScriptReference() != null)
		{
			this.add(settings.getPrettifyJavaScriptReference());
		}
	}
}