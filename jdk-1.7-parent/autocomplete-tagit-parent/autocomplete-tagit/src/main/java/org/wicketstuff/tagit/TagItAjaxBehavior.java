package org.wicketstuff.tagit;

import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.AbstractAjaxBehavior;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.handler.TextRequestHandler;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.request.resource.UrlResourceReference;
import org.apache.wicket.resource.JQueryPluginResourceReference;
import org.apache.wicket.util.string.StringValue;
import org.apache.wicket.util.template.PackageTextTemplate;
import org.apache.wicket.util.template.TextTemplate;

/**
 * The behavior that returns JSON response for the autocomplete widget.
 */
public abstract class TagItAjaxBehavior<T> extends AbstractAjaxBehavior
{

	private static final long serialVersionUID = 1L;

	private static final ResourceReference TAG_IT_JS = new JQueryPluginResourceReference(
		TagItAjaxBehavior.class, "res/tag-it.js");

	private static final ResourceReference TAG_IT_CSS = new CssResourceReference(
		TagItAjaxBehavior.class, "res/jquery.tagit.css");

	public final void onRequest()
	{
		RequestCycle requestCycle = getComponent().getRequestCycle();
		Request request = requestCycle.getRequest();
		IRequestParameters parameters = request.getRequestParameters();
		StringValue input = parameters.getParameterValue("term");

		final Iterable<T> choices = getChoices(input.toString(""));

		String jsonArray = createJson(choices);

		requestCycle.scheduleRequestHandlerAfterCurrent(new TextRequestHandler("application/json",
			"UTF-8", jsonArray));
	}

	/**
	 * Serializes the returned choices into JSON array.
	 * 
	 * @param choices
	 *            the choices for the term
	 * @return JSON array with all choices
	 */
	protected String createJson(final Iterable<T> choices)
	{

		StringBuilder json = new StringBuilder();
		json.append('[');
		for (T choice : choices)
		{
			if (json.length() > 1)
			{
				json.append(',');
			}
			json.append('"').append(choice).append('"');
		}
		json.append(']');

		return json.toString();
	}

	/**
	 * Finds the possible choices for the provided input
	 * 
	 * @param input
	 *            the term provided by the user.
	 * @return a collection of all possible choices for this input
	 */
	protected abstract Iterable<T> getChoices(String input);

	@Override
	public void renderHead(final Component component, final IHeaderResponse response)
	{
		super.renderHead(component, response);

		response.render(CssHeaderItem.forReference(TAG_IT_CSS));
		response.render(JavaScriptHeaderItem.forReference(getComponent().getApplication().getJavaScriptLibrarySettings().getJQueryReference()));
		response.render(JavaScriptHeaderItem.forReference(new UrlResourceReference(Url.parse("//ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"))));
		response.render(JavaScriptHeaderItem.forReference(TAG_IT_JS));

		component.setOutputMarkupId(true);
		String id = component.getMarkupId();

		TextTemplate tagItConfig = getTagItConfig();

		Map<String, CharSequence> variables = new HashMap<String, CharSequence>();
		variables.put("componentId", id);
		variables.put("callbackUrl", getCallbackUrl());

		String script = tagItConfig.asString(variables);

		response.render(OnDomReadyHeaderItem.forScript(script));
	}

	/**
	 * Loads and populates the TagIt configuration
	 * 
	 * @return the JavaScript needed to "tagit" an input text field
	 */
	protected TextTemplate getTagItConfig()
	{
		return new PackageTextTemplate(TagItAjaxBehavior.class, "res/tag-it.tmpl.js");
	}
}
