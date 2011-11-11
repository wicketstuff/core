package org.wicketstuff.facebook.behaviors;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.request.IRequestParameters;

/**
 * fired when the user likes something (fb:like)
 * 
 * @author Till Freier
 * 
 */
public abstract class EdgeCreateEventBehavior extends AbstractSubscribeBehavior
{

	protected EdgeCreateEventBehavior()
	{
		super("edge.create");
	}

	/**
	 * 
	 * @param target
	 * @param url
	 *            URL that was liked
	 */
	protected abstract void onEdgeCreate(AjaxRequestTarget target, String url);

	@Override
	protected void onEvent(final AjaxRequestTarget target, final IRequestParameters parameters,
		final String response)
	{
		onEdgeCreate(target, response);
	}

}
