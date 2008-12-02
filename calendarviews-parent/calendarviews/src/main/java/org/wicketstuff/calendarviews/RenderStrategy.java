package org.wicketstuff.calendarviews;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.joda.time.DateMidnight;
import org.wicketstuff.calendarviews.logic.DateMidnightIterator;
import org.wicketstuff.calendarviews.model.IEvent;

public enum RenderStrategy {

	ONLY_ONCE_PER_EVENT {
		public void mapEvent(Map<DateMidnight, List<IEvent>> map, IEvent event, BaseCalendarView calendar) {
			addEventToDate(map, new DateMidnight(event.getStartTime()), event);
		}
	},
	EVERY_DAY_OF_EVENT {
		public void mapEvent(Map<DateMidnight, List<IEvent>> map, IEvent event, BaseCalendarView calendar) {
			DateMidnight start = new DateMidnight(event.getStartTime());
			DateMidnight end = start;
			if (event.getEndTime() != null && event.getEndTime().equals(event.getStartTime()) == false) {
				end = new DateMidnight(event.getEndTime());
			}
			if (end.isAfter(start)) {
				for (Iterator<DateMidnight> it = new DateMidnightIterator(start.toDateTime(), end.toDateTime()); it.hasNext(); ) {
					addEventToDate(map, it.next(), event);
				}
			} else {
				addEventToDate(map, start, event);
			}
		}
	},
	FIRST_AND_FIRST_OF_ROW {
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
						addEventToDate(map, date, event);
					}
				}
			} else {
				addEventToDate(map, start, event);
			}
		}
	};

	public abstract void mapEvent(Map<DateMidnight, List<IEvent>> map, IEvent event, BaseCalendarView calendar);

	private static void addEventToDate(Map<DateMidnight, List<IEvent>> map, DateMidnight date, IEvent event) {
		List<IEvent> events = map.get(date);
		if (events == null) {
			events = new ArrayList<IEvent>();
			map.put(date, events);
		}
		events.add(event);
	}

}
