package org.wicketstuff.ki;

import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.jsecurity.SecurityUtils;
import org.jsecurity.subject.Subject;

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
