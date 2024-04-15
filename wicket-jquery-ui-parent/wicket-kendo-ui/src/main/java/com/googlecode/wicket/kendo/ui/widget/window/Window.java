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

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.util.lang.Args;

/**
 * Default implementation for {@link AbstractWindow} that provides a set of {@link WindowButton}{@code s} and events such as {@link #onSubmit(AjaxRequestTarget, WindowButton)}, {@link #onError(AjaxRequestTarget, WindowButton)} and
 * {@link #onAfterSubmit(AjaxRequestTarget, WindowButton)}<br>
 * The window is automatically closed {@link #onAfterSubmit(AjaxRequestTarget, WindowButton)}
 *
 * @param <T> the model object type
 * @author Sebastien Briquet - sebfz1
 */
public abstract class Window<T> extends AbstractWindow<T>
{
	private static final long serialVersionUID = 1L;

	/* Default Button names */
	public static final String OK = "OK";
	public static final String NO = "NO";
	public static final String YES = "YES";
	public static final String CLOSE = "CLOSE";
	public static final String CANCEL = "CANCEL";
	public static final String SUBMIT = "SUBMIT";

	/* Default Button labels */
	public static final IModel<String> LBL_OK = new ResourceModel("button.ok");
	public static final IModel<String> LBL_NO = new ResourceModel("button.no");
	public static final IModel<String> LBL_YES = new ResourceModel("button.yes");
	public static final IModel<String> LBL_CLOSE = new ResourceModel("button.close");
	public static final IModel<String> LBL_CANCEL = new ResourceModel("button.cancel");
	public static final IModel<String> LBL_SUBMIT = new ResourceModel("button.submit");

	private final List<WindowButton> buttons;

	/**
	 * Constructor
	 *
	 * @param id the markup id, an html div suffice to host a window.
	 * @param title the title of the window
	 * @param buttons the set of predefined buttons
	 */
	public Window(String id, String title, WindowButtons buttons)
	{
		this(id, title, buttons.toList());
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id, an html div suffice to host a window.
	 * @param title the title of the window
	 * @param buttons the list of {@link WindowButton}
	 */
	public Window(String id, String title, List<WindowButton> buttons)
	{
		super(id, title);

		this.buttons = Args.notNull(buttons, "buttons");
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id, an html div suffice to host a window.
	 * @param title the title of the window
	 * @param buttons the set of predefined buttons
	 */
	public Window(String id, IModel<String> title, WindowButtons buttons)
	{
		this(id, title, buttons.toList());
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id, an html div suffice to host a window.
	 * @param title the title of the window
	 * @param buttons the list of {@link WindowButton}
	 */
	public Window(String id, IModel<String> title, List<WindowButton> buttons)
	{
		super(id, title);

		this.buttons = Args.notNull(buttons, "buttons");
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id, an html div suffice to host a window.
	 * @param title the title of the window
	 * @param model the model to be used in the window.
	 * @param buttons the set of predefined buttons
	 */
	public Window(String id, String title, IModel<T> model, WindowButtons buttons)
	{
		this(id, title, model, buttons.toList());
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id, an html div suffice to host a window.
	 * @param title the title of the window
	 * @param model the model to be used in the window.
	 * @param buttons the list of {@link WindowButton}
	 */
	public Window(String id, String title, IModel<T> model, List<WindowButton> buttons)
	{
		super(id, title, model);

		this.buttons = Args.notNull(buttons, "buttons");
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id, an html div suffice to host a window.
	 * @param title the title of the window
	 * @param model the model to be used in the window.
	 * @param buttons the set of predefined buttons
	 */
	public Window(String id, IModel<String> title, IModel<T> model, WindowButtons buttons)
	{
		this(id, title, model, buttons.toList());
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id, an html div suffice to host a window.
	 * @param title the title of the window
	 * @param model the model to be used in the window.
	 * @param buttons the list of {@link WindowButton}
	 */
	public Window(String id, IModel<String> title, IModel<T> model, List<WindowButton> buttons)
	{
		super(id, title, model);

		this.buttons = Args.notNull(buttons, "buttons");
	}

	// Properties //

	/**
	 * Gets the {@link Form} that should be submitted and validated
	 *
	 * @return the {@link Form}
	 */
	protected abstract Form<?> getForm();

	/**
	 * Gets the list of {@link WindowButton}{@code s} This method an be overridden to provide a behavioral helper for instance
	 *
	 * @return the list of {@link WindowButton}{@code s}
	 * @see WindowButtonUtils#setDefaultFormProcessing(WindowButtons, boolean)
	 */
	protected List<WindowButton> getButtons()
	{
		return this.buttons;
	}

	// Events //

	/**
	 * Triggered when the form is submitted, but the validation failed
	 *
	 * @param target the {@link AjaxRequestTarget}
	 * @param button the {@link WindowButton}
	 */
	protected void onError(AjaxRequestTarget target, WindowButton button)
	{
		// noop
	}

	/**
	 * Triggered when the form is submitted, and the validation succeed
	 *
	 * @param target the {@link AjaxRequestTarget}
	 * @param button the {@link WindowButton}
	 */
	protected void onSubmit(AjaxRequestTarget target, WindowButton button)
	{
		// noop
	}

	/**
	 * Triggered after the form is submitted, and the validation succeed<br>
	 * Closes the dialog by default
	 *
	 * @param target the {@link AjaxRequestTarget}
	 * @param button the {@link WindowButton}
	 */
	protected void onAfterSubmit(AjaxRequestTarget target, WindowButton button)
	{
		this.close(target);
	}

	// Factories //

	/**
	 * Gets a new {@link WindowButtonPanel}
	 *
	 * @param id the markup id
	 * @param buttons the list of {@link WindowButton}
	 * @return the new {@link WindowButtonPanel}
	 */
	protected WindowButtonPanel newButtonPanel(String id, List<WindowButton> buttons)
	{
		return new WindowButtonPanel(id, buttons) {

			private static final long serialVersionUID = 1L;

			@Override
			protected Form<?> getForm()
			{
				return Window.this.getForm();
			}

			@Override
			protected void onError(AjaxRequestTarget target, WindowButton button)
			{
				Window.this.onError(target, button);
			}

			@Override
			protected void onSubmit(AjaxRequestTarget target, WindowButton button)
			{
				Window.this.onSubmit(target, button);
			}

			@Override
			protected void onAfterSubmit(AjaxRequestTarget target, WindowButton button)
			{
				Window.this.onAfterSubmit(target, button);
			}
		};
	}
}
