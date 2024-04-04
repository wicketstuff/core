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
public class FoundationShowByOrientationBehavior extends FoundationBaseBehavior {

	private static final long serialVersionUID = 1L;
	
	public enum ShowByOrientationType { 
		SHOW_FOR_LANDSCAPE,
		SHOW_FOR_PORTRAIT };
	
	private IModel<ShowByOrientationType> showTypeModel;

	/**
	 * Create ShowByOrientationBehavior.
	 * @param showType - Show type.
	 */
	public FoundationShowByOrientationBehavior(ShowByOrientationType showType) {
		this(Model.of(showType));
	}
	
	/**
	 * Create ShowByOrientationBehavior.
	 * @param showTypeModel - Model for show type.
	 */
	public FoundationShowByOrientationBehavior(IModel<ShowByOrientationType> showTypeModel) {
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
