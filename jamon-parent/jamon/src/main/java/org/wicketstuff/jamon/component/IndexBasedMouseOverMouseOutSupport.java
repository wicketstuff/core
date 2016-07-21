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
package org.wicketstuff.jamon.component;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.model.Model;

/**
 * Util class to add mouseover, mouseout behavior. Typically this is used in listviews and tables.
 * The css classes "even" and "odd" should be present.
 * 
 * @author lars
 *
 */
class IndexBasedMouseOverMouseOutSupport
{

	private IndexBasedMouseOverMouseOutSupport()
	{
	}

	/**
	 * Add mouseover and mouseout behavior.
	 * 
	 * @param <T>
	 *            The type of Component.
	 * @param component
	 *            The component to add the behavior to.
	 * @param index
	 *            The index of the Component in a repeatable container (such as table, listview).
	 *            The index is used to determine the original className for the mouseout event. If
	 *            index is an odd number then "odd" is used as css class, otherwise "even".
	 * @return The component, handy for chaining method calls.
	 */
	public static <T extends Component> T add(T component, int index)
	{
		String originalClassName = (index % 2 == 0) ? "even" : "odd";
		component.add(AttributeModifier.append("onmouseover",
			Model.<String> of("this.className='selected'")));
		component.add(AttributeModifier.append("onmouseout",
			Model.<String> of(String.format("this.className='%s'", originalClassName))));
		return component;
	}
}
