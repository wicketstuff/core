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

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.plugins.sfmenu.resource.HoverIntentJavaScriptResourceReference;
import com.googlecode.wicket.jquery.ui.plugins.sfmenu.resource.SuperfishJavaScriptResourceReference;
import com.googlecode.wicket.jquery.ui.plugins.sfmenu.settings.SuperfishLibrarySettings;

/***
 * Provides the Superfish menu plugin behavior
 *
 * @author Ludger Kluitmann - JavaLuigi
 * @since 6.12.0
 */
public class SfMenuBehavior extends JQueryBehavior
{
	private static final long serialVersionUID = 1L;
	private static final String METHOD = "superfish";

	/***
	 * Constructor
	 *
	 * @param selector the html selector (ie: "#myId")
	 */
	public SfMenuBehavior(String selector)
	{
		this(selector, new Options(), false);
	}

	/***
	 * Constructor
	 *
	 * @param selector the html selector (ie: "#myId")
	 * @param options the {@link Options}
	 * @param isVertical indicates whether the menu is a vertical menu
	 */
	public SfMenuBehavior(String selector, Options options, Boolean isVertical)
	{
		super(selector, METHOD, options);

		SuperfishLibrarySettings settings = SuperfishLibrarySettings.get();

		if (settings.getStyleSheetReference() != null)
		{
			this.add(settings.getStyleSheetReference());
		}

		if (isVertical && settings.getVerticalStyleSheetReference() != null)
		{
			this.add(settings.getVerticalStyleSheetReference());
		}

		this.add(HoverIntentJavaScriptResourceReference.get());
		this.add(SuperfishJavaScriptResourceReference.get());
	}
}
