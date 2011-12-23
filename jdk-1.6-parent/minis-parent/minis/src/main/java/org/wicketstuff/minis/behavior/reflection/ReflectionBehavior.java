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
package org.wicketstuff.minis.behavior.reflection;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnLoadHeaderItem;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.util.string.AppendingStringBuffer;

/**
 * Behavior for adding reflections to an image. Uses reflection.js (v1.6), licensed under a MIT
 * license.
 * <p>
 * Add this behavior to your image to generate a reflection. You can change the height and opacity
 * of the reflection using the provided setters.
 * <p>
 * Ultimately the markup you attach this behavior to must be an image, but it need not be a Wicket
 * {@link Image} component, a markup container will suffice, provided the tag is an img-tag.
 * <p>
 * You can also use this class to generate add and remove reflection scriptlets for use in Ajax
 * request targets or any other place you want. All you need to do is ensure the reflection.js is
 * added to the head of the document. You can use the {@link #REFLECTION_JS} resource reference for
 * that.
 * <p>
 * The reflection.js library and more documentation can be found here <a
 * href="http://cow.neondragon.net/stuff/reflection/"
 * >http://cow.neondragon.net/stuff/reflection/</a>
 * 
 * @author Martijn Dashorst
 */
public class ReflectionBehavior extends Behavior
{
	/**
	 * Container for javascript calls this behavior enables
	 * 
	 * @author ivaynberg
	 * 
	 */
	public static final class Javascript
	{
		/**
		 * Gets the Javascript for removing a reflection from an image. You have to manually add the
		 * REFLECTION_JS resource reference to your page if you use this script directly.
		 * 
		 * @param id
		 *            the markup id of the image.
		 * @return the script.
		 */
		public static CharSequence hide(final String id)
		{
			final AppendingStringBuffer sb2 = new AppendingStringBuffer();
			sb2.append("Reflection.remove(document.getElementById('");
			sb2.append(id);
			sb2.append("');");
			return sb2;
		}

		/**
		 * Gets the script that adds a reflection to an image. You have to manually add the
		 * REFLECTION_JS resource reference to your page if you use this script directly.
		 * 
		 * @param id
		 *            the markup id of the image
		 * @param opacity
		 *            the opacity of the reflection (may be null for default value)
		 * @param height
		 *            the height of the reflection (may be null for default value)
		 * @return the script
		 */
		public static CharSequence show(final String id, final Integer opacity, final Integer height)
		{
			final AppendingStringBuffer sb2 = new AppendingStringBuffer();
			sb2.append("Reflection.add(document.getElementById('");
			sb2.append(id);
			sb2.append("'), { ");
			String komma = "";
			if (height != null)
			{
				sb2.append("height: ").append(height.toString()).append("/100");
				komma = ",";
			}
			if (opacity != null)
			{
				sb2.append(komma);
				sb2.append("opacity: ").append(opacity.toString()).append("/100");
			}
			sb2.append("});");
			return sb2;
		}

		/**
		 * Private constructor
		 */
		private Javascript()
		{
			super();
		}
	}

	private static final long serialVersionUID = 1L;

	/** The resource reference to the reflection.js file. */
	public static final ResourceReference REFLECTION_JS = new JavaScriptResourceReference(
		ReflectionBehavior.class, "reflection.js");

	/** The opacity of the reflection, ranges from 0 to 100. */
	private Integer reflectionOpacity = null;

	/** The percentage of the height of the reflection, ranges from 0 to 100. */
	private Integer reflectionHeight = null;

	/** List of components this behavior was added to. */
	private final List<Component> components = new ArrayList<Component>();

	/**
	 * Default constructor, creates a reflection using the default settings from reflection.js.
	 */
	public ReflectionBehavior()
	{
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param height
	 *            see {@link #setReflectionHeight(Integer)}
	 * @param opacity
	 *            see {@link #setReflectionOpacity(Integer)}
	 */
	public ReflectionBehavior(final Integer height, final Integer opacity)
	{
		setReflectionHeight(height);
		setReflectionOpacity(opacity);
	}

	/**
	 * Binds the component to this behavior.
	 * 
	 * @see Behavior#bind(Component)
	 */
	@Override
	public void bind(final Component component)
	{
		super.bind(component);
		component.setOutputMarkupId(true);
		components.add(component);
	}

	/**
	 * Adds the reflection.js javascript to the page.
	 * 
	 * @see Behavior#renderHead(org.apache.wicket.markup.html.IHeaderResponse)
	 */
	@Override
	public void renderHead(Component c, final IHeaderResponse response)
	{
		super.renderHead(c, response);
		response.render(JavaScriptHeaderItem.forReference(REFLECTION_JS));

		final StringBuilder sb = new StringBuilder();
		for (final Component component : components)
			if (component.isVisibleInHierarchy())
			{
				sb.append(Javascript.show(component.getMarkupId(), reflectionOpacity,
					reflectionHeight));
				sb.append("\n");
			}
		response.render(OnLoadHeaderItem.forScript(sb.toString()));
	}

	/**
	 * Sets the reflection height. This is a percentage of the original image. A height of 50 means
	 * that the reflection will be half the size of the original image, increasing the image size to
	 * 150%. Set the value to null to reset to the default value.
	 * 
	 * @param height
	 *            the height of the reflection as a percentage of the original image, valid values
	 *            range from 0 to 100.
	 */
	public void setReflectionHeight(final Integer height)
	{
		reflectionHeight = height;
	}

	/**
	 * Sets the opacity of the reflection.
	 * 
	 * @param opacity
	 *            the opacity. Valid values range from 0 to 100.
	 */
	public void setReflectionOpacity(final Integer opacity)
	{
		reflectionOpacity = opacity;
	}
}
