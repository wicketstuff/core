package org.wicketstuff.facebook.behaviors;

/**
 * fired when the authResponse changes
 * 
 * @author Till Freier
 * 
 */
public abstract class AuthResponseChangeEventBehavior extends AbstractAuthEventBehavior
{

	protected AuthResponseChangeEventBehavior()
	{
		super("auth.authResponseChange");
	}

}
