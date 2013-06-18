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
package com.googlecode.wicket.jquery.ui.plugins.emoticons;

import org.apache.wicket.Application;
import org.apache.wicket.settings.IJavaScriptLibrarySettings;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.plugins.emoticons.settings.EmoticonsLibrarySettings;
import com.googlecode.wicket.jquery.ui.plugins.emoticons.settings.IEmoticonsLibrarySettings;

/**
 * Provides the jQuery css-emoticons plugin behavior
 *
 * @author Sebastien Briquet - sebfz1
 */
public class EmoticonsBehavior extends JQueryBehavior
{
	private static final long serialVersionUID = 1L;
	private static final String METHOD = "emoticonize";

	/**
	 * Gets the {@link IEmoticonsLibrarySettings}
	 *
	 * @return null if Application's {@link IJavaScriptLibrarySettings} is not an instance of {@link IEmoticonsLibrarySettings}
	 */
	private static IEmoticonsLibrarySettings getLibrarySettings()
	{
		if (Application.exists() && (Application.get().getJavaScriptLibrarySettings() instanceof IEmoticonsLibrarySettings))
		{
			return (IEmoticonsLibrarySettings) Application.get().getJavaScriptLibrarySettings();
		}

		return EmoticonsLibrarySettings.get();
	}


	/**
	 * Constructor
	 * @param selector the html selector (ie: "#myId")
	 */
	public EmoticonsBehavior(String selector)
	{
		this(selector, new Options());
	}

	/**
	 * Constructor
	 * @param selector the html selector (ie: "#myId")
	 * @param options the {@link Options}
	 */
	public EmoticonsBehavior(String selector, Options options)
	{
		super(selector, METHOD, options);

		this.initReferences();
	}

	/**
	 * Initializes CSS & JavaScript resource references
	 */
	private void initReferences()
	{
		IEmoticonsLibrarySettings settings = getLibrarySettings();

		// jquery.cssemoticons.css //
		if (settings.getEmoticonsStyleSheetReference() != null)
		{
			this.add(settings.getEmoticonsStyleSheetReference());
		}

		// jquery.cssemoticons.min.js //
		if (settings.getEmoticonsJavaScriptReference() != null)
		{
			this.add(settings.getEmoticonsJavaScriptReference());
		}
	}
}
