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
package org.wicketstuff.dojo11.dojodnd;

import java.util.HashMap;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.wicketstuff.dojo11.AbstractDojoWidgetBehavior;
import org.wicketstuff.dojo11.templates.DojoPackagedTextTemplate;

/**
 * Handler for a {@link DojoDropContainer}
 * @author <a href="http://www.demay-fr.net/blog">Vincent Demay</a>
 *
 */
public class DojoDropContainerHandler extends AbstractDojoWidgetBehavior
{
	private static final String FUNCTION_CONNECT_DROP_CONTAINER = "connectDojoDropContainer";
	private static final String JAVASCRIPT;
	private static final String JAVASCRIPT_ID;
	/**
	 * disallow dropping of all items
	 */
	public static final String ACCEPT_NONE = AttributeModifier.VALUELESS_ATTRIBUTE_REMOVE;
	
	static {
		DojoPackagedTextTemplate template = new DojoPackagedTextTemplate(DojoDropContainer.class, DojoDropContainer.class.getSimpleName()+".js");
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("connectDojoDropContainerFunction", FUNCTION_CONNECT_DROP_CONTAINER);
		JAVASCRIPT = template.asString(map);
		JAVASCRIPT_ID = template.getStaticKey();
	}
	private IModel acceptModel;
	
	/** container handler is attached to. */
	private DojoDropContainer container;

	/**
	 * Construct.
	 * @param acceptModel 
	 */
	public DojoDropContainerHandler(IModel acceptModel)
	{	
		this.acceptModel = acceptModel;
	}
	
	/**
	 * @see org.apache.wicket.ajax.AbstractDefaultAjaxBehavior#onBind()
	 */
	protected void onBind()
	{
		super.onBind();
		getComponent().add(new AttributeModifier("accept", true, new PropertyModel(this, "acceptModel.object")));
		this.container = (DojoDropContainer)getComponent();
	}
	
	protected final void respond(AjaxRequestTarget target)
	{
		container.onAjaxModelUpdated(target);
	}

	/**
	 * @return the drop container.
	 */
	protected DojoDropContainer getDojoDropContainer() {
		return this.container;
	}
	
	/**
	 * @see org.wicketstuff.dojo11.AbstractRequireDojoBehavior#setRequire(org.wicketstuff.dojo11.AbstractRequireDojoBehavior.RequireDojoLibs)
	 */
	public void setRequire(RequireDojoLibs libs)
	{
		super.setRequire(libs);
		libs.add("dojo.dnd.Source");
	}
	
	/**
	 * @see org.wicketstuff.dojo11.AbstractRequireDojoBehavior#renderHead(org.apache.wicket.markup.html.IHeaderResponse)
	 */
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);
		if (isDropCallbackEnabled() && getComponent().isVisibleInHierarchy()) {
			response.renderJavascript(JAVASCRIPT, JAVASCRIPT_ID);
			response.renderOnDomReadyJavascript(FUNCTION_CONNECT_DROP_CONTAINER+"('"+getComponent().getMarkupId()+"','"+getCallbackUrl()+"')");
		}
	}

	/**
	 * @return if drop callback ({@link DojoDropContainer#onDrop(AjaxRequestTarget, org.apache.wicket.Component, int)} is enabled
	 */
	protected boolean isDropCallbackEnabled()
	{
		return true;
	}
	
	/**
	 * @return IModel<String> of accepted types
	 */
	public IModel getAcceptModel()
	{
		return acceptModel;
	}
	
	/**
	 * @param acceptModel IModel<String> of accepted types, use ACCEPT_NONE to disallow all
	 */
	public void setAcceptModel(IModel acceptModel)
	{
		this.acceptModel = acceptModel;
	}
}
