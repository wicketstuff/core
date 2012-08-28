package com.googlecode.wicket.jquery.ui.samples.pages.test;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.ui.dialog.DialogButton;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.samples.TemplatePage;

public class TestPage extends TemplatePage
{
	private static final long serialVersionUID = 1L;

	public TestPage()
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
		final TabDialog dialog = new TabDialog("dialog", "Tabs dialog box", new Model<String>()) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onClose(AjaxRequestTarget target, DialogButton button)
			{
				this.info(button + " has been clicked");

				if (button != null && button.equals(LBL_OK))
				{
					this.info(String.format("The model object is: '%s'", this.getModelObject()));
				}

				target.add(feedbackPanel);
			}
		};

		this.add(dialog);

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
