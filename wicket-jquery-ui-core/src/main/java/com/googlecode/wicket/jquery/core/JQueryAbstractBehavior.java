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
package com.googlecode.wicket.jquery.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.head.PriorityHeaderItem;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.resource.JQueryPluginResourceReference;
import org.apache.wicket.settings.JavaScriptLibrarySettings;

import com.googlecode.wicket.jquery.core.settings.JQueryLibrarySettings;

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
	 * Gets the {@link JQueryLibrarySettings}
	 *
	 * @return The {@link JQueryLibrarySettings} or {@code null} if {@link Application}'s {@link JavaScriptLibrarySettings} is not an instance of {@link JQueryLibrarySettings}
	 */
	public static JQueryLibrarySettings getJQueryLibrarySettings()
	{
		if (Application.exists() && (Application.get().getJavaScriptLibrarySettings() instanceof JQueryLibrarySettings))
		{
			return (JQueryLibrarySettings) Application.get().getJavaScriptLibrarySettings();
		}

		return null;
	}

	/** Additional references */
	private final List<ResourceReference> references;

	/**
	 * Constructor.
	 */
	public JQueryAbstractBehavior()
	{
		this.references = new ArrayList<ResourceReference>();
	}

	/**
	 * Adds a reference to be rendered at {@link #renderHead(Component, IHeaderResponse)} time.
	 *
	 * @param reference a {@link CssResourceReference}, a {@link JavaScriptResourceReference} or a {@link JQueryPluginResourceReference}
	 * @return {@code true} (as specified by {@link Collection#add})
	 */
	public final boolean add(ResourceReference reference)
	{
		return this.references.add(reference);
	}

	@Override
	public void renderHead(Component component, IHeaderResponse response)
	{
		// Gets the library settings //
		JQueryLibrarySettings settings = getJQueryLibrarySettings();

		// jQuery Globalize resource reference //
		if (settings != null && settings.getJQueryGlobalizeReference() != null)
		{
			this.renderPriorityHeaderItem(JavaScriptHeaderItem.forReference(settings.getJQueryGlobalizeReference()), response);
		}

		// Additional resource references //
		for (ResourceReference reference : this.references)
		{
			if (reference instanceof CssResourceReference)
			{
				this.renderPriorityHeaderItem(CssHeaderItem.forReference(reference), response);
			}

			if (reference instanceof JavaScriptResourceReference)
			{
				this.renderPriorityHeaderItem(JavaScriptHeaderItem.forReference(reference), response);
			}
		}

		// Adds the statement //
		String statement = this.$();

		if (statement != null)
		{
			this.renderOnDomReadyScript(statement, response);
		}
	}

	/**
	 * Renders a priority {@code HeaderItem}
	 *
	 * @param item the {@link HeaderItem}
	 * @param response the {@link IHeaderResponse}
	 */
	protected void renderPriorityHeaderItem(HeaderItem item, IHeaderResponse response)
	{
		response.render(new PriorityHeaderItem(item));
	}

	/**
	 * Renders the javascript 'on-dom-ready'<br>
	 * This can be overridden to provides a priority:<br>
	 * {@code response.render(new PriorityHeaderItem(script));}
	 *
	 * @param script the {@link JavaScriptHeaderItem}
	 * @param response the {@link IHeaderResponse}
	 */
	protected void renderOnDomReadyScript(String script, IHeaderResponse response)
	{
		response.render(OnDomReadyHeaderItem.forScript(script));
	}

	/**
	 * Gets the jQuery statement.
	 *
	 * @return the jQuery statement
	 */
	protected abstract String $();

	// Properties //

	/**
	 * Gets the jQuery statement.
	 *
	 * @return {@link #$()}
	 */
	@Override
	public String toString()
	{
		return this.$();
	}
}
