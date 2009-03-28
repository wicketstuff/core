package org.wicketstuff.ki;

import javax.servlet.http.HttpServletRequest;

import org.apache.wicket.Application;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WebRequestCycle;
import org.jsecurity.SecurityUtils;
import org.jsecurity.mgt.SecurityManager;
import org.jsecurity.subject.Subject;
import org.jsecurity.web.servlet.JSecurityFilter;

/**
 * A simple wrapper for to access {@link SecurityUtils#getSecurityManager()}
 * as an {@link IModel}
 */
public class KiSecurityManagerModel extends LoadableDetachableModel<SecurityManager>
{
  @Override
  protected SecurityManager load() {
    SecurityManager m = SecurityUtils.getSecurityManager();
    if( m == null ) {
      m = (SecurityManager)WebApplication.get().getServletContext().getAttribute( JSecurityFilter.SECURITY_MANAGER_CONTEXT_KEY );
    
      HttpServletRequest req = 
        ((WebRequestCycle)RequestCycle.get()).getWebRequest().getHttpServletRequest();
      System.out.println( "REQ:"+req.getClass() );
    }
    return m;
  }
}
