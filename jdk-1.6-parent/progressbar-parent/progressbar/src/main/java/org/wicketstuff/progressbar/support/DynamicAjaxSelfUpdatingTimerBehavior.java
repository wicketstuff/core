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
package org.wicketstuff.progressbar.support;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.util.time.Duration;

/**
 * Extend the AjaxSelfUpdatingTimerBehavior to enabled dynamic adding of the behavior. This requires
 * adding a javascript timeout if added in an ajax request.
 * 
 * @author Christopher Hlubek (hlubek)
 * 
 */
public class DynamicAjaxSelfUpdatingTimerBehavior extends AjaxSelfUpdatingTimerBehavior
{

	private static final long serialVersionUID = 1L;

	public DynamicAjaxSelfUpdatingTimerBehavior(Duration updateInterval)
	{
		super(updateInterval);
	}

	@Override
	protected void onBind()
	{
		super.onBind();
		// dynamically start the self update!
		AjaxRequestTarget ajaxRequestTarget = RequestCycle.get().find(AjaxRequestTarget.class);
		if (ajaxRequestTarget != null) {
			ajaxRequestTarget.appendJavaScript(getJsTimeoutCall(getUpdateInterval()));
		}
	}

}
