package org.wicketstuff.twitter.behavior.ajax;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.twitter.intents.TweetLink;

/**
 * @author Till Freier
 * 
 */
public class AjaxTweetEventBehaviorPage extends WebPage
{

	public AjaxTweetEventBehaviorPage()
	{
		super();

		final IModel<String> responseModel = Model.of();

		final TweetLink tweetLink = new TweetLink("tweetLink");
		tweetLink.setInReplyTo("136565831483146241");


		add(tweetLink);

		final Label label = new Label("resonse", responseModel);
		label.setOutputMarkupId(true);
		add(label);

		add(new AjaxTweetEventBehavior()
		{
			@Override
			protected void onEvent(final AjaxRequestTarget target, final Event event)
			{
				responseModel.setObject("tweeted");
				target.add(label);
			}
		});
	}
}
