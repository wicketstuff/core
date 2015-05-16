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
package com.googlecode.wicket.kendo.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.PriorityHeaderItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.kendo.ui.settings.KendoUILibrarySettings;

/**
 * Provides the base class for Kendo UI behavior implementations
 *
 * @author Sebastien Briquet - sebfz1
 */
public class KendoUIBehavior extends JQueryBehavior
{
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(KendoUIBehavior.class);

	/** Data Sources */
	private List<IKendoDataSource> datasources = null;

	/**
	 * Constructor
	 *
	 * @param selector the html selector (ie: "#myId")
	 * @param method the kendo-ui method
	 */
	public KendoUIBehavior(String selector, String method)
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
	public KendoUIBehavior(String selector, String method, Options options)
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
		KendoUILibrarySettings settings = KendoUILibrarySettings.get();

		// kendo.common.min.css //
		if (settings.getCommonStyleSheetReference() != null)
		{
			this.add(settings.getCommonStyleSheetReference());
		}

		// kendo.<theme>.min.css //
		if (settings.getThemeStyleSheetReference() != null)
		{
			this.add(settings.getThemeStyleSheetReference());
		}

		// kendo.ui.core.js //
		if (settings.getJavaScriptReference() != null)
		{
			this.add(settings.getJavaScriptReference());
		}
	}

	/**
	 * Adds a data-source to be rendered at {@link #renderHead(Component, IHeaderResponse)} time.
	 *
	 * @param datasource the {@link IKendoDataSource}
	 * @return {@code true} (as specified by {@link Collection#add})
	 */
	public boolean add(IKendoDataSource datasource)
	{
		if (this.datasources == null)
		{
			synchronized (this)
			{
				this.datasources = new ArrayList<IKendoDataSource>();
			}
		}

		return this.datasources.add(datasource);
	}

	@Override
	public void renderHead(Component component, IHeaderResponse response)
	{
		super.renderHead(component, response);

		// Data Sources //
		if (this.datasources != null)
		{
			for (IKendoDataSource datasource : this.datasources)
			{
				response.render(new PriorityHeaderItem(JavaScriptHeaderItem.forScript(datasource.prepareRender().toScript(), datasource.getToken())));
			}
		}
	}

	/**
	 * Gets the Kendo jQuery object
	 *
	 * @return the the jQuery object
	 */
	public String widget()
	{
		return this.widget(this.method);
	}

	/**
	 * Gets the Kendo jQuery object
	 *
	 * @param method the Kendo method
	 * @return the the jQuery object
	 */
	protected String widget(String method)
	{
		return String.format("jQuery('%s').data('%s')", this.selector, method);
	}
	
	/**
	 * Prepares the widget for safe removal from the DOM. Detaches all event handlers and removes jQuery.data attributes to avoid memory leaks. Calls destroy method of any child Kendo widgets.
	 * 
	 * @param target the {@link AjaxRequestTarget}
	 */
	public void destroy(AjaxRequestTarget target)
	{
		target.prependJavaScript(this.widget() + ".destroy();");
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
}
