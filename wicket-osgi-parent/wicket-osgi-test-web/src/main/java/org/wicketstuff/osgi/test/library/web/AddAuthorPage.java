package org.wicketstuff.osgi.test.library.web;

import java.io.IOException;

import javax.inject.Inject;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.wicketstuff.osgi.test.library.model.Author;
import org.wicketstuff.osgi.test.library.service.LibraryService;

public class AddAuthorPage extends WebPage
{

	@Inject
	private LibraryService libraryService;

	public class AddAuthorForm extends Form<Author>
	{

		private static final long serialVersionUID = 1L;

		public AddAuthorForm(String id, IModel<Author> model)
		{
			super(id, model);

			add(new TextField<Author>("firstName"));
			add(new TextField<Author>("lastName"));
			add(new Button("submit"));
		}

		@Override
		protected void onSubmit()
		{
			Author author = getModelObject();
			author.setId(0);
			libraryService.createAuthor(author);
			setResponsePage(BooksPage.class);
		}

	}

	public AddAuthorPage()
	{
		Author author = new Author();

		add(new AddAuthorForm("addAuthor", new CompoundPropertyModel<Author>(author)));
	}

	private void writeObject(java.io.ObjectOutputStream out) throws IOException
	{
		out.defaultWriteObject();
	}

	private void readObject(java.io.ObjectInputStream in) throws IOException
	{
		try
		{
			in.defaultReadObject();
		}
		catch (ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
