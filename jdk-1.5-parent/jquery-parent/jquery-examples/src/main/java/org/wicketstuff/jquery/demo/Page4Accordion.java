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
package org.wicketstuff.jquery.demo;

import java.util.Iterator;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.util.ArrayIteratorAdapter;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.jquery.accordion.JQAccordion;

public class Page4Accordion extends PageSupport
{

	private static final long serialVersionUID = 1L;

	public Page4Accordion() throws Exception
	{

		super();

		add(new JQAccordion("accordion1")
		{

			private static final long serialVersionUID = 1L;

			@Override
			protected Iterator<IModel<String>> getItemModels()
			{
				return new ArrayIteratorAdapter<String>(
					new String[] {
							"Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Phasellus nec leo. Duis ultricies. In id ipsum vitae ante fringilla",
							"volutpat. In pharetra. Ut ante. Vivamus tempus, leo a ullamcorper tincidunt, pede ipsum consectetuer nunc, at pellentesque",
							"libero felis in metus. Pellentesque sollicitudin neque. Nulla facilisi. Sed hendrerit tempus orci. Aenean a nulla quis risus molestie vehicula." })
				{
					@Override
					protected IModel<String> model(String obj)
					{
						return new Model<String>(obj);
					}
				};
			}

			@Override
			protected void populateItem(Item<String> item)
			{
				item.add(new Label("title", item.getDefaultModelObjectAsString().substring(0, 15) +
					" ..."));
				item.add(new Label("content", item.getDefaultModelObjectAsString()));
			}
		});
	}
}
