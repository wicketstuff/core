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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.wicket.ajax.json.JSONException;
import org.apache.wicket.ajax.json.JSONObject;
import org.apache.wicket.util.io.IClusterable;
import org.apache.wicket.util.lang.Generics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.wicket.jquery.core.utils.DateUtils;
import com.googlecode.wicket.kendo.ui.scheduler.resource.ResourceList;

/**
 * Provides a factory for building {@link SchedulerEvent}{@code s} as JSON, and vice-versa
 * 
 * @author Sebastien Briquet - sebfz1
 *
 */
public class SchedulerEventFactory implements IClusterable
{
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(SchedulerEventFactory.class);

	/**
	 * Factory class
	 */
	public SchedulerEventFactory()
	{
	}

	/**
	 * Converts a {@link SchedulerEvent} to a {@link JSONObject}
	 *
	 * @param event the {@code SchedulerEvent}
	 * @return the {@code JSONObject}
	 */
	public JSONObject toJson(SchedulerEvent event)
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
				object.put(field, event.getValue(field)); // value is type of Object
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
	 * Converts a {@link JSONObject} to a {@link SchedulerEvent}
	 *
	 * @param object the {@code JSONObject}
	 * @param lists the {@code List} of {@link ResourceList}{@code s}
	 * @return the {@code SchedulerEvent}
	 */
	public SchedulerEvent toObject(JSONObject object, List<ResourceList> lists)
	{
		try
		{
			SchedulerEvent event = new SchedulerEvent();
			event.setId(object.getInt("id"));
			event.setTitle(object.optString("title"));
			event.setDescription(object.optString("description"));
			event.setStart(object.getLong("start"));
			event.setEnd(object.getLong("end"));
			event.setAllDay(object.getBoolean("isAllDay"));

			event.setRecurrenceId(object.optString("recurrenceId"));
			event.setRecurrenceRule(object.optString("recurrenceRule"));
			event.setRecurrenceException(object.optString("recurrenceException"));

			// Resources //
			Pattern pattern = Pattern.compile("([\\w-]+)");

			for (ResourceList list : lists)
			{
				List<String> values = Generics.newArrayList();

				String field = list.getField();
				String value = object.optString(field);

				if (value != null)
				{
					Matcher matcher = pattern.matcher(value);

					while (matcher.find())
					{
						values.add(matcher.group());
					}
				}

				if (list.isMultiple())
				{
					event.setResource(field, values);
				}
				else if (!values.isEmpty())
				{
					event.setResource(field, values.get(0)); // if the underlying value is a number (even a string-number), it will be handled by Id#valueOf(I)
				}
			}

			return event;
		}
		catch (JSONException e)
		{
			LOG.error(e.getMessage(), e);
		}

		return null;
	}
}
