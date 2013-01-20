package com.googlecode.wicket.jquery.ui.samples.pages.button;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;

public class AjaxButtonPage extends AbstractButtonPage
{
	private static final long serialVersionUID = 1L;
	
	public AjaxButtonPage()
	{
		this.init();
	}
	
	private void init()
	{
		final Form<Void> form = new Form<Void>("form");
		this.add(form);

		// FeedbackPanel //
		final FeedbackPanel feedback = new JQueryFeedbackPanel("feedback");
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
	
	private void info(Component component)
	{
		this.info(component.getMarkupId() + " has been clicked");
	}
}
