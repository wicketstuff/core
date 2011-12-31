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

/**
 *****************************************************************************
 * File: FullPageRefreshFlowExecutionListener.java
 *****************************************************************************
 *
 * @author Clint Checketts, Florian Braun, Doug Hall, Subramanian Murali
 * @version $Id: $
 */
package org.wicketstuff.webflow.controller;

import org.springframework.webflow.core.collection.AttributeMap;
import org.springframework.webflow.core.collection.MutableAttributeMap;
import org.springframework.webflow.definition.FlowDefinition;
import org.springframework.webflow.definition.StateDefinition;
import org.springframework.webflow.definition.TransitionDefinition;
import org.springframework.webflow.execution.*;
public class FullPageRefreshFlowExecutionListener implements
		FlowExecutionListener
{

	/** {@inheritDoc} */
	public void eventSignaled(RequestContext context, Event event)
	{
		// TODO Auto-generated method stub

	}

	/** {@inheritDoc} */
	public void exceptionThrown(RequestContext context,
			FlowExecutionException exception)
	{
		// TODO Auto-generated method stub

	}

	/** {@inheritDoc} */
	public void paused(RequestContext context)
	{
		// TODO Auto-generated method stub

	}

	/** {@inheritDoc} */
	public void requestProcessed(RequestContext context)
	{
		// TODO Auto-generated method stub

	}

	/** {@inheritDoc} */
	public void requestSubmitted(RequestContext context)
	{
		// TODO Auto-generated method stub

	}

	/** {@inheritDoc} */
	public void resuming(RequestContext context)
	{
		// TODO Auto-generated method stub

	}

	/** {@inheritDoc} */
	public void sessionCreating(RequestContext context,
			FlowDefinition definition)
	{

	}

	/** {@inheritDoc} */
	public void sessionEnded(RequestContext context, FlowSession session,
			String outcome, AttributeMap output)
	{
		// TODO Auto-generated method stub

	}

	/** {@inheritDoc} */
	public void sessionEnding(RequestContext context, FlowSession session,
			String outcome, MutableAttributeMap output)
	{
		// TODO Auto-generated method stub

	}

	/** {@inheritDoc} */
	public void sessionStarted(RequestContext context, FlowSession session)
	{
		// TODO Auto-generated method stub

	}

	/** {@inheritDoc} */
	public void sessionStarting(RequestContext context, FlowSession session,
			MutableAttributeMap input)
	{
		// TODO Auto-generated method stub

	}

	/** {@inheritDoc} */
	public void stateEntered(RequestContext context,
			StateDefinition previousState, StateDefinition state)
	{
		// TODO Auto-generated method stub

	}

	/** {@inheritDoc} */
	public void stateEntering(RequestContext context, StateDefinition state)
			throws EnterStateVetoException
	{
	}

	/** {@inheritDoc} */
	public void transitionExecuting(RequestContext context,
			TransitionDefinition transition)
	{
		// TODO Auto-generated method stub

	}

	/** {@inheritDoc} */
	public void viewRendered(RequestContext context, View view,
			StateDefinition viewState)
	{
		// TODO Auto-generated method stub

	}

	/** {@inheritDoc} */
	public void viewRendering(RequestContext context, View view,
			StateDefinition viewState)
	{
		// TODO Auto-generated method stub

	}

}

