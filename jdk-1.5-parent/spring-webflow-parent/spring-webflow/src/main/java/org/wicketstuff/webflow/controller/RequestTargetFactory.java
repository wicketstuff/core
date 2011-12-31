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
 * File: RequestTargetFactory.java
 *****************************************************************************
 *
 * @author Clint Checketts, Florian Braun, Doug Hall, Subramanian Murali
 * @version $Id: $
 */
package org.wicketstuff.webflow.controller;

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.PageParameters;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.RequestParameters;
import org.apache.wicket.request.target.component
    .BookmarkablePageRequestTarget;

import org.wicketstuff.webflow.FlowUtils;
import org.wicketstuff.webflow.PageFlowConstants;
import org.wicketstuff.webflow.session.FlowState;
import org.wicketstuff.webflow.session.PageFlowSession;
public class RequestTargetFactory
{
	/**
	 * <p>buildFromState.</p>
	 *
	 * @param flowExecutionKey a {@link java.lang.String} object.
	 * @param requestCycle a {@link org.apache.wicket.RequestCycle} object.
	 * @param requestParameters a {@link org.apache.wicket.request.RequestParameters} object.
	 * @return a {@link org.apache.wicket.request.target.component.BookmarkablePageRequestTarget} object.
	 */
	public static BookmarkablePageRequestTarget buildFromState(String flowExecutionKey,RequestCycle requestCycle, RequestParameters requestParameters){
		FlowState<?> flowState = PageFlowSession.get().getFlowState();
		
		Class<? extends Component> viewStateClass = null;
		if(PageFlowConstants.PAGE_FLOW_FINAL_STATE_DONE.equals(flowExecutionKey)){
			viewStateClass = flowState.getFinalViewClass();
		}
		else 
		{
				viewStateClass = new SpringWebFlowFacade().getViewStateClass(flowExecutionKey);
				if(viewStateClass != null){
					flowState.setCurrentViewState(flowExecutionKey);
				}
				
		}

		
		if(viewStateClass == null){
			viewStateClass = flowState.getLastRenderedViewClass();
			
			if(PageFlowConstants.PAGE_FLOW_FINAL_STATE_DONE.equals(flowState.getCurrentViewState())){
				FlowUtils.addFeedbackErrorBackDisallowed("RequestTargetFactory 46");	
			} else {
				FlowUtils.addFeedbackErrorBadUrl("RequestTargetFactory 48");
			}
			
		}
		
		//View class wasn't in the lastRendered, so go to the homepage
		if(viewStateClass == null){
			viewStateClass = Application.get().getHomePage();
		}
		
		//Make sure the flowstate is updated since the flowExecutionKey may have changed or the homePage would have been loaded
		flowState.setLastRenderedViewClass(viewStateClass);
		flowState.setCurrentViewStepNumber(0, viewStateClass.getName());
		
		
		PageParameters pars = requestCycle.getPageParameters();
		if(pars == null)
		{
			pars = new PageParameters(requestParameters.getParameters());
		}
		pars.put("flowExecutionKey", PageFlowSession.get().getFlowState().getCurrentViewState());
		
		//TODO Need to determine the last displayed page class when the current view state is DONE and the input
		//flow execution is not valid
		
		//Create a target for the last rendered page
		@SuppressWarnings("unchecked")
		BookmarkablePageRequestTarget target = new BookmarkablePageRequestTarget((Class<? extends WebPage>)
				viewStateClass, pars);
		
		return target;
		
		
//		
//		 // Look up next class in SWF
//		try
//		{
//
//			flowState.setLastRenderedViewClass(viewStateClass);
//			 
////			target = getTargetForLastRenderedPage(requestParameters);	
//			requestCycle.setResponsePage(viewStateClass,pars);	
//		} catch (WicketSpringWebFlowException wEx){
//			LOG.debug(wEx.getMessage());
//			requestCycle.getSession().error(wEx.getUserVisibleMessage());
//			
//			// If there was an error performing a transition, redirect back to the current page, if that
//			// isn't possible returning to the homepage will sufice
//			if(lastRenderedClass != null){
//				requestCycle.setResponsePage(lastRenderedClass);	
//			} else {
//				requestCycle.setResponsePage(Application.get().getHomePage());
//			}
	}
	
	
	/**
	 * <p>buildFromTransition.</p>
	 *
	 * @param eventId a {@link java.lang.String} object.
	 * @param requestCycle a {@link org.apache.wicket.RequestCycle} object.
	 * @return a {@link org.apache.wicket.request.target.component.BookmarkablePageRequestTarget} object.
	 */
	public static BookmarkablePageRequestTarget buildFromTransition(String eventId,RequestCycle requestCycle){
		FlowState<?> flowState = PageFlowSession.get().getFlowState();
		
		Class<? extends WebPage> nextPage = FlowUtils.getNextPage(eventId);
		
		PageParameters pars = requestCycle.getPageParameters();
		if(pars == null){
			pars = new PageParameters();
		}
		pars.put("flowExecutionKey", flowState.getCurrentViewState());
		
		//TODO Need to determine the last displayed page class when the current view state is DONE and the input
		//flow execution is not valid
		
		//Create a target for the last rendered page
		BookmarkablePageRequestTarget target = new BookmarkablePageRequestTarget((Class<? extends WebPage>)
				nextPage, pars);
		
		return target;
	}
}

