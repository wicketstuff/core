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
package org.wicketstuff.dojo11.dojofx;


import org.apache.wicket.Component;

/**
 * This classacts as an AjaxHandler an can be added to components. It adds a
 * dojo.lfx.html wiper to the class which reactes to a target component's ONCLICK
 * method.
 * 
 * TODO: dojo.lfx.html currently
 * only supports top-down wiping, so down-top wiping and horizontal wiping is
 * currently not supported. We have however, requested this on the Dojo
 * animation wishlist. TODO: streamlining javascript handling: see
 * renderHeadContribution(HtmlHeaderContainer container).
 * 
 * @author Marco van de Haar
 * @author Ruud Booltink
 * 
 */
public class FXOnClickWiper extends FxToggler
{


	/**
	 * Constructor with custom startDisplay
	 * 
	 * @param trigger
	 *            Component which'ONCLICK triggers the wiping effect
	 * @param duration
	 *            duration for the animation.
	 * @param startDisplay
	 *            whether the wiping component starts wiped in or wiped
	 *            out(wiped in means visible)
	 */
	public FXOnClickWiper(int duration, Component trigger, boolean startDisplay)
	{
		super(ToggleEvents.CLICK, ToggleAnimations.getWipe(null, duration, null), trigger, startDisplay);

	}

	/**
	 * Constructor with default startDisplay
	 * 
	 * @param trigger
	 *            component which'ONCLICK triggers the wiping effect
	 * @param duration
	 *            duration for the animation.
	 */
	public FXOnClickWiper(int duration, Component trigger)
	{
		this(duration, trigger, false);
	}
}
