/*
 * Copyright 2014 WicketStuff.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.wicket.mount.example.ui;

import java.util.UUID;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.wicket.mount.core.annotation.MountPath;
import org.wicketstuff.wicket.mount.example.ui.pkgmount.PackageMountPage1;
import org.wicketstuff.wicket.mount.example.ui.pkgmount.PackageMountPage2;
import org.wicketstuff.wicket.mount.example.ui.urlparam.ParamTestPage;

/**
 * @author jsarman
 */
@MountPath("index.html")
public class HomePage extends WebPage
{

	private static final long serialVersionUID = 1L;

	public HomePage(final PageParameters parameters)
	{

		String mp = getClass().getAnnotation(MountPath.class).value();
		// Add the simplest type of label
		add(new Label("message",
				"The relative url should be set to " + mp));

		add(new Link("pg1-bookmark")
		{

			@Override
			public void onClick()
			{
				setResponsePage(PackageMountPage1.class);
			}
		});
		add(new Link("pg2-nonbookmark")
		{

			@Override
			public void onClick()
			{
				setResponsePage(new PackageMountPage2());
			}
		});

		final String random = UUID.randomUUID().toString();
		add(new Link("url-param")
		{

			@Override
			public void onClick()
			{
				setResponsePage(ParamTestPage.class, new PageParameters().add("testParam", random));
			}
		}.add(new Label("randomParam", "Goto mounted page with pageparameter mounted as part of url. Parameter = " + random)));
		
	}
}
