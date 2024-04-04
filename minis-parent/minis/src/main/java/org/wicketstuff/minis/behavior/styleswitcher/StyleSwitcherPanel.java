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
package org.wicketstuff.minis.behavior.styleswitcher;

import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.resource.PackageResourceReference;

/**
 * StyleSwitcherPanel
 * 
 * An example of using {@link StyleSwitcher} with three {@link StyleSwitcherLink} components to
 * quickly switch between small, medium, and large fonts by simply clicking an icon.
 * 
 * Inspired by Paul Sowden's A List Apart article "Altenative Style"
 * http://alistapart.com/stories/alternate/
 * 
 * @author Tauren Mills (tauren)
 */
public class StyleSwitcherPanel extends Panel
{
	private static final long serialVersionUID = 1L;

	private static final PackageResourceReference SS_CSS_LARGE = new PackageResourceReference(
		StyleSwitcher.class, "large.css");
	private static final PackageResourceReference SS_CSS_MEDIUM = new PackageResourceReference(
		StyleSwitcher.class, "medium.css");
	private static final PackageResourceReference SS_CSS_SMALL = new PackageResourceReference(
		StyleSwitcher.class, "small.css");

	/**
	 * Constructor
	 * 
	 * @param id
	 */
	public StyleSwitcherPanel(final String id)
	{
		super(id);

		final StyleSwitcher switcher = new StyleSwitcher();
		switcher.addStylesheet("large", SS_CSS_LARGE);
		switcher.addStylesheet("medium", SS_CSS_MEDIUM);
		switcher.addStylesheet("small", SS_CSS_SMALL);
		add(switcher);

		add(new StyleSwitcherLink("largelink", "large").add(new Image("large",
			new PackageResourceReference(StyleSwitcher.class, "large.png"))));
		add(new StyleSwitcherLink("mediumlink", "medium").add(new Image("medium",
			new PackageResourceReference(StyleSwitcher.class, "medium.png"))));
		add(new StyleSwitcherLink("smalllink", "small").add(new Image("small",
			new PackageResourceReference(StyleSwitcher.class, "small.png"))));
	}
}
