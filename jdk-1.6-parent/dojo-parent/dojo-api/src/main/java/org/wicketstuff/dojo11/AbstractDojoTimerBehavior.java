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

import org.apache.wicket.ajax.AbstractAjaxTimerBehavior;
import org.apache.wicket.util.time.Duration;

/**
 * Dojo Ajax auto update handler. <br/>
 * Bind this handler to any component implementing IUpdatable.<br/>
 * Every <i>interval</i> the bound component's update() method will be called<br/>
 * followed by a rerender of the bound component.<br/>
 * 
 * @author Ruud Booltink
 * @author Marco van de Haar
 *
 * TODO : see getJsTimeoutCall in {@link AbstractAjaxTimerBehavior} to implement it
 */
public abstract class AbstractDojoTimerBehavior extends AbstractAjaxTimerBehavior
{
	private String loadingId = "";
	
	/**
	 * 
	 * @param updateInterval Duration between AJAX callbacks
	 */
	public AbstractDojoTimerBehavior(final Duration updateInterval)
	{
		super(updateInterval);
	}
	
	/**
	 * @param updateInterval Duration between AJAX callbacks
	 * @param loadingId
	 */
	public AbstractDojoTimerBehavior(final Duration updateInterval, String loadingId)
	{
		super(updateInterval);
		this.loadingId = loadingId;
		
	}
	
}
