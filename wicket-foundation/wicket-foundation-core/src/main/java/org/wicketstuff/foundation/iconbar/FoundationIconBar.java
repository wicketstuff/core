package org.wicketstuff.foundation.iconbar;

import java.util.List;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.ListModel;
import org.wicketstuff.foundation.component.FoundationBasePanel;
import org.wicketstuff.foundation.icon.FoundationIcon;
import org.wicketstuff.foundation.util.Attribute;
import org.wicketstuff.foundation.util.StringUtil;

/**
 * An Icon Bar provides a menu to quickly navigate an app.
 * http://foundation.zurb.com/docs/components/icon-bar.html
 * @author ilkka
 *
 */
public class FoundationIconBar extends FoundationBasePanel {

	private static final long serialVersionUID = 1L;
	
	private IModel<IconBarOptions> optionsModel;
	private IModel<List<IconBarItem>> itemsModel;

	/**
	 * Create FoundationIconBar.
	 * @param id - Wicket id.
	 * @param items - List of icon bar items.
	 */
	public FoundationIconBar(String id, List<IconBarItem> items) {
		this(id, new IconBarOptions(), items);
	}
	
	/**
	 * Create FoundationIconBar.
	 * @param id - Wicket id.
	 * @param options - Options for the icon bar.
	 * @param items - List of icon bar items.
	 */
	public FoundationIconBar(String id, IconBarOptions options, List<IconBarItem> items) {
		this(id, Model.of(options), new ListModel<>(items));
	}
	
	/**
	 * Create FoundationIconBar.
	 * @param id - Wicket id.
	 * @param optionsModel - Options for the icon bar.
	 * @param itemsModel - Model for the icon bar items.
	 */
	public FoundationIconBar(String id, IModel<IconBarOptions> optionsModel, IModel<List<IconBarItem>> itemsModel) {
		super(id);
		this.optionsModel = optionsModel;
		this.itemsModel = itemsModel;
		add(new ListView<IconBarItem>("item", itemsModel) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<IconBarItem> item) {
				if (item.getModelObject().getImageResourceReference() != null) {
					item.add(new Image("img", item.getModelObject().getImageResourceReference()));
					item.add(new WebMarkupContainer("icon").setVisible(false));
				} else {
					item.add(new WebMarkupContainer("img").setVisible(false));
					item.add(new FoundationIcon("icon", item.getModelObject().getIconType()));
				}
				WebMarkupContainer label = new WebMarkupContainer("label");
				item.add(label);
				label.add(new Label("text", item.getModelObject().getLabel()));
			}
		});
	}
	
	@Override
	protected void onComponentTag(ComponentTag tag) {
		Attribute.addClass(tag, "icon-bar");
		IconBarOptions options = optionsModel.getObject();
		if (options.getVerticalStyle() != null) {
			Attribute.addClass(tag, StringUtil.EnumNameToCssClassName(options.getVerticalStyle().name()));
		}
		if (options.isLabelRight()) {
			Attribute.addClass(tag, "label-right");
		}
		switch (itemsModel.getObject().size()) {
			case 1: Attribute.addClass(tag, "one-up"); break;
			case 2: Attribute.addClass(tag, "two-up"); break;
			case 3: Attribute.addClass(tag, "three-up"); break;
			case 4: Attribute.addClass(tag, "four-up"); break;
			case 5: Attribute.addClass(tag, "five-up"); break;
			case 6: Attribute.addClass(tag, "six-up"); break;
		}
		super.onComponentTag(tag);
	}
	
	@Override
	protected void onDetach() {
		optionsModel.detach();
		itemsModel.detach();
		super.onDetach();
	}
}
