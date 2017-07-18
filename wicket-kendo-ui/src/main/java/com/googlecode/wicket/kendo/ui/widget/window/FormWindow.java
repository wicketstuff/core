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

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.kendo.ui.panel.KendoFeedbackPanel;

/**
 * Provides a Kendo UI base Window containing a {@link Form}, a {@link KendoFeedbackPanel}, a {@value Window#OK} and a {@value Window#CANCEL} button<br>
 * <br>
 * Implementation may look like:
 * 
 * <pre>
 *  <code>
 *  final Form&lt;?&gt; form = this.getForm();
 *  form.add(new TextField("myId", this.getModel()));
 *  </code>
 * </pre>
 *
 * @param <T> the type of the model object
 * @author Sebastien Briquet - sebfz1
 * @since 8.0.0
 */
public abstract class FormWindow<T> extends Window<T> // NOSONAR
{
	private static final long serialVersionUID = 1L;

	private final Form<?> form;
	private KendoFeedbackPanel feedback;

	/**
	 * Constructor
	 *
	 * @param id the markup id, an html div suffice to host a window.
	 * @param title the title of the window
	 */
	public FormWindow(String id, String title)
	{
		this(id, Model.of(title), null);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id, an html div suffice to host a window.
	 * @param title the title of the window
	 * @param model the model to be used in the window
	 */
	public FormWindow(String id, String title, IModel<T> model)
	{
		this(id, Model.of(title), model);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id, an html div suffice to host a window.
	 * @param title the title of the window
	 */
	public FormWindow(String id, IModel<String> title)
	{
		this(id, title, null);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id, an html div suffice to host a window.
	 * @param title the title of the window
	 * @param model the model to be used in the window
	 */
	public FormWindow(String id, IModel<String> title, IModel<T> model)
	{
		super(id, title, model, WindowButtons.OK_CANCEL);

		// form //
		this.form = FormWindow.newForm("form");
		this.add(this.form);
	}

	// Events //

	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		// feedback //
		this.feedback = this.newFeedbackPanel("feedback");
		this.form.add(this.feedback);

		// buttons //
		this.form.add(this.newButtonPanel("buttons", this.getButtons()));
	}

	@Override
	protected void onOpen(IPartialPageRequestHandler handler)
	{
		super.onOpen(handler);

		handler.add(this.form);
	}

	@Override
	protected void onError(AjaxRequestTarget target, WindowButton button)
	{
		super.onError(target, button);

		target.add(this.feedback);
	}

	@Override
	protected void onSubmit(AjaxRequestTarget target, WindowButton button)
	{
		if (button != null)
		{
			if (button.match(OK))
			{
				this.onSubmit(target);
			}
			else if (button.match(CANCEL))
			{
				this.onCancel(target);
			}
		}
	}

	/**
	 * Triggered when the 'submit' button is clicked, and the validation succeed
	 *
	 * @param target the {@link AjaxRequestTarget}
	 */
	protected abstract void onSubmit(AjaxRequestTarget target);

	/**
	 * Triggered when the 'cancel' button is clicked
	 *
	 * @param target the {@link AjaxRequestTarget}
	 */
	protected void onCancel(AjaxRequestTarget target)
	{
		// noop
	}

	// Properties //

	/**
	 * Gets the inner {@link Form}
	 *
	 * @return the form
	 */
	@Override
	public Form<?> getForm()
	{
		return this.form;
	}

	// Factories //

	/**
	 * Gets a new {@link Form}
	 *
	 * @param id the markup id
	 * @return the new form
	 */
	private static Form<Void> newForm(String id)
	{
		return new Form<Void>(id) {

			private static final long serialVersionUID = 1L;

			@Override
			protected boolean wantSubmitOnParentFormSubmit()
			{
				return false;
			}
		};
	}

	/**
	 * Gets a new {@link KendoFeedbackPanel}
	 * 
	 * @param id the markup id
	 * @return a new {@code KendoFeedbackPanel}
	 */
	protected KendoFeedbackPanel newFeedbackPanel(String id)
	{
		return new KendoFeedbackPanel(id, this);
	}
}
