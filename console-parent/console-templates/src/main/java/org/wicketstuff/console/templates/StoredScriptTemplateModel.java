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

import org.apache.wicket.model.LoadableDetachableModel;

/**
 * A {@link LoadableDetachableModel} for {@link ScriptTemplate}s from an
 * {@link IScriptTemplateStore}.
 * 
 * @author cretzel
 * 
 */
public class StoredScriptTemplateModel extends LoadableDetachableModel<ScriptTemplate>
{
	private static final long serialVersionUID = 1L;

	private final IScriptTemplateStore store;
	private final Long id;

	public StoredScriptTemplateModel(final IScriptTemplateStore store, final Long id)
	{
		this.store = store;
		this.id = id;
	}

	public StoredScriptTemplateModel(final IScriptTemplateStore store, final ScriptTemplate template)
	{
		super(template);
		this.store = store;
		id = template.id;
	}

	@Override
	protected ScriptTemplate load()
	{
		return store.getById(id);
	}

}
