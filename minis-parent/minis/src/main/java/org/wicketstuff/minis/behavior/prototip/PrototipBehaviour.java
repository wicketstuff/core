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
package org.wicketstuff.minis.behavior.prototip;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.head.OnLoadHeaderItem;
import org.apache.wicket.request.resource.PackageResourceReference;

/**
 * Prototip behavior
 * 
 * The component you add this behavior to will be the component which the tooltip appears for
 * 
 * note this does not work if you add the behavior to a panel
 * 
 * 
 * @author Richard Wilkinson
 * 
 */
public class PrototipBehaviour extends Behavior
{
	private static final long serialVersionUID = 1L;

	protected Component source;
	protected String tooltip = null;
	protected Component tooltipComponent = null;
	protected PrototipSettings settings = null;
	protected boolean overrideHeaderContributor = false;
	protected String title = null;
	protected boolean onLoad = true;

	/**
	 * Made this static as it is very unlikely that you would want different versions of prototip.js
	 * across your site
	 */
	protected static JS_TYPE selectedJsType = JS_TYPE.MIN;

	/**
	 * Default constructor If you use this then you must set either a string, or a component
	 * manually
	 */
	public PrototipBehaviour()
	{
	}

	/**
	 * Provide a simple string as a tooltip
	 * 
	 * @param tooltip
	 */
	public PrototipBehaviour(String tooltip)
	{
		this.tooltip = tooltip;
	}

	/**
	 * Provide a component to show as the tool tip (eg a panel)
	 * 
	 * @param panel
	 */
	public PrototipBehaviour(Component panel)
	{
		tooltipComponent = panel;
		panel.setOutputMarkupId(true);
	}

	/**
	 * Add the required css and js files to the page Permission to distribute prototip files given
	 * by prototip creator Nick Stakenburg (http://www.nickstakenburg.com)
	 * 
	 * Also add the javascript to create the tooltip
	 */
	@Override
	public void renderHead(Component c, IHeaderResponse response)
	{
		if (onLoad)
		{
			response.render(OnLoadHeaderItem.forScript(toJavascript()));
		}
		else
		{
			response.render(OnDomReadyHeaderItem.forScript(toJavascript()));
		}
		if (!overrideHeaderContributor)
		{
			response.render(CssHeaderItem.forReference(new PackageResourceReference(
				PrototipBehaviour.class, "prototip.css"), "screen"));
			switch (selectedJsType)
			{
				case NORMAL :
					response.render(JavaScriptHeaderItem.forReference(new PackageResourceReference(
						PrototipBehaviour.class, "prototip.js")));
					break;
				case MIN :
					response.render(JavaScriptHeaderItem.forReference(new PackageResourceReference(
						PrototipBehaviour.class, "prototip-min.js")));
					break;
			}
		}
	}

	/**
	 * override bind so that the component you add this behavior to becomes the component the
	 * tooltip applies to
	 * 
	 */
	@Override
	public void bind(Component component)
	{
		super.bind(component);
		source = component;
		source.setOutputMarkupId(true);
	}

	/**
	 * Given an ajax request target, remove this tip from the page
	 * 
	 * @param target
	 */
	public void remove(AjaxRequestTarget target)
	{
		if (source != null)
		{
			StringBuilder removeJs = new StringBuilder();
			removeJs.append("Tips.remove($('").append(source.getMarkupId()).append("'));");
			target.appendJavaScript(removeJs.toString());
		}
	}

	/**
	 * Given an ajax request target, hide this tip on the page
	 * 
	 * @param target
	 */
	public void hide(AjaxRequestTarget target)
	{
		if (source != null)
		{
			StringBuilder hideJs = new StringBuilder();
			hideJs.append("Tips.hide($('").append(source.getMarkupId()).append("'));");
			target.appendJavaScript(hideJs.toString());
		}
	}

