package org.wicketstuff.shiro.example.realm;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import java.util.Collection;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAccount;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Les Hazlewood
 * @since Aug 28, 2008 5:13:06 PM
 */
public class SillyRealm extends AuthorizingRealm
{
	static final Logger log = LoggerFactory.getLogger(SillyRealm.class);

	public SillyRealm()
	{
		log.info("constructor...");

		// Only do authentication once for each request
		setCacheManager(new MemoryConstrainedCacheManager());
	}

	/**
	 * Simulates a call to an underlying data store - in a 'real' application, this call would
	 * communicate with an underlying data store via an EIS API (JDBC, JPA, Hibernate, etc).
	 * <p/>
	 * Note that when implementing your own realm, there is no need to check against a password (or
	 * other credentials) in this method. The {@link org.apache.shiro.realm.AuthenticatingRealm
	 * AuthenticatingRealm} superclass will do that automatically via the use of a configured
	 * {@link org.apache.shiro.authc.credential.CredentialsMatcher CredentialsMatcher} (see this
	 * example's corresponding <code>shiro.ini</code> file to see a configured credentials matcher).
	 * <p/>
	 * All that is required is that the account information include directly the credentials found
	 * in the EIS.
	 * 
	 * @param username
	 *            the username for the account data to retrieve
	 * @return the Account information corresponding to the specified username:
	 */
	protected SimpleAccount getAccount(String username)
	{
		log.info("get account: " + username);

		// just create a dummy. A real app would construct one based on EIS access.
		SimpleAccount account = new SimpleAccount(username, "pass", getName());
		// simulate some roles and permissions:
		account.addRole("user");

		if ("admin".equals(username))
		{
			account.addRole("admin");
		}

		// most applications would assign permissions to Roles instead of users directly because
		// this is much more
		// flexible (it is easier to configure roles and then change role-to-user assignments than
		// it is to maintain
		// permissions for each user).
		// But these next lines assign permissions directly to this trivial account object just for
		// simulation's sake:
		account.addStringPermission("blogEntry:edit"); // this user is allowed to 'edit' _any_
		// blogEntry
		// fine-grained instance level permission:
		account.addStringPermission("printer:print:laserjet2000"); // allowed to 'print' to the
		// 'printer' identified
		// by the id 'laserjet2000'
		account.addStringPermission("view"); // all users have view permission

		return account;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
		throws AuthenticationException
	{
		log.info("doGetAuthenticationInfo: " + token);

		// we can safely cast to a UsernamePasswordToken here, because this class 'supports'
		// UsernamePasswordToken
		// objects. See the Realm.supports() method if your application will use a different type of
		// token.
		UsernamePasswordToken upToken = (UsernamePasswordToken)token;
		return getAccount(upToken.getUsername());
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals)
	{
		log.info("doGetAuthorizationInfo: " + principals);

		// simulate a call to an underlying data store:

		// get only the principals that this realm cares about:
		@SuppressWarnings("unchecked")
		Collection<String> thisRealmPrincipals = principals.fromRealm(getName());
		if (thisRealmPrincipals == null || thisRealmPrincipals.isEmpty())
		{
			return null;
		}

		// note that the return value of 'getName()' here is whatever you specify it to be in
		// jsecurity.ini
		// in this case, the jsecurity.ini file calls it 'myRealm'. All realms must have a unique
		// name.

		// Since this realm supplied these principals from the doGetAuthenticationInfo method above
		// when the
		// user logged-in and that method in this simple example has only one principal - a username
		// - we can safely
		// assume the only element in this collection is that username.
		String username = thisRealmPrincipals.iterator().next();

		// call the underlying EIS for the account data:
		return getAccount(username);
	}
}
