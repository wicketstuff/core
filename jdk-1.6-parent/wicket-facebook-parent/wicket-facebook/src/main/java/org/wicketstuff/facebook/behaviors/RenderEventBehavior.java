package org.wicketstuff.facebook.behaviors;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.request.IRequestParameters;

/**
 * fired when a call to FB.XFBML.parse() completes
 * 
 * @author Till Freier
 * 
 */
public abstract class RenderEventBehavior extends AbstractSubscribeBehavior
{
	/**
	 * 
	 */
	protected RenderEventBehavior()
	{
		super("xfbml.render");
	}

	@Override
	protected void onEvent(final AjaxRequestTarget target, final IRequestParameters parameters,
		final String response)
	{
		onRender(target);
	}

	/**
	 * 
	 * @param target
	 */
	protected abstract void onRender(AjaxRequestTarget target);

}
