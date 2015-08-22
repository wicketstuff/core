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
package org.wicketstuff.security.hive.authentication;

import java.io.Serializable;
import java.util.Set;

import org.wicketstuff.security.hive.authorization.Principal;

/**
 * Subject represents (part of) an authenticated entity, such as an individual, a corporation, or a
 * login id. It can be decorated with certain rights ({@link Principal} s). Most implementations
 * will provide some means to add (and sometimes remove) principals, however all must honor the
 * readonly flag. {@link #setReadOnly()} is automatically triggered after a login. Subjects are
 * created by {@link LoginContext}s as placeholder for the permissions of a user for the duration of
 * the session.
 * 
 * @author marrink
 */
public interface Subject extends Serializable
{
	/**
	 * A readonly view of the principals.
	 * 
	 * @return the principals
	 */
	public Set<Principal> getPrincipals();

	/**
	 * When set it is no longer possible to change the set of principals of this subject.
	 * 
	 * @return true if this Subject is readonly, false otherwise
	 */
	public boolean isReadOnly();

	/**
	 * Mark this subject as readonly. preventing principals to be added or removed. Note this method
	 * is always called on a subject after it has been handed over to the security layer.
	 */
	public void setReadOnly();

}