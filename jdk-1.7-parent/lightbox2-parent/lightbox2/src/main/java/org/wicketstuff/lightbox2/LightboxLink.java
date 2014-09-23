/*
 *  Copyright 2012 inaiat.
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
package org.wicketstuff.lightbox2;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.resource.ResourceReference;
import org.wicketstuff.lightbox2.references.LightboxCssResourceReference;
import org.wicketstuff.lightbox2.references.LightboxJavascriptResourceReference;

public class LightboxLink extends AbstractLink
{

	private static final long serialVersionUID = 8055791489048600642L;

	private final ResourceReference imageResourceReference;
	private final String contextRelativePath;
	private final String group;

	public LightboxLink(String id, final String contextRelativePath)
	{
		this(id, contextRelativePath, null);
	}

	public LightboxLink(String id, final ResourceReference imageResource)
	{
		this(id, imageResource, null);
	}

	public LightboxLink(String id, final String contextRelativePath, String group)
	{
		super(id);
		this.contextRelativePath = contextRelativePath;
		this.imageResourceReference = null;
		this.group = group;
	}

	public LightboxLink(String id, final ResourceReference imageResource, String group)
	{
		super(id);
		this.imageResourceReference = imageResource;
		this.contextRelativePath = null;
		this.group = group;
	}

	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);
		response.render(JavaScriptHeaderItem.forReference(LightboxJavascriptResourceReference.get()));
		response.render(CssHeaderItem.forReference(LightboxCssResourceReference.get()));
	}

	@Override
	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);
		checkComponentTag(tag, "a");
		CharSequence charSequence = null;
		if (contextRelativePath == null)
		{
			charSequence = RequestCycle.get().urlFor(imageResourceReference,
				getPage().getPageParameters());
		}
		else
		{
			charSequence = contextRelativePath;
		}
		tag.put("href", charSequence);

		StringBuilder stringRel = new StringBuilder();
		stringRel.append("lightbox");
		if (group != null)
		{
			stringRel.append("[").append(group).append("]");
		}
		tag.put("rel", stringRel);
		
		if (group != null)
		{
			tag.put("data-lightbox", group);
		}
		else
		{
			tag.put("data-lightbox", getMarkupId());
		}
	}

}
