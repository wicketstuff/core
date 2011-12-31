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
 * File: FlowUtils.java
 *****************************************************************************
 *
 * @author Clint Checketts, Florian Braun, Doug Hall
 * @version $Id: $
 */
package org.wicketstuff.webflow;

import java.lang.reflect.Constructor;

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.PageParameters;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.util.string.Strings;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.webflow.engine.RequestControlContext;
import org.wicketstuff.webflow.controller.PageFlowController;
import org.wicketstuff.webflow.session.FlowState;
import org.wicketstuff.webflow.session.PageFlowSession;
public class FlowUtils
{
	private static final Logger LOG = LoggerFactory.getLogger(FlowUtils.class);
	
	/**
	 * <p>getNextPage.</p>
	 *
	 * @param eventId a {@link java.lang.String} object.
	 * @return a {@link java.lang.Class} object.
	 */
	@SuppressWarnings("unchecked")
	public static Class <? extends WebPage> getNextPage(String eventId){
		
		//Retrieve the current view state
		String currentViewState = PageFlowSession.get().getFlowState().getCurrentViewState();
		
		//Resume the application flow from the current view state for the current transitioning event
		Class<? extends Component> transitionedViewStateClass = PageFlowController.resumeApplicationFlow(
			currentViewState, eventId);
		
		//Set the Response Page
		if(transitionedViewStateClass != null)
		{
			return (Class<? extends WebPage>) transitionedViewStateClass;
		} else {
			return (Class<? extends WebPage>)  transitionedViewStateClass;
			//TODO remove this whole return since the render method is handling settingTheResponsePage
			//throw new WicketRuntimeException("Unable to fetch view state: "+transitionedViewStateClass+" Event:"+eventId);
		}
	}
	
	
	
	/**
	 * <p>setResponsePageForEventId.</p>
	 *
	 * @param eventId a {@link java.lang.String} object.
	 */
	public static void setResponsePageForEventId(String eventId)
	{

		//Retrieve the current view state
		String currentViewState = PageFlowSession.get().getFlowState().getCurrentViewState();

		//Resume the application flow from the current view state for the current transitioning event
		@SuppressWarnings("unchecked")
		Class<? extends WebPage> transitionedViewStateClassName = (Class <? extends WebPage>)PageFlowController.resumeApplicationFlow(
			currentViewState, eventId);
	
		//Set the Response Page
		if(transitionedViewStateClassName != null)
		{
			//String transitionedViewState = PageFlowAppSession.get().getFlowState().getCurrentViewState();

			PageParameters transitionViewStateParameters = new PageParameters();
			//transitionViewStateParameters.put("flowExecutionKey", transitionedViewState);
			
			RequestCycle.get().setResponsePage( transitionedViewStateClassName,transitionViewStateParameters);
		}	
		
	}
	
	/**
	 * <p>doEnterHelper.</p>
	 *
	 * @param context a {@link org.springframework.webflow.engine.RequestControlContext} object.
	 * @param stepNumber a int.
	 */
	public static void doEnterHelper(RequestControlContext context, int stepNumber){
		String stateId = context.getCurrentState().getId();
		PageFlowSession.get().getFlowState().setCurrentViewStateId(stateId);
		PageFlowSession.get().getFlowState().setCurrentViewStepNumber(stepNumber,stateId);
	}

	/**
	 * <p>addFeedbackErrorBadUrl.</p>
	 *
	 * @param caller an identifier that shows the location in code calling causing this error message. This is useful if the same message can be spawned by d
	 */
//	private static String callerLocationIfInDevMode(String caller){
		//FIXME RE-add the dev check
//		if(WicketUtils.getConfigurationType().equals("development")){
//			return caller;
//		} else {
//			return IGlobalConstants.EMPTY_STRING;
//		}
//	}
	public static void addFeedbackErrorBadUrl(String caller)
	{
//		String callerLocation = callerLocationIfInDevMode(caller);
		
		//FIXME Lookup error message from properties file
//		Session.get().error(callerLocation+
//				ErdcStringResourceLoader.retrieveMessage("WebFlow.IncorrectAddress", 
//						ErdcStringResourceLoader.ENT_MESSAGE_APP, 
//						ErdcStringResourceLoader.ENT_MESSAGE_VERSION));
	}
	
