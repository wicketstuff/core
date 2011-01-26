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
package org.wicketstuff.dojo11;

import org.apache.wicket.ajax.AjaxEventBehavior;

/**
 * An Dojo behavior that is attached to a certain client-side (usually
 * javascript) event, such as onClick, onChange, onKeyDown, etc.
 * <p>
 * Example:
 * 
 * <pre>
 *       DropDownChoice choice=new DropDownChoice(...);
 *       choice.add(new DojoEventBehavior(&quot;onchange&quot;) {
 *           protected void onEvent(AjaxRequestTarget target) {
 *               System.out.println(&quot;ajax here!&quot;);
 *           }
 *       }
 * </pre>
 * 
 * This behavior will be linked to the onChange javascript event of the select
 * box this DropDownChoice represents, and so anytime a new option is selected
 * we will get the System.out message
 * 
 * @author vdemay
 * 
 * TODO : add Drag event, onShow for tab handler and others
 */
public abstract class DojoEventBehavior extends AjaxEventBehavior
{

	/**
	 * @param event
	 */
	public DojoEventBehavior(String event)
	{
		super(event);
	}
}
