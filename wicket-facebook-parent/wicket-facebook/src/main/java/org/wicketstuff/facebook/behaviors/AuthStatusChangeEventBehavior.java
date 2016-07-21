package org.wicketstuff.facebook.behaviors;

/**
 * fired when the status changes
 * 
 * @author Till Freier
 * 
 */
public abstract class AuthStatusChangeEventBehavior extends AbstractAuthEventBehavior
{
	protected AuthStatusChangeEventBehavior()
	{
		super("auth.statusChange");
	}

}
