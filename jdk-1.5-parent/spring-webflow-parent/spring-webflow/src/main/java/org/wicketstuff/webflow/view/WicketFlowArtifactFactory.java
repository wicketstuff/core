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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.binding.mapping.Mapper;
import org.springframework.webflow.core.collection.AttributeMap;
import org.springframework.webflow.engine.*;
import org.springframework.webflow.engine.builder.FlowArtifactFactory;
import org.springframework.webflow.execution.Action;
import org.springframework.webflow.execution.ViewFactory;
import org.wicketstuff.webflow.PageFlowConstants;
import org.wicketstuff.webflow.state.WicketActionState;
import org.wicketstuff.webflow.state.WicketEndState;
import org.wicketstuff.webflow.state.WicketViewState;


/**
 * WicketFlowArtifactFactory is an extension of the default FlowArtifactFactory that provides methods to create Wicket Action State,
 * Wicket View State, Wicket End State.
 *
 * @author Clint Checketts, Florian Braun, Doug Hall, Subramanian Murali
 * @version $Id: $
 */
public class WicketFlowArtifactFactory extends FlowArtifactFactory
{
	private static final Logger LOG = LoggerFactory.getLogger(WicketFlowArtifactFactory.class);
	
	/* (non-Javadoc)
	 * @see org.springframework.webflow.engine.builder.FlowArtifactFactory#createActionState(java.lang.String, org.springframework.webflow.engine.Flow, org.springframework.webflow.execution.Action[], org.springframework.webflow.execution.Action[], org.springframework.webflow.engine.Transition[], org.springframework.webflow.engine.FlowExecutionExceptionHandler[], org.springframework.webflow.execution.Action[], org.springframework.webflow.core.collection.AttributeMap)
	 */
	/** {@inheritDoc} */
	@Override
	public State createActionState(String id, Flow flow, Action[] entryActions, Action[] actions,
			Transition[] transitions, FlowExecutionExceptionHandler[] exceptionHandlers, Action[] exitActions,
			AttributeMap attributes)
	{
		WicketActionState actionState = new WicketActionState(flow, id);
		actionState.getActionList().addAll(actions);
		configureCommonProperties(actionState, entryActions, transitions, exceptionHandlers, exitActions, attributes);
		return actionState;
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.webflow.engine.builder.FlowArtifactFactory#createViewState(java.lang.String, org.springframework.webflow.engine.Flow, org.springframework.webflow.engine.ViewVariable[], org.springframework.webflow.execution.Action[], org.springframework.webflow.execution.ViewFactory, java.lang.Boolean, boolean, org.springframework.webflow.execution.Action[], org.springframework.webflow.engine.Transition[], org.springframework.webflow.engine.FlowExecutionExceptionHandler[], org.springframework.webflow.execution.Action[], org.springframework.webflow.core.collection.AttributeMap)
	 */
	/** {@inheritDoc} */
	@Override	
	public State createViewState(String id, Flow flow, ViewVariable[] variables, Action[] entryActions,
			ViewFactory viewFactory, Boolean redirect, boolean popup, Action[] renderActions, Transition[] transitions,
			FlowExecutionExceptionHandler[] exceptionHandlers, Action[] exitActions, AttributeMap attributes) 
	{
		String progressBarText = getStepText(attributes);
		Integer stepNumber = getStepNumber(id, attributes);
		
		WicketViewState viewState = new WicketViewState(flow, id, viewFactory,progressBarText,stepNumber);
		viewState.addVariables(variables);
		viewState.setRedirect(redirect);
		viewState.setPopup(popup);
		
		viewState.getRenderActionList().addAll(renderActions);
		configureCommonProperties(viewState, entryActions, transitions, exceptionHandlers, exitActions, attributes);
		return viewState;
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.webflow.engine.builder.FlowArtifactFactory#createEndState(java.lang.String, org.springframework.webflow.engine.Flow, org.springframework.webflow.execution.Action[], org.springframework.webflow.execution.Action, org.springframework.binding.mapping.Mapper, org.springframework.webflow.engine.FlowExecutionExceptionHandler[], org.springframework.webflow.core.collection.AttributeMap)
	 */
	/** {@inheritDoc} */
	@Override
	public State createEndState(String id, Flow flow, Action[] entryActions, Action finalResponseAction,
			Mapper outputMapper, FlowExecutionExceptionHandler[] exceptionHandlers, AttributeMap attributes) 
	{
		String progressBarText = getStepText(attributes);
		Integer stepNumber = getStepNumber(id, attributes);
		
		WicketEndState endState = new WicketEndState(flow, id,progressBarText, stepNumber);
		
		if (finalResponseAction != null) 
		{
			endState.setFinalResponseAction(finalResponseAction);
		}
		if (outputMapper != null) 
		{
			endState.setOutputMapper(outputMapper);
		}
		configureCommonProperties(endState, entryActions, exceptionHandlers, attributes);
		return endState;
	}

	/**
	 * Retrieves the Text for the progress bar step
	 * 
	 * @param attributes
	 * @return
	 */
	private String getStepText(AttributeMap attributes)
	{
		return attributes.getString(PageFlowConstants.PROGRESS_BAR_TEXT);
	}
	

	/**
	 * Gets the step number for the progress bar step
	 * 
	 * @param id
	 * @param attributes
	 * @return
	 */
	private Integer getStepNumber(String id, AttributeMap attributes)
	{
		Integer stepNumber = 0;
		try
		{
			String stepNumberInput = attributes.getString(PageFlowConstants.PROGRESS_BAR_STEP_NUMBER,"0");
			stepNumber = Integer.valueOf(stepNumberInput);
			
		}
		catch(NumberFormatException e)
		{
			LOG.warn("Step number for Flow step "+id+" isn't an integer. Value was: "+ e.getMessage());
			stepNumber = 0;
		}
		return stepNumber;
	}
	
	/**
	 * Configure common properties for a transitionable state.
	 */
	private void configureCommonProperties(TransitionableState state, Action[] entryActions, Transition[] transitions,
			FlowExecutionExceptionHandler[] exceptionHandlers, Action[] exitActions, AttributeMap attributes) 
	{
		configureCommonProperties(state, entryActions, exceptionHandlers, attributes);
		state.getTransitionSet().addAll(transitions);
		state.getExitActionList().addAll(exitActions);
	}
	
	/**
	 * Configure common properties for a state.
	 */
	private void configureCommonProperties(State state, Action[] entryActions,
			FlowExecutionExceptionHandler[] exceptionHandlers, AttributeMap attributes) 
	{
		state.getEntryActionList().addAll(entryActions);
		state.getExceptionHandlerSet().addAll(exceptionHandlers);
		state.getAttributes().putAll(attributes);
	}	
}

