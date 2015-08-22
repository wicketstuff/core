package org.wicketstuff.foundation.dropdown;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.foundation.util.Attribute;

/**
 * Dropdown buttons are elements that, when tapped, reveal additional content.
 * FoundationContentDropdown opens additional HTML content when clicked.
 * http://foundation.zurb.com/docs/components/dropdown_buttons.html
 * @author ilkka
 *
 */
public class FoundationContentDropdown extends DropdownBase {

	private static final long serialVersionUID = 1L;

	/**
	 * Create FoundationContentDropdown.
	 * @param id - Wicket id.
	 * @param title - Title for the dropdown button.
	 * @param options - Options for the dropdown.
	 * @param content - Dropdown content HTML.
	 */
	public FoundationContentDropdown(String id, String title, DropdownOptions options, String content) {
		this(id, Model.of(title), Model.of(options), Model.of(content));
	}
	
	/**
	 * Create FoundationContentDropdown.
	 * @param id - Wicket id.
	 * @param titleModel - Model for the dropdown button title.
	 * @param optionsModel - Model for the dropdown options.
	 * @param contentModel - Model for the dropdown content HTML.
	 */
	public FoundationContentDropdown(String id, IModel<String> titleModel, IModel<DropdownOptions> optionsModel, IModel<String> contentModel) {
		super(id, titleModel, optionsModel);
		FoundationDropdownContent content = new FoundationDropdownContent("content", contentModel);
		add(content);
		DropdownOptions options = optionsModel.getObject().setType(DropdownType.DROPDOWNCONTENT);
		optionsModel.setObject(options);
	}

	@Override
	protected String getContainerMarkupId() {
		return this.get("content").getMarkupId();
	}
	
	private class FoundationDropdownContent extends WebMarkupContainer {

		private static final long serialVersionUID = 1L;
		
		private IModel<String> contentModel;
		
		public FoundationDropdownContent(String id, IModel<String> contentModel) {
			super(id);
			this.contentModel = contentModel;
			this.setOutputMarkupId(true);
		}
		
		@Override
		protected void onComponentTag(ComponentTag tag) {
			Attribute.addAttribute(tag, "data-dropdown-content");
			Attribute.addClass(tag, "f-dropdown");
			Attribute.addClass(tag, "content");
			Attribute.addAttribute(tag, "aria-hidden", true);
			Attribute.addAttribute(tag, "tabindex", -1);
			super.onComponentTag(tag);
		}
		
		@Override
		public void onComponentTagBody(MarkupStream markupStream,
				ComponentTag openTag) {
			this.replaceComponentTagBody(markupStream, openTag, contentModel.getObject());
			super.onComponentTagBody(markupStream, openTag);
		}
		
		@Override
		protected void onDetach() {
			contentModel.detach();
			super.onDetach();
		}
	}	
}
