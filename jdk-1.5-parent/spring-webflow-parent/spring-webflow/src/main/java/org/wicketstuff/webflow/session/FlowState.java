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
 * @author Clint Checketts, Florian Braun, Doug Hall, Subramanian Murali
 * @version $Id: $
 */
package org.wicketstuff.webflow.session;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import org.wicketstuff.webflow.controller.PageFlowController;
import org.wicketstuff.webflow.conversation.PageFlowConversationContainer;
public class FlowState<T> implements Serializable
{
	private static final long	serialVersionUID	= 1L;

	public enum StateType{
		VIEWSTATE,
		ENDSTATE
	}
	
	private class StateCache implements Serializable{
		private static final long	serialVersionUID	= 1L;
		public String	id;
		public int progressLinksStepNumber;
	}
	
	private List<StateCache> statesVisited;
	
	/**
	 * <p>Constructor for FlowState.</p>
	 *
	 * @param <T> a T object.
	 */
	public FlowState()
	{
		statesVisited = new ArrayList<StateCache>();
	}
	
	
	//	TODO This attribute doesn't work with Conditional Starting Points
	//Variable that represents the start view state in web flow
	private String startViewState;
	
	//Variable that represents the current view state in web flow
	private String currentViewState;
	
	//Variable that represents the current view state in web flow
	private String currentViewStateId;
	
	//Variable to let you know which step you are in, used when displaying progress links
	private int currentViewStepNumber;
	
	//TODO This attribute doesn't work with Conditional Starting Points	
	//Variable that represents the start view class of the application for the user
	private Class<? extends Component> startViewClass;
	
	//Variable that represents the last rendered view class for the user
	private Class<? extends Component> lastRenderedViewClass;
	
	//Variable that represents the final view class of the application for the user
	private Class<? extends Component> finalViewClass;
	
	//Attribute that represents the web flow conversation
	private PageFlowConversationContainer pageFlowConversationContainer;
	
	//Attribute that represents whether the session contains any page flow specific error messages
	private boolean hasPageFlowErrorMessages = false;
	
	//Attribute that represents the list of states to be displayed in the progress bar
	private List<ProgressLink> progressLinks;
	
	//Model that holds the overall object for the flow
	private IModel<T> model;
	
	/**
	 * <p>Getter for the field <code>model</code>.</p>
	 *
	 * @return a {@link org.apache.wicket.model.IModel} object.
	 */
	public IModel<T> getModel()
	{
		return model;
	}

	/**
	 * <p>Setter for the field <code>model</code>.</p>
	 *
	 * @param model a {@link org.apache.wicket.model.IModel} object.
	 */
	public void setModel(IModel<T> model)
	{
		this.model = model;
	}

	/**
	 * <p>getModelObject.</p>
	 *
	 * @return a T object.
	 */
	public T getModelObject()
	{
		if(model != null)
		{
			return model.getObject();
		}
		else 
		{
			return null;
		}
	}

	/**
	 * <p>setModelObject.</p>
	 *
	 * @param object a T object.
	 */
	public void setModelObject(T object)
	{
		// Check whether anything can be set at all
		if (model == null)
		{
			throw new IllegalStateException(
				"Attempt to set model object on null model of FlowState");
		}
		
		this.model.setObject(object);
	}
	
	
	
	
	
	
	
	
	/**
	 * Method that returns the current view state in web flow.
	 *
	 * @return String - Current View State.
	 */
	public String getCurrentViewState() 
	{
		return currentViewState;
	}

	/**
	 * Method that sets the current view state in web flow.
	 *
	 * @param currentViewState - Current View State.
	 */
	public void setCurrentViewState(String currentViewState) 
	{
		this.currentViewState = currentViewState;
	}

	/**
	 * Method that retrieves the start view state in web flow.
	 *
	 * @return String - Start View State.
	 */
	public String getStartViewState() 
	{
		return startViewState;
	}

	/**
	 * Method that sets the current view state in web flow.
	 *
	 * @param startViewState - Start View State.
	 */
	public void setStartViewState(String startViewState) 
	{
		this.startViewState = startViewState;
	}

	/**
	 * Method that retrieves the start view class name.
	 *
	 * @return String - Start view class name.
	 */
	public Class<? extends Component> getStartViewClass() 
	{
		return startViewClass;
	}

	/**
	 * Method that sets the start view class name.
	 *
	 * @param startViewClass - Start view class name.
	 */
	public void setStartViewClass(Class<? extends Component> startViewClass) 
	{
		this.startViewClass = startViewClass;
	}

	/**
	 * Method that retrieves the last rendered view class name.
	 *
	 * @return String - Last rendered view class name.
	 */
	public Class<? extends Component>  getLastRenderedViewClass() 
	{
		return lastRenderedViewClass;
	}

