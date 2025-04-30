/*
Licensed under the Apache License, Version 2.0 (the "License") http://www.apache.org/licenses/LICENSE-2.0
 */
package org.wicketstuff.jquery.ui.samples.jqueryui.menu;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.wicketstuff.jquery.core.resource.StyleSheetPackageHeaderItem;
import org.wicketstuff.jquery.ui.panel.JQueryFeedbackPanel;
import org.wicketstuff.jquery.ui.widget.menu.ContextMenu;
import org.wicketstuff.jquery.ui.widget.menu.ContextMenuBehavior;
import org.wicketstuff.jquery.ui.widget.menu.IMenuItem;

public class ContextMenuPage extends AbstractMenuPage
{
	private static final long serialVersionUID = 1L;

	public ContextMenuPage()
	{
		// FeedbackPanel //
		final FeedbackPanel feedback = new JQueryFeedbackPanel("feedback");
		this.add(feedback.setOutputMarkupId(true));

		// Menu //
		final ContextMenu menu = new ContextMenu("menu", DefaultMenuPage.newMenuItemList()) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onContextMenu(AjaxRequestTarget target, Component component)
			{
				// the menu-item list can be modified here
				// this.getItemList().add(new MenuItem("my new item"));
			}

			@Override
			public void onClick(AjaxRequestTarget target, IMenuItem item)
			{
				this.info("Clicked " + item.getTitle().getObject());

				target.add(this);
				target.add(feedback);
			}
		};

		this.add(menu);

		// Labels //
		final Label label1 = new Label("label1", "my label 1");
		label1.add(new ContextMenuBehavior(menu));
		this.add(label1);

		final Label label2 = new Label("label2", "my label 2");
		label2.add(new ContextMenuBehavior(menu));
		this.add(label2);
	}

	// Methods //

	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);

		response.render(new StyleSheetPackageHeaderItem(ContextMenuPage.class));
	}
}
