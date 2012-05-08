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

import org.apache.wicket.Application;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxCallDecorator;
import org.apache.wicket.ajax.calldecorator.AjaxCallThrottlingDecorator;
import org.apache.wicket.request.IRequestCycle;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.WebResponse;
import org.apache.wicket.util.time.Duration;

/**
 * Provides the {@link AbstractDefaultAjaxBehavior} for the {@link AutoCompleteTextField}
 * 
 * @author Sebastien Briquet - sebastien@7thweb.net
 *
 * @param <T> the type of the model object
 */
abstract class AutoCompleteBehavior<T> extends AbstractDefaultAjaxBehavior
{
	private static final long serialVersionUID = 1L;
	private static final String QUERY = "term";

	protected abstract List<T> getChoices(String input);
	
	@Override
	protected void respond(AjaxRequestTarget target)
	{
		final RequestCycle requestCycle = RequestCycle.get();
		final String value = requestCycle.getRequest().getQueryParameters().getParameterValue(QUERY).toString();
		
		final IRequestHandler handler = this.newRequestHandler(value);
		requestCycle.scheduleRequestHandlerAfterCurrent(handler);
	}

	@Override
	protected IAjaxCallDecorator getAjaxCallDecorator()
	{
		return new AjaxCallThrottlingDecorator("throttle", Duration.ONE_SECOND);
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
			public void respond(final IRequestCycle requestCycle)
			{
				WebResponse response = (WebResponse)requestCycle.getResponse();

				final String encoding = Application.get().getRequestCycleSettings().getResponseRequestEncoding();
				response.setContentType("text/json; charset=" + encoding);
				response.disableCaching();

				List<T> choices = AutoCompleteBehavior.this.getChoices(input);
				
				if (choices != null)
				{
					StringBuilder builder = new StringBuilder("[ ");
				
					int index = 0;
					for (T choice : choices)
					{
						if (index++ > 0) { builder.append(", "); }
						
						builder.append("{ ");
						builder.append("\"id\": \"").append(String.valueOf(index)).append("\", ");
//						builder.append("\"label\": \"").append(choice.toString()).append("\", ");
						builder.append("\"value\": \"").append(choice.toString()).append("\"");
						builder.append(" }");
					}

					builder.append(" ]");

					response.write(builder);
				}
			}

			public void detach(final IRequestCycle requestCycle)
			{
			}
		};
	}
}