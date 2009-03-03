package org.wicketstuff.yui.behavior.dragdrop;

import org.apache.wicket.RequestCycle;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.resources.JavascriptResourceReference;

@SuppressWarnings("serial")
public abstract class YuiListDragDropBehavior extends AbstractDefaultAjaxBehavior
{
	private static final String YAHOO_DOM_EVENT_2_7_0 = "http://yui.yahooapis.com/combo?2.7.0/build/yahoo-dom-event/yahoo-dom-event.js&2.7.0/build/dragdrop/dragdrop-min.js";

	private static final String DRAGDROP_MIN = "http://yui.yahooapis.com/2.7.0/build/dragdrop/dragdrop-min.js";

	private static final String ANIMATION_MIN = "http://yui.yahooapis.com/2.7.0/build/animation/animation-min.js";

	protected static final String PREFIX = "YDD_";

	protected static final String PREFIX_GRP = "YDD_GRP_";

	private static final ResourceReference DYN_TAB_JS = new JavascriptResourceReference(YuiListDragDropBehavior.class,
			"YuiListDragDropBehavior.js");

	private int tabIndex;

	public YuiListDragDropBehavior(int tabIndex)
	{
		super();
		this.tabIndex = tabIndex;
	}

	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);
		response.renderJavascriptReference(YAHOO_DOM_EVENT_2_7_0);
		response.renderJavascriptReference(DRAGDROP_MIN);
		response.renderJavascriptReference(ANIMATION_MIN);
		response.renderJavascriptReference(DYN_TAB_JS);
		response.renderOnDomReadyJavascript(getJavascriptForDragDrop(getComponent().getParent().getMarkupId(),
				getComponent().getMarkupId()));
	}

	@Override
	protected void respond(AjaxRequestTarget target)
	{
		String swapId = RequestCycle.get().getRequest().getParameter("swapId");
		onDrop(target, swapId);
	}

	public abstract void onDrop(AjaxRequestTarget target, String swapId);

	@Override
	protected void onBind()
	{
		super.onBind();
	}

	private String getJavascriptForDragDrop(String targetMarkupId, String markupId)
	{
		final StringBuffer js = new StringBuffer();

		String targetVarId = PREFIX + targetMarkupId;
		final String groupId = PREFIX_GRP + targetMarkupId;

		js.append(targetVarId).append(" = new YAHOO.util.DDTarget(\"" + targetMarkupId + "\", \"" + groupId + "\");\n");

		String varId = PREFIX + markupId;
		js.append(varId + " = new YAHOO.ddlist.DDList(\"" + markupId + "\",\"" + groupId + "\"," + getConfig() + ","
				+ getCallbackWicket() + "," + tabIndex +");\n");
		js.append(varId).append(".setHandleElId(\"" + markupId + "\");\n");

		return js.toString();
	}

	private String getCallbackWicket()
	{
		return "function () { " + getCallbackScript() + " }";
	}

	@Override
	protected CharSequence getCallbackScript()
	{
		return generateCallbackScript("wicketAjaxGet('" + getCallbackUrl(false) + "&swapId=' + this.lastSwapId");
	}

	private String getConfig()
	{
		return "{}";
	}
}
