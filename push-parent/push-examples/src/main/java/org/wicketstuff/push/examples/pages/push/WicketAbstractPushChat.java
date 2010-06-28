package org.wicketstuff.push.examples.pages.push;

import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.wicketstuff.push.IPushService;
import org.wicketstuff.push.IPushTarget;
import org.wicketstuff.push.examples.pages.ExamplePage;

/**
 * Examples of chat using {@link IPushService}.
 * <p>
 * This example is abstract because it doesn't define which push service
 * implementation it uses.
 * <p>
 * Concrete subclasses only have to provide {@link #getPushService()}
 * implementation, returning any {@link IPushService} implementation.
 * <p>
 * The whole example doesn't depend on which implementation is used,
 * and show easy it is to switch between implementations.
 *
 * @author Vincent Demay
 * @author Xavier Hanin
 */
public abstract class WicketAbstractPushChat extends ExamplePage {
	private static final long serialVersionUID = 1L;

	/**
	 * Stores the unique chat room instance.
	 * In real world application, we wouldn't use a static instance for this,
	 * but probably something accessed from the application or the business layer
	 */
	private final static ChatRoom CHAT_ROOM = new ChatRoom();


	@SuppressWarnings("serial")
  public WicketAbstractPushChat(final PageParameters parameters)
	{
		final Message model = new Message();

		final Form<Message> formChat = new Form<Message>("chatForm", new CompoundPropertyModel<Message>(model));

		final TextField<String> field = new TextField<String>("user");
		field.setOutputMarkupId(false);
		formChat.add(field);

		final Label chat = new Label("chat", "");
		chat.setOutputMarkupId(true);
		formChat.add(chat);
		final IPushTarget pushTarget = getPushService().installPush(this);
		CHAT_ROOM.addListener(new ChatListener() {
			public void onMessage(final Message msg) {
				if (pushTarget.isConnected()) {
					pushTarget.appendJavascript("document.getElementById('" + chat.getMarkupId() + "').innerHTML += '" + msg + "<br/>'");
					pushTarget.trigger();
				} else {
					CHAT_ROOM.removeListener(this);
				}
			}
		});

		final TextField<String> mess = new TextField<String>("message");
		mess.setOutputMarkupId(true);
		formChat.add(mess);

		formChat.add(new AjaxButton("send", formChat){
			@Override
			protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {
				// send a message in the chat room
				CHAT_ROOM.send((Message)form.getModelObject());

				//clear message area add focus it
				target.appendJavascript("document.getElementById('" + mess.getMarkupId() + "').value =''");
				target.focusComponent(mess);
			}
		});
		add(formChat);
	}


	protected abstract IPushService getPushService();
}
