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
package org.wicketstuff.calendarviews.exampleapp;

import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Page;
import org.apache.wicket.PageParameters;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.string.JavascriptUtils;
import org.joda.time.DateMidnight;
import org.wicketstuff.calendarviews.LargeView;
import org.wicketstuff.calendarviews.model.IEvent;
import org.wicketstuff.calendarviews.model.TimePeriod;

/**
 * @author Jeremy Thomerson
 */
public class HomePage extends WebPage {

	private static final String KEY_WEEKS = "0";
//	private static final Logger LOGGER = LoggerFactory.getLogger(HomePage.class);
	private static final long serialVersionUID = 1L;
	public static final ResourceReference EXAMPLES_CSS_REFERENCE = new ResourceReference(HomePage.class, "examples.css");

	public HomePage() {
		this(0);
	}
	public HomePage(PageParameters params) {
		this(params.getInt(KEY_WEEKS));
	}
	
	public HomePage(int weeks) {
		TimePeriod tp = LargeView.createMonthViewDates();
		if (weeks != 0) {
			tp = LargeView.createWeeksViewDates(weeks);
		}
		add(new LargeView("large", tp, new PersistentRandomTestEventProvider()) {
			private static final long serialVersionUID = 1L;

			@Override
			protected Page createMoreDetailPage(IModel<DateMidnight> model, IModel<List<IEvent>> eventsModel) {
				Page page = super.createMoreDetailPage(model, eventsModel);
				page.add(HeaderContributor.forCss(EXAMPLES_CSS_REFERENCE));
				return page;
			}
			@Override
			protected WebMarkupContainer createEventLink(String id, final IModel<IEvent> model) {
				WebMarkupContainer wmc = new WebMarkupContainer(id);
				wmc.add(new AttributeModifier("onclick", true, new AbstractReadOnlyModel<String>() {
					private static final long serialVersionUID = 1L;

					@Override
					public String getObject() {
						return "alert('" + JavascriptUtils.escapeQuotes(model.getObject().getTitle()) + "');";
					}
					
				}));
				return wmc;
			}
		});
		
		add(HeaderContributor.forCss(EXAMPLES_CSS_REFERENCE));
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