	/**
	 * <p>addFeedbackErrorBackDisallowed.</p>
	 *
	 * @param caller a {@link java.lang.String} object.
	 */
	public static void addFeedbackErrorBackDisallowed(String caller)
	{
//		String callerLocation = callerLocationIfInDevMode(caller);
		
		//FIXME Lookup error message from properties file
//		Session.get().error(callerLocation+
//				ErdcStringResourceLoader.retrieveMessage("WebFlow.BackDisallowed", 
//						ErdcStringResourceLoader.ENT_MESSAGE_APP, 
//						ErdcStringResourceLoader.ENT_MESSAGE_VERSION));
		
	}

	/**
	 * <p>pageFlowTransition.</p>
	 *
	 * @param triggeringComponent a {@link org.apache.wicket.Component} object.
	 * @param target a {@link org.apache.wicket.ajax.AjaxRequestTarget} object.
	 * @param eventId a {@link java.lang.String} object.
	 */
	public static void pageFlowTransition(Component triggeringComponent, AjaxRequestTarget target, String eventId)
	{
		// Retrieve the Page Flow Session instance
		PageFlowSession<?> pageFlowSession = PageFlowSession.get();
		
		//Resume the application flow from the current view state for the current transitioning event
		Class<? extends Component> transitionedViewStateClass = PageFlowController.resumeApplicationFlow(
			pageFlowSession.getFlowState().getCurrentViewState(), eventId);
		
		//If the transitioned view state class name is not null or empty
		if(transitionedViewStateClass != null)
		{
			//Retrieve the content panel that needs to be added to the ajax request target for the incoming request
			Panel nextPanel = getPageFlowContentPanel(transitionedViewStateClass);
			
			IFlowContainerPanel containerPanel = triggeringComponent.findParent(IFlowContainerPanel.class);
			
			Component component = containerPanel.getContentPanel();
			if(component instanceof Panel)
			{
				containerPanel.setContentPanel(nextPanel);
				containerPanel.onPanelFlowChange(target);
			}
			
			target.addComponent(nextPanel);
			
			StringBuffer yuiRegisterJS = new StringBuffer()
			.append("YUI({filter: 'raw'}).use('history', function(Y) {\n")
			.append(" var history = new Y.HistoryHash();")
			.append(" history.addValue('flowExecutionKey', '").append(pageFlowSession.getFlowState().getCurrentViewState()).append("');\n")
			.append("});\n");

			target.appendJavascript(yuiRegisterJS.toString());	
		}
		
	}
	
	/**
	 * Private method that returns the panel for the input panel class name.
	 *
	 * @return Panel - Panel for the input panel class name.
	 * @param containerPanelClass a {@link java.lang.Class} object.
	 */
	public static Panel getPageFlowContentPanel(Class<? extends Component> containerPanelClass)
	{
		//Panel to be returned
		Panel flowPanel = null;
		
		//If the input panel class is not null 
		if(containerPanelClass != null)
		{
			try 
			{
				//Retrieve the constructor with a String parameter
				Constructor<?> constructor = containerPanelClass.getConstructor(new Class[] {String.class});
				
				//Get a new instance of the panel
				Object object = constructor.newInstance((Object []) new String[] {PageFlowConstants.FLOW_CONTENT_PANEL});

				if(object instanceof Panel){
					flowPanel = (Panel)object;
				}
			} 
			catch (Exception exception) 
			{
				LOG.error("An exception occurred while trying to find panel class name retrieved from Spring Web Flow " + containerPanelClass.getName(),
						exception);
			}
		}
		
		return flowPanel;
	}
	
	/**
	 * Private method that returns the name of the home panel for the flow.
	 *
	 * @return String - Home panel class name.
	 */
	public static Class<? extends Component> getFlowStartStateClass()
	{
		//Retrieve the Page Flow App Session instance
		FlowState<?> flowState = PageFlowSession.get().getFlowState();
		
		//Retreive the start view state from session
		String startViewState = flowState.getStartViewState();
		
		//Variable that holds the home panel class name as a string
		Class<? extends Component> homePanelClass = null;
		
		//Check if the start view state is not null or empty (means start view state is already available in session)
		if(!Strings.isEmpty(startViewState))
		{
			//We use a cached startClass since the homepage can get called several times in a given request
			homePanelClass = flowState.getStartViewClass();
		}
		else
		{
			//Retrieve the flow id
			String flowId = ((IPageFlowContainer) Application.get()).getFlowId();
			
			//Start the Application Flow
			homePanelClass = PageFlowController.startApplicationFlow(flowId);
		}
		
		if(homePanelClass == null)
		{
			LOG.error("An exception occurred while trying to retrieve the home page class for the application");
		}
		
		return homePanelClass;
	}


	
}

