package org.wicketstuff.jquery.ui.samples.kendoui.button;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.wicketstuff.kendo.ui.form.button.AjaxButton;
import org.wicketstuff.kendo.ui.panel.KendoFeedbackPanel;

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
			protected void onSubmit(AjaxRequestTarget target)
			{
				AjaxButtonPage.this.info(this);
				target.add(feedback);
			}
		});

		form.add(new AjaxButton("button2") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target)
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
