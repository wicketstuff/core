// -[KeepHeading]-

// -[Copyright]-

/**
 * � 2006, 2009. Step Ahead Software Pty Ltd. All rights reserved.
 * 
 * Source file created and managed by Javelin (TM) Step Ahead Software. To
 * maintain code and model synchronization you may directly edit code in method
 * bodies and any sections starting with the 'Keep_*' marker. Make all other
 * changes via Javelin. See http://stepaheadsoftware.com for more details.
 */
package org.wicketstuff.modalx;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;

// -[Class]-

/**
 * Class Name : ModalFormPanel Diagram : Wicket Generic Modal Windows Project : Echobase framework
 * Type : concrete Base class for all modal content panels that contain a form.
 * 
 * @author Chris Colman
 */
public class ModalFormPanel extends ModalContentPanel
{
	private static final long serialVersionUID = 1L;
	// -[KeepWithinClass]-
	public static final int MR_OK = 1;
	public static final int MR_CANCEL = 2;

	// -[Fields]-

	/**
	 * Used to display errors to the user.
	 */
	private org.apache.wicket.markup.html.panel.FeedbackPanel feedbackPanel;

	/**
	 * Stores the result of this modal form: ok or cancel.
	 */
	private int modalResult = MR_CANCEL;

	protected Form<?> form;

	// -[Methods]-

	/**
	 * Adds the button controls to the form. By default OK and Cancel buttons are added but this
	 * method can be overridden to add a different button set like YES, NO etc.,
	 */
	public void addControlComponents()
	{
		// Add a submit button.
		form.add(new AjaxButton("ok", form)
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit(AjaxRequestTarget target)
			{
				modalResult = MR_OK;
				target.add(feedbackPanel);
				ModalFormPanel.this.updateModel();
				closeModal(target);
			}

			@Override
			public void onError(AjaxRequestTarget target)
			{
				target.add(feedbackPanel);
			}
		});

		// Add a cancel button.
		AjaxButton cancelBtn = new AjaxButton("cancel", form)
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target)
			{
				modalResult = MR_CANCEL;
				onCancel(target);
			}

			@Override
			protected void onError(AjaxRequestTarget target)
			{
				target.add(feedbackPanel);
			}
		};

		// no validation for cancel button
		cancelBtn.setDefaultFormProcessing(false);
		form.add(cancelBtn);
	}

	/**
	 * Sets modalResult
	 */
	public void setModalResult(int modalResult)
	{
		this.modalResult = modalResult;
	}

	/**
	 * Returns modalResult
	 */
	public int getModalResult()
	{
		return modalResult;
	}

	/**
	 * Called after form submission with successful validation to update the domain model with data
	 * from the form components.
	 */
	public void updateModel()
	{
	}

	/**
	 * Get the ModalContentWindow to show this ModalContentPanel.
	 */
	@Override
	public void show(AjaxRequestTarget target)
	{
		// All forms have a feedback panel
		feedbackPanel = new org.apache.wicket.markup.html.panel.FeedbackPanel("feedback");
		feedbackPanel.setOutputMarkupId(true);
		add(feedbackPanel);

		// Create the form, to use later for the buttons
		form = createForm();

		addFormComponents();

		addControlComponents();

		add(form);

		super.show(target);
	}

	/**
	 * Overridden in derived classes to add the form components. Calll form.add to add components,
	 * not just add.
	 */
	public void addFormComponents()
	{
	}

	/**
	 * Closes the modal window.
	 */
	public void onCancel(AjaxRequestTarget target)
	{
		modalContentWindow.close(target);
	}

	/**
	 * Creates a Form object. Can be overridden in derived classes to create a form object that
	 * extends Form.
	 */
	public Form<?> createForm()
	{
		return new Form<Void>("form");
	}

	/**
	 * Constructs the object passing in a IWindowClosedListener (which can be null if parent is not
	 * interested in being notified when the child form is closed).
	 */
	public ModalFormPanel(ModalContentWindow iModalContentWindow,
		IWindowCloseListener iWindowCloseListener)
	{
		super(iModalContentWindow, iWindowCloseListener);

		windowCloseListener = iWindowCloseListener;
	}

}
