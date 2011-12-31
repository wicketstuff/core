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
 * File: WicketActionState
 *****************************************************************************/
package org.wicketstuff.webflow.state;

import java.util.Iterator;

import org.springframework.core.style.StylerUtils;
import org.springframework.webflow.engine.*;
import org.springframework.webflow.execution.*;
import org.wicketstuff.webflow.PageFlowConstants;


/**
 * The default ActionState that comes with the Spring Web Flow distribution requires that transitions need to be specified
 * for all possible outcome for a conditional expression.  For example, if there is a conditional expression based on the
 * 'country' of a member, then the transitions will need to be defined for all possible countries that could be returned
 * by the conditional expression as illustrated below:
 *
 * <action-state id="checkCountry">
 * 		<evaluate expression="externalContext.session.flowState.modelObject.personalDetails.country" />
 * 		<transition on="UK" to="Page1" />
 * 		<transition on="USA" to="DefaultPage" />
 * 		<transition on="GERMANY" to="DefaultPage" />
 * 		..
 * 		..
 * 		<transition on="VENEZUALA" to="DefaultPage" />
 * 		<transition on="NICARAGUA" to="DefaultPage" />
 * 		<transition on="TRINIDAD" to="DefaultPage" />
 * </action-state>
 *
 * Wicket Action State provides the flexibility to specify a DEFAULT transition for a conditional
 * expression evaluation as illustrated in the example below:
 *
 * <action-state id="checkCountry">
 * 		<evaluate expression="externalContext.session.flowState.modelObject.personalDetails.country" />
 * 		<transition on="UK" to="Page1" />
 * 		<transition on="DEFAULT" to="DefaultPage" />
 * </action-state>
 *
 * @author Clint Checketts, Florian Braun, Doug Hall, Subramanian Murali
 * @version $Id: $
 */
public class WicketActionState extends ActionState
{
	/**
	 * Constructor.
	 *
	 * @param flow - Flow instance.
	 * @param id  - Identifier for the Action State.
	 * @throws java.lang.IllegalArgumentException if any.
	 */
	public WicketActionState(Flow flow, String id) throws IllegalArgumentException 
	{
		super(flow, id);
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.webflow.engine.ActionState#doEnter(org.springframework.webflow.engine.RequestControlContext)
	 */
	/** {@inheritDoc} */
	protected void doEnter(RequestControlContext context) throws FlowExecutionException 
	{
		int executionCount = 0;
		String[] eventIds = new String[getActionList().size()];
		@SuppressWarnings("unchecked")
		Iterator<Action> it = getActionList().iterator();
		
		while (it.hasNext()) 
		{
			Action action = it.next();
			Event event = ActionExecutor.execute(action, context);
			if (event != null) 
			{
				eventIds[executionCount] = event.getId();
				try 
				{
					context.handleEvent(event);
					return;
				} 
				catch (NoMatchingActionResultTransitionException e) 
				{
					try
					{
						Event event1 = new Event(event.getSource(), PageFlowConstants.PAGE_FLOW_DEFAULT_TRANSITION);
						context.handleEvent(event1);
						return;
					}
					catch (NoMatchingActionResultTransitionException e1) 
					{
						//Do nothing
					}
				}
			} 
			else 
			{
				eventIds[executionCount] = null;
			}
			executionCount++;
		}
		if (executionCount > 0) 
		{
			throw new NoMatchingTransitionException(getFlow().getId(), getId(), context.getCurrentEvent(),
					"No transition was matched on the event(s) signaled by the [" + executionCount
						+ "] action(s) that executed in this action state '" + getId() + "' of flow '"
						+ getFlow().getId() + "'; transitions must be defined to handle action result outcomes -- "
						+ "possible flow configuration error? Note: the eventIds signaled were: '"
						+ StylerUtils.style(eventIds)
						+ "', while the supported set of transitional criteria for this action state is '"
						+ StylerUtils.style(getTransitionSet().getTransitionCriterias()) + "'");
		} 
		else 
		{
			throw new IllegalStateException(
					"No actions were executed, thus I cannot execute any state transition "
						+ "-- programmer configuration error; make sure you add at least one action to this state's action list");
		}
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.webflow.engine.ActionState#getRequiredTransition(org.springframework.webflow.execution.RequestContext)
	 */
	/** {@inheritDoc} */
	public Transition getRequiredTransition(RequestContext context) throws NoMatchingTransitionException {
		Transition transition = getTransitionSet().getTransition(context);
		if (transition == null) {
			throw new NoMatchingActionResultTransitionException(this, context.getCurrentEvent());
		}
		return transition;
	}	
	
	/**
	 * Local "no transition found" exception used to report that an action result could not be mapped to a state
	 * transition.
	 */
	private static class NoMatchingActionResultTransitionException extends NoMatchingTransitionException 
	{
		private static final long	serialVersionUID	= 1L;

		/**
		 * Creates a new exception.
		 * @param state the action state
		 * @param resultEvent the action result event
		 */
		public NoMatchingActionResultTransitionException(ActionState state, Event resultEvent) 
		{
			super(state.getFlow().getId(), state.getId(), resultEvent,
					"Cannot find a transition matching an action result event; continuing with next action...");
		}
	}	
}

