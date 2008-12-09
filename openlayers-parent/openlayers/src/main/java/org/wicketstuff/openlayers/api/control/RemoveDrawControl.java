package org.wicketstuff.openlayers.api.control;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.model.Model;
import org.wicketstuff.openlayers.OpenLayersMap;

/**
 * Attaches itself to a element, and onclick removes the drawcontrol 
 * @author Nino Martinez Wael
 *
 */
public class RemoveDrawControl extends AbstractBehavior {
	
	private OpenLayersMap map;
	public RemoveDrawControl(OpenLayersMap map) {
		this.map=map;
	}

	@Override
	public void bind(Component component) {
		super.bind(component);
		component.add(new AttributeAppender("onclick",new Model<String>(map.getJSinvokeNoLineEnd("removeDrawFeature()")),";"));
	}
}
