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

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.core.JQueryAbstractBehavior;
import com.googlecode.wicket.kendo.ui.form.TextField;
import com.googlecode.wicket.kendo.ui.panel.KendoFeedbackPanel;

/**
 * Provides a Kendo UI Window having a {@link TextField}, an 'Ok' and a 'Cancel' button
 *
 * @param <T> the type of the model object
 * @author Sebastien Briquet - sebfz1
 * @since 6.17.0
 */
public abstract class InputWindow<T> extends Window<T>
{
	private static final long serialVersionUID = 1L;

	private final Form<?> form;
	private KendoFeedbackPanel feedback;
	private IModel<String> labelModel;

	private FormComponent<T> textField;

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

		this.labelModel = label;

		// form //
		this.form = InputWindow.newForm("form");
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

		// label //
		this.form.add(this.newLabel("label", this.labelModel));

		// field //
		this.textField = this.newTextField("input", this.getModel());
		this.textField.setOutputMarkupId(true);
		this.textField.setRequired(this.isRequired());
		this.form.add(this.textField);

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

	// Methods //

	@Override
	public void detachModels()
	{
		super.detachModels();

		if (this.labelModel != null)
		{
			this.labelModel.detach();
		}
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
	 * Gets the label model object
	 * 
	 * @return label the label
	 */
	public String getLabel()
	{
		return this.labelModel.getObject();
	}

	/**
	 * Sets the label model object
	 * 
	 * @param label the label
	 */
	public void setLabel(String label)
	{
		this.labelModel.setObject(label);
	}

	/**
	 * Indicates whether the {@link TextField}'s value is required
	 *
	 * @return true by default
	 */
	protected boolean isRequired()
	{
		return true;
	}

	public String getTextFieldMarkupId()
	{
		return this.textField.getMarkupId();
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

	/**
	 * Gets a new {@link Component} that will be used as a label in the window.<br>
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

	/**
	 * Gets a new {@link FormComponent} that will be used as an input.<br>
	 * Override this method when you need to use a {@code IValidator} or different input type, e.g. {@code NumberTextField} or {@code PasswordField}.
	 * 
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @return the new {@link FormComponent}
	 */
	protected FormComponent<T> newTextField(String id, IModel<T> model)
	{
		return new TextField<T>(id, model);
	}

	// Classes //

	/**
	 * Provides an auto-focus Behavior on {@link InputWindow}'s {@code TextField}
	 */
	public static class AutoFocusBehavior extends JQueryAbstractBehavior
	{
		private static final long serialVersionUID = 1L;

		private InputWindow<?> window = null;

		public AutoFocusBehavior()
		{
		}

		@Override
		public void bind(Component component)
		{
			super.bind(component);

			if (component instanceof InputWindow<?>)
			{
				this.window = (InputWindow<?>) component;
			}
		}

		@Override
		protected String $()
		{
			if (this.window != null)
			{
				return String.format("%s.bind('activate', function() { jQuery('#%s').focus(); });", this.window.widget(), this.window.getTextFieldMarkupId());
			}

			return null;
		}
	}
}
