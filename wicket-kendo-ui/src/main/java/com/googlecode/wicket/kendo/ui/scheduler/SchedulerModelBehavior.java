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

import java.time.ZonedDateTime;
import java.util.List;

import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.util.lang.Args;

import com.github.openjson.JSONArray;
import com.googlecode.wicket.jquery.core.behavior.AjaxCallbackBehavior;
import com.googlecode.wicket.jquery.core.utils.DateUtils;

/**
 * Provides the behavior that loads {@link SchedulerEvent}{@code s} according to {@link SchedulerModel} start &amp; end dates
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public class SchedulerModelBehavior extends AjaxCallbackBehavior
{
	private static final long serialVersionUID = 1L;

	private final SchedulerModel model;
	private final ISchedulerConverter converter;

	/**
	 * Constructor
	 *
	 * @param model the {@link SchedulerModel}
	 * @param converter the {@link SchedulerConverter}
	 */
	public SchedulerModelBehavior(final SchedulerModel model, ISchedulerConverter converter)
	{
		this.model = model;
		this.converter = Args.notNull(converter, "converter");
	}

	/**
	 * Sets the start date to the model<br>
	 * This can be overridden to perform additional operation on date before the assignment.
	 *
	 * @param model the {@link SchedulerModel}
	 * @param datetime the {@link ZonedDateTime}
	 */
	protected void setModelStartDate(SchedulerModel model, ZonedDateTime datetime)
	{
		model.setStart(datetime);
	}

	/**
	 * Sets the until/end date to the model<br>
	 * This can be overridden to perform additional operation on date before the assignment.
	 *
	 * @param model the {@link SchedulerModel}
	 * @param datetime the {@link ZonedDateTime}
	 */
	protected void setModelUntilDate(SchedulerModel model, ZonedDateTime datetime)
	{
		model.setUntil(datetime);
	}

	@Override
	protected String getResponse(IRequestParameters parameters)
	{
		final long startTimestamp = parameters.getParameterValue("start").toLong(0);
		final long untilTimestamp = parameters.getParameterValue("end").toLong(0);

		final JSONArray payload = new JSONArray();

		if (this.model != null)
		{
			this.setModelStartDate(this.model, DateUtils.toZonedDateTime(startTimestamp, this.converter.getOffset()));
			this.setModelUntilDate(this.model, DateUtils.toZonedDateTime(untilTimestamp, this.converter.getOffset()));

			List<SchedulerEvent> list = this.model.getObject(); // calls load()

			if (list != null)
			{
				for (SchedulerEvent event : list)
				{
					if (this.model instanceof ISchedulerVisitor)
					{
						event.accept((ISchedulerVisitor) this.model); // last chance to set options
					}

					if (event.isVisible())
					{
						payload.put(this.converter.toJson(event));
					}
				}
			}
		}

		return payload.toString();
	}
}
