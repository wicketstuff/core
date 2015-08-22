package org.wicketstuff.foundation.orbitslider;

import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.repeater.AbstractRepeater;
import org.apache.wicket.model.IModel;
import org.wicketstuff.foundation.util.Attribute;

public class FoundationOrbitSlider extends OrbitSliderBase {

	private static final long serialVersionUID = 1L;
	
	public FoundationOrbitSlider(String id, IModel<List<OrbitSliderItem>> itemsModel) {
		super(id, itemsModel);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected AbstractRepeater createRepeater(String id) {
		return new ListView<OrbitSliderItem>(id, (IModel<List<OrbitSliderItem>>)getDefaultModel()) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<OrbitSliderItem> item) {
				item.add(new AttributeModifier("data-orbit-slide", item.getModelObject().getName()));
				item.add(new Image("img", item.getModelObject().getResource()));
				CaptionContainer container = new CaptionContainer("captionContainer");
				item.add(container);
				container.add(new Label("caption", item.getModelObject().getCaption()));
			}
		};
	}
	
	private static class CaptionContainer extends WebMarkupContainer {

		private static final long serialVersionUID = 1L;

		public CaptionContainer(String id) {
			super(id);
		}
		
		@Override
		protected void onComponentTag(ComponentTag tag) {
			Attribute.addClass(tag, "orbit-caption");
			super.onComponentTag(tag);
		}
	}
}
