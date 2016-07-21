package org.wicketstuff.jquery.lightbox;

import org.apache.wicket.Component;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.wicketstuff.jquery.JQueryBehavior;

/**
 * 
 * Adds LightBox hehaviour to the selected component. The default is to look for li's with anchors
 * in them and create lightbox from the href of the anchors. You can override this by using the
 * constructor that takes the css selector as an argument
 * 
 * @author Edvin Syse <edvin@sysedata.no>
 * @created 2008-06-26
 * 
 */
public class LightboxBehaviour extends JQueryBehavior
{
	private static final long serialVersionUID = 1L;
	private static final ResourceReference LIGHTBOX_JS = new PackageResourceReference(
		LightboxBehaviour.class, "jquery.lightbox-0.5.js");
	private static final ResourceReference LIGHTBOX_CSS = new PackageResourceReference(
		LightboxBehaviour.class, "jquery.lightbox-0.5.css");
	private LightboxOptions options;
	private String selector = "li a";

	/*
	 * Default constructor, selects anchors inside an li as default lightbox target.
	 */
	public LightboxBehaviour()
	{
		options = new LightboxOptions();
	}

	/*
	 * Constructor that lets you override the css selector so you can choose what to convert to a
	 * lightbox.
	 */
	public LightboxBehaviour(String selector)
	{
		this();
		this.selector = selector;
	}

	public LightboxOptions getOptions()
	{
		return options;
	}

	@Override
	protected CharSequence getOnReadyScript()
	{
		return "\t$('#" + getComponent().getMarkupId() + getSelector() + "').lightBox(" +
			options.toString(false) + ");";
	}

	public String getSelector()
	{
		return " " + selector;
	}

	@Override
	public void renderHead(Component component, IHeaderResponse response)
	{
		super.renderHead(component, response);
		response.render(JavaScriptHeaderItem.forReference(getLightboxJs()));
		response.render(CssHeaderItem.forReference(getLightboxCss()));
	}

	@Override
	protected void onBind()
	{
		getComponent().setOutputMarkupId(true);
	}

	private ResourceReference getLightboxJs()
	{
		return LIGHTBOX_JS;
	}

	public ResourceReference getLightboxCss()
	{
		return LIGHTBOX_CSS;
	}
}