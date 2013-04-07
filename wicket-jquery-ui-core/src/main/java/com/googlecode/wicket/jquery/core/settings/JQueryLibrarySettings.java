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
package com.googlecode.wicket.jquery.core.settings;

import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.settings.def.JavaScriptLibrarySettings;

import com.googlecode.wicket.jquery.core.resource.JQueryUIResourceReference;

/**
 * Default implementation of {@link IJQueryLibrarySettings}.<br/>
 *
 * <code><pre>
 * public class MyApplication extends WebApplication
 * {
 *     public void init()
 *     {
 *         super.init();
 *
 *         IJQueryLibrarySettings librarySettings = new JQueryLibrarySettings();
 *         librarySettings.setJQueryUIReference(new JQueryPluginResourceReference(MyApplication.class, "jquery-ui-x.x.x.min.js"));
 *
 *         //to enable globalization:
 *         librarySettings.setJQueryGlobalizeReference(JQueryGlobalizeResourceReference.get());
 *
 *         this.setJavaScriptLibrarySettings(librarySettings);
 *     }
 * }
 * <pre></code>
 *
 * <b>Note:</b> The reference given as parameter can be null.
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public class JQueryLibrarySettings extends JavaScriptLibrarySettings implements IJQueryLibrarySettings
{
	private ResourceReference jQueryUIReference = JQueryUIResourceReference.get();
	private ResourceReference jQueryGlobalizeReference = null; //null by default, meaning the user has to set it explicitly

	// jQuery UI //

	@Override
	public ResourceReference getJQueryUIReference()
	{
		return this.jQueryUIReference;
	}

	@Override
	public void setJQueryUIReference(ResourceReference reference)
	{
		this.jQueryUIReference = reference;
	}

	// jQuery Globalize //

	@Override
	public ResourceReference getJQueryGlobalizeReference()
	{
		return this.jQueryGlobalizeReference;
	}

	@Override
	public void setJQueryGlobalizeReference(ResourceReference reference)
	{
		this.jQueryGlobalizeReference = reference;
	}

}
