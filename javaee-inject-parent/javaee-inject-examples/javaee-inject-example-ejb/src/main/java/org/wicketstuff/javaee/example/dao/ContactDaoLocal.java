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
package org.wicketstuff.javaee.example.dao;

import jakarta.ejb.Local;

import java.util.List;

import org.wicketstuff.javaee.example.model.Contact;

/**
 * 
 * @author Filippo Diotalevi
 */
@Local
public interface ContactDaoLocal
{

	/**
	 * Returns the currently available contacts
	 * 
	 * @return every contact in the database
	 */
	public List<Contact> getContacts();

	/**
	 * Returns a specific Contact from DB
	 * 
	 * @param id
	 *            The Id for the Contact
	 * @return The specified Contact object
	 */
	public Contact getContact(Long id);

	/**
	 * Persist a new Contact in the DB
	 * 
	 * @param name
	 *            The name of the new Contact
	 * @param email
	 *            The e-mail address of the new Contact
	 */
	public void addContact(String name, String email);

	/**
	 * Removes a specific item from the DB
	 * 
	 * @param modelObject
	 *            The specific Contact object, which we wants to remove
	 */
	public void remove(Contact modelObject);
}
