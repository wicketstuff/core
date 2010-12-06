/**
 *
 */
package org.wicketstuff.push;

import java.util.Set;
import java.util.TreeSet;

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.IDetachListener;
import org.apache.wicket.settings.IFrameworkSettings;

/**
 *
 * @author Rodolfo Hansen
 */
public class PushDetachListener implements IDetachListener
{
  private static final Set<String> REGISTERED_APPLICATIONS =
    new TreeSet<String>();

  private final String key;
  private IDetachListener chainedListener;
  private IPushService service;

  private PushDetachListener(final IDetachListener listener, final IPushService service, String key)
  {
    this.chainedListener = listener;
    this.service = service;
    this.key = key;
  }

  @Override
  public void onDetach(Component component)
  {
    if (chainedListener != null)
      chainedListener.onDetach(component);
  }

  @Override
  public void onDestroyListener()
  {
    synchronized (REGISTERED_APPLICATIONS)
    {
      service.destroy();

      if (chainedListener != null)
        chainedListener.onDestroyListener();

      REGISTERED_APPLICATIONS.remove(key);
      service = null;
      chainedListener = null;
    }
  }

  public static void registerService(IPushService service, Application application) {
    String key = application.getApplicationKey();
    synchronized (REGISTERED_APPLICATIONS)
    {
      if (!REGISTERED_APPLICATIONS.contains(key)) {
        final IFrameworkSettings settings = application.getFrameworkSettings();
        IDetachListener prevL = settings.getDetachListener();
        settings.setDetachListener(new PushDetachListener(prevL, service, key));
        REGISTERED_APPLICATIONS.add(key);
      }
    }
  }
}
