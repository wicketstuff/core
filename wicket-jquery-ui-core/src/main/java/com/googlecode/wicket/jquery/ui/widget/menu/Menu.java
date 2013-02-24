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
package com.googlecode.wicket.jquery.ui.widget.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.googlecode.wicket.jquery.ui.JQueryBehavior;
import com.googlecode.wicket.jquery.ui.JQueryEvent;
import com.googlecode.wicket.jquery.ui.JQueryPanel;
import com.googlecode.wicket.jquery.ui.Options;
import com.googlecode.wicket.jquery.ui.ajax.JQueryAjaxBehavior;
import com.googlecode.wicket.jquery.ui.utils.RequestCycleUtils;

/**
 * Provides the jQuery menu based on a {@link JQueryPanel}
 *
 * @author Sebastien Briquet - sebfz1
 */
public class Menu extends JQueryPanel
{
	private static final long serialVersionUID = 1L;

	private final List<IMenuItem> items; //root level items
	private WebMarkupContainer root;

	/**
	 * Keep a reference to the {@link MenuItem}<code>s</code> hash
	 */
	private Map<String, IMenuItem> map = new HashMap<String, IMenuItem>();

	private JQueryAjaxBehavior onSelectBehavior;

	/**
	 * Constructor
	 * @param id the markup id
	 */
	public Menu(String id)
	{
		this(id, new ArrayList<IMenuItem>());
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param items the menu-items
	 */
	public Menu(String id, List<IMenuItem> items)
	{
		super(id);

		this.items = items;
		this.init();
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param options {@link Options}
	 */
	public Menu(String id, Options options)
	{
		this(id, new ArrayList<IMenuItem>() , options);
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param items the menu-items
	 * @param options {@link Options}
	 */
	public Menu(String id, List<IMenuItem> items, Options options)
	{
		super(id, options);

		this.items = items;
		this.init();
	}

	/**
	 * Initialization
	 */
	private void init()
	{
		this.root = new WebMarkupContainer("root");
		this.root.add(new ListFragment("list", this.items));

		this.add(this.root);
	}

	// Events //
	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.add(this.onSelectBehavior = this.newOnSelectBehavior());
		this.add(JQueryWidget.newWidgetBehavior(this, JQueryWidget.getSelector(this.root)));
	}

	/**
	 * Called immediately after the onConfigure method in a behavior. Since this is before the rendering
	 * cycle has begun, the behavior can modify the configuration of the component (i.e. {@link Options})
	 *
	 * @param behavior the {@link JQueryBehavior}
	 */
	protected void onConfigure(JQueryBehavior behavior)
	{
	}

	@Override
	public void onEvent(IEvent<?> event)
	{
		super.onEvent(event);

		if (event.getPayload() instanceof SelectEvent)
		{
			SelectEvent payload = (SelectEvent) event.getPayload();
			AjaxRequestTarget target = payload.getTarget();

			IMenuItem item = this.map.get(payload.getHash());

			if (item != null)
			{
				item.onClick(target);
				this.onClick(target, item);
			}
		}
	}

	/**
	 * Triggered when a menu-item is clicked
	 * @param target the {@link AjaxRequestTarget}
	 * @param item the {@link IMenuItem}
	 */
	protected void onClick(AjaxRequestTarget target, IMenuItem item)
	{
	}

	// IJQueryWidget //
	@Override
	public JQueryBehavior newWidgetBehavior(String selector)
	{
		return new MenuBehavior(selector, this.options) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onConfigure(Component component)
			{
				Menu.this.onConfigure(this);

				this.setOption("select", onSelectBehavior.getCallbackFunction());
			}
		};
	}


	// Factories //
	/**
	 * Gets a new {@link JQueryAjaxBehavior} that acts as the 'select' javascript callback
	 * @return the {@link JQueryAjaxBehavior}
	 */
	private JQueryAjaxBehavior newOnSelectBehavior()
	{
		return new JQueryAjaxBehavior(this) {

			private static final long serialVersionUID = 1L;

			@Override
			public String getCallbackFunction()
			{
				return "function(event, ui) { " + this.getCallbackScript() + " }";
			}

			@Override
			public CharSequence getCallbackScript()
			{
				return this.generateCallbackScript("wicketAjaxGet('" + this.getCallbackUrl() + "&id=' + ui.item.context.id");
			}

			@Override
			protected JQueryEvent newEvent(AjaxRequestTarget target)
			{
				return new SelectEvent(target);
			}
		};
	}


	// Event objects //
	/**
	 * Provides an event object that will be broadcasted by the {@link JQueryAjaxBehavior} 'select' callback
	 */
	private class SelectEvent extends JQueryEvent
	{
		private final String hash;

		/**
		 * Constructor
		 * @param target the {@link AjaxRequestTarget}
		 */
		public SelectEvent(AjaxRequestTarget target)
		{
			super(target);

			this.hash = RequestCycleUtils.getQueryParameterValue("id").toString();
		}

		/**
		 * Gets the menu's id-hash
		 * @return the id-hash
		 */
		public String getHash()
		{
			return this.hash;
		}
	}


	// Fragments //
	/**
	 * Represents a menu {@link Fragment}. Could be either the root or a sub-menu
	 */
	private class MenuFragment extends Fragment
	{
		private static final long serialVersionUID = 1L;

		public MenuFragment(String id, List<IMenuItem> items)
		{
			super(id, "menu-fragment", Menu.this);

			this.add(new ListFragment("list", items));
			this.setVisible(items.size() > 0);
		}
	}

	/**
	 * Represents a {@link Fragment} of a list of menu-items
	 */
	private class ListFragment extends Fragment
	{
		private static final long serialVersionUID = 1L;

		public ListFragment(String id, List<IMenuItem> items)
		{
			super(id, "list-fragment", Menu.this);

			this.add(new ListView<IMenuItem>("items", items) {

				private static final long serialVersionUID = 1L;

				@Override
				protected void populateItem(ListItem<IMenuItem> item)
				{
					IMenuItem menuItem = item.getModelObject();
					Menu.this.map.put(menuItem.getId(), menuItem);

					item.add(AttributeModifier.replace("id", menuItem.getId()));
					item.add(new ItemFragment("item", menuItem));
					item.add(new MenuFragment("menu", menuItem.getItems()));

					if (!menuItem.isEnabled())
					{
						item.add(AttributeModifier.append("class", Model.of("ui-state-disabled")));
					}

				}
			});
		}
	}

	/**
	 * Represents a {@link Fragment} of a menu-item
	 */
	private class ItemFragment extends Fragment
	{
		private static final long serialVersionUID = 1L;

		public ItemFragment(String id, IMenuItem item)
		{
			super(id, "item-fragment", Menu.this);

			this.add(new EmptyPanel("icon").add(AttributeModifier.append("class", new PropertyModel<String>(item, "iconClass"))));
			this.add(new Label("title", item.getTitle()));
		}
	}
}
