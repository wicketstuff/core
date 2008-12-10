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

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.model.IModel;
import org.wicketstuff.calendarviews.model.ICategorizedEvent;
import org.wicketstuff.calendarviews.model.IEvent;
import org.wicketstuff.calendarviews.util.StringUtil;

/**
 * A behavior for adding a class attribute of <tt>ICategorizedEvent</tt>
 * events to a component, even if there is already a class on the tag.  
 * If there is, this behavior will add a space between the two classes.
 *  
 * @author Jeremy Thomerson
 */
public class AddCssClassBehavior extends AbstractBehavior {
	private static final String ATTRIBUTE_NAME = "class";

	private static final long serialVersionUID = 1L;

	private final IModel<IEvent> mEventModel;
	
	public AddCssClassBehavior(IModel<IEvent> event) {
		mEventModel = event;
	}

	@Override
	public void onComponentTag(Component component, ComponentTag tag) {
		super.onComponentTag(component, tag);
		List<String> classes = new ArrayList<String>();
		String existing = tag.getAttributes().getString(ATTRIBUTE_NAME);
		if (StringUtil.isEmpty(existing) == false) {
			classes.add(existing);
		}
		
		IEvent event = mEventModel.getObject();
		classes.add(event.isAllDayEvent() ? "allday" : "partday");
		
		if (event instanceof ICategorizedEvent) {
			String css = ((ICategorizedEvent) event).getCssClassForCategory();
			classes.add(css);
		}
		tag.put(ATTRIBUTE_NAME, StringUtil.join(classes.toArray(new String[classes.size()]), " "));
	}
}
