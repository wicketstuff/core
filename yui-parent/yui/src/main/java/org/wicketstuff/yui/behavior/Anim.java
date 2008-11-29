package org.wicketstuff.yui.behavior;


/**
 * Anim/Motion/Scoll/Motion
 * @author josh
 *
 */
public class Anim extends AnimEffect
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * the duration in seconds for the Animation
	 */
	private int duration;
	
	/**
	 * the easing to use
	 */
	private Easing easing;

	/**
	 * construct
	 */
	public Anim()
	{
		super();
	}

	/**
	 * construct
	 * 
	 * @param type
	 * @param attributes
	 */
	public Anim(Type type, Attributes attributes)
	{
		super(type, attributes);
	}

	/**
	 * construct
	 * @param type
	 */
	public Anim(Type type)
	{
		super(type);
	}

	/**
	 * 
	 * @param motion
	 * @param display
	 * @param duration
	 * @param function
	 */
	public Anim(Type type, Attributes attributes, int duration, Easing easing)
	{
		this(type, attributes);
		this.duration = duration;
		this.easing = easing;
	}
	
	/**
	 * TODO : clean up
	 */
	@Override
	public String getOpts()
	{
		return  duration + "," + easing.function();
	}

}
