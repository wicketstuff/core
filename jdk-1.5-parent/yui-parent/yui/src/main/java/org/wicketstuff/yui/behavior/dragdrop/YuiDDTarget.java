package org.wicketstuff.yui.behavior.dragdrop;

import org.apache.wicket.Component;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AbstractBehavior;
import org.wicketstuff.yui.markup.html.contributor.yuiloader.YuiLoaderContributor;
import org.wicketstuff.yui.markup.html.contributor.yuiloader.YuiLoaderModule;

/**
 * DDTarget used for empty list
 * @author josh
 *
 */
public abstract class YuiDDTarget extends AbstractBehavior
{

	private static final long serialVersionUID = 1L;

	private static final String MODULE_NAME = "wicket_yui_target";

	private static final ResourceReference MODULE_REF_JS = new ResourceReference(YuiDDTarget.class,
			"YuiDDTarget.js");

	private static final String[] MODULE_REQUIRES = { "dragdrop", "animation" };

	protected static final String PREFIX = "YDDT_";

	private String groupId = "default";

	private String targetId;

	public YuiDDTarget(String groupId)
	{
		super();
		this.groupId = groupId;
	}

	@SuppressWarnings("serial")
	@Override
	public void bind(Component component)
	{
		super.bind(component);
		component.setOutputMarkupId(true);
		targetId = component.getMarkupId();
		component.add(YuiLoaderContributor.addModule(new YuiLoaderModule(MODULE_NAME,
				YuiLoaderModule.ModuleType.js, MODULE_REF_JS, MODULE_REQUIRES)
		{
			@Override
			public String onSuccessJS()
			{
				return YuiDDTarget.this.getInitJS();
			}
		}));
	}

	private String getInitJS()
	{
		final StringBuffer js = new StringBuffer();

		String targetVarId = PREFIX + targetId;

		js.append("var " + targetVarId).append(
				" = new YAHOO.util.DDTarget(\"" + getTargetId() + "\", \"" + getGroupId() + "\","
						+ getConfig() + ");\n");

		return js.toString();
	}

	private String getGroupId()
	{
		return groupId;
	}

	private String getTargetId()
	{
		return targetId;
	}

	/**
	 * the Javascript Config or DDTarget
	 * 
	 * @return
	 */
	protected String getConfig()
	{
		return "{}";
	}

	public abstract void onDrop(AjaxRequestTarget target, Component component);
}
