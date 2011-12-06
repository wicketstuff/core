package org.wicketstuff.yui.behavior.animation;

/**
 * 
 * @author josh
 *
 */
public enum YuiEasing
{
	backBoth, 
	backIn, 
	backOut, 
	bounceBoth, 
	bounceIn, 
	bounceOut,
	easeBoth, 
	easeBothStrong, 
	easeIn, 
	easeInStrong, 
	easeNone, 
	easeOut,
	easeOutStrong, 
	elasticBoth, 
	elasticIn, 
	elasticOut;

	public String constant()
	{
		return "YAHOO.util.Easing." + this;  
	}
}
