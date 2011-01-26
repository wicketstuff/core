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
 * 
 */
@Deprecated
public class FXOnClickSlider extends FxToggler
{

	private String HTMLID;
	private String componentId;
	private final int x;
	private final int y;
	private final String type;
	private int fromx;
	private int fromy;


	/**
	 * @param duration
	 * @param trigger
	 * @param x
	 * @param y
	 * @param relative
	 */
	public FXOnClickSlider(int duration, Component trigger, int x, int y, boolean relative)
	{
		super(ToggleEvents.CLICK, ToggleAnimations.getSlide(x, y, relative, duration), trigger, true);
		this.x = x;
		this.y = y;
		if (relative)
		{
			type = "relative";
		}
		else
		{
			type = "absolute";
		}

	}


//	protected void onBind()
//	{
//		Component c = getComponent();
//		this.component = (Component)c;
//		this.componentId = c.getId();
//
//		// create a unique HTML for the explode component
//		HTMLID = this.component.getId() + "_" + component.getPath();
//		// Add ID to component, and bind effect to trigger
//		this.component.add(new AttributeModifier("id", true, new Model(HTMLID)));
//
//		this.component.add(new AppendAttributeModifier("style", true, new Model(
//				"position: absolute")));
//
//		if (type == "relative")
//		{
//			this.getTrigger().add(
//					new AppendAttributeModifier(getEventName(), true, new Model(
//							"dojo.lfx.html.slideBy(document.getElementById('" + HTMLID + "'), [" + x
//									+ ", " + y + "]," + getDuration() + ").play();")));
//		}
//		// assume that type == absolute
//		else
//		{
//			this.getTrigger().add(
//					new AppendAttributeModifier(getEventName(), true, new Model(
//							"dojo.lfx.html.slideTo(document.getElementById('" + HTMLID + "'), [" + x
//									+ ", " + y + "]," + getDuration() + ").play();")));
//		}
//
//	}

}
