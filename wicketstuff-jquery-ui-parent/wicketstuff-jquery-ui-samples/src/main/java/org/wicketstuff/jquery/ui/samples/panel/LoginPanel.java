/*
Licensed under the Apache License, Version 2.0 (the "License") http://www.apache.org/licenses/LICENSE-2.0
 */
package org.wicketstuff.jquery.ui.samples.panel;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.wicketstuff.jquery.ui.form.button.Button;
import org.wicketstuff.jquery.ui.samples.SampleSession;

public class LoginPanel extends Panel
{
	private static final long serialVersionUID = 1L;

	public LoginPanel(String id)
	{
		super(id);

		final Form<Void> form = new Form<Void>("sform");
		this.add(form);

		form.add(new Button("login") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit()
			{
				SampleSession.get().login();
			}
		});

		form.add(new Button("logout") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit()
			{
				SampleSession.get().logout();
			}
		});
	}
}
