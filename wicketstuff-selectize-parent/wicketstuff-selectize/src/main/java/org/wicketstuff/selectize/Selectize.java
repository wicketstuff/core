/**
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.wicketstuff.selectize;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.json.JSONArray;
import org.apache.wicket.ajax.json.JSONObject;
import org.apache.wicket.core.util.string.ComponentRenderer;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.IRequestCycle;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.WebResponse;
import org.apache.wicket.util.string.Strings;
import org.apache.wicket.util.template.PackageTextTemplate;
import org.wicketstuff.selectize.SelectizeCssResourceReference.Theme;

/**
 * Used to create a selectize.js choice component
 * 
 * @author Tobias Soloschenko
 *
 */
@SuppressWarnings("rawtypes")
public class Selectize extends FormComponent
{

	private static final long serialVersionUID = 1L;

	private Theme theme = Theme.NONE;

	private String delimiter = ",";

	private String placeholder;

	private IModel<Collection<SelectizeOptionGroup>> optionGroups;

	private SelectizeAjaxBehavior selectizeAjaxBehavior;

	public static final String SELECTIZE_COMPONENT_ID = "SELECTIZE_COMPONENT_ID";

	private class SelectizeAjaxBehavior extends AbstractDefaultAjaxBehavior
	{

		private static final long serialVersionUID = 1L;

		@Override
		protected void respond(AjaxRequestTarget target)
		{
			RequestCycle requestCycle = RequestCycle.get();
			final String search = requestCycle.getRequest()
				.getQueryParameters()
				.getParameterValue("search")
				.toString();
			requestCycle.scheduleRequestHandlerAfterCurrent(new IRequestHandler()
			{
				@Override
				public void respond(IRequestCycle requestCycle)
				{
					WebResponse response = (WebResponse)requestCycle.getResponse();
					response.setContentType("application/json");
					response.write(response(search).toString());
				}

				@Override
				public void detach(IRequestCycle requestCycle)
				{
					// NOOP
				}
			});
		}
	}

	public Selectize(String id)
	{
		this(id, null, null);
	}

	public Selectize(String id, IModel options)
	{
		this(id, null, options);
	}

