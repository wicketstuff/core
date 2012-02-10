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
import org.wicketstuff.calendarviews.model.BasicCategorizedEvent;
import org.wicketstuff.calendarviews.model.BasicEvent;
import org.wicketstuff.calendarviews.model.IEvent;
import org.wicketstuff.calendarviews.model.IEventProvider;

/**
 * @author Jeremy Thomerson
 */
public class RandomTestEventProvider extends LoadableDetachableModel<Collection<? extends IEvent>>
	implements IEventProvider
{

	private static final long serialVersionUID = 1L;
	private static final long MILLIS_DAY = 1000 * 60 * 60 * 24;
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd");
	private static final DateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm");

	private final Random mRandom = new Random();
	private final Set<IEvent> mEvents = new HashSet<IEvent>();

	@Override
	protected Collection<? extends IEvent> load()
	{
		return mEvents;
	}

	public void initializeWithDateRange(Date start, Date end)
	{
		if (isAttached())
		{
			return;
		}
		int counter = 1;
		if (getAdjustedDate(start, +3).before(end))
		{
			// let's add one that starts 10 days before and runs three days into range
			mEvents.add(createEvent(counter++, true, getAdjustedDate(start, -10),
				getAdjustedDate(start, +3)));
		}
		// let's add one that starts 5 days before and runs 5 days after range
		mEvents.add(createEvent(counter++, true, getAdjustedDate(start, -5),
			getAdjustedDate(end, +5)));

		// start a few days early so that we can get ones that start before but extend into this
// range
		for (Date current = getAdjustedDate(start, -3); current.before(end); current = getAdjustedDate(
			current, +1))
		{
			int events = mRandom.nextInt(4);
			for (int i = 1; i <= events; i++)
			{
				IEvent evt = createRandomEvent(current, counter++);
				if (evt.getStartTime().before(start))
				{
					// this is one that starts out of range, so let's only add it if it extends into
// range
					// this mimics what a well-behaved provider should do according to contract
					if (evt.getEndTime() == null || evt.getEndTime().before(start))
					{
						counter--;
						continue;
					}
				}
				mEvents.add(evt);
			}
		}
	}

	protected final Date getAdjustedDate(Date date, int days)
	{
		return new Date(date.getTime() + (days * MILLIS_DAY));
	}

	private IEvent createRandomEvent(Date current, int id)
	{
		// TODO: add support for events that last from a certain time today to a certain time
// tomorrow
		boolean multiDay = mRandom.nextInt(4) == 1;
		Date start = new DateMidnight(current).toDate();
		if (multiDay)
		{
			// a multi-day event (always all-day)
			Date end = new DateTime(start).plusDays(mRandom.nextInt(9)).toDate();
			return createEvent(id, true, start, end);
		}
		boolean allDay = mRandom.nextInt(3) == 1;
		if (allDay)
		{
			// an all day, single-day event
			return createEvent(id, true, start, null);
		}

		// this is a partial day event
		int startHour = mRandom.nextInt(13);
		int startMinutes = (mRandom.nextInt(4) * 15);
		int durationHours = mRandom.nextInt(5);
		int durationMinutes = (mRandom.nextInt(4) * 15);
		start = new DateTime(new DateMidnight(current)).plusHours(startHour)
			.plusMinutes(startMinutes)
			.toDate();
		Date end = new DateTime(start).plusHours(durationHours)
			.plusMinutes(durationMinutes)
			.toDate();
		return createEvent(id, false, start, end);
	}

	private BasicEvent createEvent(int id, boolean allDay, Date start, Date end)
	{
		BasicCategorizedEvent event = new BasicCategorizedEvent();
		StringBuffer title = new StringBuffer();
		// title.append("Event");
		title.append(" #").append(id);
		if (allDay)
		{
			title.append(" [").append(DATE_FORMAT.format(start));
			if (end != null)
			{
				title.append(" - ").append(DATE_FORMAT.format(end));
			}
		}
		else
		{
			title.append(" [").append(TIME_FORMAT.format(start));
			if (end != null)
			{
				title.append(" - ").append(TIME_FORMAT.format(end));
			}
		}
		title.append("]");
		event.setTitle(title.toString());
		event.setAllDayEvent(allDay);
		event.setStartTime(start);
		event.setEndTime(end);
		event.setCssClassForCategory("cat" + ((mRandom.nextInt(3)) + 1));
		return event;
	}

}
