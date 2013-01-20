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
package com.googlecode.wicket.jquery.ui.calendar;

import java.util.Date;
import java.util.List;

import org.apache.wicket.Application;
import org.apache.wicket.behavior.AbstractAjaxBehavior;
import org.apache.wicket.request.IRequestCycle;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.WebResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides the behavior that gets the {@link CalendarEvent}<code>s</code> from the {@link CalendarModel}
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
class CalendarModelBehavior extends AbstractAjaxBehavior
{
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(CalendarModelBehavior.class);

	private CalendarModel model;

	/**
	 * Constructor
	 *
	 * @param model the {@link CalendarModel}
	 */
	public CalendarModelBehavior(CalendarModel model)
	{
		this.model = model;
	}

	@Override
	public void onRequest()
	{
		final RequestCycle requestCycle = RequestCycle.get();
		IRequestParameters parameters = requestCycle.getRequest().getQueryParameters();

		final long start = parameters.getParameterValue("start").toLong(0);
		final long end = parameters.getParameterValue("end").toLong(0);

		if (this.model != null)
		{
			this.model.setStart(new Date(start  * 1000));
			this.model.setEnd(new Date(end * 1000));
		}

		final IRequestHandler handler = this.newRequestHandler();
		requestCycle.scheduleRequestHandlerAfterCurrent(handler);
	}

	/**
	 * Gets the new {@link IRequestHandler} that will respond the list of {@link CalendarEvent} in a json format
	 *
	 * @return the {@link IRequestHandler}
	 */
	private IRequestHandler newRequestHandler()
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

				if (model != null)
				{
					List<? extends CalendarEvent> list =  model.getObject(); // calls load()

					if (list != null)
					{
						StringBuilder builder = new StringBuilder("[ ");

						int count = 0;
						for (CalendarEvent event : list)
						{
							if (model instanceof ICalendarVisitor)
							{
								event.accept((ICalendarVisitor) model); //last chance to set options
							}

							if (count++ > 0) { builder.append(", "); }
							builder.append(event.toString());
						}

						builder.append(" ]");

						LOG.debug(builder.toString());
						response.write(builder);
					}
				}
			}

			@Override
			public void detach(final IRequestCycle requestCycle)
			{
			}
		};
	}
}