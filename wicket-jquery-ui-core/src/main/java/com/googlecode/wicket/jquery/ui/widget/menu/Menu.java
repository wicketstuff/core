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
import org.apache.wicket.markup.html.panel.Fragment;

import com.googlecode.wicket.jquery.ui.JQueryBehavior;
import com.googlecode.wicket.jquery.ui.JQueryEvent;
import com.googlecode.wicket.jquery.ui.JQueryPanel;
import com.googlecode.wicket.jquery.ui.Options;
import com.googlecode.wicket.jquery.ui.ajax.JQueryAjaxBehavior;
import com.googlecode.wicket.jquery.ui.utils.RequestCycleUtils;

/**
 * TODO javadoc
 *
 * @author Sebastien Briquet - sebfz1
 */
public class Menu extends JQueryPanel
{
	private static final long serialVersionUID = 1L;

	private final List<MenuItem> items;
	private WebMarkupContainer root;

	/**
	 * Keep a reference to the {@link MenuItem} hash
	 */
	private Map<String, MenuItem> map = new HashMap<String, MenuItem>();

	private JQueryAjaxBehavior onSelectBehavior;

	/**
	 * Constructor
	 * @param id the markup id
	 */
	public Menu(String id)
	{
		this(id, new ArrayList<MenuItem>());
	}

	/**
	 * Constructor
	 * @param id the markup id
	 */
	public Menu(String id, List<MenuItem> items)
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
		this(id, new ArrayList<MenuItem>() , options);
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param options {@link Options}
	 */
	public Menu(String id, List<MenuItem> items, Options options)
	{
		super(id, options);

		this.items = items;
		this.init();
	}

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

			MenuItem item = this.map.get(payload.getHash());

			if (item != null)
			{
				item.onClick(target);
				this.onClick(target, item);
			}
		}
	}

	protected void onClick(AjaxRequestTarget target, MenuItem item)
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
	 * Gets a new {@link JQueryAjaxBehavior} that acts as the 'activate' javascript callback
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
	 * Provides an event object that will be broadcasted by the {@link JQueryAjaxBehavior} 'activate' callback
	 */
	private class SelectEvent extends JQueryEvent
	{
		private final String hash;

		/**
		 * Constructor
		 * @param target the {@link AjaxRequestTarget}
		 * @param step the {@link Step} (Start or Stop)
		 */
		public SelectEvent(AjaxRequestTarget target)
		{
			super(target);

			this.hash = RequestCycleUtils.getQueryParameterValue("id").toString();
		}

		/**
		 * Gets the tab's index
		 * @return the index
		 */
		public String getHash()
		{
			return this.hash;
		}
	}

	class MenuFragment extends Fragment
	{
		private static final long serialVersionUID = 1L;

		public MenuFragment(String id, List<MenuItem> items)
		{
			super(id, "menu-fragment", Menu.this);

			this.add(new ListFragment("list", items));
			this.setVisible(items.size() > 0);
		}
	}

	class ListFragment extends Fragment
	{
		private static final long serialVersionUID = 1L;

		public ListFragment(String id, List<MenuItem> items)
		{
			super(id, "list-fragment", Menu.this);

			this.add(new ListView<MenuItem>("items", items) {

				private static final long serialVersionUID = 1L;

				@Override
				protected void populateItem(ListItem<MenuItem> item)
				{
					MenuItem mi = item.getModelObject();

					map.put(mi.getId(), mi);

					item.add(AttributeModifier.replace("id", mi.getId()));
					item.add(new ItemFragment("item", item.getModelObject()));
					item.add(new MenuFragment("menu", item.getModelObject().getItems()));
				}
			});
		}
	}

	class ItemFragment extends Fragment
	{
		private static final long serialVersionUID = 1L;

		public ItemFragment(String id, MenuItem item)
		{
			super(id, "item-fragment", Menu.this);

			this.add(new Label("title", item.getTitle()));
		}
	}
}
