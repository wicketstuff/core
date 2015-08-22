/*
 * Copyright 2012 Igor Vaynberg
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this work except in compliance with
 * the License. You may obtain a copy of the License in the LICENSE file, or at:
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.wicketstuff.select2;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.wicket.IResourceListener;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.json.JSONException;
import org.apache.wicket.ajax.json.JSONWriter;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.HiddenField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.http.WebRequest;
import org.apache.wicket.request.http.WebResponse;
import org.apache.wicket.util.string.Strings;

/**
 * Base class for Select2 components
 *
 * @param <T>
 *            type of choice object
 * @param <M>
 *            type of model object
 * @author igor
 */
abstract class AbstractSelect2Choice<T, M> extends HiddenField<M> implements IResourceListener
{

	private static final long serialVersionUID = 1L;

	private final Settings settings = new Settings();

	private ChoiceProvider<T> provider;

	private boolean convertInputPerformed = false;


	/**
	 * @param id
	 * 		markup id
	 */
	public AbstractSelect2Choice(String id)
	{
		this(id, null, null);
	}

	/**
	 * Constructor
	 *
	 * @param id
	 *            component id
	 * @param model
	 *            component model
	 */
	public AbstractSelect2Choice(String id, IModel<M> model)
	{
		this(id, model, null);
	}


	/**
	 * Constructor.
	 *
	 * @param id
	 *            component id
	 * @param provider
	 *            choice provider
	 */
	public AbstractSelect2Choice(String id, ChoiceProvider<T> provider)
	{
		this(id, null, provider);
	}

	/**
	 * Construct.
	 *
	 * @param id
	 *            markup id
	 * @param model
	 *            model for select
	 *
	 * @see HiddenField#HiddenField(String, IModel)
	 */
	public AbstractSelect2Choice(String id, IModel<M> model, ChoiceProvider<T> provider)
	{
		super(id, model);
		this.provider = provider;
		add(new Select2ResourcesBehavior());
		setOutputMarkupId(true);
	}

	/**
	 * @return Select2 settings for this component
	 */
	public final Settings getSettings()
	{
		return settings;
	}

	/**
	 * Sets the choice provider
	 *
	 * @param provider
	 *            provider to set
	 */
	public final void setProvider(ChoiceProvider<T> provider)
	{
		this.provider = provider;
	}

	/**
	 * @return choice provider
	 */
	public final ChoiceProvider<T> getProvider()
	{
		if (provider == null)
		{
			throw new IllegalStateException("Select2 choice component: " + getId() +
					" does not have a ChoiceProvider set");
		}
		return provider;
	}

	@Override
	public final void convertInput()
	{
		// AbstractSelect2Choice uses ChoiceProvider to convert IDS into objects.
		// The #getConverter() method is not supported by Select2Choice.
		setConvertedInput(convertValue(getInputAsArray()));
		convertInputPerformed = true;
	}

	/**
	 * Convert IDS into choices.
	 *
	 * @param ids
	 * 		list of identities
	 * @return collection of choices or empty collection
	 */
	protected final Collection<T> convertIdsToChoices(List<String> ids)
	{
		if (ids == null || ids.isEmpty())
		{
			return Collections.emptyList();
		}
        return getProvider().toChoices(ids);
	}

	/**
	 * Gets the markup id that is safe to use in jQuery by escaping dots in the default
	 * {@link #getMarkup()}
	 *
	 * @return markup id
	 */
	protected String getJquerySafeMarkupId()
	{
		return getMarkupId().replace(".", "\\\\.");
	}

