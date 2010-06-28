package org.wicketstuff.push.cometd;

import java.lang.ref.WeakReference;

import org.apache.wicket.Application;
import org.apache.wicket.Session;

/** Base class for Wicket enabled removal Listeners.
 * Allows the Wicket Application singleton, and the listener's current
 * web session to safely traverses thread boundaries.
 * @author rhansen@kindleit.net
 */
abstract class RemovalListener implements WicketRemoveListener {

  private final WeakReference<? extends Application> _application;
  private final WeakReference<? extends Session> _listenerSession;

  public RemovalListener(final Application app, final Session sess) {
    _application = new WeakReference<Application>(app);
    _listenerSession = new WeakReference<Session>(sess);
  }

  public Application getApplication() {
    return _application.get();
  }

  public Session getListenerSession() {
    return _listenerSession.get();
  }

  public abstract void removed(String clientId, boolean timeout);

}
