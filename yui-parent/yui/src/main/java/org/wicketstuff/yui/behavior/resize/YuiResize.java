package org.wicketstuff.yui.behavior.resize;

import org.apache.wicket.Component;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.protocol.http.RequestUtils;
import org.wicketstuff.yui.markup.html.contributor.YuiLoaderContributor;
import org.wicketstuff.yui.markup.html.contributor.YuiLoaderModule;

public class YuiResize extends AbstractBehavior
{

	private static final long serialVersionUID = 1L;

	private static final String WICKET_YUI_RESIZE = "wicket_yui_resize";

	private static final ResourceReference WICKET_YUI_RESIZE_JS = new ResourceReference(
			YuiResize.class, "YuiResize.js");

	private static final String[] REQUIRE_YUI_MODULES = { "resize", "animation" };

	private String componentId;


	@Override
	public void bind(Component component)
	{
		super.bind(component);

		component.setOutputMarkupId(true);
		componentId = component.getMarkupId();

		String relativePath = (String)RequestCycle.get().urlFor(WICKET_YUI_RESIZE_JS);
		String fullpath = RequestUtils.toAbsolutePath(relativePath);

		component.add(YuiLoaderContributor.addModule(new YuiLoaderModule(WICKET_YUI_RESIZE,
				YuiLoaderModule.ModuleType.js, fullpath, REQUIRE_YUI_MODULES)
		{
			private static final long serialVersionUID = 1L;

			@Override
			public String getInitJS()
			{
				return initJS();
			}
		}));
	}

	private String initJS()
	{
		return "var " + getYuiResizeVar() + " = new Wicket.yui.Resize(\"" + getComponentId()
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
		return componentId;
	}

	protected String getYuiResizeVar()
	{
		return "var_" + getComponentId() + "_rs";
	}

}
