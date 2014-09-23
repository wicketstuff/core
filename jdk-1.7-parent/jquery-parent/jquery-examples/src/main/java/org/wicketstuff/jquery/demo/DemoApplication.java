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
package org.wicketstuff.jquery.demo;

import java.util.Date;

import org.apache.wicket.ConverterLocator;
import org.apache.wicket.IConverterLocator;
import org.apache.wicket.Page;
import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.markup.html.IPackageResourceGuard;
import org.apache.wicket.markup.html.SecurePackageResourceGuard;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.convert.converter.DateConverter;
import org.wicketstuff.jquery.ajaxbackbutton.Page4AjaxBackButton;
import org.wicketstuff.jquery.demo.dnd.Page4ClientSideOnly;
import org.wicketstuff.jquery.demo.ui.Page4Slider;

public class DemoApplication extends WebApplication
{

	@Override
	protected void init()
	{
		getMarkupSettings().setStripWicketTags(true);
		mountPackage("/samples", Page4Block.class);
		mountPackage("/backbutton", Page4AjaxBackButton.class);
		mountPackage("/samples/dnd", Page4ClientSideOnly.class);
		mountPackage("/ui", Page4Slider.class);
		super.init();

        IPackageResourceGuard packageResourceGuard = getResourceSettings().getPackageResourceGuard();
        if (packageResourceGuard instanceof SecurePackageResourceGuard)
        {
            SecurePackageResourceGuard secureGuard = (SecurePackageResourceGuard) packageResourceGuard;
            secureGuard.addPattern("+*.map");
        }

        // if (Application.DEVELOPMENT.equals(getConfigurationType())) {
// getDebugSettings().setOutputMarkupContainerClassName(true);
// }
	}

	@Override
	public Class<? extends Page> getHomePage()
	{
		return Page4ClientSideOnly.class;
	}

	@Override
	protected IConverterLocator newConverterLocator()
	{
		ConverterLocator back = new ConverterLocator();
		back.set(Date.class, new DateConverter());
		return back;
	}

	@Override
	public RuntimeConfigurationType getConfigurationType()
	{
		return RuntimeConfigurationType.DEPLOYMENT;
// return RuntimeConfigurationType.DEVELOPMENT;
	}

}
