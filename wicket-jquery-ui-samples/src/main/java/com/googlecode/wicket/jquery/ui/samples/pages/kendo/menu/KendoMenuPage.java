package com.googlecode.wicket.jquery.ui.samples.pages.kendo.menu;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.kendo.ui.KendoIcon;
import com.googlecode.wicket.kendo.ui.panel.KendoFeedbackPanel;
import com.googlecode.wicket.kendo.ui.widget.menu.Menu;
import com.googlecode.wicket.kendo.ui.widget.menu.item.IMenuItem;
import com.googlecode.wicket.kendo.ui.widget.menu.item.MenuItem;


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
				this.info("Clicked " + item.getTitle().getObject());

				target.add(this);
				target.add(feedback);
			}
		});
	}
}
