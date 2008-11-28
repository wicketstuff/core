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

package org.wicketstuff.calendarviews.logic;

import java.util.Iterator;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;

/**
 * @author Jeremy Thomerson
 */
public class DateMidnightIterator implements Iterator<DateMidnight> {

	private DateTime mCurrent;
	private final DateTime mEndDateTime;
	
	public DateMidnightIterator(DateTime start, DateTime end, int first, int count) {
		mCurrent = start.plusDays(first);
		mEndDateTime = mCurrent.plusDays(count);
	}

	public DateMidnightIterator(DateTime start, DateTime end) {
		this(start, end, 0, new Period(start, end, PeriodType.days()).getDays() + 1);
	}

	public boolean hasNext() {
		return mCurrent.isBefore(mEndDateTime);
	}

	public DateMidnight next() {
		DateMidnight dm = new DateMidnight(mCurrent);
		mCurrent = mCurrent.plusDays(1);
		return dm;
	}

	public void remove() {
		throw new UnsupportedOperationException("this should not be called");
	}
	
}
