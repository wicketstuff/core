package org.wicketstuff.minis.behavior.image;

/**
 * Static implementation of {@link AbstractImageDimensionProvider}. Width and
 * height information is provided at object construction time.
 *
 * @author akiraly
 */
public class ImageDimensionProvider extends AbstractImageDimensionProvider {
	private static final long serialVersionUID = -5997818865070010585L;

	private final String width;

	private final String height;

	/**
	 * Constructor.
	 *
	 * @param width
	 *            width of the image
	 * @param height
	 *            height of the image
	 */
	public ImageDimensionProvider(String width, String height) {
		super();
		this.width = width;
		this.height = height;
	}

	@Override
	public final String getWidth() {
		return width;
	}

	@Override
	public final String getHeight() {
		return height;
	}
}
