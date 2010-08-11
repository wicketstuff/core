package org.wicketstuff.scriptaculous.fx;

import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.util.template.PackagedTextTemplate;
import org.apache.wicket.util.template.TextTemplate;

public class ToasterCSSHeaderContributor extends AbstractBehavior implements IHeaderContributor {
	
	private final TextTemplate toasterCSS = new PackagedTextTemplate(
			ToasterCSSHeaderContributor.class, "Toaster.css");

	
	
	
	public ToasterCSSHeaderContributor(String componentId, ToasterSettings toasterSettings) {
		
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("id",componentId);
		variables.put("vertical", toasterSettings.getLocation().getVertical());
		variables.put("horizontal", toasterSettings.getLocation().getHorizontal());
		variables.put("verticalpx", toasterSettings.getLocation().getVerticalPx());
		variables.put("horizontalpx", toasterSettings.getLocation().getHorizontalPx());

		variables.put("border-color", toasterSettings.getToasterBorderColor());
		variables.put("background-color", toasterSettings.getToasterBackground());
		toasterCSS.interpolate(variables);
		
	}
	
	
	@Override
	public void renderHead(IHeaderResponse response) {
		// TODO Auto-generated method stub
		super.renderHead(response);
		response.renderString(toasterCSS.asString());
	
	}

}
