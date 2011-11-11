package org.wicketstuff.facebook.behaviors;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import org.wicketstuff.facebook.FacebookSdk;
import org.wicketstuff.facebook.plugins.LikeButton;

/**
 * 
 * @author Till Freier
 * 
 */
public class EdgeEventPage extends WebPage
{
	/**
	 * 
	 */
	public EdgeEventPage()
	{
		add(new FacebookSdk("fb-root"));
		add(new LikeButton("likeButton", Model.of("http://localhost/")));

		final Model<String> responseModel = new Model<String>();
		final Label responseLabel = new Label("response", responseModel);
		responseLabel.setOutputMarkupId(true);
		add(responseLabel);

		add(new EdgeCreateEventBehavior()
		{

			@Override
			protected void onEdgeCreate(final AjaxRequestTarget target, final String url)
			{
				final StringBuilder sb = new StringBuilder();
				sb.append("edge created: ").append(url).append('\n');

				responseModel.setObject(sb.toString());

				target.add(responseLabel);
			}
		});

		add(new EdgeRemoveEventBehavior()
		{

			@Override
			protected void onEdgeRemove(final AjaxRequestTarget target, final String url)
			{
				final StringBuilder sb = new StringBuilder();
				sb.append("edge removed: ").append(url).append('\n');

				responseModel.setObject(sb.toString());

				target.add(responseLabel);
			}
		});
	}

}
