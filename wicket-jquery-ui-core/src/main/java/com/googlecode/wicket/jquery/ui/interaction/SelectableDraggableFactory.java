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
package com.googlecode.wicket.jquery.ui.interaction;

import com.googlecode.wicket.jquery.ui.JQueryBehavior;


/**
 * Provides a default implementation of {@link AbstractDraggableFactory} related to a {@link Selectable} widget<br/>
 * <br/>
 * <br/>
 * Inspired from:<br/>
 * http://stackoverflow.com/questions/793559/grouping-draggable-objects-with-jquery-ui-draggable<br/>
 *
 * @author Sebastien Briquet - sebfz1
 */
public class SelectableDraggableFactory extends AbstractDraggableFactory
{
	private static final long serialVersionUID = 1L;

	@Override
	protected String getHelper(String selector)
	{
		StringBuilder helper = new StringBuilder("function() { ");
		helper.append("var container = jQuery('<div/>').attr('id', 'draggingContainer');");
		helper.append("jQuery('").append(selector).append("').find('.ui-selected').each(");
		helper.append("  function() { ");
		helper.append("    container.append(jQuery(this).clone()); }");
		helper.append("  );");
		helper.append("  return container; ");
		helper.append("}");

		return helper.toString();
	}

	@Override
	protected Draggable<?> create(String id, String selector, final String helper)
	{
		return new Draggable<Void>(id) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onConfigure(JQueryBehavior behavior)
			{
				super.onConfigure(behavior);

				behavior.setOption("helper", helper);
			}
		};
	}


}
