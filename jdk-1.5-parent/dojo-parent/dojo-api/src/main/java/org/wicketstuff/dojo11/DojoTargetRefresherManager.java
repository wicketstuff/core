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
package org.wicketstuff.dojo11;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.AjaxRequestTarget.IJavascriptResponse;
import org.apache.wicket.ajax.AjaxRequestTarget.IListener;
import org.apache.wicket.protocol.http.WebRequest;
import org.apache.wicket.protocol.http.WebRequestCycle;
import org.wicketstuff.dojo11.application.IDojoRequest;

/**
 * A Manager to deal with {@link AjaxRequestTarget} and makeWidget in dojo.
 * Only top level dojoComponents needs to be rerender during a response 
 * to a Ajax Query
 * 
 * @author Vincent Demay
 *
 */
public class DojoTargetRefresherManager implements IListener
{
	/**
	 * 
	 */
	private static final String HTTP_REQUEST_ATTRIBUTE_NAME = DojoTargetRefresherManager.class.getName();
	private HashMap<String, Component> dojoComponents;
	private AjaxRequestTarget target;
	
	/**
	 * Construct.
	 */
	public DojoTargetRefresherManager(){	
		dojoComponents = new HashMap<String, Component>();
	}

	/**
	 * @see org.apache.wicket.ajax.AjaxRequestTarget.IListener#onAfterRespond(java.util.Map, org.apache.wicket.ajax.AjaxRequestTarget.IJavascriptResponse)
	 */
	@SuppressWarnings("unchecked")
	public void onAfterRespond(Map map, IJavascriptResponse response)
	{
		//we need to find all dojoWidget that should be reParsed
		Iterator<Entry<String,Component>> it = dojoComponents.entrySet().iterator();
		HashMap<String, Component> real = new HashMap<String, Component>();
		String requires = "";
		
		while (it.hasNext()){
			Component c = (Component)((Entry<String,Component>)it.next()).getValue();
			
			for (Object behavior : c.getBehaviors()){
				if (behavior instanceof AbstractRequireDojoBehavior){
					requires += ((AbstractRequireDojoBehavior)behavior).getRequire();
				}
			}
			
			if (!hasParentAdded(c) && c instanceof IDojoWidget){
				//we do not need to reParse This widget, remove it
				real.put(c.getMarkupId(), c);
			}
		}
		dojoComponents = real;

		if (generateReParseJs()!=null){
			response.addJavascript(requires + generateReParseJs());
		}
	}

	/**
	 * @see org.apache.wicket.ajax.AjaxRequestTarget.IListener#onBeforeRespond(java.util.Map, org.apache.wicket.ajax.AjaxRequestTarget)
	 */
	@SuppressWarnings("unchecked")
	public void onBeforeRespond(Map map, AjaxRequestTarget target)
	{	
		this.target = target;
	}
	
	/**
	 * @param component 
	 */
	public void addComponent(Component component){
		dojoComponents.put(component.getMarkupId(), component);
	}
	
	private String generateReParseJs(){
		if (!dojoComponents.isEmpty()){
			return "dojo.hostenv.makeWidgets();";
		} else {
			return null;
		}
	}
	
	/**
	 * Look for ancestor in the hierarchie
	 * @param component 
	 * @return
	 */
	private boolean hasParentAdded(Component component){
		Component current = component;
		while(current.getParent()!= null){
			 if (dojoComponents.containsKey(current.getParent().getMarkupId()) && (current.getParent() instanceof IDojoWidget)){
				 return true;
			 }
			 current = current.getParent();
		}
		return false;
	}

	/**
	 * @return get the {@link DojoTargetRefresherManager} for this request
	 */
	public static DojoTargetRefresherManager get()
	{
		WebRequest request = (WebRequest) WebRequestCycle.get().getRequest();
		if (request instanceof IDojoRequest) {
			return ((IDojoRequest) request).getDojoTargetRefresherManager(true);
		} else {
			DojoTargetRefresherManager mgr = (DojoTargetRefresherManager) request.getHttpServletRequest().getAttribute(HTTP_REQUEST_ATTRIBUTE_NAME);
			if (mgr == null) {
				mgr = new DojoTargetRefresherManager();
				request.getHttpServletRequest().setAttribute(HTTP_REQUEST_ATTRIBUTE_NAME, mgr);
			}
			return mgr;
		}
	}

	

}
