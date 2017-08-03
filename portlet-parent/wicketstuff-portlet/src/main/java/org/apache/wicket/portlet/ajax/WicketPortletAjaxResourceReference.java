package org.apache.wicket.portlet.ajax;

import java.util.List;

import org.apache.wicket.ajax.WicketAjaxJQueryResourceReference;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

/**
 * @author Konstantinos Karavitis
 *
 */
public class WicketPortletAjaxResourceReference extends JavaScriptResourceReference
{
	private static final long serialVersionUID = 1L;

	private static WicketPortletAjaxResourceReference INSTANCE = new WicketPortletAjaxResourceReference();

	/**
	 * @return the singleton INSTANCE
	 */
	public static WicketPortletAjaxResourceReference get()
	{
		return INSTANCE;
	}

	private WicketPortletAjaxResourceReference()
	{
		super(WicketPortletAjaxResourceReference.class, "res/js/wicket-portlet-ajax.js");
	}

	@Override
	public List<HeaderItem> getDependencies()
	{
		List<HeaderItem> dependencies = super.getDependencies();
		dependencies.add(JavaScriptHeaderItem.forReference(WicketAjaxJQueryResourceReference.get()));
		return dependencies;
	}
}
