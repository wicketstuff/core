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
package org.wicketstuff.wicket.mount.example.ui.pkgmount;

import org.apache.wicket.Application;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.wicketstuff.wicket.mount.AutoMountWebApplication;
import org.wicketstuff.wicket.mount.annotation.AutoMount;
import org.wicketstuff.wicket.mount.core.annotation.MountPath;

/**
 * @author jsarman
 */
public class PackageMountPage2 extends WebPage
{

	private static final long serialVersionUID = 1L;

	public PackageMountPage2()
	{

		final String mp;
		if (Application.get() instanceof AutoMountWebApplication)
		{
			String mimeType = Application.get().getClass().getAnnotation(AutoMount.class).mimeExtension();
			mp = getClass().getPackage().getAnnotation(MountPath.class).value()
					+ "/" + getClass().getSimpleName() + "." + mimeType;
		} else
		{
			mp = getClass().getPackage().getAnnotation(MountPath.class).value()
					+ "/" + getClass().getSimpleName();
		}

		add(new Label("message",
				"The relative url should be set to " + mp));
	}
}
