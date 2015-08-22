package org.wicketstuff.facebook.behaviors;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.request.IRequestParameters;

/**
 * https://developers.facebook.com/docs/reference/javascript/FB.Event.subscribe/
 * 
 * fired when user is prompted to log in or opt in to Platform after clicking a Like button
 * 
 * @author Till Freier
 * 
 */
public abstract class AuthPromptEventBehavior extends AbstractSubscribeBehavior
{

	protected AuthPromptEventBehavior()
	{
		super("auth.prompt");
	}

	@Override
	protected void onEvent(final AjaxRequestTarget target, final IRequestParameters parameters,
		final String response)
	{

		onPrompt(target, response);
	}

	/**
	 * 
	 * @param target
	 * @param url
	 *            the URL that initiated the prompt
	 */
	protected abstract void onPrompt(AjaxRequestTarget target, String url);

}
