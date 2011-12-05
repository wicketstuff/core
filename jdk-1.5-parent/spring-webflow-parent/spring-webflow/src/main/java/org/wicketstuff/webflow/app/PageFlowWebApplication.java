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

import org.apache.wicket.Application;
import org.apache.wicket.Page;
import org.apache.wicket.Request;
import org.apache.wicket.Response;
import org.apache.wicket.Session;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.IRequestCycleProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;
import org.springframework.webflow.executor.FlowExecutor;
import org.wicketstuff.webflow.FlowUtils;
import org.wicketstuff.webflow.IPageFlowContainer;
import org.wicketstuff.webflow.controller.PageFlowRequestCycleProcessor;
import org.wicketstuff.webflow.session.PageFlowSession;
import org.wicketstuff.webflow.view.WicketFlowArtifactFactory;


/**
 * Base application class for a Wicket application that requires an externalized page flow based on Spring Web Flow.
 * All applications that require a externalized page flow with full page refresh, will need to extend this class.
 * This application uses PageFlowRequestCycleProcessor to provide back button support.
 *
 * @author Clint Checketts, Florian Braun, Doug Hall, Subramanian Murali
 * @version $Id: $
 */
public abstract class PageFlowWebApplication extends WebApplication implements IPageFlowContainer
{
	//Spring Application Context
	private ApplicationContext applicationContext;
	
	/**
	 * <p>Constructor for PageFlowWebApplication.</p>
	 */
	public PageFlowWebApplication() {
		this(null);
	}
	
	/**
	 * <p>Constructor for PageFlowWebApplication.</p>
	 *
	 * @param applicationContext2 a {@link org.springframework.context.ApplicationContext} object.
	 */
	public PageFlowWebApplication(ApplicationContext applicationContext2) {
		applicationContext = applicationContext2;
	}

	/**
	 * {@inheritDoc}
	 *
	 * Method that returns the home page of the application by querying Spring Web Flow.
	 *
	 * This method checks if the start view state is available in the page flow session.
	 *
	 *      If start view state is available in the page flow session, it returns the initial view class name
	 *         from page flow sesssion as the home page class.
	 *      If start view state is not available in the page flow session, it launches a new Application flow
	 *         in Spring Web Flow and returns the view state class corresponding to the newly launched state
	 *         as the home page class.
	 *
	 *  This method has been marked as final to ensure that developers don't override this method to return
	 *  a home page class that is not based on Spring Web Flow.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Class<? extends Page> getHomePage()
	{
		return (Class<? extends Page>)FlowUtils.getFlowStartStateClass();
	}
	
	/** {@inheritDoc} */
	@Override
	public Session newSession(Request request, Response response) {
		return new PageFlowSession<Object>(request);
	}
	

	/** {@inheritDoc} */
	@Override
	protected void init()
	{		
		super.init();
		//Retrieve the servlet context and initialize the Spring Application Context
		if(applicationContext == null){
			ServletContext sc = getServletContext();
			applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(sc);
		}
		
		WicketFlowArtifactFactory factory = new WicketFlowArtifactFactory();
		FlowBuilderServices flowBuilderServices = (FlowBuilderServices) applicationContext.getBean("flowBuilderServices");
		flowBuilderServices.setFlowArtifactFactory(factory);
	}
	
	/**
	 * {@inheritDoc}
	 *
	 * This method returns PageFlowRequestCycleProcessor as the request cycle processor to provide back button support.
	 */
	@Override
	protected final IRequestCycleProcessor newRequestCycleProcessor() 
	{
		return new PageFlowRequestCycleProcessor();
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
	 * This Flow Executor instance will be used by SpringWebFlowFacade to communicate with Spring Web Flow.
	 *
	 * @return FlowExecutor - Flow Executor Instance.
	 */
	public FlowExecutor getFlowExecutor()
	{
		return (FlowExecutor) applicationContext.getBean("flowExecutor");
	}
	
	
	/**
	 * Override for easy getting the current {@link WebApplication} without having to cast
	 * it.
	 *
	 * @return a {@link org.wicketstuff.webflow.app.PageFlowWebApplication} object.
	 */
	public static PageFlowWebApplication get()
	{
		Application application = Application.get();

		if (application instanceof PageFlowWebApplication == false)
		{
			throw new WicketRuntimeException(
				"The application attached to the current thread is not a " +
				PageFlowWebApplication.class.getSimpleName());
		}

		return (PageFlowWebApplication)application;
	}
		
	/**
	 * Method that returns a boolean that indicates whether a progress bar is to be displayed in the application.
	 *
	 * @return boolean - boolean that indicates whether a progress bar is to be displayed.
	 */
	public boolean isProgressBarToBeDisplayed()
	{
		return false;
	}


}
