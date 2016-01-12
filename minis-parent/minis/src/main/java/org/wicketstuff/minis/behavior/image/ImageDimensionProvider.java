package org.wicketstuff.minis.behavior.image;

import org.apache.wicket.Component;
import org.apache.wicket.markup.ComponentTag;

/**
 * Static implementation of {@link AbstractImageDimensionProvider}. Width and height information is
 * provided at object construction time.
 * 
 * @author akiraly
 */
public class ImageDimensionProvider extends AbstractImageDimensionProvider
{
	private static final long serialVersionUID = -5997818865070010585L;

	private final ImageDimension dimension;

	/**
	 * Constructor.
	 * 
	 * @param width
	 *            width of the image
	 * @param height
	 *            height of the image
	 */
	public ImageDimensionProvider(int width, int height)
	{
		this(new ImageDimension(Integer.toString(width), Integer.toString(height)));
	}

	/**
	 * Constructor.
	 * 
	 * @param dimension
	 *            dimension of the image, can be null
	 */
	public ImageDimensionProvider(ImageDimension dimension)
	{
		this.dimension = dimension;
	}

	@Override
	protected ImageDimension getImageDimension(Component component, ComponentTag tag)
	{
		return dimension;
	}
}