	/**
	 * Method that sets the last rendered view class name.
	 *
	 * @param lastRenderedViewClass - Last rendered view class name.
	 */
	public void setLastRenderedViewClass(Class<? extends Component> lastRenderedViewClass) 
	{
		this.lastRenderedViewClass = lastRenderedViewClass;
	}	
	
	/**
	 * Method that retrieves the final view class name.
	 *
	 * @return String - Final view class name.
	 */
	public Class<? extends Component> getFinalViewClass() 
	{
		return finalViewClass;
	}

	/**
	 * Method that sets the final view class name.
	 *
	 * @param finalViewClass - Final view class name.
	 */
	public void setFinalViewClass(Class<? extends Component> finalViewClass) 
	{
		this.finalViewClass = finalViewClass;
	}
	
//	public FlowHelper getFlowHelper(){
//		//There is no setFlowHelper method. Instead it is initialized the first time it is needed 
//		if(this.flowHelper == null){
//			this.flowHelper = new FlowHelper();
//		}
//		
//		return this.flowHelper;
//	}
	
	/**
	 * Method that returns page flow conversation container.
	 *
	 * @return PageFlowConversationContainer - Page flow conversation container.
	 */
	public PageFlowConversationContainer getPageFlowConversationContainer()
	{
		return pageFlowConversationContainer;
	}

	/**
	 * Method that sets the page flow conversation container.
	 *
	 * @param pageFlowConversationContainer - Page flow conversation container.
	 */
	public void setPageFlowConversationContainer(
		PageFlowConversationContainer pageFlowConversationContainer)
	{
		this.pageFlowConversationContainer = pageFlowConversationContainer;
	}

	/**
	 * Method that returns whether there are any page flow specific error messages in session.
	 *
	 * @return boolean - Boolean that represents whether there are any page flow specific error messages in session.
	 */
	public boolean hasPageFlowErrorMessages()
	{
		return hasPageFlowErrorMessages;
	}

	/**
	 * Method that sets whether there are any page flow specific error messages in session.
	 *
	 * @param hasPageFlowErrorMessages - - Boolean that represents whether there are any page flow specific error messages in session.
	 */
	public void setHasPageFlowErrorMessages(boolean hasPageFlowErrorMessages)
	{
		this.hasPageFlowErrorMessages = hasPageFlowErrorMessages;
	}

//	/**
//	 * Method that returns the list of states for the progress bar..
//	 * 
//	 * @return List - Progress bar state list.
//	 */
	/**
	 * <p>getProgressBarLinks.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	public List<ProgressLink> getProgressBarLinks()
	{
		if(progressLinks == null){
			progressLinks = PageFlowController.populateProgressBarStates();
		}
		
		return progressLinks;
	}
//
//	/**
//	 * Method that sets the progress bar state map.
//	 * 
//	 * @param progressBarLinks
//	 */
//	public void setProgressBarLinks(List<ProgressLink> progressBarLinks)
//	{
//		this.progressLinks = progressBarLinks;
//	}
//
//	public int getCurrentViewStepNumber()
//	{
//		return currentViewStepNumber;
//	}

	/**
	 * <p>Setter for the field <code>currentViewStepNumber</code>.</p>
	 *
	 * @param stepNumberInput a int.
	 * @param stateId a {@link java.lang.String} object.
	 */
	public void setCurrentViewStepNumber(int stepNumberInput, String stateId)
	{
		int stepNumber = 0;
		
		//if step number != 0 then return that number
		if(stepNumberInput != 0){
			stepNumber = stepNumberInput;
		} else {
			//else lookup the class and get the number there
			stepNumber = getStepNumberForStateId(stateId);
		}
		
		this.currentViewStepNumber = stepNumber;
		
	}


	private int getStepNumberForStateId(String stateId)
	{
		for(StateCache state : statesVisited){
			if(state.id.equals(stateId)){
				return state.progressLinksStepNumber;
			}
		}
		StateCache newState = new StateCache();
		newState.id = stateId;
		newState.progressLinksStepNumber = this.currentViewStepNumber;
		
		statesVisited.add(newState);
		
		return newState.progressLinksStepNumber;
	}

	/**
	 * <p>Getter for the field <code>currentViewStateId</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getCurrentViewStateId(){
		return this.currentViewStateId;
	}
	
	/**
	 * <p>Setter for the field <code>currentViewStateId</code>.</p>
	 *
	 * @param transitionedViewStateId a {@link java.lang.String} object.
	 */
	public void setCurrentViewStateId(String transitionedViewStateId) {
		this.currentViewStateId = transitionedViewStateId;
	}

}

