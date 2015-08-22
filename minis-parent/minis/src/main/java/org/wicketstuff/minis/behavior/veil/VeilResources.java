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
package org.wicketstuff.minis.behavior.veil;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.request.resource.ResourceReference;

/**
 * A behavior that includes all the necessary resources to use the veil on the client
 * 
 * @author ivaynberg
 */
public class VeilResources extends Behavior
{
	/**
	 * JavaScript interface for the veil
	 * 
	 * @author ivaynberg
	 */
	public static class Javascript
	{
		/**
		 * Javascript interface to the veil that is decoupled from wicket
		 * 
		 * @author ivaynberg
		 */
		public static class Generic
		{
			/**
			 * Generates javascript to hide a veil over a tag
			 * 
			 * @param markupId
			 *            markup id of tag that will be covered by veil
			 * @param className
			 *            css class name for veil
			 * @return javascript
			 */
			public static String hide(final String markupId)
			{
				return "Wicket.Veil.toggle('" + markupId + "');";
			}

			/**
			 * Generates javascript to show a veil over a tag
			 * 
			 * @param markupId
			 *            markup id of tag that will be covered by veil
			 * @return javascript
			 */
			public static String show(final String markupId)
			{
				return show(markupId, DEFAULT_CSS_CLASS_NAME);
			}

			/**
			 * Generates javascript to show a veil over a tag
			 * 
			 * @param markupId
			 *            markup id of tag that will be covered by veil
			 * @param className
			 *            css class name for veil
			 * @return javascript
			 */
			public static String show(final String markupId, final String className)
			{
				return "Wicket.Veil.show('" + markupId + "', {className:'" + className + "'});";
			}

			/**
			 * Generates javascript to toggle a veil over a tag
			 * 
			 * @param markupId
			 *            markup id of tag that will be covered by veil
			 * @return javascript
			 */

			public static String toggle(final String markupId)
			{
				return toggle(markupId, DEFAULT_CSS_CLASS_NAME);
			}

			/**
			 * Generates javascript to toggle a veil over a tag
			 * 
			 * @param markupId
			 *            markup id of tag that will be covered by veil
			 * @param className
			 *            css class name for veil
			 * @return javascript
			 */
			public static String toggle(final String markupId, final String className)
			{
				return "Wicket.Veil.toggle('" + markupId + "', {className:'" + className + "'});";
			}

			/**
			 * Block instantiation
			 */
			private Generic()
			{

			}
		}

		/**
		 * Generates javascript to hide a veil over a tag
		 * 
		 * @param component
		 *            component that will be covered by veil
		 * @param className
		 *            css class name for veil
		 * @return javascript
		 */
		public static String hide(final Component component)
		{
			return Generic.hide(component.getMarkupId());
		}

		/**
		 * Generates javascript to show a veil over a tag
		 * 
		 * @param component
		 *            component that will be covered by veil
		 * @return javascript
		 */
		public static String show(final Component component)
		{
			return Generic.show(component.getMarkupId());
		}

		/**
		 * Generates javascript to show a veil over a tag
		 * 
		 * @param component
		 *            component that will be covered by veil
		 * @param className
		 *            css class name for veil
		 * @return javascript
		 */
		public static String show(final Component component, final String className)
		{
			return Generic.show(component.getMarkupId(), className);
		}

		/**
		 * Generates javascript to show a toggle over a tag
		 * 
		 * @param component
		 *            component that will be covered by veil
		 * @return javascript
		 */
		public static String toggle(final Component component)
		{
			return Generic.toggle(component.getMarkupId());
		}

		/**
		 * Generates javascript to toggle a veil over a tag
		 * 
		 * @param component
		 *            component that will be covered by veil
		 * @param className
		 *            css class name for veil
		 * @return javascript
		 */
		public static String toggle(final Component component, final String className)
		{
			return Generic.toggle(component.getMarkupId(), className);
		}

		/**
		 * Block instantiation
		 */
		private Javascript()
		{

		}
	}

	private static final long serialVersionUID = 1L;

	private static final ResourceReference JS = new JavaScriptResourceReference(
		VeilResources.class, "wicket-veil.js");

	private static final ResourceReference CSS = new PackageResourceReference(VeilResources.class,
		"wicket-veil.css");

	/**
	 * Css name of wicket's default veil css class
	 */
	public static final String DEFAULT_CSS_CLASS_NAME = "wicket-veil";

	/**
	 * @see Behavior#bind(org.apache.wicket.Component)
	 */
	@Override
	public void bind(final Component component)
	{
		super.bind(component);
		component.setOutputMarkupId(true);
	}

	/**
	 * @see Behavior#renderHead(org.apache.wicket.markup.html.IHeaderResponse)
	 */
	@Override
	public void renderHead(final Component c, final IHeaderResponse response)
	{
		response.render(JavaScriptHeaderItem.forReference(JS));
		response.render(CssHeaderItem.forReference(CSS));
	}

}
