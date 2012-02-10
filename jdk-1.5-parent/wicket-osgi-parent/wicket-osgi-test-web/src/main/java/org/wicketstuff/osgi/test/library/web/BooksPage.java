package org.wicketstuff.osgi.test.library.web;

import javax.inject.Inject;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.wicketstuff.osgi.test.library.service.LibraryService;

public class BooksPage extends WebPage
{
	private static final long serialVersionUID = 1L;

	@Inject
	private LibraryService libraryService;

	public BooksPage()
	{
		add(new Label("numBooks", Long.toString(libraryService.getNumBooks())));
		add(new Label("numAuthors", Long.toString(libraryService.getNumAuthors())));
	}
}
