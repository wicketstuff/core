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
package org.wicketstuff.security.actions;

/**
 * Immutable {@link WaspAction} class. These actions are instantiated by an ActionFactory.
 * 
 * @author marrink
 */
public abstract class AbstractWaspAction implements WaspAction
{
	private final String name;

	/**
	 * The default constructor for actions.
	 * 
	 * @param name
	 *            the name of this action
	 */
	protected AbstractWaspAction(String name)
	{
		this.name = name;
		if (isEmpty(name))
			throw new IllegalArgumentException(
				"Name argument may not be null, whitespace or the empty string");
	}

	/**
	 * Small check to see if a string contains more then just whitespace. Copied from
	 * {@link org.apache.wicket.util.string.Strings#isEmpty(CharSequence)} to keep this as much
	 * separated as possible from wicket code
	 * 
	 * @param string
	 * @return
	 */
	private static boolean isEmpty(final CharSequence string)
	{
		return string == null || string.length() == 0 || string.toString().trim().length() == 0;
	}

	/**
	 * 
	 * @see org.wicketstuff.security.actions.WaspAction#getName()
	 */
	public final String getName()
	{
		return name;
	}

}
