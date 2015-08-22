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
package org.wicketstuff.security.log;

import org.apache.wicket.MetaDataKey;

/**
 * key to store {@link IAuthorizationMessageSource} in the request metadata. This object is
 * typically only available in the request after at some point is determined that the authorization
 * has failed. After that the IErrorMessageSource can be used to add extra information to the error.
 * This can then later be used to provide a detailed error message.
 * 
 * @author marrink
 */
public class AuthorizationErrorKey extends MetaDataKey<IAuthorizationMessageSource>
{
	private static final long serialVersionUID = 1L;

	/**
	 * Construct.
	 * 
	 * 
	 */
	public AuthorizationErrorKey()
	{
		super();
	}

}
