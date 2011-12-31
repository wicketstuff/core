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
 * File: SpringWebFlowFacade
 *****************************************************************************/
package org.wicketstuff.webflow.controller;

import org.apache.wicket.Component;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WebRequest;
import org.apache.wicket.protocol.http.WebResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.webflow.context.ExternalContextHolder;
import org.springframework.webflow.core.FlowException;
import org.springframework.webflow.engine.Flow;
import org.springframework.webflow.engine.NoMatchingTransitionException;
import org.springframework.webflow.engine.ViewState;
import org.springframework.webflow.execution.FlowExecution;
import org.springframework.webflow.execution.FlowExecutionKey;
import org.springframework.webflow.execution.repository.FlowExecutionLock;
import org.springframework.webflow.execution.repository
    .FlowExecutionRepository;
import org.springframework.webflow.execution.repository
    .FlowExecutionRestorationFailureException;
import org.springframework.webflow.executor.FlowExecutionResult;
import org.springframework.webflow.executor.FlowExecutor;
import org.springframework.webflow.executor.FlowExecutorImpl;
import org.wicketstuff.webflow.IPageFlowContainer;
import org.wicketstuff.webflow.PageFlowConstants;
import org.wicketstuff.webflow.WicketSpringWebFlowException;
import org.wicketstuff.webflow.context.PageFlowExternalContext;
import org.wicketstuff.webflow.session.PageFlowSession;
import org.wicketstuff.webflow.view.WicketView;


/**
 * Facade class that fronts all calls from Wicket Infrastructure to Spring Web Flow.
 *
 * This facade provides methods for the following:
 * 		To launch a new application flow.
 * 		To resume an existing application flow.
 * 		To validate whether a flow execution key is valid.
 * 		To retrieve the view state class corresponding to a flow execution key.
 * 		To retrieve the flow instance corresponding to a flow execution key.
 *
 * @author Clint Checketts, Florian Braun, Doug Hall, Subramanian Murali
 * @version $Id: $
 */
public class SpringWebFlowFacade 
{
	private static final Logger LOG = LoggerFactory.getLogger(SpringWebFlowFacade.class); 
	
	//Flow Executor Instance
	FlowExecutor flowExecutor;
	
	//Wicket External Context instance
	PageFlowExternalContext context;
	
	/**
	 * Constructor that accepts the flow executor and page flow external context as arguments.
	 *
	 * @param flowExecutor - FlowExecutor instance to be used by this facade to communicate with Spring Web Flow.
	 * @param context - Page Flow External Context to be used by this facade to communicate with Spring Web Flow.
	 */
	public SpringWebFlowFacade(FlowExecutor flowExecutor, PageFlowExternalContext context)
	{
		this.flowExecutor = flowExecutor;
		this.context = context;
	}
	
	/**
	 * Constructor that retrieves and initializes the flow executor and page flow external context  by itself.
	 */
	public SpringWebFlowFacade()
	{
		WebApplication application = WebApplication.get();

		//Retrieve the Page Flow App Session instance
		PageFlowSession<?> pageFlowSession = PageFlowSession.get();
		
		if(application instanceof IPageFlowContainer)
		{
			//Retrieve the flow executor instance
			this.flowExecutor = ((IPageFlowContainer) application).getFlowExecutor();
			
			//Construct a new page flow external context
			this.context = new PageFlowExternalContext(application.getServletContext(),
				((WebRequest)((RequestCycle)RequestCycle.get()).getRequest()).getHttpServletRequest(), 
				((WebResponse)((RequestCycle)RequestCycle.get()).getResponse()).getHttpServletResponse(), 
				pageFlowSession);			
		}
		else
		{
			throw new WicketRuntimeException("Application must implement the interface "+IPageFlowContainer.class.getName());
		}
	}	
	
	
	
	/**
	 * Method that launches a new web flow execution for the input flow id and returns the launched
	 * view state.
	 *
	 * @param flowId - Flow Id for which the web flow execution needs to be launched.
	 * @return String - Launched view state.
	 */
	public String launchWebFlowExecution(String flowId)
	{
		//Variable that stores the launched view state
		String launchedViewState = null;
		
		if(flowId != null)
		{
			try
			{
				//Launch the Web Flow Execution
				FlowExecutionResult result = flowExecutor.launchExecution(flowId, null, context);

				//Retrieve the launched view state
				launchedViewState = result.getPausedKey();
			}
						catch(FlowException flowException)
			{
				throw new WicketSpringWebFlowException("Unable to start flow",flowException);
			}
		}
		
		return launchedViewState;
	}
	
	/**
	 * Method that resumes an existing flow execution for the input view state and returns the transitioned
	 * view state.
	 *
	 * @param viewState - View state from which the application flow needs to be resumed..
	 * @return String - Transitioned view state.
	 */
	public String resumeWebFlowExecution(String viewState)
	{
		//Variable that stores the transitioned view state
		String transitionedViewState = null;
		
		if(viewState != null)
		{
			try
			{
				//Resume the execution in Spring Web Flow
				FlowExecutionResult result = flowExecutor.resumeExecution(viewState, context);
				
				if(result.isEnded())
				{
					transitionedViewState = PageFlowConstants.PAGE_FLOW_FINAL_STATE_DONE;
				}
				else
				{
					transitionedViewState = result.getPausedKey();		
				}	
			}
			catch(FlowExecutionRestorationFailureException flowException)
			{
				LOG.error("A Flow Exception occured in Spring Web Flow while resuming the application flow from the input view state" +
					viewState, flowException);
			}
			catch(NoMatchingTransitionException transitionEx){
				throw new WicketSpringWebFlowException("Invalid view transition",transitionEx);
			}
			catch(FlowException flowException)
			{
				if(viewState.equals(PageFlowConstants.PAGE_FLOW_FINAL_STATE_DONE)){
					throw new WicketSpringWebFlowException("Flow completed. Unable to return to flow.",flowException);
				} else {
					throw new WicketSpringWebFlowException("Invalid view transition",flowException);
				}
			}
		}
		
		return transitionedViewState;
	}	
	
