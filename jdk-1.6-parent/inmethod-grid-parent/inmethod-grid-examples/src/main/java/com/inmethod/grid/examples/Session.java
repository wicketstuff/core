package com.inmethod.grid.examples;

import java.util.Locale;

import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;

import com.inmethod.grid.examples.contact.Contact;
import com.inmethod.grid.examples.contact.ContactsDatabase;

/**
 * Wicket {@link org.apache.wicket.Session} subclass that keeps a {@link ContactsDatabase}. This is
 * necessary so that when user edits a {@link Contact} the change is isolated from other users.
 * 
 * @author Matej Knopp
 * 
 */
public class Session extends WebSession
{

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * 
	 * @param request
	 */
	public Session(Request request)
	{
		super(request);
	}

	/**
	 * Returns the {@link ContactsDatabase}.
	 * 
	 * @return {@link ContactsDatabase} instance.
	 */
	public ContactsDatabase getDatabase()
	{
		return database;
	}

	private ContactsDatabase database = new ContactsDatabase(330);

	@Override
	public Locale getLocale()
	{
		return Locale.ENGLISH;
	}

}
