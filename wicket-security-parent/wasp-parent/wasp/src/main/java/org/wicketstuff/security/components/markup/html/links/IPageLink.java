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
package org.wicketstuff.security.components.markup.html.links;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.util.io.IClusterable;

/**
 * Interface that is used to implement delayed page linking. The getPage() method returns an
 * instance of Page when a link is actually clicked (thus avoiding the need to create a destination
 * Page object for every link on a given Page in advance). The getPageIdentity() method returns the
 * subclass of Page that getPage() will return if and when it is called.
 * <p>
 * This way of arranging things is useful in determining whether a link links to a given page, which
 * is in turn useful for deciding how to display the link (because links in a navigation which link
 * to a page itself are not useful and generally should instead indicate where the user is in the
 * navigation).
 *
 * @see Link#linksTo(Page)
 * @author Jonathan Locke
 */
public interface IPageLink extends IClusterable
{
	/**
	 * Gets the page to go to.
	 *
	 * @return The page to go to.
	 */
	Page getPage();

	/**
	 * Gets the class of the destination page, which serves as a form of identity that can be used
	 * to determine if a link is on the same Page that it links to.
	 * <p>
	 * A page's identity is used if the user is allowed to visit the target page.
	 *
	 * @return The class of page linked to, as a form of identity
	 * @see Link#linksTo(Page)
	 */
	Class<? extends Page> getPageIdentity();
}
