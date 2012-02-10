package org.wicketstuff.datatables;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.PackageResourceReference;

public class DemoDatatable extends WebMarkupContainer
{

	private static final long serialVersionUID = -4387194295178034384L;

	public DemoDatatable(String id)
	{
		super(id);
		setOutputMarkupId(true);

		add(new AttributeModifier("class", Model.of("display")));
	}

	@Override
	public void renderHead(IHeaderResponse response)
	{
		renderDemoCSS(response);
		renderBasicJS(response);

		StringBuilder js = new StringBuilder();
		js.append("$(document).ready( function() {\n");
		js.append("	$('#" + getMarkupId() + "').dataTable( {\n");
		js.append("		\"bJQueryUI\": true,\n");
		js.append("		\"sPaginationType\": \"full_numbers\"\n");
		js.append("	} );\n");
		js.append("} );");

		response.renderJavaScript(js, getId() + "_datatables");
	}

	private void renderDemoCSS(IHeaderResponse response)
	{
		final Class<DemoDatatable> _ = DemoDatatable.class;
		response.renderCSSReference(
			new PackageResourceReference(_, "media/css/demo_table_jui.css"), "screen");

		response.renderCSSReference(new PackageResourceReference(_, "media/css/" + getJUITheme() +
			"/jquery-ui-1.8.10.custom.css"), "screen");
	}

	private String getJUITheme()
	{
		return "smoothness";
	}

	private void renderBasicJS(IHeaderResponse response)
	{
		final Class<DemoDatatable> _ = DemoDatatable.class;

		response.renderJavaScriptReference(new PackageResourceReference(_, "media/js/jquery.js"));
		response.renderJavaScriptReference(new PackageResourceReference(_,
			"media/js/jquery.dataTables.min.js"));
		response.renderJavaScriptReference(new PackageResourceReference(_,
			"media/js/jquery-ui-1.8.10.custom.min.js"));
	}
}
