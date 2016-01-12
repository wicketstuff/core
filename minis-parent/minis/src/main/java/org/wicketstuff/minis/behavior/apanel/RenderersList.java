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
package org.wicketstuff.minis.behavior.apanel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;

/**
 * Stores list of renderers.
 */
class RenderersList implements Serializable
{
	public static abstract class BaseRenderer<T extends Component> implements IComponentRenderer<T>
	{
		private static final long serialVersionUID = 1L;

		protected String getIdAttribute(final Component component)
		{
			return "wicket:id=\"" + component.getId() + "\"";
		}
	}
	public static abstract class BaseWebMarkupContainerRenderer<T extends WebMarkupContainer>
		extends BaseRenderer<T>
	{
		private static final long serialVersionUID = 1L;

		protected final ILayout layout;

		protected BaseWebMarkupContainerRenderer()
		{
			this.layout = new FlowLayout();
		}

		protected BaseWebMarkupContainerRenderer(final ILayout layout)
		{
			this.layout = layout;
		}

		protected CharSequence getBodyMarkup(final WebMarkupContainer container)
		{
			final List<Component> componentsToRender = new ArrayList<Component>();

			container.visitChildren(new IVisitor<Component, Void>()
			{

				public void component(final Component component, final IVisit<Void> visit)
				{
					componentsToRender.add(component);
					visit.dontGoDeeper();
				}
			});

			return layout.renderComponents(componentsToRender);
		}
	}

	public static final class ButtonRenderer extends BaseRenderer<Button>
	{
		private static final long serialVersionUID = 1L;

		public Class<Button> getComponentClass()
		{
			return Button.class;
		}

		public CharSequence getMarkup(final Button component)
		{
			return String.format("<input type=\"submit\" %s/>", getIdAttribute(component));
		}
	}

	public static final class DefaultRenderer extends BaseRenderer<Component>
	{
		private static final long serialVersionUID = 1L;

		public Class<Component> getComponentClass()
		{
			return Component.class;
		}

		public CharSequence getMarkup(final Component component)
		{
			return String.format("<span %s></span>", getIdAttribute(component));
		}
	}

	public static final class DefaultWebMarkupContainerRenderer extends
		BaseWebMarkupContainerRenderer<WebMarkupContainer>
	{
		private static final long serialVersionUID = 1L;

		public Class<WebMarkupContainer> getComponentClass()
		{
			return WebMarkupContainer.class;
		}

		public CharSequence getMarkup(final WebMarkupContainer component)
		{
			return String.format("<span %s>%s</span>", getIdAttribute(component),
				getBodyMarkup(component));
		}
	}

	// mocleiri: does not seem compatible with 1.5
// public static final class DefaultWebMarkupContainerWithMarkupRenderer extends
// BaseWebMarkupContainerRenderer<WebMarkupContainerWithAssociatedMarkup>
// {
// private static final long serialVersionUID = 1L;
//
// public Class<WebMarkupContainerWithAssociatedMarkup> getComponentClass()
// {
// return WebMarkupContainerWithAssociatedMarkup.class;
// }
//
// public CharSequence getMarkup(final WebMarkupContainerWithAssociatedMarkup component)
// {
// return String.format("<span %s></span>", getIdAttribute(component));
// }
// }

	@SuppressWarnings("rawtypes")
	public static final class FormRenderer extends BaseWebMarkupContainerRenderer<Form>
	{
		private static final long serialVersionUID = 1L;

		public Class<Form> getComponentClass()
		{
			return Form.class;
		}

		public CharSequence getMarkup(final Form component)
		{
			return String.format("<form %s>%s</form>", getIdAttribute(component),
				getBodyMarkup(component));
		}
	}

	// -------------- renderes --------------

	public static final class LabelRenderer extends BaseRenderer<Label>
	{
		private static final long serialVersionUID = 1L;

		public Class<Label> getComponentClass()
		{
			return Label.class;
		}

		public CharSequence getMarkup(final Label component)
		{
			return String.format("<span %s></span>", getIdAttribute(component));
		}
	}

	@SuppressWarnings("rawtypes")
	public static final class LinkRenderer extends BaseWebMarkupContainerRenderer<Link>
	{
		private static final long serialVersionUID = 1L;

