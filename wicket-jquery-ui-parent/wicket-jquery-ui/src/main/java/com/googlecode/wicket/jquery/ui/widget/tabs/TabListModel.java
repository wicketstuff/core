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
package com.googlecode.wicket.jquery.ui.widget.tabs;

import java.util.List;

import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.model.util.ListModel;

/**
 * Provides a loadable (not detachable) {@link ListModel} of {@link ITab}{@code s}
 *
 * @author Sebastien Briquet - sebfz1
 * @since 6.20.0
 */
public abstract class TabListModel extends ListModel<ITab>
{
	private static final long serialVersionUID = 1L;

	private transient boolean load = true;

	@Override
	public List<ITab> getObject()
	{
		if (this.load)
		{
			this.setObject(this.load());
			this.load = false;
		}

		return super.getObject();
	}

	/**
	 * Loads the model object
	 * 
	 * @return the {@link List} of {@link ITab}
	 */
	protected abstract List<ITab> load();

	/**
	 * Will force reload the model object next time {@link #getObject()} is called
	 */
	public void flush()
	{
		this.load = true;
	}
}
