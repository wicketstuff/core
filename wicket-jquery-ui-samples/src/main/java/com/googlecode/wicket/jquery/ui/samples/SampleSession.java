package com.googlecode.wicket.jquery.ui.samples;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;

import com.googlecode.wicket.jquery.ui.IJQuerySecurityProvider;

public class SampleSession extends WebSession implements IJQuerySecurityProvider
{
	private static final long serialVersionUID = 1L;

	public static SampleSession get()
	{
		return (SampleSession)Session.get();
	}

	private final List<String> roles;

	public SampleSession(Request request)
	{
		super(request);
		this.roles = new ArrayList<String>();
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
