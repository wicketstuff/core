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
 * File: WebFlowAjaxLink
 *****************************************************************************/
package org.wicketstuff.webflow.components;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.model.IModel;
import org.wicketstuff.webflow.FlowUtils;


/**
 * <p>WebFlowAjaxLink class.</p>
 *
 * @author Clint Checketts, Florian Braun, Doug Hall, Subramanian Murali
 * @version $Id: $
 */
public class WebFlowAjaxLink<T> extends AjaxLink<T>
{
	private static final long	serialVersionUID	= 1L;
	
	//Attribute that represents the event id for web flow/page flow transition
	//This is an optional attribute.
	//If not set, markup id will be used as the event id for web flow/page flow transition
	private String eventId;
	
	
	/**
	 * <p>Constructor for WebFlowAjaxLink.</p>
	 *
	 * @param id Markup id, also sets the flows event id to be triggered. Call setEventId() if it needs to be different.
	 * @param model a {@link org.apache.wicket.model.IModel} object.
	 * @param <T> a T object.
	 */
	public WebFlowAjaxLink(String id, IModel<T> model)
	{
		super(id, model);
		this.eventId = id;

	}

	/**
	 * <p>Constructor for WebFlowAjaxLink.</p>
	 *
	 * @param id Markup id, also sets the flows event id to be triggered. Call setEventId() if it needs to be different.
	 */
	public WebFlowAjaxLink(String id)
	{
		super(id);
		this.eventId = id;

	}

	/** {@inheritDoc} */
	@Override
	public void onClick(AjaxRequestTarget target)
	{
		FlowUtils.pageFlowTransition(this,target, getEventId());
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
	 * @return a {@link org.wicketstuff.webflow.components.WebFlowAjaxLink} object.
	 */
	public WebFlowAjaxLink<T> setEventId(String eventId)
	{
		this.eventId = eventId;
		return this;
	}
}

