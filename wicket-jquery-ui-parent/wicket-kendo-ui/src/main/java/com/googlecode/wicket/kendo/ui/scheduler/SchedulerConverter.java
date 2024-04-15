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

import java.time.ZoneOffset;
import java.util.List;

import com.github.openjson.JSONArray;
import com.github.openjson.JSONObject;

import com.googlecode.wicket.jquery.core.utils.DateUtils;
import com.googlecode.wicket.jquery.core.utils.JsonUtils;
import com.googlecode.wicket.kendo.ui.scheduler.resource.ResourceList;

/**
 * Default implementation of {@link ISchedulerConverter}
 * 
 * @author Sebastien Briquet - sebfz1
 *
 */
public class SchedulerConverter implements ISchedulerConverter
{
	private static final long serialVersionUID = 1L;

	private final ZoneOffset offset;

	/**
	 * Constructor
	 * 
	 * @param offset the offset (ie: {@link ZoneOffset#UTC}) to be used for converting {@link JSONObject} timestamps
	 */
	public SchedulerConverter(ZoneOffset offset)
	{
		this.offset = offset;
	}

	@Override
	public ZoneOffset getOffset()
	{
		return offset;
	}

	@Override
	public JSONObject toJson(SchedulerEvent event)
	{
		JSONObject object = new JSONObject();

		object.put("id", event.getId()); // Object
		object.put("isAllDay", event.isAllDay());
		object.putOpt("title", event.getTitle()); // may be null
		object.putOpt("description", event.getDescription()); // may be null

		if (event.getStart() != null)
		{
			object.put("start", DateUtils.toString(event.getStart()));
		}

		if (event.getUntil() != null)
		{
			object.put("end", DateUtils.toString(event.getUntil()));
		}

		// recurrence //
		object.putOpt("recurrenceId", event.getRecurrenceId()); // may be null
		object.putOpt("recurrenceRule", event.getRecurrenceRule()); // may be null
		object.putOpt("recurrenceException", event.getRecurrenceException()); // may be null

		// resources //
		for (String field : event.getFields())
		{
			object.put(field, event.getValue(field)); // value is type of Object
		}

		return object;
	}

	@Override
	public SchedulerEvent toObject(JSONObject object, List<ResourceList> lists)
	{
		SchedulerEvent event = this.newSchedulerEvent();

		event.setId(object.get("id")); // Object
		event.setTitle(object.optString("title"));
		event.setDescription(object.optString("description"));

		event.setStart(DateUtils.toZonedDateTime(object.getLong("start"), this.offset));
		event.setUntil(DateUtils.toZonedDateTime(object.getLong("end"), this.offset));
		event.setAllDay(object.getBoolean("isAllDay"));

		event.setRecurrenceId(object.optString("recurrenceId"));
		event.setRecurrenceRule(object.optString("recurrenceRule"));
		event.setRecurrenceException(object.optString("recurrenceException"));

		// Resources //
		for (ResourceList list : lists)
		{
			String field = list.getField();
			Object value = object.opt(field);

			if (list.isMultiple() && value instanceof JSONArray)
			{
				event.setValue(field, JsonUtils.toList((JSONArray) value));
			}
			else
			{
				event.setValue(field, value);
			}
		}

		return event;
	}

	/**
	 * Gets a new {@link SchedulerEvent}
	 * 
	 * @return a new {@link SchedulerEvent}
	 */
	protected SchedulerEvent newSchedulerEvent()
	{
		return new SchedulerEvent();
	}
}
