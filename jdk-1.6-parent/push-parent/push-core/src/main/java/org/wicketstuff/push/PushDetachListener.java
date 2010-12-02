/**
 *
 */
package org.wicketstuff.push;

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.IClusterable;
import org.apache.wicket.IDetachListener;

/**
 *
 * @author Rodolfo Hansen
 */
public abstract class PushDetachListener implements IDetachListener, IClusterable
{
  private static final long serialVersionUID = 1L;

  private IDetachListener chainedListener;

  protected PushDetachListener(final Application app)
  {
    this.chainedListener = app.getFrameworkSettings().getDetachListener();
  }

  public final void onDetach(Component component)
  {
    if (chainedListener != null)
      chainedListener.onDetach(component);
  }

  public final void onDestroyListener()
  {
    destroyService();

    if (chainedListener != null)
      chainedListener.onDestroyListener();

    chainedListener = null;
  }

  protected abstract void destroyService();

}
