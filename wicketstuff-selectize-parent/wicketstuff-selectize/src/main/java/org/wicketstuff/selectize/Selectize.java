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

import java.io.InputStream;
import java.util.Collection;
import java.util.List;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.json.JSONArray;
import org.apache.wicket.ajax.json.JSONObject;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.io.IOUtils;
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
		try (InputStream inputStream = Selectize.class.getResourceAsStream("res/js/selectize_init.js"))
		{
			String string = new String(IOUtils.toByteArray(inputStream));
			JSONObject selectizeConfig = new JSONObject();
			selectizeConfig.put("selectizeMarkupId", getMarkupId());
			if (delimiter != null)
			{
				selectizeConfig.put("delimiter", delimiter);
			}
			if (getDefaultModelObject() instanceof List<?>)
			{
				List<?> options = (List<?>)getDefaultModelObject();
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
			String replace = string.replaceAll("%\\(selectizeConfig\\)", selectizeConfig.toString());
			response.render(OnDomReadyHeaderItem.forScript(replace));
		}
		catch (Exception e)
		{
			throw new WicketRuntimeException("Error while initializing the selectize script", e);
		}
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
}
