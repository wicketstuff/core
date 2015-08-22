package org.wicketstuff.foundation.label;

import java.io.Serializable;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.wicketstuff.foundation.util.Attribute;
import org.wicketstuff.foundation.util.StringUtil;

/**
 * Labels are useful inline styles that can be dropped into body copy to call out certain sections or to attach metadata.
 * http://foundation.zurb.com/docs/components/labels.html
 * @author ilkka
 *
 */
public class FoundationLabel extends Label {

	private static final long serialVersionUID = 1L;
	
	private LabelOptions options;

	/**
	 * Create FoundationLabel.
	 * @param id - Wicket id.
	 */
	public FoundationLabel(final String id) {
		this(id, new LabelOptions());
	}

	/**
	 * Create FoundationLabel.
	 * @param id - Wicket id.
	 * @param options - Options for the label.
	 */
	public FoundationLabel(final String id, LabelOptions options) {
		super(id);
		this.options = options;
	}
	
	/**
	 * Create FoundationLabel.
	 * @param id - Wicket id.
	 * @param label - Text for the label.
	 */
	public FoundationLabel(final String id, Serializable label) {
		this(id, label, new LabelOptions());
	}

	/**
	 * Create FoundationLabel.
	 * @param id - Wicket id.
	 * @param label - Text for the label.
	 * @param options - Options for the label.
	 */
	public FoundationLabel(final String id, Serializable label, LabelOptions options) {
		super(id, label);
		this.options = options;
	}
	
	/**
	 * Create FoundationLabel.
	 * @param id - Wicket id.
	 * @param model - Model for the label.
	 */
	public FoundationLabel(final String id, IModel<?> model) {
		this(id, model, new LabelOptions());
	}
	
	/**
	 * Create FoundationLabel.
	 * @param id - Wicket id.
	 * @param model - Model for the label.
	 * @param options - Options for the label.
	 */
	public FoundationLabel(final String id, IModel<?> model, LabelOptions options) {
		super(id, model);
		this.options = options;
	}
	
	@Override
	protected void onComponentTag(ComponentTag tag) {
		Attribute.addClass(tag, "label");
		if (options.getColor() != null) {
			Attribute.addClass(tag, StringUtil.EnumNameToCssClassName(options.getColor().name()));
		}
		if (options.getRadius() != null) {
			Attribute.addClass(tag, StringUtil.EnumNameToCssClassName(options.getRadius().name()));
		}
		super.onComponentTag(tag);
	}
}
