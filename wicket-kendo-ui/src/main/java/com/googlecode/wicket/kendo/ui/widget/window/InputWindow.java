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
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.kendo.ui.form.TextField;
import com.googlecode.wicket.kendo.ui.panel.KendoFeedbackPanel;

/**
 * Provides a Kendo UI Window having a {@link TextField}, a 'Submit' button and a 'Cancel' button
 *
 * @param <T> the type of the model object
 *
 * @author Sebastien Briquet - sebfz1
 * @since 6.17.0
 */
public abstract class InputWindow<T> extends Window<T>
{
	private static final long serialVersionUID = 1L;

	/** feedback panel */
	private final KendoFeedbackPanel feedback;

	private final Form<?> form;

	/**
	 * Constructor
	 *
	 * @param id the markup id, an html div suffice to host a window.
	 * @param title the title of the window
	 * @param label the label of the textfield
	 */
	public InputWindow(String id, String title, String label)
	{
		this(id, Model.of(title), null, Model.of(label));
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id, an html div suffice to host a window.
	 * @param title the title of the window
	 * @param label the label of the textfield
	 */
	public InputWindow(String id, IModel<String> title, IModel<String> label)
	{
		this(id, title, null, label);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id, an html div suffice to host a window.
	 * @param title the title of the window
	 * @param model the model to be used in the window
	 * @param label the label of the textfield
	 */
	public InputWindow(String id, String title, IModel<T> model, String label)
	{
		this(id, Model.of(title), model, Model.of(label));
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id, an html div suffice to host a window.
	 * @param title the title of the window
	 * @param model the model to be used in the window
	 * @param label the label of the textfield
	 */
	public InputWindow(String id, IModel<String> title, IModel<T> model, IModel<String> label)
	{
		super(id, title, model, WindowButtons.OK_CANCEL);

		// form //
		this.form = InputWindow.newForm("form");
		this.add(this.form);

		// feedback //
		this.feedback = new KendoFeedbackPanel("feedback");
		this.form.add(this.feedback);

		// label //
		this.form.add(new Label("label", label));
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

	/**
	 * Indicates whether the {@link TextField}'s value is required
	 *
	 * @return true by default
	 */
	protected boolean isRequired()
	{
		// XXX: changed InputButton#isRequired to true by default because it makes more sense - SILENT API BREAK
		return true;
	}

	// Events //

	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		// field //
		this.form.add(new TextField<T>("input", this.getModel()).setRequired(this.isRequired()));

		// buttons //
		this.form.add(this.newButtonPanel("buttons", this.getButtons()));
	}

	@Override
	protected void onOpen(AjaxRequestTarget target)
	{
		super.onOpen(target);

		target.add(this.form);
	}

	@Override
	protected void onError(AjaxRequestTarget target, WindowButton button)
	{
		target.add(this.feedback);
	}

	@Override
	protected void onSubmit(AjaxRequestTarget target, WindowButton button)
	{
		if (button != null)
		{
			if (button.match(LBL_OK))
			{
				this.onSubmit(target);
			}
			else if (button.match(LBL_CANCEL))
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
