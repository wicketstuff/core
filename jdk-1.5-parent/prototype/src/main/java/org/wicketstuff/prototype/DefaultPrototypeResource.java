package org.wicketstuff.prototype;

import org.apache.wicket.Application;
import org.apache.wicket.request.resource.PackageResource;

/**
 * The default resource for the prototype script if no other was installed on
 * the {@link Application}.
 * 
 * @see PrototypeResourceReference#install(Application,
 *      org.apache.wicket.Resource)
 */
class DefaultPrototypeResource extends PackageResource {

	public DefaultPrototypeResource() {
		super(DefaultPrototypeResource.class, "prototype.js", null, null, null);
	}
}
