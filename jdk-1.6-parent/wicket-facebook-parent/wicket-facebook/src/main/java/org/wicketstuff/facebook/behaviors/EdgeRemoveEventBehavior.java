package org.wicketstuff.facebook.behaviors;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.request.IRequestParameters;

/**
 * fired when the user unlikes something (fb:like)
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

	/**
	 * 
	 * @param target
	 * @param url
	 *            URL that was unliked
	 */
	protected abstract void onEdgeRemove(AjaxRequestTarget target, String url);

	@Override
	protected void onEvent(final AjaxRequestTarget target, final IRequestParameters parameters,
		final String response)
	{
		onEdgeRemove(target, response);
	}

}
