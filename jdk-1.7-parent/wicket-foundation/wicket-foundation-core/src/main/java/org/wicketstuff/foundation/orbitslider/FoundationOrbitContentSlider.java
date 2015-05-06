package org.wicketstuff.foundation.orbitslider;

import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.repeater.AbstractRepeater;
import org.apache.wicket.model.IModel;

public class FoundationOrbitContentSlider extends OrbitSliderBase {

	private static final long serialVersionUID = 1L;

	public FoundationOrbitContentSlider(String id, IModel<List<OrbitSliderContent>> itemsModel) {
		super(id, itemsModel);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected AbstractRepeater createRepeater(String id) {
		return new ListView<OrbitSliderContent>(id, (IModel<List<OrbitSliderContent>>)getDefaultModel()) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<OrbitSliderContent> item) {
				item.add(new AttributeModifier("data-orbit-slide", item.getModelObject().getName()));
				item.add(new Label("heading", item.getModelObject().getHeading()));
				item.add(new Label("subheading", item.getModelObject().getSubheading()));
			}
		};
	}
}
