package com.inmethod.icon;

import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.handler.resource.ResourceReferenceRequestHandler;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.request.resource.ResourceReference;

/**
 * Simple icon implementation that uses images in classpath or a {@link ResourceReference} as icon
 * source.
 * 
 * @author Matej Knopp
 */
public class PackageIcon implements Icon
{

	private static final long serialVersionUID = 1L;

	private final ResourceReference reference;

	/**
	 * Creates a new icon instance.
	 * 
	 * @param reference
	 *            resource reference that can be used to get the icon
	 */
	public PackageIcon(ResourceReference reference)
	{
		this.reference = reference;
	}

	/**
	 * Creates a new icon instance.
	 * 
	 * @param scope
	 *            Class relative to which the icon is placed in classpath
	 * @param name
	 *            Icon name relative to the <code>scope</code> class
	 */
	public PackageIcon(Class<?> scope, String name)
	{
		reference = new PackageResourceReference(scope, name);
	}

	/**
	 * {@inheritDoc}
	 */
	public int getHeight()
	{
		return -1;
	}

	/**
	 * {@inheritDoc}
	 */
	public int getWidth()
	{
		return -1;
	}

	/**
	 * {@inheritDoc}
	 */
	public CharSequence getUrl()
	{
		return RequestCycle.get().urlFor(new ResourceReferenceRequestHandler(reference));
	}

}
