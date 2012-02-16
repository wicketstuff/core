/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributed by United Services Automotive Association (USAA)
 */

/**
 *****************************************************************************
 * File: WicketSpringWebFlowException.java
 *****************************************************************************
 *
 * @author Clint Checketts, Florian Braun, Doug Hall, Subramanian Murali
 * @version $Id: $
 */
package org.wicketstuff.webflow;

import org.apache.wicket.WicketRuntimeException;
public class WicketSpringWebFlowException extends WicketRuntimeException
{
	String userVisibleMessage;
	
	/**
	 * <p>Constructor for WicketSpringWebFlowException.</p>
	 *
	 * @param userVisibleMessage a {@link java.lang.String} object.
	 * @param ex a {@link java.lang.Throwable} object.
	 */
	public WicketSpringWebFlowException(String userVisibleMessage,Throwable ex)
	{
		super(ex);
		this.userVisibleMessage = userVisibleMessage;
	}

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	/**
	 * <p>Getter for the field <code>userVisibleMessage</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getUserVisibleMessage()
	{
		return userVisibleMessage;
	}

	
	
}

