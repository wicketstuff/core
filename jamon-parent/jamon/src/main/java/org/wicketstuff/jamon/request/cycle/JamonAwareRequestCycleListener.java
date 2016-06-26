/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.jamon.request.cycle;

import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.component.IRequestablePage;
import org.apache.wicket.request.cycle.AbstractRequestCycleListener;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.Application;
import org.apache.wicket.core.request.handler.BookmarkablePageRequestHandler;
import org.apache.wicket.core.request.handler.BufferedResponseRequestHandler;
import org.apache.wicket.core.request.handler.IPageClassRequestHandler;
import org.apache.wicket.core.request.handler.ListenerInterfaceRequestHandler;

import com.jamonapi.Monitor;


/**
 * <p>
 * To use this class in your {@link WebApplication} simply override the {@link WebApplication#init}
 * method in your own {@link WebApplication}.
 * </p>
 * <p>
 * The responsibility of the {@link JamonAwareRequestCycleListener} is to create the label of the
 * {@link Monitor} currently in use by the {@link JamonMonitoredRequestCycle}. The label consists of
 * the source from where a request originated from and the target to where it will resolve to. <br>
 * This class can only be used in combination with {@link JamonMonitoredRequestCycle}. If used by
 * itself an {@link IllegalStateException} will be thrown.
 * </p>
 * <p>
 * <b>Implementation limitations:</b> <br>
 * Only if the {@link RequestCycle} comes from an {@link BookmarkablePageRequestHandler} or an
 * {@link ListenerInterfaceRequestHandler} <i>and</i> the eventual target of the
 * {@link RequestCycle} is an {@link PageRequestHandler} or an
 * {@link BookmarkablePageRequestHandler} the Monitors are created. If you want to support more
 * types of targets you can extend this class and implement the methods
 * {@link #doResolveSourceLabel(IRequestHandler, JamonMonitoredRequestCycle)} and
 * {@link #doResolveTargetLabel(IRequestHandler, JamonMonitoredRequestCycle)}.
 * </p>
 * 
 * @author lars
 * 
 */
public class JamonAwareRequestCycleListener extends AbstractRequestCycleListener
{

	private static final String DELIMETER = ":";
	private final boolean includeSourceNameInMonitorLabel;

	public JamonAwareRequestCycleListener(Application app, boolean includeSourceNameInMonitorLabel)
	{
		this.includeSourceNameInMonitorLabel = includeSourceNameInMonitorLabel;
	}

	@Override
	public void onBeginRequest(RequestCycle cycle)
	{
		JamonMonitoredRequestCycleContext.registerTo(cycle, includeSourceNameInMonitorLabel);
		getContextOf(cycle).startTimeRequest();
		super.onBeginRequest(cycle);
	}

	private JamonMonitoredRequestCycleContext getContextOf(RequestCycle cycle)
	{
		return JamonMonitoredRequestCycleContext.get(cycle);
	}

	@Override
	public void onEndRequest(RequestCycle cycle)
	{
		super.onEndRequest(cycle);
		getContextOf(cycle).stopTimeRequest();
	}

	@Override
	public void onRequestHandlerResolved(RequestCycle cycle, IRequestHandler handler)
	{
		super.onRequestHandlerResolved(cycle, handler);
		resolveSourceLabel(handler, cycle);
	}

	@Override
	public void onRequestHandlerExecuted(RequestCycle cycle, IRequestHandler handler)
	{
		super.onRequestHandlerExecuted(cycle, handler);
		// this is the last request target.
		resolveTargetLabel(handler, cycle);
	}

	@Override
	public void onRequestHandlerScheduled(RequestCycle cycle, IRequestHandler handler)
	{
		super.onRequestHandlerScheduled(cycle, handler);
		// this is the last request target.
		resolveTargetLabel(handler, cycle);
	}

	/**
	 * Subclasses should implement this method if they want to monitor more types of
	 * {@link IRequestHandler}s than this {@link JamonAwareRequestCycleListener} supports. See
	 * {@link JamonAwareRequestCycleListener} javadoc for the currently supported types. Besides
	 * this method subclasses should also consider implementing
	 * {@link #doResolveTargetLabel(IRequestHandler, JamonMonitoredRequestCycle)}. <br>
	 * Subclasses should at least call the following methods on the given
	 * {@link JamonMonitoredRequestCycle}: <br>
	 * <ul>
	 * <li>{@link JamonMonitoredRequestCycle#comesFromPage(Class)}</li>
	 * <li>{@link JamonMonitoredRequestCycle#setSource(String)}</li>
	 * </ul>
	 * <br>
	 * The default implementation of this method does nothing.
	 * 
	 * @param requestHandler
	 *            The request target of where the request originated from.
	 * @param cycle
	 *            The {@link JamonMonitoredRequestCycle}.
	 */
	protected void doResolveSourceLabel(IRequestHandler requestHandler, RequestCycle cycle)
	{
	}

