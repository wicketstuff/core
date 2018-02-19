package com.googlecode.wicket.jquery.ui.samples.kendoui.menu;

import java.util.List;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.lang.Generics;

import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.kendo.ui.KendoIcon;
import com.googlecode.wicket.kendo.ui.form.NumberTextField;
import com.googlecode.wicket.kendo.ui.form.button.AjaxButton;
import com.googlecode.wicket.kendo.ui.panel.KendoFeedbackPanel;
import com.googlecode.wicket.kendo.ui.widget.menu.Menu;
import com.googlecode.wicket.kendo.ui.widget.menu.form.MenuItemAjaxButton;
import com.googlecode.wicket.kendo.ui.widget.menu.form.MenuItemForm;
import com.googlecode.wicket.kendo.ui.widget.menu.item.IMenuItem;
import com.googlecode.wicket.kendo.ui.widget.menu.item.MenuItem;

public class KendoMenuPage extends AbstractMenuPage
{
	private static final long serialVersionUID = 1L;

	static List<IMenuItem> newMenuItemList()
	{
		List<IMenuItem> list = Generics.newArrayList();

		list.add(new MenuItem("Item with icon", KendoIcon.CLOCK));
		list.add(new MenuItem("Change the title") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target)
			{
				this.setTitle(Model.of("Title changed!"));
			}
		});
		list.add(new MenuItem("With sub-menu", newSubMenuList()));
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
		List<IMenuItem> list = Generics.newArrayList();

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

			// methods //

			@Override
			protected void addMenuItem(ListItem<IMenuItem> item, IMenuItem menuItem)
			{
				if (menuItem instanceof MoveToPositionMenuItem)
				{
					item.add(new MoveToPositionPanel("item", (MoveToPositionMenuItem) menuItem, KendoMenuPage.this));
					item.add(new WebMarkupContainer("menu"));
				}
				else
				{
					super.addMenuItem(item, menuItem);
				}
			}

			// events //

			@Override
			public void onClick(AjaxRequestTarget target, IMenuItem item)
			{
				if (item instanceof MoveToPositionMenuItem)
				{
					MoveToPositionMenuItem moveToPositionMenuItem = (MoveToPositionMenuItem) item;
					this.info("Set the position to: " + moveToPositionMenuItem.getPosition());
				}
				else
				{
					this.info("Clicked " + item.getTitle().getObject());
				}

				target.add(this);
				target.add(feedback);
			}
		});
	}

	static class MoveToPositionPanel extends Fragment
	{
		private static final long serialVersionUID = 1L;

		/**
		 * Constructor.
		 *
		 * @param id The component id
		 * @param menuItem
		 */
		public MoveToPositionPanel(String id, MoveToPositionMenuItem menuItem, MarkupContainer provider)
		{
			super(id, "moveToPosition", provider);

			Form<Void> form = new MenuItemForm<Void>("form");
			this.add(form);

			Options options = new Options("format", Options.asString("n0")); // integer format, no floating points
			final NumberTextField<Integer> textField = new NumberTextField<Integer>("position", new PropertyModel<Integer>(menuItem, "position"), options);
			final AjaxButton button = new MenuItemAjaxButton("moveToPositionBtn");

			form.add(textField, button);
		}
	}

	static class MoveToPositionMenuItem extends MenuItem
	{
		private static final long serialVersionUID = 1L;

		private int position = 1;

		private MoveToPositionMenuItem(String title)
		{
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
