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
package com.googlecode.wicket.kendo.ui;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.AjaxRequestTarget.AbstractListener;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;
import org.apache.wicket.util.visit.Visits;

/**
 * INTERNAL USE<br/>
 * Provides an {@code IListener} for {@link KendoUIBehavior}{@code s} that destroys widgets about to be repainted.
 * 
 * @author Sebastien Briquet - sebfz1
 */
public class KendoDestroyListener extends AbstractListener
{
	/**
	 * Specifies that a widgets can be automatically destroyed
	 */
	public interface IDestroyable
	{
		/**
		 * Prepares the widget for safe removal from the DOM.<br/>
		 * Detaches all event handlers and removes jQuery.data attributes to avoid memory leaks.<br/>
		 * Calls destroy method of any child Kendo widgets.
		 * 
		 * @param target the {@link AjaxRequestTarget}
		 */
		void destroy(AjaxRequestTarget target);
	}

	// Events //

	@Override
	public void onBeforeRespond(Map<String, Component> map, AjaxRequestTarget target)
	{
		for (Entry<String, Component> entry : map.entrySet())
		{
			Visits.visitPostOrder(entry.getValue(), this.newBeforeRespondVisitor(target));
		}
	}

	// Factories //

	/**
	 * Gets a new {@link IVisitor} that will be used by {@link #onBeforeRespond(Map, AjaxRequestTarget)}
	 *
	 * @return the new {@code IVisitor}
	 */
	protected IVisitor<Component, Object> newBeforeRespondVisitor(final AjaxRequestTarget target)
	{
		return new IVisitor<Component, Object>() {

			@Override
			public void component(Component component, IVisit<Object> visit)
			{
				for (KendoUIBehavior behavior : component.getBehaviors(KendoUIBehavior.class))
				{
					behavior.destroy(target);
				}
			}
		};
	}
}
