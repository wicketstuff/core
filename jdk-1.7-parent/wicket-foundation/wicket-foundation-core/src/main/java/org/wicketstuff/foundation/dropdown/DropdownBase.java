package org.wicketstuff.foundation.dropdown;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.foundation.component.FoundationJsPanel;
import org.wicketstuff.foundation.util.Attribute;
import org.wicketstuff.foundation.util.StringUtil;

/**
 * Base class for the foundation dropdowns.
 * @author ilkka
 *
 */
abstract class DropdownBase extends FoundationJsPanel {

	private static final long serialVersionUID = 1L;
	
	private IModel<String> titleModel;
	private IModel<DropdownOptions> optionsModel;

	/**
	 * Create FoundationDropdownBase.
	 * @param id - Wicket id.
	 * @param title - Dropdown button title.
	 * @param options - Options for the dropdown.
	 */
	public DropdownBase(String id, String title, DropdownOptions options) {
		this(id, Model.of(title), Model.of(options));
	}
	
	/**
	 * Create FoundationDropdownBase.
	 * @param id - Wicket id.
	 * @param titleModel - Model for dropdown button title.
	 * @param optionsModel - Model for dropdown options.
	 */
	public DropdownBase(String id, IModel<String> titleModel, IModel<DropdownOptions> optionsModel) {
		super(id);
		this.titleModel = titleModel;
		this.optionsModel = optionsModel;
	}
	
	@Override
	protected void onInitialize() {
		String markupId = getContainerMarkupId();
		FoundationDropdownLink btn = new FoundationDropdownLink("btn", markupId, titleModel, optionsModel);
		add(btn);
		super.onInitialize();
	}
	
	protected abstract String getContainerMarkupId();
	
	@Override
	protected void onDetach() {
		titleModel.detach();
		optionsModel.detach();
		super.onDetach();
	}
	
	private static class FoundationDropdownLink extends WebMarkupContainer {

		private static final long serialVersionUID = 1L;
		
		private String containerId;
		private IModel<String> titleModel;
		private IModel<DropdownOptions> optionsModel;

		public FoundationDropdownLink(String id, String containerId, IModel<String> titleModel, IModel<DropdownOptions> optionsModel) {
			super(id);
			this.containerId = containerId;
			this.titleModel = titleModel;
			this.optionsModel = optionsModel;
		}
		
		@Override
		protected void onComponentTag(ComponentTag tag) {
			DropdownOptions options = optionsModel.getObject();
			DropdownType type = options.getType();
			if (type == null || type.equals(DropdownType.DROPDOWN) || type.equals(DropdownType.DROPDOWNLINK) || type.equals(DropdownType.DROPDOWNCONTENT)) {
				tag.setName("a");
			}
			Attribute.addAttribute(tag, "data-dropdown", containerId);
			Attribute.addAttribute(tag, "aria-controls", containerId);
			Attribute.addAttribute(tag, "aria-expanded", false);
			if (type != null && (type.equals(DropdownType.DROPDOWNLINK) || type.equals(DropdownType.DROPDOWNBUTTON))) {
				Attribute.addClass(tag, "button");
			}
			if (type != null && type.equals(DropdownType.DROPDOWNBUTTON)) {
				Attribute.addClass(tag, "dropdown");
			}
			if (options.getColor() != null) {
				Attribute.addClass(tag, StringUtil.EnumNameToCssClassName(options.getColor().name()));
			}
			if (options.getRadius() != null) {
				Attribute.addClass(tag, StringUtil.EnumNameToCssClassName(options.getRadius().name()));
			}
			if (options.getSize() != null) {
				Attribute.addClass(tag, StringUtil.EnumNameToCssClassName(options.getSize().name()));
			}
			if (options.getExpansion() != null) {
				Attribute.addClass(tag, StringUtil.EnumNameToCssClassName(options.getExpansion().name()));
			}
			if (options.getListAlignment() != null) {
				String partial = StringUtil.EnumNameToCssClassName(options.getListAlignment().name());
				Attribute.addDataOptions(tag, "align:" + partial);
			}
			if (options.getHover() != null) {
				Attribute.addDataOptions(tag, "is_hover:true");
			}
			super.onComponentTag(tag);
		}
		
		@Override
		public void onComponentTagBody(MarkupStream markupStream,
				ComponentTag openTag) {
			this.replaceComponentTagBody(markupStream, openTag, titleModel.getObject());
			super.onComponentTagBody(markupStream, openTag);
		}
		
		@Override
		protected void onDetach() {
			titleModel.detach();
			optionsModel.detach();
			super.onDetach();
		}
	}
}
