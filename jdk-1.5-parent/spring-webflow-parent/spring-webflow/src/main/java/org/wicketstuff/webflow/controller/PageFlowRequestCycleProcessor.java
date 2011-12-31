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
/** ***************************************************************************
 * File: PageFlowRequestCycleProcessor
 *****************************************************************************/
package org.wicketstuff.webflow.controller;

import org.apache.wicket.IRequestTarget;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.RestartResponseException;
import org.apache.wicket.protocol.http.WebRequestCycleProcessor;
import org.apache.wicket.request.RequestParameters;
import org.apache.wicket.request.target.component
    .BookmarkablePageRequestTarget;

import org.wicketstuff.webflow.PageFlowConstants;
import org.wicketstuff.webflow.session.PageFlowSession;


/**
 * Request cycle processor that provides back button support for a Spring Web Flow based Page flow application
 * with Full Page Refresh.
 *
 * @author Clint Checketts, Florian Braun, Doug Hall, Subramanian Murali
 * @version $Id: $
 */
public class PageFlowRequestCycleProcessor extends WebRequestCycleProcessor 
{
	
	/** {@inheritDoc} */
	@Override
	public IRequestTarget resolve(RequestCycle requestCycle, RequestParameters requestParameters)
	{
		//If Key in URL use it	
		if(requestParameters.getParameters().containsKey(PageFlowConstants.FLOW_EXECUTION_KEY))
		{
			String[] flowExecutionKeyParms = (String[])requestParameters.getParameters().get(PageFlowConstants.FLOW_EXECUTION_KEY);
			if(flowExecutionKeyParms.length > 0){
				return RequestTargetFactory.buildFromState(flowExecutionKeyParms[0], requestCycle, requestParameters);
			}
		}
		
		IRequestTarget target = super.resolve(requestCycle, requestParameters);
		
		// If the target is bookmarkable but mounted it needs to go back and check pageflow
		if (target instanceof BookmarkablePageRequestTarget)
		{
//			BookmarkablePageRequestTarget bookmarkableTarget = (BookmarkablePageRequestTarget)target;

//			if (PAGE_FLOW_PAGE_CLASS.isAssignableFrom(bookmarkableTarget.getPageClass()) ||
//				PANEL_FLOW_PAGE_CLASS.isAssignableFrom(bookmarkableTarget.getPageClass()))
//			{
				String currentStateFromSession = PageFlowSession.get().getFlowState().getCurrentViewState();
				return RequestTargetFactory.buildFromState(currentStateFromSession, requestCycle, requestParameters);
//			}
		}
		
		return target;
	}
	
	/** {@inheritDoc} */
	@Override
	protected IRequestTarget resolveHomePageTarget(RequestCycle requestCycle, RequestParameters requestParameters)
	{
		
		//Grab the target as Wicket would normally calculate it
		BookmarkablePageRequestTarget classicTarget =(BookmarkablePageRequestTarget) super.resolveHomePageTarget(requestCycle, requestParameters);
		BookmarkablePageRequestTarget newTarget =  resolvePageFlowTargets(classicTarget,requestCycle,requestParameters);
		
		//If the target that PageFlow is returning isn't the real home page we need to redirect to it.
		if(!classicTarget.getPageClass().equals(newTarget.getPageClass())){
			
			//Redirect to the homepage if session is trying to go to the end of the flow since we are restarting it
			String currentStateFromSession = PageFlowSession.get().getFlowState().getCurrentViewState();
			if(PageFlowConstants.PAGE_FLOW_FINAL_STATE_DONE.equals(currentStateFromSession)){
				//Reset the whole flow state in preparation for a new flow
				PageFlowSession.get().resetFlowState();
				
				// This line is to reset the target because the flowstate was just reset so the url needs to be regenerated
				classicTarget =(BookmarkablePageRequestTarget) super.resolveHomePageTarget(requestCycle, requestParameters);
				
				return classicTarget;
			}
			
			throw new RestartResponseException(newTarget.getPageClass(), newTarget.getPageParameters());
		}
		
		return resolvePageFlowTargets(classicTarget,requestCycle,requestParameters); 
	}
	
	/** {@inheritDoc} */
	@Override
	protected IRequestTarget resolveBookmarkablePage(RequestCycle requestCycle, RequestParameters requestParameters)
	{
		//Grab the target as Wicket would normally calculate it
		BookmarkablePageRequestTarget classicTarget =(BookmarkablePageRequestTarget) super.resolveBookmarkablePage( requestCycle,  requestParameters);
		
		return resolvePageFlowTargets(classicTarget,requestCycle,requestParameters);
	}
	
	/**
	 * <p>resolvePageFlowTargets.</p>
	 *
	 * @param classicTarget a {@link org.apache.wicket.request.target.component.BookmarkablePageRequestTarget} object.
	 * @param requestCycle a {@link org.apache.wicket.RequestCycle} object.
	 * @param requestParameters a {@link org.apache.wicket.request.RequestParameters} object.
	 * @return a {@link org.apache.wicket.request.target.component.BookmarkablePageRequestTarget} object.
	 */
	protected BookmarkablePageRequestTarget resolvePageFlowTargets(BookmarkablePageRequestTarget classicTarget, RequestCycle requestCycle, RequestParameters requestParameters)
	{
		BookmarkablePageRequestTarget target = null;
		
		String currentStateFromSession = PageFlowSession.get().getFlowState().getCurrentViewState();
		//If the target still hasn't been set, target from the user's session data
		if(currentStateFromSession != null)
		{
			target = RequestTargetFactory.buildFromState(currentStateFromSession, requestCycle, requestParameters);
		}
		
		//TODO if the target is null, redirect back to the last rendered page, or the homepage
		if(target == null)
		{
			target = classicTarget;	
		}
		
		return target;
	}
}
