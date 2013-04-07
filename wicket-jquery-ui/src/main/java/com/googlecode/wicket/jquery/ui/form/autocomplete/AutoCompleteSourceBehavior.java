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

import java.util.Collections;
import java.util.List;

import org.apache.wicket.Application;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.request.IRequestCycle;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.WebResponse;

import com.googlecode.wicket.jquery.core.renderer.ITextRenderer;

/**
 * Provides the {@link AbstractDefaultAjaxBehavior} for the {@link AutoCompleteTextField}
 *
 * @author Sebastien Briquet - sebfz1
 *
 * @param <T> the type of the model object
 */
abstract class AutoCompleteSourceBehavior<T> extends AbstractDefaultAjaxBehavior
{
	private static final long serialVersionUID = 1L;
	private static final String QUERY = "term";
	private static final String QUOTE = "\"";

	private final ITextRenderer<? super T> renderer;

	public AutoCompleteSourceBehavior(ITextRenderer<? super T> renderer)
	{
		this.renderer = renderer;
	}

	/**
	 * Gets choices matching the provided input
	 * @param input String that represent the query
	 * @return the list of choices
	 */
	protected abstract List<T> getChoices(String input);

	/**
	 * Gets the property list that should be appended to the JSON response. The value corresponding to the property is retrieved from the {@link ITextRenderer#getText(Object, String)}
	 * @return the property list
	 */
	protected List<String> getProperties()
	{
		return Collections.emptyList();
	}


	@Override
	protected void respond(AjaxRequestTarget target)
	{
		final RequestCycle requestCycle = RequestCycle.get();
		final String value = requestCycle.getRequest().getQueryParameters().getParameterValue(QUERY).toString();

		final IRequestHandler handler = this.newRequestHandler(value);
		requestCycle.scheduleRequestHandlerAfterCurrent(handler);
	}

	/**
	 * Gets a new {@link IRequestHandler} that will call {@link #getChoices(String)} and will build be JSON response corresponding to the specified 'input' argument.
	 * @param input user input
	 * @return a new {@link IRequestHandler}
	 */
	private IRequestHandler newRequestHandler(final String input)
	{
		return new IRequestHandler()
		{
			@Override
			public void respond(final IRequestCycle requestCycle)
			{
				WebResponse response = (WebResponse)requestCycle.getResponse();

				final String encoding = Application.get().getRequestCycleSettings().getResponseRequestEncoding();
				response.setContentType("text/json; charset=" + encoding);
				response.disableCaching();

				List<T> choices = AutoCompleteSourceBehavior.this.getChoices(input);
				List<String> properties = AutoCompleteSourceBehavior.this.getProperties();

				if (choices != null)
				{
					StringBuilder builder = new StringBuilder("[ ");

					int index = 0;
					for (T choice : choices)
					{
						if (index++ > 0) { builder.append(", "); }

						builder.append("{ ");
						builder.append(QUOTE).append("id").append(QUOTE).append(": ").append(QUOTE).append(Integer.toString(index)).append(QUOTE); /* id is a reserved word */
						builder.append(", ");
						builder.append(QUOTE).append("value").append(QUOTE).append(": ").append(QUOTE).append(renderer.getText(choice)).append(QUOTE); /* value is a reserved word */

						if (properties != null)
						{
							for (String property : properties)
							{
								builder.append(", ");
								builder.append(QUOTE).append(property).append(QUOTE).append(": ").append(QUOTE).append(renderer.getText(choice, property)).append(QUOTE);
							}
						}

						builder.append(" }");
					}

					builder.append(" ]");

					response.write(builder);
				}
			}

			@Override
			public void detach(final IRequestCycle requestCycle)
			{
			}
		};
	}
}