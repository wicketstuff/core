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
import java.util.List;

import org.apache.wicket.ajax.json.JSONException;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.IModel;
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

	public Select2MultiChoice(String id, IModel<Collection<T>> model, ChoiceProvider<T> provider)
	{
		super(id, model, provider);
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
	public void convertInput()
	{
		String input = getWebRequest().getRequestParameters()
			.getParameterValue(getInputName())
			.toString();
		final Collection<T> choices;
		if (Strings.isEmpty(input))
		{
			choices = new ArrayList<T>();
		}
		else
		{
			List<String> ids = splitInput(input);
			if (isAjax())
			{
				choices = getProvider().toChoices(ids);
			}
			else
			{
				choices = new ArrayList<T>();
				List<T> predefinedChoices = getChoices();
				for (int i = 0; i < predefinedChoices.size(); i++)
				{
					T item = predefinedChoices.get(i);
					for (String id : ids)
					{
						if (id.equals(getRenderer().getIdValue(item, i)))
						{
							choices.add(item);
							if (choices.size() == ids.size())
							{
								break;
							}
						}
					}
				}
			}
		}
		setConvertedInput(choices);
	}

	@Override
	public void updateModel()
	{
		FormComponent.updateCollectionModel(this);
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
			return "";
		}

		return super.getModelValue();
	}

	@Override
	protected void renderInitializationScript(IHeaderResponse response)
	{
		Collection<? extends T> choices;
		if (!isValid() && hasRawInput())
		{
			convertInput();
			choices = getConvertedInput();
		}
		else
		{
			choices = getModelObject();
		}

		if (choices != null && !choices.isEmpty())
		{

			JsonBuilder selection = new JsonBuilder();

			try
			{
				selection.array();
				for (T choice : choices)
				{
					selection.object();
					getProvider().toJson(choice, selection);
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

	static List<String> splitInput(String input)
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
					if (openBracket == 0)
					{
						String substring = input.substring(lastStartIdx, i + 1);
						result.add(substring);
						lastStartIdx = null;
					}
				}
			}

			return result;
		}

		return Arrays.asList(input.split(","));
	}
}
