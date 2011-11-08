package org.wicketstuff.facebook.behaviors;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.request.IRequestParameters;

/**
 * https://developers.facebook.com/docs/reference/javascript/FB.Event.subscribe/
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
	 * @param status
	 */
	protected abstract void onPrompt(AjaxRequestTarget target, String status);

}
