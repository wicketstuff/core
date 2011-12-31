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
 * File: WebFlowButton
 *****************************************************************************/
package org.wicketstuff.webflow.components;

import org.apache.wicket.RequestCycle;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.model.IModel;

import org.wicketstuff.webflow.controller.RequestTargetFactory;


/**
 * Button that uses Spring Web Flow to determine the next page to be displayed.
 *
 * @author Clint Checketts, Florian Braun, Doug Hall, Subramanian Murali
 * @version $Id: $
 */
public class WebFlowButton extends Button 
{
	private static final long serialVersionUID = 1L;
	
	//Attribute that represents the event id for web flow/page flow transition
	//This is an optional attribute.
	//If not set, markup id will be used as the event id for web flow/page flow transition
	private String eventId;	
	
	/**
	 * Constructor.
	 *
	 * @param id - Mark up id.
	 * @param model a {@link org.apache.wicket.model.IModel} object.
	 */
	public WebFlowButton(String id, IModel<String> model) 
	{
		super(id,model);
		this.eventId = id;		
	}
	
	/**
	 * <p>Constructor for WebFlowButton.</p>
	 *
	 * @param id a {@link java.lang.String} object.
	 */
	public WebFlowButton(String id)
	{
		super(id);
		this.eventId = id;		
	}

	/* (non-Javadoc)
	 * @see org.apache.wicket.markup.html.form.Button#onSubmit()
	 */
	/**
	 * <p>onSubmit.</p>
	 */
	public void onSubmit()
	{
		super.onSubmit();
		RequestCycle requestCycle = RequestCycle.get();
		requestCycle.setRequestTarget( RequestTargetFactory.buildFromTransition(eventId, requestCycle));
//		FlowUtils.setResponsePageForEventId(this.getEventId());
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
	 * @return a {@link org.wicketstuff.webflow.components.WebFlowButton} object.
	 */
	public WebFlowButton setEventId(String eventId)
	{
		this.eventId = eventId;
		return this;
	}		
}
