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
/*******************************************************************************
 * File: PageFlowSession
 ******************************************************************************/
package org.wicketstuff.webflow.session;

import org.apache.wicket.Request;
import org.apache.wicket.protocol.http.WebSession;

/**
 * App Session class for a Page Flow / Panel Flow based application.
 * All Page Flow / Panel Flow applications should extend this session class to store the
 * required data objects.
 *
 * @author Clint Checketts, Florian Braun, Doug Hall, Subramanian Murali
 * @version $Id: $
 */
public class PageFlowSession<T> extends WebSession
{
	private static final long	serialVersionUID	= 1L;

	private FlowState<T> flowState;
	

	/**
	 * <p>Constructor for PageFlowSession.</p>
	 *
	 * @param request a {@link org.apache.wicket.Request} object.
	 * @param <T> a T object.
	 */
	public PageFlowSession(Request request)
	{
		super(request);
		flowState = new FlowState<T>();
		onFlowStateInit(flowState); //NOPMD This is a valid extension point
	}
	
	
	/**
	 * This API hook is to allow the application to provide any setup logic to be called
	 * when the flow is first initialized or reset
	 *
	 * @param flowState2 a {@link org.wicketstuff.webflow.session.FlowState} object.
	 */
	protected void onFlowStateInit(FlowState<T> flowState2)
	{
		// Application logic
		
	}


	/**
	 * <p>get.</p>
	 *
	 * @return a {@link org.wicketstuff.webflow.session.PageFlowSession} object.
	 */
	public static PageFlowSession<?> get(){
		return (PageFlowSession<?>)WebSession.get();
	}

	/**
	 * <p>Getter for the field <code>flowState</code>.</p>
	 *
	 * @return a {@link org.wicketstuff.webflow.session.FlowState} object.
	 */
	public FlowState<T> getFlowState()
	{
		return flowState;
	}
	
	/**
	 * <p>resetFlowState.</p>
	 */
	public void resetFlowState(){
		flowState = new FlowState<T>();
		onFlowStateInit(flowState);
	}

	/**
	 * <p>Setter for the field <code>flowState</code>.</p>
	 *
	 * @param flowstate a {@link org.wicketstuff.webflow.session.FlowState} object.
	 */
	public void setFlowState(FlowState<T> flowstate)
	{
		this.flowState = flowstate;
	}
	
	/** {@inheritDoc} */
	@Override
	protected void detach()
	{
		if(flowState != null && flowState.getModel() != null){
			flowState.getModel().detach();
		}
		super.detach();
	}
}

