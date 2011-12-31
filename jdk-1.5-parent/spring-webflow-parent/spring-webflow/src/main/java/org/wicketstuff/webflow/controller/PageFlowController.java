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
package org.wicketstuff.webflow.controller;

import java.util.*;

import org.apache.wicket.Component;
import org.apache.wicket.util.string.Strings;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.webflow.engine.Flow;
import org.springframework.webflow.engine.State;
import org.wicketstuff.webflow.PageFlowConstants;
import org.wicketstuff.webflow.context.PageFlowSharedContext;
import org.wicketstuff.webflow.context.PageFlowSharedContextHolder;
import org.wicketstuff.webflow.session.PageFlowSession;
import org.wicketstuff.webflow.session.ProgressLink;
import org.wicketstuff.webflow.state.IProgressBarState;
import org.wicketstuff.webflow.state.WicketEndState;
import org.wicketstuff.webflow.state.WicketViewState;


/**
 * PageFlowController provides methods to launch a new application flow and
 * to resume an existing application flow and it uses SpringWebFlowFacade to
 * communicate with Spring Web Flow.
 *
 * @author Clint Checketts, Florian Braun, Doug Hall, Subramanian Murali
 * @version $Id: $
 */
public class PageFlowController
{
	private static final Logger LOG = LoggerFactory.getLogger(PageFlowController.class);
	
	/**
	 * Method that starts a new application flow for the input flow id and
	 * returns the newly launched view state class name. After starting a new
	 * application flow, this method returns the
	 *
	 * @param flowId -
	 *            Flow id for which the application flow needs to be started.
	 * @return String - Launched view state class name.
	 */
	public static Class<? extends Component> startApplicationFlow(
			String flowId)
	{
		// Launched View State Class
		
		Class<? extends Component> launchedViewStateClass = null;

		if (!Strings.isEmpty(flowId))
		{
			// Initialize the PageFlowContext in ThreadLocal
			PageFlowSharedContextHolder.initializeSharedContext();

			// Instantiate a new Spring Web Flow Facade
			SpringWebFlowFacade webFlowFacade = new SpringWebFlowFacade();

			// Start a new Application Flow and retrieve the launch view state
			String launchViewState = webFlowFacade.launchWebFlowExecution(flowId);

			// Retrieve the rendered view state class from shared context
			PageFlowSharedContext sharedContext = PageFlowSharedContextHolder
					.getSharedContext();
			launchedViewStateClass = sharedContext.getRenderedViewStateClass();

			// Clear the PageFlowContext in ThreadLocal
			PageFlowSharedContextHolder.clearSharedContext();

			// Retrieve the Page Flow App Session instance
			PageFlowSession<?> pageFlowAppSession = PageFlowSession.get();

			// Set the retrieved launch view state as the current view state
			pageFlowAppSession.getFlowState().setCurrentViewState(
					launchViewState);

			// Set the retrieved launch view state as the start view state
			pageFlowAppSession.getFlowState()
					.setStartViewState(launchViewState);

			// Set the launched view state class name in the session
			pageFlowAppSession.getFlowState().setStartViewClass(
					launchedViewStateClass);

			// Set the last rendered view state class name in the session
			pageFlowAppSession.getFlowState().setLastRenderedViewClass(
					launchedViewStateClass);
		}

		return launchedViewStateClass;
	}

	/**
	 * Method that resumes an application flow for the input event id from the
	 * input view state and returns the transitioned view state class name.
	 *
	 * @param inputViewState -
	 *            View state from which the application flow needs to be
	 *            transitioned.
	 * @param eventId -
	 *            Event id for which the application flow needs to be
	 *            transitioned.
	 * @return String - Transitioned view state class name.
	 */
	public static Class<? extends Component> resumeApplicationFlow(
			String inputViewState, String eventId)
	{
		// Transitioned View State Class
		Class<? extends Component> transitionedViewStateClass = null;

		if (!Strings.isEmpty(inputViewState)
				&& !Strings.isEmpty(eventId))
		{
			// Initialize the PageFlowContext in ThreadLocal
			PageFlowSharedContextHolder.initializeSharedContext();

			// Set the input event id in the PageFlowSharedContext
			PageFlowSharedContextHolder.getSharedContext().setEventId(eventId);

			// Construct a new Spring Web Flow Facade
			SpringWebFlowFacade webFlowFacade = new SpringWebFlowFacade();

			// Resume the Application Flow and retrieve the transitioned view
			// state
			String transitionedViewState = webFlowFacade
					.resumeWebFlowExecution(inputViewState);

			// Retrieve the rendered view state class from shared context
			PageFlowSharedContext sharedContext = PageFlowSharedContextHolder
					.getSharedContext();
			transitionedViewStateClass = sharedContext
					.getRenderedViewStateClass();

			// Clear the PageFlowContext in ThreadLocal
			PageFlowSharedContextHolder.clearSharedContext();

			// Retrieve the Page Flow App Session instance
			PageFlowSession<?> pageFlowAppSession = PageFlowSession.get();

			// Set the transitioned view state as the current view state in
			// session
			pageFlowAppSession.getFlowState().setCurrentViewState(
					transitionedViewState);

			// Set the last rendered view state class in the session
			pageFlowAppSession.getFlowState().setLastRenderedViewClass(
					transitionedViewStateClass);

			// If the transitioned view state is DONE, set the final view class
			// name as the transitioned view state class name
			if (PageFlowConstants.PAGE_FLOW_FINAL_STATE_DONE
					.equals(transitionedViewState))
			{
				pageFlowAppSession.getFlowState().setFinalViewClass(
						transitionedViewStateClass);
			}
		}

		return transitionedViewStateClass;
	}

