package org.wicketstuff.yui.markup.html.ellipse;

import java.io.Serializable;

/**
 * EllipseHelper will help generate the points needed.
 * 
 *  x = a cos theta
 *  y = b sin theta 
 * 
 * @author josh
 *
 */
public class EllipseHelper implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * width (major axis)
	 */
	private double width = 0;
	
	/**
	 * height (minor axis)
	 */
	private double height = 0;
	
	/**
	 * the number of parts to divide the ellipse
	 */
	private int segments = 1;

	/**
	 * in degrees of a segment
	 */
	private float theta; 
	
	/**
	 * Construct 
	 * @param width
	 * @param height
	 * @param segments
	 */
	public EllipseHelper(double width, double height, int segments)
	{
		this.width = width;
		this.height = height;
		this.segments = segments;
		this.theta = 360 / segments;
	}

	/**
	 * construct
	 */
	public EllipseHelper(int segments)
	{
		this.segments = segments;
		this.theta = 360 / segments;
	}
	
	/**
	 *  x = a cos theta
	 *  a is half width
	 *  
	 * @param segment
	 * @return
	 */
	public double getX(int segment)
	{
		return (getWidth() / 2) * Math.cos(getRadians(segment));
	}
	
	/**
	 *  y = b sin theta
	 *   
	 * @param segment
	 * @return
	 */
	public double getY(int segment)
	{
		return (getHeight() / 2) * Math.sin(getRadians(segment));
	}
	
	/**
	 * return the theta to use in radians, compenstating for the
	 * 90 deg to move the first element to the top
	 * 
	 * @param segment
	 * @return
	 */
	private double getRadians(int segment)
	{
		float t = (getTheta() * segment) - 90;
		return Math.toRadians(t);
	}

	/**
	 * the Left value in HTML position terms by add half-width because
	 * the centre of the ellipse is half-width from the 0,0 position in HTML
	 * 
	 * @param segment
	 * @return
	 */
	public int getLeft(int segment)
	{
		return (int)((getWidth()/2) + getX(segment));
	}
	
	/**
	 * the Left value in HTML position terms by add half-height because
	 * the centre of the ellipse is half-height from the 0,0 position in HTML
	 * 
	 * @param segment
	 * @return
	 */
	public int getTop(int segment)
	{
		return (int)((getHeight()/2) + getY(segment));
	}
	
	public double getWidth()
	{
		return width;
	}

	public void setWidth(double width)
	{
		this.width = width;
	}

	public double getHeight()
	{
		return height;
	}

	public void setHeight(double height)
	{
		this.height = height;
	}

	public int getSegments()
	{
		return segments;
	}

	public void setSegments(int segments)
	{
		this.segments = segments;
	}

	public float getTheta()
	{
		return theta;
	}

	public void setTheta(float theta)
	{
		this.theta = theta;
	}
}
