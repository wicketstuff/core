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

import com.googlecode.wicket.jquery.core.utils.DateUtils;

/**
 * Factory/Helper class for {@link SchedulerEvent}<tt>s</tt>
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public class SchedulerEventFactory
{
	public static String toJson(SchedulerEvent event) throws JSONException
	{
		JSONObject object = new JSONObject();

		object.put("id", event.getId());
		object.put("isAllDay", event.isAllDay());

		if (event.getTitle() != null)
		{
			object.put("title", event.getTitle());
		}

		if (event.getStart() != null)
		{
			object.put("start", DateUtils.toISO8601(event.getStart()));
		}

		if (event.getEnd() != null)
		{
			object.put("end", DateUtils.toISO8601(event.getEnd()));
		}

		// TODO Scheduler, to complete

		return object.toString();
	}

	/**
	 * Factory class
	 */
	private SchedulerEventFactory()
	{
	}
}
