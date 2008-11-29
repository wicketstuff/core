package org.wicketstuff.yui.behavior;

/**
 * 
 * @author josh
 *
 */
public enum Easing
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

	public String function()
	{
		return "YAHOO.util.Easing." + this;  
	}
}
