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

package org.wicketstuff.calendarviews;

import java.util.Date;

import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.calendarviews.model.IEventProvider;

/**
 * Calendar views that intend to show full weeks (Sunday-Saturday) can subclass this.
 * 
 * @author Jeremy Thomerson
 */
public class FullWeekCalendarView extends BaseCalendarView {

	private static final Logger LOGGER = LoggerFactory.getLogger(FullWeekCalendarView.class);
	private static final long serialVersionUID = 1L;

	public FullWeekCalendarView(String id, Date startDate, Date endDate, IEventProvider eventProvider) {
		super(id, startDate, endDate, eventProvider);
	}

	/**
	 * This implementation makes sure to include the entire data range specified 
	 * by the start date and end date passed in to the constructor, and any 
	 * additional days before and after that are needed to complete the full weeks
	 * contained in this view.
	 * 
	 * @return a data provider of days to be shown on the calendar
	 */
	protected final IDataProvider<DateMidnight> createDaysDataProvider() {
		int firstDOW = getFirstDayOfWeek();
		int lastDOW = getLastDayOfWeek();
		// TODO: is this logic right?  doing this since JODA has Sunday as day 7
		int add = firstDOW > lastDOW ? -7 : 0;
		final DateTime start = new DateTime(getStartDate()).withDayOfWeek(firstDOW).plusDays(add);
		final DateTime end = new DateTime(getEndDate()).withDayOfWeek(lastDOW);
		final Period period = new Period(start, end, PeriodType.days());

		getEventProvider().initializeWithDateRange(start.toDate(), end.toDate());
		
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("start: " + start + "; end: " + end + "; days: " + period.getDays());
		}
		
		return createDaysDataProvider(start, end, period);
	}
}
