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
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.core.JQueryAbstractBehavior;
import com.googlecode.wicket.jquery.core.resource.StyleSheetPackageHeaderItem;
import com.googlecode.wicket.kendo.ui.form.TextField;

/**
 * Provides a Kendo UI Window having a {@link TextField}, a {@value Window#OK} and a {@value Window#CANCEL} button
 *
 * @param <T> the type of the model object
 * @author Sebastien Briquet - sebfz1
 * @since 6.17.0
 */
public abstract class InputWindow<T> extends FormWindow<T> // NOSONAR
{
	private static final long serialVersionUID = 1L;

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
		super(id, title, model);

		this.labelModel = label;
	}

	// Methods //

	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);

		response.render(new StyleSheetPackageHeaderItem(InputWindow.class));
	}

	@Override
	public void detachModels()
	{
		super.detachModels();

		if (this.labelModel != null)
		{
			this.labelModel.detach();
		}
	}

	// Events //

	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		final Form<?> form = this.getForm();

		// label //
		form.add(this.newLabel("label", this.labelModel));

		// field //
		this.textField = this.newTextField("input", this.getModel());
		this.textField.setOutputMarkupId(true);
		this.textField.setRequired(this.isRequired());
		form.add(this.textField);
	}

	// Properties //

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
			// noop
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
