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
package org.wicketstuff.dojo11.dojodnd;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.parser.XmlTag;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.string.Strings;
import org.apache.wicket.util.value.IValueMap;

/**
 * @author Stefan Fussenegger
 */
public class DojoDraggableBehavior extends AbstractBehavior
{
	/**
	 * same as {@link DojoDropContainer#DEFAULT_ACCEPT}
	 */
	public static final String DEFAULT_DND_TYPE = DojoDropContainer.DEFAULT_ACCEPT;
	
	private static final String DOJO_DND_ITEM_CLASS = "dojoDndItem";
	private IModel dndTypeModel;

	/**
	 * Construct.
	 */
	public DojoDraggableBehavior() {
		this(new Model(DEFAULT_DND_TYPE));
	}
	
	/**
	 * Construct.
	 * @param dndTypeModel
	 */
	public DojoDraggableBehavior(IModel dndTypeModel) {
		this.dndTypeModel = dndTypeModel;
	}
	
	/**
	 * @see org.apache.wicket.behavior.IBehavior#onComponentTag(org.apache.wicket.Component,
	 *      org.apache.wicket.markup.ComponentTag)
	 */
	public final void onComponentTag(Component component, ComponentTag tag)
	{
		if (tag.getType() != XmlTag.CLOSE)
		{
			final IValueMap attributes = tag.getAttributes();
			attributes.put("dndType", dndTypeModel.getObject());
			String cls = (String) attributes.get("class");
			if (Strings.isEmpty(cls)) {
				cls = DOJO_DND_ITEM_CLASS;
			} else {
				cls = cls + " " + DOJO_DND_ITEM_CLASS;
			}
			attributes.put("class", cls);
		}
	}

	/**
	 * @see org.apache.wicket.behavior.AbstractBehavior#bind(org.apache.wicket.Component)
	 */
	@Override
	public void bind(Component component)
	{
		super.bind(component);
		component.setOutputMarkupId(true);
	}

	/**
	 * @see org.apache.wicket.behavior.AbstractBehavior#detach(org.apache.wicket.Component)
	 */
	@Override
	public void detach(Component component)
	{
		dndTypeModel.detach();
		super.detach(component);
	}
	
	
}
