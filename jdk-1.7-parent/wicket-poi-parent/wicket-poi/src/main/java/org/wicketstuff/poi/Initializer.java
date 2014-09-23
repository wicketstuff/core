package org.wicketstuff.poi;

import org.apache.wicket.Application;
import org.apache.wicket.IInitializer;

/**
 * Initializer for resource models only.
 *
 * @author Jesse Long
 */
public class Initializer
	implements IInitializer
{

	/**
	 * Initializes the application. This implementation does nothing.
	 *
	 * @param application The {@link Application} to initialize.
	 */
	@Override
	public void init(Application application)
	{
	}

	/**
	 * Destroys the application. This implementation does nothing.
	 *
	 * @param application The {@link Application} to destroy.
	 */
	@Override
	public void destroy(Application application)
	{
	}
}