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
package org.wicketstuff.webflow.components;

import org.apache.wicket.Component;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.protocol.http.WebResponse;
import org.apache.wicket.util.string.Strings;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.webflow.FlowUtils;
import org.wicketstuff.webflow.IFlowContainerPanel;
import org.wicketstuff.webflow.PageFlowConstants;
import org.wicketstuff.webflow.controller.SpringWebFlowFacade;
import org.wicketstuff.webflow.session.FlowState;
import org.wicketstuff.webflow.session.PageFlowSession;


/**
 *  Container Panel for Panel Flow Applications.
 *
 * @author Clint Checketts, Florian Braun, Doug Hall, Subramanian Murali
 * @version $Id: $
 */
//TODO Namespace the dynamically rendered java script
public class WebFlowContainerPanel extends Panel implements IHeaderContributor, IFlowContainerPanel
{

	private static final Logger LOG = LoggerFactory.getLogger(WebFlowContainerPanel.class);
	private static final long serialVersionUID	= 1L;
	
	/**
	 * The panel holding the contents displayed by the flow
	 */
	private Panel contentPanel;
	
	/**
	 * Constructor.
	 *
	 * @param id - Mark Up Id.
	 */
	public WebFlowContainerPanel(String id)
	{
		super(id);
		
		
		//Add a  panel that actually holds the content.
		setContentPanel(new WebFlowPanel(PageFlowConstants.FLOW_CONTENT_PANEL)); //NOPMD 
		
		//This behavior generates a java script "goBackTo(flowExecutionKey)" 
		//which will be called by YUI history manager when the user hits the back button on the browser.
		this.add(new CallBackBehavior());	
	}
	

	/**
	 * <p>Getter for the field <code>contentPanel</code>.</p>
	 *
	 * @return a {@link org.apache.wicket.markup.html.panel.Panel} object.
	 */
	public Panel getContentPanel()
	{
		return contentPanel;
	}

	/** {@inheritDoc} */
	public void setContentPanel(Panel newContentPanel)
	{
		addOrReplace(newContentPanel);
		this.contentPanel = newContentPanel;
	}
	
	/** {@inheritDoc} */
	public void renderHead(IHeaderResponse response)
	{
		response.renderCSSReference(new ResourceReference(WebFlowContainerPanel.class,"WebFlowContainerPanel.css"));		
		response.renderJavascriptReference(new ResourceReference(WebFlowContainerPanel.class,"WebFlowContainerPanel.js"));		

		//These header setting tell the browser to always get he freshest content from the server so it doesn't cache
		// and display the wrong page in the flow, when it should truly be fetching a new page.
		if(response instanceof WebResponse){
			((WebResponse)response).setHeader("Pragma", "no-cache"); 
			((WebResponse)response).setHeader("Cache-Control", "no-cache, max-age=0, must-revalidate, no-store");	
		}
		 

	}
	
	/**
	 * {@inheritDoc}
	 *
	 *  Hook that allows implementing classes to hook into the change event
	 */
	public void onPanelFlowChange(AjaxRequestTarget target)
	{
	}


	
	/**
	 * Method that returns the class name of the content panel to be rendered for the incoming back button request from the client.  
	 * This method determines the class name of the content panel based on the flow execution key available in the request and 
	 * the value of the current view state available in the web flow session.  This method also sets error / warning messages in the 
	 * page flow session during this process of determining the content panel class name.
	 * 
	 * @return String - Wizard content panel class name.
	 */
	private Class<? extends Component> getContentPanelClass()
	{
		//Variable that represents the content panel class to be returned
		Class<? extends Component> contentPanelClass = null;
		
		//Retrieve the flow execution key available in the request
		String flowExecutionKey = RequestCycle.get().getRequest().getParameter(PageFlowConstants.FLOW_EXECUTION_KEY);
		
		//Retrieve the WebFlow Session
		FlowState<?> flowState = PageFlowSession.get().getFlowState();
		
		//Retrieve the current view state from Web Flow Session
		String currentViewState = flowState.getCurrentViewState();
		
		//There is no flow execution key available in the request.
		if(Strings.isEmpty(flowExecutionKey))
		{
			contentPanelClass = getPanelClassNoKey(flowState,currentViewState);
		}
		//There is flow execution key available in the request.
		//The flow execution key available in the request is DONE.		
		else if(PageFlowConstants.PAGE_FLOW_FINAL_STATE_DONE.equals(flowExecutionKey))
		{
			contentPanelClass = getPanelClassDoneKey(flowState,currentViewState);
		}
		else if(PageFlowConstants.PANEL_FLOW_INITIAL_STATE.equals(flowExecutionKey))
		{
			//Set the start view state as null in the session.
			//Setting the start view state as null will let the home panel class name to be determined by
			//launching a new flow.				
			flowState.setStartViewState(null);
			
			//Set the home panel class name as the content panel class name.
			contentPanelClass = FlowUtils.getFlowStartStateClass();	
		}
		//The flow execution key available in the request is not DONE.
		else
		{
			contentPanelClass = getPanelClassOtherKey(flowState,currentViewState,flowExecutionKey);
		}

		return contentPanelClass;
	}

