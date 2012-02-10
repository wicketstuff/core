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

package org.wicketstuff.console.examples.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wicketstuff.console.engine.Lang;
import org.wicketstuff.console.examples.hibernate.IHibernateScriptTemplateDao;
import org.wicketstuff.console.examples.hibernate.PersistentScriptTemplate;
import org.wicketstuff.console.templates.PackagedScriptTemplates;
import org.wicketstuff.console.templates.ScriptTemplate;

/**
 * Initialization routines.
 * 
 * @author cretzel
 */
@Service
public class InitServiceImpl implements InitService, InitializingBean
{

	private final IHibernateScriptTemplateDao hibernateDao;

	@Autowired
	public InitServiceImpl(final IHibernateScriptTemplateDao hibernateDao)
	{
		this.hibernateDao = hibernateDao;
	}

	public void init()
	{
		transferPackagedScriptTemplatesToHibernate();
	}

	@Transactional
	public void afterPropertiesSet() throws Exception
	{
		init();
	}

	/**
	 * Transfers the scripts from {@link PackagedScriptTemplates} to the Hibernate store.
	 */
	private void transferPackagedScriptTemplatesToHibernate()
	{
		final PackagedScriptTemplates pst = new PackagedScriptTemplates();
		final List<ScriptTemplate> templates = new ArrayList<ScriptTemplate>();
		for (final Lang lang : Lang.values())
		{
			templates.addAll(pst.findAll(lang));
		}

		for (final ScriptTemplate template : templates)
		{
			final PersistentScriptTemplate pTemplate = PersistentScriptTemplate.fromScriptTemplate(template);
			pTemplate.setId(null);
			hibernateDao.save(pTemplate);
		}
	}

}
