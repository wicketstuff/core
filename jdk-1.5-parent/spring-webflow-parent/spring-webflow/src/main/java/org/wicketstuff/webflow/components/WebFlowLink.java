/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributed by United Services Automotive Association (USAA)
 */
/* ***************************************************************************
 * File: WebFlowLink
 *****************************************************************************/
package org.wicketstuff.webflow.components;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.wicketstuff.webflow.FlowUtils;


/**
 * Link that uses Spring Web Flow to determine the next page to be displayed.
 *
 * @author Clint Checketts, Florian Braun, Doug Hall, Subramanian Murali
 * @version $Id: $
 */
public class WebFlowLink<T> extends Link<T> 
{
	private static final long serialVersionUID = 1L;
	
	//Attribute that represents the event id for web flow/page flow transition
	//This is an optional attribute.
	//If not set, markup id will be used as the event id for web flow/page flow transition
	private String eventId;
	
	/**
	 * Constructor.
	 *
	 * @param id - Mark up id this will be used to trigger the flow's event id.
	 * @param <T> a T object.
	 */
	public WebFlowLink(String id) 
	{
		super(id);
		this.eventId = id;
	}
	
	
	
	/**
	 * <p>Constructor for WebFlowLink.</p>
	 *
	 * @param id a {@link java.lang.String} object.
	 * @param model a {@link org.apache.wicket.model.IModel} object.
	 */
	public WebFlowLink(String id, IModel<T> model)
	{
		super(id, model);
		this.eventId = id;
	}



	/* (non-Javadoc)
	 * @see org.apache.wicket.markup.html.link.Link#onClick()
	 */
	/**
	 * <p>onClick.</p>
	 */
	public void onClick()
	{
		FlowUtils.setResponsePageForEventId(this.getEventId());
	}
	
	/**
	 * Method that returns the event id.
	 *
	 * @return String - Event Id for Web Flow/Page Flow Transition.
	 */
	public String getEventId()
	{
		return eventId;
	}

	/**
	 * Method sets the event id.
	 *
	 * @param eventId - Event Id for Web Flow/Page Flow Transition.
	 * @return a {@link org.wicketstuff.webflow.components.WebFlowLink} object.
	 */
	public WebFlowLink<T> setEventId(String eventId)
	{
		this.eventId = eventId;
		return this;
	}	
}
