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
package com.googlecode.wicket.jquery.core.behavior;

import java.util.Collections;
import java.util.List;

import org.apache.wicket.request.IRequestParameters;

import com.github.openjson.JSONArray;
import com.github.openjson.JSONObject;
import com.googlecode.wicket.jquery.core.data.IChoiceProvider;
import com.googlecode.wicket.jquery.core.renderer.ITextRenderer;
import com.googlecode.wicket.jquery.core.template.IJQueryTemplate;
import com.googlecode.wicket.jquery.core.utils.ListUtils;

/**
 * Provides the choice {@link AjaxCallbackBehavior}
 *
 * @author Sebastien Briquet - sebfz1
 *
 * @param <T> the model object type
 */
public abstract class ChoiceModelBehavior<T> extends AjaxCallbackBehavior implements IChoiceProvider<T>
{
	private static final long serialVersionUID = 1L;
	
	/** url param for server-side filtering */
	protected static final String FILTER_VALUE = "filter[filters][0][value]";

	protected final ITextRenderer<? super T> renderer;
	protected final IJQueryTemplate template;

	/**
	 * Constructor
	 * 
	 * @param renderer the {@link ITextRenderer}
	 */
	protected ChoiceModelBehavior(ITextRenderer<? super T> renderer)
	{
		this(renderer, null);
	}

	// Methods //

	/**
	 * Constructor
	 * 
	 * @param renderer the {@link ITextRenderer}
	 * @param template the {@link IJQueryTemplate}
	 */
	protected ChoiceModelBehavior(ITextRenderer<? super T> renderer, IJQueryTemplate template)
	{
		this.renderer = renderer;
		this.template = template;
	}

	/**
	 * Gets the property list that should be appended to the JSON response.<br>
	 * The value corresponding to the property is retrieved from the {@link ITextRenderer#getText(Object, String)}
	 *
	 * @return the property list
	 */
	protected List<String> getProperties()
	{
		if (this.template != null)
		{
			return ListUtils.exclude(this.template.getTextProperties(), this.renderer.getFields()); // #198
		}

		return Collections.emptyList();
	}

	@Override
	protected String getResponse(IRequestParameters parameters)
	{
		final JSONArray payload = new JSONArray();
		final List<T> choices = this.getChoices();

		if (choices != null)
		{
			for (T choice : choices)
			{
				// ITextRenderer //
				final JSONObject object = this.renderer.render(choice);				

				// Additional properties (like template properties) //
				List<String> properties = this.getProperties();

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
