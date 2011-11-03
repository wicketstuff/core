package org.wicketstuff.facebook.plugins;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.Model;
import org.wicketstuff.facebook.behaviors.FacebookSdkBehavior;

/**
 * 
 * @author Till Freier
 * 
 */
public class SendButtonPage extends WebPage
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public SendButtonPage()
	{
		add(new FacebookSdkBehavior());

		// fb doesn't like localhost
		add(new SendButton("sendButton", Model.of("http://wicketstuff.org")));
	}

}
