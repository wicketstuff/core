package org.wicketstuff.foundation.orbitslider;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.repeater.AbstractRepeater;
import org.apache.wicket.model.IModel;
import org.wicketstuff.foundation.component.FoundationJsPanel;
import org.wicketstuff.foundation.util.Attribute;

public abstract class OrbitSliderBase extends FoundationJsPanel {

	private static final long serialVersionUID = 1L;
	
	public OrbitSliderBase(String id, IModel<?> model) {
		super(id, model);
		OrbitSliderContainer container = new OrbitSliderContainer("container");
		add(container);
		AbstractRepeater repeater = createRepeater("item");
		container.add(repeater);
	}

	protected abstract AbstractRepeater createRepeater(String id);
	
	private static class OrbitSliderContainer extends WebMarkupContainer {

		private static final long serialVersionUID = 1L;

		public OrbitSliderContainer(String id) {
			super(id);
		}
		
		@Override
		protected void onComponentTag(ComponentTag tag) {
			Attribute.addAttribute(tag, "data-orbit");
			super.onComponentTag(tag);
		}
	}
}
