package com.googlecode.wicket.jquery.ui.samples.jqueryui.dialog;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.samples.component.SimpleDialog;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton;

public class CustomDialogPage extends AbstractDialogPage
{
	private static final long serialVersionUID = 1L;

	public CustomDialogPage()
	{
		// Form //
		final Form<Void> form = new Form<Void>("form");
		this.add(form);

		// FeedbackPanel //
		final FeedbackPanel feedback = new JQueryFeedbackPanel("feedback");
		form.add(feedback.setOutputMarkupId(true));

		// Dialog //
		final SimpleDialog dialog = new SimpleDialog("dialog", "Simple dialog box", Model.of("I am the widget dialog model")) {

			private static final long serialVersionUID = 1L;

			@Override
			public boolean isDefaultCloseEventEnabled()
			{
				return true;
			}

			@Override
			public boolean isEscapeCloseEventEnabled()
			{
				return true;
			}

			@Override
			public void onClose(IPartialPageRequestHandler handler, DialogButton button)
			{
				this.info(button + " has been clicked");

				if (button != null && button.match(OK))
				{
					this.info(String.format("The model object is: '%s'", this.getModelObject()));
				}

				handler.add(feedback);
			}
		};

		this.add(dialog);

		// Buttons //
		form.add(new AjaxButton("open") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target)
			{
				dialog.open(target);
			}
		});
	}
}
