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
package com.googlecode.wicket.jquery.ui.markup.html.link;

import org.apache.wicket.Page;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.googlecode.wicket.jquery.core.IJQueryWidget;
import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.ui.JQueryIcon;
import com.googlecode.wicket.jquery.ui.form.button.ButtonBehavior;

/**
 * Provides a jQuery UI button based on a built-in {@code BookmarkablePageLink}
 *
 * @param <T> the model object type
 * @author Sebastien Briquet - sebfz1
 * @since 6.19.0
 * @since 7.0.0
 */
public class BookmarkablePageLink<T> extends org.apache.wicket.markup.html.link.BookmarkablePageLink<T> implements IJQueryWidget
{
	private static final long serialVersionUID = 1L;

	private final String icon;

	/**
	 * Constructor
	 *
	 * @param <C> the type of {@link Page}
	 * @param id the markup id
	 * @param pageClass the class of page to link to
	 */
	public <C extends Page> BookmarkablePageLink(String id, Class<C> pageClass)
	{
		this(id, pageClass, JQueryIcon.NONE);
	}

	/**
	 * Constructor
	 *
	 * @param <C> the type of {@link Page}
	 * @param id the markup id
	 * @param pageClass the class of page to link to
	 * @param icon either a {@link JQueryIcon} constant or a 'ui-icon-xxx' css class
	 */
	public <C extends Page> BookmarkablePageLink(String id, Class<C> pageClass, String icon)
	{
		super(id, pageClass);

		this.icon = icon;
	}

	/**
	 * Constructor
	 *
	 * @param <C> the type of {@link Page}
	 * @param id the markup id
	 * @param pageClass the class of page to link to
	 * @param parameters the parameters to pass to the new page when the link is clicked
	 */
	public <C extends Page> BookmarkablePageLink(String id, Class<C> pageClass, PageParameters parameters)
	{
		this(id, pageClass, parameters, JQueryIcon.NONE);
	}

	/**
	 * Constructor
	 *
	 * @param <C> the type of {@link Page}
	 * @param id the markup id
	 * @param pageClass the class of page to link to
	 * @param parameters the parameters to pass to the new page when the link is clicked
	 * @param icon either a {@link JQueryIcon} constant or a 'ui-icon-xxx' css class
	 */
	public <C extends Page> BookmarkablePageLink(String id, Class<C> pageClass, PageParameters parameters, String icon)
	{
		super(id, pageClass, parameters);

		this.icon = icon;
	}

	// Events //

	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.add(JQueryWidget.newWidgetBehavior(this));
	}

	@Override
	public void onConfigure(JQueryBehavior behavior)
	{
		behavior.setOption("disabled", !this.isEnabledInHierarchy());
	}

	@Override
	public void onBeforeRender(JQueryBehavior behavior)
	{
		// noop
	}

	@Override
	public JQueryBehavior newWidgetBehavior(String selector)
	{
		return new ButtonBehavior(selector, this.icon);
	}
}
