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
package com.googlecode.wicket.kendo.ui.widget.menu.form;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;

import com.googlecode.wicket.kendo.ui.widget.menu.Menu;
import com.googlecode.wicket.kendo.ui.widget.menu.item.IMenuItem;

/**
 * A specialization of {@link Form} that could be used for menu items with form components.<br/>
 * Suppresses the JavaScript click event for the whole menu item but the underlying &lt;button&gt;s, so the 'select' event is still fired and {@link Menu#onClick(AjaxRequestTarget, IMenuItem)} triggered<br/>
 * <br/>
 * It is recommended to use {@link MenuItemAjaxButton} for underlying buttons because bubbling is already handled
 * 
 * @author Martin Grigorov - martin-g
 * @since 6.20.0
 */
public class MenuItemForm<T> extends Form<T>
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 *
	 * @param id the markup-id
	 */
	public MenuItemForm(String id)
	{
		super(id);
	}

	/**
	 * Constructor
	 *
	 * @param id id the markup-id
	 * @param model the {@link IModel}
	 */
	public MenuItemForm(String id, IModel<T> model)
	{
		super(id, model);
	}

	// Properties //

	/**
	 * Gets the javascript statement bound to the "onclick" event.
	 * 
	 * @return the javascript statement
	 */
	protected String getOnClickStatement()
	{
		return "var $event = jQuery.event.fix(event); if ($event.target.nodeName !== 'BUTTON') { $event.stopPropagation(); }";
	}

	// Events //

	@Override
	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);

		tag.put("onclick", this.getOnClickStatement());

		// KendoUI Menu component uses <li><span class="k-link"> as a parent of the menu items
		// If there is a outer form Wicket will change the tag name to <div>
		// but the browser will render this <div> as a sibling to <span class="k-link"> instead of a child
		// this will break the JavaScript event propagation because the KendoUI click listener is on '.k-link'
		if ("div".equals(tag.getName())) {
			tag.setName("span");
		}
	}
}
