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
package org.wicketstuff.datatables.demo;

import de.agilecoders.wicket.jquery.WicketJquerySelectors;
import de.agilecoders.wicket.webjars.WicketWebjars;
import org.apache.wicket.Page;
import org.apache.wicket.markup.html.SecurePackageResourceGuard;
import org.apache.wicket.protocol.http.WebApplication;
import org.wicketstuff.datatables.demo.infiniteScroll.InfiniteScrollDemoPage;
import org.wicketstuff.datatables.demo.infiniteScroll.VirtualScrollDemoResourceReference;

public class DemoApplication extends WebApplication
{

	@Override
	public Class<? extends Page> getHomePage()
	{
		return HomePage.class;
	}

    @Override
    protected void init() {
        super.init();

        WicketWebjars.install(this);
        WicketJquerySelectors.install(this);

        mountPage("new", NewPage.class);

        infiniteScroll();

        getMarkupSettings().setStripWicketTags(true);

	    configureResourceGuard();
    }

	private void configureResourceGuard() {
		SecurePackageResourceGuard packageResourceGuard = (SecurePackageResourceGuard) getResourceSettings().getPackageResourceGuard();
		packageResourceGuard.addPattern("+*.css.map");
	}

	private void infiniteScroll() {
		mountPage("infiniteScroll", InfiniteScrollDemoPage.class);
		mountResource("infiniteScrollData", new VirtualScrollDemoResourceReference());
	}
}
