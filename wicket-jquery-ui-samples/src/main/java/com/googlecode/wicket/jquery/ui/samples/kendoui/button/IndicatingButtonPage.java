package com.googlecode.wicket.jquery.ui.samples.kendoui.button;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.wicket.kendo.ui.form.button.IndicatingAjaxButton;
import com.googlecode.wicket.kendo.ui.form.button.IndicatingButton;
import com.googlecode.wicket.kendo.ui.panel.KendoFeedbackPanel;

public class IndicatingButtonPage extends AbstractButtonPage // NOSONAR
{
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(IndicatingButtonPage.class);

	public IndicatingButtonPage()
	{
		final Form<Void> form = new Form<Void>("form") {

			private static final long serialVersionUID = 1L;

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

		this.add(form);

		// FeedbackPanel //
		final KendoFeedbackPanel feedback = new KendoFeedbackPanel("feedback");
		form.add(feedback.setOutputMarkupId(true));

		// Buttons //
		form.add(new IndicatingButton("button1") { // NOSONAR

			private static final long serialVersionUID = 1L;

			@Override
			public boolean isDisabledOnClick()
			{
				/*
				 * Caution: it seems that disabled buttons - after click - does not submit the form (anymore?)
				 */
				return false;
			}
		});

		form.add(new IndicatingAjaxButton("button2") { // NOSONAR

			private static final long serialVersionUID = 1L;

			@Override
			public boolean isDisabledOnClick()
			{
				return true;
			}

			@Override
			protected void onSubmit(AjaxRequestTarget target)
			{
				target.add(feedback);
			}
		});
	}

	private final void info(Component component)
	{
		this.info(component.getMarkupId() + " has been submitted");
	}
}
