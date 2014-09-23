package org.wicketstuff.jquery.ajaxbackbutton;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.resource.JQueryResourceReference;
import org.apache.wicket.util.string.StringValue;
import org.wicketstuff.jquery.JQueryBehavior;

/**
 * @author martin-g
 */
public abstract class HistoryAjaxBehavior extends AbstractDefaultAjaxBehavior
{

	private static final long serialVersionUID = 1L;

	private static final String HISTORY_ITEM_PARAM = "hiId";

	@Override
	public final CharSequence getCallbackUrl()
	{
		return super.getCallbackUrl() + "&" + HISTORY_ITEM_PARAM +
			"=' + HistoryManager.getHistoryItem()";
	}

	@Override
	public void renderHead(final Component component, final IHeaderResponse response)
	{
		response.render(CssHeaderItem.forReference(new PackageResourceReference(
			HistoryAjaxBehavior.class, "res/history-manager-iframe.css")));
		response.render(JavaScriptHeaderItem.forReference(JQueryResourceReference.get()));
		response.render(JavaScriptHeaderItem.forReference(new PackageResourceReference(
			HistoryAjaxBehavior.class, "res/history-manager.js")));

		/*
		 * Save the callback URL to this behavior to call it on back/forward button clicks
		 */
		response.render(JavaScriptHeaderItem.forScript(
			"var notifyBackButton = function() { wicketAjaxGet('" + getCallbackUrl() +
				", null, null, function() {return true;}.bind(this)); }", "history-manager-url"));
	}

	@Override
	protected void respond(AjaxRequestTarget target)
	{
		final StringValue componentId = RequestCycle.get()
			.getRequest()
			.getQueryParameters()
			.getParameterValue(HistoryAjaxBehavior.HISTORY_ITEM_PARAM);
		onAjaxHistoryEvent(target, componentId.toString());
	}

	/**
	 * A callback method which will be invoked when the user presses the back/forward buttons of the
	 * browser
	 * 
	 * @param target
	 *            the current request target
	 * @param componentId
	 *            the wicket:id of the component which had triggered the previous Ajax history entry
	 */
	public abstract void onAjaxHistoryEvent(final AjaxRequestTarget target, final String componentId);

	/**
	 * Registers a new entry in the history if this request is not triggered by back/forward buttons
	 * 
	 * @param target
	 *            the current request target
	 * @param component
	 *            the component which triggered this Ajax request
	 */
	public void registerAjaxEvent(final AjaxRequestTarget target, final Component component)
	{
		if (RequestCycle.get()
			.getRequest()
			.getQueryParameters()
			.getParameterValue(HistoryAjaxBehavior.HISTORY_ITEM_PARAM)
			.isNull())
		{
			target.appendJavaScript("HistoryManager.addHistoryEntry('" + component.getId() + "');");
		}
	}
}
