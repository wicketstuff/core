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
package org.wicketstuff.minis.styleswitcher;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.IClusterable;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.JavascriptResourceReference;
import org.apache.wicket.request.resource.ResourceReference;

/**
 * StyleSwitcher
 * 
 * Adds a set of alternate stylesheet links to the header with title attributes.
 * When used in conjunction with {@link StyleSwitcherLink}, it easy to switch
 * active stylesheets with the click of a link.
 * 
 * Inspired by Paul Sowden's A List Apart article "Altenative Style"
 * http://alistapart.com/stories/alternate/
 * 
 * @author Tauren Mills (tauren)
 * @author Igor Vaynberg (ivaynberg at apache dot org)
 */
public class StyleSwitcher extends AbstractBehavior
{

	private static final ResourceReference SS_JS = new JavascriptResourceReference(
			StyleSwitcher.class, "styleswitcher.js");

	private List<TitledResourceReferenceTuple> stylesheets = new ArrayList<TitledResourceReferenceTuple>();


	/**
	 * Construct.
	 * 
	 */
	public StyleSwitcher()
	{
	}

	/**
	 * Adds stylesheet to this stylesheet switcher
	 * 
	 * @param reference
	 *            A resource reference to an alternate stylesheet
	 * @param title
	 *            Name of this stylesheet
	 */
	public void addStylesheet(String title, ResourceReference reference)
	{
		stylesheets.add(new TitledResourceReferenceTuple(title, reference));
	}

	/**
	 * @see org.apache.wicket.behavior.AbstractBehavior#renderHead(org.apache.wicket.markup.html.IHeaderResponse)
	 */
	@Override
	public void renderHead(IHeaderResponse response)
	{
		response.renderJavascriptReference(SS_JS);

		for (TitledResourceReferenceTuple stylesheet : stylesheets)
		{
			StringBuilder link = new StringBuilder(128);
			link.append("<link rel=\"alternate stylesheet\" type=\"text/css\" href=\"");
			link.append(RequestCycle.get().urlFor(stylesheet.getReference(), new PageParameters()));
			link.append("\" title=\"");
			link.append(stylesheet.getTitle());
			link.append("\"/>");
			response.renderString(link.toString());
		}
	}

	/**
	 * Javascript access methods
	 * 
	 * @author Igor Vaynberg (ivaynberg at apache dot org)
	 * 
	 */
	public static class Javascript
	{
		private Javascript()
		{
		}

		public static String forActivatingStylesheet(String title)
		{
			return "StyleSwitcher.setActiveStyleSheet('" + title + "');";
		}
	}


	private static class TitledResourceReferenceTuple implements IClusterable
	{
		private String title;
		private ResourceReference reference;

		/**
		 * @param name
		 * @param reference
		 */
		public TitledResourceReferenceTuple(String name, ResourceReference reference)
		{
			super();
			this.title = name;
			this.reference = reference;
		}

		/**
		 * @return title
		 */
		public String getTitle()
		{
			return title;
		}

		/**
		 * @return reference
		 */
		public ResourceReference getReference()
		{
			return reference;
		}


	}
}
