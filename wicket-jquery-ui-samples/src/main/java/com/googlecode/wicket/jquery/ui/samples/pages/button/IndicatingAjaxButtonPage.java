package com.googlecode.wicket.jquery.ui.samples.pages.button;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.wicket.jquery.ui.form.button.IndicatingAjaxButton;
import com.googlecode.wicket.jquery.ui.form.button.IndicatingAjaxButton.Position;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;

public class IndicatingAjaxButtonPage extends AbstractButtonPage
{
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(IndicatingAjaxButtonPage.class);

	public IndicatingAjaxButtonPage()
	{
		final Form<Void> form = new Form<Void>("form");
		this.add(form);

		// FeedbackPanel //
		final FeedbackPanel feedbackPanel = new JQueryFeedbackPanel("feedback");
		form.add(feedbackPanel.setOutputMarkupId(true));

		// Buttons //
		final Button button1 = new IndicatingAjaxButton("button1") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				try
				{
					Thread.sleep(2000);
				}
				catch (InterruptedException e)
				{
					if (LOG.isDebugEnabled())
					{
						LOG.debug(e.getMessage(), e);
					}
				}

				IndicatingAjaxButtonPage.this.info(this);
				target.add(form);
			}
		};

		form.add(button1);

		form.add(new IndicatingAjaxButton("button2") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				try
				{
					Thread.sleep(2000);
				}
				catch (InterruptedException e)
				{
					if (LOG.isDebugEnabled())
					{
						LOG.debug(e.getMessage(), e);
					}
				}

				IndicatingAjaxButtonPage.this.info(this);
				target.add(form);
			}
		}.setPosition(Position.RIGHT));
	}

	private void info(Component component)
	{
		this.info(component.getMarkupId() + " has been clicked");
	}
}
