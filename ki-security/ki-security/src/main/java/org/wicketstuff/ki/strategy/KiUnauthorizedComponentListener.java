package org.wicketstuff.ki.strategy;

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.RestartResponseException;
import org.apache.wicket.authorization.IUnauthorizedComponentInstantiationListener;
import org.jsecurity.SecurityUtils;
import org.jsecurity.subject.Subject;

public class KiUnauthorizedComponentListener implements IUnauthorizedComponentInstantiationListener 
{
  private final Class<? extends Page> loginPage;
  private final Class<? extends Page> unauthorizedPage;
  
  public KiUnauthorizedComponentListener( Class<? extends Page> loginPage, Class<? extends Page> unauthorizedPage ) 
  {
    this.loginPage = loginPage;
    this.unauthorizedPage = unauthorizedPage;
  }

  public void onUnauthorizedInstantiation(Component component) {
    Subject subject = SecurityUtils.getSubject();
    if( subject.isAuthenticated() ) {
      throw new RestartResponseException( unauthorizedPage );
    }
    throw new RestartResponseAtInterceptPageException( loginPage );
  }
}
