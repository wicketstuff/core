package org.wicketstuff.yui.behavior.dragdrop;

import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.Component.IVisitor;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.IBehavior;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.resources.JavascriptResourceReference;


/**
 * DDList from <a href="http
 * ://developer.yahoo.com/yui/examples/dragdrop/dd-reorder.html">YUI Library
 * Examples: Drag & Drop: Reordering a List </a>.
 * 
 * onDragOver : for interacting with list items &lt;li&gt;
 * 
 * onDragDrop : for handling empty list using DDTarget
 * 
 * @author josh
 */
@SuppressWarnings("serial")
public abstract class YuiDDList extends AbstractDefaultAjaxBehavior
{

	/** index of this new DDList item after being dragged */
	private static final String IDX = "idx";

	private static final String DOI = "doi";

	private static final String YAHOO_DOM_EVENT_2_7_0 = "http://yui.yahooapis.com/combo?2.7.0/build/yahoo-dom-event/yahoo-dom-event.js&2.7.0/build/dragdrop/dragdrop-min.js";

	private static final String DRAGDROP_MIN = "http://yui.yahooapis.com/2.7.0/build/dragdrop/dragdrop-min.js";

	private static final String ANIMATION_MIN = "http://yui.yahooapis.com/2.7.0/build/animation/animation-min.js";

	protected static final String PREFIX = "YDDL_";

	private String listId;

	private String groupId = "default";

	private static final ResourceReference YUI_DDLIST_JS = new JavascriptResourceReference(
			YuiDDList.class, "YuiDDList.js");

	public YuiDDList()
	{
		super();
	}

	public YuiDDList(String groupId)
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
		response.renderJavascriptReference(YUI_DDLIST_JS);
		response.renderOnDomReadyJavascript(getJavascriptForDragDrop());
	}

	@Override
	protected void onBind()
	{
		super.onBind();
		getComponent().setOutputMarkupId(true);
		listId = getComponent().getMarkupId();
	}

	private String getJavascriptForDragDrop()
	{
		final StringBuffer js = new StringBuffer();

		String varId = PREFIX + listId;

		js.append(varId + " = new Wicket.yui.DDList(\"" + listId + "\",\"" + getGroupId() + "\","
				+ getConfig() + "," + getCallbackWicket() + ");\n");

		js.append(varId).append(".setHandleElId(\"" + listId + "\");\n");

		return js.toString();
	}

	private String getGroupId()
	{
		return groupId;
	}

	private String getCallbackWicket()
	{
		return "function () { " + getCallbackScript() + " }";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.wicket.ajax.AbstractDefaultAjaxBehavior#getCallbackScript()
	 */
	@Override
	protected CharSequence getCallbackScript()
	{
		StringBuffer params = new StringBuffer();

		params.append("wicketAjaxGet('").append(getCallbackUrl(false)).append("'");
		params.append(" + '&").append(IDX).append("=' + ").append("this.idx");
		params.append(" + '&").append(DOI).append("=' + ").append("this.doi");
		return generateCallbackScript(params.toString());
	}

	protected String getConfig()
	{
		return "{}";
	}

	@Override
	protected void respond(final AjaxRequestTarget target)
	{

		final String idx = RequestCycle.get().getRequest().getParameter(IDX);
		final String doi = RequestCycle.get().getRequest().getParameter(DOI);

		final int index = Integer.parseInt(idx);

		getComponent().getPage().visitChildren(new IVisitor<Component>()
		{

			public Object component(Component component)
			{
				if (component.getMarkupId().equals(doi))
				{
					Component validTargetItem = component;

					// check if the dragOverId is a Target or
					// a DDList

					List<IBehavior> behaviors = component.getBehaviors();

					for (IBehavior behavior : behaviors)
					{
						if (behavior instanceof YuiDDTarget)
						{
							YuiDDTarget ddTarget = (YuiDDTarget)behavior;
							ddTarget.onDrop(target, YuiDDList.this.getComponent());
							validTargetItem = null;
						}
					}

					onDrop(target, index, validTargetItem);
				}
				return CONTINUE_TRAVERSAL;
			}
		});
	}

	/**
	 * 
	 * @param ajaxRequestTarget
	 * @param index
	 * @param lis2
	 */
	protected abstract void onDrop(AjaxRequestTarget target, int index, Component dragOverItem);

}
