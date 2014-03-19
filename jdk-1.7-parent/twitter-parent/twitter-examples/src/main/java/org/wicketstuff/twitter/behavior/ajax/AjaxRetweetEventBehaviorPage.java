package org.wicketstuff.twitter.behavior.ajax;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.twitter.intents.RetweetLink;

/**
 * @author Till Freier
 * 
 */
public class AjaxRetweetEventBehaviorPage extends WebPage
{

	public AjaxRetweetEventBehaviorPage()
	{
		super();

		final IModel<String> responseModel = Model.of();

		final RetweetLink link = new RetweetLink("link");
		link.setTweetId("136565831483146241");

		add(link);

		final Label label = new Label("resonse", responseModel);
		label.setOutputMarkupId(true);
		add(label);

		add(new AjaxRetweetEventBehavior()
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
