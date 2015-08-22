package org.wicketstuff.openlayers.api.control;

import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.wicketstuff.openlayers.IOpenLayersMap;

/**
 * External controls
 * 
 * @author Nino Martinez Wael (nino.martinez@jayway.dk)
 * 
 */

public class PanControl extends Panel
{

	private static final long serialVersionUID = 1L;
	private static ResourceReference PAN_UP = new PackageResourceReference(PanControl.class,
		"north-mini.png");
	private static ResourceReference PAN_LEFT = new PackageResourceReference(PanControl.class,
		"west-mini.png");
	private static ResourceReference PAN_RIGHT = new PackageResourceReference(PanControl.class,
		"east-mini.png");
	private static ResourceReference PAN_DOWN = new PackageResourceReference(PanControl.class,
		"south-mini.png");

	private static int panoffset = 10;

	/**
	 * default, panoffset = 10 px
	 * 
	 * @param id
	 * @param map
	 */
	public PanControl(String id, IOpenLayersMap map)
	{
		this(id, map, panoffset);

	}

	public PanControl(String id, IOpenLayersMap map, int panOffset)
	{
		this(id, map, panOffset, PAN_UP, PAN_LEFT, PAN_RIGHT, PAN_DOWN);

	}

	/**
	 * 
	 * @param id
	 * @param map
	 * @param panOffset
	 *            how far should you pan?
	 * @param panUpResource
	 *            your images!
	 * @param panLeftResource
	 * @param panRightResource
	 * @param panDownResource
	 */
	public PanControl(String id, IOpenLayersMap map, int panOffset,
		ResourceReference panUpResource, ResourceReference panLeftResource,
		ResourceReference panRightResource, ResourceReference panDownResource)
	{
		super(id);
		Image panUp = new Image("panUp", panUpResource);
		panUp.add(new AttributeAppender("onClick",
			Model.of(map.getJSinvokeNoLineEnd("panDirection(0,-" + panOffset + ")")), ";"));
		add(panUp);

		Image panLeft = new Image("panLeft", panLeftResource);
		panLeft.add(new AttributeAppender("onClick",
			Model.of(map.getJSinvokeNoLineEnd("panDirection(-" + panOffset + ",0)")), ";"));

		add(panLeft);

		Image panRight = new Image("panRight", panRightResource);
		panRight.add(new AttributeAppender("onClick",
			Model.of(map.getJSinvokeNoLineEnd("panDirection(" + panOffset + ",0)")), ";"));

		add(panRight);

		Image panDown = new Image("panDown", panDownResource);

		panDown.add(new AttributeAppender("onClick",
			Model.of(map.getJSinvokeNoLineEnd("panDirection(0," + panOffset + ")")), ";"));

		add(panDown);

	}
}
