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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.wicket.ajax.json.JSONException;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.convert.ConversionException;
import org.apache.wicket.util.string.Strings;
import org.wicketstuff.select2.json.JsonBuilder;

/**
 * Multi-select Select2 component. Should be attached to a {@code <input type='hidden'/>} element.
 * 
 * @author igor
 * 
 * @param <T>
 *            type of choice object
 */
public class Select2MultiChoice<T> extends AbstractSelect2Choice<T, Collection<T>>
{
	private static final long serialVersionUID = 1L;

	private static final String[] EMPTY_STRING_ARRAY = new String[0];

	private static final String EMPTY_STRING = "";

	public Select2MultiChoice(String id, IModel<Collection<T>> model, ChoiceProvider<T> provider)
	{
		super(id, model, provider);
	}

	public Select2MultiChoice(String id, List<T> choices, IChoiceRenderer<T> renderer)
	{
		super(id, null, choices, renderer);
	}

	public Select2MultiChoice(String id, IModel<Collection<T>> model, List<T> choices, IChoiceRenderer<T> renderer)
	{
		super(id, model, choices, renderer);
	}

	public Select2MultiChoice(String id, IModel<Collection<T>> model)
	{
		super(id, model);
	}

	public Select2MultiChoice(String id)
	{
		super(id);
	}

	@Override
	public final String[] getInputAsArray()
	{
		String value = getRequest().getRequestParameters().getParameterValue(getInputName()).toString();
		if (Strings.isEmpty(value))
		{
			return EMPTY_STRING_ARRAY;
		}
		else
		{
			return splitInput(value);
		}
	}

	@Override
	public void updateModel()
	{
		FormComponent.updateCollectionModel(this);
	}

	@Override
	protected final Collection<T> convertValue(String[] value) throws ConversionException
	{
		if (value != null && value.length > 0)
		{
			return convertIdsToChoices(Arrays.asList(value));
		}
		else
		{
			return Collections.emptyList();
		}
	}

	@Override
	protected void onInitialize()
	{
		super.onInitialize();
		getSettings().setMultiple(true);
	}

	@Override
	protected String getModelValue()
	{
		Collection<T> values = getModelObject();
		// if values is null or empty set value attribute to an empty string
		// rather then '[]' which does not make sense
		if (values == null || values.isEmpty())
		{
			return EMPTY_STRING;
		}
		return super.getModelValue();
	}

	@Override
	protected void renderInitializationScript(IHeaderResponse response)
	{
		Collection<? extends T> choices = getCurrentValue();
		if (choices != null && !choices.isEmpty())
		{
			JsonBuilder selection = new JsonBuilder();
			try
			{
				selection.array();
				for (T choice : choices)
				{
					selection.object();
					if (isAjax())
					{
						getProvider().toJson(choice, selection);
					}
					else
					{
						renderChoice(choice, selection);
					}
					selection.endObject();
				}
				selection.endArray();
			}
			catch (JSONException e)
			{
				throw new RuntimeException("Error converting model object to Json", e);
			}
			response.render(OnDomReadyHeaderItem.forScript(JQuery.execute(
				"$('#%s').select2('data', %s);", getJquerySafeMarkupId(), selection.toJson())));
		}
	}

	private static String[] splitInput(String input)
	{
		if (input.startsWith("{") && input.endsWith("}"))
		{
			// Assume we're using JSON IDs
			List<String> result = new ArrayList<String>();

			int openBracket = 0;
			Integer lastStartIdx = null;
			for (int i = 0; i < input.length(); i++)
			{
				char c = input.charAt(i);
				if (c == '{')
				{
					openBracket++;
					if (lastStartIdx == null)
					{
						lastStartIdx = i;
					}
				}
				if (c == '}')
				{
					openBracket--;
					if (openBracket == 0 && lastStartIdx != null)
					{
						String substring = input.substring(lastStartIdx, i + 1);
						result.add(substring);
						lastStartIdx = null;
					}
				}
			}
			return result.toArray(new String[result.size()]);
		}
		return Strings.split(input, ','); // select2 uses comma as value separator
	}
}
