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
import java.util.ArrayList;
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
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.http.WebRequest;
import org.apache.wicket.request.http.WebResponse;
import org.apache.wicket.util.string.Strings;
import org.wicketstuff.select2.json.JsonBuilder;

/**
 * Base class for Select2 components
 *
 * @author igor
 *
 * @param <T>
 *            type of choice object
 * @param <M>
 *            type of model object
 */
abstract class AbstractSelect2Choice<T, M> extends HiddenField<M> implements IResourceListener
{
	private static final long serialVersionUID = 1L;

	private final Settings settings = new Settings();

	private ChoiceProvider<T> provider;
	private List<T> choices;
	private IChoiceRenderer<T> renderer;

	private boolean convertInputPerformed = false;

	/**
	 * Constructor
	 *
	 * @param id
	 *            component id
	 */
	public AbstractSelect2Choice(String id)
	{
		this(id, null, null, null);
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
		this(id, model, null, null);
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
	 * Constructor
	 *
	 * @param id
	 *            component id
	 * @param model
	 *            component model
	 * @param provider
	 *            choice provider
	 */
	public AbstractSelect2Choice(String id, IModel<M> model, ChoiceProvider<T> provider)
	{
		super(id, model);
		this.provider = provider;
		add(new Select2ResourcesBehavior());
		setOutputMarkupId(true);
	}

	/**
	 * Construct.
	 *
	 * @param id
	 *            markup id
	 * @param model
	 *            model for select
	 * @param collection
	 *            list options for select
	 */
	public AbstractSelect2Choice(String id, IModel<M> model, List<T> collection)
	{
		this(id, model, collection, null);
	}

	/**
	 * @param id
	 * 		markup id
	 * @param choices
	 * 		list options for select
	 * @param renderer
	 * 		list item renderer
	 */
	public AbstractSelect2Choice(String id, List<T> choices, IChoiceRenderer<T> renderer)
	{
		this(id, null, choices, renderer);
	}

	/**
	 * Construct.
	 *
	 * @param id
	 *            markup id
	 * @param model
	 *            model for select
	 * @param choices
	 *            list options for select
	 * @param renderer
	 *            renderer list item
	 * @see HiddenField#HiddenField(String, IModel)
	 */
	public AbstractSelect2Choice(String id, IModel<M> model, List<T> choices, IChoiceRenderer<T> renderer)
	{
		super(id, model);
		if (null == choices)
		{
			throw new IllegalStateException("Select2 choice component: " + getId() +
					" does not have a List<T> set");
		}
		if (null == renderer)
		{
			throw new IllegalStateException("Select2 choice component: " + getId() +
					" does not have a WCLChoiceRenderer<T> set");
		}
		add(new Select2ResourcesBehavior());
		this.choices = new ArrayList<T>(choices);
		this.renderer = renderer;
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
	 */
	public final void setProvider(ChoiceProvider<T> provider)
	{
		this.provider = provider;
	}

	/**
	 * @return data
	 */
	public final List<T> getChoices()
	{
		if (choices == null)
		{
			throw new IllegalStateException("Select2 choice component: " + getId() +
					" does not have a choices set");
		}
		return choices;
	}

	public final void setChoices(List<T> choices)
	{
		this.choices = choices;
	}

	public final IChoiceRenderer<T> getRenderer()
	{
		if (renderer == null)
		{
			throw new IllegalStateException("Select2 choice component: " + getId() +
					" does not have a IChoiceRenderer set");
		}
		return renderer;
	}

	public final void setRenderer(IChoiceRenderer<T> renderer)
	{
		this.renderer = renderer;
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
	protected final void convertInput()
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
		if (isAjax())
		{
			return getProvider().toChoices(ids);
		}
		else
		{
			List<T> result = new ArrayList<T>(ids.size());
			for (int idx = 0; idx < getChoices().size(); idx++)
			{
				T choice = getChoices().get(idx);
				String idValue = getRenderer().getIdValue(choice, idx);
				if (idValue != null && ids.contains(idValue))
				{
					result.add(choice);
				}
			}
			return result;
		}
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
		if (!isAjax())
		{
			// attach select options
			renderChoices();
		}
		// initialize select2
		response.render(OnDomReadyHeaderItem.forScript(JQuery.execute("$('#%s').select2(%s);",
				getJquerySafeMarkupId(), getSettings().toJson())));
		// select current value
		renderInitializationScript(response);
	}

	/**
	 * Renders script used to initialize the value of Select2 after it is created so it matches the
	 * current model object.
	 *
	 * @param response
	 *            header response
	 */
	// TODO add <M> (getCurrentValue) to method's signature in 7.0
	protected abstract void renderInitializationScript(IHeaderResponse response);

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
			AjaxSettings ajax = settings.getAjax(true);
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

		Response<T> response = new Response<T>();
		getProvider().query(term, page, response);

		// jsonize and write out the choices to the response

		WebResponse webResponse = (WebResponse)getRequestCycle().getResponse();
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

	/**
	 * render single choice
	 *
	 * @param choice
	 *            choice
	 * @param jsonBuilder
	 *            json builder
	 */
	protected void renderChoice(T choice, JsonBuilder jsonBuilder) throws JSONException
	{
		String key = getRenderer().getIdValue(choice, getChoices().lastIndexOf(choice));
		Object value = getRenderer().getDisplayValue(choice);
		jsonBuilder.key("id").value(key).key("text").value(value);
	}

	// render options on select2
	private void renderChoices()
	{
		JsonBuilder selection = new JsonBuilder();
		try
		{
			selection.object();
			selection.key("more").value(false);
			selection.key("results").array();
			attachChoicesJson(selection);
			selection.endArray().endObject();
		}
		catch (JSONException e)
		{
			throw new RuntimeException("Error converting model object to Json", e);
		}

		getSettings().setData(selection.toJson().toString());
	}

	private void attachChoicesJson(JsonBuilder jsonBuilder) throws JSONException
	{
		for (T choice : choices)
		{
			jsonBuilder.object();
			renderChoice(choice, jsonBuilder);
			jsonBuilder.endObject();
		}
	}
}
