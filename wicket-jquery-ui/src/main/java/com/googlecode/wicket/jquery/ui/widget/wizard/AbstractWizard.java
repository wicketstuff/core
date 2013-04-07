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
package com.googlecode.wicket.jquery.ui.widget.wizard;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.wizard.IWizard;
import org.apache.wicket.extensions.wizard.IWizardModel;
import org.apache.wicket.extensions.wizard.IWizardModelListener;
import org.apache.wicket.extensions.wizard.IWizardStep;
import org.apache.wicket.extensions.wizard.Wizard;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;

import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.widget.dialog.AbstractFormDialog;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton;

/**
 * Provides the base class for wizard-based dialogs
 * @author Sebastien Briquet - sebfz1
 *
 * @param <T> the model object type
 */
public abstract class AbstractWizard<T extends Serializable> extends AbstractFormDialog<T> implements IWizardModelListener, IWizard
{
	private static final long serialVersionUID = 1L;

	private IWizardModel wizardModel;
	private Form<T> form;
	private FeedbackPanel feedback;

	// Buttons //
	private DialogButton btnPrev;
	private DialogButton btnNext;
	private DialogButton btnLast;
	private DialogButton btnFinish;
	private DialogButton btnCancel;

	/**
	 * Constructor
	 * @param id the markup id
	 * @param title the dialog's title
	 */
	public AbstractWizard(String id, String title)
	{
		super(id, title);
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param title the dialog's title
	 */
	public AbstractWizard(String id, IModel<String> title)
	{
		super(id, title);
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param title the dialog's title
	 * @param wizardModel the {@link IWizardModel}
	 */
	public AbstractWizard(String id, String title, IWizardModel wizardModel)
	{
		super(id, title);

		this.init(wizardModel);
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param title the dialog's title
	 * @param wizardModel the {@link IWizardModel}
	 */
	public AbstractWizard(String id, IModel<String> title, IWizardModel wizardModel)
	{
		super(id, title);

		this.init(wizardModel);
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param title the dialog's title
	 * @param modal indicates whether the dialog is modal
	 */
	public AbstractWizard(String id, String title, boolean modal)
	{
		super(id, title, modal);
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param title the dialog's title
	 * @param modal indicates whether the dialog is modal
	 */
	public AbstractWizard(String id, IModel<String> title, boolean modal)
	{
		super(id, title, modal);
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param title the dialog's title
	 * @param model the dialog's model
	 * @param modal indicates whether the dialog is modal
	 */
	public AbstractWizard(String id, String title, IModel<T> model, boolean modal)
	{
		super(id, title, model, modal);
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param title the dialog's title
	 * @param model the dialog's model
	 * @param modal indicates whether the dialog is modal
	 */
	public AbstractWizard(String id, IModel<String> title, IModel<T> model, boolean modal)
	{
		super(id, title, model, modal);
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param title the dialog's title
	 * @param model the dialog's model
	 */
	public AbstractWizard(String id, String title, IModel<T> model)
	{
		super(id, title, model);
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param title the dialog's title
	 * @param model the dialog's model
	 */
	public AbstractWizard(String id, IModel<String> title, IModel<T> model)
	{
		super(id, title, model);
	}

	/**
	 * Initialization
	 * @param wizardModel the {@link IWizardModel}
	 */
	protected void init(final IWizardModel wizardModel)
	{
		if (wizardModel == null)
		{
			throw new IllegalArgumentException("argument wizardModel must be not null");
		}

		this.wizardModel = wizardModel;
		this.wizardModel.addListener(this);

		// form //
		this.form = new Form<T>(Wizard.FORM_ID);
		this.add(this.form);

		// header (title + summary )//
		this.form.add(new EmptyPanel(Wizard.HEADER_ID));

		// Feedback //
		this.feedback = new JQueryFeedbackPanel(Wizard.FEEDBACK_ID);
		this.form.add(this.feedback);

		// dummy view to be replaced //
		this.form.add(new EmptyPanel(Wizard.VIEW_ID));
//		this.form.add(newOverviewBar(Wizard.OVERVIEW_ID)); //OverviewBar not handled
	}

	// Properties //
	public FeedbackPanel getFeedbackPanel()
	{
		return this.feedback;
	}

	// Events //
	@Override
	protected void onInitialize()
	{
		// buttons (located in #onInitialize() for #getString() to work properly, without warnings)
		this.btnPrev = new DialogButton(this.getString("wizard.button.prev"));
		this.btnNext = new DialogButton(this.getString("wizard.button.next"));
		this.btnLast = new DialogButton(this.getString("wizard.button.last"));
		this.btnFinish = new DialogButton(this.getString("wizard.button.finish"));
		this.btnCancel = new DialogButton(this.getString("wizard.button.cancel"));

		// should be called *after* button's initialization
		super.onInitialize();
	}

	/**
	 * Called when the wizard needs to be configured. For instance when the wizard opens or when the step changes.
	 * @param target the {@link AjaxRequestTarget}
	 */
	protected void onConfigure(AjaxRequestTarget target)
	{
		// configure buttons //
		this.btnPrev.setEnabled(this.wizardModel.isPreviousAvailable(), target);
		this.btnNext.setEnabled(this.wizardModel.isNextAvailable(), target);
		this.btnLast.setEnabled(this.wizardModel.isLastAvailable(), target);
		this.btnLast.setVisible(this.wizardModel.isLastVisible(), target);
		this.btnCancel.setVisible(this.wizardModel.isCancelVisible(), target);

		boolean enabled = this.wizardModel.isLastStep(this.wizardModel.getActiveStep());
		this.btnFinish.setEnabled(enabled, target);
		//TODO: WizardModelStrategy#isLastStepEnabled()

		// update form //
		target.add(this.form);
	}

	@Override
	protected void onOpen(AjaxRequestTarget target)
	{
		super.onOpen(target);

		this.wizardModel.reset(); // reset model to prepare for action
		this.onConfigure(target);
	}

	/**
	 * TODO: javadoc
	 * Triggered when a button is clicked.
	 * If the button is a form-submitter button, the validation should have succeeded for this event to be triggered.
	 * This implementation overrides the default implementation to not close the dialog.
	 */
	@Override
	public final void onClick(AjaxRequestTarget target, DialogButton button)
	{
		if (button.equals(this.getSubmitButton()))
		{
			this.onFinish();
			this.onFinish(target);
			this.close(target, button);
		}
		else if (button.equals(this.getCancelButton()))
		{
			this.onCancel();
			this.onCancel(target);
			this.close(target, this.getCancelButton());
		}
		else
		{
			// will call onActiveStepChanged
			if (button.equals(this.btnPrev))
			{
				this.getWizardModel().previous();
			}
			else if (button.equals(this.btnNext))
			{
				this.getWizardModel().next();
			}
			else if (button.equals(this.btnLast))
			{
				this.getWizardModel().last();
			}

			// reconfigure buttons and refresh the form //
			AbstractWizard.this.onConfigure(target);
		}
	}

	@Override
	protected void onSubmit(AjaxRequestTarget target)
	{
		/* If the clicked button was a form-submitter, calls step#applyState() */
		IWizardModel wizardModel = this.getWizardModel();
		wizardModel.getActiveStep().applyState();
	}

	@Override
	protected final void onError(AjaxRequestTarget target)
	{
		target.add(this.feedback);
	}

	@Override
	public void onFinish()
	{
		// from IWizardModelListener, not intended to be used.
	}

	@Override
	public void onCancel()
	{
		// from IWizardModelListener, not intended to be used.
	}

	/**
	 * Triggered when the wizard completes
	 * @param target the {@link AjaxRequestTarget}
	 */
	protected abstract void onFinish(AjaxRequestTarget target);

	/**
	 * Triggered when the wizard has been canceled
	 * @param target the {@link AjaxRequestTarget}
	 */
	protected void onCancel(AjaxRequestTarget target)
	{
	}


	// IWizard //
	@Override
	public IWizardModel getWizardModel()
	{
		return this.wizardModel;
	}


	// IWizardModelListener //
	@Override
	public void onActiveStepChanged(IWizardStep step)
	{
		this.form.replace(step.getHeader(Wizard.HEADER_ID, this, this));
		this.form.replace(step.getView(Wizard.VIEW_ID, this, this));
	}


	// AbstractFormDialog //
	@Override
	protected List<DialogButton> getButtons()
	{
		return Arrays.asList(this.btnPrev, this.btnNext, this.btnLast, this.btnFinish, this.btnCancel);
	}

	@Override
	protected DialogButton getSubmitButton()
	{
		return this.btnFinish;
	}

	/**
	 * Gets the button that is in charge to cancel to wizard.<br/>
	 * @return the cancel button
	 */
	protected DialogButton getCancelButton()
	{
		return this.btnCancel;
	}

	@Override
	public Form<?> getForm()
	{
		return this.form;
	}

	/**
	 * Returns the form associated to the button.<br/>
	 * It means that it will return the form if the supplied button is considered as a form submitter and null otherwise.
	 * <br/>
	 * This method may be overridden to specify other form-submitter buttons (ie: btnPrev)
	 *
	 * @param button the dialog's button
	 * @return the {@link Form} or <code>null</code>
	 */
	@Override
	protected Form<?> getForm(DialogButton button)
	{
		//btnNext and btnLast are also form-submitter buttons (in addition to btnFinish)
		if (button.equals(this.btnNext) || button.equals(this.btnLast))
		{
			return this.getForm();
		}

		return super.getForm(button);
	}
}
