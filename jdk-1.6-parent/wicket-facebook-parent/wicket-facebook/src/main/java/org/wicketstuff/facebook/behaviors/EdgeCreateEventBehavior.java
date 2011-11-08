package org.wicketstuff.facebook.behaviors;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.request.IRequestParameters;

/**
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

	@Override
	protected void onEvent(final AjaxRequestTarget target, final IRequestParameters parameters,
		final String response)
	{
		onEdgeCreate(target, response);
	}

	protected abstract void onEdgeCreate(AjaxRequestTarget target, String url);

}
