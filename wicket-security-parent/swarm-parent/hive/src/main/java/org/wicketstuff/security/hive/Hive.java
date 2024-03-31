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
package org.wicketstuff.security.hive;

import java.security.Policy;
import java.util.Set;

import org.wicketstuff.security.hive.authentication.Subject;
import org.wicketstuff.security.hive.authorization.Permission;
import org.wicketstuff.security.hive.authorization.Principal;

/**
 * A Hive contains the security policy for the system, much like the {@link Policy} in JAAS does.
 * However where the Policy only has a single instance for each virtual machine, multiple Hives are
 * allowed per vm. Allowing for instance multiple web applications to each have there own security
 * policies or share one. In effect making a hive a codebased policy. The {@link HiveMind} keeps a
 * registry of hives based on keys, everyone with the right key can access that hive, this allows
 * you to choose if multiple (different) webapps all share the same same hive or let each instance
 * of an application have there own hive. The key (sometimes referred to as the "queen", to stick
 * with wasp terminology ;)) can be any object. A good choice would be the application itself or if
 * it is a web application the contextname could be used.
 * 
 * @author marrink
 */
public interface Hive
{
	/**
	 * Checks if this hive contains an exact match for this principal.
	 * 
	 * @param principal
	 * @return true if the hive contains the principal, false otherwise.
	 */
	public boolean containsPrincipal(Principal principal);

	/**
	 * Checks if this hive contains exactly the permission.
	 * 
	 * @param permission
	 * @return true if the hive contains the permission.
	 */
	public boolean containsPermission(Permission permission);

	/**
	 * Checks if the subject has the exact permission, or if the permission is implied by any of the
	 * subjects principals.
	 * 
	 * @param subject
	 *            the (optional) subject
	 * @param permission
	 *            the permission to check.
	 * @return true if the subject has or implies the permission, false otherwise.
	 */
	public boolean hasPermission(Subject subject, Permission permission);

	/**
	 * Returns a set of {@link Principal}s, each containing this permission. Implementors must not
	 * allow modifications to the set change the internal state of the hive.
	 * 
	 * @param permission
	 * @return set, never null
	 */
	public Set<Principal> getPrincipals(Permission permission);

	/**
	 * Returns a set of {@link Permission}s, contained within the principal. Implementors must not
	 * allow modifications to the set change the internal state of the hive.
	 * 
	 * @param principal
	 * @return set, never null
	 */
	public Set<Permission> getPermissions(Principal principal);
}
