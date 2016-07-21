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
package org.wicketstuff.minis.component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.MetaDataKey;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

/**
 * This class solves: <a href=
 * "http://apache-wicket.1842946.n4.nabble.com/Using-up-down-remove-links-of-ListView-items-clears-text-fields-td2325224.html"
 * >Using up/down/remove links of ListView items clears text fields</a>
 * 
 * @author <a href="http://sebthom.de/">Sebastian Thomschke</a>
 */
public final class ListViewFormComponentReuseManager implements Serializable
{
	private static final long serialVersionUID = 1L;

	private static final MetaDataKey<ListViewFormComponentReuseManager> REUSE_MANAGER_META_KEY = new MetaDataKey<ListViewFormComponentReuseManager>()
	{
		private static final long serialVersionUID = 1L;
	};

	/**
	 * Either adds the given <code>newComponent</code> to the parent or reuses an existing component
	 * instance previously used with the same model object.
	 * 
	 * @param <T>
	 * @param parent
	 * @param newComponent
	 * @return <code>newComponent</code> or a component previously bound to the same model object
	 */
	public static <T extends FormComponent<?>> T addOrReuse(final MarkupContainer parent,
		final T newComponent)
	{
		Object rowModelObject = null;
		MarkupContainer current = parent;
		while (current != null)
		{
			if (current instanceof ListItem<?>)
				rowModelObject = current.getDefaultModelObject();
			if (current instanceof ListView)
			{
				ListViewFormComponentReuseManager mgr = current.getMetaData(REUSE_MANAGER_META_KEY);
				if (mgr == null)
				{
					mgr = new ListViewFormComponentReuseManager();
					current.setMetaData(REUSE_MANAGER_META_KEY, mgr);
				}
				@SuppressWarnings("unchecked")
				final T componentToUse = (T)mgr.rememberOrReuse(rowModelObject,
					(FormComponent<?>)newComponent);
				parent.add(componentToUse);
				return componentToUse;
			}
			current = current.getParent();
		}
		return newComponent;
	}

	private final Map<Object, Map<String, FormComponent<?>>> componentsByRowModelObject = new HashMap<Object, Map<String, FormComponent<?>>>();

	private ListViewFormComponentReuseManager()
	{
		super();
	}

	/**
	 * @param rowModelObject
	 *            an object identifying the current row, e.g. the business object represented by
	 *            this row
	 * @return <code>newComponent</code> or a component previously bound to the same model object
	 */
	private <T extends FormComponent<?>> T rememberOrReuse(final Object rowModelObject,
		final T newComponent)
	{
		Map<String, FormComponent<?>> rowComponents = componentsByRowModelObject.get(rowModelObject);
		if (rowComponents == null)
			componentsByRowModelObject.put(rowModelObject,
				rowComponents = new HashMap<String, FormComponent<?>>());

		@SuppressWarnings("unchecked")
		final T existingComponent = (T)rowComponents.get(newComponent.getId());
		if (existingComponent == null)
		{
			rowComponents.put(newComponent.getId(), newComponent);
			return newComponent;
		}
		return existingComponent;
	}
}
