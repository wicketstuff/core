package org.wicketstuff.yui.behavior.dragdrop;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.markup.html.IHeaderResponse;

public abstract class YuiDDTarget extends AbstractBehavior
{

	private static final long serialVersionUID = 1L;

	private static final String YAHOO_DOM_EVENT_2_7_0 = "http://yui.yahooapis.com/combo?2.7.0/build/yahoo-dom-event/yahoo-dom-event.js&2.7.0/build/dragdrop/dragdrop-min.js";

	private static final String DRAGDROP_MIN = "http://yui.yahooapis.com/2.7.0/build/dragdrop/dragdrop-min.js";

	private static final String ANIMATION_MIN = "http://yui.yahooapis.com/2.7.0/build/animation/animation-min.js";

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
		response.renderJavascriptReference(YAHOO_DOM_EVENT_2_7_0);
		response.renderJavascriptReference(DRAGDROP_MIN);
		response.renderJavascriptReference(ANIMATION_MIN);
		response.renderOnDomReadyJavascript(getJavascriptForDragDrop());
	}

	@Override
	public void bind(Component component)
	{
		super.bind(component);
		component.setOutputMarkupId(true);
		targetId = component.getMarkupId();
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
