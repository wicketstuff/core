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

import org.apache.log4j.Logger;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

public class EventPanel<T extends CalendarEvent> extends Panel {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(EventPanel.class);

	public EventPanel(final String id, final WicketCalendar<T> parent, final CalendarEvent event, final int hourHeight, final boolean removable) {
		super(id, new IModel<CalendarEvent>() {
			private static final long serialVersionUID = 1L;
			// mocleiri: removed for java 5 compatibility
//			@Override
			public CalendarEvent getObject() {
				return event;
			}
			// mocleiri: removed for java 5 compatibility
//			@Override
			public void setObject(CalendarEvent object) {
			}
			// mocleiri: removed for java 5 compatibility
//			@Override
			public void detach() {
			}
		});
		
		setOutputMarkupId(true);
		
		add(new SimpleAttributeModifier("style", event.getEventStyle(hourHeight)));
		
		add(new Image("image", event.getEventImageResource()));
		add(new Label("caption", event.getCaption()).add(new SimpleAttributeModifier("style", "color: "+event.getColor().html())));
		
		if(removable) {
			add(new AjaxFallbackLink<String>("remove") {
				private static final long serialVersionUID = 1L;
				@Override
				public void onClick(AjaxRequestTarget target) {
					event.remove();
					target.addComponent(parent.getParent().getParent());
				}
			});
		}
		else {
			add(new WebMarkupContainer("remove").setVisible(false));
		}
	}
}