	private Class<? extends Component> getPanelClassOtherKey(FlowState<?> flowState, String currentViewState,
			String flowExecutionKey)
	{
		Class<? extends Component> contentPanelClass;
		
		//There is no current view state available in the session.
		if(Strings.isEmpty(currentViewState))
		{
			//Set the start view state as null in the session.
			//Setting the start view state as null will let the home panel class name to be determined by
			//launching a new flow.
			flowState.setStartViewState(null);
			
			//Set the home panel class name as the content panel class name
			contentPanelClass = FlowUtils.getFlowStartStateClass();
			
		}
		else if(new SpringWebFlowFacade().isFlowExecutionKeyValid(flowExecutionKey))
		{
			//The input flow execution key is the same as the current view state in session.
			if(currentViewState.equals(flowExecutionKey))
			{
				//Retrieve the view state class for the input flow execution key.
				//Set the view state class name for the input flow execution key as the content panel class name
				contentPanelClass = new SpringWebFlowFacade().getViewStateClass(flowExecutionKey);
				
				//Set the last rendered view class as the retrieved view state class.
				flowState.setLastRenderedViewClass(contentPanelClass);
			}
			else
			{
				//Set the current view state as the input flow execution key.
				flowState.setCurrentViewState(flowExecutionKey);
				
				//Retrieve the view state class for the input flow execution key.
				Class<? extends Component> viewStateClass = new SpringWebFlowFacade().getViewStateClass(flowExecutionKey);
				
				//Set the view state class name for the input flow execution key as the content panel class name
				if(viewStateClass != null){
					contentPanelClass = viewStateClass;	
					
					//Set the last rendered view class as the retrieved view state class.
					flowState.setLastRenderedViewClass(contentPanelClass);	
				} else {
//					WebSession.get().error(WicketConstants.FLOW_TRANSITION_ERROR);
					FlowUtils.addFeedbackErrorBadUrl("FLOW_CONTAINER_3");
					
					contentPanelClass = flowState.getLastRenderedViewClass();
				}
				
							
			}
		}
		//The flow execution key is not valid.
		else
		{
			//The current view state in the session is DONE.
			if(currentViewState.equals(PageFlowConstants.PAGE_FLOW_FINAL_STATE_DONE))
			{
				//Set an error message in the Wicket session.
				//The Application Flow is complete.  You cannot traverse back.
//				WebSession.get().error(WicketConstants.FLOW_TRANSITION_ERROR);
				FlowUtils.addFeedbackErrorBackDisallowed("FLOW_CONTAINER_1");
				
				//Set the last rendered view class name as the content panel class name.
				contentPanelClass = flowState.getLastRenderedViewClass();
			}
			else
			{
				//The flow execution key is messed up
//				WebSession.get().error(WicketConstants.FLOW_TRANSITION_ERROR);
				FlowUtils.addFeedbackErrorBadUrl("FLOW_CONTAINER_2");
				
				
				//Set the last rendered view class name as the content panel class name.
				contentPanelClass = flowState.getLastRenderedViewClass();					
			}
		}
		return contentPanelClass;
	}

