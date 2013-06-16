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
package com.googlecode.wicket.jquery.ui.widget.menu;

import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;

import com.googlecode.wicket.jquery.core.JQueryAbstractBehavior;
import com.googlecode.wicket.jquery.core.Options;

/**
 * Provides a context-menu based on the {@link Menu}
 *
 * @author Sebastien Briquet - sebfz1
 */
//TODO remove 6.8.2-SNAPSHOT in description
public class ContextMenu extends Menu
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * @param id the markup id
	 */
	public ContextMenu(String id)
	{
		super(id);
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param items the menu-items
	 */
	public ContextMenu(String id, List<IMenuItem> items)
	{
		super(id, items);
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param options {@link Options}
	 */
	public ContextMenu(String id, Options options)
	{
		super(id, options);
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param items the menu-items
	 * @param options {@link Options}
	 */
	public ContextMenu(String id, List<IMenuItem> items, Options options)
	{
		super(id, options);
	}


	// Events //
	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.add(AttributeModifier.append("style", "position: absolute; display: none;"));
		this.add(this.newContextMenuDocumentBehavior());
	}

	/**
	 * Fired by a component that holds a {@link ContextMenuBehavior}
	 * @param target the {@link AjaxRequestTarget}
	 * @param component the component that holds a {@link ContextMenuBehavior}
	 */
	void fireOnContextMenu(AjaxRequestTarget target, Component component)
	{
		this.onContextMenu(target, component);

		target.add(this);
		target.appendJavaScript(String.format("jQuery(function() { jQuery('%s').show().position({ collision: 'none', my: 'left top', of: jQuery('%s') }); });", JQueryWidget.getSelector(this), JQueryWidget.getSelector(component)));
	}

	/**
	 * Triggered when 'contextmenu' event is triggered by a component that holds a {@link ContextMenuBehavior}
	 * @param target the {@link AjaxRequestTarget}
	 * @param component the component that holds a {@link ContextMenuBehavior}
	 */
	protected void onContextMenu(AjaxRequestTarget target, Component component)
	{
	}


	// Factories //
	/**
	 * Gets a new {@link JQueryAbstractBehavior} that handles the closing of the context-menu
	 * @return a {@link JQueryAbstractBehavior}
	 */
	protected JQueryAbstractBehavior newContextMenuDocumentBehavior()
	{
		return new JQueryAbstractBehavior("context-menu-document") {

			private static final long serialVersionUID = 1L;

			@Override
			protected String $()
			{
				String selector = JQueryWidget.getSelector(ContextMenu.this);

				StringBuilder builder = new StringBuilder();
				builder.append("jQuery(function() {\n");
				builder.append("jQuery(document).click(function(e) { if (!(jQuery(e.target).is('.").append(ContextMenuBehavior.COMPONENT_CSS).append("') && e.which == 3)) { jQuery('").append(selector).append("').hide(); } } );\n"); // hide on click (assume context-menu click is the right-click)
				builder.append("jQuery(document).keyup(function(e) { if (e.which == 27) { jQuery('").append(selector).append("').hide(); } });\n"); // hide on escape
				builder.append("});");

				return builder.toString();
			}
		};
	}
}
