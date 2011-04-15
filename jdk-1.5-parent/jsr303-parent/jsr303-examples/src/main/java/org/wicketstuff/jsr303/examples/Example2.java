/**
 * 
 */
package org.wicketstuff.jsr303.examples;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.hibernate.validator.constraints.Email;
import org.wicketstuff.jsr303.PropertyValidation;

public class Example2 extends WebPage
{
	private static final long serialVersionUID = 1L;

	static class Data implements Serializable
	{
		private static final long serialVersionUID = 1L;
		@Email
		@NotNull
		String email = "";
	}

	private final Data dummy = new Data();

	public Example2()
	{
		final Form<Void> form = new Form<Void>("form");
		add(form);
		add(new FeedbackPanel("fb"));
		add(new WebMarkupContainer("message")
		{
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isVisible()
			{
				return form.isSubmitted() && !form.hasError();
			}
		});
		form.add(new PropertyValidation());
		form.add(new TextField<String>("email", new PropertyModel<String>(dummy, "email")));
	}
}
