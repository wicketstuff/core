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
package org.wicketstuff.calendarviews.util;

import java.util.Comparator;

import org.wicketstuff.calendarviews.model.IEvent;

/**
 * General utility holding comparators used within this library.  Not typically
 * useful or intended for use outside of CalendarViews.
 * 
 * @author Jeremy Thomerson
 */
public abstract class Comparators {
	
	private Comparators() {
		// no-op hide-a-constructor
	}
	
	public static final Comparator<IEvent> EVENT_START_DATE_ASC_COMPARATOR = new Comparator<IEvent>() {

		public int compare(IEvent o1, IEvent o2) {
			if (o1 == o2) {
				return 0;
			} else if (o1 == null) {
				return 1;
			} else if (o2 == null) {
				return -1;
			}
			// NOTE: we don't do null checks on o1.getStartTime() here because
			//		a start time is required to be used.
			return o1.getStartTime().compareTo(o2.getStartTime());
		}
		
	};

}
