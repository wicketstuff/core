package org.wicketstuff.foundation.visibility;

import org.apache.wicket.Component;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.foundation.behavior.FoundationBaseBehavior;
import org.wicketstuff.foundation.util.Attribute;
import org.wicketstuff.foundation.util.StringUtil;

/**
 * Visibility classes let you show or hide elements based on screen size or device orientation.
 * http://foundation.zurb.com/docs/components/visibility.html
 * @author ilkka
 *
 */
public class FoundationVisibleForScreenSizeBehavior extends FoundationBaseBehavior {

	private static final long serialVersionUID = 1L;
	
	public enum VisibleForScreenSizeType { 
		VISIBLE_FOR_SMALL_ONLY,
		VISIBLE_FOR_MEDIUM_UP,
		VISIBLE_FOR_MEDIUM_ONLY,
		VISIBLE_FOR_LARGE_UP,
		VISIBLE_FOR_LARGE_ONLY,
		VISIBLE_FOR_XLARGE_UP,
		VISIBLE_FOR_XLARGE_ONLY,
		VISIBLE_FOR_XXLARGE_UP };
	
	private IModel<VisibleForScreenSizeType> showTypeModel;

	/**
	 * Create VisibleForScreenSizeBehavior.
	 * @param showType - Show type.
	 */
	public FoundationVisibleForScreenSizeBehavior(VisibleForScreenSizeType showType) {
		this(Model.of(showType));
	}
	
	/**
	 * Create VisibleForScreenSizeBehavior.
	 * @param showTypeModel - Model for show type.
	 */
	public FoundationVisibleForScreenSizeBehavior(IModel<VisibleForScreenSizeType> showTypeModel) {
		this.showTypeModel = showTypeModel;
	}
	
	@Override
	public void onComponentTag(Component component, ComponentTag tag) {
		super.onComponentTag(component, tag);
		Attribute.addClass(tag, StringUtil.EnumNameToCssClassName(showTypeModel.getObject().name()));
	}

	@Override
	public void detach(Component component) {
		showTypeModel.detach();
		super.detach(component);
	}
}
