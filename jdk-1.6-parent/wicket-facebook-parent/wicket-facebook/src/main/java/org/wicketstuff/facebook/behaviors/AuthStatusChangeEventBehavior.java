package org.wicketstuff.facebook.behaviors;

/**
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
