/**
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.wicketstuff.jeeweb.ajax;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * Wraps the created ajax request target and the page parameters received by the ajax request, to
 * provide them via event to the current rendered page.
 * 
 * @author Tobias Soloschenko
 *
 */
public class JEEWebGlobalAjaxEvent
{

	private AjaxRequestTarget ajaxRequestTarget;

	private PageParameters pageParameters;

	private IRequestParameters postParameters;

	public JEEWebGlobalAjaxEvent(AjaxRequestTarget ajaxRequestTarget,
		PageParameters pageParameters, IRequestParameters postParameters)
	{
		this.ajaxRequestTarget = ajaxRequestTarget;
		this.pageParameters = pageParameters;
		this.postParameters = postParameters;
	}

	/**
	 * Gets the ajax request target created during the ajax call
	 * 
	 * @return the ajax request target
	 */
	public AjaxRequestTarget getAjaxRequestTarget()
	{
		return ajaxRequestTarget;
	}

	/**
	 * Sets the ajax request target during the ajax call
	 * 
	 * @param ajaxRequestTarget
	 *            the ajax request target
	 */
	public void setAjaxRequestTarget(AjaxRequestTarget ajaxRequestTarget)
	{
		this.ajaxRequestTarget = ajaxRequestTarget;
	}

	/**
	 * Gets the page parameters received by the ajax call
	 * 
	 * @return the page parameters
	 */
	public PageParameters getPageParameters()
	{
		return pageParameters;
	}

	/**
	 * Sets the page parameter received by the ajax call
	 * 
	 * @param pageParameters
	 *            the page parameters
	 */
	public void setPageParameters(PageParameters pageParameters)
	{
		this.pageParameters = pageParameters;
	}

	/**
	 * Gets the post parameters received by the ajax call
	 * 
	 * @return the post parameters
	 */
	public IRequestParameters getPostParameters()
	{
		return postParameters;
	}

	/**
	 * Sets the post parameters during the request call
	 * 
	 * @param postParameters
	 *            the post parameters
	 */
	public void setPostParameters(IRequestParameters postParameters)
	{
		this.postParameters = postParameters;
	}

	/**
	 * Gets the event object from the original event. Only used to make the code a little bit
	 * smarter.
	 * 
	 * @param event
	 *            the event received by the page
	 * @return the event to get the ajax request target and the parameters from
	 */
	public static JEEWebGlobalAjaxEvent getCastedEvent(IEvent<?> event)
	{
		if (event.getPayload() instanceof JEEWebGlobalAjaxEvent)
		{
			return ((JEEWebGlobalAjaxEvent)event.getPayload());
		}
		else
		{
			return null;
		}
	}
}
