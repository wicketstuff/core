package org.wicketstuff.foundation.foundationpanel;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.wicketstuff.foundation.border.FoundationBaseBorder;

/**
 * A panel is a simple, helpful Foundation component that enables you to outline sections of your page easily.
 * http://foundation.zurb.com/docs/components/panels.html
 * @author ilkka
 *
 */
public class FoundationPanelBorder extends FoundationBaseBorder {

	private static final long serialVersionUID = 1L;
	
	private IModel<PanelType> typeModel;

	/**
	 * Create FoundationPanelBorder.
	 * @param id - Wicket id.
	 * @param typeModel - Model for the panel type.
	 */
	public FoundationPanelBorder(String id, IModel<PanelType> typeModel) {
		super(id);
		this.typeModel = typeModel;
		initComponents();
	}

	/**
	 * Create FoundationPanel.
	 * @param id - Wicket id.
	 * @param model - Model for the panel.
	 * @param typeModel - Model for the panel type.
	 */
	public FoundationPanelBorder(String id, IModel<?> model, IModel<PanelType> typeModel) {
		super(id, model);
		this.typeModel = typeModel;
		initComponents();
	}
	
	private void initComponents() {
		WebMarkupContainer wrapper = new WebMarkupContainer("wrapper");
		PanelType type = typeModel.getObject();
		if (type.equals(PanelType.NORMAL)) {
			wrapper.add(new AttributeModifier("class", "panel"));
		} else if (type.equals(PanelType.CALLOUT)) {
			wrapper.add(new AttributeModifier("class", "panel callout radius"));
		}
		this.addToBorder(wrapper);
	}
	
	@Override
	protected void onDetach() {
		this.typeModel.detach();
		super.onDetach();
	}
}
