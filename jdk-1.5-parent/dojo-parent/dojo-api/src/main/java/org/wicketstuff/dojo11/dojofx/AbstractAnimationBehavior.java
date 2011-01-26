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

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.util.string.Strings;
import org.wicketstuff.dojo11.AbstractRequireDojoBehavior;

/**
 * @author Stefan Fussenegger
 */
public abstract class AbstractAnimationBehavior extends AbstractRequireDojoBehavior
{	
	/**
	 * @return the animation
	 */
	public abstract Animation getAnimation();
	
	/**
	 * @param libs
	 */
	public void setRequire(RequireDojoLibs libs)
	{
		getAnimation().setRequire(libs);
	}

	
	
	@Override
	protected void onComponentTag(ComponentTag tag)
	{
		String style = getAnimation().getInitStyle();
		if (!Strings.isEmpty(style)) {
			String currentStyle = (String) tag.getAttributes().get("style");
			if (!Strings.isEmpty(currentStyle)) {
				style = currentStyle + ";"+style;
			}
			tag.getAttributes().put("style", style);
		}
		super.onComponentTag(tag);
	}

	/**
	 * @see org.apache.wicket.ajax.AbstractDefaultAjaxBehavior#respond(org.apache.wicket.ajax.AjaxRequestTarget)
	 */
	@Override
	protected void respond(AjaxRequestTarget target)
	{
		// do nothing
	}

	/**
	 * @see org.wicketstuff.dojo11.AbstractRequireDojoBehavior#renderHead(org.apache.wicket.markup.html.IHeaderResponse)
	 */
	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);
		String init = getAnimation().getInitJavaScript(getComponent().getMarkupId());
		if (!Strings.isEmpty(init)) {
			response.renderOnDomReadyJavascript(init);
		}
	}
	
	
}
