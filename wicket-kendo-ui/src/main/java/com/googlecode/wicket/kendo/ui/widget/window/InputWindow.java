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

import com.googlecode.wicket.kendo.ui.KendoIcon;
import com.googlecode.wicket.kendo.ui.form.TextField;
import com.googlecode.wicket.kendo.ui.form.button.AjaxButton;
import com.googlecode.wicket.kendo.ui.panel.KendoFeedbackPanel;

/**
 * Provides a Kendo UI window having a {@link TextField}, a 'Submit' button and a 'Cancel' button
 *
 * @param <T> the type of the model object
 *
 * @author Sebastien Briquet - sebfz1
 * @since 6.17.0
 */
public abstract class InputWindow<T> extends AbstractWindow<T>
{
	private static final long serialVersionUID = 1L;

	/** feedback panel */
	private KendoFeedbackPanel feedback;

	/** label model */
	private final IModel<String> label;

	private final Form<Void> form;

	/**
	 * Constructor
	 *
	 * @param id the markupId, an html div suffice to host a window.
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
	 * @param id the markupId, an html div suffice to host a window.
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
	 * @param id markupId, an html div suffice to host a window.
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
	 * @param id markupId, an html div suffice to host a window.
	 * @param title the title of the window
	 * @param model the model to be used in the window
	 * @param label the label of the textfield
	 */
	public InputWindow(String id, IModel<String> title, IModel<T> model, IModel<String> label)
	{
		super(id, title, model, true);

		this.label = label;
		this.form = new Form<Void>("form");
	}

	// Properties //

	/**
	 * Indicated whether the {@link TextField}'s value value is required
	 *
	 * @return true or false
	 */
	protected boolean isRequired()
	{
		return false;
	}

	// Events //
	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		// Form //
		this.add(this.form);

		// FeedbackPanel //
		this.feedback = new KendoFeedbackPanel("feedback");
		this.form.add(this.feedback);

		// Label //
		this.form.add(new Label("label", this.label));

		// TextField //
		this.form.add(new TextField<T>("input", this.getModel()).setRequired(this.isRequired()));

		// Button //
		this.form.add(this.newSubmitButton("submit"));
		this.form.add(this.newCloseButton("close"));
	}

	@Override
	protected void onOpen(AjaxRequestTarget target)
	{
		super.onOpen(target);

		target.add(this.form);
	}

	/**
	 * Triggered when the form is submitted, and the validation succeed
	 *
	 * @param target the {@link AjaxRequestTarget}
	 */
	protected abstract void onSubmit(AjaxRequestTarget target);

	/**
	 * Triggered after the form is submitted, and the validation succeed<br/>
	 * Closes the dialog by default (if not overridden)
	 *
	 * @param target the {@link AjaxRequestTarget}
	 */
	protected void onAfterSubmit(AjaxRequestTarget target)
	{
		this.close(target);
	}

	// Factories //

	/**
	 * Gets a new 'Submit' button
	 *
	 * @param id the markup id
	 * @return the {@link AjaxButton}
	 */
	private AjaxButton newSubmitButton(String id)
	{
		return new AjaxButton(id) {

			private static final long serialVersionUID = 1L;

			@Override
			protected String getIcon()
			{
				return KendoIcon.TICK;
			}

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> unused)
			{
				InputWindow.this.onSubmit(target);
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> unused)
			{
				target.add(InputWindow.this.feedback);
			}

			@Override
			protected void onAfterSubmit(AjaxRequestTarget target, Form<?> unused)
			{
				InputWindow.this.onAfterSubmit(target);
			}
		};
	}

	/**
	 * Gets a new 'Close' button
	 *
	 * @param id the markup id
	 * @return the {@link AjaxButton}
	 */
	private AjaxButton newCloseButton(String id)
	{
		return new AjaxButton(id) {

			private static final long serialVersionUID = 1L;

			@Override
			protected String getIcon()
			{
				return KendoIcon.CANCEL;
			}

			@Override
			protected void onInitialize()
			{
				super.onInitialize();

				this.setDefaultFormProcessing(false);
			}

			@Override
			protected void onAfterSubmit(AjaxRequestTarget target, Form<?> unused)
			{
				InputWindow.this.onAfterSubmit(target);
			}
		};
	}
}
