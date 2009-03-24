package org.wicketstuff.ki;

import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.jsecurity.SecurityUtils;
import org.jsecurity.mgt.SecurityManager;
import org.jsecurity.subject.Subject;

/**
 * A simple wrapper for to access {@link SecurityUtils#getSecurityManager()}
 * as an {@link IModel}
 */
public class KiSecurityManagerModel extends AbstractReadOnlyModel<SecurityManager>
{
  @Override
  public SecurityManager getObject() {
    return SecurityUtils.getSecurityManager();
  }
}
