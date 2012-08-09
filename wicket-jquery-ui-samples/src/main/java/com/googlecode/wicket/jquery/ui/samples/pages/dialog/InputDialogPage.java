package com.googlecode.wicket.jquery.ui.samples.pages.dialog;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.ui.dialog.DialogButton;
import com.googlecode.wicket.jquery.ui.dialog.InputDialog;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;

public class InputDialogPage extends AbstractDialogPage
{
	private static final long serialVersionUID = 1L;
	
	public InputDialogPage()
	{
		this.init();
	}
	
	private void init()
	{
		final Form<Void> form = new Form<Void>("form");
		this.add(form);

		// FeedbackPanel //
		form.add(new JQueryFeedbackPanel("feedback"));
		
		// Dialog //
		final InputDialog<String> dialog = new InputDialog<String>("dialog", "Input", "Please provide a value:", new Model<String>("a sample value")) {
			
			private static final long serialVersionUID = 1L;
			
			@Override
			public void onSubmit(AjaxRequestTarget target)
			{
				this.info("The form has been submitted");
				this.info(String.format("The model object is: '%s'", this.getModelObject()));
			}

			@Override
			protected void onClose(AjaxRequestTarget target, DialogButton button)
			{
				this.info(button + " has been clicked");
				target.add(form);
			}
		};

		this.add(dialog); //the dialog is not within the form
		
		// Buttons //
		form.add(new AjaxButton("open") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				dialog.open(target);
			}
		});
	}
}
