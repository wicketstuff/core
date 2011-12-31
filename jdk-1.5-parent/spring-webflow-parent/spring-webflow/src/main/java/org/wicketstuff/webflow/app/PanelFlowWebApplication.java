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
package org.wicketstuff.webflow.app;

import javax.servlet.ServletContext;

import org.apache.wicket.Request;
import org.apache.wicket.Response;
import org.apache.wicket.Session;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;
import org.springframework.webflow.executor.FlowExecutor;
import org.wicketstuff.webflow.IPageFlowContainer;
import org.wicketstuff.webflow.session.PageFlowSession;
import org.wicketstuff.webflow.view.WicketFlowArtifactFactory;


/**
 * Base application class for a Wicket application that requires an externalized panel flow based on Spring Web Flow.
 *
 * A Panel Flow can be defined as an ajax based panel flow in which the header and footer sections of the page will
 * be retained and only the center part of the page will be updated on navigation.  In other words, there will not be any
 * full page refreshes happening in panel flow.
 *
 * All applications that require a ajax based panel flow with back button support will need to extend this class.
 *
 * @author Clint Checketts, Florian Braun, Doug Hall, Subramanian Murali
 * @version $Id: $
 */
public abstract class PanelFlowWebApplication extends WebApplication implements IPageFlowContainer
{
	//Spring Application Context
	private ApplicationContext applicationContext;
	
	/**
	 * {@inheritDoc}
	 *
	 * This method returns the home page for the panel flow application.  All panel flow application will need to override
	 * this method and provide PanelFlowPage as the home page class.
	 */
	@Override
	public abstract Class<? extends WebPage> getHomePage();
	

	/** {@inheritDoc} */
	@Override
	public Session newSession(Request request, Response response) {
		return new PageFlowSession<Object>(request);
	}
	
	/* (non-Javadoc)
	 * @see org.apache.wicket.protocol.http.WebApplication#init()
	 */
	/** {@inheritDoc} */
	@Override
	protected void init()
	{
		//Retrieve the servlet context and initialize the Spring Application Context
		ServletContext sc = getServletContext();
		applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(sc);
		
		//Change the Flow Artifact Factory to be WicketFlowArtifactFactory
		WicketFlowArtifactFactory factory = new WicketFlowArtifactFactory();
		FlowBuilderServices flowBuilderServices = (FlowBuilderServices) applicationContext.getBean("flowBuilderServices");
		flowBuilderServices.setFlowArtifactFactory(factory);	
	}
	

	
	/**
	 * Method that returns the Spring Application Context.
	 *
	 * @return ApplicationContext - Spring ApplicationContext.
	 */
	public final ApplicationContext getApplicationContext() 
	{
		return applicationContext;
	}

	/**
	 * Method that returns the Flow Executor instance from Spring Application Context.
	 * This Flow Executor instance will be used by WicketSpringWebFlowFacade to communicate with Spring Web Flow.
	 *
	 * @return FlowExecutor - Flow Executor Instance.
	 */
	public FlowExecutor getFlowExecutor()
	{
		return (FlowExecutor) applicationContext.getBean("flowExecutor");
	}
	

}