	/**
	 * Method that populates the progress bar states in the PageFlowSession
	 * based on the input view state for a page flow / panel flow application.
	 *
	 * @return a {@link java.util.List} object.
	 */
	public static List<ProgressLink> populateProgressBarStates()
	{
		String viewStateString = PageFlowSession.get().getFlowState().getStartViewState();
		
		// Retrieve the flow instance for the input view state
		Flow flow = new SpringWebFlowFacade().getFlow(viewStateString);
		Map<Integer, State> progressBarStateMap = collectProgressStates(flow);
		
		Iterator<Integer> iterator = progressBarStateMap.keySet().iterator();
		List<ProgressLink> progressLinks = new ArrayList<ProgressLink>();
		
		// counter used to calculate the step number used in the progress bar 
		int stepCounter = 0;
		
		while (iterator.hasNext())
		{
			Integer stepNumber = iterator.next();
			State progressBarState = progressBarStateMap.get(stepNumber);

			if (progressBarState instanceof IProgressBarState)
			{
				IProgressBarState viewState = (IProgressBarState) progressBarState;

				String progressBarText = viewState.getProgressBarText();
				
				if ( (!Strings.isEmpty(viewState.getProgressBarText())) && 
					 (!viewState.getProgressBarText().equals(progressBarText)) )
				{
					stepCounter++;
				}

				ProgressLink progressLink = new ProgressLink(null, stepCounter, progressBarText);
				progressLink.setFlowStepNumber(viewState.getStepNumber());
				
				progressLinks.add(progressLink);
			}
		}

		// Populate the ProgressBarLinks
		return progressLinks;
	}
	

	private static Map<Integer, State> collectProgressStates(Flow flow)
	{
		boolean endStateFound = false;
		Map<Integer, State> progressBarStateMap = new TreeMap<Integer, State>();
		
		// Retrieve the array of all state ids from the flow instance
		String[] stateIds =null;
		if(flow != null){
			stateIds = flow.getStateIds();
		}

		if (stateIds == null || stateIds.length == 0)
		{
			return progressBarStateMap;
		}
		
		for (int i = 0; i < stateIds.length; i++)
		{
			State state = flow.getStateInstance(stateIds[i]);

			if (state instanceof WicketViewState)
			{
				WicketViewState viewState = (WicketViewState) state;

				Integer stepNumber = viewState.getStepNumber();
				String newProgressBarText = viewState.getProgressBarText();

				if (Strings.isEmpty(newProgressBarText))
				{
					continue;
				}

				if (progressBarStateMap.containsKey(stepNumber))
				{
					State existingState = (State) progressBarStateMap
							.get(stepNumber);

					if (existingState instanceof WicketEndState)
					{
						LOG.error("A view State and a end state have been configured with the same step number: State ID:"+existingState.getId());
						break;
					}

					String existingProgressBarText = ((WicketViewState) existingState)
							.getProgressBarText();

					if (!newProgressBarText.equals(existingProgressBarText))
					{
						LOG.error("Step Number {} has been configured with two different progress bar text",viewState.getStepNumber());
						break;
					}
				}
				else
				{
					progressBarStateMap.put(viewState.getStepNumber(),
							viewState);
				}
			}
			if (state instanceof WicketEndState)
			{
				WicketEndState endState = (WicketEndState) state;

				Integer stepNumber = endState.getStepNumber();
				String newProgressBarText = endState.getProgressBarText();

				if (Strings.isEmpty(newProgressBarText))
				{
					LOG.error("Progress Bar Text not specified for the End State : {}", endState.getId());
					continue;
				}

				if (!endStateFound)
				{
					if (progressBarStateMap.containsKey(stepNumber))
					{
						State existingState = (State) progressBarStateMap
								.get(stepNumber);

						if (existingState instanceof WicketViewState)
						{
							LOG.error("A view State and a end state have been configured with the same step number. State ID: {}",existingState.getId());
							break;
						}
					}
					else
					{
						progressBarStateMap.put(stepNumber, endState);
						endStateFound = true;
					}
				}
				else
				{
					LOG.error("There are two end states configured for the flow {}", flow.getId());
					break;
				}
			}
		}
		return progressBarStateMap;
	}
}
