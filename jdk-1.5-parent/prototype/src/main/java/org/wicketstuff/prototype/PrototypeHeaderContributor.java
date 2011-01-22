package org.wicketstuff.prototype;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.IHeaderResponse;

/**
 * A contributor of a "prototype.js" script to a header of a page.
 */
public final class PrototypeHeaderContributor extends Behavior {
		

	/**
	 * 
	 */
	private static final long serialVersionUID = -4763885449836613344L;

	@Override
	public void renderHead(Component c, IHeaderResponse response) {

		response.renderJavaScriptReference(PrototypeResourceReference.INSTANCE);
	}
}
