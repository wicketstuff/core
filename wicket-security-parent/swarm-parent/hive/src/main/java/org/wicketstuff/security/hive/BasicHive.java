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

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.security.hive.authentication.Subject;
import org.wicketstuff.security.hive.authorization.Permission;
import org.wicketstuff.security.hive.authorization.Principal;
import org.wicketstuff.security.util.ManyToManyMap;

/**
 * Basic implementation of a Hive. It contains basic add methods to facilitate factories. It also
 * might be locked after which no changes can be made to the hive.
 * 
 * @author marrink
 */
public class BasicHive implements Hive
{
	private static final Logger log = LoggerFactory.getLogger(BasicHive.class);

	/**
	 * Maps {@link Permission}s to {@link Principal}s
	 */
	private ManyToManyMap<Permission, Principal> principals;

	/**
	 * indicates if permissions and or principals are accepted by the hive.
	 */
	private boolean locked = false;

	/**
	 * 
	 * Construct.
	 */
	public BasicHive()
	{
		// guess lots of principals
		principals = new ManyToManyMap<Permission, Principal>(500);
	}

	/**
	 * Locks this hive. No changes are allowed anymore. After this {@link #isLocked()} will return
	 * true;
	 */
	public final void lock()
	{
		locked = true;
		if (log.isDebugEnabled())
			log.debug("Locking Hive, permissions can not be added anymore.");
	}

	/**
	 * Check if the hive is locked. If the hive is locked no changes can be made.
	 * 
	 * @return true if the hive is locked, false otherwise.
	 */
	public final boolean isLocked()
	{
		return locked;
	}

	/**
	 * Adds a new Principal to the hive.
	 * 
	 * @param principal
	 *            the principal
	 * @param permissions
	 *            a required collection of granted permissions for the principal
	 * @throws IllegalStateException
	 *             if the hive is locked
	 * @throws IllegalArgumentException
	 *             if either parameter is null
	 */
	public final void addPrincipal(Principal principal, Collection<Permission> permissions)
	{
		if (isLocked())
			throw new IllegalStateException("While the hive is locked no changes are allowed.");
		if (principal == null)
			throw new IllegalArgumentException("A principal is required.");
		if (permissions == null)
			throw new IllegalArgumentException(
				"At least one permission is required for principal " + principal);
		boolean debug = log.isDebugEnabled();
		for (Permission next : permissions)
		{
			principals.add(next, principal);
			if (debug)
				log.debug("Adding " + next + " to " + principal);
		}
	}

	/**
	 * Adds a new permission to a principal.
	 * 
	 * @param principal
	 *            the principal
	 * @param permission
	 *            the permission granted
	 * @throws IllegalStateException
	 *             if the hive is locked
	 * @throws IllegalArgumentException
	 *             if either parameter is null
	 */
	public final void addPermission(Principal principal, Permission permission)
	{
		if (isLocked())
			throw new IllegalStateException("While the hive is locked no changes are allowed.");
		if (principal == null)
			throw new IllegalArgumentException("A principal is required.");
		if (permission == null)
			throw new IllegalArgumentException("A permission is required.");
		principals.add(permission, principal);
		if (log.isDebugEnabled())
			log.debug("Adding " + permission + " to " + principal);
	}

	/**
	 * @see org.wicketstuff.security.hive.Hive#containsPrincipal(org.wicketstuff.security.hive.authorization.Principal)
	 */
	public final boolean containsPrincipal(Principal principal)
	{
		return principals.containsRight(principal);
	}

	/**
	 * Allows subclasses to retrieve previously cached results and thus speed up the check. By
	 * default null is returned, meaning no cached value
	 * 
	 * @param subject
	 *            (optional) subject
	 * @param permission
	 *            the permission to check
	 * @return null if there is no cached value, true if the permission is granted, false if the
	 *         permission is denied
	 */
	protected Boolean cacheLookUp(Subject subject, Permission permission)
	{
		return null;
	}

	/**
	 * Allows subclasses to cache the result of a check and thus speed up this check the next time.
	 * By default this method does not have an implementation.
	 * 
	 * @param subject
	 *            (optional) subject
	 * @param permission
	 *            the permission to check
	 * @param result
	 *            the result of the permission
	 */
	protected void cacheResult(Subject subject, Permission permission, boolean result)
	{
		// noop
	}

	/**
	 * @see org.wicketstuff.security.hive.Hive#hasPermission(org.wicketstuff.security.hive.authentication.Subject,
	 *      org.wicketstuff.security.hive.authorization.Permission)
	 */
	public final boolean hasPermission(Subject subject, Permission permission)
	{
		Boolean cacheResult = cacheLookUp(subject, permission);
		if (cacheResult != null)
		{
			if (log.isDebugEnabled())
				log.debug(subject + " has a cached match for " + permission + ", result " +
					cacheResult.booleanValue());
			return cacheResult.booleanValue();
		}
		if (hasPrincipal(subject, principals.getRight(permission)))
		{
			if (log.isDebugEnabled())
				log.debug(subject + " has an exact match for " + permission);
			cacheResult(subject, permission, true);
			return true;
		}
		// permission has no exact match, perform an implies check
		Iterator<Permission> it = principals.leftIterator();
		while (it.hasNext())
		{
			Permission possibleMatch = it.next();
			if (!possibleMatch.implies(permission))
				continue;
			if (hasPrincipal(subject, principals.getRight(possibleMatch)))
			{
				if (log.isDebugEnabled())
					log.debug(subject + " implies " + permission);
				cacheResult(subject, permission, true);
				return true;
			}
		}
		if (log.isDebugEnabled())
			log.debug(subject + " does not have or implies " + permission);
		cacheResult(subject, permission, false);
		return false;
	}

	/**
	 * Checks if the subject has or implies any of the principals in the set.
	 * 
	 * @param subject
	 *            optional authenticated subject
	 * @param principalSet
	 *            set of principals
	 * @return true if the subject has or implies at least one of the principals, false otherwise.
	 */
	private final boolean hasPrincipal(Subject subject, Set<Principal> principalSet)
	{
		if (!principalSet.isEmpty())
		{
			Set<Principal> subjectPrincipals;
			if (subject == null)
				subjectPrincipals = Collections.emptySet();
			else
				subjectPrincipals = subject.getPrincipals();
			for (Principal curPrincipal : principalSet)
			{
				if (subjectPrincipals.contains(curPrincipal) || curPrincipal.implies(subject))
					return true;
			}
		}
		return false;
	}

	/**
	 * @see org.wicketstuff.security.hive.Hive#containsPermission(org.wicketstuff.security.hive.authorization.Permission)
	 */
	public final boolean containsPermission(Permission permission)
	{
		return principals.containsLeft(permission);
	}

	/**
	 * 
	 * @see org.wicketstuff.security.hive.Hive#getPermissions(org.wicketstuff.security.hive.authorization.Principal)
	 */
	public final Set<Permission> getPermissions(Principal principal)
	{
		Set<Permission> set = principals.getLeft(principal);
		if (set == null)
			return Collections.emptySet();
		return Collections.unmodifiableSet(set);
	}

	/**
	 * 
	 * @see org.wicketstuff.security.hive.Hive#getPrincipals(org.wicketstuff.security.hive.authorization.Permission)
	 */
	public final Set<Principal> getPrincipals(Permission permission)
	{
		Set<Principal> set = principals.getRight(permission);
		if (set == null)
			return Collections.emptySet();
		return Collections.unmodifiableSet(set);
	}
}
