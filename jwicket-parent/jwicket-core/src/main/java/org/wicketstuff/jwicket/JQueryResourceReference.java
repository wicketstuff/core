package org.wicketstuff.jwicket;


import org.apache.wicket.ResourceReference;
import org.apache.wicket.WicketRuntimeException;


/**	Base class for all Header contributing resource references.
 *	The resource references are divided into two main categories:
 *  <ol><li></li>Resources that must not be modified by the user
 *  			of this jQuery-Wicket library. This are e.g.
 *  			libraries that are patches/modified to work around
 *  			some speical problems. If this libraries would have
 *  			been replaced by the user, the functionality of of
 *  			this lirary will definitely be broken.
 *  	<li></li>Resources that maybe replaced by the user.
 *  </ol>
 *
 *  If you write your own module for this library you may need to use
 *  e.g. the newest ui-core.js version. Then you can provide your own
 *  version of the js library and it overrides the internal one because
 *  ui-core.js is marked as {@link JQueryResourceReferenceType#OVERRIDABLE}.
 *
 *  If you try to replace the core jQuery.js library itself, this may
 *  have no effect as the library may be marked as
 *  {@link JQueryResourceReferenceType#NOT_OVERRIDABLE}. This is because
 *  jQuery.js contains some special patches at the moment. This may
 *  change in future versions of this library if the jQuery developers fix
 *  some problems in the official release.
 *
 *  If you try to replace any library that is marked as
 *  {@link JQueryResourceReferenceType#NOT_OVERRIDABLE} with your own version
 *  this will lead to a {@link WicketRuntimeException}.
 */
public class JQueryResourceReference extends ResourceReference {

	private static final long serialVersionUID = 1L;


	public JQueryResourceReference(final Class<?> scope, final String name) {
		this(scope, name, JQueryResourceReferenceType.OVERRIDABLE);
	}

	public JQueryResourceReference(final Class<?> scope, final String name, final JQueryResourceReferenceType type) {
		super(scope, name);
		this.type = type;
	}

	private final JQueryResourceReferenceType type;

	public JQueryResourceReferenceType getType() {
		return this.type;
	}
	
	
	public boolean isOverridable() {
		return getType() == JQueryResourceReferenceType.OVERRIDABLE;
	}
}
