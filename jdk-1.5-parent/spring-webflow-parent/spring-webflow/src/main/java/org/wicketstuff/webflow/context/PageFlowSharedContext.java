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
package org.wicketstuff.webflow.context;

import org.apache.wicket.Component;

/**
 * PageFlowSharedContext contains page flow related contextual information shared by Wicket and Web Flow in ThreadLocal.
 *
 * When a flow (in Web Flow) is launched/resumed by Wicket, Web Flow sets the view state class name in PageFlowSharedContext
 * that will be later picked up by Wicket as the rendered view class name.
 *
 * When a flow needs to be resumed, Wicket sets the event id in PageFlowSharedContext which will be picked up by Spring Web Flow
 * as the transitioning event.
 *
 * @author Clint Checketts, Florian Braun, Doug Hall, Subramanian Murali
 * @version $Id: $
 */
public class PageFlowSharedContext 
{
	//Attribute that represents the event id for which the matching transition needs to be executed as part of the 
	//flow execution.
	//This attribute will be set the component that generates the event and will be returned by the getEventId()
	//method of the WicketView class.
	private String eventId;
	
	//Attribute that represents the rendered view state class name.
	//This attribute will be set by the WebFlowExecutionListener after the view is rendered by Web Flow.
	//This attribute will be used by Wicket to render the appropriate page or panel.
	private Class<? extends Component> renderedViewStateClass;

	/**
	 * Method that returns the event id.
	 *
	 * @return String - Event Id.
	 */
	public String getEventId() 
	{
		return eventId;
	}

	/**
	 * Method that sets the event id.
	 *
	 * @param eventId - Event Id.
	 */
	public void setEventId(String eventId)
	{
		this.eventId = eventId;
	}

	/**
	 * Method that returns the rendered view state class name.
	 *
	 * @return String - Rendered view state class name.
	 */
	public Class<? extends Component> getRenderedViewStateClass() 
	{
		return renderedViewStateClass;
	}

	/**
	 * Method that sets the rendered view state class name.
	 *
	 * @param renderedViewStateClass a {@link java.lang.Class} object.
	 */
	public void setRenderedViewStateClass(Class<? extends Component> renderedViewStateClass) 
	{
		this.renderedViewStateClass = renderedViewStateClass;
	}
}
