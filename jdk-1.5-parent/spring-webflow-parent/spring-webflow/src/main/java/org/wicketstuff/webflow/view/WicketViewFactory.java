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
 * File: WicketViewFactory
 *****************************************************************************/
package org.wicketstuff.webflow.view;

import org.apache.wicket.Component;
import org.springframework.webflow.execution.RequestContext;
import org.springframework.webflow.execution.View;
import org.springframework.webflow.execution.ViewFactory;


/**
 * Wicket View Factory for Spring Web Flow that creates wicket views.
 *
 * @author Clint Checketts, Florian Braun, Doug Hall, Subramanian Murali
 * @version $Id: $
 */
public class WicketViewFactory implements ViewFactory
{
	//View Id
	private Class<? extends Component> viewId;
	
	/**
	 * Creates a new wicket based view factory.
	 *
	 * @param viewId the id of the view as an expression
	 */
	public WicketViewFactory(Class<? extends Component> viewId)
	{
		super();
		this.viewId = viewId;
	}

	/* (non-Javadoc)
	 * @see org.springframework.webflow.execution.ViewFactory#getView(org.springframework.webflow.execution.RequestContext)
	 */
	/** {@inheritDoc} */
	public View getView(RequestContext context)
	{
		return new WicketView(context, viewId);
	}

	/**
	 * Method that returns the view id.
	 *
	 * @return String.
	 */
	public Class<? extends Component> getViewId() 
	{
		return viewId;
	}

	/**
	 * Method that sets the view id.
	 *
	 * @param viewId - View Id for which the WicketView needs to be created.
	 */
	public void setViewId(Class<? extends Component> viewId) 
	{
		this.viewId = viewId;
	}
}
