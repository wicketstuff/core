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
package com.googlecode.wicket.kendo.ui.repeater.dataview;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.Application;
import org.apache.wicket.behavior.AbstractAjaxBehavior;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.request.IRequestCycle;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.WebResponse;

import com.googlecode.wicket.jquery.core.renderer.ITextRenderer;
import com.googlecode.wicket.jquery.core.template.IJQueryTemplate;
import com.googlecode.wicket.jquery.core.utils.BuilderUtils;
import com.googlecode.wicket.jquery.core.utils.ListUtils;

/**
 * Provides the {@link IDataProvider} data source {@link AbstractAjaxBehavior}
 *
 * @param <T> the type of the model object
 * @author Sebastien Briquet - sebfz1
 */
public class DataProviderBehavior<T> extends AbstractAjaxBehavior
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
	public void onRequest()
	{
		final RequestCycle requestCycle = RequestCycle.get();
		final IRequestParameters parameters = requestCycle.getRequest().getQueryParameters();
		final int first = parameters.getParameterValue("skip").toInt(0);
		final int count = parameters.getParameterValue("take").toInt(Integer.MAX_VALUE);

		requestCycle.scheduleRequestHandlerAfterCurrent(this.newRequestHandler(first, count));
	}

	/**
	 * Gets the new {@link IRequestHandler} that will respond the data in a json format
	 *
	 * @param first the first row number
	 * @param count the count of rows
	 * @return a new {@code IRequestHandler}
	 */
	private IRequestHandler newRequestHandler(final int first, final int count)
	{
		return new DataProviderRequestHandler(first, count);
	}

	/**
	 * Provides the {@link IRequestHandler}
	 */
	protected class DataProviderRequestHandler implements IRequestHandler
	{
		private final int first;
		private final int count;

		public DataProviderRequestHandler(int first, int count)
		{
			this.first = first;
			this.count = count;
		}

		@Override
		public void respond(final IRequestCycle requestCycle)
		{
			WebResponse response = (WebResponse) requestCycle.getResponse();

			final String encoding = Application.get().getRequestCycleSettings().getResponseRequestEncoding();
			response.setContentType("application/json; charset=" + encoding);
			response.disableCaching();

			// final long size = provider.size();
			final Iterator<? extends T> iterator = provider.iterator(first, count);

			// builds JSON result //
			StringBuilder builder = new StringBuilder("[");

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
					builder.append(renderer.render(object));

					// Additional properties (like template properties) //
					List<String> properties = DataProviderBehavior.this.getProperties();

					for (String property : properties)
					{
						builder.append(", ");
						BuilderUtils.append(builder, property, renderer.getText(object, property));
					}

					builder.append(" }");
				}
			}

			builder.append(" ]");

			response.write(builder);
		}

		@Override
		public void detach(final IRequestCycle requestCycle)
		{
			provider.detach();
		}
	}
}
