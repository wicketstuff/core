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

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.wicketstuff.console.engine.Lang;
import org.wicketstuff.console.templates.ScriptTemplate;

/**
 * A Dao for {@link ScriptTemplate}s
 * 
 * @author cretzel
 */
@Repository
@Transactional
public class HibernateScriptTemplateDao implements IHibernateScriptTemplateDao
{

	@Autowired
	private SessionFactory sessionFactory;

	public void save(final PersistentScriptTemplate t)
	{
		sessionFactory.getCurrentSession().saveOrUpdate(t);
	}

	@SuppressWarnings("unchecked")
	public List<PersistentScriptTemplate> findAll(final Lang lang)
	{
		return sessionFactory.getCurrentSession()
			.createCriteria(PersistentScriptTemplate.class)
			.add(Restrictions.eq("lang", lang))
			.list();
	}

	public PersistentScriptTemplate get(final Long id)
	{
		return (PersistentScriptTemplate)sessionFactory.getCurrentSession().get(
			PersistentScriptTemplate.class, id);
	}

	public void delete(final Long id)
	{
		final PersistentScriptTemplate template = get(id);
		if (template != null)
		{
			sessionFactory.getCurrentSession().delete(template);
		}
	}
}