	/**
	 * Subclasses should implement this method if they want to monitor more types of
	 * {@link IRequestHandler}s than this {@link JamonAwareRequestCycleListener} supports. See
	 * {@link JamonAwareRequestCycleListener} javadoc for the currently supported types. Besides
	 * this method subclasses should also consider implementing
	 * {@link #doResolveSourceLabel(IRequestHandler, JamonMonitoredRequestCycle)}. <br>
	 * Subclasses should at least call the method
	 * {@link JamonMonitoredRequestCycle#setTarget(Class)} on the given
	 * {@link JamonMonitoredRequestCycle}. <br>
	 * The default implementation of this method does nothing.
	 * 
	 * @param requestHandler
	 *            The request target of where the request will resolve to.
	 * @param cycle
	 *            The {@link JamonMonitoredRequestCycle}.
	 */
	protected void doResolveTargetLabel(IRequestHandler requestHandler, RequestCycle cycle)
	{
	}

	/*
	 * Resolves the source label. This is where the request originated from. This can be a link,
	 * direct page access, checkbox etc. The source label is then setup upon the given cycle in the
	 * for of: PageClassName.componentId.
	 */
	private void resolveSourceLabel(IRequestHandler requestHandler, RequestCycle cycle)
	{
		JamonMonitoredRequestCycleContext context;
		if (requestHandler instanceof ListenerInterfaceRequestHandler)
		{
			context = getContextOf(cycle);
			ListenerInterfaceRequestHandler handler = (ListenerInterfaceRequestHandler)requestHandler;
			Class<? extends IRequestablePage> pageClass = handler.getPageClass();
			context.comesFromPage(pageClass);
			String source = addComponentNameToLabelIfNotRedirectPageRequestTarget(handler,
				pageClass.getSimpleName());
			context.setSource(source);
		}
		else if (requestHandler instanceof IPageClassRequestHandler)
		{
			context = getContextOf(cycle);
			IPageClassRequestHandler handler = (IPageClassRequestHandler)requestHandler;
			context.comesFromPage(handler.getPageClass());
			context.setSource(handler.getPageClass().getSimpleName());
		}
		else
		{
			doResolveSourceLabel(requestHandler, cycle);
		}
	}

	private String addComponentNameToLabelIfNotRedirectPageRequestTarget(
		ListenerInterfaceRequestHandler handler, String source)
	{
		return source += "." + getRelativePath(handler);
	}

	/*
	 * returns the relative path with a max of 3. This to prevent extremely long names. My guess is
	 * that three is sufficient for determining which link was clicked.
	 */
	private String getRelativePath(ListenerInterfaceRequestHandler handler)
	{

		String relativePath = handler.getComponentPath();
		String[] parts = relativePath.split(DELIMETER);
		if (parts.length > 3)
		{
			relativePath = new StringBuilder(parts[parts.length - 3]).append(DELIMETER)
				.append(parts[parts.length - 2]).append(DELIMETER).append(parts[parts.length - 1])
				.toString();
		}
		return relativePath;
	}

	/*
	 * Resolves the target label. This is where the request resolves to. In all cases this will be
	 * the name of the page class that is (partially in case of Ajax) rendered.
	 */
	private void resolveTargetLabel(IRequestHandler requestHandler, RequestCycle cycle)
	{
		JamonMonitoredRequestCycleContext context;
		if (requestHandler instanceof IPageClassRequestHandler)
		{
			context = getContextOf(cycle);
			IPageClassRequestHandler target = (IPageClassRequestHandler)requestHandler;
			Class<? extends IRequestablePage> pageClass = target.getPageClass();
			context.setTarget(pageClass);
		}
		else if (requestHandler instanceof BufferedResponseRequestHandler) {
			context = getContextOf(cycle);
			context.dontMonitorThisRequest();
		}
		else
		{
			doResolveTargetLabel(requestHandler, cycle);
		}
	}
}
