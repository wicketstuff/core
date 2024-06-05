package org.wicketstuff.datatables;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.Model;
import org.wicketstuff.datatables.res.DataTablesCssReference;
import org.wicketstuff.datatables.res.DataTablesJsReference;

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

		response.render(JavaScriptHeaderItem.forScript(js, getId() + "_datatables"));
	}

	private void renderDemoCSS(IHeaderResponse response)
	{
		response.render(CssHeaderItem.forReference(new DataTablesCssReference()));
	}

	private void renderBasicJS(IHeaderResponse response)
	{
		response.render(JavaScriptHeaderItem.forReference(new DataTablesJsReference()));
	}
}
