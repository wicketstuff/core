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

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.ResourceReference;

import com.googlecode.wicket.jquery.ui.settings.JQueryLibrarySettings;

/**
 * Provides the base class for every jQuery behavior.
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public abstract class JQueryAbstractBehavior extends Behavior
{
	private static final long serialVersionUID = 1L;

	/**
	 * Behavior name
	 */
	private final String name;

	/**
	 * Additional references
	 */
	private final List<ResourceReference> references;

	/**
	 * Constructor.
	 * @param name the name of the behavior. It is used in the token so the behavior can be identified in the generated page.
	 */
	public JQueryAbstractBehavior(final String name)
	{
		this.name = name;
		this.references = new ArrayList<ResourceReference>();
	}


	/**
	 * Adds a reference to be added at {@link #renderHead(Component, IHeaderResponse)} time.
	 * @param reference a {@link CssResourceReference} or a {@link JavaScriptResourceReference}
	 * @return true (as specified by Collection.add(E))
	 */
	public boolean add(ResourceReference reference)
	{
		return this.references.add(reference);
	}

	@Override
	public void renderHead(Component component, IHeaderResponse response)
	{
		// Adds jQuery Core javascript resource reference //
		if (JQueryLibrarySettings.getJQueryReference() != null)
		{
			response.renderJavaScriptReference(JQueryLibrarySettings.getJQueryReference());
		}

		// Adds jQuery UI javascript resource reference //
		if (JQueryLibrarySettings.getJQueryUIReference() != null)
		{
			response.renderJavaScriptReference(JQueryLibrarySettings.getJQueryUIReference());
		}

		// Adds jQuery Globalize javascript resource reference //
		if (JQueryLibrarySettings.getJQueryGlobalizeReference() != null)
		{
			response.renderJavaScriptReference(JQueryLibrarySettings.getJQueryGlobalizeReference());
		}

		// Adds additional resource references //
		for(ResourceReference reference : this.references)
		{
			if (reference instanceof JavaScriptResourceReference)
			{
				response.renderJavaScriptReference(reference);
			}

			if (reference instanceof CssResourceReference)
			{
				response.renderCSSReference(reference);
			}
		}

		// Adds the statement //
		response.renderJavaScript(this.toString(), this.getToken());
	}

	/**
	 * Get the unique behavior token that act as the script id.
	 * @return the token
	 */
	String getToken()
	{
		return String.format("jquery-%s-%d", this.name, this.hashCode());
	}

	/**
	 * <code>
	 * http://blog.nemikor.com/2009/04/08/basic-usage-of-the-jquery-ui-dialog/
	 * </code>
	 */
	@Override
	public void beforeRender(Component component)
	{
		AjaxRequestTarget target = AjaxRequestTarget.get();

		if (target != null)
		{
			target.appendJavaScript(this.toString());
		}
	}

	/**
	 * Gets the jQuery statement.
	 * @return Statement like 'jQuery(function() { ... })'
	 */
	protected abstract String $();

	@Override
	public final String toString()
	{
		return this.$();
	}
}
