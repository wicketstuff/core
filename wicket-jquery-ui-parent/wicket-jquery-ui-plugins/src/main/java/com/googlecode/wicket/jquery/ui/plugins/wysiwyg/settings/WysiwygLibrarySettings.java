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

import org.apache.wicket.request.resource.ResourceReference;

import com.googlecode.wicket.jquery.ui.plugins.wysiwyg.resource.BootstrapCSSResourceReference;
import com.googlecode.wicket.jquery.ui.plugins.wysiwyg.resource.BootstrapDropDownJavaScriptResourceReference;
import com.googlecode.wicket.jquery.ui.plugins.wysiwyg.resource.BootstrapWysiwygJavaScriptResourceReference;
import com.googlecode.wicket.jquery.ui.plugins.wysiwyg.resource.EditorJavaScriptResourceReference;
import com.googlecode.wicket.jquery.ui.plugins.wysiwyg.resource.EditorStyleSheetResourceReference;
import com.googlecode.wicket.jquery.ui.plugins.wysiwyg.resource.JQueryHotKeysJavaScriptResourceReference;
import com.googlecode.wicket.jquery.ui.plugins.wysiwyg.resource.PrettifyJavaScriptResourceReference;

/**
 * Provides library settings for Wysiwyg resource references<br>
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public class WysiwygLibrarySettings
{
	private static WysiwygLibrarySettings instance = null;

	private ResourceReference bootstrapCssReference = BootstrapCSSResourceReference.get();
	private ResourceReference editorStyleSheetReference = EditorStyleSheetResourceReference.get();
	private ResourceReference editorJavaScriptResourceReference = EditorJavaScriptResourceReference.get();

	private ResourceReference bootstrapDropDownJavaScriptReference = BootstrapDropDownJavaScriptResourceReference.get();
	private ResourceReference bootstrapWysiwygJavaScriptReference = BootstrapWysiwygJavaScriptResourceReference.get();
	private ResourceReference jQueryHotKeysJavaScriptReference = JQueryHotKeysJavaScriptResourceReference.get();
	private ResourceReference prettifyJavaScriptReference = PrettifyJavaScriptResourceReference.get();

	/**
	 * Gets the {@link WysiwygLibrarySettings} instance
	 *
	 * @return the {@link WysiwygLibrarySettings} instance
	 */
	public static synchronized WysiwygLibrarySettings get()
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
	public ResourceReference getBootstrapCssReference()
	{
		return this.bootstrapCssReference;
	}

	public void setBootstrapCssReference(ResourceReference reference)
	{
		this.bootstrapCssReference = reference;
	}

	public ResourceReference getEditorStyleSheetReference()
	{
		return this.editorStyleSheetReference;
	}

	public void setEditorStyleSheetReference(ResourceReference reference)
	{
		this.editorStyleSheetReference = reference;
	}

	// JavaScripts //
	public ResourceReference getBootstrapDropDownJavaScriptReference()
	{
		return this.bootstrapDropDownJavaScriptReference;
	}

	public void setBootstrapDropDownJavaScriptReference(ResourceReference reference)
	{
		this.bootstrapDropDownJavaScriptReference = reference;
	}

	public ResourceReference getBootstrapWysiwygJavaScriptReference()
	{
		return this.bootstrapWysiwygJavaScriptReference;
	}

	public void setBootstrapWysiwygJavaScriptReference(ResourceReference reference)
	{
		this.bootstrapWysiwygJavaScriptReference = reference;
	}

	public ResourceReference getJQueryHotKeysJavaScriptReference()
	{
		return this.jQueryHotKeysJavaScriptReference;
	}

	public void setjQueryHotKeysJavaScriptReference(ResourceReference reference)
	{
		this.jQueryHotKeysJavaScriptReference = reference;
	}

	public ResourceReference getPrettifyJavaScriptReference()
	{
		return this.prettifyJavaScriptReference;
	}

	public void setPrettifyJavaScriptReference(ResourceReference reference)
	{
		this.prettifyJavaScriptReference = reference;
	}

	public ResourceReference getEditorJavaScriptResourceReference()
	{
		return this.editorJavaScriptResourceReference;
	}

	public void setEditorJavaScriptResourceReference(ResourceReference editorJavaScriptResourceReference)
	{
		this.editorJavaScriptResourceReference = editorJavaScriptResourceReference;
	}
}
