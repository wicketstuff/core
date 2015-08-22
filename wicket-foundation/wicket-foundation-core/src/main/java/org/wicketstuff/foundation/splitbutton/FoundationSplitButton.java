package org.wicketstuff.foundation.splitbutton;

import java.util.List;

import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.ListModel;
import org.wicketstuff.foundation.component.FoundationJsPanel;
import org.wicketstuff.foundation.util.Attribute;
import org.wicketstuff.foundation.util.StringUtil;

public abstract class FoundationSplitButton extends FoundationJsPanel {

	private static final long serialVersionUID = 1L;

	public FoundationSplitButton(String id, String buttonTitle, List<String> linkTitles) {
		this(id, Model.of(buttonTitle), new ListModel<>(linkTitles));
	}

	public FoundationSplitButton(String id, String buttonTitle, List<String> linkTitles, SplitButtonOptions options) {
		this(id, Model.of(buttonTitle), new ListModel<>(linkTitles), Model.of(options));
	}
	
	public FoundationSplitButton(String id, IModel<String> buttonTitleModel, IModel<List<String>> linkTitleModels) {
		this(id, buttonTitleModel, linkTitleModels, Model.of(new SplitButtonOptions()));
	}
	
	public FoundationSplitButton(String id, IModel<String> buttonTitleModel, IModel<List<String>> linkTitleModels, IModel<SplitButtonOptions> optionsModel) {
		super(id);
		final DropdownContainer dropdownContainer = new DropdownContainer("dropdown", linkTitleModels);
		add(dropdownContainer);
		AbstractLink button = createButton("button");
		add(button);
		button.add(new AttributeAppender("class", "button").setSeparator(" "));
		button.add(new AttributeAppender("class", "split").setSeparator(" "));
		SplitButtonOptions options = optionsModel.getObject();
		if (options.getColor() != null) {
			button.add(new AttributeAppender("class", StringUtil.EnumNameToCssClassName(options.getColor().name())).setSeparator(" "));
		}
		if (options.getRadius() != null) {
			button.add(new AttributeAppender("class", StringUtil.EnumNameToCssClassName(options.getRadius().name())).setSeparator(" "));
		}
		if (options.getSize() != null) {
			button.add(new AttributeAppender("class", StringUtil.EnumNameToCssClassName(options.getSize().name())).setSeparator(" "));
		}
		button.add(new Label("buttonText", buttonTitleModel));
		button.add(new WebMarkupContainer("buttonData") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onComponentTag(ComponentTag tag) {
				Attribute.addAttribute(tag, "data-dropdown", dropdownContainer.getMarkupId());
				super.onComponentTag(tag);
			}
		});
	}
	
	public abstract AbstractLink createButton(String id);
	
	public abstract AbstractLink createDropdownLink(String id, int idx);
	
	private class DropdownContainer extends WebMarkupContainer {

		private static final long serialVersionUID = 1L;
		
		public DropdownContainer(String id, IModel<List<String>> linkTitleModels) {
			super(id);
			this.setOutputMarkupId(true);
			
			ListView<String> lv = new ListView<String>("dropdownItem", linkTitleModels) {

				private static final long serialVersionUID = 1L;

				@Override
				protected void populateItem(ListItem<String> item) {
					AbstractLink dropdownItemLink = FoundationSplitButton.this.createDropdownLink("dropdownItemLink", item.getIndex());
					item.add(dropdownItemLink);
					dropdownItemLink.add(new Label("dropdownItemText", item.getModel()));
				}
			};
			add(lv);
		}
		
		@Override
		protected void onComponentTag(ComponentTag tag) {
			Attribute.addAttribute(tag, "data-dropdown-content");
			Attribute.addClass(tag, "f-dropdown");
			super.onComponentTag(tag);
		}
	}
}
