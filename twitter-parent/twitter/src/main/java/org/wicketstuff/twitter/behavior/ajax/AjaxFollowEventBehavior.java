package org.wicketstuff.twitter.behavior.ajax;

/**
 * https://dev.twitter.com/docs/intents/events#follow
 * 
 * Supported by: Tweet Button, Follow Button, & Web Intents
 * 
 * This event will populate the followed user_id in the Event Object's data method.
 * 
 * @see Event#getDataUserId()
 * 
 * @author Till Freier
 * 
 */
public abstract class AjaxFollowEventBehavior extends AbstractAjaxTwitterEventBehavior
{

	public AjaxFollowEventBehavior()
	{
		super("follow");
	}

}
