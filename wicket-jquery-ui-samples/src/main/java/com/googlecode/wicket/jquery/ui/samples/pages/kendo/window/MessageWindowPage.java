package com.googlecode.wicket.jquery.ui.samples.pages.kendo.window;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;

import com.googlecode.wicket.kendo.ui.KendoIcon;
import com.googlecode.wicket.kendo.ui.form.button.AjaxButton;
import com.googlecode.wicket.kendo.ui.panel.KendoFeedbackPanel;
import com.googlecode.wicket.kendo.ui.widget.window.MessageWindow;
import com.googlecode.wicket.kendo.ui.widget.window.WindowButton;
import com.googlecode.wicket.kendo.ui.widget.window.WindowButtons;

public class MessageWindowPage extends AbstractWindowPage
{
	private static final long serialVersionUID = 1L;

	public MessageWindowPage()
	{
		final Form<Void> form = new Form<Void>("form");
		this.add(form);

		// FeedbackPanel //
		final KendoFeedbackPanel feedback = new KendoFeedbackPanel("feedback");
		form.add(feedback.setOutputMarkupId(true));

		// Window //
		final MessageWindow window = new MessageWindow("window", "Information", "This is a sample message", WindowButtons.OK_CANCEL, KendoIcon.NOTE) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, WindowButton button)
			{
				if (button.match(LBL_OK))
				{
					this.info("Ok has been clicked");
				}
				else
				{
					this.warn("Cancel has been clicked");
				}

				target.add(feedback);
			}
		};

		this.add(window);

		// Button //
		form.add(new AjaxButton("button") {

			private static final long serialVersionUID = 1L;
			
			@Override
			protected String getIcon()
			{
				return KendoIcon.MAXIMIZE;
			}

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				window.open(target);
			}
		});
	}
}
