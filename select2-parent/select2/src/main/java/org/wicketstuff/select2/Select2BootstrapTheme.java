package org.wicketstuff.select2;

import de.agilecoders.wicket.webjars.request.resource.WebjarsCssResourceReference;
import org.apache.wicket.Component;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.ResourceReference;

/**
 * Select2BootstrapThemeBehavior. Adds bootstrap look and feel to select2, See
 *
 * https://github.com/select2/select2-bootstrap-theme
 * 
 * @author Ernesto Reinaldo Barreiro (reirn70@gmail.com)
 *  
 */
public class Select2BootstrapTheme implements  ISelect2Theme
{
	private static final long serialVersionUID = 1L;

	private boolean useBootstrapWebJar = false;

	private static final ResourceReference CSS = new CssResourceReference(Select2BootstrapTheme.class, "/res/bootstrap/select2-bootstrap.css");

	public Select2BootstrapTheme(boolean useBootstrapWebJar)
	{
		this.useBootstrapWebJar = useBootstrapWebJar;
	}

	public void renderHead(final Component varComponent, final IHeaderResponse response)
	{
		if (useBootstrapWebJar)
		{
			response.render(CssHeaderItem.forReference(new WebjarsCssResourceReference("/bootstrap/current/css/bootstrap.css")));
		}
		response.render(CssHeaderItem.forReference(CSS));
	}

	@Override
	public String name()
	{
		return "bootstrap";
	}
}
