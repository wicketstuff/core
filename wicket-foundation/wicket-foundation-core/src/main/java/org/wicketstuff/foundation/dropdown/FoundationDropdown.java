package org.wicketstuff.foundation.dropdown;

import java.util.List;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.ListModel;
import org.wicketstuff.foundation.util.Attribute;
import org.wicketstuff.foundation.util.StringUtil;

/**
 * Dropdown buttons are elements that, when tapped, reveal additional content.
 * FoundationDropdown provides set of links for the user to click.
 * http://foundation.zurb.com/docs/components/dropdown_buttons.html
 * @author ilkka
 *
 */
public abstract class FoundationDropdown extends DropdownBase {

	private static final long serialVersionUID = 1L;

	/**
	 * Create FoundationDropdown.
	 * @param id - Wicket id.
	 * @param title - Title of the dropdown button.
	 * @param options - Options for the dropdown.
	 * @param linkTitles - Titles for the dropdown links.
	 */
	public FoundationDropdown(String id, String title, DropdownOptions options, List<String> linkTitles) {
		this(id, Model.of(title), Model.of(options), new ListModel<>(linkTitles));
	}
	
	/**
	 * Create FoundationDropdown.
	 * @param id - Wicket id.
	 * @param titleModel - Model for the dropdown button title.
	 * @param optionsModel - Model for the dropdown options.
	 * @param linkTitleModels - Model for the dropdown link titles.
	 */
	public FoundationDropdown(String id, IModel<String> titleModel, IModel<DropdownOptions> optionsModel, IModel<List<String>> linkTitleModels) {
		super(id, titleModel, optionsModel);
		FoundationDropdownContainer container = new FoundationDropdownContainer("container", linkTitleModels, optionsModel);
		add(container);
	}

	@Override
	protected String getContainerMarkupId() {
		return this.get("container").getMarkupId();
	}
	
	protected abstract AbstractLink createDropdownLink(int idx, String id);
	
	private class FoundationDropdownContainer extends WebMarkupContainer {

		private static final long serialVersionUID = 1L;
		
		private IModel<DropdownOptions> optionsModel;

		public FoundationDropdownContainer(String id, IModel<List<String>> linkTitleModels, IModel<DropdownOptions> optionsModel) {
			super(id);
			this.optionsModel = optionsModel;
			this.setOutputMarkupId(true);
			
			ListView<String> lv = new ListView<String>("item", linkTitleModels) {

				private static final long serialVersionUID = 1L;

				@Override
				protected void populateItem(ListItem<String> item) {
					AbstractLink link = FoundationDropdown.this.createDropdownLink(item.getIndex(), "link");
					item.add(link);
					link.add(new Label("body", item.getModel()));
				}
			};
			add(lv);
		}
		
		@Override
		protected void onComponentTag(ComponentTag tag) {
			Attribute.addAttribute(tag, "data-dropdown-content");
			Attribute.addClass(tag, "f-dropdown");
			Attribute.addAttribute(tag, "aria-hidden", true);
			Attribute.addAttribute(tag, "tabindex", -1);
			DropdownOptions options = optionsModel.getObject();
			if (options.getListStyle() != null) {
				Attribute.addClass(tag, StringUtil.EnumNameToCssClassName(options.getListStyle().name()));
			}
			super.onComponentTag(tag);
		}
		
		@Override
		protected void onDetach() {
			optionsModel.detach();
			super.onDetach();
		}
	}
}
