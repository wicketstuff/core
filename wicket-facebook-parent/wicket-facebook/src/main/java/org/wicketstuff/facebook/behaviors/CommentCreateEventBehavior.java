package org.wicketstuff.facebook.behaviors;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.request.IRequestParameters;

/**
 * fired when the user adds a comment (fb:comments)
 * 
 * @author Till Freier
 * 
 */
public abstract class CommentCreateEventBehavior extends AbstractSubscribeBehavior
{
	private static final String COMMENT_ID = "commentID";
	private static final String HREF = "href";

	/**
	 * 
	 */
	protected CommentCreateEventBehavior()
	{
		super("comment.create");
	}

	/**
	 * 
	 * @param target
	 * @param href
	 *            Open Graph URL of the Comment Plugin
	 * @param commentId
	 *            The commentID of the new comment
	 */
	protected abstract void onCommentCreate(AjaxRequestTarget target, String href, String commentId);

	@Override
	protected void onEvent(final AjaxRequestTarget target, final IRequestParameters parameters,
		final String response)
	{
		final String href = parameters.getParameterValue(HREF).toOptionalString();
		final String commentId = parameters.getParameterValue(COMMENT_ID).toOptionalString();

		onCommentCreate(target, href, commentId);
	}

}
