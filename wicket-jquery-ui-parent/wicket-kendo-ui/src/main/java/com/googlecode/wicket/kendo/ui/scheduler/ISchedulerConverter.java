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

import org.apache.wicket.util.io.IClusterable;

import com.github.openjson.JSONObject;
import com.googlecode.wicket.kendo.ui.scheduler.resource.ResourceList;

/**
 * Provides a converter for building {@link SchedulerEvent}{@code s} as {@link JSONObject}, and vice-versa
 * 
 * @author Sebastien Briquet - sebfz1
 *
 */
public interface ISchedulerConverter extends IClusterable
{
	/**
	 * Gets the offset (ie: {@link ZoneOffset#UTC}) to be used for converting {@link JSONObject} timestamps
	 * 
	 * @return the {@link ZoneOffset}
	 */
	ZoneOffset getOffset();

	/**
	 * Converts a {@link SchedulerEvent} to a {@link JSONObject}
	 *
	 * @param event the {@code SchedulerEvent}
	 * @return the {@code JSONObject}
	 */
	JSONObject toJson(SchedulerEvent event);

	/**
	 * Converts a {@link JSONObject} to a {@link SchedulerEvent}
	 *
	 * @param object the {@code JSONObject}
	 * @param lists the {@code List} of {@link ResourceList}{@code s}
	 * @return the {@code SchedulerEvent}
	 */
	SchedulerEvent toObject(JSONObject object, List<ResourceList> lists);

}
