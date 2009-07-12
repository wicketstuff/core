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

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.wicketstuff.yui.behavior.dragdrop.YuiDDList;
import org.wicketstuff.yui.markup.html.list.YuiDDListView;

public abstract class DDCalendarPanel<T extends CalendarEvent> extends Panel {
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger.getLogger(DDCalendarPanel.class);
	
	private final WicketCalendar<T> calendar;
	protected final CalendarModel calendarModel;

	public DDCalendarPanel(final String id, final List<String> hours, final int hourHeight, final CalendarModel model, final boolean showWeekNavigation, final String itemListCaption, final boolean itemsRemovable) {
		super(id);
		setOutputMarkupId(true);
		
		calendarModel = model;
		
		calendar = new WicketCalendar<T>("calendar", calendarModel, hourHeight, hours, showWeekNavigation) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onDrop(int dayOfWeek, int hourOfDay, T object, AjaxRequestTarget target) {
				DDCalendarPanel.this.onDrop(dayOfWeek, hourOfDay, object, target);
			}
		};
		calendar.setOutputMarkupId(true);
		add(calendar);
		
		add(new Label("calendarItemListCaption", itemListCaption));
		
		WebMarkupContainer itemListContainer = new WebMarkupContainer("taskListContainer");
		itemListContainer.setOutputMarkupId(true);
		add(itemListContainer);
		
		itemListContainer.add(new YuiDDListView<T>("eventList", new IModel<List<T>>() {
					private static final long serialVersionUID = 1L;
		
					@Override
					public List<T> getObject() {
						return getItems();
					}
		
					@Override
					public void setObject(List<T> object) {
					}
		
					@Override
					public void detach() {
					}
				}) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(final ListItem<T> item)
			{
				/*
				 *  WARNING: Do *NOT* call super.populateItem() as it adds a YuiDDList which removes the item from the list
				 *  after getting dropped. This is unnecessary and leads to errors as the task list gets its data from the
				 *  database and therefore is updated automatically.
				 */
				item.add(new YuiDDList(getGroupId())
				{
					private static final long serialVersionUID = 1L;
					@Override
					protected void onDrop(AjaxRequestTarget target, int index, Component dragOverItem)
					{
						// Just do nothing! (see above)
					}
				});
				
				//item.add(new Label("taskListLabel", item.getModelObject().toString()));
				
				item.add(new EventPanel<T>("eventListContent", calendar, item.getModelObject(), hourHeight, itemsRemovable));
			}

			@Override
			protected String getGroupId()
			{
				return WicketCalendar.DD_GROUP;
			}

			@Override
			protected void onAjaxUpdate(AjaxRequestTarget target) {
				target.addComponent(getParent());
			}
		});
	}
	
	protected abstract void onDrop(int dayOfWeek, int hourOfDay, T object, AjaxRequestTarget target);

	protected abstract List<T> getItems();
}