	private Class<? extends Component> getPanelClassDoneKey(FlowState<?> flowState, String currentViewState)
	{
		Class<? extends Component> contentPanelClass;
		
		//There is no current view state available in the session.
		if(Strings.isEmpty(currentViewState))
		{
			//Set the start view state as null in the session.
			//Setting the start view state as null will let the home panel class name to be determined by
			//launching a new flow.				
			flowState.setStartViewState(null);
			
			//Set the home panel class name as the  content panel class name.
			contentPanelClass = FlowUtils.getFlowStartStateClass();
		}
		//There is current view state available in the session.
		//The current view state in the session is DONE.			
		else if(currentViewState.equals(PageFlowConstants.PAGE_FLOW_FINAL_STATE_DONE))
		{
			//Set the last rendered view class as the final view class.
			flowState.setLastRenderedViewClass(flowState.getFinalViewClass());
			
			//Set the final view class name as the  content panel class name.
			contentPanelClass = flowState.getFinalViewClass();			
		}
		//The current view state is not DONE and the user has not already completed the application flow.
		else
		{
			//You cannot traverse to this page.
			
			//WebSession.get().error(WicketConstants.FLOW_TRANSITION_ERROR);
			FlowUtils.addFeedbackErrorBackDisallowed("FLOW_CONTAINER_4");
			
			flowState.setHasPageFlowErrorMessages(true);
			
			//Set the last rendered view class name as the content panel class name.
			contentPanelClass = flowState.getLastRenderedViewClass();
		}	
		return contentPanelClass;
	}

	private Class<? extends Component> getPanelClassNoKey(FlowState<?> flowState, String currentViewState)
	{
		Class<? extends Component> contentPanelClass;

		//There is no current view state available in the session.
		if(Strings.isEmpty(currentViewState))
		{
			//Set the home panel class name as the  content panel class name
			contentPanelClass = FlowUtils.getFlowStartStateClass();
		}
		//There is current view state available in the session.
		//The current view state is DONE and the user has already completed the application flow.			
		else if(currentViewState.equals(PageFlowConstants.PAGE_FLOW_FINAL_STATE_DONE))
		{
			//Set the start view state as null in the session.  
			//Setting the start view state as null will let the home panel class name to be determined by
			//launching a new flow.
			flowState.setStartViewState(null);
			
			//Set the home panel class name as the  content panel class name
			contentPanelClass = FlowUtils.getFlowStartStateClass();
		}
		//The current view state is not DONE and the user has not already completed the application flow.
		else
		{
			//Set the last rendered view class name as the  content panel class name.
			contentPanelClass = flowState.getLastRenderedViewClass();
		}
		return contentPanelClass;
	}


	private class CallBackBehavior extends AbstractDefaultAjaxBehavior 
	{
		private static final long serialVersionUID = 1L;

		@Override
		public void renderHead(IHeaderResponse response) 
		{
			super.renderHead(response);
			
			//Retrieve the current view state
//			String currentViewState = PageFlowSession.get().getFlowState().getCurrentViewState();
			
			String callbackUrl = getCallbackUrl().toString();
			 
			if(callbackUrl.indexOf("flowExecutionKey=") != -1)
			{
				callbackUrl = callbackUrl.substring(0, callbackUrl.indexOf("&flowExecutionKey="));
				//TODO Is there a possibility that there could be anything after the flow execution key
				//need to verify
			}

			StringBuffer sb = new StringBuffer()
				.append("function goBackTo(flowExecutionKey) { \n")
				.append("  wicketAjaxGet('").append(callbackUrl).append("&flowExecutionKey=' + flowExecutionKey);\n")
				.append("}\n");

			response.renderJavascript(sb.toString(), "BackButtonJS");
		}

		@Override
		protected void respond(AjaxRequestTarget target) 
		{
			//Flow Panel that needs to be added to the Ajax Target
			Panel flowPanel = null;
				
			//Retrieve the content panel class name
			Class<? extends Component> contentPanelClass = getContentPanelClass();
			
			if(contentPanelClass != null)
			{
				//Retrieve the panel for the retrieved content panel class name
				flowPanel = FlowUtils.getPageFlowContentPanel(contentPanelClass);
				
				if(flowPanel != null)
				{
					flowPanel.setOutputMarkupId(true);
					contentPanel.replaceWith(flowPanel);
					contentPanel= flowPanel;
				}
			}
			else
			{
				LOG.error("Flow panel class name cannot be determined for the back button request from the client");
			}						
							
			if(flowPanel != null){
				target.addComponent(flowPanel);
			}
			
			onPanelFlowChange(target); //NOPMD This is an expected extension point
			
		}
	}


}

