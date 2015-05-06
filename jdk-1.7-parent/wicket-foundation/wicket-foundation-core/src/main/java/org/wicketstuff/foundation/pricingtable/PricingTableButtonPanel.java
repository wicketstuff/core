package org.wicketstuff.foundation.pricingtable;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.wicketstuff.foundation.util.Attribute;

/**
 * 
 * Simple button panel for pricing table.
 *
 */
public abstract class PricingTableButtonPanel extends Panel {

	private static final long serialVersionUID = 1L;

	public PricingTableButtonPanel(String id, IModel<String> buttonTextModel) {
		super(id);

		AjaxLink<Void> btn = new AjaxLink<Void>("btn") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onComponentTag(ComponentTag tag) {
				Attribute.addClass(tag, "button");
				super.onComponentTag(tag);
			}
			
			@Override
			public void onClick(AjaxRequestTarget target) {
				PricingTableButtonPanel.this.onClick(target);
			}
		};
		add(btn);
		btn.add(new Label("btnText", buttonTextModel));
	}
	
	public abstract void onClick(AjaxRequestTarget target);
}
