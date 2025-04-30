/*
Licensed under the Apache License, Version 2.0 (the "License") http://www.apache.org/licenses/LICENSE-2.0
 */
package org.wicketstuff.jquery.ui.samples.jqueryui.dialog;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.StringResourceModel;
import org.wicketstuff.jquery.ui.form.button.AjaxButton;
import org.wicketstuff.jquery.ui.panel.JQueryFeedbackPanel;
import org.wicketstuff.jquery.ui.widget.dialog.DialogButton;
import org.wicketstuff.jquery.ui.widget.dialog.DialogButtons;
import org.wicketstuff.jquery.ui.widget.dialog.DialogIcon;
import org.wicketstuff.jquery.ui.widget.dialog.MessageDialog;

public class MessageDialogPage extends AbstractDialogPage
{
	private static final long serialVersionUID = 1L;

	public MessageDialogPage()
	{
		final Form<Void> form = new Form<Void>("form");
		this.add(form);

		// FeedbackPanel //
		final FeedbackPanel feedback = new JQueryFeedbackPanel("feedback");
		form.add(feedback.setOutputMarkupId(true));

		// Dialog //
		final MessageDialog informDialog = new MessageDialog("infoDialog", "Information", "This is a information message", DialogButtons.OK_CANCEL, DialogIcon.INFO) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClose(IPartialPageRequestHandler handler, DialogButton button)
			{
				// noop
			}
		};

		this.add(informDialog);

		final MessageDialog warningDialog = new MessageDialog("warningDialog", "Warning", "Are you sure you want to do something?", DialogButtons.YES_NO, DialogIcon.WARN) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClose(IPartialPageRequestHandler handler, DialogButton button)
			{
				// noop
			}
		};

		this.add(warningDialog);

		final MessageDialog errorDialog = new MessageDialog("errorDialog", new StringResourceModel("dialog.error.title", this, null), new StringResourceModel("dialog.error.message", this, null), DialogButtons.OK, DialogIcon.ERROR) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClose(IPartialPageRequestHandler handler, DialogButton button)
			{
				// noop
			}
		};

		this.add(errorDialog);


		// Buttons //
		form.add(new AjaxButton("info") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target)
			{
				informDialog.open(target);
			}
		});

		form.add(new AjaxButton("warning") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target)
			{
				warningDialog.open(target);
			}
		});

		form.add(new AjaxButton("error") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target)
			{
				errorDialog.open(target);
			}
		});
	}
}
