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
package org.wicketstuff.webflow.view;

import java.io.IOException;
import java.io.Serializable;

import org.apache.wicket.Component;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;
import org.springframework.webflow.execution.View;
import org.wicketstuff.webflow.context.PageFlowSharedContext;
import org.wicketstuff.webflow.context.PageFlowSharedContextHolder;


/**
 * Wicket View Implementation for Web Flow.
 *
 * @author Clint Checketts, Florian Braun, Doug Hall, Subramanian Murali
 * @version $Id: $
 */
public class WicketView implements View
{
	//Spring Web Flow Request Context that provides access to the outer world
	private RequestContext requestContext;
	
	//View Id
	private Class<? extends Component> viewId;
	
	//Event Id
	private String eventId;
	
	private boolean userEventProcessed;	
	
	/**
	 * Constructor.
	 *
	 * @param requestContext a {@link org.springframework.webflow.execution.RequestContext} object.
	 * @param viewId a {@link java.lang.Class} object.
	 */
	public WicketView(RequestContext requestContext, Class<? extends Component> viewId)
	{
		this.requestContext = requestContext;
		this.viewId = viewId;
	}

	/* (non-Javadoc)
	 * @see org.springframework.webflow.execution.View#render()
	 */
	/**
	 * <p>render.</p>
	 *
	 * @throws java.io.IOException if any.
	 */
	public void render() throws IOException
	{
		//Spring Web Flow will not handle the rendering of pages / components for Wicket.
		//Set the view State Class in the WicketWebFlowSharedContext
		
		//TODO move to state's doEnter
		PageFlowSharedContextHolder.getSharedContext().setRenderedViewStateClass(viewId);	
	}

	/* (non-Javadoc)
	 * @see org.springframework.webflow.execution.View#getUserEventState()
	 */
	/**
	 * <p>getUserEventState.</p>
	 *
	 * @return a {@link java.io.Serializable} object.
	 */
	public Serializable getUserEventState()
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.webflow.execution.View#hasFlowEvent()
	 */
	/**
	 * <p>hasFlowEvent.</p>
	 *
	 * @return a boolean.
	 */
	public boolean hasFlowEvent()
	{
		return userEventProcessed;
	}

	/* (non-Javadoc)
	 * @see org.springframework.webflow.execution.View#processUserEvent()
	 */
	/**
	 * <p>processUserEvent.</p>
	 */
	public void processUserEvent()
	{
		String eventId = getEventId();
		if (eventId == null) 
		{
			return;
		}
		userEventProcessed = true;
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.webflow.execution.View#getFlowEvent()
	 */
	/**
	 * <p>getFlowEvent.</p>
	 *
	 * @return a {@link org.springframework.webflow.execution.Event} object.
	 */
	public Event getFlowEvent()
	{
		return new Event(this, getEventId());
	}

	/* (non-Javadoc)
	 * @see org.springframework.webflow.execution.View#saveState()
	 */
	/**
	 * <p>saveState.</p>
	 */
	public void saveState()
	{
	}

	/* (non-Javadoc)
	 * @see org.springframework.webflow.execution.View#userEventQueued()
	 */
	/**
	 * <p>userEventQueued.</p>
	 *
	 * @return a boolean.
	 */
	public boolean userEventQueued()
	{
		return !userEventProcessed && getEventId() != null;
	}
	
	/**
	 * Returns the id of the user event being processed.
	 *
	 * @return the user event
	 */
	protected String getEventId() 
	{
		if (eventId == null) 
		{
			PageFlowSharedContext context = PageFlowSharedContextHolder.getSharedContext();
			eventId = context.getEventId();
		}
		return this.eventId;
	}	
	
	/**
	 * Return the Wicket view Id.
	 *
	 * @return String - Wicket View Id.
	 */
	public Class<? extends Component> getViewId()
	{
		return viewId;
	}

	/**
	 * Return the Spring Web Flow Request Context.
	 *
	 * @return a {@link org.springframework.webflow.execution.RequestContext} object.
	 */
	public RequestContext getRequestContext() 
	{
		return requestContext;
	}
}
