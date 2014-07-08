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
package com.googlecode.wicket.kendo.ui.widget.menu.item;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.kendo.ui.KendoIcon;

/**
 * Provides a menu-item that redirect to a url
 *
 * @author Sebastien Briquet - sebfz1
 * @since 6.15.0
 */
public class UrlMenuItem extends AbstractMenuItem
{
	private static final long serialVersionUID = 1L;

	private final CharSequence url;

	/**
	 * Constructor
	 *
	 * @param title the title of the menu-item
	 * @param url the url to redirect to
	 */
	public UrlMenuItem(String title, CharSequence url)
	{
		this(Model.of(title), url);
	}

	/**
	 * Constructor
	 *
	 * @param title the title of the menu-item
	 * @param icon either a {@link KendoIcon} constant or a 'k-i-<i>icon</i>' css class
	 * @param url the url to redirect to
	 */
	public UrlMenuItem(String title, String icon, CharSequence url)
	{
		this(Model.of(title), icon, url);
	}

	/**
	 * Constructor
	 *
	 * @param title IModel that represent the title of the menu-item
	 * @param url the url to redirect to
	 */
	public UrlMenuItem(IModel<String> title, CharSequence url)
	{
		this(title, KendoIcon.NONE, url);
	}

	/**
	 * Constructor
	 *
	 * @param title IModel that represent the title of the menu-item
	 * @param icon either a {@link KendoIcon} constant or a 'k-i-<i>icon</i>' css class
	 * @param url the url to redirect to
	 */
	public UrlMenuItem(IModel<String> title, String icon, CharSequence url)
	{
		super(title, icon);

		this.url = url;
	}

	// Events //

	@Override
	public void onClick(AjaxRequestTarget target)
	{
		target.appendJavaScript(String.format("location.href='%s'", this.url));
	}
}
