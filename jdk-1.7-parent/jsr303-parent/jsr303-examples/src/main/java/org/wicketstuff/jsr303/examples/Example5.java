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
import org.wicketstuff.jsr303.BeanValidator;

public class Example5 extends WebPage
{
	private static final long serialVersionUID = 1L;

	@FooConstraint
	static class Data implements Serializable, FieldBundle
	{
		private static final long serialVersionUID = 1L;

		public String getField1()
		{
			return field1;
		}

		public String getField2()
		{
			return field2;
		}

		String field1;
		String field2;
	}

	private final Data dummy = new Data();

	public Example5()
	{
		final Form<Data> form = new Form<Data>("form", new CompoundPropertyModel<Data>(dummy))
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit()
			{
				super.onSubmit();

				if (!new BeanValidator(this).isValid(dummy))
				{
					// execute...
				}
				else
				{
					// stay here...
				}
			}
		};

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

		form.add(new TextField<String>("field1"));
		form.add(new TextField<String>("field2"));
	}
}
