package org.wicketstuff.twitter;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.model.IModel;

/**
 * 
 * @author Till Freier
 * 
 */
public class FollowButton extends MarkupContainer
{
	private final boolean showFollowerCount = false;

	private final IModel<?> accountModel;

	public FollowButton(final String id, final IModel<?> accountModel)
	{
		super(id);
		this.accountModel = accountModel;

		// TODO complete
	}


}
