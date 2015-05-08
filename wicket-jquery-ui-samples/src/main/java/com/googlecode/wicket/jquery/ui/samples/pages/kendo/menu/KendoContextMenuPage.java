package com.googlecode.wicket.jquery.ui.samples.pages.kendo.menu;

import org.apache.wicket.ajax.AjaxRequestTarget;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.kendo.ui.widget.menu.ContextMenu;
import com.googlecode.wicket.kendo.ui.widget.menu.item.IMenuItem;

public class KendoContextMenuPage extends AbstractMenuPage
{
	private static final long serialVersionUID = 1L;

	public KendoContextMenuPage()
	{
		// Context Menu //
		this.add(new ContextMenu("menu", KendoMenuPage.newMenuItemList()) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onConfigure(JQueryBehavior behavior) {
				super.onConfigure(behavior);

				behavior.setOption("target", Options.asString("#menu-target"));
			}

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
