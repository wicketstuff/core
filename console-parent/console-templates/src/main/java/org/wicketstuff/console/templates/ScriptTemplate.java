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

import java.io.Serializable;

import org.wicketstuff.console.engine.Lang;

/**
 * Represents a single script template.
 * <p>
 * Script templates can be used to provide frequently used scripts. These script templates can than
 * be provided to a {@link ScriptTemplateSelectionTablePanel} to make them accessible through the
 * UI.
 * 
 * @author cretzel
 */
public class ScriptTemplate implements Serializable
{

	private static final long serialVersionUID = 1L;

	public Long id;
	public String title;
	public String script;
	public Lang lang;

	public ScriptTemplate(final Long id, final String title, final String script, final Lang lang)
	{
		this.id = id;
		this.title = title;
		this.script = script;
		this.lang = lang;
	}

	public ScriptTemplate(final String title, final String script, final Lang lang)
	{
		this.title = title;
		this.script = script;
		this.lang = lang;
	}

	@Override
	public String toString()
	{
		return "ScriptTemplate [id=" + id + ", title=" + title + ", lang=" + lang + "]";
	}


}