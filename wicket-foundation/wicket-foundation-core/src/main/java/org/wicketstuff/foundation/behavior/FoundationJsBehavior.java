package org.wicketstuff.foundation.behavior;

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.wicketstuff.foundation.Foundation;

/**
 * Behavior that includes Foundation CSS and JS libraries.
 * @author ilkka
 *
 */
public class FoundationJsBehavior extends FoundationBaseBehavior {

	private static final long serialVersionUID = 1L;

	@Override
	public void renderHead(Component component, IHeaderResponse response) {
		super.renderHead(component, response);
		response.render(JavaScriptHeaderItem.forReference(Foundation.getModernizrJsReference()));
		response.render(JavaScriptHeaderItem.forReference(Application.get().getJavaScriptLibrarySettings().getJQueryReference()));
		response.render(JavaScriptHeaderItem.forReference(Foundation.getFastclickJsReference()));
		response.render(JavaScriptHeaderItem.forReference(Foundation.getFoundationJsReference()));
        response.render(OnDomReadyHeaderItem.forScript(Foundation.getFoundationInitScript()));
	}
}
