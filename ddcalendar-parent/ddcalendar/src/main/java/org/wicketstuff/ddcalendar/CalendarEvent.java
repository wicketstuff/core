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

import org.apache.wicket.ResourceReference;
import org.joda.time.LocalTime;


public interface CalendarEvent extends Serializable {
	String getCaption();
	String getDescription();
	LocalTime getStart();
	int getDay();
	Color getBackgroundColor();
	Color getColor();
	int getDuration();
	String getEventStyle(int hourHeight);
	ResourceReference getEventImageResource();
	void remove();
}
