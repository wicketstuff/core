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
package org.wicketstuff.wicket.mount.example;

import org.apache.wicket.Page;
import org.wicketstuff.wicket.mount.AutoMountWebApplication;
import org.wicketstuff.wicket.mount.annotation.AutoMount;
import org.wicketstuff.wicket.mount.example.ui.HomePage;

/**
 * @author jsarman
 */
@AutoMount(mimeExtension = "html")
public class WicketApplication extends AutoMountWebApplication
{

	@Override
	public Class<? extends Page> getHomePage()
	{
		return HomePage.class;
	}

}
