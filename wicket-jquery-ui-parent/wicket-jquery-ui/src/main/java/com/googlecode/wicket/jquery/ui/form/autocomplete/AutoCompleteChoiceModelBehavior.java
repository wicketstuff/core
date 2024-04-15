/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.googlecode.wicket.jquery.ui.form.autocomplete;

import java.util.List;

import org.apache.wicket.behavior.AbstractAjaxBehavior;
import org.apache.wicket.request.IRequestParameters;

import com.github.openjson.JSONArray;
import com.github.openjson.JSONObject;
import com.googlecode.wicket.jquery.core.behavior.ChoiceModelBehavior;
import com.googlecode.wicket.jquery.core.renderer.ITextRenderer;
import com.googlecode.wicket.jquery.core.template.IJQueryTemplate;

/**
 * Provides the {@link AbstractAjaxBehavior} for the {@link AutoCompleteTextField}
 *
 * @param <T> the model object type
 * @author Sebastien Briquet - sebfz1
 */
abstract class AutoCompleteChoiceModelBehavior<T> extends ChoiceModelBehavior<T>
{
	private static final long serialVersionUID = 1L;

	public AutoCompleteChoiceModelBehavior(ITextRenderer<? super T> renderer)
	{
		super(renderer);
	}

	public AutoCompleteChoiceModelBehavior(ITextRenderer<? super T> renderer, IJQueryTemplate template)
	{
		super(renderer, template);
	}

	@Override
	protected String getResponse(IRequestParameters parameters)
	{
		final JSONArray payload = new JSONArray();
		final List<T> choices = this.getChoices();

		if (choices != null)
		{
			for (int index = 0; index < choices.size(); ++index)
			{
				final T choice = choices.get(index);

				// ITextRenderer //
				final JSONObject object = this.renderer.render(choice);
				object.put("id", Integer.toString(index)); /* 'id' is a reserved word */
				object.put("value", this.renderer.getText(choice)); /* 'value' is a reserved word */

				// Additional properties (like template properties) //
				List<String> properties = AutoCompleteChoiceModelBehavior.this.getProperties();

				for (String property : properties)
				{
					object.put(property, this.renderer.getText(choice, property));
				}

				payload.put(object);
			}
		}

		return payload.toString();
	}
}
