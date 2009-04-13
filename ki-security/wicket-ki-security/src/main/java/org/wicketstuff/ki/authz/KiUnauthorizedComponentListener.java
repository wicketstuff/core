package org.wicketstuff.ki.authz;

import java.lang.annotation.Annotation;

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.RestartResponseException;
import org.apache.wicket.Session;
import org.apache.wicket.authorization.IUnauthorizedComponentInstantiationListener;
import org.jsecurity.SecurityUtils;
import org.jsecurity.subject.Subject;
import org.wicketstuff.ki.authz.annotations.AnnotationsKiAuthorizationStrategy;
import org.wicketstuff.ki.authz.annotations.InstantiationRequiresAuthentication;
import org.wicketstuff.ki.authz.annotations.InstantiationRequiresPermission;
import org.wicketstuff.ki.authz.annotations.InstantiationRequiresRole;

public class KiUnauthorizedComponentListener implements IUnauthorizedComponentInstantiationListener 
{
  private final Class<? extends Page> loginPage;
  private final Class<? extends Page> unauthorizedPage;
  private AnnotationsKiAuthorizationStrategy annotationStrategy = null;
  
  public KiUnauthorizedComponentListener( 
      Class<? extends Page> loginPage, 
      Class<? extends Page> unauthorizedPage, 
      AnnotationsKiAuthorizationStrategy s  ) 
  {
    this.loginPage = loginPage;
    this.unauthorizedPage = unauthorizedPage;
    annotationStrategy = s;
  }

  public void onUnauthorizedInstantiation(Component component) 
  {
    Subject subject = SecurityUtils.getSubject();
    boolean unauth = subject.getPrincipal() != null;
    Class<? extends Page> page = unauth ? unauthorizedPage : loginPage;
    
    if( annotationStrategy != null ) {
      Annotation fail = annotationStrategy.checkInvalidInstantiation(  component.getClass() );
      if( fail != null ) {
        // Annotation inheretance would sure be nice!!!

        // Check Permissions
        if( fail instanceof InstantiationRequiresPermission) {
          InstantiationRequiresPermission perm = (InstantiationRequiresPermission)fail;
          if( unauth ) {
            if( perm.unauthorizedMessage().length() > 0 ) {
              Session.get().error( getMessage(perm.unauthorizedMessage(), fail, component ) );
            }
            if( perm.unauthorizedPage() != Page.class ) {
              page = perm.unauthorizedPage();
            }
          }
          else {
            if( perm.loginMessage() .length() > 0 ) {
              Session.get().info( getMessage(perm.loginMessage(), fail, component ) );
            }
            if( perm.loginPage() != Page.class ) {
              page = perm.loginPage();
            }
          }
        }
        
        // Check Roles
        if( fail instanceof InstantiationRequiresRole) {
          InstantiationRequiresRole role = (InstantiationRequiresRole)fail;
          if( unauth ) {
            if( role.unauthorizedMessage().length() > 0 ) {
              Session.get().info( "XXXXXXXXXXX" );  // HYMMM Why is this not showing up?!?!?!?!?!?!
              Session.get().error( getMessage(role.unauthorizedMessage(), fail, component ) );
            }
            if( role.unauthorizedPage() != Page.class ) {
              page = role.unauthorizedPage();
            }
          }
          else {
            if( role.loginMessage().length() > 0 ) {
              Session.get().info( getMessage(role.loginMessage(), fail, component ) );
            }
            if( role.loginPage() != Page.class ) {
              page = role.loginPage();
            }
          }
        }
        
        if( fail instanceof InstantiationRequiresAuthentication ) {
          if( !unauth ) {
            InstantiationRequiresAuthentication a = (InstantiationRequiresAuthentication)fail;
            if( a.loginMessage().length() > 0 ) {
              Session.get().info( getMessage(a.loginMessage(), fail, component ) );
            }
            if( a.loginPage() != Page.class ) {
              page = a.loginPage();
            }
          }
        }
      }
    }
    
    if( unauth ) {
      throw new RestartResponseException( page );
    }
    throw new RestartResponseAtInterceptPageException( page );
  }
  

  public AnnotationsKiAuthorizationStrategy getAnnotationStrategy() {
    return annotationStrategy;
  }

  public void setAnnotationStrategy(
      AnnotationsKiAuthorizationStrategy annotationStrategy) {
    this.annotationStrategy = annotationStrategy;
  }

  //----------------------------------------------------------------------------
  //----------------------------------------------------------------------------
  
  protected String getMessage( String key, Annotation anno, Component comp )
  {
    return key; // TODO, this could be more complicated....
  }

}
