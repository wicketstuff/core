package org.wicketstuff.jwicket;


import org.apache.wicket.request.resource.PackageResourceReference;

public interface IStyleResolver {

	public PackageResourceReference[] getCssResources();
}
