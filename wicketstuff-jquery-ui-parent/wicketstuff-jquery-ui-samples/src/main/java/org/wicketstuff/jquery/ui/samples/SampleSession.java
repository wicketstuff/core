/*
Licensed under the Apache License, Version 2.0 (the "License") http://www.apache.org/licenses/LICENSE-2.0
 */
package org.wicketstuff.jquery.ui.samples;

import java.util.List;

import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;
import org.apache.wicket.util.lang.Generics;
import org.wicketstuff.jquery.core.IJQuerySecurityProvider;

public class SampleSession extends WebSession implements IJQuerySecurityProvider
{
	private static final long serialVersionUID = 1L;

	/** Default Kendo theme; matches a {@code <theme>-main.css} bundled by the samples app. */
	public static final String DEFAULT_KENDO_THEME = "default";

	private final List<String> roles;

	private String kendoTheme = DEFAULT_KENDO_THEME;

	public SampleSession(Request request)
	{
		super(request);

		this.roles = Generics.newArrayList();
	}

	public static SampleSession get()
	{
		return (SampleSession) Session.get();
	}

	/**
	 * Gets the Kendo UI theme selected for this session (a {@code <theme>-main.css} basename, ie: {@code default}, {@code bootstrap}, {@code material},
	 * {@code meridian}).
	 *
	 * @return the theme name
	 */
	public String getKendoTheme()
	{
		return this.kendoTheme;
	}

	/**
	 * Sets the Kendo UI theme for this session
	 *
	 * @param kendoTheme the theme name
	 */
	public void setKendoTheme(String kendoTheme)
	{
		this.kendoTheme = kendoTheme;
	}

	public final void login()
	{
		if (!this.roles.contains(SampleRoles.DEMO_ROLE))
		{
			this.roles.add(SampleRoles.DEMO_ROLE);
		}
	}

	public final void logout()
	{
		this.roles.clear();
	}

	/**
	 * Check whether one of the supplied roles match a user role
	 *
	 * @see IJQuerySecurityProvider#hasRole(String...)
	 * @param roles the roles to be checked
	 * @return true or false
	 */
	@Override
	public final boolean hasRole(String... roles)
	{
		for (String role : roles)
		{
			if (this.hasRole(role))
			{
				return true;
			}
		}

		return false;
	}

	protected final boolean hasRole(String role)
	{
		return this.roles.contains(role);
	}
}
