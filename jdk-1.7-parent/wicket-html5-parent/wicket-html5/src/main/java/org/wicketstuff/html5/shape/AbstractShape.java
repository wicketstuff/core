package org.wicketstuff.html5.shape;

/**
 * The abstract shape provides basic functionality to all shapes
 * 
 * @author Tobias Soloschenko
 *
 */
public abstract class AbstractShape implements Shape
{

	private String transitionTime;


	@Override
	public String getTransitionTime()
	{
		return this.transitionTime;
	}


	/**
	 * Sets the transition the shape is used on hover effects
	 * 
	 * @param transitionTime
	 *            the transition time of the shape. Example: 5s (5 seconds)
	 */
	@Override
	public Shape transitionTime(String transitionTime)
	{
		this.transitionTime = transitionTime;
		return this;
	}
}