		public Class<Link> getComponentClass()
		{
			return Link.class;
		}

		public CharSequence getMarkup(final Link component)
		{
			return String.format("<a %s>%s</a>", getIdAttribute(component),
				getBodyMarkup(component));
		}
	}

	@SuppressWarnings("rawtypes")
	public static final class ListViewRenderer extends BaseWebMarkupContainerRenderer<ListView>
	{
		private static final long serialVersionUID = 1L;

		public Class<ListView> getComponentClass()
		{
			return ListView.class;
		}

		private CharSequence getListViewBodyMarkup(final ListView listView)
		{
			if (listView.getList().isEmpty())
				return "";
			final ListItem listItem = (ListItem)listView.get("0");
			return getBodyMarkup(listItem);
		}

		public CharSequence getMarkup(final ListView component)
		{
			return String.format("<span %s>%s</span>", getIdAttribute(component),
				getListViewBodyMarkup(component));
		}
	}

	public static final class RepeatingViewRenderer extends
		BaseWebMarkupContainerRenderer<RepeatingView>
	{
		private static final long serialVersionUID = 1L;

		public Class<RepeatingView> getComponentClass()
		{
			return RepeatingView.class;
		}

		public CharSequence getMarkup(final RepeatingView component)
		{
			final String[] markup = new String[1];
			component.visitChildren(new IVisitor<Component, Void>()
			{
				public void component(final Component component, final IVisit<Void> visit)
				{
					final CharSequence c = layout.renderComponents(Collections.singletonList(component));
					markup[0] = c.toString().replace(getIdAttribute(component), "%s");
					visit.stop();
				}
			});

			if (markup[0] == null)
				markup[0] = "<span %s></span>";

			return String.format(markup[0], getIdAttribute(component));
		}
	}

	private static final long serialVersionUID = 1L;

	private static final List<IComponentRenderer<?>> DEFAULT_RENDERERS = new ArrayList<IComponentRenderer<?>>();

	public static List<IComponentRenderer<?>> getDefaultRenderers()
	{
		return Collections.unmodifiableList(DEFAULT_RENDERERS);
	}

	private final List<IComponentRenderer<?>> renderers;

	static
	{
		DEFAULT_RENDERERS.add(new LabelRenderer());
		DEFAULT_RENDERERS.add(new LinkRenderer());
		DEFAULT_RENDERERS.add(new ListViewRenderer());
		DEFAULT_RENDERERS.add(new RepeatingViewRenderer());
		DEFAULT_RENDERERS.add(new FormRenderer());
		DEFAULT_RENDERERS.add(new ButtonRenderer());
// mocleiri: TODO make 1.5 compatible.
// DEFAULT_RENDERERS.add(new DefaultWebMarkupContainerWithMarkupRenderer());
		DEFAULT_RENDERERS.add(new DefaultWebMarkupContainerRenderer());
		DEFAULT_RENDERERS.add(new DefaultRenderer());
	}

	public RenderersList(final List<IComponentRenderer<?>> renderers)
	{
		this.renderers = renderers;
	}

	/**
	 * Note: unchecked cast is suppressed because this method is intended to return renderer that
	 * should be used with {@link org.apache.wicket.Component} collections but
	 * {@link org.wicketstuff.minis.behavior.apanel.IComponentRenderer#getMarkup(org.apache.wicket.Component)}
	 * method can only handle certain subtype of {@link org.apache.wicket.Component}.
	 * 
	 * @param aClass
	 *            class of the component
	 * @return {@link org.wicketstuff.minis.behavior.apanel.IComponentRenderer} for specified
	 *         component class
	 */
	IComponentRenderer<Component> findRendererForClass(final Class<? extends Component> aClass)
	{
		for (final IComponentRenderer<?> componentRenderer : renderers)
			if (componentRenderer.getComponentClass().isAssignableFrom(aClass))
			{
				@SuppressWarnings("unchecked")
				IComponentRenderer<Component> result = (IComponentRenderer<Component>)componentRenderer;
				return result;
			}
		throw new WicketRuntimeException("Can't find renderer for class " + aClass);
	}
}
