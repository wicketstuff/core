package org.wicketstuff.twitter.behavior.ajax;

/**
 * https://dev.twitter.com/docs/intents/events#favorite <br>
 * Supported by: Web Intents <br>
 * This event will populate the favorited tweet_id in the Event Object's data method.
 * 
 * @see Event#getDataTweetId()
 * 
 * @author Till Freier
 * 
 */
public abstract class AjaxFavoriteEventBehavior extends AbstractAjaxTwitterEventBehavior
{

	public AjaxFavoriteEventBehavior()
	{
		super("favorite");
	}
}
