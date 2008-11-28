/**
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

package org.wicketstuff.calendarviews.exampleapp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.apache.wicket.model.LoadableDetachableModel;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.wicketstuff.calendarviews.model.BasicEvent;
import org.wicketstuff.calendarviews.model.IEvent;
import org.wicketstuff.calendarviews.model.IEventProvider;

/**
 * @author Jeremy Thomerson
 */
public class RandomTestEventProvider extends LoadableDetachableModel<Collection<? extends IEvent>> implements IEventProvider {

	private static final long serialVersionUID = 1L;
	private static final long MILLIS_DAY = 1000 * 60 * 60 * 24;
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd");
	
	private final Random mRandom = new Random();
	private final Set<IEvent> mEvents = new HashSet<IEvent>();
	
	protected Collection<? extends IEvent> load() {
		return mEvents;
	}

	public void initializeWithDateRange(Date start, Date end) {
		int counter = 1;
		for (Date current = start; current.before(end); current = new Date(current.getTime() + MILLIS_DAY)) {
			int events = mRandom.nextInt(4);
			for (int i = 1; i <= events; i++) {
				mEvents.add(createRandomEvent(current, counter++));
			}
		}
	}

	private IEvent createRandomEvent(Date current, int id) {
		// TODO: add support for events that last an hour, thirty minutes, or several hours on a single day
		// and events that last from a certain time today to a certain time tomorrow
		boolean allDay = mRandom.nextBoolean();
		boolean multiDay = mRandom.nextBoolean();
		Date start = new DateMidnight(current).toDate();
		Date end = null;
		if (multiDay) {
			end = new DateTime(start).plusDays(mRandom.nextInt(9)).toDate();
		}
		
		BasicEvent event = new BasicEvent();
		StringBuffer title = new StringBuffer();
		title.append("Event #").append(id).append(" [").append(DATE_FORMAT.format(start));
		if (end != null) {
			title.append(" - ").append(DATE_FORMAT.format(end));
		}
		title.append("]");
		event.setTitle(title.toString());
		event.setAllDayEvent(allDay);
		event.setStartTime(start);
		event.setEndTime(end);
		return event;
	}

}
