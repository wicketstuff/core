package com.googlecode.wicket.jquery.ui.samples.pages.kendo.button;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.wicket.kendo.ui.form.button.IndicatingAjaxButton;
import com.googlecode.wicket.kendo.ui.form.button.IndicatingButton;
import com.googlecode.wicket.kendo.ui.panel.KendoFeedbackPanel;

public class IndicatingButtonPage extends AbstractButtonPage
{
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(IndicatingButtonPage.class);

	public IndicatingButtonPage()
	{
		final Form<Void> form = new Form<Void>("form");
		this.add(form);

		// FeedbackPanel //
		final KendoFeedbackPanel feedback = new KendoFeedbackPanel("feedback");
		form.add(feedback.setOutputMarkupId(true));

		// Buttons //
		final Button button1 = new IndicatingButton("button1") {

			private static final long serialVersionUID = 1L;

			@Override
			protected boolean isDisabledOnClick()
			{
				/*
				 * Warning: if true the button will not be send as part of the post because of its disabled state. Therefore Button.onSubmit() will not be reached, Form.onSubmit() should be used instead.
				 */
				return false; // default value
			}

			@Override
			public void onSubmit()
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

				IndicatingButtonPage.this.info(this);
			}
		};

		form.add(button1);

		form.add(new IndicatingAjaxButton("button2") {

			private static final long serialVersionUID = 1L;

			@Override
			protected boolean isDisabledOnClick()
			{
				return true;
			}

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

				IndicatingButtonPage.this.info(this);
				target.add(feedback);
			}
		});
	}

	private final void info(Component component)
	{
		this.info(component.getMarkupId() + " has been clicked");
	}
}