	/**
	 * Escapes single quotes in localized strings to be used as JavaScript strings enclosed in
	 * single quotes
	 *
	 * @param key
	 *            resource key for localized message
	 * @return localized string with escaped single quotes
	 */
	protected String getEscapedJsString(String key)
	{
		String value = getString(key);

		return Strings.replaceAll(value, "'", "\\'").toString();
	}

	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);

		// initialize select2
		response.render(OnDomReadyHeaderItem.forScript(JQuery.execute("$('#%s').select2(%s);",
				getJquerySafeMarkupId(), getSettings().toJson())));
		M currentValue = getCurrentValue();
		if (canInitializeInitialValue(currentValue))
		{
			// select current value
			renderInitializationScript(response, currentValue);
		}
	}

	/**
	 * Renders script used to initialize the value of Select2 after it is created so it matches the
	 * current model object.
	 *
	 * @param response
	 * 		header response
	 * @param value
	 * 		value to display
	 */
	protected abstract void renderInitializationScript(IHeaderResponse response, M value);

	/**
	 * @return current value, suitable for rendering as selected value in select2 component
	 */
	protected final M getCurrentValue()
	{
		if (hasRawInput())
		{
			// since raw input is retained, we need explicitly convert it to target value
			if (convertInputPerformed)
			{
				return getConvertedInput();
			}
			else
			{
				String raw = getRawInput();
				return raw == null ? null : convertValue(raw.split(FormComponent.VALUE_SEPARATOR));
			}
		}
		else
		{
			return getModelObject();
		}
	}

	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		// configure the ajax callbacks
		if (isAjax())
		{
			AjaxSettings ajax = getSettings().getAjax(true);
			ajax.setData(String.format(
					"function(term, page) { return { term: term, page:page, '%s':true, '%s':[window.location.protocol, '//', window.location.host, window.location.pathname].join('')}; }",
					WebRequest.PARAM_AJAX, WebRequest.PARAM_AJAX_BASE_URL));
			ajax.setResults("function(data, page) { return data; }");
		}
		// configure the localized strings/renderers
		getSettings().setFormatNoMatches(
				"function() { return '" + getEscapedJsString("noMatches") + "';}");
		getSettings().setFormatInputTooShort(
				"function(input, min) { return min - input.length == 1 ? '" +
						getEscapedJsString("inputTooShortSingular") + "' : '" +
						getEscapedJsString("inputTooShortPlural") +
						"'.replace('{number}', min - input.length); }");
		getSettings().setFormatSelectionTooBig(
				"function(limit) { return limit == 1 ? '" +
						getEscapedJsString("selectionTooBigSingular") + "' : '" +
						getEscapedJsString("selectionTooBigPlural") + "'.replace('{limit}', limit); }");
		getSettings().setFormatLoadMore(
				"function() { return '" + getEscapedJsString("loadMore") + "';}");
		getSettings().setFormatSearching(
                "function() { return '" + getEscapedJsString("searching") + "';}");
	}

	@Override
	protected void onConfigure()
	{
		super.onConfigure();
		if (isAjax())
		{
			getSettings().getAjax().setUrl(urlFor(IResourceListener.INTERFACE, null));
		}
	}

	@Override
	public void onEvent(IEvent<?> event)
	{
		super.onEvent(event);
		if (event.getPayload() instanceof AjaxRequestTarget)
		{
			AjaxRequestTarget target = (AjaxRequestTarget)event.getPayload();
			if (target.getComponents().contains(this))
			{

				// if this component is being repainted by ajax, directly, we
				// must destroy Select2 so it removes
				// its elements from DOM
				target.prependJavaScript(JQuery.execute("$('#%s').select2('destroy');",
						getJquerySafeMarkupId()));
			}
		}
	}

	@Override
	protected boolean getStatelessHint()
	{
		return !isAjax();
	}

	public boolean isAjax()
	{
		return provider != null;
	}

	@Override
	public void onResourceRequested()
	{

		// this is the callback that retrieves matching choices used to populate
		// the dropdown

		Request request = getRequestCycle().getRequest();
		IRequestParameters params = request.getRequestParameters();

		// retrieve choices matching the search term

		String term = params.getParameterValue("term").toOptionalString();

		int page = params.getParameterValue("page").toInt(1);
		// select2 uses 1-based paging, but in wicket world we are used to
		// 0-based
		page -= 1;

		Response<T> response = new Response<>();
		getProvider().query(term, page, response);

		// jsonize and write out the choices to the response

		WebResponse webResponse = (WebResponse) getRequestCycle().getResponse();
		webResponse.setContentType("application/json");

		OutputStreamWriter out = new OutputStreamWriter(webResponse.getOutputStream(),
				getRequest().getCharset());
		JSONWriter json = new JSONWriter(out);

		try
		{
			json.object();
			json.key("results").array();
			for (T item : response)
			{
				json.object();
				getProvider().toJson(item, json);
				json.endObject();
			}
			json.endArray();
			json.key("more").value(response.getHasMore()).endObject();
		}
		catch (JSONException e)
		{
			throw new RuntimeException("Could not write Json response", e);
		}

		try
		{
			out.flush();
		}
		catch (IOException e)
		{
			throw new RuntimeException("Could not write Json to servlet response", e);
		}
	}

	@Override
	protected void onDetach()
	{
		convertInputPerformed = false;
		if (isAjax())
		{
			getProvider().detach();
		}
		super.onDetach();
	}

	private static boolean canInitializeInitialValue(Object value)
	{
        if (value instanceof Collection)
        {
            return !((Collection) value).isEmpty();
        } else
        {
            return value != null;
        }
    }

}
