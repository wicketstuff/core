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

import org.apache.wicket.ajax.json.JSONException;
import org.apache.wicket.ajax.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.wicket.jquery.core.utils.DateUtils;

/**
 * Provides a factory for building {@link SchedulerEvent}{@code s} as JSON
 * 
 * @author Sebastien Briquet - sebfz1
 *
 */
public class SchedulerEventFactory
{
	private static final Logger LOG = LoggerFactory.getLogger(SchedulerEventFactory.class);

	/**
	 * Converts a {@code SchedulerEvent} to a {@link JSONObject}
	 *
	 * @param event the {@link SchedulerEvent}
	 * @return the {@link JSONObject}
	 */
	public static JSONObject toJson(SchedulerEvent event)
	{
		try
		{
			JSONObject object = new JSONObject();

			object.put("id", event.getId());
			object.put("isAllDay", event.isAllDay());

			if (event.getTitle() != null)
			{
				object.put("title", event.getTitle());
			}

			if (event.getDescription() != null)
			{
				object.put("description", event.getDescription());
			}

			if (event.getStart() != null)
			{
				object.put("start", DateUtils.toISO8601(event.getStart()));
			}

			if (event.getEnd() != null)
			{
				object.put("end", DateUtils.toISO8601(event.getEnd()));
			}

			// recurrence //
			if (event.getRecurrenceId() != null)
			{
				object.put("recurrenceId", event.getRecurrenceId());
			}

			if (event.getRecurrenceRule() != null)
			{
				object.put("recurrenceRule", event.getRecurrenceRule());
			}

			if (event.getRecurrenceException() != null)
			{
				object.put("recurrenceException", event.getRecurrenceException());
			}

			// resources //
			for (String field : event.getFields())
			{
				object.put(field, event.getValue(field)); // value is type of Object (String, Integer, List<String> or List<Integer>)
			}

			return object;
		}
		catch (JSONException e)
		{
			LOG.error(e.getMessage(), e);
		}

		return null;
	}

	/**
	 * Factory class
	 */
	private SchedulerEventFactory()
	{
	}
}
