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

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IFormSubmitter;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.IModelComparator;

import com.googlecode.wicket.jquery.core.ajax.IJQueryAjaxAware;

/**
 * Provides the base class for form-based dialogs
 *
 * @author Sebastien Briquet - sebfz1
 * @param <T> the model object type
 */
public abstract class AbstractFormDialog<T extends Serializable> extends AbstractDialog<T>
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param title the dialog's title
	 */
	public AbstractFormDialog(String id, String title)
	{
		super(id, title);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param title the dialog's title
	 */
	public AbstractFormDialog(String id, IModel<String> title)
	{
		super(id, title);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param title the dialog's title
	 * @param model the dialog's model
	 */
	public AbstractFormDialog(String id, String title, IModel<T> model)
	{
		super(id, title, model, true);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param title the dialog's title
	 * @param model the dialog's model
	 */
	public AbstractFormDialog(String id, IModel<String> title, IModel<T> model)
	{
		super(id, title, model, true);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param title the dialog's title
	 * @param modal indicates whether the dialog is modal
	 */
	public AbstractFormDialog(String id, String title, boolean modal)
	{
		super(id, title, modal);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param title the dialog's title
	 * @param modal indicates whether the dialog is modal
	 */
	public AbstractFormDialog(String id, IModel<String> title, boolean modal)
	{
		super(id, title, modal);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param title the dialog's title
	 * @param model the dialog's model
	 * @param modal indicates whether the dialog is modal
	 */
	public AbstractFormDialog(String id, String title, IModel<T> model, boolean modal)
	{
		super(id, title, model, modal);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param title the dialog's title
	 * @param model the dialog's model
	 * @param modal indicates whether the dialog is modal
	 */
	public AbstractFormDialog(String id, IModel<String> title, IModel<T> model, boolean modal)
	{
		super(id, title, model, modal);
	}

	// Properties //

	/**
	 * Gets the button that is in charge to submit the form.<br/>
	 * It should be in the list of buttons returned by {@link #getButtons()}
	 *
	 * @return the submit button
	 */
	protected abstract DialogButton getSubmitButton();

	/**
	 * Returns whether form should be processed the default way. When false (default is true), all validation and form updating is bypassed and the onSubmit method of that button is called directly, and the onSubmit method of the parent
	 * form is not called. A common use for this is to create a cancel button.
	 *
	 * @return defaultFormProcessing
	 */
	public boolean getDefaultFormProcessing()
	{
		return true; // default
	}

	/**
	 * Gets the form to be validated by this dialog.<br/>
	 * Warning, the onSubmit and the onError are being delegated to this dialog. However, it does not prevent the use of Form#onSubmit nor Form#onError
	 *
	 * @return the form
	 */
	public abstract Form<?> getForm();

	/**
	 * Gets the form associated to the button.<br/>
	 * It means that it will return the form if the button is the submit button and null otherwise. The callback script will differ depending on this.
	 *
	 * @param button the dialog's button
	 * @return the {@link Form} or {@code null}
	 */
	protected Form<?> getForm(DialogButton button)
	{
		if (button.equals(this.getSubmitButton()))
		{
			return this.getForm();
		}

		return null;
	}

	@Override
	public IModelComparator getModelComparator()
	{
		return IModelComparator.ALWAYS_FALSE; // fixes #119
	}

	// Events //

	@Override
	protected void onInitialize()
	{
		if (this.getForm() == null)
		{
			throw new WicketRuntimeException("The form should not be null at this stage"); // if null, will not be available to the DialogBehavior
		}

		super.onInitialize();
	}

	@Override
	protected void onModelChanged()
	{
		this.getForm().modelChanged(); // fixes #119
	}

	@Override
	void internalOnClick(AjaxRequestTarget target, DialogButton button)
	{
		final Form<?> form = this.getForm(button);

		if (form != null)
		{
			form.getRootForm().onFormSubmitted(new DialogFormSubmitter(target));

			if (!form.hasError())
			{
				this.onClick(target, button); // fires onClick (& closes the dialog by default)
			}
		}
		else
		{
			this.onClick(target, button); // fires onClick (& closes the dialog by default)
		}
	}

	@Override
	public void onClose(AjaxRequestTarget target, DialogButton button)
	{
		// not mandatory to override
	}

	/**
	 * Triggered when the form is submitted, but the validation failed
	 *
	 * @param target
	 */
	protected abstract void onError(AjaxRequestTarget target);

	/**
	 * Triggered when the form is submitted, and the validation succeed
	 *
	 * @param target the {@link AjaxRequestTarget}
	 */
	protected abstract void onSubmit(AjaxRequestTarget target);

	// Factories //

	/**
	 * Gets the {@link ButtonAjaxPostBehavior} associated to the specified button.
	 *
	 * @return the {@link ButtonAjaxBehavior}
	 */
	@Override
	protected ButtonAjaxBehavior newButtonAjaxBehavior(IJQueryAjaxAware source, DialogButton button)
	{
		return new ButtonAjaxPostBehavior(source, button, this.getForm(button));
	}

	/**
	 * Provides the form-dialog {@link IFormSubmitter}<br/>
	 * This is basically the same technic used in AjaxButton class.
	 */
	protected class DialogFormSubmitter implements IFormSubmitter
	{
		private final AjaxRequestTarget target;

		/**
		 * Constructor
		 *
		 * @param target the {@link AjaxRequestTarget}
		 */
		public DialogFormSubmitter(AjaxRequestTarget target)
		{
			this.target = target;
		}

		@Override
		public Form<?> getForm()
		{
			return AbstractFormDialog.this.getForm();
		}

		@Override
		public boolean getDefaultFormProcessing()
		{
			return AbstractFormDialog.this.getDefaultFormProcessing();
		}

		@Override
		public void onSubmit()
		{
			AbstractFormDialog.this.onSubmit(this.target);
		}

		@Override
		public void onError()
		{
			AbstractFormDialog.this.onError(this.target);
		}

		@Override
		public void onAfterSubmit()
		{
			// wicket6
		}
	}
}
