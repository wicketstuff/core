package org.wicketstuff.html5.shape;

/**
 * Creates an ellipse shape
 * 
 * @author Tobias Soloschenko
 *
 */
public class Ellipse extends AbstractShape
{

	private String xradius;
	private String yradius;
	private String x;
	private String y;


	/**
	 * Creates an ellipse shape
	 * 
	 * @param xradius
	 *            the radius of the x axis
	 * @param yradius
	 *            the radius of the y axis
	 */
	public Ellipse(String xradius, String yradius)
	{
		this(xradius, yradius, null, null);
		this.xradius = xradius;
		this.yradius = yradius;
	}

	/**
	 * Creates an ellipse shape
	 * 
	 * @param xradius
	 *            the radius of the x axis
	 * @param yradius
	 *            the radius of the y axis
	 * @param x
	 *            the x position
	 * @param y
	 *            the y position
	 */
	public Ellipse(String xradius, String yradius, String x, String y)
	{
		this.xradius = xradius;
		this.yradius = yradius;
		this.x = x;
		this.y = y;
	}

	@Override
	public String getName()
	{
		return "ellipse";
	}

	@Override
	public String getValues()
	{
		return this.xradius + " " + this.yradius +
			(this.x != null && this.y != null ? " at " + this.x + " " + this.y : "");
	}
}
