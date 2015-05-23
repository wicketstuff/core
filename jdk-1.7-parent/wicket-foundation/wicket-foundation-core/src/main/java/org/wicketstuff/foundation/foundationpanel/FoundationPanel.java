package org.wicketstuff.foundation.foundationpanel;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.model.IModel;
import org.wicketstuff.foundation.component.FoundationBasePanel;
import org.wicketstuff.foundation.util.Attribute;

/**
 * A panel is a simple, helpful Foundation component that enables you to outline sections of your page easily.
 * http://foundation.zurb.com/docs/components/panels.html
 * @author ilkka
 *
 */
public abstract class FoundationPanel extends FoundationBasePanel {

	private static final long serialVersionUID = 1L;
	
	private IModel<PanelType> typeModel;

	/**
	 * Create FoundationPanel.
	 * @param id - Wicket id.
	 * @param typeModel - Model for the panel type.
	 */
	public FoundationPanel(String id, IModel<PanelType> typeModel) {
		super(id);
		this.typeModel = typeModel;
	}
	
	/**
	 * Create FoundationPanel.
	 * @param id - Wicket id.
	 * @param model - Model for the panel.
	 * @param typeModel - Model for the panel type.
	 */
	public FoundationPanel(final String id, final IModel<?> model, IModel<PanelType> typeModel) {
		super(id, model);
		this.typeModel = typeModel;
	}
	
	@Override
	protected void onComponentTag(ComponentTag tag) {
		PanelType type = typeModel.getObject();
		if (type.equals(PanelType.NORMAL)) {
			Attribute.addClass(tag, "panel");
		} else if (type.equals(PanelType.CALLOUT)) {
			Attribute.addClass(tag, "panel callout radius");
		}
		super.onComponentTag(tag);
	}
	
	@Override
	protected void onDetach() {
		this.typeModel.detach();
		super.onDetach();
	}
}
