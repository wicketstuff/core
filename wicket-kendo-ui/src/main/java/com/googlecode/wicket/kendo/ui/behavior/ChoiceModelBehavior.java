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
package com.googlecode.wicket.kendo.ui.behavior;

import java.util.List;

import org.apache.wicket.Application;
import org.apache.wicket.behavior.AbstractAjaxBehavior;
import org.apache.wicket.request.IRequestCycle;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.WebResponse;

import com.googlecode.wicket.jquery.core.renderer.ITextRenderer;

/**
 * Provides the choice ajax loading behavior
 *
 * @author Sebastien Briquet - sebfz1
 *
 * @param <T> the model object type
 */
public abstract class ChoiceModelBehavior<T> extends AbstractAjaxBehavior
{
	private static final long serialVersionUID = 1L;

	private final ITextRenderer<? super T> renderer;

	public ChoiceModelBehavior(ITextRenderer<? super T> renderer)
	{
		super();

		this.renderer = renderer;
	}

	@Override
	public void onRequest()
	{
		RequestCycle.get().scheduleRequestHandlerAfterCurrent(this.newRequestHandler());
	}

	/**
	 * Get a new {@link IRequestHandler}
	 * @return a new {@link IRequestHandler}
	 */
	protected IRequestHandler newRequestHandler()
	{
		return new IRequestHandler() {

			@Override
			public void respond(final IRequestCycle requestCycle)
			{
				WebResponse response = (WebResponse) requestCycle.getResponse();

				final String encoding = Application.get().getRequestCycleSettings().getResponseRequestEncoding();
				response.setContentType("application/json; charset=" + encoding);
				response.disableCaching();

				List<T> list = ChoiceModelBehavior.this.getChoices();

				if (list != null)
				{
					int count = 0;
					StringBuilder builder = new StringBuilder("[");

					for (T object : list)
					{
						if (count++ > 0)
						{
							builder.append(",");
						}

						builder.append(ChoiceModelBehavior.this.renderer.toJson(object));
					}

					builder.append("]");

					response.write(builder);
				}
			}

			@Override
			public void detach(final IRequestCycle requestCycle)
			{
				// noop
			}
		};
	}

	/**
	 * Get the {@link List} of choices
	 *
	 * @return the list of choices
	 */
	public abstract List<T> getChoices();
}
