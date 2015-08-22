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
public class FoundationHiddenByScreenSizeBehavior extends FoundationBaseBehavior {

	private static final long serialVersionUID = 1L;
	
	public enum HiddenByScreenSizeType { 
		HIDDEN_FOR_SMALL_ONLY,
		HIDDEN_FOR_MEDIUM_UP,
		HIDDEN_FOR_MEDIUM_ONLY,
		HIDDEN_FOR_LARGE_UP,
		HIDDEN_FOR_LARGE_ONLY,
		HIDDEN_FOR_XLARGE_UP,
		HIDDEN_FOR_XLARGE_ONLY,
		HIDDEN_FOR_XXLARGE_UP };
	
	private IModel<HiddenByScreenSizeType> hideTypeModel;

	/**
	 * Create HiddenByScreenSizeBehavior.
	 * @param hideType - Hide type.
	 */
	public FoundationHiddenByScreenSizeBehavior(HiddenByScreenSizeType hideType) {
		this(Model.of(hideType));
	}
	
	/**
	 * Create HiddenByScreenSizeBehavior.
	 * @param hideTypeModel - Model for hide type.
	 */
	public FoundationHiddenByScreenSizeBehavior(IModel<HiddenByScreenSizeType> hideTypeModel) {
		this.hideTypeModel = hideTypeModel;
	}
	
	@Override
	public void onComponentTag(Component component, ComponentTag tag) {
		super.onComponentTag(component, tag);
		Attribute.addClass(tag, StringUtil.EnumNameToCssClassName(hideTypeModel.getObject().name()));
	}

	@Override
	public void detach(Component component) {
		hideTypeModel.detach();
		super.detach(component);
	}
}
