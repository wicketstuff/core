package org.wicketstuff.prototype;

import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;

/**
 * A contributor of a "prototype.js" script to a header of a page.
 */
public final class PrototypeHeaderContributor extends AbstractBehavior
		implements IHeaderContributor {

	@Override
	public void renderHead(IHeaderResponse response) {

		response.renderJavascriptReference(PrototypeResourceReference.INSTANCE);
	}
}
