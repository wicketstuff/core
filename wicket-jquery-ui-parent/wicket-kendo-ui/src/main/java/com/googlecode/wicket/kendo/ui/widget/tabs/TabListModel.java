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
package com.googlecode.wicket.kendo.ui.widget.tabs;

import java.util.List;

import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.model.util.ListModel;

/**
 * Provides a loadable (non-detachable) {@link ListModel} to use as {@link TabbedPanel}'s model
 *
 * @author Sebastien Briquet - sebfz1
 */
public abstract class TabListModel extends ListModel<ITab>
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 */
	public TabListModel()
	{
		super(null);
	}

	@Override
	public List<ITab> getObject()
	{
		if (super.getObject() == null)
		{
			this.setObject(this.load());
		}

		return super.getObject();
	}

	/**
	 * Clears the underlying list of {@code ITabs}
	 */
	public void clear()
	{
		this.setObject(null);
	}

	/**
	 * Loads the list of {@code ITabs}
	 * 
	 * @return the list of {@code ITabs}
	 */
	protected abstract List<ITab> load();
}
