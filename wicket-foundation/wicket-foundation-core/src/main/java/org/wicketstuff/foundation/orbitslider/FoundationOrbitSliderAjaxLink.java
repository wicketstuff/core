package org.wicketstuff.foundation.orbitslider;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.model.IModel;
import org.wicketstuff.foundation.util.Attribute;

public class FoundationOrbitSliderAjaxLink extends AjaxLink<Void> {

	private static final long serialVersionUID = 1L;
	
	private IModel<String> slideNameModel;

	public FoundationOrbitSliderAjaxLink(String id, IModel<String> slideNameModel) {
		super(id);
		this.slideNameModel = slideNameModel;
	}

	@Override
	public void onClick(AjaxRequestTarget target) {
		// NOP
	}
	
	@Override
	protected void onComponentTag(ComponentTag tag) {
		Attribute.addAttribute(tag, "data-orbit-link", slideNameModel.getObject());
		super.onComponentTag(tag);
	}
	
	@Override
	protected void onDetach() {
		slideNameModel.detach();
		super.onDetach();
	}
}
