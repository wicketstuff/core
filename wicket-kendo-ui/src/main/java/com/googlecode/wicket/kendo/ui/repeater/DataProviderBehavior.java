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
package com.googlecode.wicket.kendo.ui.repeater;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.request.IRequestParameters;

import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.behavior.AjaxCallbackBehavior;
import com.googlecode.wicket.jquery.core.renderer.ITextRenderer;
import com.googlecode.wicket.jquery.core.template.IJQueryTemplate;
import com.googlecode.wicket.jquery.core.utils.BuilderUtils;
import com.googlecode.wicket.jquery.core.utils.ListUtils;

/**
 * Provides the {@link IDataProvider} data source {@link AjaxCallbackBehavior}
 *
 * @param <T> the type of the model object
 * @author Sebastien Briquet - sebfz1
 */
public class DataProviderBehavior<T> extends AjaxCallbackBehavior
{
	private static final long serialVersionUID = 1L;

	private final IDataProvider<T> provider;
	private final ITextRenderer<? super T> renderer;
	private final IJQueryTemplate template;

	/**
	 * Constructor
	 *
	 * @param provider the {@link IDataProvider}
	 */
	public DataProviderBehavior(final IDataProvider<T> provider, ITextRenderer<? super T> renderer)
	{
		this(provider, renderer, null);
	}

	public DataProviderBehavior(final IDataProvider<T> provider, ITextRenderer<? super T> renderer, IJQueryTemplate template)
	{
		this.provider = provider;
		this.renderer = renderer;
		this.template = template;
	}

	/**
	 * Gets the property set that should be appended to the JSON response.<br/>
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
		final int first = parameters.getParameterValue("skip").toInt(0);
		final int count = parameters.getParameterValue("take").toInt(Short.MAX_VALUE);

		final long size = this.provider.size();
		final Iterator<? extends T> iterator = this.provider.iterator(first, count);

		// builds JSON result //
		StringBuilder builder = new StringBuilder();
		builder.append("{ ");
		BuilderUtils.append(builder, "__count", size);
		builder.append(", ");
		builder.append(Options.QUOTE).append("results").append(Options.QUOTE).append(": ");
		builder.append("[ ");

		if (iterator != null)
		{
			for (int index = 0; iterator.hasNext(); index++)
			{
				T object = iterator.next();

				if (index > 0)
				{
					builder.append(", ");
				}

				builder.append("{ ");

				// ITextRenderer //
				builder.append(this.renderer.render(object));

				// Additional properties (like template properties) //
				for (String property : this.getProperties())
				{
					builder.append(", ");
					BuilderUtils.append(builder, property, this.renderer.getText(object, property));
				}

				builder.append(" }");
			}
		}

		builder.append(" ] }");

		return builder.toString();
	}

	@Override
	public void detach(Component component)
	{
		super.detach(component);

		this.provider.detach();
	}
}
