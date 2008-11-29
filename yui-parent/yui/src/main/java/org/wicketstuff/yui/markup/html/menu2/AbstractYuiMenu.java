package org.wicketstuff.yui.markup.html.menu2;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.util.string.Strings;
import org.wicketstuff.yui.YuiHeaderContributor;

public abstract class AbstractYuiMenu extends Panel {
	private WebMarkupContainer menucontainer;
	private String menuId;

	public AbstractYuiMenu(String wicketId, final String menuId) {
		super(wicketId);

		this.menuId = menuId;

		add(YuiHeaderContributor.forModule("menu", null, false, "2.5.2"));

		menucontainer = getMenuContainer();
		if (menucontainer != null) {
			add(menucontainer);
		}
	}

	protected WebMarkupContainer getMenuContainer() {
		if (menucontainer == null) {
			menucontainer = new WebMarkupContainer("menucontainer") {
				private static final long serialVersionUID = 1L;
				@Override
				protected void onComponentTag(ComponentTag tag) {
					super.onComponentTag(tag);

					if (!Strings.isEmpty(menuId)) {
						tag.put("id", menuId);
					}
					tag.put("class", getMenuClass());
				}
			};

		}
		return menucontainer;
	}

	protected String getMenuId() {
		return menuId;
	}

	protected abstract String getMenuClass();

	protected abstract String getMenuName();

}
