package org.wicketstuff.facebook.behaviors;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.request.IRequestParameters;

/**
 * 
 * @author Till Freier
 * 
 */
public abstract class EdgeRemoveEventBehavior extends AbstractSubscribeBehavior
{

	protected EdgeRemoveEventBehavior()
	{
		super("edge.remove");
	}

	@Override
	protected void onEvent(final AjaxRequestTarget target, final IRequestParameters parameters,
		final String response)
	{
		onEdgeRemove(target, response);
	}

	protected abstract void onEdgeRemove(AjaxRequestTarget target, String url);

}
