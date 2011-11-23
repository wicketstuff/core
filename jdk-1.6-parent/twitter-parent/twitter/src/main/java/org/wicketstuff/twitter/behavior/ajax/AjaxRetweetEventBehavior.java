package org.wicketstuff.twitter.behavior.ajax;

/**
 * https://dev.twitter.com/docs/intents/events#retweet <br>
 * Supported by: Web Intents <br>
 * This event will populate the original Tweet that was retweeted's source_tweet_id in the
 * {@link Event} Object.
 * 
 * @see Event#getDataSourceTweetId()
 * 
 * @author Till Freier
 * 
 */
public abstract class AjaxRetweetEventBehavior extends AbstractAjaxTwitterEventBehavior
{

	public AjaxRetweetEventBehavior()
	{
		super("retweet");
	}

}
