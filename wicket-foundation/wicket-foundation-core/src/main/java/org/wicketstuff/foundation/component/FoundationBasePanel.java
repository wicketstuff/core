package org.wicketstuff.foundation.component;

import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.wicketstuff.foundation.Foundation;

/**
 * Base class for Foundation styled Panels.
 * @author ilkka
 *
 */
public abstract class FoundationBasePanel extends Panel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create FoundationBasePanel.
	 * @param id - Wicket id.
	 */
	public FoundationBasePanel(String id) {
		super(id);
	}
	
	/**
	 * Create FoundationBasePanel.
	 * @param id - Wicket id.
	 * @param model - Model for the panel.
	 */
	public FoundationBasePanel(String id, IModel<?> model) {
		super(id, model);
	}
	
	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		response.render(CssHeaderItem.forReference(Foundation.getFoundationCssReference()));
		response.render(CssHeaderItem.forReference(Foundation.getFoundationIconsCssReference()));
	}
}