	@SuppressWarnings("unchecked")
	public Selectize(String id, IModel<Collection<SelectizeOptionGroup>> optionGroups,
		IModel options)
	{
		super(id, options);
		setOutputMarkupPlaceholderTag(true);
		this.optionGroups = optionGroups;
	}

	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);
		response.render(JavaScriptHeaderItem.forReference(SelectizeJavaScriptResourceReference.instance()));
		response.render(CssHeaderItem.forReference(SelectizeCssResourceReference.instance(theme)));
		response.render(JavaScriptHeaderItem.forReference(HandlebarsJavaScriptResourceReference.instance()));
		try (PackageTextTemplate packageTextTemplate = new PackageTextTemplate(Selectize.class,
			"res/js/selectize_init.js"))
		{

			JSONObject selectizeConfig = new JSONObject();
			selectizeConfig.put("selectizeMarkupId", getMarkupId());
			if (delimiter != null)
			{
				selectizeConfig.put("delimiter", delimiter);
			}
			Object defaultModelObject = getDefaultModelObject();

			// Options to be shown if provided
			if (defaultModelObject instanceof List<?>)
			{
				List<?> options = (List<?>)defaultModelObject;
				JSONArray array = new JSONArray();
				for (Object option : options)
				{
					if (option instanceof SelectizeOption)
					{
						array.put(option);
					}
					else
					{
						throw new WicketRuntimeException(
							"If the options are given as list, please use " +
								SelectizeOption.class.getName() + " for each object.");
					}
				}
				selectizeConfig.put("optionsToAdd", array);
			}

			// If the user is allowed to create items
			if (defaultModelObject != null && !(defaultModelObject instanceof List<?>))
			{
				selectizeConfig.put("createAvailable", true);
			}

			// Option Groups to be shown if provided
			if (optionGroups != null)
			{
				selectizeConfig.put("optgroupField", SelectizeOptionGroup.OPT_GROUP_FIELD);
				selectizeConfig.put("optgroupLabelField",
					SelectizeOptionGroup.OPT_GROUP_LABEL_FIELD);

				JSONArray optionGroupsArray = new JSONArray();
				for (SelectizeOptionGroup optionGroup : optionGroups.getObject())
				{
					optionGroupsArray.put(optionGroup);
				}
				selectizeConfig.put("optgroupsToAdd", optionGroupsArray);
			}

			// If Ajax is enabled, provide more information to the context like wicket base url and
			// callback url
			if (selectizeAjaxBehavior != null)
			{
				addAjaxBaseUrl(response);
				selectizeConfig.put("ajaxCallback", selectizeAjaxBehavior.getCallbackUrl()
					.toString());

				Panel responseTemplate = responseTemplate();
				if (responseTemplate == null)
				{
					throw new WicketRuntimeException(
						"A template which is going to be used to display items has to be provided!");
				}
				selectizeConfig.put("ajaxTemplate",
					ComponentRenderer.renderComponent(responseTemplate));
			}

			Map<String, String> variablesMap = new HashMap<String, String>();
			variablesMap.put("selectizeConfig", selectizeConfig.toString());
			response.render(OnDomReadyHeaderItem.forScript(packageTextTemplate.asString(variablesMap)));
		}
		catch (Exception e)
		{
			throw new WicketRuntimeException("Error while initializing the selectize script", e);
		}
	}

	private void addAjaxBaseUrl(IHeaderResponse response)
	{
		// render ajax base url. It's required by Wicket Ajax support.
		Url baseUrl = RequestCycle.get().getUrlRenderer().getBaseUrl();
		CharSequence ajaxBaseUrl = Strings.escapeMarkup(baseUrl.toString());
		response.render(JavaScriptHeaderItem.forScript("Wicket.Ajax.baseUrl=\"" + ajaxBaseUrl +
			"\";", "wicket-ajax-base-url"));

	}

	@Override
	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);
		if (placeholder != null)
		{
			tag.put("placeholder", placeholder);
		}
		Object defaultModelObject = getDefaultModelObject();
		if (defaultModelObject != null && !(defaultModelObject instanceof List<?>))
		{
			tag.put("value", getDefaultModelObjectAsString());
		}
	}

	/**
	 * Sets the delimiter (default is ",")
	 * 
	 * @param delimiter
	 *            the delimiter to be used
	 */
	public void setDelimiter(String delimiter)
	{
		this.delimiter = delimiter;
	}

	/**
	 * Sets the theme of the selectize component
	 * 
	 * @param theme
	 *            the theme
	 */
	public void setTheme(Theme theme)
	{
		this.theme = theme;
	}

	/**
	 * Sets the placeholder
	 * 
	 * @param placeholder
	 *            the placeholder to be used
	 */
	public void setPlaceholder(String placeholder)
	{
		this.placeholder = placeholder;
	}

	/**
	 * Used to enable AJAX handling. Please override the following methods to provide data<br>
	 * <br>
	 * 
	 * Method for response content: {@link #response(String)}<br>
	 * Method for template how to display: TODO
	 * 
	 */
	public void enableAjaxHandling()
	{
		add(this.selectizeAjaxBehavior = new SelectizeAjaxBehavior());
	}

	/**
	 * Provides the response and data for AJAX calls
	 * 
	 * @param search
	 *            the search query parameter the user typed into the text field
	 * @return the selectize response with a list of {@link SelectizeOption}'s
	 */
	protected SelectizeResponse response(String search)
	{
		return new SelectizeResponse(Collections.<SelectizeOption> emptyList());
	}

	/**
	 * Provides the template to be shown in the select result<br>
	 * <br>
	 * <b>Important:</b> The item ({@link SelectizeOption}) to refer to is named <b>item</b>!
	 * 
	 * @return the Panel to be rendered in the template
	 */
	protected Panel responseTemplate()
	{
		return null;
	}
}