	/**
	 * Get string to add the prototip to the page
	 * 
	 * @return the String
	 */
	protected String toJavascript()
	{
		StringBuilder script = new StringBuilder();
		String optionString = null;
		if (settings != null)
		{
			optionString = settings.getOptionsString(title);
		}
		if (tooltip != null)
		{
			script.append("new Tip($('")
				.append(source.getMarkupId())
				.append("'),'")
				.append(tooltip)
				.append("'");
		}
		else if (tooltipComponent != null)
		{
			script.append("new Tip($('")
				.append(source.getMarkupId())
				.append("'),$('")
				.append(tooltipComponent.getMarkupId())
				.append("')");
		}
		if (optionString != null && !optionString.equals(""))
		{
			script.append(", ").append(optionString);
		}
		script.append(");");
		return script.toString();
	}

	/**
	 * @return the source
	 */
	public Component getSource()
	{
		return source;
	}

	/**
	 * @param source
	 *            the source to set
	 * @return this object
	 */
	public PrototipBehaviour setSource(Component source)
	{
		this.source = source;
		return this;
	}

	/**
	 * @return the tooltip
	 */
	public String getTooltip()
	{
		return tooltip;
	}

	/**
	 * @param tooltip
	 *            the tooltip to set
	 * @return this object
	 */
	public PrototipBehaviour setTooltip(String tooltip)
	{
		this.tooltip = tooltip;
		tooltipComponent = null;
		return this;
	}

	/**
	 * @return the tooltip component
	 */
	public Component getTooltipComponent()
	{
		return tooltipComponent;
	}

	/**
	 * @param panel
	 *            the panel to set
	 * @return this object
	 */
	public PrototipBehaviour setTooltipComponent(Component tooltipComponent)
	{
		this.tooltipComponent = tooltipComponent;
		this.tooltipComponent.setOutputMarkupId(true);
		tooltip = null;
		return this;
	}

	/**
	 * @return the settings
	 */
	public PrototipSettings getSettings()
	{
		return settings;
	}

	/**
	 * @param settings
	 *            the settings to set
	 * @return this object
	 */
	public PrototipBehaviour setSettings(PrototipSettings settings)
	{
		this.settings = settings;
		return this;
	}

	/**
	 * @return the overrideHeaderContributor
	 */
	public boolean isOverrideHeaderContributor()
	{
		return overrideHeaderContributor;
	}

	/**
	 * If you do not want this behavour to add the required javascript and css files to the header
	 * set this to true (default false)
	 * 
	 * @param overrideHeaderContributor
	 *            the overrideHeaderContributor to set
	 * @return this object
	 */
	public PrototipBehaviour setOverrideHeaderContributor(boolean overrideHeaderContributor)
	{
		this.overrideHeaderContributor = overrideHeaderContributor;
		return this;
	}

	/**
	 * @return the title
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 * @return this object
	 */
	public PrototipBehaviour setTitle(String title)
	{
		this.title = title;
		return this;
	}

	/**
	 * @return the selectedJsType
	 */
	public static JS_TYPE getSelectedJsType()
	{
		return selectedJsType;
	}

	/**
	 * There are 3 different js files which can be included: a normal uncompressed one a minified
	 * one a minified and gziped one
	 * 
	 * To override the default (the minified one) set this parameter
	 * 
	 * This is a static method and as such affects all PrototipBehaviour in the system, this is so
	 * that you do not have to set the type for every PrototipBehaviour that you use (which is
	 * tedious)
	 * 
	 * @param selectedJsType
	 *            the selectedJsType to set
	 */
	public static void setSelectedJsType(JS_TYPE selectedJsType)
	{
		PrototipBehaviour.selectedJsType = selectedJsType;
	}

	/**
	 * Is the javascript set to load 'onload' if false then it will be 'ondomready'
	 * 
	 * @return
	 */
	public boolean isOnLoad()
	{
		return onLoad;
	}

	/**
	 * Is the javascript set to load 'onload' if false then it will be 'ondomready'
	 * 
	 * @param onLoad
	 */
	public void setOnLoad(boolean onLoad)
	{
		this.onLoad = onLoad;
	}
}
