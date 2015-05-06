package org.wicketstuff.foundation.border;

import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.model.IModel;
import org.wicketstuff.foundation.Foundation;

/**
 * Base class for Borders that includes Foundation CSS.
 * @author ilkka
 *
 */
public abstract class FoundationBaseBorder extends Border {

	private static final long serialVersionUID = 1L;

	/**
	 * Create FoundationBaseBorder.
	 * @param id - Wicket id.
	 */
	public FoundationBaseBorder(String id) {
		super(id);
	}
	
	/**
	 * Create FoundationBaseBorder.
	 * @param id - Wicket id.
	 * @param model - Model for the component.
	 */
	public FoundationBaseBorder(String id, IModel<?> model) {
		super(id, model);
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		response.render(CssHeaderItem.forReference(Foundation.getFoundationCssReference()));
	}
}
