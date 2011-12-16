package org.wicketstuff.jsr303;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;

/**
 * Test page with a simple form.
 * 
 * @author akiraly
 */
public class FormTestPage extends WebPage
{
	private static final long serialVersionUID = 1L;

	public static class Data implements Serializable
	{
		private static final long serialVersionUID = 1L;

		@NotNull
		private String email;

		public String getEmail()
		{
			return email;
		}
	}

	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		Form<Data> form = new Form<Data>("form", new CompoundPropertyModel<Data>(new Data()));
		add(form);
		form.add(new PropertyValidation());
		form.add(new TextField<String>("email"));
	}
}
