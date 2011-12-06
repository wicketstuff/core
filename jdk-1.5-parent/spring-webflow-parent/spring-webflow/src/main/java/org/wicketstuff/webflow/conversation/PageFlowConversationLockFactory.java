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
/* ***************************************************************************
 * File: PageFlowConversationLockFactory.java
 *****************************************************************************/
package org.wicketstuff.webflow.conversation;

import org.springframework.webflow.conversation.impl.ConversationLock;

/**
 * Utility class for creating PageFlowConversationLock instances.
 *
 * @author Clint Checketts, Florian Braun, Doug Hall, Subramanian Murali
 * @version $Id: $
 */
public class PageFlowConversationLockFactory 
{
	private int timeoutSeconds = 30;

	/**
	 * Returns the period of time that can elapse before a lock attempt times out for locks created by this factory.
	 *
	 * @return a int.
	 */
	public int getTimeoutSeconds() 
	{
		return timeoutSeconds;
	}

	/**
	 * Sets the period of time that can elapse before a lock attempt times out for locks created by this factory.
	 *
	 * @param timeoutSeconds the timeout period in seconds
	 */
	public void setTimeoutSeconds(int timeoutSeconds) 
	{
		this.timeoutSeconds = timeoutSeconds;
	}

	/**
	 * Creates a new concurrent conversation lock.
	 *
	 * @return ConversationLock
	 */
	public ConversationLock createLock() 
	{
		return new PageFlowConversationLock(timeoutSeconds);
	}
}
