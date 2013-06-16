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
package com.googlecode.wicket.jquery.ui.plugins.wysiwyg;

import org.apache.wicket.Application;
import org.apache.wicket.markup.html.IPackageResourceGuard;
import org.apache.wicket.markup.html.SecurePackageResourceGuard;
import org.apache.wicket.markup.html.SecurePackageResourceGuard.SearchPattern;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.Options;

public class WysiwygBehavior extends JQueryBehavior
{
	private static final long serialVersionUID = 1L;
	private static final String METHOD = "wysiwyg";

	// Bootstrap resources //
	private static final JavaScriptResourceReference BOOTSTRAP = new JavaScriptResourceReference(WysiwygBehavior.class, "js/bootstrap.min.js");
	private static final CssResourceReference BOOTSTRAP_COMBINED = new CssResourceReference(WysiwygBehavior.class, "css/bootstrap-combined.no-icons.min.css");
	private static final CssResourceReference BOOTSTRAP_RESPONSIVE = new CssResourceReference(WysiwygBehavior.class, "css/bootstrap-responsive.min.css");

	// Wysiwyg resources //
	private static final JavaScriptResourceReference WYSIWYG = new JavaScriptResourceReference(WysiwygBehavior.class, "js/bootstrap-wysiwyg.js");
	private static final JavaScriptResourceReference HOTKEYS = new JavaScriptResourceReference(WysiwygBehavior.class, "js/jquery.hotkeys.js");
	private static final JavaScriptResourceReference PRETTIFY = new JavaScriptResourceReference(WysiwygBehavior.class, "js/prettify.js");
	private static final CssResourceReference FONT_AWESOME = new CssResourceReference(WysiwygBehavior.class, "css/font-awesome.css");
	private static final CssResourceReference EDITOR = new CssResourceReference(WysiwygBehavior.class, "css/editor.css");

	public WysiwygBehavior(String selector)
	{
		this(selector, new Options());
	}

	//TODO: andunslg / solomax - Configurable resource references should be created
	public WysiwygBehavior(String selector, Options options)
	{
		super(selector, METHOD, options);

		this.initReferences();

		// init resource guard //
		IPackageResourceGuard packageResourceGuard = Application.get().getResourceSettings().getPackageResourceGuard();

		if (packageResourceGuard instanceof SecurePackageResourceGuard)
		{
			SecurePackageResourceGuard guard = (SecurePackageResourceGuard) packageResourceGuard;

			if (!guard.getPattern().contains(new SearchPattern("+*.eot")))
			{
				guard.addPattern("+*.eot");
				guard.addPattern("+*.woff");
				guard.addPattern("+*.ttf");
			}
		}
	}

	private void initReferences()
	{
		this.add(BOOTSTRAP_COMBINED);
		this.add(BOOTSTRAP_RESPONSIVE);
		this.add(FONT_AWESOME);
		this.add(EDITOR);
		this.add(WYSIWYG);
		this.add(BOOTSTRAP);
		this.add(HOTKEYS);
		this.add(PRETTIFY);
	}
}
