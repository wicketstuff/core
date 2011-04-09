package org.wicketstuff.prototype;

import org.apache.wicket.Application;
import org.apache.wicket.request.resource.AbstractResource;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.jslibraries.JSLib;

/**
 * A reference to the "prototype.js" script.
 * 
 * @deprecated
 */
public final class PrototypeResourceReference extends ResourceReference {

	private static final Logger log = LoggerFactory
			.getLogger(PrototypeResourceReference.class);

	/**
	 * Singleton instance of this reference.
	 */
	public static final PrototypeResourceReference INSTANCE = new PrototypeResourceReference();

	/**
	 * The name of this resource reference.
	 */
	public static final String NAME = "prototype.js";

	private PrototypeResourceReference() {
		super(PrototypeResourceReference.class, NAME);
	}

	@Override
	public IResource getResource() {
		return new DefaultPrototypeResource();
	}

	/**
	 * Install the given resource to be used for the prototypes script. <br>
	 * Note: This method does nothing.
	 * 
	 * @deprecated use
	 *             {@link JSLib#setOverrideProviders(Application, org.wicketstuff.jslibraries.Provider...)}
	 */
	public static void install(final Application application,
			final AbstractResource resource) {
		log
				.warn("wicketstuff-prototype is deprecated: Change your dependency to wicketstuff-jslibraries if you want to provide your own prototype.js - see JSLib#setOverrideProviders()");
	}
}
