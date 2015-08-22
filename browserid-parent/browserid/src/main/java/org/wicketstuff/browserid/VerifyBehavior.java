package org.wicketstuff.browserid;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.Component;
import org.apache.wicket.Session;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.util.io.IOUtils;
import org.apache.wicket.util.string.StringValue;
import org.apache.wicket.util.template.PackageTextTemplate;
import org.apache.wicket.util.template.TextTemplate;
import org.wicketstuff.browserid.BrowserId.Status;

/**
 * The behavior that should be attached to the "Sign In" button. It cares about loading the
 * authentication window and notifying the caller via {@link #onSuccess(AjaxRequestTarget)} or
 * {@link #onFailure(AjaxRequestTarget, String)}
 */
public abstract class VerifyBehavior extends AbstractDefaultAjaxBehavior
{

	private static final long serialVersionUID = 1L;

	@Override
	public void renderHead(final Component component, final IHeaderResponse response)
	{
		component.setOutputMarkupId(true);
		super.renderHead(component, response);

		final Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("componentId", component.getMarkupId());
		variables.put("callbackUrl", getCallbackUrl());

		final TextTemplate verifyTemplate = new PackageTextTemplate(VerifyBehavior.class,
			"verify.js.tmpl");
		String asString = verifyTemplate.asString(variables);
		response.render(OnDomReadyHeaderItem.forScript(asString));
	}

	@Override
	protected void respond(AjaxRequestTarget target)
	{
		RequestCycle cycle = RequestCycle.get();
		Request request = cycle.getRequest();
		StringValue assertionParam = request.getQueryParameters().getParameterValue("assertion");
		StringValue audienceParam = request.getQueryParameters().getParameterValue("audience");

		if (assertionParam.isEmpty() == false && audienceParam.isEmpty() == false)
		{
			String failureReason = verify(assertionParam.toString(), audienceParam.toString());
			if (failureReason == null)
			{
				onSuccess(target);
			}
			else
			{
				onFailure(target, failureReason);
			}
		}
	}

	private String verify(final String assertion, final String audience)
	{
		String failureReason = null;
		try
		{
			URL verifyUrl = new URL("https://browserid.org/verify");
			URLConnection urlConnection = verifyUrl.openConnection();
			urlConnection.setDoOutput(true);
			OutputStream outputStream = urlConnection.getOutputStream();
			String postParams = "assertion=" + assertion + "&audience=" + audience;
			outputStream.write(postParams.getBytes());
			outputStream.close();

			String response = IOUtils.toString(urlConnection.getInputStream(), "UTF-8");

			BrowserId browserId = BrowserId.of(response);
			if (browserId != null)
			{
				if (Status.OK.equals(browserId.getStatus()))
				{
					SessionHelper.logIn(Session.get(), browserId);
				}
				else
				{
					failureReason = browserId.getReason();
				}
			}
		}
		catch (IOException e)
		{
			failureReason = e.getMessage();
		}

		return failureReason;
	}

	protected abstract void onSuccess(AjaxRequestTarget target);

	protected abstract void onFailure(AjaxRequestTarget target, String failureReason);

}
