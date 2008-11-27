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

package org.wicketstuff.calendarviews.exampleapp;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.Link;
import org.wicketstuff.calendarviews.LargeView;

/**
 * @author Jeremy Thomerson
 */
public class HomePage extends WebPage {

	private static final String KEY_WEEKS = "0";
//	private static final Logger LOGGER = LoggerFactory.getLogger(HomePage.class);
	private static final long serialVersionUID = 1L;

	public HomePage() {
		this(0);
	}
	public HomePage(PageParameters params) {
		this(params.getInt(KEY_WEEKS));
	}
	
	public HomePage(int weeks) {
		if (weeks == 0) {
			add(LargeView.createMonthView("large", new PersistentRandomTestEventProvider()));
		} else {
			add(LargeView.createWeeksView("large", new PersistentRandomTestEventProvider(), weeks));
		}
		
		addLinks();
	}

	private void addLinks() {
		add(createLink("month", 0));
		add(createLink("2weeks", 2));
		add(createLink("3weeks", 3));
		add(createLink("4weeks", 4));
		add(createLink("5weeks", 5));
		add(createLink("6weeks", 6));
	}
	
	public static PageParameters createParameters(int weeks) {
		PageParameters params = null;
		if (weeks > 0) {
			params = new PageParameters();
			params.add(KEY_WEEKS, Integer.toString(weeks));
		}
		return params;
	}
	public static Link<Void> createLink(String id, final int weeks) {
		return new Link<Void>(id) {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				PersistentRandomTestEventProvider.clearEventsForFreshReload();
				setResponsePage(HomePage.class, createParameters(weeks));
			}
			
		};
	}
}
