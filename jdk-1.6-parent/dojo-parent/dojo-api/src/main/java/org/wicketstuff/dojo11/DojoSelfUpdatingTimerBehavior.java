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

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.util.time.Duration;

/**
 * <p>
 * Automatically re-renders the component it is attached to via AJAX at a
 * regular interval.
 * </p>
 * <p>
 * Add this Behavior to a Component to autoRenderIt every updateInterval. You can add an other 
 * component/js to the target with onPostProcessTarget method
 * 
 * </p>
 * 
 * @author Vincent Demay
 */
@SuppressWarnings("serial")
public class DojoSelfUpdatingTimerBehavior extends AbstractDojoTimerBehavior
{

	/**
	 * Construct.
	 * 
	 * @param updateInterval
	 *            Duration between AJAX callbacks
	 */
	public DojoSelfUpdatingTimerBehavior(Duration updateInterval)
	{
		super(updateInterval);
	}
	
	/**
	 * @param updateInterval
	 * @param loadingId
	 */
	public DojoSelfUpdatingTimerBehavior(final Duration updateInterval, final String loadingId)
	{
		super(updateInterval, loadingId);
	}

	/**
	 * @see org.wicketstuff.dojo11.AbstractDojoTimerBehavior#onTimer(org.apache.wicket.ajax.AjaxRequestTarget)
	 */
	protected final void onTimer(final AjaxRequestTarget target)
	{
		target.addComponent(getComponent());
		onPostProcessTarget(target);
	}

	/**
	 * Give the subclass a chance to add something to the target, like a
	 * javascript effect call. Called after the hosting component has been added
	 * to the target.
	 * 
	 * @param target The AJAX target
	 */
	protected void onPostProcessTarget(final AjaxRequestTarget target)
	{
	}

}
