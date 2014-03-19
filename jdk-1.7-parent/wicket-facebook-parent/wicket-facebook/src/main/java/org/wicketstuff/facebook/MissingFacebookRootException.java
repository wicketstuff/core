package org.wicketstuff.facebook;

import org.apache.wicket.WicketRuntimeException;

public class MissingFacebookRootException extends WicketRuntimeException
{
	/**
	 * 
	 */
	public MissingFacebookRootException()
	{
		super("You have to add the FacebookSdk component to your Page!");
	}

}
