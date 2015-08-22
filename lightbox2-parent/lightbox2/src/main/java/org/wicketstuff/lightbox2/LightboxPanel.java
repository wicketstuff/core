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

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.image.ContextImage;
import org.apache.wicket.markup.html.panel.Panel;

public class LightboxPanel extends Panel
{

	private static final long serialVersionUID = 1021639483382388566L;

	public LightboxPanel(String id, String image, String thumbnail)
	{
		this(id, image, thumbnail, null, null, null);
	}

	public LightboxPanel(String id, String image, String thumbnail, String group)
	{
		this(id, image, thumbnail, group, null, null);
	}

	public LightboxPanel(String id, String image, String thumbnail, String group, String linkTitle,
		String imageAlt)
	{
		super(id);
		ContextImage contextImage = new ContextImage("imageResourceReference", thumbnail);
		if (imageAlt != null)
		{
			contextImage.add(new AttributeModifier("alt", imageAlt));
		}

		LightboxLink lightboxLink = new LightboxLink("link", image, group);
		if (linkTitle != null)
		{
			lightboxLink.add(new AttributeModifier("title", linkTitle));
		}
		lightboxLink.add(contextImage);
		add(lightboxLink);
	}

}
