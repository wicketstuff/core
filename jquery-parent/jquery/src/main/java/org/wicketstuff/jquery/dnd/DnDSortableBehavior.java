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
package org.wicketstuff.jquery.dnd;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.StringHeaderItem;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.util.template.PackageTextTemplate;
import org.wicketstuff.jquery.JQueryBehavior;
import org.wicketstuff.jquery.Options;

// TODO: disable callback to serverside if clientsideonly
@SuppressWarnings("serial")
public class DnDSortableBehavior extends JQueryBehavior
{
	public static final ResourceReference DNDSORTABLEBEHAVIOR_JS = new PackageResourceReference(
		DnDSortableBehavior.class, DnDSortableBehavior.class.getSimpleName() + ".js");

	protected Options options_;

	protected ArrayList<MarkupContainer> containers_;

	public DnDSortableBehavior()
	{
		this(null);
	}

	/**
	 * Create a DnDSortableBehavior with default options override.
	 * <ul>
	 * <li>options include every optionsof the js component (see <a
	 * href="http://interface.eyecon.ro/docs/sort">http://interface.eyecon.ro/docs/sort</a> for the
	 * base list of options).</li>
	 * <li>"containerclass" : the CSS' class of every container to be sortable (default is bind
	 * component (handler) + "_dndContainer"</li>
	 * <li>"startOnLoad" : boolean, true => sortable feature is started on page load (default) else,
	 * the client side must call the JSFunctionName4Start.</li>
	 * <ul>
	 * 
	 * @param options
	 *            the overriden options to use
	 */
	public DnDSortableBehavior(Options options)
	{
		super();
		if (options == null)
		{
			options = new Options();
		}
		options_ = options;
		options_.set("accept", "dndItem", false)
			.set("helperclass", "sortHelper", false)
			.set("activeclass", "sortableactive", false)
			.set("hoverclass", "sortablehover", false)
			// .set("handle", ".dndItem", false)
			.set("tolerance", "pointer", false)
			.set("startOnLoad", Boolean.TRUE, false);
		containers_ = new ArrayList<MarkupContainer>();
	}

	@Override
	public void renderHead(Component component, IHeaderResponse response)
	{
		super.renderHead(component, response);
		response.render(JavaScriptHeaderItem.forReference(INTERFACE_JS));
		response.render(JavaScriptHeaderItem.forReference(DNDSORTABLEBEHAVIOR_JS));
		response.render(StringHeaderItem.forString(getHead(false)));
	}

	public CharSequence getRebindScript()
	{
		return getHead(true);
	}

	private CharSequence getHead(boolean rebind)
	{
		// load the css template we created form the res package
		PackageTextTemplate template = new PackageTextTemplate(DnDSortableBehavior.class,
			DnDSortableBehavior.class.getSimpleName() + (rebind ? "-rebind.tmpl" : "-head.tmpl"));

		// create a variable subsitution map
		CharSequence itemSelector = "." + options_.get("accept");
		CharSequence handleSelector = (CharSequence)options_.get("handle");
		if (handleSelector == null)
		{
			// only for CSS
			handleSelector = itemSelector;
		}
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("containerSelector", "." + getContainerCSSClass());
		params.put("helperclass", options_.get("helperclass", "").toString());
		params.put("handleSelector", handleSelector);
		params.put("itemSelector", itemSelector);
		params.put("options", options_.toString(true));
		params.put("callbackUrl", getCallbackUrl());
		params.put("dndHandlerStart", getJSFunctionName4Start());
		params.put("dndHandlerStop", getJSFunctionName4Stop());
		// params.put("startOnLoad", options_.get("startOnLoad", "true").toString());
		// perform subsitution and return the result
		return template.asString(params);
	}

	private CharSequence getContainerCSSClass()
	{
		CharSequence back = (CharSequence)options_.get("containerclass", null);
		if (back == null)
		{
			back = getComponent().getId() + "_dndContainer";
		}
		return back;
	}

	@Override
	public String getOnReadyScript()
	{
		return "$( " + getJSFunctionName4Start() + " );";
	}

	/**
	 * @return the name of the javascript function to start the behavior on client side.
	 */
	public CharSequence getJSFunctionName4Start()
	{
		return getComponent().getId() + "_dndStart";
	}

	/**
	 * @return the name of the javascript function to stop the behavior on client side.
	 */
	public CharSequence getJSFunctionName4Stop()
	{
		return getComponent().getId() + "_dndStop";
	}

