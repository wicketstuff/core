package org.wicketstuff.jquery.lavalamp;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.wicketstuff.jquery.JQueryBehavior;

public class JQueryLavaLampBehavior extends JQueryBehavior
{
	private static final long serialVersionUID = -428199323406701848L;

	public static final ResourceReference JQUERY_EASING_JS = new PackageResourceReference(
		JQueryLavaLampBehavior.class, "jquery.easing.1.1.js");
	public static final ResourceReference JQUERY_LAVALAMP_JS = new PackageResourceReference(
		JQueryLavaLampBehavior.class, "jquery.lavalamp.js");

	public JQueryLavaLampBehavior()
	{
		super();
	}

	@Override
	public void renderHead(Component component, IHeaderResponse response)
	{
		super.renderHead(component, response);
		response.render(JavaScriptHeaderItem.forReference(JQUERY_EASING_JS));
		response.render(JavaScriptHeaderItem.forReference(JQUERY_LAVALAMP_JS));
	}

	@Override
	protected void respond(AjaxRequestTarget target)
	{
	}

}
