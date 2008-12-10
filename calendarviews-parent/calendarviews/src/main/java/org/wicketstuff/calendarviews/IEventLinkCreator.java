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
