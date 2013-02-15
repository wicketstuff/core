package com.googlecode.wicket.jquery.ui.samples.pages.menu;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;

import com.googlecode.wicket.jquery.ui.widget.menu.Menu;
import com.googlecode.wicket.jquery.ui.widget.menu.MenuItem;

public class DefaultMenuPage extends AbstractMenuPage
{
	private static final long serialVersionUID = 1L;

	public DefaultMenuPage()
	{
		List<MenuItem> items = Arrays.asList(
				new MenuItem("Aberdeen"),
				new MenuItem("Ada"),
				new MenuItem("Adamsville"),
				new MenuItem("Addyston"),
				new MenuItem("Delphi", Arrays.asList(
						new MenuItem("Ada"),
						new MenuItem("Saarland"),
						new MenuItem("Salzburg"))),
				new MenuItem("Desactivate me") {

					private static final long serialVersionUID = 1L;

					@Override
					protected void onClick(AjaxRequestTarget target)
					{
//						this.set
					}
				});

		this.add(new Menu("menu", items));
	}
}
