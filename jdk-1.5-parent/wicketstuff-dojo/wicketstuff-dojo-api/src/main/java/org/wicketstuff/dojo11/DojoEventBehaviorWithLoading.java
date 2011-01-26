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

import org.apache.wicket.Component;
import org.apache.wicket.ajax.IAjaxIndicatorAware;

/**
 * This Behavior work as {@link DojoEventBehavior} but it allow user to set a component on it
 * this component will be show during ajax request running and Hide when response will be render. 
 * see on the DojoEventBehaviorWithLoading constructor
 * 
 * @author vdemay
 *
 */
public abstract class DojoEventBehaviorWithLoading extends DojoEventBehavior implements IAjaxIndicatorAware
{

	private Component loadingComponent;
	
	/**
	 * @param event
	 * @param loadingComponent component to show or hide for loading
	 */
	public DojoEventBehaviorWithLoading(String event, Component loadingComponent)
	{
		super(event);
		this.loadingComponent = loadingComponent;
	}

	/* (non-Javadoc)
	 * @see wicket.ajax.IAjaxIndicatorAware#getAjaxIndicatorMarkupId()
	 */
	/**
	 * @see org.apache.wicket.ajax.IAjaxIndicatorAware#getAjaxIndicatorMarkupId()
	 */
	public String getAjaxIndicatorMarkupId()
	{
		return loadingComponent.getMarkupId();
	}

	protected void onBind()
	{
		super.onBind();
		loadingComponent.setOutputMarkupId(true);
	}

}
