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

/**
 * This is just an extension of <tt>BasicEvent</tt> that adds the specified CSS class to events.
 * 
 * @see BasicEvent
 * @author Jeremy Thomerson
 */
public class BasicCategorizedEvent extends BasicEvent implements ICategorizedEvent
{
	private static final long serialVersionUID = 1L;

	private String mCssClassForCategory;

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result +
			(mCssClassForCategory == null ? 0 : mCssClassForCategory.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		BasicCategorizedEvent other = (BasicCategorizedEvent)obj;
		if (mCssClassForCategory == null)
		{
			if (other.mCssClassForCategory != null)
				return false;
		}
		else if (!mCssClassForCategory.equals(other.mCssClassForCategory))
			return false;
		return true;
	}

	public String getCssClassForCategory()
	{
		return mCssClassForCategory;
	}

	public void setCssClassForCategory(String cssClassForCategory)
	{
		mCssClassForCategory = cssClassForCategory;
	}

}
