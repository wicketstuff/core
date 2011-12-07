package org.wicketstuff.jquery.demo;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.wicketstuff.jquery.Options;
import org.wicketstuff.jquery.jgrowl.JGrowlFeedbackPanel;

@SuppressWarnings("serial")
public class Page4JGrowl extends PageSupport
{

	public Page4JGrowl()
	{

		final JGrowlFeedbackPanel feedback = new JGrowlFeedbackPanel("jgrowlFeedback");
		add(feedback);

		final Options errorOptions = new Options();
		errorOptions.set("header", "Error");
		errorOptions.set("theme", "error");
		errorOptions.set("glue", "before");
		feedback.setErrorMessageOptions(errorOptions);

		final Options infoOptions = new Options();
		infoOptions.set("header", "Info");
		infoOptions.set("theme", "info");
		infoOptions.set("glue", "after");
		feedback.setInfoMessageOptions(infoOptions);

		final AjaxLink<Void> link = new AjaxLink<Void>("showButton")
		{

			@Override
			public void onClick(final AjaxRequestTarget target)
			{

				error("An ERROR message");

				info("An INFO message");

				target.add(feedback);
			}
		};

		add(link);
	}

}
