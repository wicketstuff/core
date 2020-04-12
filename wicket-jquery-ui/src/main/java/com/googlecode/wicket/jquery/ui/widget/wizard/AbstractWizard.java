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
import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
import org.apache.wicket.extensions.wizard.IWizard;
import org.apache.wicket.extensions.wizard.IWizardModel;
import org.apache.wicket.extensions.wizard.IWizardModelListener;
import org.apache.wicket.extensions.wizard.IWizardStep;
import org.apache.wicket.extensions.wizard.Wizard;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.lang.Args;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.resource.StyleSheetPackageHeaderItem;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.widget.dialog.AbstractDialog;
import com.googlecode.wicket.jquery.ui.widget.dialog.AbstractFormDialog;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton;

/**
 * Provides the base class for wizard-based dialogs
 *
 * @author Sebastien Briquet - sebfz1
 *
 * @param <T> the model object type
 */
public abstract class AbstractWizard<T extends Serializable> extends AbstractFormDialog<T> implements IWizardModelListener, IWizard
{
	private static final long serialVersionUID = 1L;

	private IWizardModel wizardModel;
	private Form<T> form;
	private WebMarkupContainer feedback;

	// Buttons //
	private DialogButton btnPrev;
	private DialogButton btnNext;
	private DialogButton btnLast;
	private DialogButton btnFinish;
	private DialogButton btnCancel;

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param title the dialog's title
	 */
	public AbstractWizard(String id, String title)
	{
		super(id, title);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param title the dialog's title
	 */
	public AbstractWizard(String id, IModel<String> title)
	{
		super(id, title);
	}

	/**
	 * Constructor
	 *
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
	 *
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
	 *
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
	 *
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
	 *
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
	 *
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
	 *
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
	 *
	 * @param id the markup id
	 * @param title the dialog's title
	 * @param model the dialog's model
	 */
	public AbstractWizard(String id, IModel<String> title, IModel<T> model)
	{
		super(id, title, model);
	}

	// Methods //

	/**
	 * Initialization
	 *
	 * @param wizardModel the {@link IWizardModel}
	 */
	protected final void init(final IWizardModel wizardModel)
	{
		Args.notNull(wizardModel, "wizardModel");

		this.wizardModel = wizardModel;
		this.wizardModel.addListener(this);

		// form //
		this.form = new Form<T>(Wizard.FORM_ID);
		this.add(this.form);

		// header (title + summary )//
		this.form.add(new EmptyPanel(Wizard.HEADER_ID));

		// dummy view to be replaced //
		this.form.add(new EmptyPanel(Wizard.VIEW_ID));
		// this.form.add(newOverviewBar(Wizard.OVERVIEW_ID)); //OverviewBar not handled
	}

	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);