	@Override
	public final void respond(AjaxRequestTarget target)
	{
		try
		{
			Request req = RequestCycle.get().getRequest();
			if (logger().isDebugEnabled())
			{
				logger().debug("params : {}", req.getRequestParameters());
			}
			onDnD(
				target,
				// req.getParameter("itemId"),
				req.getQueryParameters().getParameterValue("srcContainerId").toString(),
				req.getQueryParameters().getParameterValue("srcPosition").toInt(),
				req.getQueryParameters().getParameterValue("destContainerId").toString(),
				req.getQueryParameters().getParameterValue("destPosition").toInt());
		}
		catch (RuntimeException exc)
		{
			throw exc;
		}
		catch (Exception exc)
		{
			throw new RuntimeException("wrap: " + exc.getMessage(), exc);
		}
	}

	/**
	 * Call when a component has been moved on client side. The default implementation log (as
	 * debug) the incomming parameters, search the component and forward to onDnD(AjaxRequestTarget
	 * target, String itemId, String srcContainerId, int srcPos, String destContainerId, int
	 * destPos).
	 * 
	 * @param target
	 *            a target, provide if a response,
	 * @param srcContainerId
	 *            the html id of source container from where item come, (null if not previously
	 *            registered by via registerContainer(...)).
	 * @param srcPos
	 *            the position/index of item into srcContainer before moving.
	 * @param destContainerId
	 *            the html id of destination container where item is, (null if not previously
	 *            registered by via registerContainer(...)).
	 * @param destPos
	 *            the position/index of item into srcContainer after moving.
	 */
	public void onDnD(AjaxRequestTarget target, String srcContainerId, int srcPos,
		String destContainerId, int destPos)
	{
		if (logger().isDebugEnabled())
		{
			logger().debug("srcContainerId={}, srcPos={}, destContainerId={}, destPos={}",
				new Object[] { srcContainerId, srcPos, destContainerId, destPos });
		}
		MarkupContainer srcContainer = null;
		MarkupContainer destContainer = null;
		for (MarkupContainer container : containers_)
		{
			if ((srcContainerId != null) && srcContainerId.equals(container.getMarkupId()))
			{
				srcContainer = container;
			}
			if ((destContainerId != null) && destContainerId.equals(container.getMarkupId()))
			{
				destContainer = container;
			}
			if ((srcContainer != null) && (destContainer != null))
			{
				break;
			}
		}
// if (srcContainer != null) {
// item = findByMarkupId(srcContainer, itemId);
// }
// if ((item == null) && (destContainer != null)) {
// item = findByMarkupId(destContainer, itemId);
// }
		boolean updateContainers = onDnD(target, srcContainer, srcPos, destContainer, destPos);
		if (updateContainers && (target != null))
		{
			// target is null in testcase
			// (optional) if you need to keep in sync component, markupId on serverside and client
// side
			target.add(srcContainer);
			if (srcContainer != destContainer)
			{
				target.add(destContainer);
			}
			target.appendJavaScript(getJSFunctionName4Start() + "();");
		}
	}

	/**
	 * Call when a component has been moved on client side (to be overwrited). The default
	 * implementation log (as debug) the incomming parameters.
	 * 
	 * @param target
	 *            a target, provide if a response,
	 * @param srcContainer
	 *            the source container from where item come, (null if not previously registered by
	 *            via registerContainer(...)).
	 * @param srcPos
	 *            the position/index of item into srcContainer before moving.
	 * @param destContainer
	 *            the destination container where item is, (null if not previously registered by via
	 *            registerContainer(...)).
	 * @param destPos
	 *            the position/index of item into srcContainer after moving.
	 * @return false if you don't need to keep in sync component, markupId on serverside and client
	 *         side, else return true to send to client side the srcContainer and destContainer and
	 *         to update the handler (consume more resource, server, network, client).
	 */
	public boolean onDnD(AjaxRequestTarget target, MarkupContainer srcContainer, int srcPos,
		MarkupContainer destContainer, int destPos)
	{
		if (logger().isDebugEnabled())
		{
			logger().debug("srcContainer={}, srcPos={}, destContainer={}, destPos={}",
				new Object[] { srcContainer, srcPos, destContainer, destPos });
		}
		return false;
	}

	/**
	 * Register a container as a container for draggable/droppable items. (add the css class and
	 * markupId to be find on clientside).
	 * 
	 * @param v
	 *            the container to register.
	 * @return this
	 */
	protected DnDSortableBehavior registerContainer(MarkupContainer v)
	{
		v.add(new AttributeAppender("class", new Model<String>(
			String.valueOf(getContainerCSSClass())), " "));
		v.setOutputMarkupId(true);
		containers_.add(v);
		return this;
	}

	/**
	 * Register a component as draggable/moveable item. (add the css class and markupId to be find
	 * on clientside).
	 * 
	 * @param v
	 *            the component to register.
	 * @return this
	 */
	protected DnDSortableBehavior registerItem(Component v)
	{
		v.add(new AttributeAppender("class", new Model<String>(
			String.valueOf(options_.get("accept"))), " "));
		v.setOutputMarkupId(true);
		return this;
	}


}
