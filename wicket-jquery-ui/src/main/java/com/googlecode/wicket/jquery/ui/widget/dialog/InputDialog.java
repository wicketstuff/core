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

import java.io.Serializable;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.lang.Args;

import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;

/**
 * Provides a modal dialog box that ask an input to the user
 *
 * @author Sebastien Briquet - sebfz1
 *
 * @param <T> the type of the model object of the {@link TextField}
 */
public abstract class InputDialog<T extends Serializable> extends AbstractFormDialog<T>
{
	private static final long serialVersionUID = 1L;

	/** feedback panel */
	private final FeedbackPanel feedback;

	private final Form<?> form;
	private IModel<String> labelModel;

	/**
	 * Constructor supplying a new default model.
	 *
	 * @param id the markupId, an html div suffice to host a dialog.
	 * @param title the title of the dialog
	 * @param label text that will be displayed in front of the text field.
	 */
	public InputDialog(String id, String title, String label)
	{
		this(id, title, label, new Model<T>());
	}

	/**
	 * Constructor supplying a new default model.
	 *
	 * @param id the markupId, an html div suffice to host a dialog.
	 * @param title the title of the dialog
	 * @param label text that will be displayed in front of the text field.
	 */
	public InputDialog(String id, IModel<String> title, IModel<String> label)
	{
		this(id, title, label, new Model<T>());
	}

	/**
	 *
	 * @param id the markupId, an html div suffice to host a dialog.
	 * @param title the title of the dialog
	 * @param label text that will be displayed in front of the text field.
	 * @param model the model to be used
	 */
	public InputDialog(String id, String title, String label, IModel<T> model)
	{
		this(id, Model.of(title), Model.of(label), model);
	}

	/**
	 *
	 * @param id the markupId, an html div suffice to host a dialog.
	 * @param title the title of the dialog
	 * @param label text that will be displayed in front of the text field.
	 * @param model the model to be used
	 */
	public InputDialog(String id, IModel<String> title, IModel<String> label, IModel<T> model)
	{
		super(id, title, model, true);

		this.labelModel = label;

		// form //
		this.form = InputDialog.newForm("form");
		this.add(this.form);

		// feedback //
		this.feedback = new JQueryFeedbackPanel("feedback");
		this.form.add(this.feedback);
	}

	// Events //

	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		// label //
		this.form.add(this.newLabel("label", this.labelModel));

		// field //
		this.form.add(this.newTextField("input", this.getModel()).setRequired(this.isRequired()));
	}

	@Override
	protected void onOpen(AjaxRequestTarget target)
	{
		super.onOpen(target);

		target.add(this.form);
	}

	@Override
	public final void onError(AjaxRequestTarget target)
	{
		target.add(this.feedback);
	}

	@Override
	protected void onDetach()
	{
		super.onDetach();

		this.labelModel.detach();
	}

	// Properties //

	/**
	 * Sets the text that will be displayed in front of the text field.
	 *
	 * @return the dialog's label
	 * @deprecated useless, will be removed
	 */
	@Deprecated
	public IModel<String> getLabel()
	{
		return this.labelModel;
	}

	/**
	 * Sets the text that will be displayed in front of the text field.
	 *
	 * @param label the dialog's label
	 * @deprecated useless, will be removed
	 */
	@Deprecated
	public void setLabel(IModel<String> label)
	{
		Args.notNull(label, "label");

		this.labelModel = label;
	}

	@Override
	public Form<?> getForm()
	{
		return this.form;
	}

	/**
	 * Indicates whether the underlying input is required
	 *
	 * @return true by default
	 */
	public boolean isRequired()
	{
		return true;
	}

	@Override
	public boolean isResizable()
	{
		return true;
	}

	@Override
	protected List<DialogButton> getButtons()
	{
		return DialogButtons.OK_CANCEL.toList();
	}

	@Override
	protected DialogButton getSubmitButton()
	{
		return this.findButton(OK);
	}

	// Factories //

	/**
	 * Gets a new {@link Form}
	 *
	 * @param id the markup-id
	 * @return the new form
	 */
	private static Form<?> newForm(String id)
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
	 * Gets a new {@link Component} that will be used as a label in the dialog.<br/>
	 * Override this method when you need to show formatted label.
	 *
	 * @param id the markup-id
	 * @param model the label {@link IModel}
	 * @return the new label component.
	 */
	protected Component newLabel(String id, IModel<String> model)
	{
		return new Label(id, model);
	}

	/**
	 * Gets a new {@link FormComponent} that will be used as an input.<br/>
	 * Override this method when you need to use a {@code IValidator} or different input type, e.g. {@code NumberTextField} or {@code PasswordField}.
	 * 
	 * @param id the markup-id
	 * @param model the {@link IModel}
	 * @return the new {@link FormComponent}
	 */
	protected FormComponent<T> newTextField(String id, IModel<T> model)
	{
		return new TextField<T>(id, model);
	}
}