	/**
	 * Method that checks if the input flow execution key is valid.
	 *
	 * @param inputKey - Input Flow Execution Key that needs to be checked if it is valid.
	 * @return boolean - boolean that represents whether the input flow execution key is valid.
	 */
	public boolean isFlowExecutionKeyValid(String inputKey)
	{
		//boolean that indicates if the input flow execution key is valid
		boolean isValid = false;
		
		//Set the ExternalContext
		ExternalContextHolder.setExternalContext(context);

		//Retrieve the Flow Execution Repository
		FlowExecutionRepository repository = ((FlowExecutorImpl)flowExecutor).getExecutionRepository();
		
		try 
		{
			//Parse the Flow Execution Key
			FlowExecutionKey flowExecutionKey = repository.parseFlowExecutionKey(inputKey);

			//Retrieve the Flow Execution Lock
			FlowExecutionLock lock = repository.getLock(flowExecutionKey);
			//Set the isValid to true as we are successfully able to retrieve a lock for the input flow execution key
			if(lock != null)
			{
				isValid = true;
			}
		}
		catch(Exception exception)
		{
			LOG.debug("A Flow Exception occured in Spring Web Flow while determining whether the input flow execution key is valid: " +
					inputKey, exception);
		}
		finally
		{
			//Reset the external context
			ExternalContextHolder.setExternalContext(null);
		}
		
		return isValid;
	}
	
	/**
	 * Method that returns the view state class corresponding to the input flow execution key from Spring Web Flow.
	 *
	 * @param inputFlowExecutionKey - Flow execution key for which the view state class name needs to be determined
	 * 					              from Spring Web Flow.
	 * @return Class - View state class for the input flow execution key.
	 */
	public Class<? extends Component> getViewStateClass(String inputFlowExecutionKey)
	{
		//Variable that holds the view state class
		Class<? extends Component> viewStateClass = null;
		
		//Set the ExternalContext
		ExternalContextHolder.setExternalContext(context);

		//Retrieve the Flow Execution Repository
		FlowExecutionRepository repository = ((FlowExecutorImpl)flowExecutor).getExecutionRepository();
		
		//Parse the Flow Execution Key
		FlowExecutionKey flowExecutionKey = repository.parseFlowExecutionKey(inputFlowExecutionKey);
		
		try 
		{
			//Retrieve the Flow Execution instance corresponding to the flow execution key
			FlowExecution flowExecution = repository.getFlowExecution(flowExecutionKey);
			
			//Retrieve the view state from the retrieved flow execution instance
			ViewState viewState = (ViewState) flowExecution.getActiveSession().getState();

			//Retrieve the view state class name
			viewStateClass= ((WicketView) viewState.getViewFactory().getView(null)).getViewId();
			

		}
		catch (FlowExecutionRestorationFailureException flowRestorationException) 
		{
			//TODO throw our own runtime exception here that pushes the right message up the feedback panel
			LOG.error("A problem occurred restoring the flow execution with key '" + viewStateClass+"'",
				flowRestorationException);
		}
		
		catch(Exception exception)
		{
			LOG.error("An exception occurred while trying to retrieve the view state " +
					"class for the input flow execution key from Spring Web Flow: " + flowExecutionKey,
				exception);
		}
		finally
		{
			//Reset the external context
			ExternalContextHolder.setExternalContext(null);
		}
		
		return viewStateClass;
	}
	
	/**
	 * Method that returns the flow instance corresponding to the input flow execution key from Spring Web Flow.
	 *
	 * @param inputFlowExecutionKey - Flow execution key for which the view state class name needs to be determined
	 * 					              from Spring Web Flow.
	 * @return Flow - Flow instance for the input flow execution key.
	 */
	public Flow getFlow(String inputFlowExecutionKey)
	{
		//Set the ExternalContext
		ExternalContextHolder.setExternalContext(context);

		//Retrieve the Flow Execution Repository
		FlowExecutionRepository repository = ((FlowExecutorImpl)flowExecutor).getExecutionRepository();
		//Flow instance that needs to be returned
		Flow flow = null;
		
		try 
		{
			//Parse the Flow Execution Key
			FlowExecutionKey flowExecutionKey = repository.parseFlowExecutionKey(inputFlowExecutionKey);
			
			//Retrieve the Flow Execution instance corresponding to the flow execution key
			FlowExecution flowExecution = repository.getFlowExecution(flowExecutionKey);
			
			//Retrieve the view state from the retrieved flow execution instance
			ViewState viewState = (ViewState) flowExecution.getActiveSession().getState();
			
			//Retrieve the flow instance corresponding to the flow execution key
			flow = viewState.getFlow();
		}
		catch(Exception exception)
		{
			LOG.error("An exception occurred while trying to retrieve the view state class for the input flow execution key " +
				"from Spring Web Flow " + inputFlowExecutionKey,
				exception);
		}
		finally
		{
			//Reset the external context
			ExternalContextHolder.setExternalContext(null);
		}
		
		return flow;
	}	
}
