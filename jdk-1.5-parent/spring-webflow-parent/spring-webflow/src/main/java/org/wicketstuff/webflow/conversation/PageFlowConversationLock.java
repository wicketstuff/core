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
 * File: PageFlowConversationLock.java
 *****************************************************************************/
package org.wicketstuff.webflow.conversation;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.webflow.conversation.ConversationLockException;
import org.springframework.webflow.conversation.impl.ConversationLock;
import org.springframework.webflow.conversation.impl.LockInterruptedException;
import org.springframework.webflow.conversation.impl.LockTimeoutException;

/**
 * Page Flow conversation lock that relies on a {@link ReentrantLock} within Java 5's <code>util.concurrent.locks</code>
 * package.
 *
 * @author Clint Checketts, Florian Braun, Doug Hall, Subramanian Murali
 * @version $Id: $
 */
public class PageFlowConversationLock implements ConversationLock 
{
	private static final long	serialVersionUID	= 1L;

	private Lock lock = new ReentrantLock();

	private int timeoutSeconds;

	/**
	 * Constructor.
	 *
	 * @param timeoutSeconds a int.
	 */
	public PageFlowConversationLock(int timeoutSeconds) 
	{
		this.timeoutSeconds = timeoutSeconds;
	}

	/* (non-Javadoc)
	 * @see org.springframework.webflow.conversation.impl.ConversationLock#lock()
	 */
	/**
	 * <p>lock.</p>
	 *
	 * @throws org.springframework.webflow.conversation.ConversationLockException if any.
	 */
	public void lock() throws ConversationLockException 
	{
		try 
		{
			boolean acquired = lock.tryLock(timeoutSeconds, TimeUnit.SECONDS);
			if (!acquired) 
			{
				throw new LockTimeoutException(timeoutSeconds);
			}
		} 
		catch (InterruptedException e) 
		{
			throw new LockInterruptedException(e);
		}
	}

	/* (non-Javadoc)
	 * @see org.springframework.webflow.conversation.impl.ConversationLock#unlock()
	 */
	/**
	 * <p>unlock.</p>
	 */
	public void unlock() 
	{
		lock.unlock();
	}
}
