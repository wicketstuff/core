package org.wicketstuff.facebook.behaviors;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import org.wicketstuff.facebook.FacebookSdk;
import org.wicketstuff.facebook.plugins.ActivityFeed;
import org.wicketstuff.facebook.plugins.Comments;
import org.wicketstuff.facebook.plugins.Facepile;

/**
 * @author Till Freier
 * 
 */
public class RenderEventPage extends WebPage
{

	/**
	 * 
	 */
	public RenderEventPage()
	{
		add(new FacebookSdk("fb-root"));
		add(new Comments(
			"comments",
			Model.of("http://localhost:8080/wicket/bookmarkable/org.wicketstuff.facebook.plugins.CommentPage")));

		add(new ActivityFeed("feed", Model.of("localhost")));
		add(new Facepile("facepile", Model.of("http://localhost/")));

		final Model<String> responseModel = new Model<String>();
		final Label responseLabel = new Label("response", responseModel);
		responseLabel.setOutputMarkupId(true);
		add(responseLabel);

		add(new RenderEventBehavior()
		{
			@Override
			protected void onRender(final AjaxRequestTarget target)
			{
				responseModel.setObject("rendered!");

				target.add(responseLabel);
			}
		});
	}

}
