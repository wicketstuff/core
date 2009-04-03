package org.wicketstuff.yui.behavior.animation;

import org.wicketstuff.yui.helper.Attributes;


/**
 * Anim/Motion/Scoll/Motion
 * @author josh
 *
 */
public class YuiAnim extends YuiAnimEffect
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
	private YuiEasing easing;

	/**
	 * construct
	 */
	public YuiAnim()
	{
		super();
	}

	/**
	 * construct
	 * 
	 * @param type
	 * @param attributes
	 */
	public YuiAnim(Type type, Attributes attributes)
	{
		super(type, attributes);
	}

	/**
	 * construct
	 * @param type
	 */
	public YuiAnim(Type type)
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
	public YuiAnim(Type type, Attributes attributes, int duration, YuiEasing easing)
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
		return  duration + "," + easing.constant();
	}

}
