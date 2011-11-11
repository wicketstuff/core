package org.wicketstuff.facebook.behaviors;

/**
 * fired when the user logs in
 * 
 * @author Till Freier
 * 
 */
public abstract class AuthLoginEventBehavior extends AbstractAuthEventBehavior
{

	protected AuthLoginEventBehavior()
	{
		super("auth.login");
	}

}
