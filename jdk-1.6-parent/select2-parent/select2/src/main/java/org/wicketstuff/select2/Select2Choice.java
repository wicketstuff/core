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

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.ajax.json.JSONException;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.convert.ConversionException;
import org.wicketstuff.select2.json.JsonBuilder;

/**
 * Single-select Select2 component. Should be attached to a {@code <input type='hidden'/>} element.
 * 
 * @author igor
 * 
 * @param <T>
 *            type of choice object
 */
public class Select2Choice<T> extends AbstractSelect2Choice<T, T>
{
	private static final long serialVersionUID = 1L;

	public Select2Choice(String id, IModel<T> model, ChoiceProvider<T> provider)
	{
		super(id, model, provider);
	}

	public Select2Choice(String id, ChoiceProvider<T> provider)
	{
		super(id, provider);
	}

	// will be dropped in 7.0
	@Deprecated
	public Select2Choice(String id, IModel<T> model)
	{
		super(id, model);
	}

	// will be dropped in 7.0
	@Deprecated
	public Select2Choice(String id)
	{
		super(id);
	}

	@Override
	protected final void convertInput()
	{
		// Select2Choice uses ChoiceProvider to convert IDS into objects.
		// The #getConverter() method is not supported by Select2Choice.
		setConvertedInput(convertValue(getInputAsArray()));
	}

	@Override
	protected final T convertValue(String[] value) throws ConversionException
	{
		if (value != null && value.length > 0)
		{
			List<String> ids = Collections.singletonList(value[0]);
			Iterator<T> iterator = getProvider().toChoices(ids).iterator();
			return iterator.hasNext() ? iterator.next() : null;
		}
		else
		{
			return null;
		}
	}

	@Override
	protected void renderInitializationScript(IHeaderResponse response)
	{
		// hasRawInput() == true indicates, that form was submitted with errors.
		// It means model was not updated yet.
		T value = hasRawInput() ? getConvertedInput() : getModelObject();
		if (value != null)
		{
			JsonBuilder selection = new JsonBuilder();
			try
			{
				selection.object();
				getProvider().toJson(value, selection);
				selection.endObject();
			}
			catch (JSONException e)
			{
				throw new RuntimeException("Error converting model object to Json", e);
			}
			response.render(OnDomReadyHeaderItem.forScript(JQuery.execute(
				"$('#%s').select2('data', %s);", getJquerySafeMarkupId(), selection.toJson())));
		}
	}
}
