package org.wicketstuff.facebook.behaviors;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import org.wicketstuff.facebook.FacebookSdk;
import org.wicketstuff.facebook.plugins.Comments;

/**
 * 
 * @author Till Freier
 * 
 */
public class CommentEventPage extends WebPage
{

	/**
	 * 
	 */
	public CommentEventPage()
	{
		add(new FacebookSdk("fb-root"));
		add(new Comments(
			"comments",
			Model.of("http://localhost:8080/wicket/bookmarkable/org.wicketstuff.facebook.plugins.CommentPage")));

		final Model<String> responseModel = new Model<String>();
		final Label responseLabel = new Label("response", responseModel);
		responseLabel.setOutputMarkupId(true);
		add(responseLabel);

		add(new CommentCreateEventBehavior()
		{
			@Override
			protected void onCommentCreate(final AjaxRequestTarget target, final String href,
				final String commentId)
			{
				final StringBuilder sb = new StringBuilder();
				sb.append("comment created: ").append(commentId).append('\n');

				responseModel.setObject(sb.toString());
				target.add(responseLabel);
			}
		});
		add(new CommentRemoveEventBehavior()
		{
			@Override
			protected void onCommentRemove(final AjaxRequestTarget target, final String href,
				final String commentId)
			{
				final StringBuilder sb = new StringBuilder();
				sb.append("comment removed: ").append(commentId).append('\n');

				responseModel.setObject(sb.toString());
				target.add(responseLabel);
			}
		});
	}

}
