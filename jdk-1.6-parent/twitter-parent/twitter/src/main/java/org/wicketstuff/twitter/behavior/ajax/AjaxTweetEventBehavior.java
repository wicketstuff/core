package org.wicketstuff.twitter.behavior.ajax;

/**
 * https://dev.twitter.com/docs/intents/events#tweet
 * 
 * Supported by: Tweet Button & Web Intents
 * 
 * This event will be triggered when the user publishes his tweet within the share box.
 * 
 * @author Till Freier
 * 
 */
public abstract class AjaxTweetEventBehavior extends AbstractAjaxTwitterEventBehavior
{

	public AjaxTweetEventBehavior()
	{
		super("tweet");
	}
}
