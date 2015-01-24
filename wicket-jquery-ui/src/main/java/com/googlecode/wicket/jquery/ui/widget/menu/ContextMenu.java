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

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.ComponentTag;

import com.googlecode.wicket.jquery.core.JQueryAbstractBehavior;
import com.googlecode.wicket.jquery.core.Options;

/**
 * Provides a context-menu based on the {@link Menu}
 *
 * @author Sebastien Briquet - sebfz1
 */
public class ContextMenu extends Menu
{
	private static final long serialVersionUID = 1L;

	/** CSS class used to identify a {@link Menu}. It could be useful to perform some jQuery operation on all menu in the page (hiding for instance) */
	public static final String CONTEXTMENU_CSS_CLASS = "context-menu";

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 */
	public ContextMenu(String id)
	{
		super(id);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param items the menu-items
	 */
	public ContextMenu(String id, List<IMenuItem> items)
	{
		super(id, items);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param options {@link Options}
	 */
	public ContextMenu(String id, Options options)
	{
		super(id, options);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param items the menu-items
	 * @param options {@link Options}
	 */
	public ContextMenu(String id, List<IMenuItem> items, Options options)
	{
		super(id, items, options);
	}

	// Properties //

	/**
	 * Gets the jQuery UI position option (as JSON-string) that should be applied on the {@link ContextMenu} when 'contextmenu' event is triggered
	 *
	 * @param component the {@link Component} that fired the 'contextmenu' event
	 * @return the jQuery position option (as string)
	 */
	protected String getPositionOption(Component component)
	{
		return String.format("{ my: 'left top', collision: 'none', of: jQuery('%s') }", JQueryWidget.getSelector(component));
	}

	// Events //

	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.add(this.newContextMenuDocumentBehavior());
	}

	@Override
	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);

		tag.append("class", CONTEXTMENU_CSS_CLASS, " ");
		tag.append("style", "position: absolute; display: none;", ";");
	}

	/**
	 * Fired by a component that holds a {@link ContextMenuBehavior}
	 *
	 * @param target the {@link AjaxRequestTarget}
	 * @param component the component that holds a {@link ContextMenuBehavior}
	 */
	void fireOnContextMenu(AjaxRequestTarget target, Component component)
	{
		this.onContextMenu(target, component);

		target.add(this);
		target.appendJavaScript(String.format("jQuery('%s').show().position(%s);", JQueryWidget.getSelector(this), this.getPositionOption(component)));
	}

	/**
	 * Triggered when 'contextmenu' event is triggered by a component that holds a {@link ContextMenuBehavior}
	 *
	 * @param target the {@link AjaxRequestTarget}
	 * @param component the component that holds a {@link ContextMenuBehavior}
	 */
	protected void onContextMenu(AjaxRequestTarget target, Component component)
	{
		// noop
	}

	// Factories //
	/**
	 * Gets a new {@link JQueryAbstractBehavior} that handles the closing of the context-menu
	 *
	 * @return a {@link JQueryAbstractBehavior}
	 */
	protected JQueryAbstractBehavior newContextMenuDocumentBehavior()
	{
		return new JQueryAbstractBehavior("context-menu-document") {

			private static final long serialVersionUID = 1L;

			@Override
			public String $()
			{
				StringBuilder builder = new StringBuilder();
				String selector = JQueryWidget.getSelector(ContextMenu.this);

				// hide on click (outside invoker area) //
				builder.append("jQuery(document).click(function(e) { if (!(jQuery(e.target).is('.").append(ContextMenuBehavior.INVOKER_CSS_CLASS).append("'))) { jQuery('").append(selector).append("').hide(); } } );\n");

				// hide on escape //
				builder.append("jQuery(document).keyup(function(e) { if (e.which == 27) { jQuery('").append(selector).append("').hide(); } });\n");

				return builder.toString();
			}
		};
	}
}
