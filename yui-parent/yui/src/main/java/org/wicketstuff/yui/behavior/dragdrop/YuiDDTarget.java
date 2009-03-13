package org.wicketstuff.yui.behavior.dragdrop;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.wicketstuff.yui.YuiHeaderContributor;

public abstract class YuiDDTarget extends AbstractBehavior
{

	private static final long serialVersionUID = 1L;

	protected static final String PREFIX = "YDDT_";

	private String groupId = "default";

	private String targetId;

	public YuiDDTarget(String groupId)
	{
		super();
		this.groupId = groupId;
	}

	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);
		response.renderOnDomReadyJavascript(getJavascriptForDragDrop());
	}

	@Override
	public void bind(Component component)
	{
		super.bind(component);
		component.setOutputMarkupId(true);
		targetId = component.getMarkupId();
		component.add(YuiHeaderContributor.forModule("yahoo"));
		component.add(YuiHeaderContributor.forModule("dom"));
		component.add(YuiHeaderContributor.forModule("event"));
		component.add(YuiHeaderContributor.forModule("dragdrop"));
	}

	private String getJavascriptForDragDrop()
	{
		final StringBuffer js = new StringBuffer();

		String targetVarId = PREFIX + targetId;

		js.append(targetVarId).append(
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
