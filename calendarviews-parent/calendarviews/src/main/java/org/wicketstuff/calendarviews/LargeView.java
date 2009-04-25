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
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow.PageCreator;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.JavascriptPackageResource;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.GridView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.calendarviews.modal.DateDetailPage;
import org.wicketstuff.calendarviews.model.IEvent;
import org.wicketstuff.calendarviews.model.IEventProvider;
import org.wicketstuff.calendarviews.model.TimePeriod;
import org.wicketstuff.jslibraries.JSReference;
import org.wicketstuff.jslibraries.Library;
import org.wicketstuff.jslibraries.VersionDescriptor;

/**
 * This is a larger view of a calendar, typically used for multiple weeks
 * or entire months.  It generates a month-style grid calendar with events
 * that can span multiple days and supports categorized events.<br />
 * <br />
 * You could think of it as similar to the month view in Outlook or Google
 * calendar.
 * 
 * @author Jeremy Thomerson
 */
public class LargeView extends FullWeekCalendarView {
	
	private static final VersionDescriptor JS_LIB_VERSION_DESCRIPTOR = VersionDescriptor.alwaysLatestOfVersion(Library.PROTOTYPE, 1, 6);
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(LargeView.class);

	private final ModalWindow mDetailModal;
	
	public LargeView(String id, TimePeriod tp, IEventProvider eventProvider) {
		this(id, tp.getStartDate(), tp.getEndDate(), eventProvider);
	}

