package org.wicketstuff.facebook.plugins;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.Model;
import org.wicketstuff.facebook.behaviors.FacebookSdkBehavior;

/**
 * 
 * @author Till Freier
 * 
 */
public class FacepilePage extends WebPage
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public FacepilePage()
	{
		add(new FacebookSdkBehavior());

		add(new Facepile("facepile", Model.of("http://wicketstuff.org")));
	}
}
