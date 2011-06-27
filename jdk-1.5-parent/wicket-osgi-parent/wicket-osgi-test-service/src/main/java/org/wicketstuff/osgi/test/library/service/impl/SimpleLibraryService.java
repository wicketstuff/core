/*
 * Copyright 2010 Harald Wellmann
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.wicketstuff.osgi.test.library.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.wicketstuff.osgi.test.library.model.Author;
import org.wicketstuff.osgi.test.library.model.Book;
import org.wicketstuff.osgi.test.library.service.LibraryService;


public class SimpleLibraryService implements LibraryService
{

	private static final long serialVersionUID = 1L;

	private int authorId;
	private int bookId;

	private Map<Integer, Author> authors = new TreeMap<Integer, Author>();
	private Map<Integer, Book> books = new TreeMap<Integer, Book>();

	public void fillLibrary()
	{
		if (getNumBooks() != 0)
			return;

		Author mann = new Author();
		mann.setFirstName("Thomas");
		mann.setLastName("Mann");

		Author steinbeck = new Author();
		steinbeck.setFirstName("John");
		steinbeck.setLastName("Steinbeck");

		Book buddenbrooks = new Book();
		buddenbrooks.setTitle("Buddenbrooks");
		buddenbrooks.setAuthor(mann);
		mann.getBooks().add(buddenbrooks);

		Book eden = new Book();
		eden.setTitle("East of Eden");
		eden.setAuthor(steinbeck);
		steinbeck.getBooks().add(eden);

		save(mann);
		save(steinbeck);
		save(buddenbrooks);
		save(eden);
	}

	private void save(Author author)
	{
		author.setId(++authorId);
		authors.put(author.getId(), author);
	}

	private void save(Book book)
	{
		book.setId(++bookId);
		books.put(book.getId(), book);
	}

	public List<Book> findBooksByAuthor(String lastName)
	{
		List<Book> results = new ArrayList<Book>();
		for (Book book : books.values())
		{
			if (lastName.equals(book.getAuthor().getLastName()))
			{
				results.add(book);
			}
		}
		return results;
	}

	public List<Book> findBooksByTitle(String title)
	{
		List<Book> results = new ArrayList<Book>();
		for (Book book : books.values())
		{
			if (title.equals(book.getTitle()))
			{
				results.add(book);
			}
		}
		return results;
	}

	public long getNumBooks()
	{
		return books.size();
	}

	public long getNumAuthors()
	{
		return authors.size();
	}

	public void createAuthor(Author author)
	{
		save(author);
	}
}
