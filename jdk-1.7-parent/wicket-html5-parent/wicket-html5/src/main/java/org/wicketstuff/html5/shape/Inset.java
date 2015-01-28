package org.wicketstuff.html5.shape;


/**
 * Creates a inset shape - it's like a rectangle
 * 
 * @author Tobias Soloschenko
 *
 */
public class Inset extends AbstractShape
{

	private String top;
	private String right;
	private String bottom;
	private String left;
	private String border_radius;

	/**
	 * Creates a inset shape
	 * 
	 * @param top
	 *            the top distance
	 * @param right
	 *            the right distance
	 * @param bottom
	 *            the bottom distance
	 * @param left
	 *            the left distance
	 * @param border_radius
	 *            the radius
	 */
	public Inset(String top, String right, String bottom, String left, String border_radius)
	{
		this.top = top;
		this.right = right;
		this.bottom = bottom;
		this.left = left;
		this.border_radius = border_radius;
	}

	public Inset(String top, String right, String bottom, String left)
	{
		this(top, right, bottom, left, null);
	}

	@Override
	public String getName()
	{
		return "inset";
	}

	@Override
	public String getValues()
	{
		return this.top + this.right + this.bottom + this.left +
			(this.border_radius != null ? this.border_radius : "");
	}

}
