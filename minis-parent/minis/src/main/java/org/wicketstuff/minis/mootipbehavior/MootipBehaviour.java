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
package org.wicketstuff.minis.mootipbehavior;

import java.util.HashMap;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.resources.CompressedResourceReference;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.template.PackagedTextTemplate;
import org.apache.wicket.util.template.TextTemplate;

/**
 * Mootip behavior, implements integration with this
 * http://www.uhleeka.com/dev/mootips/
 * 
 * The component you add this behavior to will be the component which the
 * tooltip appears for
 * 
 * 
 * @author nino.martinez @ jayway.dk
 * 
 */
public class MootipBehaviour extends AbstractBehavior
{
	private static final long serialVersionUID = 1L;

	private MootipAjaxListener mootipAjaxListener = null;

	private final TextTemplate mooTipTemplate = new PackagedTextTemplate(MootipBehaviour.class,
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

	public boolean isAjax()
	{
		return ajax;
	}

	/**
	 * simple tooltip, using title as tool tip it uses this syntax for splitting
	 * <code> title='this will be title:this will be content'</code>
	 * 
	 * @param title
	 * @param content
	 */
	public MootipBehaviour(String title, String content)
	{
		addTitle = true;
		this.content = content;
		this.title = title;
	}


	public void setMootipSettings(MootipSettings mootipSettings)
	{
		this.mootipSettings = mootipSettings;
	}

	/**
	 * simple tooltip, using title as tool tip it uses this syntax for splitting
	 * <code> title='this will be title:this will be content'</code>
	 * 
	 * @param title
	 * @param content
	 */
	public MootipBehaviour(String title, String content, boolean contribute)
	{
		this(title, content);
		this.contributeCSS = contribute;
	}


	/**
	 * Ajax ToolTip, retrieves the panel with an ajax call. Requests the panel
	 * on each tooltip display
	 * 
	 * @param panel
	 */
	public MootipBehaviour(MootipPanel panel)
	{
		ajax = true;
		this.panel = panel;
	}


	/**
	 * Ajax ToolTip, retrieves the panel with an ajax call. Requests the panel
	 * on each tooltip display
	 * 
	 * @param panel
	 */
	public MootipBehaviour(MootipPanel panel, boolean contribute)
	{
		this(panel);
		this.contributeCSS = contribute;
	}


	@Override
	public void bind(Component component)
	{
		super.bind(component);
		this.component = component;
		this.component.setOutputMarkupId(true);
		if (addTitle && !isAjax())
		{
			component.add(new AttributeModifier("title", true, new Model<String>(title + "::"
					+ content)));
		}
		if (isAjax())
		{
			mootipAjaxListener = new MootipAjaxListener(panel);
			this.component.add(new MootipAjaxListener(panel));
			component.add(new AttributeModifier("title", true, new Model<String>(
					"CALLBACK:mootipAjax" + getEscapedComponentMarkupId() + "()")));
		}


		component.add(new AttributeModifier("class", true, new Model<String>("toolTipImg"
				+ getEscapedComponentMarkupId())));


		component.setOutputMarkupId(true);

	}


	private String generateJS(TextTemplate textTemplate)
	{
		HashMap<String, Object> variables = new HashMap<String, Object>();
		String widgetId = getEscapedComponentMarkupId();

		variables.put("id", widgetId);
		variables.put("widgetId", ".toolTipImg" + getEscapedComponentMarkupId());
		variables.put("maxTitleChars", mootipSettings.getMaxTitleChars());
		variables.put("evalAlways", mootipSettings.isEvalAlways());
		variables.put("showOnClick", mootipSettings.isShowOnClick());
		variables.put("showOnMouseEnter", mootipSettings.isShowOnMouseEnter());
		variables.put("showDelay", mootipSettings.getShowDelay());
		variables.put("hideDelay", mootipSettings.getHideDelay());
		variables.put("className", mootipSettings.getClassName());
		variables.put("offsetX", Math.round( mootipSettings.getOffsets().getX()));
		variables.put("offsetY",  Math.round(mootipSettings.getOffsets().getY()));
		variables.put("fixed", mootipSettings.isFixed());


		textTemplate.interpolate(variables);
		return textTemplate.asString();


	}

	/**
	 * Gets the escaped DOM id that the input will get attached to. All non word
	 * characters (\W) will be removed from the string.
	 * 
	 * @return The DOM id of the input - same as the component's markup id}
	 */
	protected final String getEscapedComponentMarkupId()
	{
		return component.getMarkupId().replaceAll("\\W", "");

	}


	/**
	 * Add the required css and js files to the page
	 * 
	 * Also add the javascript to create the tooltip
	 * 
	 * @see org.apache.wicket.markup.html.IHeaderContributor#renderHead(org.apache.wicket.markup.html.IHeaderResponse)
	 */
	@Override
	public void renderHead(IHeaderResponse response)
	{

		response.renderJavascriptReference(new CompressedResourceReference(MootipBehaviour.class,
				"mootools.v1.11.js"));
		response.renderJavascriptReference(new CompressedResourceReference(MootipBehaviour.class,
				"mootips.v1.11.js"));
		if (contributeCSS)
		{
			response.renderCSSReference((new CompressedResourceReference(MootipBehaviour.class,
					"tip.css")));
		}

		response.renderOnLoadJavascript(generateJS(mooTipTemplate));
	}


}
