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

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.wicket.ResourceReference;
import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.wicketstuff.calendarviews.logic.DateMidnightIterator;
import org.wicketstuff.calendarviews.model.IEvent;
import org.wicketstuff.calendarviews.model.IEventProvider;
import org.wicketstuff.calendarviews.util.Comparators;

public abstract class BaseCalendarView extends Panel {

	private static final long serialVersionUID = 1L;
//	private static final Logger LOGGER = LoggerFactory.getLogger(BaseCalendarView.class);

	private final Date mStartDate;
	private final Date mEndDate;
	private IEventProvider mEventProvider;
	private IEventLinkCreator mEventLinkCreator = IEventLinkCreator.DEFAULT_IMPL;

	public BaseCalendarView(String id, Date startDate, Date endDate, IEventProvider eventProvider) {
		super(id);
		add(HeaderContributor.forCss(new ResourceReference(LargeView.class, "calendars.css")));
		if (startDate == null || endDate == null || eventProvider == null) {
			throw new IllegalArgumentException("no null parameters are allowed in this constructor");
		}
		mStartDate = startDate;
		mEndDate = endDate;
		mEventProvider = eventProvider;
	}

	/* Helper methods for subclasses */
	protected final Map<DateMidnight, List<IEvent>> convertToMapByDay(Collection<? extends IEvent> allEvents) {
		// TODO: this could probably use a much more efficient algorithm
		Map<DateMidnight, List<IEvent>> map = new HashMap<DateMidnight, List<IEvent>>();
		for (IEvent event : allEvents) {
			getRenderStrategy().mapEvent(map, event, this);
		}
		// now sort
		for (List<IEvent> list : map.values()) {
			Collections.sort(list, Comparators.EVENT_START_DATE_ASC_COMPARATOR);
		}
		return map;
	}

	protected IRenderStrategy getRenderStrategy() {
		return IRenderStrategy.EVERY_DAY_OF_EVENT;
	}

	protected IDataProvider<DateMidnight> createDaysDataProvider(final DateTime start, final DateTime end, final Period period) {
		return new IDataProvider<DateMidnight>() {
			private static final long serialVersionUID = 1L;

			public Iterator<? extends DateMidnight> iterator(final int first, int count) {
				return createDateMidnightIterator(start, end, first, count);
			}

			public IModel<DateMidnight> model(DateMidnight object) {
				return new Model<DateMidnight>(object);
			}

			public int size() {
				return period.getDays() + 1;
			}

			public void detach() {
				// no-op
			}
			
			@Override
			public String toString() {
				return "BaseCalendarView#DaysDataProvider [size: " + size() + "]";
			}
		};
	}

	protected Iterator<? extends DateMidnight> createDateMidnightIterator(DateTime start, DateTime end, int first, int count) {
		return new DateMidnightIterator(start, end, first, count);
	}

	protected final int getNumberOfColumns() {
		return 7;
	}

	protected final int getLastDayOfWeek() {
		return 6;
	}

	protected final int getFirstDayOfWeek() {
		return 7;
	}
	

	/* Getters / Setters */
	public final IEventProvider getEventProvider() {
		return mEventProvider;
	}
	public final Date getStartDate() {
		return mStartDate;
	}
	public final Date getEndDate() {
		return mEndDate;
	}
	public final IEventLinkCreator getEventLinkCreator() {
		return mEventLinkCreator;
	}

	public BaseCalendarView setEventLinkCreator(IEventLinkCreator eventLinkCreator) {
		if (eventLinkCreator == null) {
			throw new IllegalArgumentException("event link creator can not be null");
		}
		mEventLinkCreator = eventLinkCreator;
		return this;
	}
}
