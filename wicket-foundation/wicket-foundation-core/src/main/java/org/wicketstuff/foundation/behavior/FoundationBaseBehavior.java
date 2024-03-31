package org.wicketstuff.foundation.behavior;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.wicketstuff.foundation.Foundation;

/**
 * Behavior that includes Foundation CSS.
 * @author ilkka
 *
 */
public abstract class FoundationBaseBehavior extends Behavior {

	private static final long serialVersionUID = 1L;

	@Override
	public void renderHead(Component component, IHeaderResponse response) {
		super.renderHead(component, response);
		response.render(CssHeaderItem.forReference(Foundation.getFoundationCssReference()));
	}
}
