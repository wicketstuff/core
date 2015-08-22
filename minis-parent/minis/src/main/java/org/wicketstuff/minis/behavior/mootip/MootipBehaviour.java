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
package org.wicketstuff.minis.behavior.mootip;

import java.util.HashMap;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnLoadHeaderItem;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.util.template.PackageTextTemplate;
import org.apache.wicket.util.template.TextTemplate;

/**
 * Mootip behavior, implements integration with this http://www.uhleeka.com/dev/mootips/
 * 
 * The component you add this behavior to will be the component which the tooltip appears for
 * 
 * @author nino.martinez @ jayway.dk
 */
public class MootipBehaviour extends Behavior
{
	private static final long serialVersionUID = 1L;

	private final TextTemplate mooTipTemplate = new PackageTextTemplate(MootipBehaviour.class,
		"MootipBehaviour.js");

	private MootipSettings mootipSettings = new MootipSettings();

	/** The target component. */
	private Component component;

	private boolean addTitle = false;

	private String content = "";
	private String title = "";

	private boolean ajax = false;
	private MootipPanel panel = null;

	private boolean contributeCSS = true;

	/**
	 * Ajax ToolTip, retrieves the panel with an ajax call. Requests the panel on each tooltip
	 * display
	 * 
	 * @param panel
	 */
	public MootipBehaviour(final MootipPanel panel)
	{
		ajax = true;
		this.panel = panel;
	}

	/**
	 * Ajax ToolTip, retrieves the panel with an ajax call. Requests the panel on each tooltip
	 * display
	 * 
	 * @param panel
	 */
	public MootipBehaviour(final MootipPanel panel, final boolean contribute)
	{
		this(panel);
		contributeCSS = contribute;
	}


	/**
	 * simple tooltip, using title as tool tip it uses this syntax for splitting
	 * <code> title='this will be title:this will be content'</code>
	 * 
	 * @param title
	 * @param content
	 */
	public MootipBehaviour(final String title, final String content)
	{
		addTitle = true;
		this.content = content;
		this.title = title;
	}

	/**
	 * simple tooltip, using title as tool tip it uses this syntax for splitting
	 * <code> title='this will be title:this will be content'</code>
	 * 
	 * @param title
	 * @param content
	 */
	public MootipBehaviour(final String title, final String content, final boolean contribute)
	{
		this(title, content);
		contributeCSS = contribute;
	}


	@Override
	public void bind(final Component component)
	{
		super.bind(component);
		this.component = component;
		this.component.setOutputMarkupId(true);
		if (addTitle && !isAjax())
			component.add(AttributeModifier.replace("title", title + "::" + content));
		if (isAjax())
		{
			this.component.add(new MootipAjaxListener(panel));
			component.add(AttributeModifier.replace("title", "CALLBACK:mootipAjax" +
				getEscapedComponentMarkupId() + "()"));
		}

		component.add(AttributeModifier.replace("class", "toolTipImg" +
			getEscapedComponentMarkupId()));

		component.setOutputMarkupId(true);
	}

	private String generateJS(final TextTemplate textTemplate)
	{
		final HashMap<String, Object> variables = new HashMap<String, Object>();
		final String widgetId = getEscapedComponentMarkupId();

		variables.put("id", widgetId);
		variables.put("widgetId", ".toolTipImg" + getEscapedComponentMarkupId());
		variables.put("maxTitleChars", mootipSettings.getMaxTitleChars());
		variables.put("evalAlways", mootipSettings.isEvalAlways());
		variables.put("showOnClick", mootipSettings.isShowOnClick());
		variables.put("showOnMouseEnter", mootipSettings.isShowOnMouseEnter());
		variables.put("showDelay", mootipSettings.getShowDelay());
		variables.put("hideDelay", mootipSettings.getHideDelay());
		variables.put("className", mootipSettings.getClassName());
		variables.put("offsetX", Math.round(mootipSettings.getOffsets().getX()));
		variables.put("offsetY", Math.round(mootipSettings.getOffsets().getY()));
		variables.put("fixed", mootipSettings.isFixed());

		textTemplate.interpolate(variables);
		return textTemplate.asString();
	}

	/**
	 * Gets the escaped DOM id that the input will get attached to. All non word characters (\W)
	 * will be removed from the string.
	 * 
	 * @return The DOM id of the input - same as the component's markup id}
	 */
	protected final String getEscapedComponentMarkupId()
	{
		return component.getMarkupId().replaceAll("\\W", "");
	}

	public boolean isAjax()
	{
		return ajax;
	}

	/**
	 * Add the required css and js files to the page
	 * 
	 * Also add the javascript to create the tooltip
	 */
	@Override
	public void renderHead(Component c, final IHeaderResponse response)
	{
		response.render(JavaScriptHeaderItem.forReference(new PackageResourceReference(
			MootipBehaviour.class, "mootools.v1.11.js")));
		response.render(JavaScriptHeaderItem.forReference(new PackageResourceReference(
			MootipBehaviour.class, "mootips.v1.11.js")));
		if (contributeCSS)
			response.render(CssHeaderItem.forReference(new PackageResourceReference(
				MootipBehaviour.class, "tip.css")));

		response.render(OnLoadHeaderItem.forScript(generateJS(mooTipTemplate)));
	}

	public void setMootipSettings(final MootipSettings mootipSettings)
	{
		this.mootipSettings = mootipSettings;
	}
}
