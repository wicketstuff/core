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
package com.googlecode.wicket.kendo.ui.utils;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;

import com.googlecode.wicket.jquery.core.IJQueryWidget.JQueryWidget;
import com.googlecode.wicket.kendo.ui.effect.KendoEffect;

/**
 * Utility class for Kendo UI effects
 *
 * @see <a href="http://docs.telerik.com/kendo-ui/api/javascript/effects/common">http://docs.telerik.com/kendo-ui/api/javascript/effects/common</a>
 * @author Sebastien Briquet - sebfz1
 */
public class KendoEffectUtils
{
	/** default effect */
	static final KendoEffect effect = KendoEffect.SLIDEIN_RIGHT;

	/**
	 * Utility class
	 */
	private KendoEffectUtils()
	{
	}

	/**
	 * Reloads a {@link Component} using a default {@link KendoEffect}: {@value #effect}
	 *
	 * @param target the {@link AjaxRequestTarget}
	 * @param component the {@link Component}
	 */
	public static void reload(AjaxRequestTarget target, Component component)
	{
		KendoEffectUtils.reload(target, component, KendoEffectUtils.effect);
	}

	/**
	 * Reloads a {@link Component} using a specified {@link KendoEffect}
	 *
	 * @param target the {@link AjaxRequestTarget}
	 * @param component the {@link Component}
	 */
	public static void reload(AjaxRequestTarget target, Component component, KendoEffect effect)
	{
		String selector = JQueryWidget.getSelector(component);

		target.add(component);
		target.appendJavaScript(String.format("kendo.fx(jQuery('%s')).%s.play();", selector, effect));
	}
}
