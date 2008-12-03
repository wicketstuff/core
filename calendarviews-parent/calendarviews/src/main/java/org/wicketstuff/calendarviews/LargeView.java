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
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
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
import org.joda.time.Days;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.calendarviews.model.IEvent;
import org.wicketstuff.calendarviews.model.IEventProvider;
import org.wicketstuff.jslibraries.JSReference;
import org.wicketstuff.jslibraries.Library;
import org.wicketstuff.jslibraries.VersionDescriptor;

/**
 * @author Jeremy Thomerson
 */
public class LargeView extends FullWeekCalendarView {
	
	private static final VersionDescriptor JS_LIB_VERSION_DESCRIPTOR = VersionDescriptor.alwaysLatestOfVersion(Library.PROTOTYPE, 1, 6);
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(LargeView.class);

	public LargeView(String id, Date startDate, Date endDate, IEventProvider eventProvider) {
		super(id, startDate, endDate, eventProvider);

		addJavascriptInitializers();
		
		IDataProvider<DateMidnight> dp = createDaysDataProvider();
		Collection<? extends IEvent> allEvents = getEventProvider().getObject();
		final Map<DateMidnight, List<IEvent>> mapOfEvents = convertToMapByDay(allEvents);
		
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Data provider: " + dp);
		}
		add(new LargeGridView("rows", dp, mapOfEvents));
	}

	@Override
	protected RenderStrategy getRenderStrategy() {
		return RenderStrategy.FIRST_AND_FIRST_OF_ROW;
	}
	
	private void addJavascriptInitializers() {
		setOutputMarkupId(true);
		add(HeaderContributor.forJavaScript(JSReference.getReference(JS_LIB_VERSION_DESCRIPTOR)));
		add(HeaderContributor.forJavaScript(getClass(), "LargeView.js"));
		add(new HeaderContributor(new IHeaderContributor() {
			private static final long serialVersionUID = 1L;

			public void renderHead(IHeaderResponse response) {
				String calID = LargeView.this.getMarkupId();
				response.renderOnDomReadyJavascript("LargeViewCalendar.initialize('" + calID + "');");
			}
		}));
	}

	protected ListView<IEvent> createEventListView(String id, final IModel<DateMidnight> dateModel, final int cellsLeftInRow, IModel<List<IEvent>> model) {
		return new ListView<IEvent>(id, model) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<IEvent> item) {
				item.add(new Label("title", new PropertyModel<String>(item.getModel(), "title")));
				item.add(new HowManyDaysClassBehavior(dateModel, cellsLeftInRow, item.getModel()));
			}
		};
	}

	private class LargeGridView extends GridView<DateMidnight> {
		private static final long serialVersionUID = 1L;
		
		private transient Map<DateMidnight, List<IEvent>> mMapOfEvents;
		private int mCounter;
		
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
			int cell = (mCounter++ % getColumns()) + 1;
			int cellsLeft = getColumns() - cell;
			item.add(new Label("date", new PropertyModel<Integer>(item.getModel(), "dayOfMonth")));
			item.add(createEventListView("events", item.getModel(), cellsLeft, new AbstractReadOnlyModel<List<IEvent>>() {
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

	private static class HowManyDaysClassBehavior extends AbstractBehavior {
		private static final long serialVersionUID = 1L;

		private int mDaysLeftInRow;
		private IModel<DateMidnight> mDateModel;
		private IModel<IEvent> mEventModel;
		
		public HowManyDaysClassBehavior(IModel<DateMidnight> dateModel, int daysLeftInRow, IModel<IEvent> model) {
			mDaysLeftInRow = daysLeftInRow;
			mDateModel = dateModel;
			mEventModel = model;
		}

		@Override
		public void onComponentTag(Component component, ComponentTag tag) {
			super.onComponentTag(component, tag);
			DateMidnight day = mDateModel.getObject();
			Date end = mEventModel.getObject().getEndTime();
			int numberOfDays = 1;
			if (end != null) {
				DateTime endTime = new DateTime(end);
				int days = Math.abs(Days.daysBetween(day, endTime).getDays());
				numberOfDays = Math.min(days, mDaysLeftInRow) + 1;
			}
			tag.put("days", numberOfDays);
		}
	}
}
