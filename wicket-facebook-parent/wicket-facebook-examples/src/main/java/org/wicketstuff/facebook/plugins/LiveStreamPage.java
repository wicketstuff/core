package org.wicketstuff.facebook.plugins;

import org.apache.wicket.markup.html.WebPage;
import org.wicketstuff.facebook.FacebookSdk;

/**
 * 
 * @author Till Freier
 * 
 */
public class LiveStreamPage extends WebPage
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LiveStreamPage()
	{
		add(new FacebookSdk("fb-root", "142662635778399"));

		add(new LiveStream("stream", "142662635778399"));
	}


}
