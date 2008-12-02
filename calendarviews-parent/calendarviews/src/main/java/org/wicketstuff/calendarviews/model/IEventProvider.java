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

import java.util.Collection;
import java.util.Date;

import org.apache.wicket.model.IModel;

/**
 * @author Jeremy Thomerson
 */
public interface IEventProvider extends IModel<Collection<? extends IEvent>> {

	/**
	 * Called by the calendar view when it has computed what the actual start
	 * and end dates of the range that it will be displaying are. This is
	 * necessary, for example, because you may initialize the calendar view with
	 * a range of Wednesday through a Thursday, but depending on the type of
	 * view that it is, it may show from the Sunday prior to your start to the
	 * Saturday after your end.<br />
	 * <br />
	 * Note that the end time is exclusive.  Therefore, if the calendar is 
	 * displaying Sunday through Saturday, the start date will be Sunday at
	 * 00:00:00.000 and the end date will be the following Sunday at 00:00:00.000<br />
	 * <br />
	 * Also notable is that you need to remember to include not only events that
	 * begin within this range, but also events that begin before, but end within
	 * this range.  i.e. your query may be like:<br />
	 * <pre>
	 * 	where (start > :start and start < :end)
	 *	   or (end < :end and end > :start)
	 * </pre>
	 * 
	 * @param start The first instant that the calendar will display, inclusive
	 * @param end The last instant that the calendar will display, exclusive
	 */
	void initializeWithDateRange(Date start, Date end);
	
}
