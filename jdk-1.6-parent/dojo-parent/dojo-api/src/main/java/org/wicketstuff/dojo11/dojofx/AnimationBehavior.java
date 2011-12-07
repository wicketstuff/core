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

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.util.string.Strings;
import org.wicketstuff.dojo11.util.JavaScriptEvent;

/**
 * @author Stefan Fussenegger
 * TODO: test
 */
public class AnimationBehavior extends AbstractAnimationBehavior
{
	private Animation animation;
	private JavaScriptEvent event;

	/**
	 * Construct.
	 * @param animation
	 * @param event 
	 */
	public AnimationBehavior(Animation animation, JavaScriptEvent event) {
		this.animation = animation;
		this.event = event;
	}

	/**
	 * @see org.wicketstuff.dojo11.dojofx.AbstractAnimationBehavior#getAnimation()
	 */
	@Override
	public Animation getAnimation()
	{
		return animation;
	}

	@Override
	protected void onComponentTag(ComponentTag tag)
	{
		String animation = getAnimation().getAnimationScript(getComponent().getMarkupId());
		String current = tag.getAttributes().getString(event.getName());
		if (!Strings.isEmpty(current)) {
			animation = current+";"+animation;
		}
		tag.getAttributes().put(event.getName(), animation);
		super.onComponentTag(tag);
	}
	
	
}
