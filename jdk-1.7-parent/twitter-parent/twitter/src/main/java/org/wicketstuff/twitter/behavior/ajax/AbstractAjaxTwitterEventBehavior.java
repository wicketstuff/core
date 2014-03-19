package org.wicketstuff.twitter.behavior.ajax;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.RequestCycle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.twitter.behavior.TwitterApiBehavior;

/**
 * 
 * @author Till Freier
 * 
 */
public abstract class AbstractAjaxTwitterEventBehavior extends AbstractDefaultAjaxBehavior
{

	private static final String DATA_SCREEN_NAME = "data.screen_name";

	private static final String DATA_SOURCE_TWEET_ID = "data.source_tweet_id";

	private static final String DATA_TWEET_ID = "data.tweet_id";

	private static final String DATA_USER_ID = "data.user_id";

	private static final Logger log = LoggerFactory.getLogger(AbstractAjaxTwitterEventBehavior.class);

	private static final String REGION = "region";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String TYPE = "type";

	private final String eventId;


	public AbstractAjaxTwitterEventBehavior(final String event)
	{
		super();
		eventId = event;
	}

	/**
	 * @param js
	 * @param param
	 */
	protected StringBuilder appendUrlParamter(final StringBuilder js, final String urlVarName,
		final String param)
	{
		js.append("if(response != null");
		final StringBuilder var = new StringBuilder("response");
		for (final String part : param.split("\\."))
		{
			js.append(" && ");
			var.append('.');

			var.append(part);
			js.append(var).append("!=null");
		}
		js.append(") ");
		js.append(urlVarName);
		js.append(" += '&")
			.append(param)
			.append("='+")
			.append("escape(response.")
			.append(param)
			.append(");\n");

		return js;
	}


	/**
	 * 
	 * @param target
	 * @param requestParameters
	 */
	protected abstract void onEvent(AjaxRequestTarget target, final Event event);

	@Override
	public void renderHead(final Component component, final IHeaderResponse response)
	{
		super.renderHead(component, response);

		response.render(JavaScriptHeaderItem.forUrl(TwitterApiBehavior.WIDGETS_JS));

		final StringBuilder js = new StringBuilder();
		js.append("twttr.events.bind('").append(eventId).append("', function(response) { \n");

		final String urlVar = "callbackUrl";
		js.append("var ").append(urlVar).append("='").append(getCallbackUrl()).append("';");

		appendUrlParamter(js, urlVar, TYPE);
		appendUrlParamter(js, urlVar, REGION);
		appendUrlParamter(js, urlVar, DATA_TWEET_ID);
		appendUrlParamter(js, urlVar, DATA_SOURCE_TWEET_ID);
		appendUrlParamter(js, urlVar, DATA_SCREEN_NAME);
		appendUrlParamter(js, urlVar, DATA_USER_ID);

		js.append("Wicket.Ajax.get(callbackUrl, function(){},function(){});\n");
		js.append("}");
		js.append(");");

		response.render(JavaScriptHeaderItem.forScript(js.toString(), null));

	}

	@Override
	protected void respond(final AjaxRequestTarget target)
	{
		final Request request = RequestCycle.get().getRequest();
		final IRequestParameters requestParameters = request.getRequestParameters();

		log.debug("responsed");

		final Event event = Event.createEvent(requestParameters.getParameterValue(TYPE)
			.toOptionalString(), requestParameters.getParameterValue(REGION).toOptionalString(),
			requestParameters.getParameterValue(DATA_USER_ID).toOptionalString(),
			requestParameters.getParameterValue(DATA_TWEET_ID).toOptionalString(),
			requestParameters.getParameterValue(DATA_SOURCE_TWEET_ID).toOptionalString(),
			requestParameters.getParameterValue(DATA_SCREEN_NAME).toOptionalString());

		onEvent(target, event);
	}

}
