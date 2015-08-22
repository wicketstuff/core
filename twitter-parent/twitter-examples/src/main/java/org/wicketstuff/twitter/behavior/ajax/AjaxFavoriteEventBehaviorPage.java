package org.wicketstuff.twitter.behavior.ajax;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.twitter.intents.FavoriteLink;

/**
 * @author Till Freier
 * 
 */
public class AjaxFavoriteEventBehaviorPage extends WebPage
{

	public AjaxFavoriteEventBehaviorPage()
	{
		super();

		final IModel<String> responseModel = Model.of();

		final FavoriteLink link = new FavoriteLink("link");
		link.setTweetId("136565831483146241");

		add(link);

		final Label label = new Label("resonse", responseModel);
		label.setOutputMarkupId(true);
		add(label);

		add(new AjaxFavoriteEventBehavior()
		{
			@Override
			protected void onEvent(final AjaxRequestTarget target, final Event event)
			{
				responseModel.setObject("retweeted");
				target.add(label);
			}
		});
	}
}
