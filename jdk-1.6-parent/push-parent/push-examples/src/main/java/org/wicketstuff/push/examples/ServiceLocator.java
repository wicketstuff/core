package org.wicketstuff.push.examples;

import org.wicketstuff.push.examples.chatservice.ChatService;

public class ServiceLocator {

	private static final ChatService chatService = new ChatService();

	public static ChatService getChatService() {
		return chatService;
	}

	private ServiceLocator() {
		super();
	}
}
