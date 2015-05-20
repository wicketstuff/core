package com.googlecode.wicket.jquery.ui.samples.pages.kendo.menu;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.wicket.kendo.ui.form.NumberTextField;
import com.googlecode.wicket.kendo.ui.form.button.AjaxButton;
import com.googlecode.wicket.kendo.ui.widget.menu.item.MenuItemForm;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.kendo.ui.KendoIcon;
import com.googlecode.wicket.kendo.ui.panel.KendoFeedbackPanel;
import com.googlecode.wicket.kendo.ui.widget.menu.Menu;
import com.googlecode.wicket.kendo.ui.widget.menu.item.IMenuItem;
import com.googlecode.wicket.kendo.ui.widget.menu.item.MenuItem;
import org.apache.wicket.model.PropertyModel;


public class KendoMenuPage extends AbstractMenuPage
{
	private static final long serialVersionUID = 1L;

	static List<IMenuItem> newMenuItemList()
	{
		List<IMenuItem> list = new ArrayList<IMenuItem>();

		list.add(new MenuItem("Item with icon", KendoIcon.CLOCK));
		list.add(new MenuItem("Change the title") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target)
			{
				this.setTitle(Model.of("Title changed!"));
			}
		});
		list.add(new MenuItem("Another menu item"));
		list.add(new MenuItem("Menu item, with sub-menu", newSubMenuList()));
		list.add(new MenuItem("Desactivate me") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target)
			{
				this.setEnabled(false);
			}
		});
		list.add(new MoveToPositionMenuItem("A menu item with a Form"));

		return list;
	}

	static List<IMenuItem> newSubMenuList()
	{
		List<IMenuItem> list = new ArrayList<IMenuItem>();

		list.add(new MenuItem("Sub-menu #1"));
		list.add(new MenuItem("Sub-menu #2"));
		list.add(new MenuItem("Sub-menu #3"));

		return list;
	}


	public KendoMenuPage()
	{
		// FeedbackPanel //
		final KendoFeedbackPanel feedback = new KendoFeedbackPanel("feedback");
		this.add(feedback);

		// Menu //
		this.add(new Menu("menu", KendoMenuPage.newMenuItemList()) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target, IMenuItem item)
			{
				if (item instanceof KendoMenuPage.MoveToPositionMenuItem)
				{
					KendoMenuPage.MoveToPositionMenuItem moveToPositionMenuItem = (KendoMenuPage.MoveToPositionMenuItem) item;
					this.info("Set the position to: " + moveToPositionMenuItem.getPosition());
				}
				else
				{
					this.info("Clicked " + item.getTitle().getObject());
				}

				target.add(this);
				target.add(feedback);
			}

			@Override
			protected void addMenuItem(ListItem<IMenuItem> item, IMenuItem menuItem) {
				if (menuItem instanceof KendoMenuPage.MoveToPositionMenuItem) {
					item.add(new MoveToPositionPanel("item", (KendoMenuPage.MoveToPositionMenuItem) menuItem, KendoMenuPage.this));
					item.add(new WebMarkupContainer("menu"));
				} else {
					super.addMenuItem(item, menuItem);
				}
			}
		});
	}

	public static class MoveToPositionPanel extends Fragment
	{
		/**
		 * Constructor.
		 *
		 * @param id The component id
		 * @param menuItem
		 */
		public MoveToPositionPanel(String id, KendoMenuPage.MoveToPositionMenuItem menuItem, MarkupContainer markupProvider) {
			super(id, "moveToPosition", markupProvider);

			MenuItemForm<Void> form = new MenuItemForm<Void>("form");
			add(form);

			final NumberTextField<Integer> position = new NumberTextField<Integer>("position", new PropertyModel<Integer>(menuItem, "position"));
			AjaxButton applyBtn = new AjaxButton("moveToPositionBtn") {
				@Override
				protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
					super.updateAjaxAttributes(attributes);
					attributes.setAllowDefault(true);
					attributes.setEventPropagation(AjaxRequestAttributes.EventPropagation.BUBBLE);
				}
			};

			form.add(position, applyBtn);
		}
	}
	public static class MoveToPositionMenuItem extends MenuItem
	{
		private int position = 1;

		private MoveToPositionMenuItem(String title) {
			super(title);
		}

		public int getPosition()
		{
			return position;
		}

		public void setPosition(int position)
		{
			this.position = position;
		}
	}

}
