package org.wicketstuff.foundation.buttongroup;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.wicketstuff.foundation.border.FoundationBaseBorder;

/**
 * ButtonBarBorder is handy for wrapping multiple button groups together.
 * @author ilkka
 *
 */
public class FoundationButtonBarBorder extends FoundationBaseBorder {

	private static final long serialVersionUID = 1L;

	/**
	 * Create FoundationButtonBarBorder.
	 * @param id - Wicket id.
	 */
	public FoundationButtonBarBorder(String id) {
		super(id);
		initComponents();
	}
	
	/**
	 * Create FoundationButtonBarBorder.
	 * @param id - Wicket id.
	 * @param model - Model for the border.
	 */
	public FoundationButtonBarBorder(String id, IModel<?> model) {
		super(id, model);
		initComponents();
	}
	
	private void initComponents() {
		WebMarkupContainer wrapper = new WebMarkupContainer("wrapper");
		wrapper.add(new AttributeModifier("class", "button-bar"));
		this.addToBorder(wrapper);
	}
}
