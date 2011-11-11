package org.wicketstuff.facebook.behaviors;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.request.IRequestParameters;

/**
 * https://developers.facebook.com/docs/reference/javascript/FB.Event.subscribe/
 * 
 * @author Till Freier
 * 
 */
public abstract class AbstractAuthEventBehavior extends AbstractSubscribeBehavior
{
	private static final String ACCESS_TOKEN = "authResponse.accessToken";
	private static final String EXPIRES_IN = "authResponse.expiresIn";
	private static final String SIGNED_REQUEST = "authResponse.signedRequest";
	private static final String STATUS = "status";
	private static final String USER_ID = "authResponse.userID";

	/**
	 * 
	 * @param event
	 */
	protected AbstractAuthEventBehavior(final String event)
	{
		super(event, STATUS, USER_ID, SIGNED_REQUEST, EXPIRES_IN, ACCESS_TOKEN);
	}

	@Override
	protected void onEvent(final AjaxRequestTarget target, final IRequestParameters parameters,
		final String response)
	{
		final String status = parameters.getParameterValue(STATUS).toOptionalString();
		final String userId = parameters.getParameterValue(USER_ID).toOptionalString();
		final String signedRequest = parameters.getParameterValue(SIGNED_REQUEST)
			.toOptionalString();
		final String expiresIn = parameters.getParameterValue(EXPIRES_IN).toOptionalString();
		final String accessToken = parameters.getParameterValue(ACCESS_TOKEN).toOptionalString();

		onSessionEvent(target, status, userId, signedRequest, expiresIn, accessToken);
	}

	/**
	 * 
	 * @param status
	 *            Current status of the session
	 * @param userId
	 *            String representing the current user's ID
	 * @param signedRequest
	 *            String with the current signedRequest
	 * @param expiresIn
	 *            UNIX time when the session expires
	 * @param accessToken
	 *            Access token of the user
	 */
	protected abstract void onSessionEvent(AjaxRequestTarget target, String status, String userId,
		String signedRequest, String expiresIn, String accessToken);

}
