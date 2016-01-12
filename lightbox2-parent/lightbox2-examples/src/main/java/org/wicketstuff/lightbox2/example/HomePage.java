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
package org.wicketstuff.lightbox2.example;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.wicketstuff.lightbox2.LightboxLink;
import org.wicketstuff.lightbox2.LightboxPanel;

public class HomePage extends WebPage
{

	private static final long serialVersionUID = -2090364494437192109L;

	public HomePage()
	{

		// Using Resoures
		ResourceReference image = new PackageResourceReference(HomePage.class,
			"resources/image-1.jpg");
		ResourceReference thumbnail = new PackageResourceReference(HomePage.class,
			"resources/thumb-1.jpg");
		add(new LightboxLink("link", image).add(new Image("image", thumbnail)));

		// Image Set
		add(new LightboxPanel("lightbox1", "images/image-1.jpg", "images/thumb-1.jpg", "plant"));
		add(new LightboxPanel("lightbox2", "images/image-2.jpg", "images/thumb-2.jpg", "plant"));
		add(new LightboxPanel("lightbox3", "images/image-3.jpg", "images/thumb-3.jpg", "plant"));

	}

}
