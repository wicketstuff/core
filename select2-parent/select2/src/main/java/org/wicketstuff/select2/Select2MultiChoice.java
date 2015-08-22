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

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.apache.wicket.ajax.json.JSONException;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.convert.ConversionException;
import org.apache.wicket.util.string.Strings;
import org.wicketstuff.select2.json.JsonBuilder;
import org.wicketstuff.select2.util.ValueSplitter;

/**
 * Multi-select Select2 component. Should be attached to a {@code <input type='hidden'/>} element.
 *
 * @param <T>
 *            type of choice object
 * @author igor
 */
public class Select2MultiChoice<T> extends AbstractSelect2Choice<T, Collection<T>>
{

	private static final long serialVersionUID = 1L;

	private static final String[] EMPTY_STRING_ARRAY = new String[0];

	private static final String EMPTY_STRING = "";

	public Select2MultiChoice(String id)
	{
		super(id);
	}

	public Select2MultiChoice(String id, IModel<Collection<T>> model)
	{
		super(id, model);
	}

	public Select2MultiChoice(String id, IModel<Collection<T>> model, ChoiceProvider<T> provider)
	{
		super(id, model, provider);
	}

	public Select2MultiChoice(String id, ChoiceProvider<T> provider)
	{
		super(id, provider);
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
			return ValueSplitter.split(value);
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
	protected void renderInitializationScript(IHeaderResponse response, Collection<T> choices)
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
