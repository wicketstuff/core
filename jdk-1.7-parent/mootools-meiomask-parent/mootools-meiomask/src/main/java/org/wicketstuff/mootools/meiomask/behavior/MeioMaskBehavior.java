/*
 *  Copyright 2010 inaiat.
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
 */
package org.wicketstuff.mootools.meiomask.behavior;

import org.apache.wicket.Component;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.util.string.JavaScriptUtils;
import org.wicketstuff.mootools.meiomask.MaskType;

/**
 * 
 * @author inaiat
 */
public class MeioMaskBehavior extends MootoolsMoreBehavior
{

	private static final long serialVersionUID = 1L;
	private static final ResourceReference MEIO_MASK = new JavaScriptResourceReference(
		MeioMaskBehavior.class, "res/meio-mask-min-2.0.1.js");
	private final MaskType maskType;
	private final String options;

	public MeioMaskBehavior(MaskType type)
	{
		this(type, null);
	}

	public MeioMaskBehavior(MaskType type, String options)
	{
		maskType = type;
		this.options = options;
	}

	@Override
	public void renderHead(org.apache.wicket.Component component, IHeaderResponse response)
	{
		super.renderHead(component, response);
		response.render(JavaScriptHeaderItem.forReference(MEIO_MASK));
	}

	@Override
	public void onComponentTag(org.apache.wicket.Component component, ComponentTag tag)
	{
		super.onComponentTag(component, tag);
		tag.put("data-meiomask", maskType.getMaskName());

		if (options != null)
		{
			tag.put("data-meiomask-options", options);
		}
	}

	@Override
	public void afterRender(Component component)
	{
		super.afterRender(component);
		Response response = component.getResponse();
		response.write(JavaScriptUtils.SCRIPT_OPEN_TAG);
		response.write("$('");
		response.write(component.getMarkupId());
		response.write("').meiomask($('");
		response.write(component.getMarkupId());
		response.write("').get('data-meiomask'), JSON.decode($('");
		response.write(component.getMarkupId());
		response.write("').get('data-meiomask-options')));");
		response.write(JavaScriptUtils.SCRIPT_CLOSE_TAG);
	}

}
