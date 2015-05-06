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
public class FoundationTouchDetectionBehavior extends FoundationBaseBehavior {

	private static final long serialVersionUID = 1L;
	
	public enum TouchDetectionType { 
		SHOW_FOR_TOUCH,
		HIDE_FOR_TOUCH };
	
	private IModel<TouchDetectionType> typeModel;

	/**
	 * Create TouchDetectionBehavior.
	 * @param type - Detection type.
	 */
	public FoundationTouchDetectionBehavior(TouchDetectionType type) {
		this(Model.of(type));
	}
	
	/**
	 * Create TouchDetectionBehavior.
	 * @param typeModel - Model for detection type.
	 */
	public FoundationTouchDetectionBehavior(IModel<TouchDetectionType> typeModel) {
		this.typeModel = typeModel;
	}
	
	@Override
	public void onComponentTag(Component component, ComponentTag tag) {
		super.onComponentTag(component, tag);
		Attribute.addClass(tag, StringUtil.EnumNameToCssClassName(typeModel.getObject().name()));
	}

	@Override
	public void detach(Component component) {
		typeModel.detach();
		super.detach(component);
	}
}
