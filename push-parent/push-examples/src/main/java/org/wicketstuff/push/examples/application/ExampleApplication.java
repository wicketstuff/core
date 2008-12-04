package org.wicketstuff.push.examples.application;

import java.io.Serializable;

import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.time.Duration;
import org.wicketstuff.push.IChannelService;
import org.wicketstuff.push.IPushService;
import org.wicketstuff.push.cometd.CometdService;
import org.wicketstuff.push.examples.pages.Index;
import org.wicketstuff.push.timer.TimerChannelService;
import org.wicketstuff.push.timer.TimerPushService;

/**
 * Runs the ExampleApplication when invoked from command line.
 */
public class ExampleApplication extends WebApplication implements Serializable {
  private static final long serialVersionUID = 1L;

  private final IChannelService cometdService;
	private final IChannelService timerChannelService;
	private final IPushService timerPushService;

	/**
	 * Constructor
	 */
	public ExampleApplication() {
		cometdService = new CometdService(this);
		timerChannelService = new TimerChannelService(Duration.seconds(2));
		timerPushService = new TimerPushService(Duration.seconds(2));
	}

	/**
	 * @see wicket.Application#getHomePage()
	 */
	@Override
  public Class getHomePage() {
		return Index.class;
	}

	public IChannelService getCometdService() {
		return cometdService;
	}

	public IChannelService getTimerChannelService() {
		return timerChannelService;
	}

	public IPushService getTimerPushService() {
		return timerPushService;
	}
}