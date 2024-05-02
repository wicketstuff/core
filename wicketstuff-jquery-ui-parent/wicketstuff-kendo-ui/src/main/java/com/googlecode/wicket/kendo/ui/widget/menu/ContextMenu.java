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
package com.googlecode.wicket.kendo.ui.widget.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;

import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.ajax.IJQueryAjaxAware;
import com.googlecode.wicket.jquery.core.ajax.JQueryAjaxBehavior;
import com.googlecode.wicket.kendo.ui.widget.menu.item.IMenuItem;

/**
 * Provides the Kendo UI context menu
 *
 * @author Martin Grigorov - martin-g
 * @since 6.20.0
 */
public class ContextMenu extends Menu implements IContextMenuListener
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 */
	public ContextMenu(String id)
	{
		this(id, new ArrayList<IMenuItem>());
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param items the list of {@link IMenuItem}
	 */
	public ContextMenu(String id, List<IMenuItem> items)
	{
		super(id, items);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param options the {@link Options}
	 */
	public ContextMenu(String id, Options options)
	{
		super(id, options);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param items the list of {@link IMenuItem}
	 * @param options the {@link Options}
	 */
	public ContextMenu(String id, List<IMenuItem> items, Options options)
	{
		super(id, items, options);
	}

	// Properties //

	@Override
	public boolean isOpenEventEnabled()
	{
		return false;
	}

	// Events //

	@Override
	public void onOpen(AjaxRequestTarget target)
	{
		// noop
	}

	// Factories //

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be wired to the 'open' event
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @return a new {@code OnOpenAjaxBehavior} by default
	 */
	protected JQueryAjaxBehavior newOnOpenAjaxBehavior(IJQueryAjaxAware source)
	{
		return new ContextMenuBehavior.OnOpenAjaxBehavior(source);
	}

	// IJQueryWidget //

	@Override
	public ContextMenuBehavior newWidgetBehavior(String selector)
	{
		return new ContextMenuBehavior(selector, this.options, this) {

			private static final long serialVersionUID = 1L;

			// properties //

			@Override
			protected Map<String, IMenuItem> getMenuItemMap()
			{
				return ContextMenu.this.getMenuItemsMap();
			}

			// factories //

			@Override
			protected JQueryAjaxBehavior newOnOpenAjaxBehavior(IJQueryAjaxAware source)
			{
				return ContextMenu.this.newOnOpenAjaxBehavior(source);
			}
		};
	}
}
