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
package org.wicketstuff.calendarviews.modal;

import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.joda.time.DateMidnight;
import org.wicketstuff.calendarviews.BaseCalendarView;
import org.wicketstuff.calendarviews.model.IEvent;

public class DateDetailPage extends WebPage {

	public DateDetailPage(IModel<DateMidnight> model, IModel<List<IEvent>> eventsModel) {
		add(HeaderContributor.forCss(BaseCalendarView.CALENDARS_CSS_REFERENCE));
		add(new Label("title", new StringResourceModel("DateDetailPage.windowTitle", null, new Object[] { model.getObject().toDate() })));
		add(new Label("inPageTitle", new StringResourceModel("DateDetailPage.inPageTitle", null, new Object[] { model.getObject().toDate() })));
		add(new PropertyListView<IEvent>("list", eventsModel) {
			private static final long serialVersionUID = 1L;
			private int mCounter = 0;
			@Override
			protected void populateItem(ListItem<IEvent> item) {
				item.add(new Label("title"));
				item.add(new Label("startTime"));
				item.add(new Label("endTime"));
				item.add(new AttributeModifier("class", true, new AbstractReadOnlyModel<String>() {
					private static final long serialVersionUID = 1L;

					@Override
					public String getObject() {
						return "row" + (mCounter++ % 2);
					}
				}));
			}
		});
	}
	
}
