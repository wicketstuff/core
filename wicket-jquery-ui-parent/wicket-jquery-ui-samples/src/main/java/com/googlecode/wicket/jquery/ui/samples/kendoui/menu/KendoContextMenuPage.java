package com.googlecode.wicket.jquery.ui.samples.kendoui.menu;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.ListItem;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.samples.kendoui.menu.KendoMenuPage.MoveToPositionMenuItem;
import com.googlecode.wicket.jquery.ui.samples.kendoui.menu.KendoMenuPage.MoveToPositionPanel;
import com.googlecode.wicket.kendo.ui.panel.KendoFeedbackPanel;
import com.googlecode.wicket.kendo.ui.widget.menu.ContextMenu;
import com.googlecode.wicket.kendo.ui.widget.menu.item.IMenuItem;

public class KendoContextMenuPage extends AbstractMenuPage
{
	private static final long serialVersionUID = 1L;

	public KendoContextMenuPage()
	{
		// FeedbackPanel //
		final KendoFeedbackPanel feedback = new KendoFeedbackPanel("feedback");
		this.add(feedback);

		// Context Menu //
		this.add(new ContextMenu("menu", KendoMenuPage.newMenuItemList()) {

			private static final long serialVersionUID = 1L;

			// methods //

			@Override
			protected void addMenuItem(ListItem<IMenuItem> item, IMenuItem menuItem)
			{
				if (menuItem instanceof MoveToPositionMenuItem)
				{
					item.add(new MoveToPositionPanel("item", (MoveToPositionMenuItem) menuItem, KendoContextMenuPage.this));
					item.add(new WebMarkupContainer("menu"));
				}
				else
				{
					super.addMenuItem(item, menuItem);
				}
			}

			// events //

			@Override
			public void onConfigure(JQueryBehavior behavior)
			{
				super.onConfigure(behavior);

				behavior.setOption("target", Options.asString("#menu-target"));
			}

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

				this.reload(target);
				target.add(feedback);
			}
		});
	}
}
