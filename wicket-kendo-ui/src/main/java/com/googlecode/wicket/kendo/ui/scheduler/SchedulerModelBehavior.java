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
package com.googlecode.wicket.kendo.ui.scheduler;

import java.util.Date;
import java.util.List;

import org.apache.wicket.Application;
import org.apache.wicket.behavior.AbstractAjaxBehavior;
import org.apache.wicket.request.IRequestCycle;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.WebResponse;

/**
 * Provides the behavior that loads {@link SchedulerEvent}{@code s} according to {@link SchedulerModel} start &amp; end dates
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public class SchedulerModelBehavior extends AbstractAjaxBehavior
{
	private static final long serialVersionUID = 1L;

	private final SchedulerModel model;

	/**
	 * Constructor
	 *
	 * @param model the {@link SchedulerModel}
	 */
	public SchedulerModelBehavior(final SchedulerModel model)
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
			this.setStartDate(this.model, new Date(start));
			this.setEndDate(this.model, new Date(end));
		}

		requestCycle.scheduleRequestHandlerAfterCurrent(this.newRequestHandler());
	}

	/**
	 * Sets the start date to the model<br/>
	 * This can be overridden to perform additional operation on date before the assignment.
	 *
	 * @param model the {@link SchedulerModel}
	 * @param date the {@link Date}
	 */
	protected void setStartDate(SchedulerModel model, Date date)
	{
		model.setStart(date);
	}

	/**
	 * Sets the end date to the model<br/>
	 * This can be overridden to perform additional operation on date before the assignment.
	 *
	 * @param model the {@link SchedulerModel}
	 * @param date the {@link Date}
	 */
	protected void setEndDate(SchedulerModel model, Date date)
	{
		model.setEnd(date);
	}

	/**
	 * Gets the new {@link IRequestHandler} that will respond the list of {@link SchedulerEvent} in a JSON format
	 *
	 * @return the {@link IRequestHandler}
	 */
	protected IRequestHandler newRequestHandler()
	{
		return new IRequestHandler() {

			@Override
			public void respond(final IRequestCycle requestCycle)
			{
				WebResponse response = (WebResponse) requestCycle.getResponse();

				final String encoding = Application.get().getRequestCycleSettings().getResponseRequestEncoding();
				response.setContentType("text/json; charset=" + encoding);
				response.disableCaching();

				if (model != null)
				{
					List<SchedulerEvent> list = model.getObject(); // calls load()

					if (list != null)
					{
						StringBuilder builder = new StringBuilder("[ ");

						int count = 0;
						for (SchedulerEvent event : list)
						{
							if (model instanceof ISchedulerVisitor)
							{
								event.accept((ISchedulerVisitor) model); // last chance to set options
							}

							if (event.isVisible())
							{
								if (count++ > 0)
								{
									builder.append(", ");
								}

								builder.append(event.toJson());
							}
						}

						builder.append(" ]");

						response.write(builder);
					}
				}
			}

			@Override
			public void detach(final IRequestCycle requestCycle)
			{
				model.detach();
			}
		};
	}
}
