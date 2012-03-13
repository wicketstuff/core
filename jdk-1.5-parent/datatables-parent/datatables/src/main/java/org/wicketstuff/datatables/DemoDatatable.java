package org.wicketstuff.datatables;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Response;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.PackageResourceReference;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.internal.HtmlHeaderContainer;
import org.apache.wicket.model.Model;

public class DemoDatatable extends WebMarkupContainer {

	private static final long serialVersionUID = -4387194295178034384L;

	public DemoDatatable(String id) {
		super(id);
		setOutputMarkupId(true);

		add(new AttributeModifier("class", new Model("display")));
		
	
	}

	
	
	@Override
	public void renderHead(HtmlHeaderContainer container) {
		
		IHeaderResponse response = container.getHeaderResponse();
		
		renderDemoCSS(response);
		renderBasicJS(response);

		StringBuilder js = new StringBuilder();
		js.append("$(document).ready( function() {\n");
		js.append("	$('#" + getMarkupId() + "').dataTable( {\n");
		js.append("		\"bJQueryUI\": true,\n");
		js.append("		\"sPaginationType\": \"full_numbers\"\n");
		js.append("	} );\n");
		js.append("} );");

		response.renderJavascript(js, getId() + "_datatables");
	}

	private void renderDemoCSS(IHeaderResponse response) {
		final Class<DemoDatatable> _ = DemoDatatable.class;
		response.renderCSSReference(new PackageResourceReference(_,
				"media/css/demo_table_jui.css"), "screen");

		response.renderCSSReference(new PackageResourceReference(_,
				"media/css/" + getJUITheme() + "/jquery-ui-1.8.10.custom.css"),
				"screen");
	}

	private String getJUITheme() {
		return "smoothness";
	}

	private void renderBasicJS(IHeaderResponse response) {
		final Class<DemoDatatable> _ = DemoDatatable.class;

		response.renderJavascriptReference(new PackageResourceReference(_,
				"media/js/jquery.js"));
		response.renderJavascriptReference(new PackageResourceReference(_,
				"media/js/jquery.dataTables.min.js"));
		response.renderJavascriptReference(new PackageResourceReference(_,
				"media/js/jquery-ui-1.8.10.custom.min.js"));
	}
}
