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
package com.googlecode.wicket.jquery.ui.plugins;

import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

import com.googlecode.wicket.jquery.ui.JQueryBehavior;
import com.googlecode.wicket.jquery.ui.Options;

public class SnippetBehavior extends JQueryBehavior
{
	private static final long serialVersionUID = 1L;
	private final String language;

	public SnippetBehavior(String selector, String language, Options options)
	{
		super(selector, "snippet", options);

		this.language = language;

		this.add(new JavaScriptResourceReference(SnippetBehavior.class, "jquery.snippet.min.js"));
		this.add(new CssResourceReference(SnippetBehavior.class, "jquery.snippet.min.css"));
	}

	@Override
	protected String $()
	{
		return String.format("jQuery(function() { jQuery('%s').%s('%s', %s); });", super.selector, super.method, this.language, super.options);
	}
}
