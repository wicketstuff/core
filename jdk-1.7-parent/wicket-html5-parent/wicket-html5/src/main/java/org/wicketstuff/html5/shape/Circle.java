package org.wicketstuff.html5.shape;

/**
 * Creates a circle shape
 * 
 * @author Tobias Soloschenko
 *
 */
public class Circle extends AbstractShape
{

	private String radius;
	private String x;
	private String y;

	/**
	 * Creates a circle shape
	 * 
	 * @param radius
	 *            the radius of the circle
	 */
	public Circle(String radius)
	{
		this(radius, null, null);
	}

	/**
	 * Creates a circle shape
	 * 
	 * @param radius
	 *            the radius of the circle
	 * @param x
	 *            the x position of the circle
	 * @param y
	 *            the y position of the circle
	 */
	public Circle(String radius, String x, String y)
	{
		this.radius = radius;
		this.x = x;
		this.y = y;
	}

	@Override
	public String getName()
	{
		return "circle";
	}

	@Override
	public String getValues()
	{
		return this.radius +
			(this.x != null && this.y != null ? " at " + this.x + " " + this.y : "");
	}

}
