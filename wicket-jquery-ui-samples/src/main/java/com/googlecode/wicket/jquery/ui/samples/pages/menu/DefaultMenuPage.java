package com.googlecode.wicket.jquery.ui.samples.pages.menu;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.ui.JQueryIcon;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.widget.menu.IMenuItem;
import com.googlecode.wicket.jquery.ui.widget.menu.Menu;
import com.googlecode.wicket.jquery.ui.widget.menu.MenuItem;

public class DefaultMenuPage extends AbstractMenuPage
{
	private static final long serialVersionUID = 1L;

	public DefaultMenuPage()
	{
		// FeedbackPanel //
		final FeedbackPanel feedback = new JQueryFeedbackPanel("feedback");
		this.add(feedback.setOutputMarkupId(true));

		// Menu //
		this.add(new Menu("menu", this.newMenuList()) {

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

	private List<IMenuItem> newMenuList()
	{
		List<IMenuItem> list = new ArrayList<IMenuItem>();

		list.add(new MenuItem("Item with icon", JQueryIcon.FLAG));
		list.add(new MenuItem("Change the title") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target)
			{
				this.setTitle(Model.of("Title changed!"));
			}
		});
		list.add(new MenuItem("Another menu item"));
		list.add(new MenuItem("Menu item, with sub-menu", JQueryIcon.BOOKMARK, this.newSubMenuList())); // css-class are also allowed
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

	private List<IMenuItem> newSubMenuList()
	{
		List<IMenuItem> list = new ArrayList<IMenuItem>();

		list.add(new MenuItem("Sub-menu #1"));
		list.add(new MenuItem("Sub-menu #2"));
		list.add(new MenuItem("Sub-menu #3"));

		return list;
	}
}
