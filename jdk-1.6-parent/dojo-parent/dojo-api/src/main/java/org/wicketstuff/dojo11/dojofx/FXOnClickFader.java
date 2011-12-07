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
 * Class that can add fading functionality to a vissible Wicket Component This
 * class let's you set a trigger, a duration, a start opacity and an end
 * opacity.
 * 
 * This class is meant for API compatibility with old wicketstuff-dojo. Possibility
 * to fade components in or out (not toggling) was removed.
 * 
 * @TODO usage examples
 * 
 * @author Marco van de Haar
 * @author Ruud Booltink
 */
public class FXOnClickFader extends FxToggler
{
	/**
	 * convenience contructor with 2 parameters; sets startDisplay default to
	 * false
	 * 
	 * @param duration
	 *            duration of the fade effect
	 * @param trigger
	 *            Component which ONCLICK method will be bound to trigger the
	 *            fade effect.
	 */
	public FXOnClickFader(int duration, Component trigger)
	{
		this(duration, trigger, false);
	}


	/**
	 * Constructor to make a simple fade effect with a trigger and a duration.
	 * 
	 * @param duration
	 *            duration of the fade effect
	 * @param trigger
	 *            Component which ONCLICK method will be bound to trigger the
	 *            fade effect.
	 * @param startDisplay
	 *            default false; true if component should start with full
	 *            opacity
	 */
	public FXOnClickFader(int duration, Component trigger, boolean startDisplay)
	{
		this(duration, trigger, startDisplay, 1.0, 0.0);
	}

	/**
	 * Constructor for a fade effect where the starting opacity and the ending
	 * opacity are set.
	 * 
	 * @param duration
	 *            duration of the fade effect
	 * @param trigger
	 *            Component which ONCLICK method will be bound to trigger the
	 *            fade effect.
	 * @param startDisplay
	 *            default false; true if component should start with starting
	 *            opacity (startOpac)
	 * @param startOpac
	 *            starting opacity between 1 and 0. (0 for transparant, 1 for
	 *            opaque)
	 * @param endOpac
	 *            ending opacity between 1 and 0. (0 for transparant, 1 for
	 *            opaque)
	 */
	public FXOnClickFader(int duration, Component trigger, boolean startDisplay, double startOpac, double endOpac)
	{
		super(ToggleEvents.CLICK, null, trigger, startDisplay);
	}
	
	
}
