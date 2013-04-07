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

import java.util.Map;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.CallbackParameter;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.JQueryEvent;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.ajax.IJQueryAjaxAware;
import com.googlecode.wicket.jquery.core.ajax.JQueryAjaxBehavior;
import com.googlecode.wicket.jquery.core.utils.RequestCycleUtils;

/**
 * Provides a jQuery menu behavior.
 *
 * @author Sebastien Briquet - sebfz1
 * @since 1.4.2
 * @since 1.6.2
 */
public abstract class MenuBehavior extends JQueryBehavior implements IJQueryAjaxAware, IMenuListener
{
	private static final long serialVersionUID = 1L;
	private static final String METHOD = "menu";

	private JQueryAjaxBehavior onSelectBehavior;

	/**
	 * Constructor
	 *
	 * @param selector the html selector (ie: "#myId")
	 */
	public MenuBehavior(String selector)
	{
		super(selector, METHOD);
	}

	/**
	 * Constructor
	 *
	 * @param selector the html selector (ie: "#myId")
	 * @param options the {@link Options}
	 */
	public MenuBehavior(String selector, Options options)
	{
		super(selector, METHOD, options);
	}


	// Properties //
	/**
	 * Gets the reference map of hash/menu-item.<br/>
	 *
	 * @return the non-null {@link Map}
	 */
	protected abstract Map<String, IMenuItem> getMenuItemMap();


	// Methods //
	@Override
	public void bind(Component component)
	{
		super.bind(component);

		component.add(this.onSelectBehavior = this.newOnSelectBehavior());
	}


	// Events //
	@Override
	public void onConfigure(Component component)
	{
		super.onConfigure(component);

		this.setOption("select", this.onSelectBehavior.getCallbackFunction());
	}

	@Override
	public void onAjax(AjaxRequestTarget target, JQueryEvent event)
	{
		if (event instanceof SelectEvent)
		{
			IMenuItem item = this.getMenuItemMap().get(((SelectEvent) event).getHash());

			if (item != null)
			{
				item.onClick(target);
				this.onClick(target, item);
			}
		}
	}

	// Factories //
	/**
	 * Gets a new {@link JQueryAjaxBehavior} that acts as the 'select' javascript callback
	 * @return the {@link JQueryAjaxBehavior}
	 */
	protected JQueryAjaxBehavior newOnSelectBehavior()
	{
		return new JQueryAjaxBehavior(this) {

			private static final long serialVersionUID = 1L;

			@Override
			protected CallbackParameter[] getCallbackParameters()
			{
				return new CallbackParameter[] {
						CallbackParameter.context("event"),
						CallbackParameter.context("ui"),
						CallbackParameter.resolved("id", "ui.item.context.id")
						};
			}

			@Override
			protected JQueryEvent newEvent()
			{
				return new SelectEvent();
			}
		};
	}


	// Event objects //
	/**
	 * Provides an event object that will be broadcasted by the {@link JQueryAjaxBehavior} 'select' callback
	 */
	protected static class SelectEvent extends JQueryEvent
	{
		private final String hash;

		/**
		 * Constructor
		 */
		public SelectEvent()
		{
			super();

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
}
