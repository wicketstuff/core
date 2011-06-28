/**
 * 
 */
package org.wicketstuff.jsr303.examples;

import java.io.Serializable;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Range;
import org.wicketstuff.jsr303.PropertyValidation;

public class Example3 extends WebPage
{
	private static final long serialVersionUID = 1L;

	static class Data implements Serializable
	{
		private static final long serialVersionUID = 1L;

		@Email
		String email;

		@Range(min = 18, max = 99)
		int age;
	}

	private final Data dummy = new Data();

	public Example3()
	{
		final Form<Data> form = new Form<Data>("form", new CompoundPropertyModel<Data>(dummy));
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
		form.add(new TextField<String>("email"));
		form.add(new TextField<Integer>("age"));
	}
}
