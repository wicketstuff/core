package org.wicketstuff.push.timer;

import java.io.Serializable;
import java.util.Map;

import org.apache.wicket.Component;
import org.apache.wicket.util.time.Duration;
import org.wicketstuff.push.ChannelEvent;
import org.wicketstuff.push.IChannelListener;
import org.wicketstuff.push.IChannelPublisher;
import org.wicketstuff.push.IChannelService;
import org.wicketstuff.push.IPushTarget;

public class TimerChannelService implements IChannelService, Serializable {
	private static final long serialVersionUID = 1L;

	private final Duration duration;
	private final IChannelPublisher publisher = new TimerChannelPublisher();

	public TimerChannelService(final Duration duration) {
		this.duration = duration;
	}

	public void addChannelListener(final Component component,
			final String listenerChannel, final IChannelListener listener) {
		
		final TimerChannelBehavior timerChannelBehavior = new TimerChannelBehavior(
				duration);
		final IPushTarget pushTarget = timerChannelBehavior.newPushTarget();
		component.add(timerChannelBehavior);
		EventStore.get().addEventStoreListener(new EventStoreListener() {

			public void eventTriggered(final String eventChannel,
					final Map<String, String> data) {
				if (listenerChannel.equals(eventChannel)) {
					listener.onEvent(listenerChannel, data, pushTarget);
					pushTarget.trigger();
				}
			}

		});
	}

	public void publish(final ChannelEvent event) {
		publisher.publish(event);
	}

}
