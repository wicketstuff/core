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
package com.googlecode.wicket.jquery.ui.widget.dialog;

import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.lang.Args;

/**
 * Provides a modal dialog box that displays a specific message, with a predefined icon and a predefined button set.<br>
 * <b>Note:</b> {@link MessageDialog} &#38; {@link MessageFormDialog} are sharing the same code, they just do not extend the same class.
 *
 * @author Sebastien Briquet - sebfz1
 */
public abstract class MessageDialog extends AbstractDialog<String>
{
	private static final long serialVersionUID = 1L;

	private Component label;
	private List<DialogButton> buttons;

	private final WebMarkupContainer container;

	/**
	 * Constructor.
	 *
	 * @param id the markupId, an html div suffice to host a dialog.
	 * @param title the title of the dialog
	 * @param message the message to be displayed
	 * @param buttons button set to be displayed
	 */
	public MessageDialog(String id, String title, String message, DialogButtons buttons)
	{
		this(id, title, message, buttons.toList(), DialogIcon.NONE);
	}

	/**
	 * Constructor.
	 *
	 * @param id the markupId, an html div suffice to host a dialog.
	 * @param title the title of the dialog
	 * @param message the message to be displayed
	 * @param buttons list of buttons to be displayed
	 */
	public MessageDialog(String id, String title, String message, List<DialogButton> buttons)
	{
		this(id, title, message, buttons, DialogIcon.NONE);
	}

	/**
	 * Constructor.
	 *
	 * @param id the markupId, an html div suffice to host a dialog.
	 * @param title the title of the dialog
	 * @param message the message to be displayed
	 * @param buttons button set to be displayed
	 * @param icon the predefined icon to display
	 */
	public MessageDialog(String id, String title, String message, DialogButtons buttons, DialogIcon icon)
	{
		this(id, Model.of(title), Model.of(message), buttons.toList(), icon);
	}

	/**
	 * Constructor.
	 *
	 * @param id the markupId, an html div suffice to host a dialog.
	 * @param title the title of the dialog
	 * @param message the message to be displayed
	 * @param buttons list of buttons to be displayed
	 * @param icon the predefined icon to display
	 */
	public MessageDialog(String id, String title, String message, List<DialogButton> buttons, DialogIcon icon)
	{
		this(id, Model.of(title), Model.of(message), buttons, icon);
	}

	/**
	 * Constructor.
	 *
	 * @param id the markupId, an html div suffice to host a dialog.
	 * @param title the title of the dialog
	 * @param message the message to be displayed
	 * @param buttons button set to be displayed
	 */
	public MessageDialog(String id, IModel<String> title, IModel<String> message, DialogButtons buttons)
	{
		this(id, title, message, buttons.toList(), DialogIcon.NONE);
	}

	/**
	 * Constructor.
	 *
	 * @param id the markupId, an html div suffice to host a dialog.
	 * @param title the title of the dialog
	 * @param message the message to be displayed
	 * @param buttons list of buttons to be displayed
	 */
	public MessageDialog(String id, IModel<String> title, IModel<String> message, List<DialogButton> buttons)
	{
		this(id, title, message, buttons, DialogIcon.NONE);
	}

	/**
	 * Constructor.
	 *
	 * @param id the markupId, an html div suffice to host a dialog.
	 * @param title the title of the dialog
	 * @param message the message to be displayed
	 * @param buttons button set to be displayed
	 * @param icon the predefined icon to display
	 */
	public MessageDialog(String id, IModel<String> title, IModel<String> message, DialogButtons buttons, DialogIcon icon)
	{
		this(id, title, message, buttons.toList(), icon);
	}

	/**
	 * Constructor.
	 *
	 * @param id the markupId, an html div suffice to host a dialog.
	 * @param title the title of the dialog
	 * @param message the message to be displayed
	 * @param buttons list of buttons to be displayed
	 * @param icon the predefined icon to display
	 */
	public MessageDialog(String id, IModel<String> title, IModel<String> message, List<DialogButton> buttons, DialogIcon icon)
	{
		super(id, title, message, true);
		
		// buttons //
		this.buttons = Args.notNull(buttons, "buttons");

		// container //
		this.container = new WebMarkupContainer("container");
		this.add(this.container);

		this.container.add(AttributeModifier.append("class", icon.getStyle()));
		this.container.add(new EmptyPanel("icon").add(AttributeModifier.replace("class", icon)));
	}

	// Properties //

	@Override
	protected final List<DialogButton> getButtons()
	{
		return this.buttons;
	}

	// Events //
	
	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		// label //
		this.label = this.newLabel("message", this.getModel());
		this.container.add(this.label.setOutputMarkupId(true));
	}

	@Override
	protected void onOpen(IPartialPageRequestHandler handler)
	{
		handler.add(this.label);
	}

	// Factories //

	/**
	 * Gets a new {@link Component} that will be used as a label in the dialog.<br>
	 * Override this method when you need to show formatted label.
	 *
	 * @param id the markup id
	 * @param model the label {@link IModel}
	 * @return the new label component.
	 */
	protected Component newLabel(String id, IModel<String> model)
	{
		return new Label(id, model);
	}
}
