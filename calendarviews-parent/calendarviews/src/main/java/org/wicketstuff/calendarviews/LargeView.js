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

var LargeViewCalendar = {};

/*
 * runMode will turn off initialization of the calendar
 * so that you can see the dom and page without any js effects
 */
LargeViewCalendar.runMode = true;

LargeViewCalendar.SELECTOR_DAYS = "div.day";
LargeViewCalendar.SELECTOR_WEEKS = "div.week";
LargeViewCalendar.SELECTOR_EVENTS_FROM_DAY = "li";

LargeViewCalendar.initialize = function(calID) {
	if (!this.runMode) {
		return;
	}
	var calendar = $(calID);
	this.prepDayElements(calendar);
	calendar.select(LargeViewCalendar.SELECTOR_DAYS).each(function(day) {
		day.select(LargeViewCalendar.SELECTOR_EVENTS_FROM_DAY).each(function(event) {
			LargeViewCalendar.correctEventSize(event, day);
		});
	});
}

LargeViewCalendar.prepDayElements = function(calendar) {
	calendar.select(LargeViewCalendar.SELECTOR_WEEKS).each(function(week) {
		var days = week.select(LargeViewCalendar.SELECTOR_DAYS).reverse();
		var following = new Array();
		days.each(function(day) {
			day.relativize();
			day.events = 0;
			day.followingDays = following.clone().reverse();
			following.push(day);
			day.date = day.select("h5").first().innerHTML;
		});
	});
}

var counter = 0;

LargeViewCalendar.correctEventSize = function(event, day) {
	var days = event.getAttribute('days');
	var header = day.select('h5').first().getHeight();
	var eventHeight = event.getHeight() + 1;
	var eventWidth = (event.getWidth() * days);
	var lastDay = day;
	event.absolutize();
	for (var i = 0; i < (days - 1); i++) {
		var fDay = day.followingDays[i];
		lastDay = fDay;
		fDay.events++;
	}
	day.events++;
	eventWidth = this.calculateWidthForEvent(event, lastDay);
	event.style.width = eventWidth;
	this.setTop(event, (event.getHeight() * (day.events - 1)) + header);
};

LargeViewCalendar.calculateWidthForEvent = function(event, lastDay) {
	return (lastDay.cumulativeOffset().left + lastDay.getWidth()) - event.cumulativeOffset().left - 7;
};
LargeViewCalendar.getTop = function(elem) {
	return elem.cumulativeOffset.top;
};
LargeViewCalendar.setTop = function(elem, top) {
	elem.style.top = top + 'px';
};
