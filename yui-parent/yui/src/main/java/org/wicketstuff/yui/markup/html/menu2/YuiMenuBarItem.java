package org.wicketstuff.yui.markup.html.menu2;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;

public class YuiMenuBarItem extends AbstractYuiMenuItem {

	private static final long serialVersionUID = 1L;
	private static final String ITEM_ID = "item";
	private static final String MENU_ITEM_ID = "menu";
	private static final String LINK_ID = "link";
	private static final String LINK_LABEL_ID = "linkLabel";

	private boolean firstOfType = false;
	private WebMarkupContainer itemContainer = null;

	YuiMenuBarItem(String label) {
		this(label, null);
	}

	YuiMenuBarItem(final IYuiMenuAction action) {
		super(MENU_ITEM_ID);
		AbstractLink link = null;

		if (action instanceof AbstractLink) {
			link = (AbstractLink) action;
			if (link.getId().equals(LINK_ID) == false) {
				throw new RuntimeException("Link's id needs to be 'link' ");
			}
		} else if (action instanceof IYuiMenuAjaxAction) {
			link = new AjaxFallbackLink(LINK_ID, action.getName()) {
				private static final long serialVersionUID = 1L;

				@Override
				public void onClick(AjaxRequestTarget target) {
					if(action instanceof IYuiMenuAjaxAction && target != null)
					{
						((IYuiMenuAjaxAction) action).onClick(target, LINK_ID);
					}
					else
					{
						action.onClick();
					}
				}
			};
		} else {
			link = new Link(LINK_ID, action.getName()) {

				private static final long serialVersionUID = 1L;

				@Override
				public void onClick() {
					action.onClick();
				}
			};
		}
		getItemContainer().add(link);
		link.add(new Label(LINK_LABEL_ID, action.getName())
				.setRenderBodyOnly(true));
		newSubMenu("emptyMenu").setVisible(false);
	}

	YuiMenuBarItem(final String label, AbstractLink link) {
		super(MENU_ITEM_ID);

		if (link == null) {
			link = new Link(LINK_ID) {
				private static final long serialVersionUID = 1L;

				@Override
				public void onClick() {
				}
			};
		}

		if (link.getId().equals(LINK_ID) == false) {
			throw new RuntimeException("Link's id needs to be 'link' ");
		}

		getItemContainer().add(link);
		link.add(new Label(LINK_LABEL_ID, new Model(label))
				.setRenderBodyOnly(true));
		newSubMenu("emptyMenu").setVisible(false);
	}

	@Override
	protected final WebMarkupContainer getItemContainer() {
		if (itemContainer == null) {
			itemContainer = new WebMarkupContainer(ITEM_ID);
			add(itemContainer);
		}
		return itemContainer;
	}

	@Override
	public String getMenuClass() {
		return "yuimenuitem";
	}

	void addFirstOfType() {
		if (firstOfType == false) {
			itemContainer.add(new AttributeAppender("class", true, new Model(
					"first-of-type"), " "));
			firstOfType = true;
		}
	}

}
