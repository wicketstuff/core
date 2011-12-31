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
package org.wicketstuff.webflow.context;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.wicket.RestartResponseException;
import org.apache.wicket.markup.html.pages.RedirectPage;

import org.springframework.webflow.context.servlet.ServletExternalContext;
import org.wicketstuff.webflow.session.PageFlowSession;


/**
 * PageFlowExternalContext provides access to the page flow / panel flow application's custom application session instance
 * in Spring Web Flow for the evaluation of conditional expressions.
 *
 * @author Clint Checketts, Florian Braun, Doug Hall, Subramanian Murali
 * @version $Id: $
 */
public class PageFlowExternalContext extends ServletExternalContext 
{
	//Page Flow App Session
	private PageFlowSession<?> session;
	
	/**
	 * Constructor.
	 *
	 * @param context - ServletContext
	 * @param request - HttpServletRequest
	 * @param response - HttpServletResponse
	 * @param session - PageFlowAppSession
	 */
	public PageFlowExternalContext(ServletContext context, HttpServletRequest request, 
		HttpServletResponse response, PageFlowSession<?> session) 
	{
		super(context, request, response);
		this.session = session;
	}

	/**
	 * Method that returns the page flow app session.
	 *
	 * @return PageFlowSession.
	 */
	public PageFlowSession<?> getSession() 
	{
		return session;
	}

	/**
	 * Method that the sets the page flow app session in the context.
	 *
	 * @param session - PageFlowSession.
	 */
	public void setSession(PageFlowSession<?> session) 
	{
		this.session = session;
	}

	/**
	 * {@inheritDoc}
	 *
	 * Allows Wicket to hook into the redirect logic using the 'externalRedirect' parameter
	 * in Spring Webflow
	 */
	@Override
	public void requestExternalRedirect(String location) throws IllegalStateException
	{
		throw new RestartResponseException(new RedirectPage(location));
	}
}
