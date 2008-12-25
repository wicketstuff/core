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
package org.wicketstuff.calendarviews;

import java.io.Serializable;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.wicketstuff.calendarviews.model.IEvent;

/**
 * Interface that allows you to create links for your calendar events.
 * 
 * TODO : should this be more generic like <tt>ICalendarSettings</tt> to 
 * 	allow other methods that modify or supplement the behavior of the calendar?
 * 
 * TODO : this extends Serializable because it ends up being serialized with
 * 	the calendar - will this trip up users and cause them to serialize things
 * 	they shouldn't?  I don't think it should be a problem, but need to revisit it
 * 
 * @author Jeremy Thomerson
 */
public interface IEventLinkCreator extends Serializable {

	/**
	 * The calendar will give you the opportunity to link each event to a page
	 * or dialog that gives more information about the event.  Override this 
	 * method to create your link.
	 *  
	 * @param id The ID for your link or WebMarkupContainer
	 * @param model The model containing the event for this link
	 * @return Some WebMarkupContainer (usually an AbstractLink of some sorts) with the ID given
	 */
	WebMarkupContainer createEventLink(String id, IModel<IEvent> model);
	
	public static final IEventLinkCreator DEFAULT_IMPL = new IEventLinkCreator() {
		private static final long serialVersionUID = 1L;

		public WebMarkupContainer createEventLink(String id, IModel<IEvent> model) {
			return new WebMarkupContainer(id);
		}
		
	};
}
