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

import java.util.List;

import org.apache.wicket.model.IDetachable;
import org.wicketstuff.console.engine.Lang;

/**
 * A store for {@link ScriptTemplate}s.
 * <p>
 * This can be used to store and retrieve frequently used scripts in/from a persistent backend.
 * 
 * @author cretzel
 * 
 */
public interface IScriptTemplateStore extends IDetachable
{

	/**
	 * Looks up a template by id
	 * 
	 * @param id
	 *            template id
	 * @return template with id {@code id}
	 */
	ScriptTemplate getById(Long id);

	/**
	 * Returns all language specific templates from this store.
	 * 
	 * @param lang
	 *            Source language
	 * @return all templates
	 */
	List<ScriptTemplate> findAll(Lang lang);

	/**
	 * @return
	 */
	boolean readOnly();

	/**
	 * Saves/Updates a script as a template in this store.
	 * 
	 * @param template
	 *            the template
	 * @throws ReadOnlyStoreException
	 *             when this operation is called on a read-only store
	 */
	void save(ScriptTemplate template) throws ReadOnlyStoreException;

	/**
	 * Delete a script from the store.
	 * 
	 * @param template
	 *            template
	 * @throws ReadOnlyStoreException
	 *             when this operation is called on a read-only store
	 */
	void delete(ScriptTemplate template) throws ReadOnlyStoreException;

	/**
	 * Deletes a script from the store
	 * 
	 * @param id
	 *            id of the script to be deleted
	 * @throws ReadOnlyStoreException
	 *             when this operation is called on a read-only store
	 */
	void delete(Long id) throws ReadOnlyStoreException;

}
