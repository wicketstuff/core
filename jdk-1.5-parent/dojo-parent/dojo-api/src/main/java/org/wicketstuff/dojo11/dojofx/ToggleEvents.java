package org.wicketstuff.dojo11.dojofx;

import org.apache.wicket.IClusterable;

/**
 * @author Stefan Fussenegger
 */
public class ToggleEvents implements IClusterable {
	/**
	 * toggle on click
	 */
	public static final ToggleEvents CLICK = new ToggleEvents("onclick");
	
	/**
	 * toggle show on mouse over and hide on mouse out
	 */
	public static final ToggleEvents MOUSE_OVER = new ToggleEvents("onmouseover", "onmouseout");
	
	/**
	 * toggle show on mouse out and hide on mouse over
	 */
	public static final ToggleEvents MOUSE_OUT = new ToggleEvents("onmouseout", "onmouseover");
	
	/**
	 * toggle on double click
	 */
	public static final ToggleEvents DOUBLE_CLICK = new ToggleEvents("ondblclick");
	
	private final String _show, _hide;
	
	/**
	 * Construct.
	 * @param toggle JS event name
	 */
	public ToggleEvents(String toggle) {
		this(toggle, toggle);
	}
	
	/**
	 * Construct.
	 * @param show JS event name
	 * @param hide JS event name
	 */
	public ToggleEvents(String show, String hide) {
		_show = show;
		_hide = hide;
	}
	
	/**
	 * @return show event
	 */
	public String getShow() {
		return _show;
	}
	
	/**
	 * @return hide event
	 */
	public String getHide() {
		return _hide;
	}
	
	/**
	 * @return true if hide and show are equal
	 */
	public boolean isToggle() {
		return getShow().equalsIgnoreCase(getHide());
	}
}