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
package org.wicketstuff.security.hive.authorization;

import java.io.Serializable;

import org.wicketstuff.security.hive.authentication.Subject;

/**
 * This interface represents the abstract notion of a principal, which can be used to represent a
 * set of permissions on the system.
 * 
 * @see java.security.Principal
 * @author marrink
 */
public interface Principal extends Serializable
{

	/**
	 * Compares this principal to the specified object. Returns true if the object passed in matches
	 * the principal represented by the implementation of this interface.
	 * 
	 * @param another
	 *            principal to compare with.
	 * @return true if the principal passed in is the same as this principal, false otherwise.
	 */
	public boolean equals(Object another);

	/**
	 * Returns a string representation of this principal.
	 * 
	 * @return a string representation of this principal.
	 */
	public String toString();

	/**
	 * Returns a hashcode for this principal.
	 * 
	 * @return a hashcode for this principal.
	 */
	public int hashCode();

	/**
	 * Returns the name of this principal.
	 * 
	 * @return the name of this principal.
	 */
	public String getName();

	/**
	 * Even though a subject does not explicitly hold a principal, it may still be implied by the
	 * subject. For example a 'read' and a 'write' principal, if the subject only holds the 'write'
	 * principal it is only logical it also implies the 'read' principal. Note principals don't have
	 * actions like permissions, the term 'read' principal is used to describe a principal
	 * containing a set of permissions granting a render action, just like the 'write' principal
	 * contains the same set of permissions only granting the enable action.
	 * 
	 * @param subject
	 *            a subject or null if no user has logged in yet
	 * @return true if the subject in any way implies this principal, false otherwise.
	 */
	public boolean implies(Subject subject);
}
