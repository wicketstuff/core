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
 * File: PageFlowContainedConversation.java
 *****************************************************************************
 *****************************************************************************/
package org.wicketstuff.webflow.conversation;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.springframework.webflow.conversation.Conversation;
import org.springframework.webflow.conversation.ConversationId;
import org.springframework.webflow.conversation.impl.ConversationLock;
import org.wicketstuff.webflow.session.PageFlowSession;


/**
 * Conversation implementation class used internally by the Page Flow Conversation Container.
 *
 * @author Clint Checketts, Florian Braun, Doug Hall, Subramanian Murali
 * @version $Id: $
 */
public class PageFlowContainedConversation implements Conversation, Serializable
{
	private static final long	serialVersionUID	= 1L;

	private PageFlowConversationContainer container;

	private ConversationId id;

	private ConversationLock lock;

	private Map<Object, Object> attributes;

	/**
	 * Create a new contained conversation.
	 *
	 * @param container the container containing the conversation
	 * @param id the unique id assigned to the conversation
	 * @param lock the conversation lock
	 */
	public PageFlowContainedConversation(PageFlowConversationContainer container, ConversationId id, ConversationLock lock) 
	{
		this.container = container;
		this.id = id;
		this.lock = lock;
		this.attributes = new HashMap<Object, Object>();
	}

	/* (non-Javadoc)
	 * @see org.springframework.webflow.conversation.Conversation#getId()
	 */
	/**
	 * <p>Getter for the field <code>id</code>.</p>
	 *
	 * @return a {@link org.springframework.webflow.conversation.ConversationId} object.
	 */
	public ConversationId getId() 
	{
		return id;
	}

	/* (non-Javadoc)
	 * @see org.springframework.webflow.conversation.Conversation#lock()
	 */
	/**
	 * <p>lock.</p>
	 */
	public void lock() 
	{
		lock.lock();
	}

	/* (non-Javadoc)
	 * @see org.springframework.webflow.conversation.Conversation#getAttribute(java.lang.Object)
	 */
	/** {@inheritDoc} */
	public Object getAttribute(Object name) 
	{
		return attributes.get(name);
	}

	/* (non-Javadoc)
	 * @see org.springframework.webflow.conversation.Conversation#putAttribute(java.lang.Object, java.lang.Object)
	 */
	/** {@inheritDoc} */
	public void putAttribute(Object name, Object value)
	{
		attributes.put(name, value);
	}

	/* (non-Javadoc)
	 * @see org.springframework.webflow.conversation.Conversation#removeAttribute(java.lang.Object)
	 */
	/** {@inheritDoc} */
	public void removeAttribute(Object name) 
	{
		attributes.remove(name);
	}

	/* (non-Javadoc)
	 * @see org.springframework.webflow.conversation.Conversation#end()
	 */
	/**
	 * <p>end.</p>
	 */
	public void end() 
	{
		container.removeConversation(getId());
	}

	/* (non-Javadoc)
	 * @see org.springframework.webflow.conversation.Conversation#unlock()
	 */
	/**
	 * <p>unlock.</p>
	 */
	public void unlock() 
	{
		lock.unlock();
		
		// re-bind the conversation container in the session
		// this is required to make session replication work correctly in
		// a clustered environment
		// we do this after releasing the lock since we're no longer
		// manipulating the contents of the conversation
//		SharedAttributeMap sessionMap = ExternalContextHolder.getExternalContext().getSessionMap();
//		synchronized (sessionMap.getMutex()) 
//		{
//			sessionMap.put(container.getSessionKey(), container);
//		}
		PageFlowSession<?> pageFlowSession = PageFlowSession.get();
		
		synchronized (pageFlowSession)
		{
			pageFlowSession.getFlowState().setPageFlowConversationContainer(container);
		}		
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	/**
	 * <p>toString.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String toString()
	{
		return getId().toString();
	}

	// id based equality
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	/** {@inheritDoc} */
	public boolean equals(Object obj) 
	{
		if (!(obj instanceof PageFlowContainedConversation)) 
		{
			return false;
		}
		return id.equals(((PageFlowContainedConversation) obj).id);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	/**
	 * <p>hashCode.</p>
	 *
	 * @return a int.
	 */
	public int hashCode() 
	{
		return id.hashCode();
	}
}
