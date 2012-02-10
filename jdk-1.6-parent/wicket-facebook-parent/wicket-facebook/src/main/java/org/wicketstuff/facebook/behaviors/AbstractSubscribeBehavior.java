package org.wicketstuff.facebook.behaviors;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.util.iterator.ComponentHierarchyIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.facebook.FacebookRootProvider;
import org.wicketstuff.facebook.MissingFacebookRootException;

/**
 * https://developers.facebook.com/docs/reference/javascript/FB.Event.subscribe/
 * 
 * @author Till Freier
 * 
 */
public abstract class AbstractSubscribeBehavior extends AbstractDefaultAjaxBehavior
{
	private static final Logger log = LoggerFactory.getLogger(AbstractSubscribeBehavior.class);

	private final String event;
	private final List<String> parameters;

	/**
	 * 
	 * @param event
	 * @param parameters
	 */
	protected AbstractSubscribeBehavior(final String event, final String... parameters)
	{
		this.event = event;
		this.parameters = Arrays.asList(parameters);
	}


	/**
	 * @param component
	 */
	private void checkFacebookRoot(final Component component)
	{
		final ComponentHierarchyIterator visitChildren = component.getPage().visitChildren(
			FacebookRootProvider.class);
		if (!visitChildren.hasNext())
			throw new MissingFacebookRootException();
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public void renderHead(final Component component, final IHeaderResponse response)
	{
		super.renderHead(component, response);

		checkFacebookRoot(component);

		final StringBuilder js = new StringBuilder();
		js.append("FB.Event.subscribe('").append(event).append("', function(response) { ");
		js.append("wicketAjaxGet('");
		js.append(getCallbackUrl());
		for (final String param : parameters)
			js.append("&")
				.append(param)
				.append("='+")
				.append("escape(response.")
				.append(param)
				.append(")+'");
		js.append("&response='+escape(response), function(){},function(){});");
		js.append("}");
		js.append(");");

		response.renderJavaScript(js.toString(), null);
	}


	protected abstract void onEvent(AjaxRequestTarget target, IRequestParameters parameters,
		String response);


	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void respond(final AjaxRequestTarget target)
	{
		final Request request = RequestCycle.get().getRequest();
		final IRequestParameters requestParameters = request.getRequestParameters();

		if (log.isDebugEnabled())
			log.debug("onRequest - response:" +
				requestParameters.getParameterValue("response").toString());

		onEvent(target, requestParameters, requestParameters.getParameterValue("response")
			.toOptionalString());
	}
}
