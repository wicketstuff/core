package com.googlecode.wicket.jquery.ui.samples.pages.dialog;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.samples.component.UploadDialog;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton;

public class UploadDialogPage extends AbstractDialogPage
{
	private static final long serialVersionUID = 1L;

	public UploadDialogPage()
	{
		final Form<Void> form = new Form<Void>("form");
		this.add(form);

		// FeedbackPanel //
		final FeedbackPanel feedback = new JQueryFeedbackPanel("feedback");
		form.add(feedback);

		// Dialog //
		final UploadDialog dialog = new UploadDialog("dialog", "Upload file") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit(AjaxRequestTarget target)
			{
				super.onSubmit(target);

				FileUpload fu = this.getModelObject();

				if (fu != null)
				{
					this.info(String.format("Uploaded file: '%s'", fu.getClientFileName()));
				}
			}

			@Override
			public void onClose(AjaxRequestTarget target, DialogButton button)
			{
				super.onClose(target, button);

				target.add(feedback);
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
