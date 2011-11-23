package org.wicketstuff.twitter.behavior.ajax;

/**
 * https://dev.twitter.com/docs/intents/events#tweet <br>
 * Supported by: Tweet Button & Follow Button <br>
 * Receive an event when the user has clicked the Tweet Button or Follow Button. <br>
 * The Event Object returned in your callback will respond to a region method returning a string
 * with four possible states:
 * 
 * <pre>
 * "tweet" — the click occurred on the Tweet Button itself, invoking an Intent (which will trigger an Intent Event Callback when complete).
 * "count" — the click occurred on the Tweet Count portion of the Tweet Button, resulting in navigation to a Twitter.com search experience.
 * "follow" — the click occurred on the Follow Button itself, which may lead to an immediate follow event if the user already had an active Twitter session, or otherwise the invocation of the Follow Web Intent which may in turn also trigger a follow event.
 * "screen_name" — the click occurred on the screen name portion of the Follow Button, resulting in the invoking of a User Intent pop-up. If the user chooses to follow from this pop-up, a follow event will be triggered.
 * </pre>
 * 
 * @see Event#getRegion()
 * 
 * @author Till Freier
 * 
 */
public abstract class AjaxClickEventBehavior extends AbstractAjaxTwitterEventBehavior
{

	public AjaxClickEventBehavior()
	{
		super("click");
	}
}
