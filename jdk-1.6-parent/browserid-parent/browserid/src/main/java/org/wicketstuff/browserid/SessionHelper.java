package org.wicketstuff.browserid;

import org.apache.wicket.MetaDataKey;
import org.apache.wicket.Session;
import org.apache.wicket.util.lang.Args;

/**
 * Utility class that stores the {@link BrowserId} (authentication data) in the current web session.
 */
public class SessionHelper
{

	private static final MetaDataKey<BrowserId> KEY = new MetaDataKey<BrowserId>()
	{

		private static final long serialVersionUID = 1L;
	};

	/**
	 * @param session
	 *            the current web session
	 * @return the authentication data. May be {@code null}.
	 */
	public static BrowserId getBrowserId(final Session session)
	{

		Args.notNull(session, "session");

		BrowserId browserId = session.getMetaData(KEY);

		return browserId;
	}

	/**
	 * 
	 * @param session
	 *            the current web session
	 * @return {@code true} if there is authentication data. {@code false} - otherwise.
	 */
	public static boolean isLoggedIn(final Session session)
	{
		return getBrowserId(session) != null;
	}

	/**
	 * Stores the authentication data in the current web session
	 * 
	 * @param session
	 *            the current web session
	 * @param browserId
	 *            the authentication data
	 */
	public static void logIn(final Session session, final BrowserId browserId)
	{
		Args.notNull(session, "session");
		Args.notNull(browserId, "browserId");

		session.setMetaData(KEY, browserId);
		;
	}

	/**
	 * Removes the authentication data from the current web session
	 * 
	 * @param session
	 *            the current web session
	 */
	public static void logOut(final Session session)
	{
		Args.notNull(session, "session");

		session.setMetaData(KEY, null);
		;
	}
}
