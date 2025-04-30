/*
Licensed under the Apache License, Version 2.0 (the "License") http://www.apache.org/licenses/LICENSE-2.0
 */
package org.wicketstuff.jquery.ui.samples.kendoui.treeview;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.wicketstuff.jquery.core.Options;
import org.wicketstuff.jquery.core.resource.StyleSheetPackageHeaderItem;
import org.wicketstuff.kendo.ui.KendoUIBehavior;

public class DefaultTreeViewPage extends AbstractTreeViewPage
{
	private static final long serialVersionUID = 1L;

	public DefaultTreeViewPage()
	{
		Options options = new Options();
		options.set("animation", false);
		options.set("select", "function(e) { e.preventDefault(); }");

		this.add(new KendoUIBehavior("#treeview", "kendoTreeView", options));
	}

	// Methods //

	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);

		response.render(new StyleSheetPackageHeaderItem(DefaultTreeViewPage.class));
	}
}
