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
package org.wicketstuff.push.examples.pages;

import static org.wicketstuff.push.examples.ServiceLocator.getChatService;

import java.text.SimpleDateFormat;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnLoadHeaderItem;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.push.AbstractPushEventHandler;
import org.wicketstuff.push.IPushEventContext;
import org.wicketstuff.push.IPushNode;
import org.wicketstuff.push.IPushNodeDisconnectedListener;
import org.wicketstuff.push.IPushService;
import org.wicketstuff.push.IPushServiceRef;
import org.wicketstuff.push.examples.chatservice.ChatRoom;
import org.wicketstuff.push.examples.chatservice.IChatListener;
import org.wicketstuff.push.examples.chatservice.Message;

/**
 * Examples of chat using {@link IPushService}.
 * <p>
 * This example is abstract because it doesn't define which push service implementation it uses.
 * <p>
 * The whole example doesn't depend on which implementation is used, and show easy it is to switch
 * between implementations.
 * 
 * @author Vincent Demay
 * @author Xavier Hanin
 * @author <a href="http://sebthom.de/">Sebastian Thomschke</a>
 */
public abstract class WicketAbstractChatPage extends WebPage
{
	private static final long serialVersionUID = 1L;

	private String user;
	private String message;
	private AjaxButton sendMessage;

	public WicketAbstractChatPage(final PageParameters parameters,
		final String pushImplementationTitle, final IPushServiceRef<?> pushServiceRef)
	{
		super(parameters);

		add(new Label("push-implementation-title", pushImplementationTitle));

		final String chatRoomName = "#wicket-push";
		final ChatRoom chatRoom = getChatService().getChatRoom(chatRoomName);

		/*
		 * add form and fields
		 */
		final Form<Object> formChat = new Form<Object>("chatForm",
			new CompoundPropertyModel<Object>(this));
		add(formChat);

		// chat history field
		final Label chatHistoryField = new Label("chatHistory", "");
		chatHistoryField.setEscapeModelStrings(false);
		chatHistoryField.setOutputMarkupId(true);
		formChat.add(chatHistoryField);

		// chat room name
		formChat.add(new Label("chatroomName", chatRoomName));

		// user field
		formChat.add(new TextField<String>("user"));

		// message field
		final TextField<String> messageField = new TextField<String>("message");
		messageField.setOutputMarkupId(true);
		formChat.add(messageField);

		// send button
		formChat.add(sendMessage = new AjaxButton("send", formChat)
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void onError(final AjaxRequestTarget target, final Form<?> form)
			{
				// nothing
			}

			@Override
			protected void onSubmit(final AjaxRequestTarget target, final Form<?> form)
			{
				// send a message in the chat room
				getChatService().getChatRoom(chatRoomName).sendAsync(user, message);

				// clear message area add focus it
				target.appendJavaScript("document.getElementById('" + messageField.getMarkupId() +
					"').value =''");
				target.focusComponent(messageField);
			}
		});

		/*
		 * install push node
		 */
		final IPushNode<Message> pushNode = pushServiceRef.get().installNode(this,
			new AbstractPushEventHandler<Message>()
			{
				private static final long serialVersionUID = 1L;

				@Override
				public void onEvent(final AjaxRequestTarget target, final Message message,
					final IPushNode<Message> node, final IPushEventContext<Message> ctx)
				{
					appendHTML(target, chatHistoryField, _renderMessage(message));
				}
			});

		// disconnect button
		formChat.add(new AjaxButton("disconnect", formChat)
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void onError(final AjaxRequestTarget target, final Form<?> form)
			{
				// nothing
			}

			@Override
			protected void onSubmit(final AjaxRequestTarget target, final Form<?> form)
			{
				pushServiceRef.get().uninstallNode(WicketAbstractChatPage.this, pushNode);
				target.add(setEnabled(false));
				target.add(sendMessage.setEnabled(false));
			}
		});

		/*
		 * connect to chat room
		 */
		chatRoom.addListener(new IChatListener()
		{
			@Override
			public void onMessage(final Message msg)
			{
				if (pushServiceRef.get().isConnected(pushNode))
					pushServiceRef.get().publish(pushNode, msg);
				else
					chatRoom.removeListener(this);
			}
		});

		/*
		 * render chat history
		 */
		final StringBuilder sb = new StringBuilder();
		for (final Message msg : chatRoom.getChatHistory())
			sb.append(_renderMessage(msg));
		chatHistoryField.setDefaultModelObject(sb);

		/*
		 * install disconnect listener
		 */
		pushServiceRef.get().addNodeDisconnectedListener(new IPushNodeDisconnectedListener()
		{
			@Override
			public void onDisconnect(final IPushNode<?> node)
			{
				if (node.equals(pushNode))
				{
					chatRoom.sendAsync("<System>", "A USER JUST LEFT THE ROOM.");
					pushServiceRef.get().removeNodeDisconnectedListener(this);
				}
			}
		});

		add(new Behavior()
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void renderHead(final Component component, final IHeaderResponse response)
			{
				super.renderHead(component, response);

				response.render(OnLoadHeaderItem.forScript("var chatHistory = document.getElementById('" +
					chatHistoryField.getMarkupId() +
					"'); chatHistory.scrollTop = chatHistory.scrollHeight;"));
			}
		});
	}

	private String _renderMessage(final Message msg)
	{
		final String date = new SimpleDateFormat("h:mm a").format(msg.getDate());

		return date + //
			" <b>" + msg.getUser() + "</b>" + //
			" said" + //
			" <b>" + msg.getMessage() + "</b><br>";
	}
}
