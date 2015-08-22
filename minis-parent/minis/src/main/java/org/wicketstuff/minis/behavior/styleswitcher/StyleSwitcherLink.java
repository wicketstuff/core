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

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * StyleSwitcherLink
 * 
 * Creates a {@link WebMarkupContainer} that adds an href attribute to a javascript function that
 * switches the active stylesheet. An instance of {@link StyleSwitcher} must already be defined in
 * order for the required javascript to be included. When used in conjunction with
 * {@link StyleSwitcherLink}, it easy to switch active stylesheets with the click of a link.
 * 
 * Example:
 * 
 * <pre>
 * <code>
 *  add(new StyleSwitcherLink(&quot;link&quot;, &quot;medium&quot;);
 *  
 *  &lt;a wicket:id=&quot;link&quot; href=&quot;#&quot;&gt;medium font size&lt;/a&gt;
 * </code>
 * </pre>
 * 
 * Inspired by Paul Sowden's A List Apart article "Altenative Style"
 * http://alistapart.com/stories/alternate/
 * 
 * @author Tauren Mills (tauren)
 * @author Igor Vaynberg (ivaynberg at apache dot org)
 */
public class StyleSwitcherLink extends WebMarkupContainer
{
	private static final long serialVersionUID = 1L;

	/**
	 * Construct.
	 * 
	 * @param id
	 *            The wicket:id used in page markup
	 * @param title
	 *            The title of the stylesheet to make active
	 */
	public StyleSwitcherLink(final String id, final IModel<?> title)
	{
		super(id, title);
	}

	/**
	 * Construct.
	 * 
	 * @param id
	 *            The wicket:id used in page markup
	 * @param title
	 *            The title of the stylesheet to make active
	 */
	public StyleSwitcherLink(final String id, final String title)
	{
		super(id, new Model<String>(title));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onComponentTag(final ComponentTag tag)
	{
		super.onComponentTag(tag);
		final String title = getDefaultModelObjectAsString();
		tag.put("href", "#");
		tag.put("onclick", StyleSwitcher.Javascript.forActivatingStylesheet(title));
	}
}
