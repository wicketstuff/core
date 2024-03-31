package org.wicketstuff.minis.behavior.mootip;

import java.awt.Point;
import java.io.Serializable;

public class MootipSettings extends Object implements Serializable
{
	private static final long serialVersionUID = 1L;

	/**
	 * set to true to display the tooltip onclick; defaults to false;
	 */
	private boolean showOnClick = false;
	/**
	 * set to false to prevent the tooltip from displaying onmouseenter; defaults to true;
	 */
	private boolean showOnMouseEnter = true;

	/**
	 * the maximum number of characters to display in the title of the tip. defaults to 30.
	 */
	private int maxTitleChars = 30;

	/**
	 * optionally you can alter the default onShow behaviour with this option (like displaying a
	 * fade in effect);
	 */
	private String onShow = null;

	/**
	 * optionally you can alter the default onHide behaviour with this option (like displaying a
	 * fade out effect);
	 */
	private String onHide = null;

	/**
	 * - the delay the onShow method is called. (defaults to 100 ms)
	 */
	private int showDelay = 100;
	/**
	 * the delay the onHide method is called. (defaults to 100 ms)
	 */
	private int hideDelay = 100;
	/**
	 * the prefix for your tooltip classNames. defaults to 'tool'.
	 * 
	 * the whole tooltip will have as classname: tool-tip
	 * 
	 * the title will have as classname: tool-title
	 * 
	 * the text will have as classname: tool-text
	 * 
	 */
	private String className = "tool";
	/**
	 * - the distance of your tooltip from the mouse. an Object with x/y properties.
	 */
	private Point offsets = new Point(0, 0);
	/**
	 * - if set to true, the toolTip will not follow the mouse.
	 */
	private boolean fixed = false;

	/**
	 * text to display as a title while loading an AJAX tooltip.
	 */
	private String loadingText;

	/**
	 * text to display when there's a problem with the AJAX request.
	 */
	private String errTitle = "Error";

	/**
	 * text to display when there's a problem with the AJAX request.
	 */
	private String errText = "An error occured";

	/**
	 * - set to true when using the AJAX or EVAL methods to handle the request on every mouseover;
	 * set to false to cache the value of the first attempt; defaults to false;
	 */
	private boolean evalAlways = false;

	public String getClassName()
	{
		return className;
	}

	public String getErrText()
	{
		return errText;
	}

	public String getErrTitle()
	{
		return errTitle;
	}

	public int getHideDelay()
	{
		return hideDelay;
	}

	public String getLoadingText()
	{
		return loadingText;
	}

	public int getMaxTitleChars()
	{
		return maxTitleChars;
	}

	public Point getOffsets()
	{
		return offsets;
	}

	public String getOnHide()
	{
		return onHide;
	}

	public String getOnShow()
	{
		return onShow;
	}

	public int getShowDelay()
	{
		return showDelay;
	}

	public boolean isEvalAlways()
	{
		return evalAlways;
	}

	public boolean isFixed()
	{
		return fixed;
	}

	public boolean isShowOnClick()
	{
		return showOnClick;
	}

	public boolean isShowOnMouseEnter()
	{
		return showOnMouseEnter;
	}

	public void setClassName(final String className)
	{
		this.className = className;
	}

	public void setErrText(final String errText)
	{
		this.errText = errText;
	}

	public void setErrTitle(final String errTitle)
	{
		this.errTitle = errTitle;
	}

	public void setEvalAlways(final boolean evalAlways)
	{
		this.evalAlways = evalAlways;
	}

	public void setFixed(final boolean fixed)
	{
		this.fixed = fixed;
	}

	public void setHideDelay(final int hideDelay)
	{
		this.hideDelay = hideDelay;
	}

	public void setLoadingText(final String loadingText)
	{
		this.loadingText = loadingText;
	}

	public void setMaxTitleChars(final int maxTitleChars)
	{
		this.maxTitleChars = maxTitleChars;
	}

	public void setOffsets(final Point offsets)
	{
		this.offsets = offsets;
	}

	public void setOnHide(final String onHide)
	{
		this.onHide = onHide;
	}

	public void setOnShow(final String onShow)
	{
		this.onShow = onShow;
	}

	public void setShowDelay(final int showDelay)
	{
		this.showDelay = showDelay;
	}

	public void setShowOnClick(final boolean showOnClick)
	{
		this.showOnClick = showOnClick;
	}

	public void setShowOnMouseEnter(final boolean showOnMouseEnter)
	{
		this.showOnMouseEnter = showOnMouseEnter;
	}
}
