package org.wicketstuff.yui.markup.html.menu2.contextMenu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.yui.markup.html.contributor.YuiHeaderContributor;

public class YuiContextMenuBehavior extends AbstractDefaultAjaxBehavior {

	private List<YuiContextMenu> menus = new ArrayList<YuiContextMenu>();
	private YuiContextMenu defaultMenu;

	public YuiContextMenuBehavior(List<YuiContextMenu> menus) {
		menus.addAll(menus);
		this.defaultMenu = menus.get(0);
	}

	public YuiContextMenuBehavior(YuiContextMenu... menus) {

		for (int i = 0; i < menus.length; i++) {
			this.menus.add(menus[i]);
		}
		this.defaultMenu = this.menus.get( 0 );;
	}
	
	public void applyAttributes( Component comp, String menuId, IModel targetId ) {
		YuiContextMenu menu = getMenuById( menuId );
		assert( menu != null);
		applyAttributes( comp, menu, targetId );
	}
	
	public void applyAttributes( Component comp, YuiContextMenu menu, IModel targetId ) {
		comp.add(new AttributeModifier("targetId", true, targetId));
		comp.add(new AttributeModifier("contextMenuId", true, new Model( menu.getMenuId())));
	}

	protected void respond(AjaxRequestTarget target) {
		String action = RequestCycle.get().getRequest().getParameter("action");
		String targetId = RequestCycle.get().getRequest().getParameter(
				"targetId");
		String menuId = RequestCycle.get().getRequest().getParameter("contextMenuId");

		YuiContextMenu menu = getMenuById(menuId);
		menu = (menu == null) ? defaultMenu : menu;
		MenuItem item = menu.getMenuItem(action);
		if (item != null) {
			item.onClick(target, targetId);
		} else {
			System.out.println("Invalid action: " + action);
		}
	}

	private YuiContextMenu getMenuById(String id) {
		YuiContextMenu ret = null;

		Iterator<YuiContextMenu> iter = menus.iterator();
		while (ret == null && iter.hasNext()) {
			YuiContextMenu m = iter.next();
			if (m.getMenuId().equals(id)) {
				ret = m;
			}
		}

		return ret;
	}

	protected String getIconUrl(ResourceReference icon) {
		return getComponent().urlFor(icon).toString();
	}

	public CharSequence getCallBackScriptForMenu(String action) {
		return generateCallbackScript("wicketAjaxGet('" + getCallbackUrl()
				+ "&action=" + action + "&targetId=' + targetId + '&contextMenuId=' + contextMenuId");
	}

	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		YuiHeaderContributor.forModule("menu", null, false, "2.5.2")
				.renderHead(response);

		response.renderJavascriptReference(new ResourceReference(
				YuiContextMenuBehavior.class, "contextMenu.js"));

		StringBuffer buf = new StringBuffer();
		

		
		buf.append( getMenuCreationScript() );
		response.renderOnDomReadyJavascript(buf.toString() );
	}
	
	public String getMenuCreationScript() {
		StringBuffer buf = new StringBuffer();
		
		for (YuiContextMenu menu : menus) {
			List<MenuItem> items = menu.getAllMenuItems();

			for (MenuItem mi : items) {

				buf.append("function ").append(mi.getPathToRoot()).append(
						"() {\n");
				buf.append(getCallBackScriptForMenu(mi.getMenuId()));
				buf.append("\n}\n");
			}

		}
		StringBuffer menuDefn = new StringBuffer();
		menuDefn.append("var oContextMenuItems = {");

		Iterator<YuiContextMenu> menuIter = menus.iterator();
		while (menuIter.hasNext()) {
			YuiContextMenu menu = menuIter.next();
			if (defaultMenu == null) {
				defaultMenu = menu;
			}

			menuDefn.append("\"").append(menu.getMenuId()).append("\": [")
					.append(menu.getItemData(this));
			menuDefn.append("]");
			if (menuIter.hasNext()) {
				menuDefn.append(",\n");
			}
		}
		menuDefn.append("};\n");

		buf.append(menuDefn.toString());

		String menuName = getComponent().getMarkupId() + "ContextMenu";

		buf.append("\nvar ").append(menuName).append(
				" = new YAHOO.widget.ContextMenu(\"").append(menuName).append(
				"\",\n").append("{ trigger: document.getElementById(\"").append(
				getComponent().getMarkupId()).append("\"), lazyload: true }")
				.append(");\n");

		buf.append(menuName).append(".menus = ").append("oContextMenuItems;\n");

		buf.append(menuName).append(".render( document.getElementById(\"").append(
				getComponent().getMarkupId()).append("\"));\n");

		buf.append(menuName).append(
				".beforeShowEvent.subscribe(onContextMenuBeforeShow);\n");


		
		return buf.toString();

	}

}
