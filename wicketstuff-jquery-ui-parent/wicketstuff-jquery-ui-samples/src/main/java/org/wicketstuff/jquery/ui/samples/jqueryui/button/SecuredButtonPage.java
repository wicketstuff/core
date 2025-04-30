/*
Licensed under the Apache License, Version 2.0 (the "License") http://www.apache.org/licenses/LICENSE-2.0
 */
package org.wicketstuff.jquery.ui.samples.jqueryui.button;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.wicketstuff.jquery.core.IJQuerySecurityProvider;
import org.wicketstuff.jquery.ui.form.button.SecuredAjaxButton;
import org.wicketstuff.jquery.ui.form.button.SecuredButton;
import org.wicketstuff.jquery.ui.panel.JQueryFeedbackPanel;
import org.wicketstuff.jquery.ui.samples.SampleRoles;
import org.wicketstuff.jquery.ui.samples.SampleSession;
import org.wicketstuff.jquery.ui.samples.panel.LoginPanel;

public class SecuredButtonPage extends AbstractButtonPage implements IJQuerySecurityProvider
{
	private static final long serialVersionUID = 1L;

	public SecuredButtonPage()
	{
		this.add(new LoginPanel("login"));

		final Form<Void> form = new Form<Void>("form");
		this.add(form);

		/* FeedbackPanel */
		form.add(new JQueryFeedbackPanel("feedback"));

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
			protected void onSubmit(AjaxRequestTarget target)
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

	private void info(Component component)
	{
		this.info(component.getMarkupId() + " has been clicked");
	}
}