	public LargeView(String id, Date startDate, Date endDate, IEventProvider eventProvider) {
		super(id, startDate, endDate, eventProvider);

		addJavascriptInitializers();
		
		add(mDetailModal = new ModalWindow("detailModal"));
		initializeDetailModalWindow(mDetailModal);
		
		IDataProvider<DateMidnight> dp = createDaysDataProvider();
		Collection<? extends IEvent> allEvents = getEventProvider().getObject();
		final Map<DateMidnight, List<IEvent>> mapOfEvents = convertToMapByDay(allEvents);
		
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Data provider: " + dp);
		}
		add(new LargeGridView("rows", dp, mapOfEvents));
	}

	protected final void initializeDetailModalWindow(ModalWindow modal) {
		modal.setPageMapName("calendar-detail-modal");
		modal.setCookieName("calendar-detail-modal");
	}

	@Override
	protected final IRenderStrategy getRenderStrategy() {
		return IRenderStrategy.FIRST_AND_FIRST_OF_ROW;
	}
	
	private void addJavascriptInitializers() {
		setOutputMarkupId(true);
		add(JavascriptPackageResource.getHeaderContribution(JSReference.getReference(JS_LIB_VERSION_DESCRIPTOR)));
		add(JavascriptPackageResource.getHeaderContribution(LargeView.class, "LargeView.js"));
		add(new HeaderContributor(new IHeaderContributor() {
			private static final long serialVersionUID = 1L;

			public void renderHead(IHeaderResponse response) {
				String calID = LargeView.this.getMarkupId();
				response.renderOnDomReadyJavascript("LargeViewCalendar.initialize('" + calID + "');");
			}
		}));
	}

	protected final ListView<IEvent> createEventListView(String id, final IModel<DateMidnight> dateModel, final int cellsLeftInRow, IModel<List<IEvent>> model) {
		return new ListView<IEvent>(id, model) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(final ListItem<IEvent> item) {
				WebMarkupContainer link = createEventLink("link", item.getModel());
				link.add(createStartTimeLabel("startTime", item.getModel()));
				link.add(new Label("title", new PropertyModel<String>(item.getModel(), "title")));
				item.add(link);
				
				// things to decorate the item itself
				item.add(new HowManyDaysClassBehavior(dateModel, cellsLeftInRow, item.getModel()));
				item.add(new AddCssClassBehavior(item.getModel()));
			}

			private Label createStartTimeLabel(String id, final IModel<IEvent> model) {
				return new Label(id, new LoadableDetachableModel<String>() {
					private static final long serialVersionUID = 1L;

					@Override
					protected String load() {
						// TODO : make this implementation more internationalized... this one is too static
						//			use dateformat or something
						DateTime start = new DateTime(model.getObject().getStartTime());
						StringBuffer sb = new StringBuffer();
						int hr = start.getHourOfDay();
						sb.append(hr > 12 ? hr - 12 : hr);
						int min = start.getMinuteOfHour();
						if (min != 0) {
							sb.append(':');
							if (min < 0) {
								sb.append('0');
							}
							sb.append(min);
						}
						sb.append(hr > 12 ? 'p' : 'a');
						return sb.toString();
					}
					
				}) {
					private static final long serialVersionUID = 1L;

					@Override
					public boolean isVisible() {
						return model.getObject().isAllDayEvent() == false;
					}
				};
			}
		};
	}

	private class LargeGridView extends GridView<DateMidnight> {
		private static final long serialVersionUID = 1L;
		
		private Map<DateMidnight, List<IEvent>> mMapOfEvents;
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
			final AbstractReadOnlyModel<List<IEvent>> eventsModel = new LoadableDetachableModel<List<IEvent>>() {
				private static final long serialVersionUID = 1L;

				@Override
				protected List<IEvent> load() {
					return mMapOfEvents.get(item.getModelObject());
				}
				
			};
			Label dateHeader = new Label("date", new PropertyModel<Integer>(item.getModel(), "dayOfMonth"));
			dateHeader.add(new AjaxEventBehavior("onclick") {
				private static final long serialVersionUID = 1L;

				@Override
				protected void onEvent(AjaxRequestTarget target) {
					LOGGER.debug("Show more events for: " + item.getModelObject());
					onMoreLinkClicked(target, item.getModel(), eventsModel);
				}
			});
			item.add(dateHeader);
			item.add(createEventListView("events", item.getModel(), cellsLeft, eventsModel));
		}
		

		@Override
		protected void onDetach() {
			super.onDetach();
		}
	}

	protected void onMoreLinkClicked(AjaxRequestTarget target, IModel<DateMidnight> model, IModel<List<IEvent>> eventsModel) {
		mDetailModal.setPageCreator(getDetailModalPageCreator(model, eventsModel));
		mDetailModal.show(target);
	}
	
	protected PageCreator getDetailModalPageCreator(final IModel<DateMidnight> model, final IModel<List<IEvent>> eventsModel) {
		return new ModalWindow.PageCreator() {
			private static final long serialVersionUID = 1L;

			public Page createPage() {
				return createMoreDetailPage(model, eventsModel);
			}

		};
	}

	protected Page createMoreDetailPage(final IModel<DateMidnight> model, final IModel<List<IEvent>> eventsModel) {
		return new DateDetailPage(model, eventsModel, new DateDetailPage.IDateDetailPageEventLinkCreator() {
			public WebMarkupContainer createEventLink(String id, IModel<IEvent> model) {
				return LargeView.this.createEventLink(id, model);
			}
		});
	}

	protected WebMarkupContainer createEventLink(String id, IModel<IEvent> model) {
		return new WebMarkupContainer(id);
	}

	public static TimePeriod createWeeksViewDates(int weeks) {
		// TODO add a similar method that allows an offset of weeks (i.e. 3 weeks, starting two weeks past today)
		Date start = new Date();
		Date end = new DateTime(start).plusWeeks(weeks - 1).toDate();
		return new TimePeriod(start, end);
	}

	public static TimePeriod createMonthViewDates() {
		Date start = new DateTime().dayOfMonth().setCopy(1).toDate();
		Date end = new DateTime(start).plusMonths(1).minusDays(1).toDate();
		return new TimePeriod(start, end);
	}

	public static TimePeriod createMonthViewDates(int month, int year) {
		Date start = new DateTime().dayOfMonth().setCopy(1).monthOfYear().setCopy(month).year().setCopy(year).toDate();
		Date end = new DateTime(start).plusMonths(1).minusDays(1).toDate();
		return new TimePeriod(start, end);
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
			// TODO: is it valid XHTML to just arbitrarily add attributes not defined
			//			in the spec?  It sure makes it simple on the JS-side to access
			//			additional data about the event needed for the client-side 
			//			rendering.
			tag.put("days", numberOfDays);
		}
	}
}
