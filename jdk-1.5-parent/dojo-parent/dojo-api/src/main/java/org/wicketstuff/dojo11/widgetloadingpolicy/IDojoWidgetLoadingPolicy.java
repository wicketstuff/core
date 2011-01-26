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
package org.wicketstuff.dojo11.widgetloadingpolicy;

import java.io.Serializable;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.wicketstuff.dojo11.AbstractDojoWidgetBehavior;
import org.wicketstuff.dojo11.IDojoWidget;

/**
 * Dojo Widget loading Policy defines when the widget need to be parse by Dojo
 * You can set this kind of policy on constructor of {@link AbstractDojoWidgetBehavior}
 * <p>
 * Most of time, this policy can be defined on a widget ( {@link IDojoWidget} ) constructor
 * </p>
 * @author Vincent Demay
 */
public interface IDojoWidgetLoadingPolicy extends Serializable
{
	/**
	 * What to render during the page renderHead
	 * @param response page response
	 * @param component the component concern by the {@link AbstractDojoWidgetBehavior}
	 */
	public void renderHead(IHeaderResponse response, Component component);
	
	/**
	 * Stuff to do when the component is rerender
	 * @param ajaxTarget 
	 *
	 */
	public void onComponentReRendered(AjaxRequestTarget ajaxTarget);
}
