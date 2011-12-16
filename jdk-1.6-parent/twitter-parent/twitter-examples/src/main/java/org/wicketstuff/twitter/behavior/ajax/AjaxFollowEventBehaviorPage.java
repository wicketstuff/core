package org.wicketstuff.twitter.behavior.ajax;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.twitter.intents.FollowLink;

/**
 * @author Till Freier
 * 
 */
public class AjaxFollowEventBehaviorPage extends WebPage
{

	public AjaxFollowEventBehaviorPage()
	{
		super();

		final IModel<String> responseModel = Model.of();

		final FollowLink link = new FollowLink("link");
		link.setScreenName("tfreier");

		add(link);

		final Label label = new Label("resonse", responseModel);
		label.setOutputMarkupId(true);
		add(label);

		add(new AjaxFollowEventBehavior()
		{
			@Override
			protected void onEvent(final AjaxRequestTarget target, final Event event)
			{
				responseModel.setObject("followed");
				target.add(label);
			}
		});
	}
}
