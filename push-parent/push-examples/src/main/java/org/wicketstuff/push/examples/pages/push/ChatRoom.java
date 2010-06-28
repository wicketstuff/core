package org.wicketstuff.push.examples.pages.push;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class ChatRoom {
	private List<ChatListener> listeners = new CopyOnWriteArrayList<ChatListener>();
	private StringBuffer chat = new StringBuffer();
	
	public void addListener(ChatListener l) {
		listeners.add(l);
	}
	
	public void removeListener(ChatListener l) {
		listeners.remove(l);
	}

	public void send(Message message) {
		chat.append(message.toString()).append("\n");
		for (ChatListener l : listeners) {
			l.onMessage(message);
		}
	}

	public String getChat() {
		return chat.toString();
	}
}
