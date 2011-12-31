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
 * File: PageFlowConversationManager.java
 *****************************************************************************/
package org.wicketstuff.webflow.conversation;

import org.springframework.webflow.conversation.Conversation;
import org.springframework.webflow.conversation.ConversationException;
import org.springframework.webflow.conversation.ConversationId;
import org.springframework.webflow.conversation.ConversationManager;
import org.springframework.webflow.conversation.ConversationParameters;
import org.springframework.webflow.conversation.impl.BadlyFormattedConversationIdException;
import org.springframework.webflow.conversation.impl.SimpleConversationId;
import org.wicketstuff.webflow.session.PageFlowSession;


/**
 * Implementation of a conversation manager that stores the conversations in the Page Flow Session instance.
 * <p>
 * Using the {@link #setMaxConversations(int) maxConversations} property, you can limit the number of concurrently
 * active conversations allowed in a single session. If the maximum is exceeded, the conversation manager will
 * automatically end the oldest conversation. The default is 5, which should be fine for most situations. Set it to -1
 * for no limit. Setting maxConversations to 1 allows easy resource cleanup in situations where there should only be one
 * active conversation per session.
 *
 * @author Clint Checketts, Florian Braun, Doug Hall, Subramanian Murali
 * @version $Id: $
 */
public class PageFlowConversationManager implements ConversationManager
{
	/**
	 * The maximum number of active conversations allowed in a session. The default is 5. This is high enough for most
	 * practical situations and low enough to avoid excessive resource usage or easy denial of service attacks.
	 */
	private int maxConversations = 5;

	/**
	 * The factory for creating conversation lock objects.
	 */
	private PageFlowConversationLockFactory conversationLockFactory = new PageFlowConversationLockFactory();

	/**
	 * Returns the maximum number of allowed concurrent conversations. The default is 5.
	 *
	 * @return a int.
	 */
	public int getMaxConversations()
	{
		return maxConversations;
	}

	/**
	 * Set the maximum number of allowed concurrent conversations. Set to -1 for no limit. The default is 5.
	 *
	 * @param maxConversations a int.
	 */
	public void setMaxConversations(int maxConversations)
	{
		this.maxConversations = maxConversations;
	}

	/**
	 * Returns the time period that can elapse before a timeout occurs on an attempt to acquire a conversation lock. The
	 * default is 30 seconds.
	 *
	 * @return a int.
	 */
	public int getLockTimeoutSeconds() 
	{
		return conversationLockFactory.getTimeoutSeconds();
	}

	/**
	 * Sets the time period that can elapse before a timeout occurs on an attempt to acquire a conversation lock. The
	 * default is 30 seconds.
	 *
	 * @param timeoutSeconds the timeout period in seconds
	 */
	public void setLockTimeoutSeconds(int timeoutSeconds)
	{
		conversationLockFactory.setTimeoutSeconds(timeoutSeconds);
	}

	/* (non-Javadoc)
	 * @see org.springframework.webflow.conversation.ConversationManager#beginConversation(org.springframework.webflow.conversation.ConversationParameters)
	 */
	/** {@inheritDoc} */
	public Conversation beginConversation(ConversationParameters conversationParameters) throws ConversationException 
	{
		return getConversationContainer().createConversation(conversationParameters, conversationLockFactory);
	}

	/* (non-Javadoc)
	 * @see org.springframework.webflow.conversation.ConversationManager#getConversation(org.springframework.webflow.conversation.ConversationId)
	 */
	/** {@inheritDoc} */
	public Conversation getConversation(ConversationId id) throws ConversationException 
	{
		return getConversationContainer().getConversation(id);
	}

	/* (non-Javadoc)
	 * @see org.springframework.webflow.conversation.ConversationManager#parseConversationId(java.lang.String)
	 */
	/** {@inheritDoc} */
	public ConversationId parseConversationId(String encodedId) throws ConversationException 
	{
		try 
		{
			return new SimpleConversationId(Integer.valueOf(encodedId));
		} 
		catch (NumberFormatException e) 
		{
			throw new BadlyFormattedConversationIdException(encodedId, e);
		}
	}

	/**
	 * Method that creates a new PageFlowConversationContainer.
	 *
	 * @return PageFlowConversationContainer.
	 */
	public final PageFlowConversationContainer createConversationContainer()
	{
		return new PageFlowConversationContainer(maxConversations);
	}

	/**
	 * Obtain the conversation container from the page flow session instance. Create a new empty conversation container and set it 
	 * in the page flow session if existing container can be found.
	 * 
	 * @return PageFlowConversationContainer
	 */
	private PageFlowConversationContainer getConversationContainer() 
	{
		PageFlowSession<?> pageFlowSession = PageFlowSession.get();
		
		synchronized (pageFlowSession)
		{
			PageFlowConversationContainer container = pageFlowSession.getFlowState().getPageFlowConversationContainer();
			
			if (container == null) 
			{
				container = createConversationContainer();
				pageFlowSession.getFlowState().setPageFlowConversationContainer(container);
			}
			return container;			
		}
	}
}
