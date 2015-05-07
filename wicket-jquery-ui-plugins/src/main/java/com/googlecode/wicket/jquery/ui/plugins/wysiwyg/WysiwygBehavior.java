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

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.plugins.wysiwyg.settings.WysiwygLibrarySettings;

/**
 * Provides the Wysiwyg plugin behavior
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public class WysiwygBehavior extends JQueryBehavior
{
	private static final long serialVersionUID = 1L;
	public static final String METHOD = "wysiwyg";

	/**
	 * Constructor
	 *
	 * @param selector the html selector (ie: "#myId")
	 */
	public WysiwygBehavior(String selector)
	{
		this(selector, new Options());
	}

	/**
	 * Constructor
	 *
	 * @param selector the html selector (ie: "#myId")
	 * @param options the {@link Options}
	 */
	public WysiwygBehavior(String selector, Options options)
	{
		super(selector, METHOD, options);

		this.initReferences();
	}

	/**
	 * Initializes CSS & JavaScript resource references
	 */
	private void initReferences()
	{
		WysiwygLibrarySettings settings = WysiwygLibrarySettings.get();

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

		// Editor JS
		if (settings.getEditorJavaScriptResourceReference() != null)
		{
			this.add(settings.getEditorJavaScriptResourceReference());
		}

		// Bootstrap Wysiwyg
		if (settings.getBootstrapWysiwygJavaScriptReference() != null)
		{
			this.add(settings.getBootstrapWysiwygJavaScriptReference());
		}

		// Bootstrap
		if (settings.getBootstrapDropDownJavaScriptReference() != null)
		{
			this.add(settings.getBootstrapDropDownJavaScriptReference());
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
