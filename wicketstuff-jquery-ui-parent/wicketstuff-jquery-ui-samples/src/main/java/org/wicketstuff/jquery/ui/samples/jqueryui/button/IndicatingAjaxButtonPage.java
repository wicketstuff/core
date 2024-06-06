/*
Licensed under the Apache License, Version 2.0 (the "License") http://www.apache.org/licenses/LICENSE-2.0
 */
package org.wicketstuff.jquery.ui.samples.jqueryui.button;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.jquery.ui.JQueryIcon;
import org.wicketstuff.jquery.ui.form.button.IndicatingAjaxButton;
import org.wicketstuff.jquery.ui.form.button.IndicatingAjaxButton.Position;
import org.wicketstuff.jquery.ui.panel.JQueryFeedbackPanel;

public class IndicatingAjaxButtonPage extends AbstractButtonPage // NOSONAR
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
		form.add(new IndicatingAjaxButton("button1") { // NOSONAR

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target)
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
				target.add(feedbackPanel);
			}
		});

		form.add(new IndicatingAjaxButton("button2") { // NOSONAR

			private static final long serialVersionUID = 1L;

			@Override
			protected String getIcon()
			{
				return JQueryIcon.PLAY;
			}

			@Override
			protected boolean isDisabledOnClick()
			{
				return true;
			}

			@Override
			protected void onSubmit(AjaxRequestTarget target)
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
				target.add(feedbackPanel);
			}
		}.setPosition(Position.RIGHT));
	}

	private void info(Component component)
	{
		this.info(component.getMarkupId() + " has been clicked");
	}
}
