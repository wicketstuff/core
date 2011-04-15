package org.wicketstuff.openlayers.api.control;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.model.Model;
import org.wicketstuff.openlayers.IOpenLayersMap;

/**
 * Attaches itself to a element, and onclick removes the drawcontrol
 * 
 * @author Nino Martinez Wael
 * 
 */
public class RemoveDrawControl extends Behavior
{

	private static final long serialVersionUID = 1L;
	private IOpenLayersMap map;

	public RemoveDrawControl(IOpenLayersMap map)
	{
		this.map = map;
	}

	@Override
	public void bind(Component component)
	{
		super.bind(component);
		component.add(new AttributeAppender("onclick",
			Model.of(map.getJSinvokeNoLineEnd("removeDrawFeature()")), ";"));
	}
}
