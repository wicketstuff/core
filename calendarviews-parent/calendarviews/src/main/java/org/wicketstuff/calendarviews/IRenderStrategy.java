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
package org.wicketstuff.calendarviews;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.joda.time.DateMidnight;
import org.wicketstuff.calendarviews.logic.DateMidnightIterator;
import org.wicketstuff.calendarviews.model.IEvent;

public interface IRenderStrategy {
	
	void mapEvent(Map<DateMidnight, List<IEvent>> map, IEvent event, BaseCalendarView calendar);

	public static final IRenderStrategy ONLY_ONCE_PER_EVENT = new IRenderStrategy() {
		public void mapEvent(Map<DateMidnight, List<IEvent>> map, IEvent event, BaseCalendarView calendar) {
			RenderStrategyUtility.addEventToDate(map, new DateMidnight(event.getStartTime()), event);
		}
	};
	
	public static final IRenderStrategy EVERY_DAY_OF_EVENT = new IRenderStrategy() {
		public void mapEvent(Map<DateMidnight, List<IEvent>> map, IEvent event, BaseCalendarView calendar) {
			DateMidnight start = new DateMidnight(event.getStartTime());
			DateMidnight end = start;
			if (event.getEndTime() != null && event.getEndTime().equals(event.getStartTime()) == false) {
				end = new DateMidnight(event.getEndTime());
			}
			if (end.isAfter(start)) {
				for (Iterator<DateMidnight> it = new DateMidnightIterator(start.toDateTime(), end.toDateTime()); it.hasNext(); ) {
					RenderStrategyUtility.addEventToDate(map, it.next(), event);
				}
			} else {
				RenderStrategyUtility.addEventToDate(map, start, event);
			}
		}
	};
	
	
	public static final IRenderStrategy FIRST_AND_FIRST_OF_ROW = new IRenderStrategy() {
		public void mapEvent(Map<DateMidnight, List<IEvent>> map, IEvent event, BaseCalendarView calendar) {
			DateMidnight start = new DateMidnight(event.getStartTime());
			DateMidnight end = start;
			if (event.getEndTime() != null && event.getEndTime().equals(event.getStartTime()) == false) {
				end = new DateMidnight(event.getEndTime());
			}
			if (end.isAfter(start)) {
				for (Iterator<DateMidnight> it = new DateMidnightIterator(start.toDateTime(), end.toDateTime()); it.hasNext(); ) {
					DateMidnight date = it.next();
					if (date.equals(start) || date.getDayOfWeek() == calendar.getFirstDayOfWeek()) {
						RenderStrategyUtility.addEventToDate(map, date, event);
					}
				}
			} else {
				RenderStrategyUtility.addEventToDate(map, start, event);
			}
		}
	};

	public static class RenderStrategyUtility {
		private static void addEventToDate(Map<DateMidnight, List<IEvent>> map, DateMidnight date, IEvent event) {
			List<IEvent> events = map.get(date);
			if (events == null) {
				events = new ArrayList<IEvent>();
				map.put(date, events);
			}
			events.add(event);
		}
	}

}
