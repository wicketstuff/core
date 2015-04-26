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
package com.googlecode.wicket.kendo.ui.widget.window;

import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.kendo.ui.KendoIcon;

/**
 * Provides a modal window that displays a specific message, with a predefined icon and a predefined button set.
 *
 * @author Sebastien Briquet - sebfz1
 */
public abstract class MessageWindow extends Window<String>
{
	private static final long serialVersionUID = 1L;

	private Label label;
	private final Form<?> form;

	/**
	 * Constructor.
	 *
	 * @param id the markup id, an html div suffice to host a dialog.
	 * @param title the title of the dialog
	 * @param message the message to be displayed
	 * @param buttons list of buttons to be displayed
	 */
	public MessageWindow(String id, String title, String message, WindowButtons buttons)
	{
		this(id, Model.of(title), Model.of(message), buttons.toList(), KendoIcon.NONE);
	}

	/**
	 * Constructor.
	 *
	 * @param id the markup id, an html div suffice to host a dialog.
	 * @param title the title of the dialog
	 * @param message the message to be displayed
	 * @param buttons list of buttons to be displayed
	 */
	public MessageWindow(String id, String title, String message, List<WindowButton> buttons)
	{
		this(id, Model.of(title), Model.of(message), buttons, KendoIcon.NONE);
	}

	/**
	 * Constructor.
	 *
	 * @param id the markup id, an html div suffice to host a dialog.
	 * @param title the title of the dialog
	 * @param message the message to be displayed
	 * @param buttons list of buttons to be displayed
	 * @param icon the predefined icon to display
	 */
	public MessageWindow(String id, String title, String message, WindowButtons buttons, String icon)
	{
		this(id, Model.of(title), Model.of(message), buttons.toList(), icon);
	}

	/**
	 * Constructor.
	 *
	 * @param id the markup id, an html div suffice to host a dialog.
	 * @param title the title of the dialog
	 * @param message the message to be displayed
	 * @param buttons list of buttons to be displayed
	 * @param icon the predefined icon to display
	 */
	public MessageWindow(String id, String title, String message, List<WindowButton> buttons, String icon)
	{
		this(id, Model.of(title), Model.of(message), buttons, icon);
	}

	/**
	 * Constructor.
	 *
	 * @param id the markup id, an html div suffice to host a dialog.
	 * @param title the title of the dialog
	 * @param message the message to be displayed
	 * @param buttons list of buttons to be displayed
	 */
	public MessageWindow(String id, IModel<String> title, IModel<String> message, WindowButtons buttons)
	{
		this(id, title, message, buttons.toList(), KendoIcon.NONE);
	}

	/**
	 * Constructor.
	 *
	 * @param id the markup id, an html div suffice to host a dialog.
	 * @param title the title of the dialog
	 * @param message the message to be displayed
	 * @param buttons list of buttons to be displayed
	 */
	public MessageWindow(String id, IModel<String> title, IModel<String> message, List<WindowButton> buttons)
	{
		this(id, title, message, buttons, KendoIcon.NONE);
	}

	/**
	 * Constructor.
	 *
	 * @param id the markup id, an html div suffice to host a dialog.
	 * @param title the title of the dialog
	 * @param message the message to be displayed
	 * @param buttons list of buttons to be displayed
	 * @param icon the predefined icon to display
	 */
	public MessageWindow(String id, IModel<String> title, IModel<String> message, WindowButtons buttons, String icon)
	{
		this(id, title, message, buttons.toList(), icon);
	}

	/**
	 * Main Constructor.
	 *
	 * @param id the markup id, an html div suffice to host a dialog.
	 * @param title the title of the dialog
	 * @param message the message to be displayed
	 * @param buttons list of buttons to be displayed
	 * @param icon the predefined icon to display
	 */
	public MessageWindow(String id, IModel<String> title, IModel<String> message, List<WindowButton> buttons, String icon)
	{
		super(id, title, message, buttons);

		// icon //
		this.add(new EmptyPanel("icon").add(AttributeModifier.append("class", KendoIcon.getCssClass(icon))));

		// label //
		this.label = new Label("text", this.getModel());
		this.add(this.label.setOutputMarkupId(true));

		// form //
		this.form = MessageWindow.newForm("form");
		this.add(this.form);
	}

	// Properties //

	@Override
	protected Form<?> getForm()
	{
		return this.form;
	}

	// Events //

	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		// buttons //
		this.form.add(this.newButtonPanel("buttons", this.getButtons()));
	}

	@Override
	protected void onOpen(AjaxRequestTarget target)
	{
		target.add(this.label);
	}

	// Factories //

	/**
	 * Gets a new {@link Form}
	 *
	 * @param id the markup-id
	 * @return the new form
	 */
	private static Form<Void> newForm(String id)
	{
		return new Form<Void>(id) {

			private static final long serialVersionUID = 1L;

			@Override
			public boolean wantSubmitOnParentFormSubmit()
			{
				return false;
			}
		};
	}
}