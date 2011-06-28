package org.wicketstuff.openlayers.api.control;

import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.wicketstuff.openlayers.IOpenLayersMap;

public class ZoomControl extends Panel
{

	private static final long serialVersionUID = 1L;
	private static ResourceReference ZOOM_IN = new PackageResourceReference(ZoomControl.class,
		"zoom-minus-mini.png");
	private static ResourceReference ZOOM_OUT = new PackageResourceReference(ZoomControl.class,
		"zoom-plus-mini.png");

	public ZoomControl(String id, IOpenLayersMap map)
	{
		this(id, map, ZOOM_IN, ZOOM_OUT);

	}

	public ZoomControl(String id, IOpenLayersMap map, ResourceReference zoomInResource,
		ResourceReference zoomOutResource)
	{
		super(id);
		Image zoomIn = new Image("zoomIn", zoomInResource);
		zoomIn.add(new AttributeAppender("onClick", Model.of(map.getJSinvokeNoLineEnd("zoomIn()")),
			";"));
		add(zoomIn);

		Image zoomOut = new Image("zoomOut", zoomOutResource);
		zoomOut.add(new AttributeAppender("onClick",
			Model.of(map.getJSinvokeNoLineEnd("zoomOut()")), ";"));
		add(zoomOut);

	}

}
