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

LargeViewCalendar.EMPTY_SPOTS = new Array();
LargeViewCalendar.SELECTOR_DAYS = 'div.day';
LargeViewCalendar.SELECTOR_WEEKS = 'div.week';
LargeViewCalendar.SELECTOR_EVENTS_FROM_DAY = 'li';
LargeViewCalendar.SELECTOR_HEADER_FROM_DAY = 'h5';

LargeViewCalendar.initialize = function(calID) {
	if (!this.runMode) {
		return;
	}
	// TODO : using random 100 value here - need to figure out better way
	for (var i = 0; i < 100; i++) {
		this.EMPTY_SPOTS.push(null);
	}
	var calendar = $(calID);
	//calendar.relativize();
	this.prepDayElements(calendar);
	this.positionEvents(calendar);
	this.postEventRendering(calendar);
	Event.observe(window, "resize", function() {
		LargeViewCalendar.prepDayElements(calendar);
		LargeViewCalendar.positionEvents(calendar);
		LargeViewCalendar.postEventRendering(calendar);
	});
};

LargeViewCalendar.postEventRendering = function(calendar) {
	calendar.select(LargeViewCalendar.SELECTOR_DAYS).each(function(day) {
		var header = null;
		if (day.moreEvents && !(day.moreEventLink)) {
			// TODO: make a real working "more events" link
			header = day.select(LargeViewCalendar.SELECTOR_HEADER_FROM_DAY).first();
			header.innerHTML += ' (' + day.moreEvents + ' more)';
			day.moreEventLink = true;
		}
	});
};

LargeViewCalendar.positionEvents = function(calendar) {
	calendar.select(LargeViewCalendar.SELECTOR_DAYS).each(function(day) {
		day.select(LargeViewCalendar.SELECTOR_EVENTS_FROM_DAY).sort(function(a, b) {
			// TODO : this will need to switch to sort on start-time
			return b.getAttribute('days') - a.getAttribute('days'); 
		}).each(function(event) {
			LargeViewCalendar.correctEventSize(event, day);
		});
	});
};

LargeViewCalendar.prepDayElements = function(calendar) {
	var weekCounter = 0;
	calendar.select(LargeViewCalendar.SELECTOR_WEEKS).each(function(week) {
		weekCounter++;
		var days = week.select(LargeViewCalendar.SELECTOR_DAYS).reverse();
		var following = new Array();
		days.each(function(day) {
			day.relativize();
			day.weekNumber = weekCounter;
			day.events = 0;
			day.spots = LargeViewCalendar.EMPTY_SPOTS.clone();
			day.moreEvents = 0;
			day.followingDays = following.clone().reverse();
			following.push(day);
			day.date = day.select(LargeViewCalendar.SELECTOR_HEADER_FROM_DAY).first().innerHTML;
		});
	});
};

LargeViewCalendar.correctEventSize = function(event, day) {
	var days = event.getAttribute('days');
	var headerHeight = day.select(LargeViewCalendar.SELECTOR_HEADER_FROM_DAY).first().getHeight();
	var eventHeight = event.getHeight() + 1;
	var eventWidth = (event.getWidth() * days);
	var lastDay = day;
	var top = null;
	var finalSpot = day.events++;
	event.absolutize();
	// TODO : using random 100 value here - need to figure out better way
	for (var spot = 0; spot < 100; spot++) {
		if (this.spotWillWork(spot, day, days)) {
			finalSpot = spot; 
			break;
		}
	}
	day.spots[finalSpot] = event;
	for (var i = 0; i < (days - 1); i++) {
		var fDay = day.followingDays[i];
		lastDay = fDay;
		fDay.spots[finalSpot] = event;
	}
	eventWidth = this.calculateWidthForEvent(event, lastDay);
	event.style.width = eventWidth;
	top = (event.getHeight() * (spot)) + headerHeight;
	top = top + (spot * 2); // TODO: this is for debugging - adding spacing - remove line
	if (top + eventHeight > day.getHeight()) {
		day.moreEvents++;
		event.hide();
	}
	//alert(event.select('span').first().innerHTML + ' - ' + finalSpot);
	this.setTop(event, top);
};

LargeViewCalendar.spotWillWork = function(spot, day, days) {
	if (day.spots[spot] != null) {
		return false;
	}
	for (var i = 0; i < (days - 1); i++) {
		var fDay = day.followingDays[i];
		if (fDay.spots[spot] != null) {
			return false;
		}
	}
	return true;
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
