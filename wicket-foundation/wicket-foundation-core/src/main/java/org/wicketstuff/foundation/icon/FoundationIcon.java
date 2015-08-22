package org.wicketstuff.foundation.icon;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.wicketstuff.foundation.util.Attribute;
import org.wicketstuff.foundation.util.StringUtil;

/**
 * Single icon from Foundation Icon Fonts 3.
 * http://zurb.com/playground/foundation-icon-fonts-3
 * @author ilkka
 *
 */
public class FoundationIcon extends WebMarkupContainer {

	private static final long serialVersionUID = 1L;
	
	private IconType iconType;

	private IconSize iconSize;

	/**
	 * Create FoundationIcon.
	 * @param id - Wicket id.
	 * @param iconType - Type of the icon.
	 */
	public FoundationIcon(String id, IconType iconType) {
		this(id, iconType, null);
	}

	/**
	 * Create FoundationIcon.
	 * @param id - Wicket id.
	 * @param iconType - Type of the icon.
	 * @param iconSize - Size of the icon.
	 */
	public FoundationIcon(String id, IconType iconType, IconSize iconSize) {
		super(id);
		this.iconType = iconType;
		this.iconSize = iconSize;
	}
	
	@Override
	protected void onComponentTag(ComponentTag tag) {
		final String prefix = "fi-";
		Attribute.addClass(tag, prefix + StringUtil.EnumNameToCssClassName(iconType.name()));
		if (iconSize != null) {
			Attribute.addClass(tag, StringUtil.EnumNameToCssClassName(iconSize.name()));
		}
		super.onComponentTag(tag);
	}
}
