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

import java.util.List;

import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.util.lang.Args;

import com.googlecode.wicket.jquery.core.behavior.AjaxCallbackBehavior;

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
	private final SchedulerEventFactory factory;

	/**
	 * Constructor
	 *
	 * @param model the {@link SchedulerModel}
	 */
	public SchedulerModelBehavior(final SchedulerModel model, SchedulerEventFactory factory)
	{
		this.model = model;
		this.factory = Args.notNull(factory, "factory");
	}

	/**
	 * Sets the start date to the model<br/>
	 * This can be overridden to perform additional operation on date before the assignment.
	 *
	 * @param model the {@link SchedulerModel}
	 * @param date the timestamp
	 */
	protected void setStartDate(SchedulerModel model, long date)
	{
		model.setStart(date);
	}

	/**
	 * Sets the end date to the model<br/>
	 * This can be overridden to perform additional operation on date before the assignment.
	 *
	 * @param model the {@link SchedulerModel}
	 * @param date the timestamp
	 */
	protected void setEndDate(SchedulerModel model, long date)
	{
		model.setEnd(date);
	}

	@Override
	protected String getResponse(IRequestParameters parameters)
	{
		final long start = parameters.getParameterValue("start").toLong(0);
		final long end = parameters.getParameterValue("end").toLong(0);

		StringBuilder builder = new StringBuilder("[ ");

		if (this.model != null)
		{
			this.setStartDate(this.model, start);
			this.setEndDate(this.model, end);

			List<SchedulerEvent> list = this.model.getObject(); // calls load()

			if (list != null)
			{

				int count = 0;
				for (SchedulerEvent event : list)
				{
					if (this.model instanceof ISchedulerVisitor)
					{
						event.accept((ISchedulerVisitor) this.model); // last chance to set options
					}

					if (event.isVisible())
					{
						if (count++ > 0)
						{
							builder.append(", ");
						}

						builder.append(this.factory.toJson(event));
					}
				}
			}
		}

		return builder.append(" ]").toString();
	}
}
