package com.googlecode.wicket.jquery.ui.samples.pages.kendo.button;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;

import com.googlecode.wicket.jquery.core.IJQuerySecurityProvider;
import com.googlecode.wicket.jquery.ui.samples.SampleRoles;
import com.googlecode.wicket.jquery.ui.samples.SampleSession;
import com.googlecode.wicket.jquery.ui.samples.panel.LoginPanel;
import com.googlecode.wicket.kendo.ui.form.button.SecuredAjaxButton;
import com.googlecode.wicket.kendo.ui.form.button.SecuredButton;
import com.googlecode.wicket.kendo.ui.panel.KendoFeedbackPanel;

public class SecuredButtonPage extends AbstractButtonPage implements IJQuerySecurityProvider
{
	private static final long serialVersionUID = 1L;

	public SecuredButtonPage()
	{
		this.add(new LoginPanel("login"));

		final Form<Void> form = new Form<Void>("form");
		this.add(form);

		/* FeedbackPanel */
		form.add(new KendoFeedbackPanel("feedback"));

		/* Button 1: The Session extends IJQuerySecurityProvider, no need to provide the IJQuerySecurityProvider parameter */
		form.add(new SecuredButton("submit", SampleRoles.DEMO_ROLE) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit()
			{
				SecuredButtonPage.this.info(this);
			}
		});

		/* Button 2: The Page (this) extends IJQuerySecurityProvider, we pass it to the constructor */
		form.add(new SecuredAjaxButton("button", this, SampleRoles.DEMO_ROLE) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				SecuredButtonPage.this.info(this);
				target.add(form);
			}
		});
	}

	/**
	 * @see IJQuerySecurityProvider#hasRole(String...)
	 */
	@Override
	public boolean hasRole(String... roles)
	{
		return SampleSession.get().hasRole(roles);
	}

	private final void info(Component component)
	{
		this.info(component.getMarkupId() + " has been clicked");
	}
}
