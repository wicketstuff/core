package org.wicketstuff.openlayers.api.control;

import org.apache.wicket.ResourceReference;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.wicketstuff.openlayers.OpenLayersMap;

public class ZoomControl extends Panel {

	private static ResourceReference ZOOM_IN = new ResourceReference(
			ZoomControl.class, "zoom-minus-mini.png");
	private static ResourceReference ZOOM_OUT = new ResourceReference(
			ZoomControl.class, "zoom-plus-mini.png");

	public ZoomControl(String id, OpenLayersMap map) {
		this(id, map, ZOOM_IN, ZOOM_OUT);

	}

	public ZoomControl(String id, OpenLayersMap map,
			ResourceReference zoomInResource, ResourceReference zoomOutResource) {
		super(id);
		Image zoomIn = new Image("zoomIn", zoomInResource);
		zoomIn.add(new AttributeAppender("onClick", new Model(map
				.getJSinvokeNoLineEnd("zoomIn()")), ";"));
		add(zoomIn);

		Image zoomOut = new Image("zoomOut", zoomOutResource);
		zoomOut.add(new AttributeAppender("onClick", new Model(map
				.getJSinvokeNoLineEnd("zoomOut()")), ";"));
		add(zoomOut);

	}

}
