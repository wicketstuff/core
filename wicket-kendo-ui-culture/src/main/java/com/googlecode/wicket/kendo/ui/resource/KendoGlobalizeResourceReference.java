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
package com.googlecode.wicket.kendo.ui.resource;

import java.util.Locale;

import org.apache.wicket.resource.JQueryPluginResourceReference;

import com.googlecode.wicket.kendo.ui.KendoCulture;

/**
 * The resource reference for the Kendo Globalize javascript library.<br/>
 * <br/>
 * <b>Warning:</b> When globalize.js is registered before Kendo scripts, then Kendo will use globalize.js features instead of Kendo Globalization.
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public class KendoGlobalizeResourceReference extends JQueryPluginResourceReference
{
	private static final long serialVersionUID = 1L;

	public static final String FILENAME_PATTERN = "kendo.culture.%s.js";

	/**
	 * Constructor
	 *
	 * @param locale the {@link Locale}
	 */
	public KendoGlobalizeResourceReference(Locale locale)
	{
		this(locale.toLanguageTag()); // java7
	}

	/**
	 * Constructor
	 *
	 * @param culture the {@link KendoCulture}
	 */
	public KendoGlobalizeResourceReference(KendoCulture culture)
	{
		this(culture.toString());
	}

	/**
	 * Constructor
	 *
	 * @param culture the culture, ie: 'fr' or 'fr-FR'
	 */
	public KendoGlobalizeResourceReference(String culture)
	{
		super(KendoGlobalizeResourceReference.class, String.format(FILENAME_PATTERN, culture));
	}
}
