package org.wicketstuff.push.examples.pages;

import java.io.Serializable;
import java.util.Map;

import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.wicketstuff.push.ChannelEvent;
import org.wicketstuff.push.IChannelListener;
import org.wicketstuff.push.IChannelService;
import org.wicketstuff.push.IChannelTarget;

/**
 * Examples of chat using {@link IChannelService}.
 * <p>
 * This example is abstract because it doesn't define which channel service
 * implementation it uses.
 * <p>
 * Concrete subclasses only have to provide {@link #getChannelService()}
 * implementation, returning any IChannelService implementation.
 * <p>
 * The whole example doesn't depend on which implementation is used,
 * and show easy it is to switch between implementations.
 *
 * @author Vincent Demay
 * @author Xavier Hanin
 */
public abstract class WicketAbstractChat extends ExamplePage {
	private static final long serialVersionUID = 1L;


	public WicketAbstractChat(final PageParameters parameters)
	{
		final Message model = new Message();

		final Form formChat = new Form("chatForm", new CompoundPropertyModel(model));

		final TextField field = new TextField("user");
		field.setOutputMarkupId(false);
		formChat.add(field);

		final Label chat = new Label("chat");
		chat.setOutputMarkupId(true);
		getChannelService().addChannelListener(this, "chat", new IChannelListener() {
			public void onEvent(final String channel, final Map datas, final IChannelTarget target) {
				target.appendJavascript("document.getElementById('" + chat.getMarkupId() + "').innerHTML += '<br/>" + datas.get("message") + "'");
			}
		});
		formChat.add(chat);

		final TextField mess = new TextField("message");
		mess.setOutputMarkupId(true);
		formChat.add(mess);

		formChat.add(new AjaxButton("send", formChat){
			@Override
			protected void onSubmit(final AjaxRequestTarget target, final Form form) {
				//Update message
				final String currentChat =
							((Message)form.getModelObject()).getUser() + " said " +
							((Message)form.getModelObject()).getMessage();
				//send an event to refesh the chat area
				final ChannelEvent event = new ChannelEvent("chat");
				event.addData("message", currentChat);
				getChannelService().publish(event);

				//clear message area add focus it
				target.appendJavascript("document.getElementById('" + mess.getMarkupId() + "').value =''");
				target.focusComponent(mess);
			}
		});
		add(formChat);
	}


	protected abstract IChannelService getChannelService();


	public class Message implements Serializable {
		private String chat;
		private String user;
		private String message;
		public String getMessage() {
			return message;
		}
		public void setMessage(final String message) {
			this.message = message;
		}
		public String getUser() {
			return user;
		}
		public void setUser(final String user) {
			this.user = user;
		}
		public String getChat() {
			return chat;
		}
		public void setChat(final String chat) {
			this.chat = chat;
		}
	}
}
