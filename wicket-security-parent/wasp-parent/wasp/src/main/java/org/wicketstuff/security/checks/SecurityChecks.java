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

import org.wicketstuff.security.actions.WaspAction;

/**
 * Class for general security checks that combines security checks to work together. Note that
 * besides this way of chaining it is also possible wrap checks like this:</br>
 * 
 * <pre>
 * <code>
 * public class MySecurityCheck extends AbstractSecurityCheck
 * {
 * 	private ISecurityCheck wrapped;
 * 
 * 	public MySecurityCheck(ISecurityCheck wrapped)
 * 	{
 * 		this.wrapped = wrapped;
 * 	}
 * 
 * 	public boolean isActionAuthorized(WaspAction action)
 * 	{
 * 		return myOwnStuff() &amp;&amp; wrapped.isActionAuthorized(action);
 * 	}
 * 
 * 	public boolean isAuthenticated()
 * 	{
 * 		return myOwnStuff() &amp;&amp; wrapped.isAuthenticated();
 * 	}
 * }
 * </code>
 * </pre>
 * 
 * @author dashorst
 */
public class SecurityChecks
{
	/**
	 * Combines the checks in an <strong>and</strong> list of checks. This means: check1 and check2
	 * and check3 and ...
	 * 
	 * @param checks
	 *            the checks to combine into a single and check
	 * @return a security check that combines the checks in a single and
	 */
	public static ISecurityCheck and(ISecurityCheck[] checks)
	{
		return new AndSecurityCheck(checks);
	}

	/**
	 * Combines the checks in an <strong>and</strong> list of checks. This means: check1 and check2.
	 * 
	 * @param check1
	 *            the first of 2 checks to combine into a single and check
	 * @param check2
	 *            the second of 2 checks to combine into a single and check
	 * @return a security check that combines the checks in a single and
	 */
	public static ISecurityCheck and(ISecurityCheck check1, ISecurityCheck check2)
	{
		return new AndSecurityCheck(new ISecurityCheck[] { check1, check2 });
	}

	/**
	 * Combines the checks in an <strong>or</strong> list of checks. This means: check1 or check2 or
	 * check3 or ...
	 * 
	 * @param checks
	 *            the checks to combine into a single or check
	 * @return a security check that combines the checks in a single or
	 */
	public static ISecurityCheck or(ISecurityCheck[] checks)
	{
		return new OrSecurityCheck(checks);
	}

	/**
	 * Combines the checks in an <strong>or</strong> list of checks. This means: check1 or check2.
	 * 
	 * @param check1
	 *            the first of 2 checks to combine into a single or check
	 * @param check2
	 *            the second of 2 checks to combine into a single or check
	 * @return a security check that combines the checks in a single or
	 */
	public static ISecurityCheck or(ISecurityCheck check1, ISecurityCheck check2)
	{
		return new OrSecurityCheck(new ISecurityCheck[] { check1, check2 });
	}

	/**
	 * SecutiryCheck that provides an *and* security check combining several checks into a single
	 * and.
	 */
	private static class AndSecurityCheck extends AbstractSecurityCheck
	{
		/** For serialization. */
		private static final long serialVersionUID = 1L;

		private ISecurityCheck[] checks;

		private AndSecurityCheck(ISecurityCheck[] checks)
		{
			this.checks = checks;
			if (checks == null)
				this.checks = new ISecurityCheck[0];
		}

		/**
		 * Tests each {@link ISecurityCheck} sequentially, returning false as soon as one fails.
		 * Note that if no checks are present it will return true.
		 * 
		 * @see org.wicketstuff.security.checks.ISecurityCheck#isActionAuthorized(org.wicketstuff.security.actions.WaspAction)
		 */
		public boolean isActionAuthorized(WaspAction action)
		{
			ISecurityCheck check;
			for (int i = 0; i < checks.length; i++)
			{
				check = checks[i];
				if (check == null)
					continue;
				if (!check.isActionAuthorized(action))
					return false;
			}
			return true;
		}

		/**
		 * Tests each {@link ISecurityCheck} sequentially, returning false as soon as one fails.
		 * Note that if no checks are present it will return true.
		 * 
		 * @see org.wicketstuff.security.checks.ISecurityCheck#isAuthenticated()
		 */
		public boolean isAuthenticated()
		{
			ISecurityCheck check;
			for (int i = 0; i < checks.length; i++)
			{
				check = checks[i];
				if (check == null)
					continue;
				if (!check.isAuthenticated())
					return false;
			}
			return true;
		}
	}

	/**
	 * SecutiryCheck that provides an <strong>or</strong> security check combining several checks
	 * into a single or.
	 */
	private static class OrSecurityCheck extends AbstractSecurityCheck
	{
		/** For serialization. */
		private static final long serialVersionUID = 1L;

		private ISecurityCheck[] checks;

		private OrSecurityCheck(ISecurityCheck[] checks)
		{
			this.checks = checks;
			if (checks == null)
				this.checks = new ISecurityCheck[0];
		}

		/**
		 * Tests each {@link ISecurityCheck} sequentially, returning true as soon as one succeeds.
		 * Note that if no checks are present it will return true.
		 * 
		 * @see org.wicketstuff.security.checks.ISecurityCheck#isActionAuthorized(org.wicketstuff.security.actions.WaspAction)
		 */
		public boolean isActionAuthorized(WaspAction action)
		{
			if (checks.length == 0)
				return true;
			boolean containsChecks = false;
			ISecurityCheck check;
			for (int i = 0; i < checks.length; i++)
			{
				check = checks[i];
				if (check == null)
					continue;
				containsChecks = true;
				if (check.isActionAuthorized(action))
					return true;
			}
			return !containsChecks;
		}

		/**
		 * Tests each {@link ISecurityCheck} sequentially, returning true as soon as one succeeds.
		 * Note that if no checks are present it will return true.
		 * 
		 * @see org.wicketstuff.security.checks.ISecurityCheck#isAuthenticated()
		 */
		public boolean isAuthenticated()
		{
			if (checks.length == 0)
				return true;
			boolean containsChecks = false;
			ISecurityCheck check;
			for (int i = 0; i < checks.length; i++)
			{
				check = checks[i];
				if (check == null)
					continue;
				containsChecks = true;
				if (check.isAuthenticated())
					return true;
			}
			return !containsChecks;
		}
	}
}
