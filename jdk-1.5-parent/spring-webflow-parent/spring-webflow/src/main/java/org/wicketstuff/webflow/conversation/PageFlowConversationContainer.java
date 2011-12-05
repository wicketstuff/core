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
 * File: PageFlowConversationContainer.java
 *****************************************************************************
 *****************************************************************************/
package org.wicketstuff.webflow.conversation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.core.JdkVersion;
import org.springframework.webflow.conversation.Conversation;
import org.springframework.webflow.conversation.ConversationId;
import org.springframework.webflow.conversation.ConversationParameters;
import org.springframework.webflow.conversation.NoSuchConversationException;
import org.springframework.webflow.conversation.impl.SimpleConversationId;

/**
 * Container for conversations that is stored in the Wicket Application's custom session. When the session expires this container will go with it,
 * implicitly expiring all contained conversations.
 *
 * @author Clint Checketts, Florian Braun, Doug Hall, Subramanian Murali
 * @version $Id: $
 */
public class PageFlowConversationContainer implements Serializable
{
	private static final long	serialVersionUID	= 1L;

	/**
	 * Maximum number of conversations in this container. -1 for unlimited.
	 */
	private int maxConversations;

	/**
	 * The contained conversations. A list of {@link PageFlowContainedConversation} objects.
	 */
	private List<PageFlowContainedConversation> conversations;

	/**
	 * The sequence for unique conversation identifiers within this container.
	 */
	private int conversationIdSequence;

	/**
	 * Create a new conversation container.
	 *
	 * @param maxConversations the maximum number of allowed concurrent conversations, -1 for unlimited
	 */
	public PageFlowConversationContainer(int maxConversations) 
	{
		this.maxConversations = maxConversations;
		this.conversations = new ArrayList<PageFlowContainedConversation>();
	}

	/**
	 * Returns the current size of the conversation container: the number of conversations contained within it.
	 *
	 * @return a int.
	 */
	public int size()
	{
		return conversations.size();
	}

	/**
	 * Create a new conversation based on given parameters and add it to the container.
	 *
	 * @param parameters descriptive conversation parameters
	 * @param lockFactory the lock factory to use to create the conversation lock
	 * @return the created conversation
	 */
	public synchronized Conversation createConversation(ConversationParameters parameters,
			PageFlowConversationLockFactory lockFactory) 
	{
		PageFlowContainedConversation conversation = new PageFlowContainedConversation(this, nextId(), lockFactory.createLock());
		conversation.putAttribute("name", parameters.getName());
		conversation.putAttribute("caption", parameters.getCaption());
		conversation.putAttribute("description", parameters.getDescription());
		conversations.add(conversation);
		if (maxExceeded()) 
		{
			// end oldest conversation
			((Conversation) conversations.get(0)).end();
		}
		return conversation;
	}

	private ConversationId nextId() 
	{
		if (JdkVersion.getMajorJavaVersion() == JdkVersion.JAVA_15)
		{
			return new SimpleConversationId(Integer.valueOf(++conversationIdSequence));
		} 
		else 
		{
			return new SimpleConversationId(new Integer(++conversationIdSequence));
		}
	}

	/**
	 * Return the identified conversation.
	 *
	 * @param id the id to lookup
	 * @return the conversation
	 * @throws org.springframework.webflow.conversation.NoSuchConversationException if the conversation cannot be found
	 */
	public synchronized Conversation getConversation(ConversationId id) throws NoSuchConversationException 
	{
		for (Iterator<PageFlowContainedConversation> it = conversations.iterator(); it.hasNext();)
		{
			PageFlowContainedConversation conversation = it.next();
			if (conversation.getId().equals(id)) 
			{
				return conversation;
			}
		}
		throw new NoSuchConversationException(id);
	}

	/**
	 * Remove identified conversation from this container.
	 *
	 * @param id a {@link org.springframework.webflow.conversation.ConversationId} object.
	 */
	public synchronized void removeConversation(ConversationId id) 
	{
		for (Iterator<PageFlowContainedConversation> it = conversations.iterator(); it.hasNext();)
		{
			PageFlowContainedConversation conversation = it.next();
			if (conversation.getId().equals(id))
			{
				it.remove();
				break;
			}
		}
	}

	/**
	 * Has the maximum number of allowed concurrent conversations in the session been exceeded?
	 */
	private boolean maxExceeded() 
	{
		return maxConversations > 0 && conversations.size() > maxConversations;
	}
}
