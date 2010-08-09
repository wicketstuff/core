/*
 * Copyright 2009 Michael Würtinger (mwuertinger@users.sourceforge.net)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * 		http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.ddcalendar;

import java.io.Serializable;
import java.util.Set;


public abstract class CalendarModelImpl implements CalendarModel, Serializable {
	private static final long serialVersionUID = 1L;
	
	private CalendarWeek week;

	public CalendarModelImpl(CalendarWeek week) {
		this.week = week;
	}
	
	public abstract Set<CalendarEvent> getEvents(CalendarWeek week);
	// mocleiri: removed for java 5 compatibility
//	@Override
	public CalendarWeek getWeek() {
		return week;
	}
//	@Override
	public Set<CalendarEvent> getEventsForCurrentWeek() {
		return getEvents(getWeek());
	}
//	@Override
	public void setNextWeek() {
		week = week.getNextWeek();
	}
//	@Override
	public void setPreviousWeek() {
		week = week.getPreviousWeek();
	}
}
