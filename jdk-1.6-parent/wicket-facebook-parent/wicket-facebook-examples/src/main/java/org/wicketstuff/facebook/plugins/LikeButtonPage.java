package org.wicketstuff.facebook.plugins;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.facebook.behaviors.FacebookSdkBehavior;
import org.wicketstuff.facebook.plugins.LikeButton.LikeButtonAction;
import org.wicketstuff.facebook.plugins.LikeButton.LikeButtonLayoutStyle;

/**
 * 
 * @author Till Freier
 * 
 */
public class LikeButtonPage extends WebPage
{

	/**
	 * 
	 */
	public LikeButtonPage()
	{

		add(new FacebookSdkBehavior());

		// URL is needed because Facebook doesn't like localhost
		final IModel<String> url = Model.of("https://github.com/tfreier");

		final LikeButton likeButton = new LikeButton("likeButton", url);
		likeButton.setLayoutStyle(LikeButtonLayoutStyle.BUTTON_COUNT);
		likeButton.setAction(LikeButtonAction.RECOMMEND);

		add(likeButton);
	}

}
