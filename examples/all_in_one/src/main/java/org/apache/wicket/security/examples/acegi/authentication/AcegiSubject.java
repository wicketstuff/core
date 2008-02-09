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
package org.apache.wicket.security.examples.acegi.authentication;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.context.SecurityContextHolder;
import org.apache.wicket.security.examples.authorization.MyPrincipal;
import org.apache.wicket.security.hive.authentication.DefaultSubject;
import org.apache.wicket.security.hive.authorization.Principal;

/**
 * Subject that gets is principals from the authenticated user in the
 * {@link SecurityContextHolder}. This class is converts all authorities to
 * {@link MyPrincipal}s but could serve as a template for your implementation.
 * 
 * @author marrink
 */
public class AcegiSubject extends DefaultSubject
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 */
	public AcegiSubject()
	{
		GrantedAuthority[] authorities = SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities();
		if (authorities != null)
		{
			Principal principal;
			for (int i = 0; i < authorities.length; i++)
			{
				principal = convert(authorities[i]);
				if (principal != null)
					addPrincipal(principal);
			}
		}
	}

	/**
	 * Converts a {@link GrantedAuthority} to a {@link Principal}
	 * 
	 * @param authority
	 * @return principal or null if the authority could not be converted
	 */
	protected Principal convert(GrantedAuthority authority)
	{
		return new MyPrincipal(authority.getAuthority());
	}

}
