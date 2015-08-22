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
package org.wicketstuff.security.checks;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.wicketstuff.security.actions.WaspAction;

/**
 * @author marrink
 */
public class SecurityChecksTest
{

	/**
	 * Test method for
	 * {@link org.wicketstuff.security.checks.SecurityChecks#and(org.wicketstuff.security.checks.ISecurityCheck[])}
	 * .
	 */
	@Test
	public void testAnd()
	{
		runCheck(SecurityChecks.and(null, null), true, true);
		runCheck(SecurityChecks.and(null), true, true);
		runCheck(SecurityChecks.and(new BogusCheck(true), null), true, true);
		runCheck(SecurityChecks.and(new ISecurityCheck[] { new BogusCheck(true) }), true, true);
		runCheck(SecurityChecks.and(new ISecurityCheck[] { new BogusCheck(true), null }), true,
			true);
		runCheck(
			SecurityChecks.and(new ISecurityCheck[] { new BogusCheck(true),
					new BogusCheck(true, false) }), true, false);
		runCheck(
			SecurityChecks.and(new ISecurityCheck[] { new BogusCheck(true),
					new BogusCheck(false, true) }), false, true);
		runCheck(
			SecurityChecks.and(new ISecurityCheck[] { new BogusCheck(true), null,
					new BogusCheck(true), new BogusCheck(false) }), false, false);
		runCheck(SecurityChecks.and(new ISecurityCheck[] { new BogusCheck(false) }), false, false);
		runCheck(SecurityChecks.and(new BogusCheck(false), new BogusCheck(false)), false, false);
	}

	/**
	 * Test method for
	 * {@link org.wicketstuff.security.checks.SecurityChecks#or(org.wicketstuff.security.checks.ISecurityCheck[])}
	 * .
	 */
	@Test
	public void testOr()
	{
		runCheck(SecurityChecks.or(null, null), true, true);
		runCheck(SecurityChecks.or(null), true, true);
		runCheck(SecurityChecks.or(new BogusCheck(true), null), true, true);
		runCheck(SecurityChecks.or(new ISecurityCheck[] { new BogusCheck(true) }), true, true);
		runCheck(SecurityChecks.or(new ISecurityCheck[] { new BogusCheck(true), null }), true, true);
		runCheck(
			SecurityChecks.or(new ISecurityCheck[] { new BogusCheck(true),
					new BogusCheck(true, false) }), true, true);
		runCheck(
			SecurityChecks.or(new ISecurityCheck[] { new BogusCheck(true),
					new BogusCheck(false, true) }), true, true);
		runCheck(
			SecurityChecks.or(new ISecurityCheck[] { new BogusCheck(true), null,
					new BogusCheck(true), new BogusCheck(false) }), true, true);
		runCheck(SecurityChecks.or(new ISecurityCheck[] { new BogusCheck(false) }), false, false);
		runCheck(SecurityChecks.or(new BogusCheck(false), new BogusCheck(false)), false, false);
	}

	/**
	 * Executes the real test.
	 * 
	 * @param check
	 * @param authorized
	 * @param authenticated
	 */
	private void runCheck(ISecurityCheck check, boolean authorized, boolean authenticated)
	{
		assertEquals(authorized, check.isActionAuthorized(null));
		assertEquals(authenticated, check.isAuthenticated());
	}

	/**
	 * Simple check for testing.
	 * 
	 * @author marrink
	 */
	private static final class BogusCheck implements ISecurityCheck
	{
		private static final long serialVersionUID = 1L;

		private boolean authorized;

		private boolean authenticated;

		/**
		 * Construct.
		 * 
		 * @param authorized
		 * @param authenticated
		 */
		public BogusCheck(boolean authorized, boolean authenticated)
		{
			super();
			this.authorized = authorized;
			this.authenticated = authenticated;
		}

		/**
		 * Construct. All same value as flag.
		 * 
		 * @param flag
		 */
		public BogusCheck(boolean flag)
		{
			super();
			authorized = flag;
			authenticated = flag;
		}

		/**
		 * 
		 * @see org.wicketstuff.security.checks.ISecurityCheck#isActionAuthorized(org.wicketstuff.security.actions.WaspAction)
		 */
		public boolean isActionAuthorized(WaspAction action)
		{
			return authorized;
		}

		/**
		 * 
		 * @see org.wicketstuff.security.checks.ISecurityCheck#isAuthenticated()
		 */
		public boolean isAuthenticated()
		{
			return authenticated;
		}

	}
}
