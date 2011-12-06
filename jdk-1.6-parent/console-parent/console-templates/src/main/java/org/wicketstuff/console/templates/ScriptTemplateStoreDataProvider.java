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

package org.wicketstuff.console.templates;

import java.util.Iterator;
import java.util.List;

import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.wicketstuff.console.engine.Lang;

/**
 * @author cretzel
 * 
 */
public class ScriptTemplateStoreDataProvider implements IDataProvider<ScriptTemplate>
{
	private static final long serialVersionUID = 1L;
	private final IScriptTemplateStore store;
	private final Lang lang;
	private List<ScriptTemplate> allTemplates;

	public ScriptTemplateStoreDataProvider(final IScriptTemplateStore store, final Lang lang)
	{
		this.store = store;
		this.lang = lang;
	}


	public Iterator<? extends ScriptTemplate> iterator(final long first, final long count)
	{
		init();
		return allTemplates.subList((int)first, (int)(first + count)).iterator();
	}

	public long size()
	{
		init();
		return allTemplates.size();
	}

	private void init()
	{
		if (allTemplates == null)
		{
			allTemplates = store.findAll(lang);
		}

	}

	public IModel<ScriptTemplate> model(final ScriptTemplate template)
	{
		return new StoredScriptTemplateModel(store, template);
	}

	public void detach()
	{
		allTemplates = null;
	}
}
