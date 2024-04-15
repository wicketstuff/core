package com.googlecode.wicket.jquery.ui.samples.kendoui.treeview;

import org.apache.wicket.markup.head.IHeaderResponse;

import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.resource.StyleSheetPackageHeaderItem;
import com.googlecode.wicket.kendo.ui.KendoUIBehavior;

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
