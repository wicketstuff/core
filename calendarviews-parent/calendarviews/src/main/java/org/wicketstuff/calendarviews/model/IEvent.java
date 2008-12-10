/**
 * Copyright (C) 2008 Jeremy Thomerson <jeremy@thomersonfamily.com>
 *
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
package org.wicketstuff.calendarviews.model;

import java.util.Date;

/**
 * The main model-object interface for using CalendarViews - this is
 * what maps your model to the CalendarViews model.  Your domain model
 * object can either implement this interface directly, or you can use
 * a DTO of sorts and convert between the two.
 * 
 * @author Jeremy Thomerson
 */
public interface IEvent {

	String getTitle();
	Date getStartTime();
	Date getEndTime();
	
	/**
	 * Is this event an all day event or does it have a specific start
	 * and end time throughout the day?  
	 * 
	 * @return true if the event lasts all day (has no hour / minute start time)
	 * 	always false if the event is a multi-day event
	 */
	boolean isAllDayEvent();
	
}
