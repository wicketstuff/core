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
package com.googlecode.wicket.jquery.core.resource;

import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;

import com.googlecode.wicket.jquery.core.settings.JQueryLibrarySettings;

/**
 * {@link HeaderItem} in charge of adding the jQuery Globalize library<br>
 * Usage:<br>
 * <code>
 * <pre>
 * public void renderHead(IHeaderResponse response)
 * {
 * 	super.renderHead(response);
 * 	
 * 	response.render(new JQueryGlobalizeHeaderItem());
 * }
 * 
 * @author Sebastien Briquet - sebfz1
 * @see JQueryLibrarySettings#getJQueryGlobalizeReference()
 *
 */
public class JQueryGlobalizeHeaderItem extends JavaScriptReferenceHeaderItem
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 */
	public JQueryGlobalizeHeaderItem()
	{
		super(JQueryGlobalizeResourceReference.get(), null, "jquery-globalize", false, null, null);
	}
}
