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

import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.Options;


/***
 *
 * @author Ludger Kluitmann - JavaLuigi
 *
 */
public abstract class SfMenuBehavior extends JQueryBehavior
{

	private static final long serialVersionUID = 1L;
	private static final String METHOD = "superfish";

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

		this.add(new CssResourceReference(SfMenuBehavior.class, "css/superfish.css"));
		if(verticalSfMenu)
		{
			this.add(new CssResourceReference(SfMenuBehavior.class, "css/superfish-vertical.css"));
		}
		this.add(new JavaScriptResourceReference(SfMenuBehavior.class, "js/hoverIntent.js"));
		this.add(new JavaScriptResourceReference(SfMenuBehavior.class, "js/superfish.min.js"));
	}

	// Properties //
	/**
	 * Gets the reference map of hash/menu-item.<br/>
	 *
	 * @return the non-null {@link Map}
	 */
	protected abstract Map<String, ISfMenuItem> getMenuItemMap();
}
