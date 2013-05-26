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
package com.googlecode.wicket.jquery.ui.kendo.settings;

import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.settings.IJavaScriptLibrarySettings;

/**
 * Provides library settings for Kendo UI resource references<br/>
 * <br/>
 * Usage:
 * <pre><code>
 * public class MyApplication extends WebApplication
 * {
 * 	public void init()
 * 	{
 * 		super.init();
 *
 * 		this.getMarkupSettings().setStripWicketTags(true);
 * 		this.setJavaScriptLibrarySettings(new MyJQueryLibrarySettings());
 * 	}
 *
 * 	static class MyJQueryLibrarySettings extends JQueryLibrarySettings implements IKendoUILibrarySettings
 * 	{
 * 		private static ResourceReference THEME_CSS_RR = new CssResourceReference(...);
 * 		private static ResourceReference COMMON_CSS_RR = new CssResourceReference(...);
 * 		private static ResourceReference JAVASCRIPT_RR = new JavaScriptResourceReference(...);
 *
 * 		public ResourceReference getKendoUICommonStyleSheetReference()
 * 		{
 * 			return COMMON_CSS_RR; //may be null; will use the one declared in the markup
 * 		}
 *
 * 		public ResourceReference getKendoUIThemeStyleSheetReference()
 * 		{
 * 			return THEME_CSS_RR; //may be null; will use the one declared in the markup
 * 		}
 *
 * 		public ResourceReference getKendoUIJavaScriptReference()
 * 		{
 * 			return JAVASCRIPT_RR; //may be null; will use the embedded one
 * 		}
 * 	}
 * }
 * </code></pre>
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public interface IKendoUILibrarySettings extends IJavaScriptLibrarySettings
{
	/**
	 * Gets the Kendo UI common stylesheet resource reference
	 *
	 * @return the {@link ResourceReference}
	 */
	ResourceReference getKendoUICommonStyleSheetReference();

	/**
	 * Gets the Kendo UI theme stylesheet resource reference
	 *
	 * @return the {@link ResourceReference}
	 */
	ResourceReference getKendoUIThemeStyleSheetReference();

	/**
	 * Gets the Kendo UI javascript resource reference
	 *
	 * @return the {@link ResourceReference}
	 */
	ResourceReference getKendoUIJavaScriptReference();
}
