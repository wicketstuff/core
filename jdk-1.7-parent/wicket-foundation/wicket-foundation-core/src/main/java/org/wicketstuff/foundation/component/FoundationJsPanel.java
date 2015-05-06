package org.wicketstuff.foundation.component;

import org.apache.wicket.Application;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.model.IModel;
import org.wicketstuff.foundation.Foundation;

/**
 * Base class Foundation styled Panels with Javascript functionality.
 * @author ilkka
 *
 */
public abstract class FoundationJsPanel extends FoundationBasePanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create FoundationJsPanel.
	 * @param id - Wicket id.
	 */
	public FoundationJsPanel(String id) {
		super(id);
	}
	
	/**
	 * Create FoundationJsPanel.
	 * @param id - Wicket id.
	 * @param model - Model for the panel.
	 */
	public FoundationJsPanel(String id, IModel<?> model) {
		super(id, model);
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		response.render(JavaScriptHeaderItem.forReference(Foundation.getModernizrJsReference()));
		response.render(JavaScriptHeaderItem.forReference(Application.get().getJavaScriptLibrarySettings().getJQueryReference()));
		response.render(JavaScriptHeaderItem.forReference(Foundation.getFastclickJsReference()));
		response.render(JavaScriptHeaderItem.forReference(Foundation.getFoundationJsReference()));
        response.render(OnDomReadyHeaderItem.forScript(Foundation.getFoundationInitScript()));
	}
}
