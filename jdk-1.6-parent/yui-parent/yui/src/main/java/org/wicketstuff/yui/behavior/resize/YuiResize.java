package org.wicketstuff.yui.behavior.resize;

import org.apache.wicket.Component;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.behavior.AbstractBehavior;
import org.wicketstuff.yui.markup.html.contributor.yuiloader.YuiLoaderContributor;
import org.wicketstuff.yui.markup.html.contributor.yuiloader.YuiLoaderModule;

public class YuiResize extends AbstractBehavior
{

	private static final long serialVersionUID = 1L;

	private static final String WICKET_YUI_RESIZE = "wicket_resize";

	private static final ResourceReference WICKET_YUI_RESIZE_JS = new ResourceReference(
			YuiResize.class, "YuiResize.js");

	private static final String[] REQUIRE_YUI_MODULES = { "resize", "animation" };

	private String componentId;

	public YuiResize()
	{
	}

	@Override
	public void bind(Component component)
	{
		super.bind(component);

		component.setOutputMarkupId(true);
		this.componentId = component.getMarkupId();

		component.add(YuiLoaderContributor.addModule(new YuiLoaderModule(WICKET_YUI_RESIZE,
				YuiLoaderModule.ModuleType.js, WICKET_YUI_RESIZE_JS, REQUIRE_YUI_MODULES)
		{
			private static final long serialVersionUID = 1L;

			@Override
			public String onSuccessJS()
			{
				return initJS();
			}
		}));
	}

	private String initJS()
	{
		return "var " + getYuiResizeVar() + " = new YAHOO.Wicket.Resize(\"" + getComponentId()
				+ "\"," + getOpts() + "," + getStartResizeJs() + "," + getResizeJs() + ");";
	}

	protected String getResizeJs()
	{
		return "{}";
	}

	protected String getStartResizeJs()
	{
		return "{}";
	}

	protected String getOpts()
	{
		return "{}";
	}

	private String getComponentId()
	{
		return this.componentId;
	}

	protected String getYuiResizeVar()
	{
		return "var_" + getComponentId() + "_rs";
	}

}
