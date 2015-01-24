package com.googlecode.wicket.jquery.ui.samples.pages.kendo.button;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;

import com.googlecode.wicket.kendo.ui.form.button.AjaxButton;
import com.googlecode.wicket.kendo.ui.panel.KendoFeedbackPanel;

public class AjaxButtonPage extends AbstractButtonPage
{
	private static final long serialVersionUID = 1L;

	public AjaxButtonPage()
	{
		this.initialize();
	}

	private void initialize()
	{
		final Form<Void> form = new Form<Void>("form");
		this.add(form);

		// FeedbackPanel //
		final KendoFeedbackPanel feedback = new KendoFeedbackPanel("feedback");
		form.add(feedback.setOutputMarkupId(true));

		// Buttons //
		form.add(new AjaxButton("button1") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				AjaxButtonPage.this.info(this);
				target.add(feedback);
			}
		});

		form.add(new AjaxButton("button2") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				AjaxButtonPage.this.info(this);
				target.add(form);
			}
		});
	}

	private final void info(Component component)
	{
		this.info(component.getMarkupId() + " has been clicked");
	}
}
