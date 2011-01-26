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
 * @author Stefan Fussenegger
 *
 */
public class FXOnMouseOverFader extends FxToggler
{
	/**
	 * Standard constructor for a fader which listens to trigger's mouseover
	 * events. if startDisplay is true, component will start faded out.
	 * 
	 * @param duration
	 * @param trigger
	 * @param startDisplay
	 *            whether component starts faded out.
	 */
	public FXOnMouseOverFader(int duration, Component trigger, boolean startDisplay)
	{
		this(duration, trigger, startDisplay, 1.0, 0.0);
	}

	/**
	 * Constructor for a fader with a set starting and ending opacity. Component
	 * will fade from start to end opacity.
	 * 
	 * @param duration
	 * @param trigger
	 * @param startDisplay
	 *            whether component starts with opacity=startOpac
	 * @param startOpac
	 * @param endOpac
	 */
	public FXOnMouseOverFader(int duration, Component trigger, boolean startDisplay,
			double startOpac, double endOpac) {
		super(ToggleEvents.MOUSE_OVER, ToggleAnimations.getFadeToOpacity(startOpac, endOpac, duration), trigger, startDisplay);
	}
}
