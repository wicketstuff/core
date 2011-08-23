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

package org.wicketstuff.console.examples.hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.wicketstuff.console.engine.Lang;
import org.wicketstuff.console.templates.ScriptTemplate;

/**
 * A persistent representation of a {@link ScriptTemplate}
 * 
 * @author cretzel
 * 
 */
@Entity
public class PersistentScriptTemplate
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String title;

	@Column(length = 1024)
	private String script;

	@Enumerated(EnumType.STRING)
	private Lang lang;

	public PersistentScriptTemplate()
	{
	}

	public PersistentScriptTemplate(final Long id, final String title, final String script,
		final Lang lang)
	{
		super();
		this.id = id;
		this.title = title;
		this.script = script;
		this.lang = lang;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(final Long id)
	{
		this.id = id;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(final String title)
	{
		this.title = title;
	}

	public String getScript()
	{
		return script;
	}

	public void setScript(final String script)
	{
		this.script = script;
	}

	public Lang getLang()
	{
		return lang;
	}

	public void setLang(final Lang lang)
	{
		this.lang = lang;
	}

	public ScriptTemplate toScriptTemplate()
	{
		return new ScriptTemplate(id, title, script, lang);
	}

	public static PersistentScriptTemplate fromScriptTemplate(final ScriptTemplate t)
	{
		return new PersistentScriptTemplate(t.id, t.title, t.script, t.lang);
	}
}
