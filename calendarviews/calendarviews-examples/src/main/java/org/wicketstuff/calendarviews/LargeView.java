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

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.GridView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.calendarviews.model.IEvent;
import org.wicketstuff.calendarviews.model.IEventProvider;

/**
 * @author Jeremy Thomerson
 */
public class LargeView extends FullWeekCalendarView {
	
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(LargeView.class);

	public LargeView(String id, Date startDate, Date endDate, IEventProvider eventProvider) {
		super(id, startDate, endDate, eventProvider);

		IDataProvider<DateMidnight> dp = createDaysDataProvider();
		Collection<? extends IEvent> allEvents = getEventProvider().getObject();
		final Map<DateMidnight, List<IEvent>> mapOfEvents = convertToMapByDay(allEvents);
		
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Data provider: " + dp);
		}
		add(new LargeGridView("rows", dp, mapOfEvents));
	}

	protected ListView<IEvent> createEventListView(String id, IModel<List<IEvent>> model) {
		return new ListView<IEvent>(id, model) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<IEvent> item) {
				item.add(new Label("title", new PropertyModel<String>(item.getModel(), "title")));
			}
		};
	}

	private class LargeGridView extends GridView<DateMidnight> {
		private static final long serialVersionUID = 1L;
		private transient Map<DateMidnight, List<IEvent>> mMapOfEvents;

		public LargeGridView(String id, IDataProvider<DateMidnight> dp, Map<DateMidnight, List<IEvent>> mapOfEvents) {
			super(id, dp);
			mMapOfEvents = mapOfEvents;
		}
		
		@Override
		public int getColumns() {
			return LargeView.this.getNumberOfColumns();
		}

		@Override
		protected void populateEmptyItem(Item<DateMidnight> item) {
			throw new UnsupportedOperationException("LargeView should not have any empty items");
		}

		@Override
		protected void populateItem(final Item<DateMidnight> item) {
			item.add(new Label("date", new PropertyModel<Integer>(item.getModel(), "dayOfMonth")));
			item.add(createEventListView("events", new AbstractReadOnlyModel<List<IEvent>>() {
				private static final long serialVersionUID = 1L;

				@Override
				public List<IEvent> getObject() {
					return mMapOfEvents.get(item.getModelObject());
				}
				
			}));
		}
		
		@Override
		protected void onDetach() {
			super.onDetach();
			mMapOfEvents = null;
		}
	}

	public static LargeView createWeeksView(String id, IEventProvider eventProvider, int weeks) {
		// TODO add a similar method that allows an offset of weeks (i.e. 3 weeks, starting two weeks past today)
		Date start = new Date();
		Date end = new DateTime(start).plusWeeks(weeks - 1).toDate();
		return new LargeView(id, start, end, eventProvider);
	}

	public static LargeView createMonthView(String id, IEventProvider eventProvider) {
		Date start = new DateTime().dayOfMonth().setCopy(1).toDate();
		Date end = new DateTime(start).plusMonths(1).minusDays(1).toDate();
		return new LargeView(id, start, end, eventProvider);
	}
	
}
