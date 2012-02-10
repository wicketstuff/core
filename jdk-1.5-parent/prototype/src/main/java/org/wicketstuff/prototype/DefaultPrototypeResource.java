package org.wicketstuff.prototype;

import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;
import org.wicketstuff.jslibraries.JSReference;
import org.wicketstuff.jslibraries.Library;
import org.wicketstuff.jslibraries.VersionDescriptor;

/**
 * The default resource for the prototype script.
 * 
 * @deprecated
 */
@Deprecated
class DefaultPrototypeResource implements IResource
{

	private static final long serialVersionUID = 1L;
	private IResource resource;

	public DefaultPrototypeResource()
	{
		ResourceReference reference = JSReference.getReference(VersionDescriptor.exactVersion(
			Library.PROTOTYPE, 1, 6, 0, 2));

		resource = reference.getResource();
	}

	public void respond(Attributes attributes)
	{
		resource.respond(attributes);
	}
}
