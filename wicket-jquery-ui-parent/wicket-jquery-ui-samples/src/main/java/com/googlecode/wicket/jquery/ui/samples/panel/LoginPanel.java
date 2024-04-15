package com.googlecode.wicket.jquery.ui.samples.panel;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;

import com.googlecode.wicket.jquery.ui.form.button.Button;
import com.googlecode.wicket.jquery.ui.samples.SampleSession;

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
