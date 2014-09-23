package org.wicketstuff.yui.behavior.animation;

import java.io.Serializable;

import org.wicketstuff.yui.helper.Attributes;

/**
 * Effects class. this is the base class for
 * YAHOO.util.Anim/Motion/Scroll and 
 * YAHOO.widgets.Effects
 * @author josh
 *
 */
public class YuiAnimEffect implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @author josh
	 *
	 */
	public enum Type 
	{
		// these are the Dave glass effects - need to clean up...
		Shadow, Fade, Appear, Fold, UnFold,
		BlindDown, BlindUp, BlindRight, BlindLeft, TV,
		ShakeLR, ShakeTB, Drop, Pulse, Shrink, Grow,
		
		// the following are the basic types
		Anim, Motion, ColorAnim, Scroll;
		
		/**
		 * @return
		 * 		the Javascript Function 
		 */
		public String functionFor() 
		{
			switch(this)
			{
				case Anim :
				case Motion :
				case ColorAnim :
				case Scroll :
					return "YAHOO.util." + this;
			}
			return "YAHOO.widget.Effects." + this;
		}
		
		/**
		 * @return
		 * 		the OnComplete function
		 */
		public String onComplete()
		{
			switch(this)
			{
				case Anim :
				case Motion :
				case ColorAnim :
				case Scroll :
					return "onComplete";
			}
			return "onEffectComplete";
		}
	}
	
	/**
	 * the attributes of this Effect
	 */
	private Attributes attributes;
	
	/**
	 * 
	 */
	private Type type;
	
	/**
	 * the construct 
	 */
	public YuiAnimEffect()
	{
		super();
	}

	/**
	 * 
	 * @param type
	 * @param attributes
	 */
	public YuiAnimEffect(Type type, Attributes attributes)
	{
		this.type = type;
		this.attributes = attributes;
	}
	
	/**
	 * 
	 * @param type
	 */
	public YuiAnimEffect(Type type)
	{
		this(type, new Attributes());
	}
	
	/**
	 * return the Yahoo function for this Effect
	 * @return
	 */
	public String newEffectJS() 
	{
		return getType().functionFor();
	}
	
	/**
	 * return the Yahoo "onComplete" function
	 * @return
	 */
	public String onCompleteJS()
	{
		return getType().onComplete();
	}

	/**
	 * 
	 * @return
	 */
	public Attributes getAttributes()
	{
		return attributes;
	}

	/**
	 * 
	 * @param attributes
	 */
	public void setAttributes(Attributes attributes)
	{
		this.attributes = attributes;
	}

	public String getOpts()
	{
		return "{}";
	}

	public Type getType()
	{
		return type;
	}

	public void setType(Type type)
	{
		this.type = type;
	}
}
