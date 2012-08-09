package com.googlecode.wicket.jquery.ui.samples.pages.dialog;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

import com.googlecode.wicket.jquery.ui.dialog.DialogButton;
import com.googlecode.wicket.jquery.ui.dialog.DialogButtons;
import com.googlecode.wicket.jquery.ui.dialog.DialogIcon;
import com.googlecode.wicket.jquery.ui.dialog.MessageDialog;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;

public class MessageDialogPage extends AbstractDialogPage
{
	private static final long serialVersionUID = 1L;
	
	public MessageDialogPage()
	{
		this.init();
	}
	
	private void init()
	{
		final Form<Void> form = new Form<Void>("form");
		this.add(form);

		// FeedbackPanel //
		final FeedbackPanel feedbackPanel = new JQueryFeedbackPanel("feedback");
		form.add(feedbackPanel.setOutputMarkupId(true));
		
		// Dialog //
		final MessageDialog informDialog = new MessageDialog("infoDialog", "Information", "This is a information message", DialogButtons.OK_CANCEL, DialogIcon.INFO) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onClose(AjaxRequestTarget target, DialogButton button)
			{
			}
		};

		this.add(informDialog);

		final MessageDialog warningDialog = new MessageDialog("warningDialog", "Warning", "Are you sure you want to do something?", DialogButtons.YES_NO, DialogIcon.WARN) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onClose(AjaxRequestTarget target, DialogButton button)
			{
			}
		};
		
		this.add(warningDialog);

		final MessageDialog errorDialog = new MessageDialog("errorDialog", "Error", "This is an error message!", DialogButtons.OK, DialogIcon.ERROR) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onClose(AjaxRequestTarget target, DialogButton button)
			{
			}
		};
		
		this.add(errorDialog);

		
		// Buttons //		
		form.add(new AjaxButton("info") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				informDialog.open(target);
			}
		});
		
		form.add(new AjaxButton("warning") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				warningDialog.open(target);
			}
		});
		
		form.add(new AjaxButton("error") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				errorDialog.open(target);
			}
		});
	}
}