		response.render(new StyleSheetPackageHeaderItem(AbstractWizard.class)); // AbstractWizard.css
	}

	/**
	 * Reloads the wizard, by calling {@link #onConfigure(IPartialPageRequestHandler)} and re-attaching the form<br>
	 * This method is called when, for instance, the wizard opens or the step changes.
	 *
	 * @param handler the {@link IPartialPageRequestHandler}
	 */
	protected void reload(IPartialPageRequestHandler handler)
	{
		this.onConfigure(handler);

		// update form //
		handler.add(this.form);
	}

	// Properties //

	/**
	 * Gets the wizard {@link FeedbackPanel}
	 *
	 * @return the {@link FeedbackPanel}
	 */
	public WebMarkupContainer getFeedbackPanel()
	{
		return this.feedback;
	}

	/**
	 * Indicates whether the wizard should close when {@link #onCancel()} is triggered
	 * 
	 * @return {@code true} by default
	 */
	protected boolean closeOnCancel()
	{
		return true;
	}

	/**
	 * Indicates whether the wizard should close when {@link #onFinish()} is triggered
	 * 
	 * @return {@code true} by default
	 */
	protected boolean closeOnFinish()
	{
		return true;
	}

	// Events //

	@Override
	protected void onInitialize()
	{
		// Feedback //
		this.feedback = this.newFeedbackPanel(Wizard.FEEDBACK_ID);
		this.form.add(this.feedback);

		// buttons (located in #onInitialize() for #getString() to work properly, without warnings)
		this.btnPrev = new DialogButton("PREV", this.getString("wizard.button.prev"));
		this.btnNext = new DialogButton("NEXT", this.getString("wizard.button.next"));
		this.btnLast = new DialogButton("LAST", this.getString("wizard.button.last"));
		this.btnFinish = new DialogButton(AbstractDialog.SUBMIT, this.getString("wizard.button.finish"));
		this.btnCancel = new DialogButton(AbstractDialog.CANCEL, this.getString("wizard.button.cancel"));

		// should be called *after* button's initialization
		super.onInitialize();
	}

	/**
	 * {@inheritDoc}<br>
	 * <b>Warning:</b> {@code autoOpen} option is not supported
	 */
	@Override
	public void onConfigure(JQueryBehavior behavior)
	{
		super.onConfigure(behavior);
	}

	/**
	 * Called when the wizard needs to be configured.
	 *
	 * @param handler the {@link IPartialPageRequestHandler}
	 */
	protected void onConfigure(IPartialPageRequestHandler handler)
	{
		// configure buttons //
		this.btnPrev.setEnabled(this.wizardModel.isPreviousAvailable(), handler);
		this.btnNext.setEnabled(this.wizardModel.isNextAvailable(), handler);
		this.btnLast.setEnabled(this.wizardModel.isLastAvailable(), handler);
		this.btnLast.setVisible(this.wizardModel.isLastVisible(), handler);
		this.btnCancel.setVisible(this.wizardModel.isCancelVisible(), handler);

		boolean enabled = this.wizardModel.isLastStep(this.wizardModel.getActiveStep());
		this.btnFinish.setEnabled(enabled, handler);
		// TODO: WizardModelStrategy#isLastStepEnabled()
	}

	@Override
	protected void onOpen(IPartialPageRequestHandler handler)
	{
		super.onOpen(handler);

		this.wizardModel.reset(); // reset model to prepare for action
		this.reload(handler);
	}

	/**
	 * Triggered when a wizard button is clicked. If the button is a form-submitter button, the validation should have succeeded for this event to be triggered. This implementation overrides the default
	 * {@link AbstractDialog#onClick(AjaxRequestTarget, DialogButton)} implementation in order to not close the dialog.
	 */
	@Override
	public final void onClick(AjaxRequestTarget target, DialogButton button)
	{
		if (button.equals(this.getSubmitButton()))
		{
			this.onFinish();
			this.onFinish(target);

			if (this.closeOnFinish())
			{
				this.close(target, button);
			}
		}
		else if (button.equals(this.getCancelButton()))
		{
			this.onCancel();
			this.onCancel(target);

			if (this.closeOnCancel())
			{
				this.close(target, button);
			}
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
			this.reload(target);
		}
	}

	@Override
	protected void onSubmit(AjaxRequestTarget target, DialogButton button)
	{
		// If the clicked button was a form-submitter, calls step#applyState() //
		IWizardModel wizardModel = this.getWizardModel();
		wizardModel.getActiveStep().applyState();
	}

	@Override
	protected final void onError(AjaxRequestTarget target, DialogButton button)
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
	 *
	 * @param target the {@link AjaxRequestTarget}
	 */
	protected abstract void onFinish(AjaxRequestTarget target);

	/**
	 * Triggered when the wizard has been canceled
	 *
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
	public DialogButton getSubmitButton()
	{
		return this.btnFinish;
	}

	/**
	 * Gets the button that is in charge to cancel to wizard.<br>
	 *
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
	 * Returns the form associated to the button.<br>
	 * It means that it will return the form if the supplied button is considered as a form submitter and null otherwise. <br>
	 * This method may be overridden to specify other form-submitter buttons (ie: btnPrev)
	 *
	 * @param button the dialog's button
	 * @return the {@link Form} or {@code null}
	 */
	@Override
	protected Form<?> getForm(DialogButton button)
	{
		// btnNext and btnLast are also form-submitter buttons (in addition to btnFinish)
		if (button.equals(this.btnNext) || button.equals(this.btnLast))
		{
			return this.getForm();
		}

		return super.getForm(button);
	}

	// Factories //

	/**
	 * Gets a new {@code FeedbackPanel}
	 * 
	 * @param id the markup id
	 * @return a new {@code JQueryFeedbackPanel} by default
	 */
	protected WebMarkupContainer newFeedbackPanel(String id)
	{
		return new JQueryFeedbackPanel(id);
	}
}
