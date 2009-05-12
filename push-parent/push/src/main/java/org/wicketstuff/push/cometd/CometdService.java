package org.wicketstuff.push.cometd;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.protocol.http.WebApplication;
import org.cometd.Bayeux;
import org.cometd.Channel;
import org.cometd.Client;
import org.cometd.Message;
import org.cometd.MessageListener;
import org.cometd.RemoveListener;
import org.mortbay.cometd.AbstractBayeux;
import org.mortbay.cometd.continuation.ContinuationBayeux;
import org.wicketstuff.push.ChannelEvent;
import org.wicketstuff.push.IChannelListener;
import org.wicketstuff.push.IChannelService;

/**
 * Cometd based implementation of {@link IChannelService}.
 * <p>
 * This service is based on cometd client implemented by the dojo toolkit, which
 * must be embedded in your application.
 * <p>
 * This implementation relies on cometd for updating the page, but actually uses
 * regular cometd events, which, if a channel listener is properly installed on
 * a component of the page using
 * {@link #addChannelListener(Component, String, IChannelListener)}, will
 * trigger a wicket ajax call to get the page actually refreshed using regular
 * wicket ajax mechanisms.
 * <p>
 * This mean that each time an event is published, a new connection is made to
 * the server to get the actual page update required by the
 * {@link IChannelListener}.
 *
 * @author Xavier Hanin
 * @author Rodolfo Hansen
 * @see IChannelService
 */
public class CometdService implements IChannelService {

  private final class RemovalForwardingListener implements MessageListener {

    public void deliver(final Client fromClient, final Client toClient,
        final Message msg) {
      final String channel = (String) msg.get("subscription");
      if (removalListeners.containsKey(channel)) {
        fromClient.addListener(removalListeners.get(channel));
      }
    }
  }

  private final class RemovalListener implements RemoveListener {

    private final RemoveListener removeListener;

    public RemovalListener(final RemoveListener listener) {
      removeListener = listener;
    }

    public void removed(final String clientId, final boolean timeout) {
      final boolean hasNoApp = !Application.exists();
      if (hasNoApp) {
        Application.set(getApplication());
      }

      removeListener.removed(clientId, timeout);

      if (hasNoApp) {
        Application.unset();
      }
    }

  }

  public static final String BAYEUX_CLIENT_PREFIX = "wicket-push";

  private final Map<String, RemoveListener> removalListeners;

  final WebApplication _application;
  private Bayeux       _bayeux;
  private Client       serviceClient;
  private boolean      listeningToConnect;

  public CometdService(final WebApplication application) {
    _application = application;
    removalListeners = Collections
        .synchronizedMap(new WeakHashMap<String, RemoveListener>());
    listeningToConnect = false;
  }

  public void addChannelListener(final Component component,
      final String channel, final IChannelListener listener) {
    component.add(new CometdBehavior(channel, listener));
  }

  /**
   * Cometd Specific method to Listen for client removals
   *
   * @param channel
   * @param listener
   */
  public void addChannelRemoveListener(final String chnl,
      final RemoveListener listener) {
    if (!listeningToConnect) {
      getBayeux().getChannel(Bayeux.META_SUBSCRIBE, true).subscribe(
          serviceClient);
      serviceClient.addListener(new RemovalForwardingListener());
      listeningToConnect = true;
    }
    removalListeners.put("/" + chnl, new RemovalListener(listener));
  }

  /**
   * Implementation of {@link IChannelService#publish(ChannelEvent)}, which
   * actually sends a cometd event to the client with a "proxy" attribute set to
   * "true", which in turn triggers a wicket ajax call to get the listener
   * notified and update the page.
   *
   * @event the event to publish, which will be modify with "proxy" set to
   *        "true"
   */
  public void publish(final ChannelEvent event) {
    /*
     * to avoid using implementation specific events, we set the proxy data
     * here. this property is used by CometdDefaultBehaviorTemplate.js to know
     * that the event should actually be converted in a wicket ajax call to get
     * the actual page refresh
     */
    event.addData("proxy", "true");
    final String channelId = "/" + event.getChannel();
    final Channel channel = getBayeux().getChannel(channelId, true);
    channel.publish(serviceClient, event.getData(), event.getId());
  }

  private final Bayeux getBayeux() {
    if (_bayeux == null) {
      initBayeux();
    }

    return _bayeux;
  }

  /**
   * Initializes the Jetty CometD Bayeux Service to be used.
   */
  private void initBayeux() {
    final String cfgType = _application.getConfigurationType();
    _bayeux = (Bayeux) _application.getServletContext().getAttribute(
        Bayeux.ATTRIBUTE);

    if (_bayeux instanceof AbstractBayeux
        && Application.DEVELOPMENT.equalsIgnoreCase(cfgType)) {
      ((AbstractBayeux) _bayeux).setLogLevel(2);
    }

    if (_bayeux instanceof ContinuationBayeux) {
      ((ContinuationBayeux) _bayeux).setJSONCommented(true);
    }

    serviceClient = _bayeux.newClient(BAYEUX_CLIENT_PREFIX);
  }

  public Application getApplication() {
    return _application;
  }

}
