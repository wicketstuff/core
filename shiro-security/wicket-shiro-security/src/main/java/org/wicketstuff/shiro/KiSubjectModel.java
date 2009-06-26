package org.wicketstuff.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;

/**
 * A simple wrapper for to access {@link SecurityUtils#getSubject()}
 * as an {@link IModel}
 */
public class KiSubjectModel extends AbstractReadOnlyModel<Subject>
{
  @Override
  public Subject getObject() {
    return SecurityUtils.getSubject();
  }
}
