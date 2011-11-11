package org.wicketstuff.facebook.plugins;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.Model;
import org.wicketstuff.facebook.FacebookSdk;

/**
 * 
 * @author Till Freier
 * 
 */
public class LikeBoxPage extends WebPage
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public LikeBoxPage()
	{
		add(new FacebookSdk("fb-root"));

		add(new LikeBox("likebox",
			Model.of("https://www.facebook.com/apps/application.php?id=142662635778399")));
	}

}
