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

package org.wicketstuff.calendarviews.model;

import java.io.Serializable;
import java.util.Date;

/**
 * This is just a simple <tt>IEvent</tt> implementation, mostly used for
 * testing. You may use it as a wrapper in your own application if you would
 * like, but it is not necessary.
 * 
 * @author Jeremy Thomerson
 */
public class BasicEvent implements IEvent, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String mTitle;
	private Date mEndTime;
	private Date mStartTime;
	private boolean mAllDayEvent;

	public BasicEvent() {
		// no-op
	}
	
	public BasicEvent(String title, Date endTime, Date startTime, boolean allDayEvent) {
		super();
		mTitle = title;
		mEndTime = endTime;
		mStartTime = startTime;
		mAllDayEvent = allDayEvent;
	}

	public String getTitle() {
		return mTitle;
	}

	public void setTitle(String title) {
		mTitle = title;
	}

	public Date getEndTime() {
		return mEndTime;
	}

	public void setEndTime(Date endTime) {
		mEndTime = endTime;
	}

	public Date getStartTime() {
		return mStartTime;
	}

	public void setStartTime(Date startTime) {
		mStartTime = startTime;
	}

	public boolean isAllDayEvent() {
		return mAllDayEvent;
	}

	public void setAllDayEvent(boolean allDayEvent) {
		mAllDayEvent = allDayEvent;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (mAllDayEvent ? 1231 : 1237);
		result = prime * result
				+ ((mEndTime == null) ? 0 : mEndTime.hashCode());
		result = prime * result
				+ ((mStartTime == null) ? 0 : mStartTime.hashCode());
		result = prime * result + ((mTitle == null) ? 0 : mTitle.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BasicEvent other = (BasicEvent) obj;
		if (mAllDayEvent != other.mAllDayEvent)
			return false;
		if (mEndTime == null) {
			if (other.mEndTime != null)
				return false;
		} else if (!mEndTime.equals(other.mEndTime))
			return false;
		if (mStartTime == null) {
			if (other.mStartTime != null)
				return false;
		} else if (!mStartTime.equals(other.mStartTime))
			return false;
		if (mTitle == null) {
			if (other.mTitle != null)
				return false;
		} else if (!mTitle.equals(other.mTitle))
			return false;
		return true;
	}

}
