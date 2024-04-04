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

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Application;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.WicketAjaxJQueryResourceReference;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.cycle.PageRequestHandlerTracker;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.util.string.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is used to configure a global ajax event hook for the embedded tags (Servlet, JSP,
 * JSF). The tag libs can be used to generate a callback function to send an ajax request to the
 * current rendered page / their components<br>
 * <br>
 * 
 * <b>WATCH OUT - The WebPage has to be configured with setStatelassHint(false); !!!!
 * 
 * The ajax functions are in the ELFunctions class beginning with ajax...
 * 
 * @see org.wicketstuff.jeeweb.el.ELFunctions
 * 
 * @author Tobias Soloschenko
 *
 */
public class JEEWebGlobalAjaxHandler extends ResourceReference
{

	private static final Logger LOGGER = LoggerFactory.getLogger(JEEWebGlobalAjaxHandler.class);

	private static final long serialVersionUID = 4348780269907263872L;


	public JEEWebGlobalAjaxHandler()
	{
		super(JEEWebGlobalAjaxHandler.class.getSimpleName());
	}

	/**
	 * Receives ajax events and delegates them to the page which where rendered previously.
	 */
	@Override
	public IResource getResource()
	{
		return new IResource()
		{
			private static final long serialVersionUID = 3070290312369930992L;

			@Override
			public void respond(Attributes attributes)
			{
				try
				{
					PageParameters parameters = attributes.getParameters();

					int pageId = parameters.get("pageId").toInt();
					Page page = (Page)WebSession.get().getPageManager().getPage(pageId);
					AjaxRequestTarget newAjaxRequestTarget = ((WebApplication)Application.get()).newAjaxRequestTarget(page);
					RequestCycle.get().scheduleRequestHandlerAfterCurrent(newAjaxRequestTarget);
					page.send(page, Broadcast.BREADTH, new JEEWebGlobalAjaxEvent(
						newAjaxRequestTarget, parameters, RequestCycle.get()
							.getRequest()
							.getPostParameters()));
				}
				catch (Exception e)
				{
					LOGGER.error("Error while processing the ajax request", e);
				}
			}
		};
	}

	/**
	 * Configures the handler to the given application.
	 * 
	 * @param application
	 *            the application to configure the handler to
	 */
	public static void configure(WebApplication application)
	{
		application.getRequestCycleListeners().add(new PageRequestHandlerTracker());
		application.mountResource("/" + JEEWebGlobalAjaxHandler.class.getSimpleName(),
			new JEEWebGlobalAjaxHandler());
		application.getHeaderContributorListeners().add(new IHeaderContributor()
		{

			private static final long serialVersionUID = 1644041155625458328L;

			@Override
			public void renderHead(IHeaderResponse response)
			{
				JavaScriptResourceReference forReference = new JavaScriptResourceReference(
					JEEWebGlobalAjaxHandler.class, JEEWebGlobalAjaxHandler.class.getSimpleName() +
						".js")
				{

					private static final long serialVersionUID = -3649384632770480975L;

					@Override
					public List<HeaderItem> getDependencies()
					{
						return new ArrayList<HeaderItem>()
						{
							private static final long serialVersionUID = 1L;
							{

								add(JavaScriptHeaderItem.forReference(WicketAjaxJQueryResourceReference.get()));
							}
						};
					}
				};
				response.render(JavaScriptHeaderItem.forReference(forReference));
				addAJaxBaseUrl(response);
			}
				
			private void addAJaxBaseUrl(IHeaderResponse response)
			{
				// render ajax base url. It's required by Wicket Ajax support.
				Url baseUrl = RequestCycle.get().getUrlRenderer().getBaseUrl();
				CharSequence ajaxBaseUrl = Strings.escapeMarkup(baseUrl.toString());
				response.render(JavaScriptHeaderItem.forScript("Wicket.Ajax.baseUrl=\""
					+ ajaxBaseUrl + "\";", "wicket-ajax-base-url"));

			}
		});
	}
}
