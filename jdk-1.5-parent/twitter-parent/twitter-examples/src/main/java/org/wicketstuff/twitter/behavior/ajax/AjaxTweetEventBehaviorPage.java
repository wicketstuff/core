package org.wicketstuff.twitter.behavior.ajax;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

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

		final ExternalLink tweetLink = new ExternalLink("tweetLink",
			"https://twitter.com/intent/tweet?in_reply_to=136565831483146241");
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
