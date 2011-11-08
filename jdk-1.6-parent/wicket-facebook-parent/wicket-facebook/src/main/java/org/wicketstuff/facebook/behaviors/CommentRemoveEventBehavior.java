package org.wicketstuff.facebook.behaviors;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.request.IRequestParameters;

/**
 * 
 * @author Till Freier
 * 
 */
public abstract class CommentRemoveEventBehavior extends AbstractSubscribeBehavior
{
	private static final String HREF = "href";
	private static final String COMMENT_ID = "commentID";

	protected CommentRemoveEventBehavior()
	{
		super("comment.remove");
	}

	@Override
	protected void onEvent(final AjaxRequestTarget target, final IRequestParameters parameters,
		final String response)
	{
		final String href = parameters.getParameterValue(HREF).toOptionalString();
		final String commentId = parameters.getParameterValue(COMMENT_ID).toOptionalString();

		onCommentRemove(target, href, commentId);
	}

	protected abstract void onCommentRemove(AjaxRequestTarget target, String href, String commentId);

}
