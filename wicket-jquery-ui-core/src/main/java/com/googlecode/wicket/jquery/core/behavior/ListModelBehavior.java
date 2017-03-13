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

import java.util.List;

import org.apache.wicket.model.IModel;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.util.lang.Generics;
import com.github.openjson.JSONObject;

import com.googlecode.wicket.jquery.core.converter.IJsonConverter;
import com.googlecode.wicket.jquery.core.utils.JsonUtils;

/**
 * Provides the behavior that loads objects/beans as JSON<br>
 * <b>Note: </b> the {@code List} of objects will be converted in JSON using {@code new JSONArray(list)}
 * 
 * @author Sebastien Briquet - sebfz1
 *
 */
public class ListModelBehavior<T> extends AjaxCallbackBehavior
{
	private static final long serialVersionUID = 1L;

	private final IModel<List<T>> model;
	private final IJsonConverter<T> converter;

	/**
	 * Constructor
	 *
	 * @param model the {@code List} of objects
	 */
	public ListModelBehavior(final IModel<List<T>> model)
	{
		this(model, null);
	}

	/**
	 * Constructor
	 *
	 * @param model the {@code List} of objects
	 * @param converter the {@link IJsonConverter}
	 */
	public ListModelBehavior(final IModel<List<T>> model, IJsonConverter<T> converter)
	{
		this.model = model;
		this.converter = converter;
	}

	@Override
	protected String getResponse(IRequestParameters parameters)
	{
		List<T> list = this.model.getObject();

		if (this.converter != null)
		{
			List<JSONObject> objects = Generics.newArrayList();

			for (T object : list)
			{
				objects.add(this.converter.toJson(object));
			}

			return JsonUtils.toString(objects);
		}

		return JsonUtils.toString(list);
	}
}
