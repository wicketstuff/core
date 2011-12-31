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
 * File: WebFlowAjaxButton
 *****************************************************************************/
package org.wicketstuff.webflow.components;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;

import org.wicketstuff.webflow.FlowUtils;


/**
 * Ajax button that uses Web Flow to determine the next panel to be displayed in a Panel Flow.
 *
 * @author Clint Checketts, Florian Braun, Doug Hall, Subramanian Murali
 * @version $Id: $
 */
public class WebFlowAjaxButton extends AjaxButton
{
	private static final long	serialVersionUID	= 1L;
	
	//Attribute that represents the event id for web flow/page flow transition
	//This is an optional attribute.
	//If not set, markup id will be used as the event id for web flow/page flow transition
	private String eventId;

	
	
	/**
	 * <p>Constructor for WebFlowAjaxButton.</p>
	 *
	 * @param id a {@link java.lang.String} object.
	 * @param form a {@link org.apache.wicket.markup.html.form.Form} object.
	 */
	public WebFlowAjaxButton(String id, Form<?> form)
	{
		super(id, form);
		this.eventId= id;
	}

	/**
	 * <p>Constructor for WebFlowAjaxButton.</p>
	 *
	 * @param id a {@link java.lang.String} object.
	 * @param model a {@link org.apache.wicket.model.IModel} object.
	 * @param form a {@link org.apache.wicket.markup.html.form.Form} object.
	 */
	public WebFlowAjaxButton(String id, IModel<String> model, Form<?> form)
	{
		super(id, model, form);
		this.eventId= id;
	}

	/**
	 * <p>Constructor for WebFlowAjaxButton.</p>
	 *
	 * @param id a {@link java.lang.String} object.
	 * @param model a {@link org.apache.wicket.model.IModel} object.
	 */
	public WebFlowAjaxButton(String id, IModel<String> model)
	{
		super(id, model);
		this.eventId= id;
	}

	/**
	 * <p>Constructor for WebFlowAjaxButton.</p>
	 *
	 * @param id a {@link java.lang.String} object.
	 */
	public WebFlowAjaxButton(String id)
	{
		super(id);
		this.eventId= id;
	}

	/* (non-Javadoc)
	 * @see org.apache.wicket.ajax.markup.html.form.AjaxButton#onError(
	 *   org.apache.wicket.ajax.AjaxRequestTarget, org.apache.wicket.markup.html.form.Form)
	 */
	/** {@inheritDoc} */
	@Override	
	protected void onError(AjaxRequestTarget target, Form<?> form)
	{
		WebFlowPanel flowPanel = (WebFlowPanel) form.getParent();
		target.addComponent(flowPanel);
	}	

	/** {@inheritDoc} */
	@Override
	protected void onSubmit(AjaxRequestTarget target, Form<?> form)
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
	 * @return a {@link org.wicketstuff.webflow.components.WebFlowAjaxButton} object.
	 */
	public WebFlowAjaxButton setEventId(String eventId)
	{
		this.eventId = eventId;
		return this;
	}	
}

