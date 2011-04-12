package org.wicketstuff.minis.behavior.image;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;

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
	 * "width" standard img attribute name
	 */
	public static final String WIDTH = "width";

	/**
	 * "height" standard img attribute name
	 */
	public static final String HEIGHT = "height";

	/**
	 * Getter.
	 * 
	 * @return the width of the image
	 */
	public abstract String getWidth();

	/**
	 * Getter.
	 * 
	 * @return the height of the image
	 */
	public abstract String getHeight();

	@Override
	public void onComponentTag(Component component, ComponentTag tag)
	{
		super.onComponentTag(component, tag);

		tag.put(WIDTH, getWidth());
		tag.put(HEIGHT, getHeight());
	}
}
