package com.googlecode.wicket.jquery.ui.samples.jqueryui.menu;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.lang.Generics;

import com.googlecode.wicket.jquery.core.resource.StyleSheetPackageHeaderItem;
import com.googlecode.wicket.jquery.ui.JQueryIcon;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.widget.menu.IMenuItem;
import com.googlecode.wicket.jquery.ui.widget.menu.Menu;
import com.googlecode.wicket.jquery.ui.widget.menu.MenuItem;

public class DefaultMenuPage extends AbstractMenuPage
{
	private static final long serialVersionUID = 1L;

	static List<IMenuItem> newMenuItemList()
	{
		List<IMenuItem> list = Generics.newArrayList();

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
		list.add(new MenuItem("Menu item, with sub-menu", JQueryIcon.BOOKMARK, newSubMenuList())); // css-class are also allowed
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
		List<IMenuItem> list = Generics.newArrayList();

		list.add(new MenuItem("Sub-menu #1"));
		list.add(new MenuItem("Sub-menu #2"));
		list.add(new MenuItem("Sub-menu #3"));

		return list;
	}

	public DefaultMenuPage()
	{
		// FeedbackPanel //
		final FeedbackPanel feedback = new JQueryFeedbackPanel("feedback");
		this.add(feedback.setOutputMarkupId(true));

		// Menu //
		this.add(new Menu("menu", DefaultMenuPage.newMenuItemList()) {

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

	// Methods //

	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);

		response.render(new StyleSheetPackageHeaderItem(DefaultMenuPage.class));
	}
}
