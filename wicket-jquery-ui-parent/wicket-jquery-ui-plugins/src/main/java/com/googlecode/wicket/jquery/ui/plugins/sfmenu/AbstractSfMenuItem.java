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

import java.util.Collections;
import java.util.List;

import org.apache.wicket.Page;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.lang.Args;
import org.apache.wicket.util.reference.ClassReference;

/**
 * Base class for {@link SfMenuItem} item, adapted for Superfish
 *
 * @author Ludger Kluitmann - JavaLuigi
 * @author Sebastien Briquet - sebfz1
 * @since 6.12.0
 */
public abstract class AbstractSfMenuItem implements ISfMenuItem
{
	private static final long serialVersionUID = 1L;

	private IModel<String> title;
	private boolean enabled = true;
	private boolean openInNewWindow = false;
	
	private final ClassReference<? extends Page> pageClassReference;
	private final PageParameters pageParameters;
	
	private final String pageUrl;

	/**
	 * Constructor
	 *
	 * @param title {@link IModel} that represent the title of the menu-item
	 */
	public AbstractSfMenuItem(IModel<String> title)
	{
		this.title = title;
		this.pageClassReference = null;
		this.pageParameters = null;
		this.pageUrl = null;
	}

	/**
	 * Constructor
	 *
	 * @param title {@link IModel} that represent the title of the menu-item
	 * @param pageClass the class of the page to redirect to when menu-item is clicked
	 */
	public AbstractSfMenuItem(IModel<String> title, Class<? extends Page> pageClass)
	{
		Args.notNull(pageClass, "pageClass");

		this.title = title;
		this.pageClassReference = ClassReference.of(pageClass);
		this.pageParameters = new PageParameters();
		this.pageUrl = null;
	}

	/**
	 * Constructor
	 *
	 * @param title {@link IModel} that represent the title of the menu-item
	 * @param pageClass the class of the page to redirect to when menu-item is clicked
	 * @param pageParameters the {@link PageParameters}
	 */
	public AbstractSfMenuItem(IModel<String> title, Class<? extends Page> pageClass, PageParameters pageParameters)
	{
		Args.notNull(pageClass, "pageClass");

		this.title = title;
		this.pageClassReference = ClassReference.of(pageClass);
		this.pageParameters = pageParameters;
		this.pageUrl = null;
	}

	/**
	 * Constructor
	 *
	 * @param title {@link IModel} that represent the title of the menu-item
	 * @param pageUrl the url of the page to redirect to when menu-item is clicked
	 */
	public AbstractSfMenuItem(IModel<String> title, String pageUrl)
	{
		this.title = title;
		this.pageClassReference = null;
		this.pageParameters = null;
		this.pageUrl = pageUrl;
	}

	/**
	 * Constructor
	 *
	 * @param title {@link IModel} that represent the title of the menu-item
	 * @param pageUrl the url of the page to redirect to when menu-item is clicked
	 * @param openInNewWindow whether the page is opened in a new window
	 */
	public AbstractSfMenuItem(IModel<String> title, String pageUrl, boolean openInNewWindow)
	{
		this.title = title;
		this.pageClassReference = null;
		this.pageParameters = null;
		this.pageUrl = pageUrl;
		this.openInNewWindow = openInNewWindow;
	}

	@Override
	public IModel<String> getTitle()
	{
		return this.title;
	}

	/**
	 * Sets the menu-item title
	 *
	 * @param title the menu-item title
	 */
	public void setTitle(IModel<String> title)
	{
		this.title = title;
	}

	@Override
	public List<ISfMenuItem> getItems()
	{
		return Collections.emptyList();
	}

	@Override
	public boolean isEnabled()
	{
		return this.enabled;
	}

	@Override
	public boolean isOpenInNewWindow()
	{
		return this.openInNewWindow;
	}

	/**
	 * Set whether the menu-item is enabled
	 *
	 * @param enabled {@code true} or {@code false}
	 */
	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}

	/**
	 * Set whether a page is opened in a new window
	 *
	 * @param openInNewWindow {@code true} or {@code false}
	 */
	public void setOpenInNewWindow(boolean openInNewWindow)
	{
		this.openInNewWindow = openInNewWindow;
	}

	/**
	 * Get the page class registered with the link
	 *
	 * @return the page class
	 */
	@Override
	public Class<? extends Page> getPageClass()
	{
		if (this.pageClassReference != null)
		{
			return this.pageClassReference.get();
		}

		return null;
	}
	
	@Override
	public PageParameters getPageParameters()
	{
		return this.pageParameters;
	}

	/**
	 * Get the url for a page. In most cases this will
	 * be an url for an external page.
	 *
	 * @return url of the page
	 */
	@Override
	public String getPageUrl()
	{
		return pageUrl;
	}
}
