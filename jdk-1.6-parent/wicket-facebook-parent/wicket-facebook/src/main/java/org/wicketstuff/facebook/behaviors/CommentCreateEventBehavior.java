package org.wicketstuff.facebook.behaviors;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.request.IRequestParameters;

/**
 * 
 * @author Till Freier
 * 
 */
public abstract class CommentCreateEventBehavior extends AbstractSubscribeBehavior
{
	private static final String HREF = "href";
	private static final String COMMENT_ID = "commentID";

	protected CommentCreateEventBehavior()
	{
		super("comment.create");
	}

	@Override
	protected void onEvent(final AjaxRequestTarget target, final IRequestParameters parameters,
		final String response)
	{
		final String href = parameters.getParameterValue(HREF).toOptionalString();
		final String commentId = parameters.getParameterValue(COMMENT_ID).toOptionalString();

		onCommentCreate(target, href, commentId);
	}

	protected abstract void onCommentCreate(AjaxRequestTarget target, String href, String commentId);

}
