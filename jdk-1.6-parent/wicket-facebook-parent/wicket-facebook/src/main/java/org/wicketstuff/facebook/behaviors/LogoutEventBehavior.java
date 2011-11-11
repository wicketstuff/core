package org.wicketstuff.facebook.behaviors;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.request.IRequestParameters;

/**
 * fired when the user logs out
 * 
 * @author Till Freier
 * 
 */
public abstract class LogoutEventBehavior extends AbstractSubscribeBehavior
{
	private static final String STATUS = "status";

	protected LogoutEventBehavior()
	{
		super("auth.logout", STATUS);
	}

	@Override
	protected void onEvent(final AjaxRequestTarget target, final IRequestParameters parameters,
		final String response)
	{
		final String status = parameters.getParameterValue(STATUS).toOptionalString();

		onLogout(target, status);
	}

	/**
	 * 
	 * @param target
	 * @param status
	 */
	protected abstract void onLogout(AjaxRequestTarget target, String status);

}
