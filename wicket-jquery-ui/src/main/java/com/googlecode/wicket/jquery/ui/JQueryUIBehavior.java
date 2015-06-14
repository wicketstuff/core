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
package com.googlecode.wicket.jquery.ui;

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.JQueryDestroyListener.IDestroyable;
import com.googlecode.wicket.jquery.ui.settings.JQueryUILibrarySettings;

/**
 * Provides the base class for every jQuery behavior.
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public class JQueryUIBehavior extends JQueryBehavior implements IDestroyable
{
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(JQueryUIBehavior.class);

	/**
	 * Constructor
	 *
	 * @param selector the html selector (ie: "#myId")
	 * @param method the kendo-ui method
	 */
	public JQueryUIBehavior(String selector, String method)
	{
		this(selector, method, new Options());
	}

	/**
	 * Constructor
	 *
	 * @param selector the html selector (ie: "#myId")
	 * @param method the kendo-ui method
	 * @param options the {@link Options}
	 */
	public JQueryUIBehavior(String selector, String method, Options options)
	{
		super(selector, method, options);

		this.initReferences();
	}
	
	// Methods //

	/**
	 * Initializes CSS & JavaScript resource references
	 */
	private void initReferences()
	{
		JQueryUILibrarySettings settings = JQueryUILibrarySettings.get();

		if (settings.getJavaScriptReference() != null)
		{
			this.add(settings.getJavaScriptReference());
		}

		if (settings.getStyleSheetReference() != null)
		{
			this.add(settings.getStyleSheetReference());
		}
	}

	/**
	 * Gets the jQuery UI object
	 *
	 * @return the jQuery UI object (if exists, 'undefined' otherwise)
	 */
	public String widget()
	{
		return this.widget(this.method);
	}

	/**
	 * Gets the jQuery UI object
	 *
	 * @param method the jQuery UI method
	 * @return the jQuery UI object (if exists, 'undefined' otherwise)
	 */
	protected String widget(String method)
	{
		return String.format("jQuery('%s').%s('instance')", this.selector, method);
	}

	@Override
	public void destroy(AjaxRequestTarget target)
	{
		target.prependJavaScript(this.$(Options.asString("destroy")));

		this.onDestroy(target);
	}

	// Events //

	@Override
	public void onConfigure(Component component)
	{
		super.onConfigure(component);

		if (!Application.get().getMarkupSettings().getStripWicketTags())
		{
			LOG.warn("Application > MarkupSettings > StripWicketTags: setting is currently set to false. It is highly recommended to set it to true to prevent widget misbehaviors.");
		}
	}

	/**
	 * Called when the widget is about to be destroyed
	 * 
	 * @param target the {@link AjaxRequestTarget}
	 * @see #destroy(AjaxRequestTarget)
	 */
	protected void onDestroy(AjaxRequestTarget target)
	{
		// noop
	}
}
