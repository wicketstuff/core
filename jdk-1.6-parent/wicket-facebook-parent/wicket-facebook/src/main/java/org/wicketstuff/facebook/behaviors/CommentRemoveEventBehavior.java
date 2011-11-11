package org.wicketstuff.facebook.behaviors;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.request.IRequestParameters;

/**
 * fired when the user removes a comment (fb:comments)
 * 
 * @author Till Freier
 * 
 */
public abstract class CommentRemoveEventBehavior extends AbstractSubscribeBehavior
{
	private static final String COMMENT_ID = "commentID";
	private static final String HREF = "href";

	protected CommentRemoveEventBehavior()
	{
		super("comment.remove");
	}

	/**
	 * 
	 * @param target
	 * @param href
	 *            Open Graph URL of the Comment Plugin
	 * @param commentId
	 *            The commentID of the deleted comment
	 */
	protected abstract void onCommentRemove(AjaxRequestTarget target, String href, String commentId);

	@Override
	protected void onEvent(final AjaxRequestTarget target, final IRequestParameters parameters,
		final String response)
	{
		final String href = parameters.getParameterValue(HREF).toOptionalString();
		final String commentId = parameters.getParameterValue(COMMENT_ID).toOptionalString();

		onCommentRemove(target, href, commentId);
	}

}
