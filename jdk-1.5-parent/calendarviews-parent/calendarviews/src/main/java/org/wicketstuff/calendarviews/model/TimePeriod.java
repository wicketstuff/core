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
package org.wicketstuff.calendarviews.model;

import java.io.Serializable;
import java.util.Date;

public class TimePeriod implements Serializable
{
	private static final long serialVersionUID = 1L;

	private final Date mStartDate;
	private final Date mEndDate;

	public TimePeriod(Date startDate, Date endDate)
	{
		super();
		mStartDate = startDate;
		mEndDate = endDate;
	}

	public Date getStartDate()
	{
		return mStartDate;
	}

	public Date getEndDate()
	{
		return mEndDate;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mEndDate == null) ? 0 : mEndDate.hashCode());
		result = prime * result + ((mStartDate == null) ? 0 : mStartDate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TimePeriod other = (TimePeriod)obj;
		if (mEndDate == null)
		{
			if (other.mEndDate != null)
				return false;
		}
		else if (!mEndDate.equals(other.mEndDate))
			return false;
		if (mStartDate == null)
		{
			if (other.mStartDate != null)
				return false;
		}
		else if (!mStartDate.equals(other.mStartDate))
			return false;
		return true;
	}

}
