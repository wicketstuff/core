package org.wicketstuff.prototype;

import org.apache.wicket.Application;
import org.apache.wicket.request.resource.AbstractResource;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.PackageResourceReference;

/**
 * A reference to the "prototype.js" script.<br>
 * Uses a default resource if no other was specified via
 * {@link #install(Application, Resource)}.
 */
public final class PrototypeResourceReference extends
		PackageResourceReference {

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

	
	// mocleiri: not sure how to convert this so I didn't.  There are no examples to check with.
	
//	/**
//	 * Use a {@link DefaultPrototypeResource} if no resource was installed on
//	 * the {@link Application}.
//	 * 
//	 * @see #install(Application, Resource)
//	 */
//	@Override
//	protected Resource newResource() {
//		return new DefaultPrototypeResource();
//	}

	/**
	 * Install the given resource to be used for the prototypes script. <br>
	 * This method has to be called before any component was rendered, e.g. in
	 * {@link Application#init()}.
	 * 
	 * @param application
	 *            the application to install the resource on
	 * @param resource
	 *            the prototypes script resource
	 */
	public static void install(final Application application,
			final AbstractResource resource) {
		application.getSharedResources().add(PrototypeResourceReference.class,
				NAME, null, null, null, resource);
	}
}
