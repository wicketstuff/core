package org.wicketstuff.minis.behavior.image;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.image.Image;

/**
 * <p>
 * This behavior can be added to {@link Image} components to render width and height attributes in
 * the markup. The exact way to retrieve the dimension information is left for the subclasses.
 * <p>
 * 
 * <p>
 * Providing dimension information for <img /> elements makes page rendering faster.
 * </p>
 * 
 * @author akiraly
 */
public abstract class AbstractImageDimensionProvider extends Behavior
{
	private static final long serialVersionUID = 2659926396935426159L;

	/**
	 * "width" standard html img attribute name
	 */
	public static final String WIDTH = "width";

	/**
	 * "height" standard html img attribute name
	 */
	public static final String HEIGHT = "height";

	/**
	 * Gets the image dimension. By default it is called by
	 * {@link #onComponentTag(Component, ComponentTag)}.
	 * 
	 * @param component
	 *            "the component that renders this tag currently"
	 * @param tag
	 *            "the tag that is rendered"
	 * @return image dimension used for width and height, can be null
	 */
	protected abstract ImageDimension getImageDimension(Component component, ComponentTag tag);

	@Override
	public void onComponentTag(Component component, ComponentTag tag)
	{
		super.onComponentTag(component, tag);

		ImageDimension dimension = getImageDimension(component, tag);

		if (dimension == null)
			return;

		tag.put(WIDTH, dimension.getWidth());
		tag.put(HEIGHT, dimension.getHeight());
	}
}
